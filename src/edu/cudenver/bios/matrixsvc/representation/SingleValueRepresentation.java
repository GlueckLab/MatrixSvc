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
package edu.cudenver.bios.matrixsvc.representation;

import edu.cudenver.bios.matrixsvc.application.MatrixConstants;
import edu.cudenver.bios.matrixsvc.application.MatrixLogger;
import edu.cudenver.bios.matrixsvc.application.MatrixServiceParameters;

import org.apache.log4j.Logger;
import org.restlet.data.MediaType;
import org.restlet.resource.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;

/**
 * XML representation of a single, non-matrix node (trace, rank, positive definite,
 * scalar multiplier.)  
 *
 * @author Jonathan Cohen
 */
public class SingleValueRepresentation extends DomRepresentation
{
	private static Logger logger = MatrixLogger.getInstance();
	/**
	 * Create an XML representation of a single value element XML response
	 * entity body.  
	 * 
	 * @param params is @see MatrixServiceParameters
	 * @param operationName use MatrixConstants SINGLE_VALUE_{operation name}
	 * @throws IOException
	 */
	public SingleValueRepresentation(MatrixServiceParameters params, int operationName) 
	throws IOException
	{
	    super(MediaType.APPLICATION_XML);
	    Document doc = getDocument();
	    logger.debug("In SingleValueRepresentation constructor");
	    Element rootElement = createXml(params, doc, operationName);
	    doc.appendChild(rootElement);
	    doc.normalizeDocument();
	}
	
	static Element createXml(MatrixServiceParameters ourParams, Document doc, int opName){
		Element rootElem = null;
		
		switch(opName){
		case MatrixConstants.SINGLE_VALUE_POSITIVE_DEFINITE:
			rootElem = doc.createElement(MatrixConstants.TAG_POSITIVE_DEFINITE);
			rootElem.setNodeValue(ourParams.isPositiveDefinite().toString());
			break;
		
		case MatrixConstants.SINGLE_VALUE_RANK:
			rootElem = doc.createElement(MatrixConstants.TAG_RANK);
			rootElem.setNodeValue(new Double(ourParams.getRank() ).toString());
			break;
		
		case MatrixConstants.SINGLE_VALUE_TRACE:
			rootElem = doc.createElement(MatrixConstants.TAG_TRACE);
			rootElem.setNodeValue(new Double(ourParams.getTrace()).toString());
			break;
		
		case MatrixConstants.SINGLE_VALUE_SCALAR_MULTIPLIER:
			rootElem = doc.createElement(MatrixConstants.TAG_SCALAR_MULTIPLIER);
			rootElem.setNodeValue(new Double(ourParams.getScalarMultiplier()).toString());
			break;
		}
		return rootElem;
	}
}
