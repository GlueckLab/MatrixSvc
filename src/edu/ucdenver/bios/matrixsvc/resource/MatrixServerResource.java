/*
 * Matrix Service for the GLIMMPSE Software System.  Processes
 * incoming HTTP requests for matrix operations like addition, substraction
 * kronecker multiplication and so on
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

package edu.ucdenver.bios.matrixsvc.resource;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.SingularValueDecompositionImpl;
import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.matrix.MatrixUtils;
import edu.ucdenver.bios.matrixsvc.appliaction.MatrixConstants;
import edu.ucdenver.bios.matrixsvc.appliaction.MatrixLogger;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;

/**
 * Resource for handling requests for element-wise multiplication calculations.
 * @author Vijay Akula
 *
 */
public class MatrixServerResource extends ServerResource
implements MatrixResource
{
	private static Logger logger = MatrixLogger.getInstance();
	MatrixHelper matrixHelper = new MatrixHelper();

	/**
	 * Implementation of sum of Matrices
	 */
	@Override
	public NamedMatrix add(ArrayList<NamedMatrix> matrixList) 
	{
		List<RealMatrix> realMatrixList = matrixHelper.toRealMatrixList(matrixList);
		RealMatrix matrixSum = null;
		boolean first = true;
		for(RealMatrix currentMatrix : realMatrixList )
		{
			if(first)
			{
				matrixSum = currentMatrix;
				first = false;
				
			}
			else if(matrixSum.getRowDimension() == currentMatrix.getRowDimension()
					&& matrixSum.getColumnDimension() == currentMatrix.getColumnDimension())
			{
				matrixSum = matrixSum.add(currentMatrix);
			}
			else
			{
				String msg = MatrixConstants.DIMENSION_ERROR;
				msg.concat(MatrixConstants.ADDITION_NOTPOSSIBLE);
	        	logger.info(msg);
	         	throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
			}
		}
		NamedMatrix matrix = matrixHelper.toNamedMatrix(matrixSum, 
				MatrixConstants.ADDITION_MATRIX_RETURN_NAME );
		
		return matrix;
	}


	@Override
	/**
	 * Implementation of differences of Martices
	 */
	public NamedMatrix subtract(ArrayList<NamedMatrix> matrixList) 
	{
		List<RealMatrix> realMatrixList = matrixHelper.toRealMatrixList(matrixList);
		RealMatrix differenceMatrix = null;
		boolean flag = true;
		for(RealMatrix currentMatrix : realMatrixList)
		{
			if(flag)
			{
				differenceMatrix = currentMatrix;
				flag = false;
			}
			else if (differenceMatrix.getRowDimension() == currentMatrix.getRowDimension()
					&& differenceMatrix.getColumnDimension() == currentMatrix.getColumnDimension())
			{
				differenceMatrix = differenceMatrix.subtract(currentMatrix);
			}
			else
			{
				String msg = MatrixConstants.DIMENSION_ERROR;
				msg.concat(MatrixConstants.SUBTRACTION_NOTPOSSIBLE);
	        	logger.info(msg);
	         	throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
			}
		}
		NamedMatrix matrix = matrixHelper.toNamedMatrix(differenceMatrix, 
					MatrixConstants.SUBTRACTION_MATRIX_RETURN_NAME);
		return matrix;
	}

	/**
	 * Implementation of multiplication of Matrices
	 */
	@Override
	public NamedMatrix multiply(ArrayList<NamedMatrix> matrixList) 
	{
		List<RealMatrix> realMatrixList = matrixHelper.toRealMatrixList(matrixList);
		RealMatrix productMatrix = null;
		boolean flag = true;
		for(RealMatrix currentMatrix: realMatrixList)
		{
			if(flag)
			{
				productMatrix = currentMatrix;
				flag = false;
			}
			else if(productMatrix.getRowDimension() == currentMatrix.getColumnDimension())
			{	
				productMatrix = productMatrix.multiply(currentMatrix);
			}
			else
			{
				displayError(MatrixConstants.MULTIPLICATION_NOTPOSSIBLE, "");
			}
		}
		NamedMatrix matrix = matrixHelper.toNamedMatrix(productMatrix, 
				MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);
		return matrix;
	}

	/**
	 * Implementation of element wise multiplication
	 */
	@Override
	public NamedMatrix elementWiseMultiply(ArrayList<NamedMatrix> matrixList) 
	{
		List<RealMatrix> realMatrixList = matrixHelper.toRealMatrixList(matrixList);

		RealMatrix productMatrix = null;
		boolean flag = true;
		for(RealMatrix currentMatrix : realMatrixList)
		{
			if(flag)
			{
				productMatrix = currentMatrix;
				flag = false;
			}
			else if(productMatrix.getRowDimension() == currentMatrix.getRowDimension()
					&& productMatrix.getColumnDimension() == currentMatrix.getColumnDimension())
			{
				productMatrix = MatrixUtils.getElementWiseProduct(productMatrix, currentMatrix);
			}
			else
			{
				displayError(MatrixConstants.ELEMENTWISE_MULTIPLICATION_NOTPOSSIBLE, 
						MatrixConstants.DIMENSION_ERROR);
			}
		}
		NamedMatrix matrix = matrixHelper.toNamedMatrix(productMatrix, 
				MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);
		return matrix;
	}

	/**
	 * Implementation of horizontal direct multiplication
	 */
	@Override
	public NamedMatrix horizontalDirectMultiply(ArrayList<NamedMatrix> matrixList) 
	{
		List<RealMatrix> realMatrixList = matrixHelper.toRealMatrixList(matrixList);
		RealMatrix productMatrix = null;
		boolean flag = true;
		for(RealMatrix currentMatrix : realMatrixList)
		{
			if(flag)
			{
				productMatrix = currentMatrix;
				flag = false;
			}
			else if(productMatrix.getRowDimension() == currentMatrix.getRowDimension())
			{
				productMatrix = MatrixUtils.getHorizontalDirectProduct(productMatrix, currentMatrix);	
			}
			else
			{
				displayError(MatrixConstants.HORIZONTAL_DIRECT_MULTIPLY_ERROR, "");
			}
		}
		NamedMatrix matrix = matrixHelper.toNamedMatrix(productMatrix, 
				MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);
		return matrix;
	}

	/**
	 * Implementation of sclar matrix multiplication
	 */
	@Override
	public NamedMatrix scalarMultiply(double scalar, NamedMatrix matrix) 
	{
		double multiplier = scalar;
		RealMatrix resultMatrix = matrixHelper.toRealMatrix(matrix);
		resultMatrix = resultMatrix.scalarMultiply(multiplier);
		NamedMatrix result = matrixHelper.toNamedMatrix(resultMatrix, 
				MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);
		return result;
	}

	/**
	 * Implementation of kronecker Multiplication
	 */
	@Override
	public NamedMatrix kroneckerMultiply(ArrayList<NamedMatrix> matrixList) 
	{
		List<RealMatrix> realMatrixList = matrixHelper.toRealMatrixList(matrixList);

		RealMatrix productMatrix = MatrixUtils.getKroneckerProduct(realMatrixList);

		NamedMatrix matrix = matrixHelper.toNamedMatrix(productMatrix, 
				MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);

		return matrix;
	}

	/**
	 * Implementation of Cholesky Decomposition
	 */
	@Override
	public ArrayList<NamedMatrix> choleskyDecompose(NamedMatrix matrix) 
	{
		if(matrix == null)
		{
			logger.info(MatrixConstants.NO_INPUT_SPECIFIED);
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, MatrixConstants.NO_INPUT_SPECIFIED);
		}
		
		/*if(!matrix.getName().equalsIgnoreCase("A"))
		{
			String msg = MatrixConstants.CHOLESKY_DECOMPOSITION_NOTPOSSIBLE;
			logger.info(msg);
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
		}*/
		
		RealMatrix realMatrix = matrixHelper.toRealMatrix(matrix);
		ArrayList<NamedMatrix> namedMatrixList = null;
		if(realMatrix.isSingular())
		{
			if(realMatrix.isSquare())
			{
				namedMatrixList = choleskyDecompose(matrix);
			}
			else
			{
				displayError(MatrixConstants.CHOLESKY_DECOMPOSITION_NOTPOSSIBLE, MatrixConstants.IS_NOT_SQUARE);
			}
		}
		else
		{
			displayError(MatrixConstants.CHOLESKY_DECOMPOSITION_NOTPOSSIBLE, MatrixConstants.IS_NOT_SYMMETRIC);
		}
		return namedMatrixList;
	}

	/**
	 * Implementation of inversion of matrix
	 */
	@Override
	public NamedMatrix invert(NamedMatrix matrix) 
	{
		if(matrix == null)
		{
			displayError(MatrixConstants.MATRIX_INVERSION_NOTPOSSIBLE, MatrixConstants.NO_INPUT_SPECIFIED);
		}
		RealMatrix inverseMatrix = null;
		RealMatrix inputMatrix = matrixHelper.toRealMatrix(matrix);
		if(inputMatrix.isSquare())
		{
			inverseMatrix = new LUDecompositionImpl(inputMatrix).getSolver().getInverse();
		}
		else
		{
			displayError(MatrixConstants.MATRIX_INVERSION_NOTPOSSIBLE, MatrixConstants.IS_NOT_SQUARE);
		}
		
		NamedMatrix result = matrixHelper.toNamedMatrix(inverseMatrix, 
				MatrixConstants.INVERSION_MATRIX_RETURN_NAME);

		return result;
	}

	/**
	 * Implementation to find rank of matrix
	 */
	@Override
	public Integer rank(NamedMatrix matrix) 
	{
		if(matrix == null)
		{
			displayError(MatrixConstants.RANK_NOTPOSSIBLE, MatrixConstants.NO_INPUT_SPECIFIED);
		}
		RealMatrix realMatrix = matrixHelper.toRealMatrix(matrix);
		Integer rank = null;
		if(realMatrix.isSquare())
		{
			SingularValueDecompositionImpl impl = 
	            	new SingularValueDecompositionImpl(realMatrix);
			rank = impl.getRank();
		}
		else
		{
			displayError(MatrixConstants.RANK_NOTPOSSIBLE, MatrixConstants.IS_NOT_SQUARE);
		}
		return rank;
	}

	/**
	 * Implementation to find trace of a matrix
	 */
	@Override
	public Double trace(NamedMatrix matrix) 
	{
		if(matrix == null)
		{
			displayError(MatrixConstants.TRACE_NOTPOSSIBLE, MatrixConstants.NO_INPUT_SPECIFIED);
		}
		Double trace = null;
		RealMatrix realMatrix = matrixHelper.toRealMatrix(matrix);
		if(realMatrix.isSquare())
		{
			trace = realMatrix.getTrace();
		}
		else
		{
			displayError(MatrixConstants.TRACE_NOTPOSSIBLE, MatrixConstants.IS_NOT_SQUARE);
		}
		return trace;
	}

	/**
	 * To find if the matrix is a Positive Definite Matrix or not
	 * returns true if the matrix is a positive definite else returns false
	 */
	@Override
	public Boolean isPositiveDefinite(NamedMatrix matrix) 
	{
		if(matrix == null)
		{
			displayError(MatrixConstants.POSITIVE_DEFINITE_NOTPOSSIBLE, 
					MatrixConstants.NO_INPUT_SPECIFIED);
		}
		RealMatrix realMatrix = matrixHelper.toRealMatrix(matrix);
		boolean isPositiveDefinite = false;
		if(realMatrix.isSquare())
		{
			isPositiveDefinite = MatrixUtils.isPositiveDefinite(realMatrix, MatrixConstants.EIGEN_TOLERANCE_DEFAULT);
		}
		else
		{
			displayError(MatrixConstants.POSITIVE_DEFINITE_NOTPOSSIBLE, 
					MatrixConstants.IS_NOT_SQUARE);
		}
		return isPositiveDefinite;
	}

	/**
	 * This method is used to perform vec operation on a matrix
	 * if a matrix is {1 2 3, 4 5 6, 7 8 9} then the output would be
	 * a colum matrix with elements
	 * {1 4 7 2 5 8 3 6 9}
	 */
	@Override
	public NamedMatrix vec(NamedMatrix matrix) 
	{
		if(matrix == null)
		{
			displayError(MatrixConstants.VEC_MATRIX_NOTPOSSIBLE, MatrixConstants.NO_INPUT_SPECIFIED);
		}
		RealMatrix realMatrix =  MatrixUtils.getVecMatrix(matrixHelper.toRealMatrix(matrix));
		
		NamedMatrix vecMatrix = matrixHelper.toNamedMatrix(realMatrix, MatrixConstants.VEC_MATRIX_RETURN_NAME);
		return vecMatrix;
	}

	/**
	 * This method is used to perform vech operation on a matrix
	 * if a matrix is {1 2 3, 4 5 6, 7 8 9} then the output
	 * would be a colum matrix with elements
	 * {1 4 7 5 8 9}
	 */
	@Override
	public NamedMatrix vech(NamedMatrix matrix) 
	{
		if(matrix == null)
		{
			displayError(MatrixConstants.VECH_MATRIX_NOTPOSSIBLE, MatrixConstants.NO_INPUT_SPECIFIED);
		}
		RealMatrix realMatrix = matrixHelper.toRealMatrix(matrix);
		if(realMatrix.isSquare())
		{
			realMatrix =  MatrixUtils.getVechMatrix(realMatrix);
		}
		else
		{
			displayError(MatrixConstants.VECH_MATRIX_NOTPOSSIBLE, MatrixConstants.IS_NOT_SQUARE);
		}
		NamedMatrix vechMatrix = matrixHelper.toNamedMatrix(realMatrix, MatrixConstants.VECH_MATRIX_RETURN_NAME);
		return vechMatrix;
	}

	/**
	 * This method is used to display errors if any
	 * @param preMsg
	 * @param posMsg
	 */
	public void displayError(String preMsg, String posMsg)
	{
		String msg = null;
		msg = preMsg.concat(posMsg);
		logger.info(msg);
		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
	}
}
