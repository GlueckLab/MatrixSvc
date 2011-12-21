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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import edu.cudenver.bios.matrix.OrthogonalPolynomialContrastCollection;
import edu.cudenver.bios.matrix.OrthogonalPolynomials;
import edu.cudenver.bios.matrixsvc.application.MatrixConstants;
import edu.cudenver.bios.matrixsvc.application.MatrixLogger;
import edu.cudenver.bios.matrixsvc.representation.OrthogonalPolynomialContrastXmlRepresentation;
import edu.cudenver.bios.utils.Factor;

/**
 * @author Jonathan Cohen
 *
 */
public class MatrixContrastResource extends ServerResource
{
	private static Logger logger = MatrixLogger.getInstance();

    /**
     * Create a polynomial contrast
     * Please see REST API documentation for details on
     * the entity body format.
     * @param entity HTTP entity body for the request
     */
	@Post
    public Representation acceptRepresentation(Representation entity)
    throws ResourceException
    {  
		if(entity  == null)
		{
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "No Inputs Failed to Creat Contrast Matrix");
		}
        DomRepresentation domRep = new DomRepresentation(entity);
        ArrayList<Factor> factors = null;
        
        try
        {
        	//Figure out the "type" of the Factor objects (between/within)
        	Node root = domRep.getDocument().getDocumentElement();
        	NamedNodeMap map = root.getAttributes();
            Node typeNode = map.getNamedItem(MatrixConstants.ATTR_TYPE);
            String type = "";
            
        	if( typeNode != null ){
        		type = typeNode.getNodeValue().trim();
        	}
        	else{
        		String msg = "Cannot find attribute '" + MatrixConstants.ATTR_TYPE +
    			"' in this factor list.";
        		logger.info(msg);
        		throw new IllegalArgumentException(msg);
        	}
        	logger.debug("in MatrixContrastResource, type="+type);
        	
        	// parse the parameters from the entity body
            factors = MatrixParamParser.getContrastParamsFromDomNode( root );
            
            // our return object
            OrthogonalPolynomialContrastCollection opCollection = null;
            
            //Operation
            if( type.equals(MatrixConstants.BETWEEN))
            {
            	opCollection =
            		OrthogonalPolynomials.betweenSubjectContrast(factors);
            }
            else if( type.equals(MatrixConstants.WITHIN))
            {
            	opCollection =
            		OrthogonalPolynomials.withinSubjectContrast(factors);
            }
            else
            {
            	String msg = "Attribute 'type' must be either 'between' or " +
            			"'within'.";
        		logger.info(msg);
        		throw new IllegalArgumentException(msg);
            }
            
            //create our response representation
            OrthogonalPolynomialContrastXmlRepresentation response = 
            	new OrthogonalPolynomialContrastXmlRepresentation( opCollection );
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
		//return domRep;
    }
}
