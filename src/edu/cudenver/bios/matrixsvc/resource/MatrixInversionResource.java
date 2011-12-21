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

import org.apache.commons.math.linear.LUDecompositionImpl;
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
 * Resource for handling requests for Matrix Addition calculations.
 * See the MatrixApplication class for URI mappings
 * 
 * @author Jonathan Cohen
 */
public class MatrixInversionResource extends ServerResource
{

    /**
     * Handle POST request to invert a matrix
     * @param entity XMl formatted matrix 
     * @return XML representation of the matrix inverse
     * @throws ResourceException
     */
	@Post
    public Representation matrixInversion(Representation entity) throws ResourceException
    {
		if(entity == null)
		{
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "No Input- Matrix Inversion not possible");
		}
    	DomRepresentation rep = new DomRepresentation(entity);
        NamedRealMatrix matrixInput = null;
        NamedRealMatrix inverseMatrix = null;
        try
        {
        	// parse the parameters from the entity body
        	matrixInput = MatrixParamParser.getMatrixInversionParamsFromDomNode(
        			rep.getDocument().getDocumentElement() );

            if(matrixInput == null ||
               !matrixInput.getName().equalsIgnoreCase("A")){
            	throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
            			"Couldn't retrieve the matrix for inversion."); 
            }
            
            // perform inversion
            inverseMatrix = new NamedRealMatrix( 
            		        new LUDecompositionImpl(matrixInput)
            		        .getSolver().getInverse() );
            
            // set name
            inverseMatrix.setName(MatrixConstants.INVERSION_MATRIX_RETURN_NAME);
            
            //create our response representation
            MatrixXmlRepresentation response = new MatrixXmlRepresentation(inverseMatrix);
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
