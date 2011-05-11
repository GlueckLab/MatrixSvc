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
import edu.cudenver.bios.matrixsvc.representation.SingleValueRepresentation;

import org.apache.commons.math.linear.EigenDecompositionImpl;
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

/**
 * Resource for handling requests for Matrix Addition calculations.
 * See the MatrixApplication class for URI mappings
 * 
 * @author Jonathan Cohen
 */
public class MatrixPositiveDefiniteResource extends Resource
{
	/**
	 * Create a new resource to handle power requests.  Data
	 * is returned as XML.
	 * 
	 * @param context restlet context
	 * @param request http request object
	 * @param response http response object
	 */
    public MatrixPositiveDefiniteResource(Context context, Request request, Response response) 
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
     * Process a POST request to perform a set of power
     * calculations.  Please see REST API documentation for details on
     * the entity body format.
     * 
     * @param entity HTTP entity body for the request
     */
    @Override 
    public void acceptRepresentation(Representation entity)
    {
        DomRepresentation rep = new DomRepresentation(entity);
        NamedRealMatrix reqMatrix = null;
        try
        {
        	// parse the parameters from the entity body
            MatrixServiceParameters params = MatrixParamParser.
              getPositiveDefiniteParamsFromDomNode( rep.getDocument().getDocumentElement() );

            //get our matrix input
            reqMatrix = params.getMatrixListFromRequest().get(0);
            

            //perform operation
            double[] eigenValues = new EigenDecompositionImpl( reqMatrix, 
            		params.getEigenTolerance() ).getRealEigenvalues();

            // if all eigenValues are positive, we return true
            boolean isPositiveDefinite = true;
            for( int i = 0; i < eigenValues.length; i++){
            	if( eigenValues[i] <= 0){
            		isPositiveDefinite = false;
            		break;
            	}
            }
            
            // set value in our parameters object
            params.setPositiveDefinite(isPositiveDefinite);
            
            //create our response representation
            SingleValueRepresentation response = new SingleValueRepresentation(
            		params,
            		MatrixConstants.SINGLE_VALUE_POSITIVE_DEFINITE);
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
