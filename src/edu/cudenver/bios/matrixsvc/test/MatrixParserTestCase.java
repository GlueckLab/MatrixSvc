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

import edu.cudenver.bios.matrixsvc.application.MatrixLogger;
import edu.cudenver.bios.matrixsvc.application.MatrixServiceParameters;
import edu.cudenver.bios.matrixsvc.application.NamedRealMatrix;
import edu.cudenver.bios.matrixsvc.resource.MatrixParamParser;

import org.apache.commons.math.linear.RealMatrix;
import org.apache.log4j.Logger;
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
 * Unit test for parsing of incoming entity body for matrix services.
 * The tests fall into three groups: root node <matrixList>, 
 * root node <matrix>, root node <paramterList>.  There are positive
 * and negative tests for all three groups.  Further, there are
 * negative tests with a variety of error conditions.
 * 
 * @author Jonathan Cohen
 */
public class MatrixParserTestCase extends TestCase
{
	private static Logger logger = MatrixLogger.getInstance();
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
	//Test Case 1: request contains a matrixList as the root
	static Document validMatrixListDoc = null;
	static Document validMatrixListDoc1 = null;
	static Document invalidMatrixListDoc = null;
	static Document invalidMatrixListDoc1 = null;
    
    //Test Case 2: request contains a matrix as the root
	static Document validMatrixDoc = null;
	static Document invalidMatrixDoc1 = null;
	static Document invalidMatrixDoc2 = null;
	static Document invalidMatrixDoc3 = null;
	static Document invalidMatrixDoc4 = null;
	static Document invalidMatrixDoc5 = null;
    static Document invalidMatrixDoc6 = null;
    
    //Test Case 3: request contains a parameterList as the root
	static Document validParameterListDoc = null;
	static Document invalidParameterListDoc = null;
	static Document invalidParameterListDoc1 = null;
    
    /*StringBuffers to hold the XML for each request*/
    
    //Valid XML for an entity body with a matrixList element
    static StringBuffer validMatrixList = new StringBuffer();
    
    //Valid XML for an entity body with a matrixList element, and an extra 
    //misc. element in it.
    static StringBuffer validMatrixList1 = new StringBuffer();
    
    //Invalid XML for an entity body with a matrixList element
    static StringBuffer invalidMatrixList = new StringBuffer();
    
    //Invalid XML for an entity body with a matrixList element
    static StringBuffer invalidMatrixList1 = new StringBuffer();
    
    //Valid XML for an entity body with a matrix element
    static StringBuffer validMatrix = new StringBuffer();
    
    //Invalid XML for an entity body with a matrix element
    static StringBuffer invalidMatrix1 = new StringBuffer();

    //Invalid XML for an entity body with a matrix element
    static StringBuffer invalidMatrix2 = new StringBuffer();
    
    //Invalid XML for an entity body with a matrix element
    static StringBuffer invalidMatrix3 = new StringBuffer();
    
    //Invalid XML for an entity body with a matrix element
    static StringBuffer invalidMatrix4 = new StringBuffer();
    
    //Invalid XML for an entity body with a matrix element
    static StringBuffer invalidMatrix5 = new StringBuffer();
    
  //Invalid XML for an entity body with a matrix element
    static StringBuffer invalidMatrix6 = new StringBuffer();
    
    //Valid XML for an entity body with a parameterList element
    static StringBuffer validParameterList = new StringBuffer();
    
    //Invalid XML for an entity body with a parameterList element
    static StringBuffer invalidParameterList = new StringBuffer();
    
    //Invalid XML for an entity body with a parameterList element
    static StringBuffer invalidParameterList1 = new StringBuffer();
    
