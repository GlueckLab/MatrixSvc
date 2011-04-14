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
import edu.cudenver.bios.power.parameters.GLMMPowerParameters;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

/**
 * Unit test for parsing of incoming entity body for matrix services
 * 
 * @author Jonathan Cohen
 */
public class TestMatrixParamParsing extends TestCase
{
	//Test Case 1: request contains a matrixList as the root
    private Document validMatrixListDoc = null;
    private Document invalidMatrixListDoc = null;
    
    //Test Case 2: request contains a matrix as the root
    private Document validMatrixDoc = null;
    private Document invalidMatrixDoc = null;
    
    //Test Case 3: request contains a parameterList as the root
    private Document validParameterListDoc = null;
    private Document invalidParameterListDoc = null;
    
    //Valid XML for an entity body with a matrixList element
    private StringBuffer validMatrixList = new StringBuffer();
    
    //Invalid XML for an entity body with a matrixList element
    private StringBuffer invalidMatrixList = new StringBuffer();
    
    //Valid XML for an entity body with a matrix element
    private StringBuffer validMatrix = new StringBuffer();
    
    //Invalid XML for an entity body with a matrix element
    private StringBuffer invalidMatrix = new StringBuffer();
    
    //Valid XML for an entity body with a parameterList element
    private StringBuffer validParameterList = new StringBuffer();
    
    //Invalid XML for an entity body with a parameterList element
    private StringBuffer invalidParameterList = new StringBuffer();
    
    private void initXML(){
    	//a valid matrix list
	    validMatrixList.append("<matrixList>")
	    .append("<matrix name='A' rows='r' columns='c'>")
	    .append("<r><c>1,1</c><c>1,2</c><c>1,3</c></r>")
	    .append("<r><c>2,1</c><c>2,2</c><c>2,3</c></r>")
	    .append("<r><c>3,1</c><c>3,2</c><c>3,3</c></r>")
	    .append("</matrix>")
	    .append("<matrix name='B' rows='r' columns='c'>")
	    .append("<r><c>1,1</c><c>1,2</c><c>1,3</c></r>")
	    .append("<r><c>2,1</c><c>2,2</c><c>2,3</c></r>")
	    .append("<r><c>3,1</c><c>3,2</c><c>3,3</c></r>")
	    .append("</matrix>")
	    .append("</matrixList>");
	    
	    //invalid matrix list (only 1 matrix)
	    invalidMatrixList.append("<matrixList>")
	    .append("<matrix name='A' rows='r' columns='c'>")
	    .append("<r><c>1,1</c><c>1,2</c><c>1,3</c></r>")
	    .append("<r><c>2,1</c><c>2,2</c><c>2,3</c></r>")
	    .append("<r><c>3,1</c><c>3,2</c><c>3,3</c></r>")
	    .append("</matrix>");
	    
	    //TODO: add other xml
    }

    
    /**
     * Convert the above strings into DOM documents
     */
    public void setUp()
    {
        try
        {
            DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            validMatrixListDoc = builder.parse(new InputSource(new StringReader(validMatrixList.toString())));
            invalidMatrixListDoc = builder.parse(new InputSource(new StringReader(invalidMatrixList.toString())));
            validMatrixDoc = builder.parse(new InputSource(new StringReader(validMatrix.toString())));
            invalidMatrixDoc = builder.parse(new InputSource(new StringReader(invalidMatrix.toString())));
            validParameterListDoc = builder.parse(new InputSource(new StringReader(validParameterList.toString())));
            invalidParameterListDoc = builder.parse(new InputSource(new StringReader(invalidParameterList.toString())));
        }
        catch (Exception e)
        {
            fail();
        }
    }

    /**
     * Test parsing of a valid matrixList parameter set
     */
    public void testValidMatrixList()
    {
        try
        {
            MatrixServiceParameters params = 
                MatrixParamParser.getMatrixParametersFromDomNode(validMatrixListDoc.getDocumentElement());
            System.out.println("Valid matrixList inputs parsed successfully");
            assertTrue(true);
        }
        catch(Exception e)
        {
            System.out.println("Exception during valid matrixList parsing: " + e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    /**
     *  Test parsing of an invalid matrixList parameter set (i.e. should throw an exception)
     */
    public void testInvalidMatrixList()
    {
        try
        {
        	MatrixServiceParameters params = 
        		MatrixParamParser.getMatrixParametersFromDomNode(invalidMatrixListDoc.getDocumentElement());
            System.out.println("Invalid matrixList inputs parsed successfully!  BAD!");
            fail();
        }
        catch(Exception e)
        {
            System.out.println("Caught exception on invalid matrixList as expected:  " + e.getMessage());
            assertTrue(true);
        }
    }

}
