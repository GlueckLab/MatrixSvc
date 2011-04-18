package edu.cudenver.bios.matrixsvc.resource;

import edu.cudenver.bios.matrixsvc.application.MatrixConstants;
import edu.cudenver.bios.matrixsvc.application.MatrixServiceParameters;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MatrixParamParser {

	/**
	 * 
	 * @param node
	 * @return MatrixServiceParameters
	 * @throws ResourceException
	 */
	public static MatrixServiceParameters getAdditionParamsFromDomNode(Node node)
	throws ResourceException
	{
		MatrixServiceParameters params = new MatrixServiceParameters();

        // make sure the root node is a proper input to an addition service
        if (!node.getNodeName().equals(MatrixConstants.TAG_MATRIX_LIST))
        {
            notifyClientBadRequest(node.getNodeName(), MatrixConstants.TAG_MATRIX_LIST);
        }
        
        // We have correct root node, parse it
        
        
        return params;
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
		MatrixServiceParameters params = new MatrixServiceParameters();

        // make sure the root node is a proper input to an addition service
        if (!node.getNodeName().equals(MatrixConstants.TAG_MATRIX_LIST))
        {
            notifyClientBadRequest(node.getNodeName(), MatrixConstants.TAG_MATRIX_LIST);
        }
        
        // We have correct root node, parse it
        
        
        return params;
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
		MatrixServiceParameters params = new MatrixServiceParameters();

        // make sure the root node is a proper input to an addition service
        if (!node.getNodeName().equals(MatrixConstants.TAG_PARAMETER_LIST))
        {
            notifyClientBadRequest(node.getNodeName(), MatrixConstants.TAG_PARAMETER_LIST);
        }
        
        // We have correct root node, parse it
        
        
        return params;
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
		MatrixServiceParameters params = new MatrixServiceParameters();

        // make sure the root node is a proper input to an addition service
        if (!node.getNodeName().equals(MatrixConstants.TAG_MATRIX_LIST))
        {
            notifyClientBadRequest(node.getNodeName(), MatrixConstants.TAG_MATRIX_LIST);
        }
        
        // We have correct root node, parse it
        
        
        return params;
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
		MatrixServiceParameters params = new MatrixServiceParameters();

        // make sure the root node is a proper input to an addition service
        if (!node.getNodeName().equals(MatrixConstants.TAG_MATRIX_LIST))
        {
            notifyClientBadRequest(node.getNodeName(), MatrixConstants.TAG_MATRIX_LIST);
        }
        
        // We have correct root node, parse it
        
        
        return params;
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
		MatrixServiceParameters params = new MatrixServiceParameters();

        // make sure the root node is a proper input to an addition service
        if (!node.getNodeName().equals(MatrixConstants.TAG_MATRIX_LIST))
        {
            notifyClientBadRequest(node.getNodeName(), MatrixConstants.TAG_MATRIX_LIST);
        }
        
        // We have correct root node, parse it
        
        
        return params;
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
		MatrixServiceParameters params = new MatrixServiceParameters();

        // make sure the root node is a proper input to an addition service
        if (!node.getNodeName().equals(MatrixConstants.TAG_MATRIX_LIST))
        {
            notifyClientBadRequest(node.getNodeName(), MatrixConstants.TAG_MATRIX_LIST);
        }
        
        // We have correct root node, parse it
        
        
        return params;
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

        // make sure the root node is a proper input to an addition service
        if (!node.getNodeName().equals(MatrixConstants.TAG_MATRIX))
        {
            notifyClientBadRequest(node.getNodeName(), MatrixConstants.TAG_MATRIX);
        }
        
        // We have correct root node, parse it
        
        
        return params;
	}
	
	
	/**
	 * 
	 * @param nodeName
	 * @param tag
	 */
	private static void notifyClientBadRequest(String nodeName, String tag)
	throws ResourceException
	{
		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
        		"Invalid root node '" + nodeName + 
        		"' when parsing matrix parameter object.  " +
        		"It must be " + tag + " for this service.");
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
	
    private static RealMatrix matrixFromDomNode(Node node) throws ResourceException
    {        
        // parse the rows / columns from the attribute list
        NamedNodeMap attrs = node.getAttributes();
        Node numRowsStr = attrs.getNamedItem(MatrixConstants.ATTR_ROWS);
        int numRows = 0;
        if (numRowsStr != null) numRows = Integer.parseInt(numRowsStr.getNodeValue());

        Node numColsStr = attrs.getNamedItem(MatrixConstants.ATTR_COLUMNS);
        int numCols = 0;
        if (numColsStr != null) numCols = Integer.parseInt(numColsStr.getNodeValue());
        
        // make sure we got a reasonable value for rows/columns
        if (numRows <= 0 || numCols <=0)
            throw new IllegalArgumentException("Invalid matrix rows/columns specified - must be positive integer");
            
        // create a placeholder matrix for storing the rows/columns
        Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(numRows, numCols);
        
        // parse the children: should contain multiple row objects with col objects as children
        NodeList rows = node.getChildNodes();
        if (rows != null && rows.getLength() > 0)
        {
            for (int rowIndex = 0; rowIndex < rows.getLength() && rowIndex < numRows; rowIndex++)
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
}