    //Init the XML using StringBuffers
    static{
    	//Valid matrix
    	validMatrix.append("<matrix name='A' rows='3' columns='3'>")
	    .append("<r><c>1</c><c>1</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>");
    	
    	//invalid matrix (char data)
	    invalidMatrix1.append("<matrix name='A' rows='3' columns='3'>")
	    .append("<r><c>a</c><c>3</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>");
	    
	    //invalid matrix (malformed XML - <rc> in first row)
	    invalidMatrix2.append("<matrix name='A' rows='3' columns='3'>")
	    .append("<rc><c>1</c><c>3</c><c>1</c></rc>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>");
    	    	
	    //invalid matrix (<matrix> misspelled)
	    invalidMatrix3.append("<matirx name='A' rows='3' columns='3'>")
	    .append("<r><c>1</c><c>1</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matirx>");
    	
	    //invalid matrix (malformed XML - "rows" misspelled)
	    invalidMatrix4.append("<matrix name='A' ros='3' columns='3'>")
	    .append("<r><c>1</c><c>3</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>");
    	
	    //invalid matrix (malformed XML - 2 numbers in 1 column position)
	    invalidMatrix5.append("<matrix name='A' rows='3' columns='3'>")
	    .append("<r><c>1,1</c><c>3</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>");
	    
	    //invalid matrix (malformed XML - "columns" misspelled)
	    invalidMatrix6.append("<matrix name='A' rows='3' coluns='3'>")
	    .append("<r><c>1</c><c>3</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>");
    	
	    //a valid matrix list
	    validMatrixList.append("<matrixList>")
	    .append("<matrix name='A' rows='3' columns='3'>")
	    .append("<r><c>1</c><c>1</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>")
	    .append("<matrix name='B' rows='3' columns='3'>")
	    .append("<r><c>1</c><c>1</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>")
	    .append("</matrixList>");
	    
	    //invalid matrix list (no matrices)
	    invalidMatrixList.append("<matrixList>")
	    .append("</matrixList>");
	    
	    //invalid matrix list (misc elements)
	    invalidMatrixList1.append("<matrixList>")
	    .append("<misc></misc>")
	    .append("</matrixList>");
	    
	    //valid matrix list (extra element): Note, this is really valid,
	    //as we should ignore misc. or extra elements.
	    validMatrixList1.append("<matrixList>")
	    .append("<matrix name='A' rows='3' columns='3'>")
	    .append("<r><c>1</c><c>1</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>")
	    .append("<matrix name='B' rows='3' columns='3'>")
	    .append("<r><c>1</c><c>1</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>")
	    .append("<misc>")
	    .append("/misc>")
	    .append("</matrixList>");
	    
	    //valid parameterList
	    validParameterList.append("<parameterList>")
	    .append("<matrix name='A' rows='3' columns='3'>")
	    .append("<r><c>1</c><c>1</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>")
	    .append("<scalarMultiplier value='10.0' />")
	    .append("</parameterList>");
	    
	    //invalid parameterList (no scalarMultiplier)
	    invalidParameterList.append("<parameterList>")
	    .append("<matrix name='A' rows='3' columns='3'>")
	    .append("<r><c>1</c><c>1</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>")
	    .append("</parameterList>");
	    
	    //invalid parameterList (no scalarMultiplier, but there is a second
	    //misc. element)
	    invalidParameterList1.append("<parameterList>")
	    .append("<matrix name='A' rows='3' columns='3'>")
	    .append("<r><c>1</c><c>1</c><c>1</c></r>")
	    .append("<r><c>2</c><c>2</c><c>2</c></r>")
	    .append("<r><c>3</c><c>3</c><c>3</c></r>")
	    .append("</matrix>")
	    .append("<misc></misc>")
	    .append("</parameterList>");
	    
	    try {
			validMatrixDoc = builder.parse(new InputSource(new StringReader(validMatrix.toString())));
			invalidMatrixDoc1 = builder.parse(new InputSource(new StringReader(invalidMatrix1.toString())));
			invalidMatrixDoc2 = builder.parse(new InputSource(new StringReader(invalidMatrix2.toString())));
			invalidMatrixDoc3 = builder.parse(new InputSource(new StringReader(invalidMatrix3.toString())));
			invalidMatrixDoc4 = builder.parse(new InputSource(new StringReader(invalidMatrix4.toString())));
			invalidMatrixDoc5 = builder.parse(new InputSource(new StringReader(invalidMatrix5.toString())));
			invalidMatrixDoc6 = builder.parse(new InputSource(new StringReader(invalidMatrix6.toString())));
			validMatrixListDoc = builder.parse(new InputSource(new StringReader(validMatrixList.toString())));
			validMatrixListDoc1 = builder.parse(new InputSource(new StringReader(validMatrixList.toString())));
			invalidMatrixListDoc = builder.parse(new InputSource(new StringReader(invalidMatrixList.toString())));
			invalidMatrixListDoc1 = builder.parse(new InputSource(new StringReader(invalidMatrixList1.toString())));
			validParameterListDoc = builder.parse(new InputSource(new StringReader(validParameterList.toString())));
			invalidParameterListDoc = builder.parse(new InputSource(new StringReader(invalidParameterList.toString())));
			invalidParameterListDoc1 = builder.parse(new InputSource(new StringReader(invalidParameterList1.toString())));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    public MatrixParserTestCase(final String name) {
        super(name);
    }

    /**
     * Test parsing of a valid matrix (the happy path).  In order for the params
     * object to be returned, the matrix DOM must parse without
     * exception.
     */
    public void testValidMatrix()
    {
        try
        {
        	// this parser method expects the node to contain a matrix as the
        	// root element
            MatrixServiceParameters params = 
                MatrixParamParser.getMatrixInversionParamsFromDomNode( 
                		validMatrixDoc.getDocumentElement());
            
            //assert that our params object is not null
            assertNotNull(params);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception during testValidMatrix(): " + e.getMessage());
        }
    }
    
    /**
     * This tests whether the List<@see RealMatrix> in the @see MatrixServiceParameters
     * object for the entity body (request) is not null.  (It should be NOT NULL.) 
     */
    public void testNotNullList()
    {
        try
        {
        	// this parser method expects the node to contain a matrix as the
        	//root element
            MatrixServiceParameters params = 
                MatrixParamParser.getMatrixInversionParamsFromDomNode( 
                		validMatrixDoc.getDocumentElement());
            
            //assert that our parameter contains a non-null list
            assertNotNull( params.getMatrixListFromRequest() );
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception during testNotNullList(): " + e.getMessage());
        }
    }
    
    /**
     * This tests that the list of matrices returned by the parser is not
     * empty.
     */
    public void testNotNullListElement()
    {
        try
        {
        	// this parser method expects the node to contain a matrix as the
        	//root element
            MatrixServiceParameters params = 
                MatrixParamParser.getMatrixInversionParamsFromDomNode( 
                		validMatrixDoc.getDocumentElement());
            //assert that our list contains a non-null element
            assertNotNull(params.getMatrixListFromRequest().get(0));
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            fail("Exception during testNotNullListElement(): " + e.getMessage());
        }
    }
    
    /**
     * This tests that the (0,0) and (2,2) elements of the matrix sent to the parser
     * have been parsed correctly (They should be 1, and 3 respectively.)
     */
    public void testCorrectMatrixElement()
    {
        try
        {
        	// this parser method expects the node to contain a matrix as the
        	//root element
            MatrixServiceParameters params = 
                MatrixParamParser.getMatrixInversionParamsFromDomNode( 
                		validMatrixDoc.getDocumentElement());
            
            //assert that our matrix contains the number 1 in the (0,0) position
            //and 3 in the (2,2) position
            RealMatrix matrix = params.getMatrixListFromRequest().get(0);
            assertEquals(1.0, matrix.getEntry(0, 0), 1E-10);
            assertEquals(3.0, matrix.getEntry(2, 2), 1E-10);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            fail("Exception during testCorrectMatrixElement(): " + e.getMessage());
        }
    }
    
    /**
     * This tests the parsing of a matrix element that contains an <rc> element
     * for one of the row tags.
     */
    public void testInvalidMatrixBadRow()
    {
        try
        {
        	// this parser method expects the node to contain a matrix as the
        	// root element
            MatrixServiceParameters params = 
                MatrixParamParser.getMatrixInversionParamsFromDomNode( 
                		invalidMatrixDoc2.getDocumentElement());
            
            fail("INVALID matrix inputs (<rc> instead of <r>) parsed successfully!  BAD!");
        }
        catch(Exception e)
        {
        	logger.info("Exception caught as expected in testInvalidMatrixBadRow(): " +
            		e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * This tests the parsing of a matrix element that contains a misspelled
     * </matrix> element.
     */
    public void testInvalidMatrixBadMatrixTag()
    {
        try
        {
        	// this parser method expects the node to contain a matrix as the
        	// root element
            MatrixServiceParameters params = 
                MatrixParamParser.getMatrixInversionParamsFromDomNode( 
                		invalidMatrixDoc3.getDocumentElement());
            
            fail("INVALID matrix inputs (<matrix> misspelled) parsed successfully!  BAD!");
        }
        catch(Exception e)
        {
        	logger.info("Exception caught as expected in testInvalidMatrixBadMatrixTag(): " +
            		e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * This tests the parsing of a matrix element that contains a misspelled
     * 'rows' attribute.
     */
    public void testInvalidMatrixRows()
    {
        try
        {
        	// this parser method expects the node to contain a matrix as the
        	// root element
            MatrixServiceParameters params = 
                MatrixParamParser.getMatrixInversionParamsFromDomNode( 
                		invalidMatrixDoc4.getDocumentElement());
            
            fail("INVALID matrix inputs ('rows' misspelled) parsed successfully!  BAD!");
        }
        catch(Exception e)
        {
        	logger.info("Exception caught as expected in testInvalidMatrixRows(): " +
            		e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * This tests the parsing of a matrix element that contains a misspelled
     * 'columns' attribute.
     */
    public void testInvalidMatrixColumns()
    {
        try
        {
        	// this parser method expects the node to contain a matrix as the
        	// root element
            MatrixServiceParameters params = 
                MatrixParamParser.getMatrixInversionParamsFromDomNode( 
                		invalidMatrixDoc6.getDocumentElement());
            
            fail("INVALID matrix inputs ('columns' misspelled) parsed successfully!  BAD!");
        }
        catch(Exception e)
        {
        	logger.info("Exception caught as expected in testInvalidMatrixColumns(): " +
            		e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * This tests the parsing of a matrix element that contains a misspelled
     * 'rows' attribute.
     */
    public void testInvalidMatrix2Numbers()
    {
        try
        {
        	// this parser method expects the node to contain a matrix as the
        	// root element
            MatrixServiceParameters params = 
                MatrixParamParser.getMatrixInversionParamsFromDomNode( 
                		invalidMatrixDoc5.getDocumentElement());
            
            fail("INVALID matrix inputs (2 numbers in 1 column position) parsed successfully!  BAD!");
        }
        catch(Exception e)
        {
        	logger.info("Exception caught as expected in testInvalidMatrix2Numbers(): " +
            		e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * This tests the parsing of a matrix element that contains a non-digit for
     * one of the matrix elements.
     */
    public void testInvalidMatrixNonDigit()
    {
        try
        {
        	// this parser method expects the node to contain a matrix as the
        	// root element
            MatrixServiceParameters params = 
                MatrixParamParser.getMatrixInversionParamsFromDomNode( 
                		invalidMatrixDoc1.getDocumentElement());
            
            fail("INVALID matrix inputs (char instead of digit) parsed successfully!  BAD!");
        }
        catch(Exception e)
        {
        	logger.info("Exception caught as expected in testInvalidMatrixNonDigit(): " +
            		e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * This tests the parsing of a valid matrixList parameter set 
     * (the happy path).
     */
    public void testValidMatrixList()
    {
        try
        {
            MatrixServiceParameters params = 
                MatrixParamParser.getAdditionParamsFromDomNode(validMatrixListDoc.getDocumentElement());
            
            ArrayList<NamedRealMatrix> list = params.getMatrixListFromRequest();
            assertNotNull(list.get(0));
            for( RealMatrix matrix: list){
            	assertNotNull(matrix);
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception caught as expected in testValidMatrixList(): " + e.getMessage());
        }
    }

    /**
     * This tests the parsing of a valid matrixList parameter set 
     * with an extra <misc> element in it.
     */
    public void testValidMatrixListMisc()
    {
        try
        {
            MatrixServiceParameters params = 
                MatrixParamParser.getAdditionParamsFromDomNode(validMatrixListDoc1.getDocumentElement());
            
            ArrayList<NamedRealMatrix> list = params.getMatrixListFromRequest();
            for( RealMatrix matrix: list){
            	assertNotNull(matrix);
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception during testValidMatrixListMisc(): " + e.getMessage());
        }
    }

    /**
     *  This tests the parsing of an invalid matrixList parameter set 
     *  (i.e. should throw an exception.)
     */
    public void testInvalidMatrixListNoMatrix()
    {
        try
        {
        	MatrixServiceParameters params = 
        		MatrixParamParser.getAdditionParamsFromDomNode(invalidMatrixListDoc.getDocumentElement());
            fail("INVALID matrixList inputs parsed successfully!  BAD!");
        }
        catch(Exception e)
        {
            logger.info("Exception caught as expected in testInvalidMatrixListNoMatrix(): " +
            		e.getMessage());
            assertTrue(true);
        }
    }

    /**
     *  This tests the parsing of an invalid matrixList parameter set 
     *  (i.e. should throw an exception.)
     */
    public void testInvalidMatrixListMisc()
    {
        try
        {
        	MatrixServiceParameters params = 
        		MatrixParamParser.getAdditionParamsFromDomNode(invalidMatrixListDoc1.getDocumentElement());
            fail("INVALID matrixList inputs parsed successfully!  BAD!");
        }
        catch(Exception e)
        {
            logger.info("Exception caught as expected in testInvalidMatrixListMisc():" +
            		e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * This tests the parsing of a valid parameterList root node 
     * (the happy path). Params object should contain a matrixList with
     * one matrix, and a Double for the scalarMultiplier.
     */
    public void testValidParameterList()
    {
        try
        {
            MatrixServiceParameters params = 
                MatrixParamParser.getScalarMultiplicationParamsFromDomNode(
                		validParameterListDoc.getDocumentElement());
            ArrayList<NamedRealMatrix> list = params.getMatrixListFromRequest();
            for( RealMatrix matrix: list){
            	assertNotNull(matrix);
            }
            Double scalarValue = params.getScalarMultiplier();
            assertNotNull(scalarValue);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception during testValidParameterList(): " + e.getMessage());
        }
    }
    
    /**
     * This tests the parsing of an invalid parameterList. The
     * Root Node doesn't contain a <scalarMultiplier> node, and thus there
     * is only one node (must be 2) in the call to getChildNodes().
     *  
     */
    public void testInvalidParameterListOneNode()
    {
        try
        {
            MatrixServiceParameters params = 
                MatrixParamParser.getScalarMultiplicationParamsFromDomNode(
                		invalidParameterListDoc.getDocumentElement());
            
            fail("The INVALID parameterList parsed successfully!  BAD!");
        }
        catch(Exception e)
        {
            logger.info("Exception caught as expected in " +
            		"testInvalidParameterList(): " + e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * This tests the parsing of an invalid parameterList root node. 
     * The root node does not contain the scalarMultiplier node, but does
     * contain a second <misc> node.  This tests whether we catch the fact
     * that the client could send a second, but incorrect node. 
     */
    public void testInvalidParameterListNoScalar()
    {
        try
        {
            MatrixServiceParameters params = 
                MatrixParamParser.getScalarMultiplicationParamsFromDomNode(
                		invalidParameterListDoc1.getDocumentElement());
            
            fail("The INVALID parameterList parsed successfully!  BAD!");
        }
        catch(Exception e)
        {
            logger.info("Exception caught as expected in " +
            		"testInvalidParameterListNoScalar(): " + e.getMessage());
            assertTrue(true);
        }
    }
    
    /**
     * This tests the parsing of a valid parameterList root node 
     * (the happy path). Params object should contain a matrixList with
     * one matrix, and a Double for the scalarMultiplier.
     */
    public void testValidScalarValue()
    {
        try
        {
            MatrixServiceParameters params = 
                MatrixParamParser.getScalarMultiplicationParamsFromDomNode(
                		validParameterListDoc.getDocumentElement());
            ArrayList<NamedRealMatrix> list = params.getMatrixListFromRequest();
            for( RealMatrix matrix: list){
            	assertNotNull(matrix);
            }
            Double scalarValue = params.getScalarMultiplier();
            assertEquals(10.0, scalarValue, 1E-10);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception during testValidScalarValue(): " + e.getMessage());
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
