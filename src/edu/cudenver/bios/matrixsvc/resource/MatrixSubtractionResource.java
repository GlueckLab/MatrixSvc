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

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.matrixsvc.application.MatrixConstants;
import edu.cudenver.bios.matrixsvc.application.MatrixLogger;
import edu.cudenver.bios.matrixsvc.application.NamedRealMatrix;
import edu.cudenver.bios.matrixsvc.representation.MatrixXmlRepresentation;

/**
 * Resource for handling requests for Matrix Subtraction calculations.
 * See the MatrixApplication class for URI mappings
 * 
 * @author Jonathan Cohen
 */
public class MatrixSubtractionResource extends ServerResource
{
	private static Logger logger = MatrixLogger.getInstance();

    /**
     * Handle POST request to subtract a list of matrices
     * @param entity XMl formatted matrix list
     * @return XML representation of the difference
     * @throws ResourceException
     */
	@Post
    public Representation matrixSubstraction(Representation entity) throws ResourceException
    {
		if(entity == null)
		{
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "No Input- Matrix Subtraction not possible");
		}
    	DomRepresentation domRep = new DomRepresentation(entity);
        NamedRealMatrix differenceMatrix = null;
        NamedRealMatrix nextMatrix = null;
        ArrayList<NamedRealMatrix> matrixList = null;
        
        try
        {
        	// parse the parameters from the entity body
        	matrixList = MatrixParamParser.
              getAdditionParamsFromDomNode( domRep.getDocument().getDocumentElement() );
            
            if(matrixList == null || matrixList.size() < 2){
            	String msg = "Matrix list must contain at least 2 matrices for subtraction.";
            	logger.info(msg);
             	throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
            }
            
            //add the matrices together
            differenceMatrix = matrixList.get(0);
            for(int i = 1; i < matrixList.size(); i++){
            	nextMatrix = matrixList.get(i);
            	differenceMatrix = new NamedRealMatrix( differenceMatrix.subtract(nextMatrix) );
            }
            
            //add the name to the matrix we're returning
            differenceMatrix.setName(MatrixConstants.SUBTRACTION_MATRIX_RETURN_NAME);
            
            //create our response representation
            MatrixXmlRepresentation response = new MatrixXmlRepresentation(differenceMatrix);
            /*getResponse().setEntity(response); 
            getResponse().setStatus(Status.SUCCESS_CREATED);*/
            return response;
        }
        catch (IllegalArgumentException iae)
        {
        	 MatrixLogger.getInstance().error(iae.getMessage());
        	 throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, iae.getMessage());

        }
        catch (IOException ioe)
        {
        	 MatrixLogger.getInstance().error(ioe.getMessage());
        	 throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, ioe.getMessage());
             
        }
    }

}
