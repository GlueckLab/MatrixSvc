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
package edu.cudenver.bios.matrixsvc.resource;

import edu.cudenver.bios.matrix.MatrixUtils;
import edu.cudenver.bios.matrixsvc.application.MatrixConstants;
import edu.cudenver.bios.matrixsvc.application.MatrixLogger;
import edu.cudenver.bios.matrixsvc.application.NamedRealMatrix;
import edu.cudenver.bios.matrixsvc.application.ScalarMultiplicationParameters;

import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * This is a utility class to parse an XML DOM Node containing the request XML
 * or "Entity Body" in RESTful terminology.  It provides all parsing necessary
 * for Matrix Services.
 * @author Jonathan Cohen
 *
 */
public class MatrixParamParser {
	private static Logger logger = MatrixLogger.getInstance();
	
	/**
	 * @param Node node
	 * @return ArrayList<NamedRealMatrix>
	 * @throws ResourceException
	 */
	public static ArrayList<NamedRealMatrix> getAdditionParamsFromDomNode(Node node)
	throws ResourceException
	{
		return processParametersFromMatrixList(node);
	}
	
	/**
	 * 
	 * @param node Node
	 * @return ArrayList<NamedRealMatrix>
	 * @throws ResourceException
	 */
	public static ArrayList<NamedRealMatrix> getSubtractionParamsFromDomNode(Node node)
	throws ResourceException
	{
		return processParametersFromMatrixList(node);
	}
	
	/**
	 * 
	 * @param node Node
	 * @return ArrayList<NamedRealMatrix>
	 * @throws ResourceException
	 */
	public static ArrayList<NamedRealMatrix> getElementWiseMultiplicationParamsFromDomNode(Node node)
	throws ResourceException
	{
		ArrayList<NamedRealMatrix> matrixList = processParametersFromMatrixList(node);
		
		//The two matrices must have the same dimensions for this operation.
		boolean ok = MatrixUtils.areDimensionsEqual( matrixList.get(0), matrixList.get(1));
		if( !ok ){
			String msg = "Matrix dimensions must be equal" +
				" for element-wise multiplication.";
		    logger.info(msg);
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
		}
		
		return matrixList;
	}
	
