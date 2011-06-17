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
	 * Create a new resource to handle addition requests.  Data
	 * returned as XML.
	 * 
	 * @param context restlet context
	 * @param request restlet request object
	 * @param response restlet response object
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
     * This method expects a <matrixList> which contains 2..n matrices
     * to add.
     * @param entity HTTP entity body for the request
     */
    @Override 
    public void acceptRepresentation(Representation entity)
    throws ResourceException
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
            
            // get the list of matrices from the request
            matrixList = params.getMatrixListFromRequest();
            if(matrixList == null || matrixList.size() < 2){
            	String msg = "Matrix list must contain at least 2 matrices for addition.";
            	logger.info(msg);
             	throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
            }
            
            //add the matrices together
            matrixA = matrixList.get(0);
            for(int i = 1; i < matrixList.size(); i++){
            	matrixB = matrixList.get(i);
            	matrixA = new NamedRealMatrix( matrixA.add(matrixB) );
            }
            
            //add the name to the summation matrix we're returning
            matrixA.setName(MatrixConstants.ADDITION_MATRIX_RETURN_NAME);
            
            //create a list and add the matrix to it
            ArrayList<NamedRealMatrix> responseList = new ArrayList<NamedRealMatrix>();
            responseList.add(matrixA);
            
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
