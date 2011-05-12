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
package edu.cudenver.bios.matrixsvc.test;

import edu.cudenver.bios.matrixsvc.application.MatrixServiceParameters;
import edu.cudenver.bios.matrixsvc.resource.MatrixParamParser;

import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

/**
 * Unit test for specific services (matrix operations).  These tests
 * will confirm whether or not the code checks for data requirements
 * for individual operations such as: the input matrix should be square; the
 * two input matrices must have the same row dimensions; 
 * the columns of A must match the rows of B  
 * @author Jonathan Cohen
 */
public class ResourceTestCase extends TestCase
{
	static DocumentBuilder builder = null;
	static{
	    DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
        try {
			 builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	/*Document objects to pass to the parser*/
	
	//Test Case 1: non square matrix input to operation that requires a square matrix 
	static Document nonSquareMatrixDoc = null;
	
	//Test Case 2: Column dimension of A != row dimension of B
	static Document invalidRowColMatrixDoc = null;
	
	//Test Case 3: Row dimension of A != row dimension of B
	static Document invalidRowMatrixDoc = null;
    
    
    /*StringBuffers to hold the XML for each request*/
    
    //Valid XML for an entity body with a matrixList element
    static StringBuffer nonSquareMatrix = new StringBuffer();
    static StringBuffer invalidRowColMatrix = new StringBuffer();
    static StringBuffer invalidRowMatrix = new StringBuffer();
   
    //Initialize the XML using StringBuffers
    static{
    	
	    //a non square matrix
    	nonSquareMatrix
	    .append("<matrix name='A' rows='3' columns='2'>")
	    .append("<r><c>1</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c></r>")
	    .append("</matrix>");
	    
    	//columns of A = 2, rows of B = 3
    	invalidRowColMatrix
    	.append("<matrixList>")
    	.append("<matrix name='A' rows='3' columns='2'>")
	    .append("<r><c>1</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c></r>")
	    .append("</matrix>")
	    .append("<matrix name='B' rows='3' columns='3'>")
	    .append("<r><c>1</c><c>1</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>")
	    .append("</matrixList>");
    	
    	//rows of A = 2, rows of B = 3
    	invalidRowMatrix
    	.append("<matrixList>")
    	.append("<matrix name='A' rows='2' columns='3'>")
	    .append("<r><c>1</c><c>1</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("</matrix>")
	    .append("<matrix name='B' rows='3' columns='3'>")
	    .append("<r><c>1</c><c>1</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>")
	    .append("</matrixList>");
    	
	    try {
	    	nonSquareMatrixDoc = builder.parse(new InputSource(new StringReader(nonSquareMatrix.toString())));
	    	invalidRowColMatrixDoc = builder.parse(new InputSource(new StringReader(invalidRowColMatrix.toString())));
	    	invalidRowMatrixDoc = builder.parse(new InputSource(new StringReader(invalidRowMatrix.toString())));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    
    /**
     * 
     * @param name
     */
    public ResourceTestCase(final String name) {
        super(name);
    }

    /**
     * Test that the incoming matrix must be square.
     */
    public void testDecompCholesky(){
    	try {
			MatrixServiceParameters params = MatrixParamParser.
			getDecompCholeskyParamsFromDomNode(nonSquareMatrixDoc.getDocumentElement());
			fail("INVALID non-square matrix parsed successfully by " +
					"getDecompCholeskyParamsFromDomNode()!  BAD!");
        }
        catch(ResourceException e)
        {
        	System.out.println("Exception caught as expected in testDecompCholesky(): " +
            		e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * 
     */
    public void testMultiplication(){
    	//The columns of A must match the rows of B for this operation.
    	try {
			MatrixServiceParameters params = MatrixParamParser.
			getMatrixMultiplicationParamsFromDomNode(invalidRowColMatrixDoc.getDocumentElement());
			fail("INVALID non-square matrix parsed successfully by " +
					"getMatrixMultiplicationParamsFromDomNode()!  BAD!");
        }
        catch(ResourceException e)
        {
        	System.out.println("Exception caught as expected in testMultiplication(): " +
            		e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * 
     */
    public void testHorizontalDirectProduct(){
    	//The two matrices must have the same row dimensions for this operation.
    	try {
			MatrixServiceParameters params = MatrixParamParser.
			getHorizontalDirectProductParamsFromDomNode(invalidRowMatrixDoc.getDocumentElement());
			fail("INVALID non-square matrix parsed successfully by " +
					"getHorizontalDirectProductParamsFromDomNode()!  BAD!");
        }
        catch(ResourceException e)
        {
        	System.out.println("Exception caught as expected in testHorizontalDirectProduct(): " +
            		e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * the incoming matrix must be square
     */
    public void testInversion(){
    	try {
			MatrixServiceParameters params = MatrixParamParser.
			getMatrixInversionParamsFromDomNode(nonSquareMatrixDoc.getDocumentElement());
			fail("INVALID non-square matrix parsed successfully by " +
					"getMatrixInversionParamsFromDomNode()!  BAD!");
        }
        catch(ResourceException e)
        {
        	System.out.println("Exception caught as expected in testInversion(): " +
            		e.getMessage());
            assertTrue(true);
        }
    }
    
    
    
    /**
     * Just a reminder to always put this line in a method until
     * it actually works against production code.
     */
    public void testTemplate(){
    	//fail("Not yet implemented.");
    	
    }
}
