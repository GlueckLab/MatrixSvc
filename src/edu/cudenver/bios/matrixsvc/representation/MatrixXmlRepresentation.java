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

import java.io.IOException;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.cudenver.bios.matrixsvc.application.MatrixConstants;
import edu.cudenver.bios.matrixsvc.application.NamedRealMatrix;

/**
 * XML representation of a Matrix.  
 * 
 * @author Jonathan Cohen
 */
public class MatrixXmlRepresentation extends DomRepresentation
{
    /**
     * Create an XML representation of a matrix response
     * entity body.  
     * 
     * @param params is @see NamedRealMatrix, a wrapper for a RealMatrix
     * that allows us to name the matrix element.  
     * @throws IOException
     */
    public MatrixXmlRepresentation(NamedRealMatrix matrix) throws IOException
    {
        super(MediaType.APPLICATION_XML);
        Document doc = getDocument();
        
        Element rootElement = createMatrixNodeFromRealMatrix(matrix, doc);
        doc.appendChild(rootElement);
        doc.normalizeDocument();
    }
    
    public static Element createMatrixNodeFromRealMatrix(NamedRealMatrix matrix,
    										             Document doc){
    	Element matrixElement = doc.createElement(MatrixConstants.TAG_MATRIX);
    	
    	//set the 'name' attribute to the correct name...
    	matrixElement.setAttribute("name", matrix.getName() );
    	
    	// set the 'rows' attribute
    	matrixElement.setAttribute("rows", Integer.toString( matrix.getRowDimension() ) );
    	
    	// set the 'columns' attribute
    	matrixElement.setAttribute("columns", Integer.toString( matrix.getColumnDimension() ) );
    	
		// loop through the rows in the matrix
    	for(int rowNum = 0; rowNum < matrix.getRowDimension(); rowNum++){
    		// create row tag
    		Element rowNode = doc.createElement(MatrixConstants.TAG_ROW);
    		matrixElement.appendChild(rowNode);
    		
    		// loop through the columns
    	    for(int colNum = 0; colNum < matrix.getColumnDimension(); colNum++){
    	    	//extract the elements using the row index and column index
    	    	//and put them in column tags
    	    	Element colNode = doc.createElement(MatrixConstants.TAG_COLUMN);
    	    	colNode.setTextContent(Double.toString( matrix.getEntry(rowNum, colNum)));
    	    	rowNode.appendChild(colNode);
    	    }
    	}
    	return matrixElement;
    }
}
