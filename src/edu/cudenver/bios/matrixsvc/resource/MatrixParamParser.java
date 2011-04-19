package edu.cudenver.bios.matrixsvc.resource;

import edu.cudenver.bios.matrixsvc.application.MatrixConstants;
import edu.cudenver.bios.matrixsvc.application.MatrixServiceParameters;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class MatrixParamParser {

	/**
	 * 
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
	 * 
	 * @return MatrixServiceParameters
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getAdditionParamsFromDomNode(Node node)
	throws ResourceException
	{
		return processParametersFromMatrixList(node);
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getSubtractionParamsFromDomNode(Node node)
	throws ResourceException
	{
		return processParametersFromMatrixList(node);
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getScalarMultiplicationParamsFromDomNode(Node node)
	throws ResourceException
	{
		return processParametersFromParameterList(node);
	}

	/**
	 * 
	 * @param node
	 * @return
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getElementWiseMultiplicationParamsFromDomNode(Node node)
	throws ResourceException
	{
		return processParametersFromMatrixList(node);
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getMatrixMultiplicationParamsFromDomNode(Node node)
	throws ResourceException
	{
		return processParametersFromMatrixList(node);
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getHorizontalDirectProductParamsFromDomNode(Node node)
	throws ResourceException
	{
		return processParametersFromMatrixList(node);
	}

	/**
	 * 
	 * @param node
	 * @return
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getKroneckerProductParamsFromDomNode(Node node)
	throws ResourceException
	{
		return processParametersFromMatrixList(node);
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getMatrixInversionParamsFromDomNode(Node node)
	throws ResourceException
	{
		MatrixServiceParameters params = new MatrixServiceParameters();
		ArrayList<RealMatrix> matrixList = new ArrayList<RealMatrix>();
		
        matrixList.add( extractMatrixFromDomNode(node) );
        params.setMatrixListFromRequest(matrixList);
        
        return params;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getMatrixRankParamsFromDomNode(Node node)
	throws ResourceException
	{
		MatrixServiceParameters params = new MatrixServiceParameters();
		ArrayList<RealMatrix> matrixList = new ArrayList<RealMatrix>();
		
        matrixList.add( extractMatrixFromDomNode(node) );
        params.setMatrixListFromRequest(matrixList);
        
        return params;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getMatrixTraceParamsFromDomNode(Node node)
	throws ResourceException
	{
		MatrixServiceParameters params = new MatrixServiceParameters();
		ArrayList<RealMatrix> matrixList = new ArrayList<RealMatrix>();
		
        matrixList.add( extractMatrixFromDomNode(node) );
        params.setMatrixListFromRequest(matrixList);
        
        return params;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getPositiveDefiniteParamsFromDomNode(Node node)
	throws ResourceException
	{
		MatrixServiceParameters params = new MatrixServiceParameters();
		ArrayList<RealMatrix> matrixList = new ArrayList<RealMatrix>();
		
        matrixList.add( extractMatrixFromDomNode(node) );
        params.setMatrixListFromRequest(matrixList);
        
        return params;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getDecompCholeskyParamsFromDomNode(Node node)
	throws ResourceException
	{
		MatrixServiceParameters params = new MatrixServiceParameters();
		ArrayList<RealMatrix> matrixList = new ArrayList<RealMatrix>();
		
        matrixList.add( extractMatrixFromDomNode(node) );
        params.setMatrixListFromRequest(matrixList);
        
        return params;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getMatrixVecParamsFromDomNode(Node node)
	throws ResourceException
	{
		MatrixServiceParameters params = new MatrixServiceParameters();
		ArrayList<RealMatrix> matrixList = new ArrayList<RealMatrix>();
		
        matrixList.add( extractMatrixFromDomNode(node) );
        params.setMatrixListFromRequest(matrixList);
        
        return params;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getMatrixVechParamsFromDomNode(Node node)
	throws ResourceException
	{
		MatrixServiceParameters params = new MatrixServiceParameters();
		ArrayList<RealMatrix> matrixList = new ArrayList<RealMatrix>();
		
        matrixList.add( extractMatrixFromDomNode(node) );
        params.setMatrixListFromRequest(matrixList);
        
        return params;
	}
	/**
	 * This is a convenience method to throw a ResourceException if the
	 * request XML's root node is incorrect.
	 * @param nodeName is the one that was found in the XML
	 * @param expectedNodeName is the expected root node name for this service.
	 */
	private static void notifyClientBadRequest(String nodeName, String expectedNodeName)
	throws ResourceException
	{
		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
        		"Invalid root node '" + nodeName + 
        		"' when parsing matrix parameter object.  " +
        		"It must be " + expectedNodeName + " for this service.");
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
    private static RealMatrix extractMatrixFromDomNode(Node node) throws ResourceException
    {   
    	//make sure we have a matrix
    	if (!node.getNodeName().equals(MatrixConstants.TAG_MATRIX))
        {
            notifyClientBadRequest(node.getNodeName(), MatrixConstants.TAG_MATRIX);
        }
    	
        // parse the rows / columns from the attribute list
        NamedNodeMap attrs = node.getAttributes();
        Node numRowsStr = attrs.getNamedItem(MatrixConstants.ATTR_ROWS);
        int numRows = 0;
        
        int numCols;
		try 
		{
			if (numRowsStr != null) numRows = Integer.parseInt(numRowsStr.getNodeValue());

			Node numColsStr = attrs.getNamedItem(MatrixConstants.ATTR_COLUMNS);
			numCols = 0;
			if (numColsStr != null) numCols = Integer.parseInt(numColsStr.getNodeValue());
		} catch (NumberFormatException e) 
		{
			e.printStackTrace();
			throw new IllegalArgumentException("Rows or Columns couldn't be converted " +
					"from a String to a number.");
		} 
        
        // make sure we got a reasonable value for rows/columns
        if (numRows <= 0 || numCols <=0)
        {
            throw new IllegalArgumentException("Invalid matrix rows/columns " +
            		"specified - must be positive integer");
        }
        
        // create a placeholder matrix for storing the rows/columns
        Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(numRows, numCols);
        
        // parse the children: should contain multiple row objects with col objects as children
        NodeList rows = node.getChildNodes();
        if (rows != null && rows.getLength() > 0)
        {
            for (int rowIndex = 0; rowIndex < rows.getLength() && 
            	rowIndex < numRows; rowIndex++)
            {
                Node row = rows.item(rowIndex);
                if (!MatrixConstants.TAG_ROW.equals(row.getNodeName()))
                    notifyClientBadRequest(row.getNodeName(), MatrixConstants.TAG_ROW);
                
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
                            double val = Double.parseDouble(valStr);
                            matrix.setEntry(rowIndex, colIndex, val);
                        }
                        else
                        {
                            throw new IllegalArgumentException("Missing data in matrix [row=" + rowIndex + " col=" + colIndex + "]");
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
     * @return int representing the scalar multiplier for this service
     * @throws IllegalArgumentException
     */
    private static double extractScalarFromDomNode(Node node)
    throws IllegalArgumentException
    {
    	if( !MatrixConstants.TAG_SCALAR_MULTIPLIER.equals( node.getNodeName() ) )
    	{
    		throw new IllegalArgumentException("Expecting " + 
    		MatrixConstants.TAG_SCALAR_MULTIPLIER + " but received " +
    		node.getNodeName() + " instead.");
    	}
    	String valStr = node.getFirstChild().getNodeValue();
    	System.out.println("scalar multiplier node name="+valStr);
    	Double val = null;
    	if( valStr != null && !valStr.isEmpty() )
    	{
    		try {
				val = Double.parseDouble(valStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new IllegalArgumentException("Scalar multiplier is null or empty.");
			}
    	}
    	return val.doubleValue();
    }
    
    /**
     * 
     * @param node
     * @return MatrixServiceParameters
     * @throws ResourceException
     */
    private static MatrixServiceParameters processParametersFromMatrixList(Node node)
    throws ResourceException
    {
    	// make sure the root node is a matrixList
        if (!node.getNodeName().equals(MatrixConstants.TAG_MATRIX_LIST))
        {
            notifyClientBadRequest(node.getNodeName(), MatrixConstants.TAG_MATRIX_LIST);
        }
        
        //initialize our return obj. and the list of matrices it will contain
    	MatrixServiceParameters params = new MatrixServiceParameters();
    	ArrayList<RealMatrix> matrixList = new ArrayList<RealMatrix>();
    	
    	//iterate over the child nodes (matrices)
    	NodeList nodeList = node.getChildNodes();
    	Node tmpNode = null;
    	for(int i = 0; i < nodeList.getLength(); i++)
    	{
    		tmpNode = nodeList.item(i);
    		
    		//make sure the node is a matrix
        	if(!tmpNode.equals(MatrixConstants.TAG_MATRIX) )
        	{
                notifyClientBadRequest(tmpNode.getNodeName(), MatrixConstants.TAG_MATRIX);
            }
    		matrixList.add( extractMatrixFromDomNode(node) );
    	}
    	params.setMatrixListFromRequest(matrixList);
        return params;	
    }
    
    /**
     * 
     * @param node
     * @return MatrixServiceParameters
     * @throws ResourceException
     */
    private static MatrixServiceParameters processParametersFromParameterList(Node node)
    throws ResourceException
    {
    	// make sure the root node is a proper input to an addition service
        if (!node.getNodeName().equals(MatrixConstants.TAG_PARAMETER_LIST))
        {
            notifyClientBadRequest(node.getNodeName(), MatrixConstants.TAG_PARAMETER_LIST);
        }
        
        //initialize our list of matrices, and the return obj.
        MatrixServiceParameters params = new MatrixServiceParameters();
        ArrayList<RealMatrix> matrixList = new ArrayList<RealMatrix>();
        
        //get our matrix
    	Node matrix = node.getFirstChild();
    	
    	//make sure the first node is a matrix
    	if(!matrix.getNodeName().equals(MatrixConstants.TAG_MATRIX) )
    	{
            notifyClientBadRequest(matrix.getNodeName(), MatrixConstants.TAG_MATRIX);
        }
        
    	//add to our matrix list, and set on the param object
    	matrixList.add( extractMatrixFromDomNode(matrix) );
        params.setMatrixListFromRequest(matrixList);
        
        //get our scalar
        Node tmpScalar = matrix.getNextSibling();
        
        //make sure the node is a scalar
    	if(!tmpScalar.getNodeName().equals(MatrixConstants.TAG_SCALAR_MULTIPLIER) )
    	{
            notifyClientBadRequest(tmpScalar.getNodeName(), MatrixConstants.TAG_SCALAR_MULTIPLIER);
        }
        params.setScalarMultiplier( extractScalarFromDomNode(tmpScalar));
        return params;	
    }
}
