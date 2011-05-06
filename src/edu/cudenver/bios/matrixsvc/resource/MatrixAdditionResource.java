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

import org.apache.log4j.Logger;
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
 * Resource for handling requests for Matrix Addition calculations.
 * See the MatrixApplication class for URI mappings
 * 
 * @author Jonathan Cohen
 */
public class MatrixAdditionResource extends Resource
{
	private static Logger logger = MatrixLogger.getInstance();
	/**
	 * Create a new resource to handle power requests.  Data
	 * is returned as XML.
	 * 
	 * @param context restlet context
	 * @param request http request object
	 * @param response http response object
	 */
    public MatrixAdditionResource(Context context, Request request, Response response) 
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
     * 
     * @param entity HTTP entity body for the request
     */
    @Override 
    public void acceptRepresentation(Representation entity)
    {
        DomRepresentation domRep = new DomRepresentation(entity);
        NamedRealMatrix matrixA = null;
        NamedRealMatrix matrixB = null;
        ArrayList<NamedRealMatrix> matrixList = null;
        
        try
        {
        	// parse the parameters from the entity body
            MatrixServiceParameters params = MatrixParamParser.
              getAdditionParamsFromDomNode( domRep.getDocument().getDocumentElement() );
            
            // get the list of matrices for the response
            matrixList = params.getMatrixListFromRequest();
             
            // get the 2 matrices to add from the list
            matrixA = matrixList.get(0);
            matrixB = matrixList.get(1);
            if(matrixA == null || matrixB == null ||
                    !matrixA.getName().equalsIgnoreCase("A")	||
                    !matrixB.getName().equalsIgnoreCase("B")){
            		logger.error("Couldn't retrieve the matrices for addition.");
                 	throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                 			"Couldn't retrieve the matrices for addition."); 
            }
            
            //add the matrices together
            NamedRealMatrix retMatrix = matrixA.add(matrixB);
            
            //add the name to the summation matrix we're returning
            retMatrix.setName(MatrixConstants.ADDITION_MATRIX_RETURN_NAME);
            
            //create a list and add the matrix to it
            ArrayList<NamedRealMatrix> responseList = new ArrayList<NamedRealMatrix>();
            responseList.add(retMatrix);
            
            //add the list to our MatrixServiceParameters object
            params.setMatrixListForResponse(responseList);
            
            //create our response representation
            MatrixXmlRepresentation response = new MatrixXmlRepresentation(params);
            getResponse().setEntity(response); 
            getResponse().setStatus(Status.SUCCESS_CREATED);
        }
        catch (ResourceException re)
        {
            logger.error(re.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(re.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(re.getStatus());
        }
        catch (Exception e)
        {
        	 logger.error(e.getMessage());
             try { getResponse().setEntity(new ErrorXMLRepresentation(e.getMessage())); }
             catch (IOException ioe) {}
             getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

}
