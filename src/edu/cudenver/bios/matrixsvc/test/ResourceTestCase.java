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

import edu.cudenver.bios.matrix.MatrixUtils;
import edu.cudenver.bios.matrixsvc.application.MatrixServiceParameters;
import edu.cudenver.bios.matrixsvc.application.NamedRealMatrix;
import edu.cudenver.bios.matrixsvc.resource.MatrixParamParser;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

/**
 * Unit test for specific services (matrix operations).  These tests
 * will confirm whether or not the code checks for data requirements
 * for individual operations such as: the input matrix should be square; the
 * two input matrices must have the same row dimensions; 
 * the columns of A must match the rows of B.  
 * 
 * It also tests method in the MatrixUtils object for vec, vech, isSymmetrical().
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
    
	//Test Case 4: Vec operation
	static Document vecDoc = null;
	
	//Test Case 5: Vech operation
	static Document vechDoc = null;
	
	/*StringBuffers to hold the XML for each request*/
    
    //Valid XML for an entity body with a matrixList element
    static StringBuffer nonSquareMatrix = new StringBuffer();
    static StringBuffer invalidRowColMatrix = new StringBuffer();
    static StringBuffer invalidRowMatrix = new StringBuffer();
    static StringBuffer vecInputs = new StringBuffer();
    static StringBuffer vechInputs = new StringBuffer();
    
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
    	
    	vecInputs
    	.append("<matrix name='A' rows='3' columns='3'>")
	    .append("<r><c>1</c><c>4</c><c>7</c></r>")
	    .append("<r><c>2</c><c>5</c><c>8</c></r>")
	    .append("<r><c>3</c><c>6</c><c>9</c></r>")
	    .append("</matrix>");
    	
    	vechInputs
    	.append("<matrix name='A' rows='3' columns='3'>")
    	.append("<r><c>1</c><c>9</c><c>4</c></r>")
    	.append("<r><c>9</c><c>2</c><c>6</c></r>")
    	.append("<r><c>4</c><c>6</c><c>3</c></r>")
    	.append("</matrix>");
    	
	    try {
	    	nonSquareMatrixDoc = builder.parse(new InputSource(new StringReader(nonSquareMatrix.toString())));
	    	invalidRowColMatrixDoc = builder.parse(new InputSource(new StringReader(invalidRowColMatrix.toString())));
	    	invalidRowMatrixDoc = builder.parse(new InputSource(new StringReader(invalidRowMatrix.toString())));
	    	vecDoc = builder.parse(new InputSource(new StringReader(vecInputs.toString())));
	    	vechDoc = builder.parse(new InputSource(new StringReader(vechInputs.toString())));
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
    public void testNonSquareMatrix(){
    	try {
			MatrixParamParser.getDecompCholeskyParamsFromDomNode(
					nonSquareMatrixDoc.getDocumentElement());
			fail("INVALID non-square matrix parsed successfully by " +
					"getDecompCholeskyParamsFromDomNode()!  BAD!");
        }
        catch(ResourceException e)
        {
        	System.out.println("Exception caught as expected in testNonSquareMatrix(): " +
            		e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * Test that getMatrixMultiplicationParamsFromDomNode() complains
     * if the column dimension of A doesn't match the row dimension of B.
     */
    public void testColsAMatchRowsB(){
    	try {
			MatrixParamParser.getMatrixMultiplicationParamsFromDomNode
				(invalidRowColMatrixDoc.getDocumentElement());
			fail("column dimension of A doesn't match the row dimension of B " +
			     " but was parsed by getMatrixMultiplicationParamsFromDomNode()!" +
			     "  BAD!");
        }
        catch(ResourceException e)
        {
        	System.out.println("Exception caught as expected in " +
        			"testColsAMatchRowsB(): " +
            		e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * Test that getHorizontalDirectProductParamsFromDomNode()
     * complains if the two matrices don't have the same row dimensions.
     */
    public void testSameRowDimensions(){
    	//
    	try {
			MatrixParamParser.getHorizontalDirectProductParamsFromDomNode
			(invalidRowMatrixDoc.getDocumentElement());
			fail("Matrices with different row dimensions parsed successfully by " +
					"getHorizontalDirectProductParamsFromDomNode()!  BAD!");
        }
        catch(ResourceException e)
        {
        	System.out.println("Exception caught as expected in " +
        			"testSameRowDimensions(): " +
            		e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * Test the getVecMatrix() method written in MatrixUtils.
     */
    public void testVec(){
    	try {
    		NamedRealMatrix input = MatrixParamParser.
				getMatrixVecParamsFromDomNode( vecDoc.getDocumentElement());
			
    		NamedRealMatrix result = new NamedRealMatrix( MatrixUtils.getVecMatrix(input));
			
			assertEquals(1.0, result.getEntry(0, 0), 1E-10);
			assertEquals(2.0, result.getEntry(1, 0), 1E-10);
			assertEquals(3.0, result.getEntry(2, 0), 1E-10);
			assertEquals(4.0, result.getEntry(3, 0), 1E-10);
			assertEquals(5.0, result.getEntry(4, 0), 1E-10);
			assertEquals(6.0, result.getEntry(5, 0), 1E-10);
			assertEquals(7.0, result.getEntry(6, 0), 1E-10);
			assertEquals(8.0, result.getEntry(7, 0), 1E-10);
			assertEquals(9.0, result.getEntry(8, 0), 1E-10);
			System.out.println("Vec operation succeeded. All values correct.");
        }
        catch(ResourceException e)
        {
        	fail(e.getMessage());
        }
    }
    
    /**
     * Test the getVechMatrix() method written in MatrixUtils.
     */
    public void testVech(){
    	try {
    		NamedRealMatrix input = MatrixParamParser.
				getMatrixVechParamsFromDomNode( vechDoc.getDocumentElement());
			NamedRealMatrix result = new NamedRealMatrix( MatrixUtils.getVechMatrix(input) );
			
			assertEquals(1.0, result.getEntry(0, 0), 1E-10);
			assertEquals(9.0, result.getEntry(1, 0), 1E-10);
			assertEquals(4.0, result.getEntry(2, 0), 1E-10);
			assertEquals(2.0, result.getEntry(3, 0), 1E-10);
			assertEquals(6.0, result.getEntry(4, 0), 1E-10);
			assertEquals(3.0, result.getEntry(5, 0), 1E-10);
			System.out.println("Vech operation succeeded. All values correct.");
        }
        catch(ResourceException e)
        {
        	e.printStackTrace();
        	fail(e.getMessage());
        }
    }
    
    /**
     * Test the isSymmetrical() method written in MatrixUtils.
     */
    public void testSymmetrical()
    {
    	try 
    	{
    		//create a symmetrical matrix
    		Array2DRowRealMatrix input = new Array2DRowRealMatrix(3, 3);
    		input.setEntry(0, 0, 1.0);
    		input.setEntry(0, 1, 9.0);
    		input.setEntry(0, 2, 4.0);
    		input.setEntry(1, 0, 9.0);
    		input.setEntry(1, 1, 2.0);
    		input.setEntry(1, 2, 6.0);
    		input.setEntry(2, 0, 4.0);
    		input.setEntry(2, 1, 6.0);
    		input.setEntry(2, 2, 3.0);
    		
			assertTrue(MatrixUtils.isSymmetric(input));
			System.out.println("testSymmetrical succeeded.");
		} catch (Exception e) {
			fail(e.getMessage());
		}
    }
    
    public void testNonSymmetrical(){
    	try 
    	{
    		//create a non-symmetrical matrix
    		Array2DRowRealMatrix input = new Array2DRowRealMatrix(3, 3);
    		input.setEntry(0, 0, 1.0);
    		input.setEntry(0, 1, 999.0);//non conforming
    		input.setEntry(0, 2, 4.0);
    		input.setEntry(1, 0, 9.0);
    		input.setEntry(1, 1, 2.0);
    		input.setEntry(1, 2, 6.0);
    		input.setEntry(2, 0, 4.0);
    		input.setEntry(2, 1, 6.0);
    		input.setEntry(2, 2, 3.0);
    		assertFalse( MatrixUtils.isSymmetric(input) );
    		System.out.println("testNonSymmetrical succeeded.");
		} catch (Exception e) {
			e.printStackTrace();
            System.out.println(e.getMessage());
		}
    }
    
    /**
     * Just a reminder to always put this line in a method until
     * it actually works against production code.
     */
//    public void testTemplate(){
//    	fail("Not yet implemented.");
//    	
//    }
}
