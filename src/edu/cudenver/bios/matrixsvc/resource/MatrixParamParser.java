package edu.cudenver.bios.matrixsvc.resource;

import edu.cudenver.bios.matrixsvc.application.MatrixConstants;
import edu.cudenver.bios.matrixsvc.application.MatrixServiceParameters;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Node;

public class MatrixParamParser {

	public static MatrixServiceParameters getMatrixParametersFromDomNode(Node node)
	throws ResourceException
	{
		MatrixServiceParameters params = new MatrixServiceParameters();

        // make sure the root node is a proper input to a matrix service
        if (!node.getNodeName().equals(MatrixConstants.TAG_MATRIX_LIST)  &&
        	!node.getNodeName().equals(MatrixConstants.TAG_PARAMETER_LIST)  &&	
        	!node.getNodeName().equals(MatrixConstants.TAG_MATRIX)  )
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid root node '" + node.getNodeName() + 
            		"' when parsing matrix parameter object.  It must be one of the following: "  + 
            		MatrixConstants.TAG_MATRIX_LIST + " or " + MatrixConstants.TAG_PARAMETER_LIST + " or " +
            		MatrixConstants.TAG_MATRIX +".");

        return params;
	}
}
