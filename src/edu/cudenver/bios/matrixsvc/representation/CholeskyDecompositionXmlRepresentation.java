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
import edu.cudenver.bios.matrixsvc.application.NamedRealMatrix;

import org.apache.commons.math.linear.CholeskyDecompositionImpl;
import org.apache.log4j.Logger;
import org.restlet.data.MediaType;

import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.IOException;

/**
 * @author Jonathan Cohen
 *
 */
public class CholeskyDecompositionXmlRepresentation extends DomRepresentation {
	private static Logger logger = MatrixLogger.getInstance();
	
	/**
	 * Create an XML representation for a Cholesky Decomposition XML response
	 * entity body. 
	 * 
	 * @param cdImpl is a @see CholeskyDecompositionImpl.
	 * @throws IOException
	 */
	public CholeskyDecompositionXmlRepresentation(CholeskyDecompositionImpl cdImpl) 
	throws IOException
	{
	    super(MediaType.APPLICATION_XML);
	    logger.debug("In CholeskyDecompositionXmlRepresentation constructor");
	    Document doc = getDocument();
	    Element rootElement = createXml(cdImpl, doc);
	    doc.appendChild(rootElement);
	    doc.normalizeDocument();
	}

	static Element createXml(CholeskyDecompositionImpl _cdImpl, Document _doc){
		NamedRealMatrix L = new NamedRealMatrix( _cdImpl.getL() );
		NamedRealMatrix LT = new NamedRealMatrix( _cdImpl.getLT() );
		L.setName(MatrixConstants.SQ_ROOT_MATRIX_RETURN_NAME);
        LT.setName(MatrixConstants.TRANSPOSE_MATRIX_RETURN_NAME);
		Element rootElem = _doc.createElement(MatrixConstants.TAG_CHOLESKY_DECOMP);
		
		//Get XML for first matrix
		logger.debug("Calling MatrixXmlRepresentation.createMatrixNodeFromRealMatrix() " +
				"for L matrix.");
		Node matrixL = MatrixXmlRepresentation.createMatrixNodeFromRealMatrix(L, _doc);
		
		//Get XML for second matrix
		logger.debug("Calling MatrixXmlRepresentation.createMatrixNodeFromRealMatrix() " +
		"for LT matrix.");
		Node matrixLT = MatrixXmlRepresentation.createMatrixNodeFromRealMatrix(LT, _doc);

		//Add our matrices to our root element
		rootElem.appendChild(matrixL);
		rootElem.appendChild(matrixLT);
		
		return rootElem;
	}

}