	/**
	 * 
	 * @param node Node
	 * @return ArrayList<NamedRealMatrix>
	 * @throws ResourceException
	 */
	public static ArrayList<NamedRealMatrix> getMatrixMultiplicationParamsFromDomNode(Node node)
	throws ResourceException
	{
		ArrayList<NamedRealMatrix> matrixList = processParametersFromMatrixList(node);
		//The columns of A must match the rows of B for this operation.
		int rowsB, colsA;
		colsA = matrixList.get(0).getColumnDimension();
		rowsB = matrixList.get(1).getRowDimension();
		if( colsA != rowsB){
			String msg = "The number of columns in matrix A (" +colsA+ ") must equal"
			+ " the number of rows in matrix B (" + rowsB + ").  " +
			"They are not equal.";
			logger.info(msg);
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,msg);
		}
		return matrixList;
	}
	
	/**
	 * 
	 * @param node Node
	 * @return ArrayList<NamedRealMatrix>
	 * @throws ResourceException
	 */
	public static ArrayList<NamedRealMatrix> getHorizontalDirectProductParamsFromDomNode(Node node)
	throws ResourceException
	{
		ArrayList<NamedRealMatrix> matrixList = processParametersFromMatrixList(node);
		
		//The two matrices must have the same row dimensions for this operation.
		int rowsA, rowsB;
		rowsA = matrixList.get(0).getRowDimension();
		rowsB = matrixList.get(1).getRowDimension();
		if( rowsA != rowsB ){
			String msg = "The row dimensions of matrix A (" + rowsA  + ") " +
					"must equal the row dimensions of matrix B (" + rowsB  + "). " +
							"They are not equal." ;
			logger.info(msg);
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,msg);
		}
		return matrixList;
	}

	/**
	 * 
	 * @param node Node
	 * @return ArrayList<NamedRealMatrix>
	 * @throws ResourceException
	 */
	public static ArrayList<NamedRealMatrix> getKroneckerProductParamsFromDomNode(Node node)
	throws ResourceException
	{
		return processParametersFromMatrixList(node);
	}
	
	/**
	 * 
	 * @param node Node
	 * @return NamedRealMatrix
	 * @throws ResourceException
	 */
	public static NamedRealMatrix getMatrixInversionParamsFromDomNode(Node node)
	throws ResourceException, IllegalArgumentException
	{
		NamedRealMatrix matrix = extractMatrixFromDomNode(node);
        
        // the incoming matrix must be square in order to proceed...
        if( !matrix.isSquare()){
        	String msg = "This operation requires a square matrix.";
        	logger.info(msg);
        	throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
        }
        return matrix;
	}
	
	/**
	 * 
	 * @param node Node
	 * @return NamedRealMatrix
	 * @throws ResourceException
	 */
	public static NamedRealMatrix getMatrixRankParamsFromDomNode(Node node)
	throws ResourceException
	{
		return extractMatrixFromDomNode(node);
    }
	
	/**
	 * 
	 * @param node Node
	 * @return NamedRealMatrix
	 * @throws ResourceException
	 */
	public static NamedRealMatrix getMatrixTraceParamsFromDomNode(Node node)
	throws ResourceException
	{
		return extractMatrixFromDomNode(node);
	}
	
	/**
	 * 
	 * @param node Node
	 * @return NamedRealMatrix
	 * @throws ResourceException
	 */
	public static NamedRealMatrix getPositiveDefiniteParamsFromDomNode(Node node)
	throws ResourceException
	{
		NamedRealMatrix matrix = extractMatrixFromDomNode(node);
		
        // the incoming matrix must be square in order to proceed...
        if( !matrix.isSquare()){
        	String msg = "This operation requires a square matrix.";
        	logger.info( msg );
        	throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
        }
        return matrix;
	}
	
	/**
	 * This method parses a <matrix> node.
	 * @param node Node
	 * @return NamedRealMatrix
	 * @throws ResourceException if the incoming matrix is non-symmetric
	 * or NOT positive definite.
	 */
	public static NamedRealMatrix getDecompCholeskyParamsFromDomNode(Node node)
	throws ResourceException
	{
		NamedRealMatrix matrix = extractMatrixFromDomNode(node);
        
        // the incoming matrix must be symmetric and positive definite
        // in order to proceed...
        if( ! matrix.isSquare() ||
        	! MatrixUtils.isSymmetric(matrix) ||	
        	! MatrixUtils.isPositiveDefinite( matrix, MatrixConstants.EIGEN_TOLERANCE ) ){
        	String msg = "This operation requires a symmetric, " +
        			"positive definite matrix.";
        	logger.info( msg );
        	throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
        }
        return matrix;
	}
	
	/**
	 * 
	 * @param node Node
	 * @return NamedRealMatrix
	 * @throws ResourceException
	 */
	public static NamedRealMatrix getMatrixVecParamsFromDomNode(Node node)
	throws ResourceException
	{
        return extractMatrixFromDomNode(node);
	}
	
	/**
	 * This method requires that the matrix be symmetrical.
	 * @param node Node
	 * @return NamedRealMatrix
	 * @throws ResourceException
	 */
	public static NamedRealMatrix getMatrixVechParamsFromDomNode(Node node)
	throws ResourceException
	{
		NamedRealMatrix matrix = extractMatrixFromDomNode(node);
        String msg = "";
        if(! MatrixUtils.isSymmetric( matrix ) ){
        	msg += "This operation requires a symmetrical matrix.";
        }
        if( !msg.equals("") ){
        	logger.info(msg);
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
					msg);
        }
        return matrix;
	}
	
	/**
	 * This is a convenience method to throw a ResourceException if the
	 * request XML's root node is incorrect.
	 * @param nodeName is the name that was found in the XML for the root node.
	 * @param expectedNodeName is the expected root node name for this service.
	 */
	private static void notifyClientBadRequest(String nodeName, String expectedNodeName)
	throws ResourceException
	{
		String msg = "Invalid node '" + nodeName + 
		"' when parsing parameter object.  " +
		"It must be '" + expectedNodeName + "' for this service.";
		
		logger.info(msg);
		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
	}
	
	/**
     * Parse a matrix from XML DOM.  The matrix should be specified as follows:
     * <p>
     * &lt;matrix name='' rows='' columns='' &gt;
     * <br>&lt;r&gt;&lt;c&gt;number&lt;c/&gt;...&lt;/r&gt;
     * <br>...
     * <br>&lt;/matrix&gt;
     * 
     * @param node
     * @return matrix object
     * @throws ResourceException
     */
    private static NamedRealMatrix extractMatrixFromDomNode(Node node) 
    throws ResourceException,IllegalArgumentException
    {   
    	//make sure we have a matrix
    	if (!MatrixConstants.TAG_MATRIX.equals(node.getNodeName().trim()))
        {
            notifyClientBadRequest(node.getNodeName().trim(), MatrixConstants.TAG_MATRIX);
        }
    	
    	// parse the matrix name, and the number of rows & columns 
    	// from the attribute list
        NamedNodeMap attrs = node.getAttributes();
        int numRows = 0;
        int numCols = 0;
		String name =  "";
        try 
		{
        	// get the name
        	Node nameStr = attrs.getNamedItem(MatrixConstants.ATTR_NAME);
        	if( nameStr != null && !nameStr.equals("")){
        		name = nameStr.getNodeValue();
        	}
        	else{
        		String msg = "Cannot find attribute " +
				"'name' in this matrix.";
        		logger.info(msg);
        		throw new IllegalArgumentException(msg);
        	}
        	
        	//get the number of rows
			Node numRowsStr = attrs.getNamedItem(MatrixConstants.ATTR_ROWS);
	        if (numRowsStr != null){
				numRows = Integer.parseInt(numRowsStr.getNodeValue());
			}
			else{
				String msg = "Cannot find attribute 'rows' in this matrix.";
				logger.info(msg);
				throw new IllegalArgumentException(msg);
			}
			
	        // get the number of columns
	        Node numColsStr = attrs.getNamedItem(MatrixConstants.ATTR_COLUMNS);
			if (numColsStr != null){
				numCols = Integer.parseInt(numColsStr.getNodeValue());
			}
			else{
				String msg = "Cannot find attribute 'columns' in this matrix.";
				logger.info(msg);
				throw new IllegalArgumentException(msg);
			}
		} 
		catch (NumberFormatException e) 
		{
			e.printStackTrace();
			String msg = "Either 'rows' or 'columns' attributes couldn't be converted " +
			"from a String to a number.";
			logger.info(msg);
			throw new IllegalArgumentException(msg);
		} 
        
        // make sure we got a reasonable value for rows/columns
        if (numRows <= 0 || numCols <=0)
        {
        	String msg = "Invalid matrix rows/columns specified - " +
        			"must be positive integer";
			logger.info(msg);
            throw new IllegalArgumentException(msg);
        }
        
        // create a place holder matrix for storing the rows/columns
        NamedRealMatrix matrix = new NamedRealMatrix(numRows, numCols, name);
        
        // parse the children: should contain multiple row objects with 
        // column objects as children
        NodeList rows = node.getChildNodes();
        if (rows != null && rows.getLength() > 0)
        {
            for (int rowIndex = 0; rowIndex < rows.getLength() && 
            	rowIndex < numRows; rowIndex++)
            {
                Node row = rows.item(rowIndex);
                if (!MatrixConstants.TAG_ROW.equals(row.getNodeName())){
                    notifyClientBadRequest(row.getNodeName(), MatrixConstants.TAG_ROW);
                }
                // get all of the columns for the current row and insert into a matrix
                NodeList columns = row.getChildNodes();
                if (columns != null && columns.getLength() > 0)
                {
                    for(int colIndex = 0; colIndex < columns.getLength() && colIndex < numCols; colIndex++)
                    {
                        Node colEntry = columns.item(colIndex);
                        String valStr = colEntry.getFirstChild().getNodeValue();
                        if (colEntry.hasChildNodes() && valStr != null && !valStr.isEmpty())
                        {
                            double val;
							try {
								val = Double.parseDouble(valStr);
							} catch (NumberFormatException e) {
								String msg = "Caught exception parsing the value of element (" 
									+ rowIndex + "," + colIndex + ").  This position " +
											"contained the following: " + valStr;
								logger.info(msg);
								throw new IllegalArgumentException(msg);
							}
                            matrix.setEntry(rowIndex, colIndex, val);
                        }
                        else
                        {
                        	String msg = "Missing data in matrix [row=" + rowIndex + " col=" 
                        		+ colIndex + "]";
							logger.info(msg);
                            throw new IllegalArgumentException(msg);
                        }
                    }
                }
            }
        }
        return matrix;
    }
    
    /**
     * 
     * @param node
     * @return double representing the scalar multiplier for the 
     * scalar multiplication service.
     * @throws IllegalArgumentException
     */
    private static double extractScalarFromDomNode(Node node)
    throws IllegalArgumentException,ResourceException
    {
    	if( !MatrixConstants.ATTR_VALUE.equals( node.getNodeName().trim() ) )
    	{
    		notifyClientBadRequest(node.getNodeName().trim(), MatrixConstants.ATTR_VALUE);
    	}
    	String valStr = node.getNodeValue();
    	Double val = null;
    	if( valStr != null && !valStr.isEmpty() )
    	{
    		try {
				val = Double.parseDouble(valStr);
			} catch (NumberFormatException e) {
				
				String msg = "Scalar multiplier value doesn't " +
				"parse to a double. Its String value is "+valStr+".";
				logger.info(msg);
				throw new IllegalArgumentException(msg);
			}
    	}
    	else
    	{
    		String msg = "Scalar multiplier is null or empty.";
			logger.info(msg);
    		throw new IllegalArgumentException(msg);
    	}
    	return val.doubleValue();
    }
    
    /**
     * Since this method expects to operate on a list of matrices, the 
     * list must contain at least one matrix.  If the list is empty, it will
     * throw an exception.
     * @param node containing 
	 * <p>
	 * &lt;matrixList&gt;
     * <br>&lt;matrix name="name" rows="number" columns="number" &gt;
     * <br>&lt;r&gt;&lt;c&gt;number&lt;/c&gt;&lt;c&gt;number&lt;/c&gt;...&lt;/r&gt;
     * <br>...
     * <br>&lt;/matrix&gt;
     * <br>&lt;matrix name="name" rows="number" columns="number" &gt;
     * <br>&lt;r&gt;&lt;c&gt;number&lt;/c&gt;&lt;c&gt;number&lt;/c&gt;...&lt;/r&gt;
     * <br>...
     * <br>&lt;/matrix&gt;
	 * <br>&lt;/matrixList&gt;
     * @return MatrixServiceParameters
     * @throws ResourceException
     */
    private static ArrayList<NamedRealMatrix> processParametersFromMatrixList(Node node)
    throws ResourceException
    {
    	// make sure the root node is a matrixList
        if ( !MatrixConstants.TAG_MATRIX_LIST.equals(node.getNodeName().trim() ) )
        {
            notifyClientBadRequest(node.getNodeName().trim(), MatrixConstants.TAG_MATRIX_LIST);
        }
        
        //initialize our list of matrices
    	ArrayList<NamedRealMatrix> matrixList = new ArrayList<NamedRealMatrix>();
    	
    	//iterate over the child nodes (matrices)
    	NodeList nodeList = node.getChildNodes();
    	if( nodeList.getLength() == 0) {
    		String msg = "The matrixList did not contain any matrices.  " +
    		"It must contain at least one.";
    		logger.info(msg);
    		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
    	}
    	for(int i = 0; i < nodeList.getLength(); i++)
    	{
    		//extract the matrix from the DOM, and add to our list
    		matrixList.add( extractMatrixFromDomNode(nodeList.item(i)) );
    	}
    	return matrixList;	
    }
    
    /**
     * 
     * @param node containing 
	 * <p>
	 * &lt;parameterList&gt;
     * <br>&lt;matrix name="name" rows="number" columns="number" &gt;
     * <br>&lt;r&gt;&lt;c&gt;number&lt;/c&gt;&lt;c&gt;number&lt;/c&gt;...&lt;/r&gt;
     * <br>...
     * <br>&lt;/matrix&gt;
     * <br>&lt;scalarMultiplier&gt;
     * <br>number
     * <br>&lt;/scalarMultiplier&gt;
     * <br>&lt;/parameterList&gt;
     * @return MatrixServiceParameters
     * @throws ResourceException
     */
    public static ScalarMultiplicationParameters getScalarMultiplicationParamsFromDomNode(Node node)
    throws ResourceException
    {
    	// make sure the root node is correct
        if (!MatrixConstants.TAG_SCALAR_MULT_PARAMETER_LIST.equals(node.getNodeName().trim()))
        {
            notifyClientBadRequest(node.getNodeName().trim(), MatrixConstants.TAG_SCALAR_MULT_PARAMETER_LIST);
        }
        boolean matrixFound = false;
        boolean scalarFound = false;
        
        //initialize our return object.
        ScalarMultiplicationParameters params = new ScalarMultiplicationParameters();
        Node scalarValue  = null;
        NamedRealMatrix matrix = null;
        
        //iterate over the child nodes to find the matrix
    	NodeList nodeList = node.getChildNodes();
    	if(nodeList.getLength() < 2)
    	{
    		String msg = "The scalarMultiplicationParams doesn't contain " +
    				"proper number of elements - 2." +
    				" It only contains "+ nodeList.getLength() + ".";
    		logger.info(msg);
    		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
    	}
    	for(int i = 0; i < nodeList.getLength(); i++)
    	{
    		//get our matrix
        	Node tmpNode = nodeList.item(i);
        	if( MatrixConstants.TAG_MATRIX.equals(tmpNode.getNodeName().trim()))
        	{
        		//parse the matrix, and add to our matrix list.
            	matrix = extractMatrixFromDomNode(tmpNode);
            	matrixFound = true;
            }
        	else if( MatrixConstants.TAG_SCALAR_MULTIPLIER.equals(tmpNode.getNodeName().trim()))
        	{
        		//get our scalar
        		NamedNodeMap attrs = tmpNode.getAttributes();
                scalarValue  = attrs.getNamedItem("value");
                scalarFound = true;
        	}
    	}
        
    	if(!matrixFound){
    		String msg = "The <scalarMultiplicationParams> doesn't contain a <matrix> element.";
    		logger.info(msg);
    		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
    	}
    	if(!scalarFound){
    		String msg = "The <scalarMultiplicationParams> doesn't contain a <scalarMultiplier> element.";
    		logger.info(msg);
    		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
    	}
    	//set matrix on the parameter object.
    	params.setMatrix(matrix);
        
        //extract scalar and set on parameter object
        params.setScalarMultiplier( extractScalarFromDomNode(scalarValue));
        return params;	
    }
}
