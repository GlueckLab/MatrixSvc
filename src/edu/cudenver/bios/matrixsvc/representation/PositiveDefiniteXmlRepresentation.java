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

import org.apache.log4j.Logger;
import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;

/**
 * @author Jonathan Cohen
 *
 */
public class PositiveDefiniteXmlRepresentation  extends DomRepresentation
{
	private static Logger logger = MatrixLogger.getInstance();
	
	/**
	 * Create an XML representation of a single value element XML response
	 * entity body.  This class handles the representation of the results
	 * of a positive definite calculation in XML.
	 * 
	 * @param isPositiveDefinite is a boolean - true, if the matrix was determined
	 * to be positive definite.
	 * @throws IOException
	 */
	public PositiveDefiniteXmlRepresentation(boolean isPositiveDefinite) 
	throws IOException
	{
	    super(MediaType.APPLICATION_XML);
	    logger.debug("In PositiveDefiniteXmlRepresentation constructor");
	    Document doc = getDocument();
	    Element rootElement = createXml(isPositiveDefinite, doc);
	    doc.appendChild(rootElement);
	    doc.normalizeDocument();
	}

	static Element createXml(boolean _isPositiveDefinite, Document _doc){
		Element rootElem = _doc.createElement(MatrixConstants.TAG_POSITIVE_DEFINITE);
		rootElem.setTextContent( new Boolean( _isPositiveDefinite ).toString() );
		logger.debug("In createXML(), isPositiveDefinite="+rootElem.getTextContent());
		return rootElem;
	}
}
