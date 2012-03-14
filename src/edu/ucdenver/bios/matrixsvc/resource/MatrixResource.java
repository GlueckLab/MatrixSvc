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

import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
/**
 * 
 * @author Vijay Akula
 *
 */
public interface MatrixResource 
{

	/**
	 * Return the sum of the matrices in the input list.
	 * @param matrixList
	 * @return
	 */
	public NamedMatrix add(ArrayList<NamedMatrix> matrixList);
	
	/**
	 * Return the difference of the matrices in the input list.
	 * @param matrixList
	 * @return
	 */
	public NamedMatrix subtract(ArrayList<NamedMatrix> matrixList);
	
	/**
	 * Return the product of the matrices in the input list.
	 * @param matrixList
	 * @return
	 */
	public NamedMatrix multiply(ArrayList<NamedMatrix> matrixList);
	
	
	/**
	 * Return the element wise product of the matrices in the input list.
	 * @param matrixList
	 * @return
	 */
	public NamedMatrix elementWiseMultiply(ArrayList<NamedMatrix> matrixList);
	
	/**
	 * Return the horizontal direct product of the matrices in the input list.
	 * @param matrixList
	 * @return
	 */
	public NamedMatrix horizontalDirectMultiply(ArrayList<NamedMatrix> matrixList);
	
	/**
	 * Return the product of the matrix with the specified scalar value.
	 * @param scalar
	 * @param matrix
	 * @return
	 */
	public NamedMatrix scalarMultiply(double scalar, NamedMatrix matrix);
	
	/**
	 * Returns the Kronecker product pf the matricex in the input list
	 * @param matrixList
	 * @return
	 */
	public NamedMatrix kroneckerMultiply(ArrayList<NamedMatrix> matrixList);
	
	/**
	 * Return the Cholesky decomposition A=LL^Tof the matrix. 
	 * The returned list of matrices will include both components 
	 * of the decomposition, named “L” and “Ltranspose” respectively.
	 * @param matrix
	 * @return
	 */
	public ArrayList<NamedMatrix> choleskyDecompose(NamedMatrix matrix);
	
	/**
	 * Return the inverse of the matrix if possible.  
	 * Throws an exception for singular matrices.
	 * @param matrix
	 * @return
	 */
	public NamedMatrix invert(NamedMatrix matrix);
	
	/**
	 * Return the rank of the matrix.
	 * @param matrix
	 * @return
	 */
	public Integer rank(NamedMatrix matrix);
	
	/**
	 * Return the trace of the matrix.
	 * @param matrix
	 * @return
	 */
	public Double trace(NamedMatrix matrix);
	
	/**
	 * Return true if the matrix is positive definite.
	 * @param matrix
	 * @return
	 */
	public Boolean isPositiveDefinite(NamedMatrix matrix);
	
	/**
	 * Return the vec of the matrix.
	 * @param matrix
	 * @return
	 */
	public NamedMatrix vec(NamedMatrix matrix);
	
	/**
	 * Return the vech of the matrix.
	 * @param matrix
	 * @return
	 */
	public NamedMatrix vech(NamedMatrix matrix);
}
