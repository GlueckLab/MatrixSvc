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
package edu.cudenver.bios.matrixsvc.resource.test;

import java.io.IOException;
import java.util.List;

import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.DomRepresentation;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;
import org.restlet.resource.XmlRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.cudenver.bios.distribution.NonCentralFDistribution;
import edu.cudenver.bios.power.GLMMPowerCalculator;
import edu.cudenver.bios.power.Power;
import edu.cudenver.bios.power.parameters.GLMMPowerParameters;
import edu.cudenver.bios.powersvc.application.PowerConstants;
import edu.cudenver.bios.powersvc.application.PowerLogger;
import edu.cudenver.bios.powersvc.representation.ErrorXMLRepresentation;
import edu.cudenver.bios.powersvc.representation.GLMMPowerListXMLRepresentation;

/**
 * Resource exposing some unit test functionality.
 * See the PowerApplication class for URI mappings
 * 
 * @author Sarah Kreidler
 */
public class FTestResource extends Resource
{
    // query param specifying the type of test
    private static final String REQUEST_TEST = "test";
    // query param specifying numerator degrees of freedom
    // for distribution tests
    private static final String REQUEST_NDF = "ndf"; 
    // query param specifying denominator degrees of freedom
    // for distribution tests
    private static final String REQUEST_DDF = "ddf"; 
    // query param specifying the desired quantile for distribution tests
    private static final String REQUEST_QUANTILE = "q"; 
    // query param specifying the critical value for distribution tests
    private static final String REQUEST_CRITICAL_VALUE = "crit"; 
    // query param specifying the non-centrality for distribution tests
    private static final String REQUEST_NONCENTRALITY = "nc";
    // test names
    private static final String TEST_FDIST = "f";
    private static final String TEST_NONCENTRALITY = "noncentrality";
    
    // xml tag for result
    private static final String TAG_RESULT = "testResult";
    /**
     * Create a new resource to handle unit test requests.  
     * Data is returned as XML.
     * 
     * @param context restlet context
     * @param request http request object
     * @param response http response object
     */
    public FTestResource(Context context, Request request, Response response) 
    {
        super(context, request, response);
        getVariants().add(new Variant(MediaType.APPLICATION_XML));
    }

    /**
     * Allow GET requests
     */
    @Override
    public boolean allowGet()
    {
        return true;
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
     * Disallow POST requests
     */
    @Override
    public boolean allowPost() 
    {
        return  false;
    }

    /**
     * Process the unit test request for a given variant.
     */
    @Override
    public Representation represent(Variant variant) 
    {
        DomRepresentation result = null;
        try
        {
            // parse the query parameters
            String ndfStr = getQuery().getFirstValue(REQUEST_NDF);
            String ddfStr = getQuery().getFirstValue(REQUEST_DDF);
            String quantileStr = getQuery().getFirstValue(REQUEST_QUANTILE);
            String criticalStr = getQuery().getFirstValue(REQUEST_CRITICAL_VALUE);
            String noncentralStr = getQuery().getFirstValue(REQUEST_NONCENTRALITY);
            if (ndfStr == null || ddfStr == null || 
                    (quantileStr == null && criticalStr == null))
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                "must specify ndf, ddf, and one of q or crit as query parameters");
            
            double ndf = Double.parseDouble(ndfStr);
            double ddf = Double.parseDouble(ddfStr);
            double quantile = Double.NaN;
            if (quantileStr != null) quantile = Double.parseDouble(quantileStr); 
            double critical = Double.NaN;
            if (criticalStr != null) critical = Double.parseDouble(criticalStr); 
            double noncentral = 0;
            if (noncentralStr != null) noncentral = Double.parseDouble(noncentralStr);

            double value = Double.NaN;
            NonCentralFDistribution fdist = new NonCentralFDistribution(ndf, ddf, noncentral);
            if (!Double.isNaN(critical))
            {
                value = fdist.cdf(critical);
            }
            else if (!Double.isNaN(quantile))
            {
                value = fdist.inverseCDF(quantile);
            }
            
            result = new DomRepresentation(MediaType.TEXT_XML);
            Document doc = result.getDocument();
            Element errorElem = doc.createElement(TAG_RESULT);
            errorElem.appendChild(doc.createTextNode(Double.toString(value)));
            doc.appendChild(errorElem);
            doc.normalizeDocument();
        }
        catch (IOException ioe)
        {
            MatrixLogger.getInstance().error(ioe.getMessage());
            try { result = new ErrorXMLRepresentation(ioe.getMessage());
            } catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        catch (NumberFormatException nfe)
        {
            MatrixLogger.getInstance().error(nfe.getMessage());
            try { result = new ErrorXMLRepresentation(nfe.getMessage());
            } catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        catch (IllegalArgumentException iae)
        {
            MatrixLogger.getInstance().error(iae.getMessage());
            try { result = new ErrorXMLRepresentation(iae.getMessage());
            } catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        catch (ResourceException re)
        {
            MatrixLogger.getInstance().error(re.getMessage());
            try { result = new ErrorXMLRepresentation(re.getMessage());
            } catch (IOException e) {}
            getResponse().setStatus(re.getStatus());
        }

        return result;
    }
}
