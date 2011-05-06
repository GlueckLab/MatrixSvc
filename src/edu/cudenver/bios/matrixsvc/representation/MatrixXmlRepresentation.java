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
import edu.cudenver.bios.matrixsvc.application.NamedRealMatrix;

import org.apache.log4j.Logger;
import org.restlet.data.MediaType;
import org.restlet.resource.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.ArrayList;

/**
 * XML representation of a Matrix.  
 * 
 * @author Jonathan Cohen
 */
public class MatrixXmlRepresentation extends DomRepresentation
{
	private static Logger logger = MatrixLogger.getInstance();
    /**
     * Create an XML representation of a matrix response
     * entity body.  
     * 
     * @param params is @see MatrixServiceParameters.  
     * @throws IOException
     */
    public MatrixXmlRepresentation(MatrixServiceParameters params) throws IOException
    {
        super(MediaType.APPLICATION_XML);
        Document doc = getDocument();
        
        Element rootElement = createMatrixXml(params.getMatrixListForResponse(), doc);
        doc.appendChild(rootElement);
        doc.normalizeDocument();
    }
    
    /**
     * This method will extract each matrix found in the param matrixList
     * and create an XML element for it.  It then puts the elements together in the
     * param doc.
     * @param matrixList is a list of NamedRealMatrix objects which should be 1...n in
     * length.
     * @param doc is the Document object to fill
     */
    static Element createMatrixXml(ArrayList<NamedRealMatrix> matrixList, Document doc){
    	// get the count of matrices in the list
    	int numMatrices = matrixList.size();
    	logger.debug("In createMatrixXML()");
    	// if the count is greater than 1, add a <matrixList> element
    	// as the root.  Otherwise, create the root as <matrix>
    	Element rootElem = null;
    	if( numMatrices > 1){
    		// create our root element
    		logger.debug("Creating root element "+MatrixConstants.TAG_MATRIX_LIST);
        	rootElem = doc.createElement(MatrixConstants.TAG_MATRIX_LIST);
        }
    	else if( numMatrices == 1){
    		// create our root element
    		logger.debug("Creating root element "+MatrixConstants.TAG_MATRIX);
        	rootElem = doc.createElement(MatrixConstants.TAG_MATRIX);
    	}
    	else{
    		//Houston, we have a problem
    		String msg = "No matrices in createMatrixXml()! " +
			"Can't create the response XML.";
    		logger.error(msg);
    		throw new IllegalArgumentException(msg);
    	}
    	
    	//iterate through the list, and create XML of each matrix
    	for(NamedRealMatrix matrix : matrixList){
	    	if( rootElem.getNodeName().equals(MatrixConstants.TAG_MATRIX_LIST)){
	    		//create a <matrix> node as a child
	    		logger.debug("Creating a <matrix> element...");
	    		Element matrixNode = doc.createElement(MatrixConstants.TAG_MATRIX);
	    		
	    		//fill it with the matrix
	    		createMatrixNodeFromRealMatrix(matrixNode, matrix, doc);
	    		
	    		//append our matrix node to the rootElem
	    		rootElem.appendChild(matrixNode);
	    	}
	    	else{
	    		//we have a matrix element already so just fill it
	    		createMatrixNodeFromRealMatrix(rootElem, matrix, doc);
	    	}
    	}
    	return rootElem;
    }

    static void createMatrixNodeFromRealMatrix(Element theNode, 
    										   NamedRealMatrix matrix,
    										   Document doc){
    	//set the 'name' attribute to the correct name...
		theNode.setAttribute("name", matrix.getName() );
    	// set the 'rows' attribute
		theNode.setAttribute("rows", Integer.toString( matrix.getRowDimension() ) );
    	// set the 'columns' attribute
		theNode.setAttribute("columns", Integer.toString( matrix.getColumnDimension() ) );
    	
		// loop through the rows in the matrix
    	for(int rowNum = 0; rowNum < matrix.getRowDimension(); rowNum++){
    		// create row tag
    		Element rowNode = doc.createElement(MatrixConstants.TAG_ROW);
    		theNode.appendChild(rowNode);
    		
    		// loop through the columns
    	    for(int colNum = 0; colNum < matrix.getColumnDimension(); colNum++){
    	    	//extract the elements using the row index and column index
    	    	//and put them in column tags
    	    	Element colNode = doc.createElement(MatrixConstants.TAG_COLUMN);
    	    	logger.debug("matrix.getEntry()=" +Double.toString( matrix.getEntry(rowNum, colNum)));
    	    	colNode.setTextContent(Double.toString( matrix.getEntry(rowNum, colNum)));
    	    	rowNode.appendChild(colNode);
    	    }
    	}
    }
}
