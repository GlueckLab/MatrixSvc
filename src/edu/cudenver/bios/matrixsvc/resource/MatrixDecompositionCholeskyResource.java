/*
 * Power Service for the GLIMMPSE Software System.  Processes
 * incoming HTTP requests for power, sample size, and detectable
 * difference
 * 
 * Copyright (C) 2010 Regents of the University of Colorado.  
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package edu.cudenver.bios.matrixsvc.resource;


import edu.cudenver.bios.matrixsvc.application.MatrixConstants;
import edu.cudenver.bios.matrixsvc.application.MatrixLogger;
import edu.cudenver.bios.matrixsvc.application.MatrixServiceParameters;
import edu.cudenver.bios.matrixsvc.application.NamedRealMatrix;
import edu.cudenver.bios.matrixsvc.representation.ErrorXMLRepresentation;
import edu.cudenver.bios.matrixsvc.representation.MatrixXmlRepresentation;

import org.apache.commons.math.linear.CholeskyDecompositionImpl;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.DomRepresentation;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.Variant;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Resource for handling requests for Cholesky Decomposition calculations.
 * See the MatrixApplication class for URI mappings
 * 
 * @author Jonathan Cohen
 */
public class MatrixDecompositionCholeskyResource extends Resource
{
	/**
	 * Create a new resource to handle matrix requests.  Data
	 * is returned as XML.
	 * 
	 * @param context restlet context
	 * @param request http request object
	 * @param response http response object
	 */
    public MatrixDecompositionCholeskyResource(Context context, Request request, Response response) 
    {
        super(context, request, response);
        getVariants().add(new Variant(MediaType.APPLICATION_XML));
    }

    /**
     * Disallow GET requests
     */
    @Override
    public boolean allowGet()
    {
        return false;
    }

    /**
     * Disallow PUT requests
     */
    @Override
    public boolean allowPut()
    {
        return false;
    }

    /**
     * Allow POST requests to create a power list
     */
    @Override
    public boolean allowPost() 
    {
        return  true;
    }

    /**
     * This operation takes a single square matrix (p x p) 
     * and returns two matrices: the matrix representing its 
     * square root (L), and its transpose (L’).  Please see 
     * Matrix Services REST API documentation for details on
     * the entity body format.
     * 
     * @param entity HTTP entity body for the request
     */
    @Override 
    public void acceptRepresentation(Representation entity)
    {
        DomRepresentation rep = new DomRepresentation(entity);
        NamedRealMatrix matrixInput = null;
        ArrayList<NamedRealMatrix> matrixList = null;
        NamedRealMatrix matrixL = null;
        NamedRealMatrix matrixTranspose = null;
        try
        {
        	// parse the parameters from the entity body
            MatrixServiceParameters params = MatrixParamParser.
              getDecompCholeskyParamsFromDomNode( rep.getDocument().getDocumentElement() );

            // get the matrix from the list
            matrixInput = params.getMatrixListFromRequest().get(0);
            
            if(matrixInput == null ||
               !matrixInput.getName().equalsIgnoreCase("A")){
            	throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
            			"Couldn't retrieve the matrix for Cholesky Decomposition."); 
            }
            
            // perform Cholesky Decomposition
            CholeskyDecompositionImpl cdImpl = new CholeskyDecompositionImpl(matrixInput);
            
            matrixL = new NamedRealMatrix( cdImpl.getL() );
            matrixTranspose = new NamedRealMatrix( cdImpl.getLT() );
            
            matrixL.setName(MatrixConstants.SQ_ROOT_MATRIX_RETURN_NAME);
            matrixTranspose.setName(MatrixConstants.TRANSPOSE_MATRIX_RETURN_NAME);
            
            //put them in a list
            ArrayList<NamedRealMatrix> list = new ArrayList<NamedRealMatrix>();
            list.add(matrixL);
            list.add(matrixTranspose);
            
            //put the list in the parameter object
            params.setMatrixListForResponse(list);
            
            //create our response representation
            MatrixXmlRepresentation response = new MatrixXmlRepresentation(params);
            getResponse().setEntity(response); 
            getResponse().setStatus(Status.SUCCESS_CREATED);
        }
        catch (ResourceException re)
        {
            MatrixLogger.getInstance().error(re.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(re.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(re.getStatus());
        }
        catch (Exception e)
        {
        	 MatrixLogger.getInstance().error(e.getMessage());
             try { getResponse().setEntity(new ErrorXMLRepresentation(e.getMessage())); }
             catch (IOException ioe) {}
             getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

}
