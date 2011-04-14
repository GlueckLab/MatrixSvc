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

import edu.cudenver.bios.matrixsvc.application.MatrixLogger;
import edu.cudenver.bios.matrixsvc.application.MatrixServiceParameters;
import edu.cudenver.bios.matrixsvc.representation.ErrorXMLRepresentation;
import edu.cudenver.bios.power.GLMMPowerCalculator;
import edu.cudenver.bios.power.Power;
import edu.cudenver.bios.power.parameters.GLMMPowerParameters;

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
import java.util.List;

/**
 * Resource for handling requests for Matrix Addition calculations.
 * See the MatrixApplication class for URI mappings
 * 
 * @author Jonathan Cohen
 */
public class MatrixAdditionResource extends Resource
{
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

        try
        {
        	/**
        	 * 1.2.	XML Request:
<matrixList>
<matrix name='A' rows='r' columns='c'>
	    <r><c>a1,1</c><c>a1,2</c>...<c>a1,c</c></r>
...
<r><c>ar,1</c><c>ar,2</c>...<c>ar,c</c></r>
</matrix>
<matrix name='B' rows='r' columns='c'>
<r><c>b1,1</c><c>b1,2</c>...<c>b1,c</c></r>
...
<r><c>br,1</c><c>br,2</c>...<c>br,c</c></r>
</matrix>
</matrixList>

1.3.	XML Response:
<matrix name=’sum’ rows='r' columns='c'>
	<r><c>s1,1</c><c>s1,2</c>...<c>s1,c</c></r>
	...
	<r><c>sr,1</c><c>sr,2</c>...<c>sr,c</c></r>
</matrix>
        	 */
            // parse the parameters from the entity body
            MatrixServiceParameters params = MatrixParamParser.
              getMatrixParametersFromDomNode(rep.getDocument().getDocumentElement());

            // create the appropriate power calculator for this model
//            GLMMPowerCalculator calculator = new GLMMPowerCalculator();
            // calculate the detecable difference results
//            List<Power> results = calculator.getPower(params);
           
            // build the response xml
//            GLMMPowerListXMLRepresentation response = new GLMMPowerListXMLRepresentation(results);
//            getResponse().setEntity(response); 
//            getResponse().setStatus(Status.SUCCESS_CREATED);
        }
        catch (IOException ioe)
        {
            MatrixLogger.getInstance().error(ioe.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(ioe.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        catch (IllegalArgumentException iae)
        {
            MatrixLogger.getInstance().error(iae.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(iae.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        catch (ResourceException re)
        {
            MatrixLogger.getInstance().error(re.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(re.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(re.getStatus());
        }
    }

}
