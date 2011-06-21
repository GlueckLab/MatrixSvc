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
import org.restlet.resource.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;

/**
 * @author Jonathan Cohen
 *
 */
public class TraceXmlRepresentation extends DomRepresentation {
	private static Logger logger = MatrixLogger.getInstance();
	
	/**
	 * Create an XML representation of a single value element XML response
	 * entity body.  This class handles the representation, in XML, of the results
	 * of a trace calculation.
	 * 
	 * @param trace is a double.
	 * @throws IOException
	 */
	public TraceXmlRepresentation(double trace) 
	throws IOException
	{
	    super(MediaType.APPLICATION_XML);
	    logger.debug("In TraceXmlRepresentation constructor");
	    Document doc = getDocument();
	    Element rootElement = createXml(trace, doc);
	    doc.appendChild(rootElement);
	    doc.normalizeDocument();
	}

	static Element createXml(double _trace, Document _doc){
		Element rootElem = _doc.createElement(MatrixConstants.TAG_TRACE);
		logger.debug("In createXML(), Trace="+_trace);
		rootElem.setTextContent(new Double(_trace).toString());
		return rootElem;
	}
}
