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


import org.apache.commons.math.linear.RealMatrix;
import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.matrix.MatrixUtils;
import edu.cudenver.bios.matrixsvc.application.MatrixLogger;
import edu.cudenver.bios.matrixsvc.application.NamedRealMatrix;
import edu.ucdenver.bios.matrixsvc.appliaction.MatrixConstants;
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
		List<RealMatrix> realMatrixList = matrixHelper.toRealMatrix(matrixList);

		RealMatrix matrixSum = realMatrixList.get(0);
		for(int i = 1; i < realMatrixList.size(); i++)
		{
        	matrixSum = new NamedRealMatrix( matrixSum.add((RealMatrix) matrixList.get(i)));
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
		List<RealMatrix> realMatrixList = matrixHelper.toRealMatrix(matrixList);
	
		RealMatrix differenceMatrix = matrixHelper.toRealMatrix(matrixList.get(0));
		RealMatrix nextMatrix = null;
		for(int i = 1; i < realMatrixList.size(); i++)
		{
			nextMatrix = realMatrixList.get(i);
			differenceMatrix = differenceMatrix.subtract(nextMatrix);
        }
		NamedMatrix matrix = matrixHelper.toNamedMatrix(differenceMatrix, 
				MatrixConstants.SUBTRACTION_MATRIX_RETURN_NAME);
		return matrix;
	}

	@Override
	/**
	 * Implementation of multiplication of Matrices
	 */
	public NamedMatrix multiply(ArrayList<NamedMatrix> matrixList) 
	{
		List<RealMatrix> realMatrixList = matrixHelper.toRealMatrix(matrixList);
		
		RealMatrix productMatrix = realMatrixList.get(0);
		RealMatrix nextMatrix = null;
		
		for(int i = 1; i< realMatrixList.size(); i++)
		{
			nextMatrix = realMatrixList.get(i);
			productMatrix = productMatrix.multiply(nextMatrix);
		}
		NamedMatrix matrix = matrixHelper.toNamedMatrix(productMatrix, 
				MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);
		
		return matrix;
	}

	@Override
	/**
	 * Implementation of element wise multiplication
	 */
	public NamedMatrix elementWiseMultiply(ArrayList<NamedMatrix> matrixList) 
	{
		List<RealMatrix> realMatrixList = matrixHelper.toRealMatrix(matrixList);
		
		RealMatrix productMatrix = realMatrixList.get(0);
		RealMatrix nextMatrix = null;
		for(int i = 1; i < realMatrixList.size(); i++)
		{
			nextMatrix = realMatrixList.get(i);
			productMatrix = MatrixUtils.getElementWiseProduct(productMatrix, nextMatrix);
		}
		NamedMatrix matrix = matrixHelper.toNamedMatrix(productMatrix, 
				MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);
		return matrix;
	}

	@Override
	/**
	 * Implementation of horizontal direct multiplication
	 */
	public NamedMatrix horizontalDirectMultiply(ArrayList<NamedMatrix> matrixList) 
	{
		List<RealMatrix> realMatrixList = matrixHelper.toRealMatrix(matrixList);
		
		RealMatrix productMatrix = realMatrixList.get(0);
		RealMatrix nextMatrix = null;
		for(int i = 1; i < realMatrixList.size(); i++)
		{
			nextMatrix = realMatrixList.get(i);
			productMatrix = MatrixUtils.getHorizontalDirectProduct(productMatrix, nextMatrix);
		}
		NamedMatrix matrix = matrixHelper.toNamedMatrix(productMatrix, 
				MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);
		
		return matrix;
	}

	@Override
	/**
	 * Implementation of sclar matrix multiplication
	 */
	public NamedMatrix scalarMultiply(double scalar, NamedMatrix matrix) 
	{
		double multiplier = scalar;
		RealMatrix resultMatrix = matrixHelper.toRealMatrix(matrix);
		resultMatrix = resultMatrix.scalarMultiply(multiplier);
		NamedMatrix result = matrixHelper.toNamedMatrix(resultMatrix, 
				MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);
		return result;
	}

	@Override
	/**
	 * Implementation of kronecker Multiplication
	 */
	public NamedMatrix kroneckerMultiply(ArrayList<NamedMatrix> matrixList) 
	{
		List<RealMatrix> realMatrixList = matrixHelper.toRealMatrix(matrixList);
		
		RealMatrix productMatrix = MatrixUtils.getKroneckerProduct(realMatrixList);
		
		NamedMatrix matrix = matrixHelper.toNamedMatrix(productMatrix, 
				MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);
		
		return matrix;
	}

	@Override
	/**
	 * Implementation of Cholesky Decomposition
	 */
	public ArrayList<NamedMatrix> choleskyDecompose(NamedMatrix matrix) 
	{
		return null;
	}

	@Override
	/**
	 * Implementation of inversion of matrix
	 */
	public NamedMatrix invert(NamedMatrix matrix) 
	{
		return null;
	}

	@Override
	/**
	 * Implementation to find rank of matrix
	 */
	public Integer rank(NamedMatrix matrix) 
	{
		return null;
	}

	@Override
	/**
	 * Implementation to find trace of a matrix
	 */
	public Double trace(NamedMatrix matrix) 
	{
		return null;
	}

	@Override
	/**
	 * To find if the matrix is a Positive Definite Matrix or not
	 * returns true if the matrix is a positive definite else returns false
	 */
	public Boolean isPositiveDefinite(NamedMatrix matrix) 
	{
		return null;
	}

	/**
	 * 
	 */
	@Override
	public NamedMatrix vec(NamedMatrix matrix) 
	{
		return null;
	}

	/**
	 * 
	 */
	@Override
	public NamedMatrix vech(NamedMatrix matrix) 
	{
		return null;
	}

}
