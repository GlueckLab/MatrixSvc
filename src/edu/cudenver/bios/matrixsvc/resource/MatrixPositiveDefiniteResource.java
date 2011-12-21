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

import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.matrix.MatrixUtils;
import edu.cudenver.bios.matrixsvc.application.MatrixConstants;
import edu.cudenver.bios.matrixsvc.application.MatrixLogger;
import edu.cudenver.bios.matrixsvc.application.NamedRealMatrix;
import edu.cudenver.bios.matrixsvc.representation.PositiveDefiniteXmlRepresentation;

/**
 * Resource for handling requests for Matrix Addition calculations.
 * See the MatrixApplication class for URI mappings
 * 
 * @author Jonathan Cohen
 */
public class MatrixPositiveDefiniteResource extends ServerResource
{
    /**
     * Handle POST request to determine if the matrix is positive definite
     * @param entity XMl formatted matrix
     * @return XML representation indicating if the matrix is positive definite
     * @throws ResourceException
     */
	@Post
    public Representation matrixPositiveDefinite(Representation entity) throws ResourceException
    {
		if(entity == null)
		{
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "No Input");
		}
        DomRepresentation rep = new DomRepresentation(entity);
        NamedRealMatrix reqMatrix = null;
        try
        {
        	// parse the parameters from the entity body
        	reqMatrix = MatrixParamParser.
              getPositiveDefiniteParamsFromDomNode( rep.getDocument().getDocumentElement() );

            //perform operation
            boolean isPositiveDefinite = MatrixUtils.isPositiveDefinite(reqMatrix,
            		MatrixConstants.EIGEN_TOLERANCE_DEFAULT);
            
            //create our response representation
            PositiveDefiniteXmlRepresentation response = new PositiveDefiniteXmlRepresentation(
            		isPositiveDefinite);
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
