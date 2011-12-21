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

import org.apache.commons.math.linear.CholeskyDecompositionImpl;
import org.apache.commons.math.linear.NonSquareMatrixException;
import org.apache.commons.math.linear.NotPositiveDefiniteMatrixException;
import org.apache.commons.math.linear.NotSymmetricMatrixException;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.matrixsvc.application.MatrixLogger;
import edu.cudenver.bios.matrixsvc.application.NamedRealMatrix;
import edu.cudenver.bios.matrixsvc.representation.CholeskyDecompositionXmlRepresentation;

/**
 * Resource for handling requests for Cholesky Decomposition calculations.
 * See the MatrixApplication class for URI mappings
 * 
 * @author Jonathan Cohen
 */
public class MatrixDecompositionCholeskyResource extends ServerResource
{
       /**
     * This operation takes a single square matrix (p x p) 
     * and produces two matrices: the matrix representing its 
     * square root (L), and its transpose.  Please see 
     * Matrix Services REST API documentation for details on
     * the entity body format.
     * 
     * @param entity HTTP entity body for the request
     * @throws NotPositiveDefiniteMatrixException 
     * @throws NotSymmetricMatrixException 
     * @throws NonSquareMatrixException 
     */
    @Post
    public Representation acceptRepresentation(Representation entity) 
    		throws ResourceException, NonSquareMatrixException, 
    		NotSymmetricMatrixException, NotPositiveDefiniteMatrixException
    {
    	if(entity == null)
    	{
    		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "No Input- Matrix Decomposition not possible");
    	}
        DomRepresentation rep = new DomRepresentation(entity);
        NamedRealMatrix matrixInput = null;
        
        try
        {
        	// parse the parameters from the entity body
            matrixInput = MatrixParamParser.
              getDecompCholeskyParamsFromDomNode( rep.getDocument().getDocumentElement() );

           if(matrixInput == null ||
               !matrixInput.getName().equalsIgnoreCase("A")){
            	throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
            			"Couldn't retrieve the matrix for Cholesky Decomposition."); 
            }
            
            // perform Cholesky Decomposition
            CholeskyDecompositionImpl cdImpl = 
            	new CholeskyDecompositionImpl(matrixInput);
    		
            //create our response representation
            CholeskyDecompositionXmlRepresentation response = 
            	new CholeskyDecompositionXmlRepresentation(cdImpl);
           /* getResponse().setEntity(response); 
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
