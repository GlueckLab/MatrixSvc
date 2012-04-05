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

import org.restlet.resource.Get;

import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;

/**
 * @author Vijay Akula
 * 
 */
public interface MatrixResource {
    @Get
    String getValue();
    
    
    
    
    /**
     * Return the sum of the matrices specified in the input list.
     * 
     * @param matrixList
     *            The matrixList contains the list of matrices on which
     *            sum operation has to be performed.
     * @return NamedMatrix
     */
    NamedMatrix add(ArrayList<NamedMatrix> matrixList);

    /**
     * Return the difference of the matrices specified in the input list.
     * 
     * @param matrixList
     *            The matrixList contains the list of matrices on which
     *            subtract operation has to be performed.
     * @return NamedMatrix
     */
    NamedMatrix subtract(ArrayList<NamedMatrix> matrixList);

    /**
     * Return the product of the matrices specified in the input list.
     * 
     * @param matrixList
     *            The matrixList contains the list of matrices on which
     *            multiply operation has to be performed.
     * @return NamedMatrix
     */
    NamedMatrix multiply(ArrayList<NamedMatrix> matrixList);

    /**
     * Return the element wise product of the matrices specified
     * in the input list.
     * 
     * @param matrixList
     *            The matrixList contains the list of matrices on which
     *            elementwiseMuttiply operation has to be performed.
     * @return NamedMatrix
     */
    NamedMatrix elementWiseMultiply(ArrayList<NamedMatrix> matrixList);

    /**
     * Return the horizontal direct product of the matrices specified in
     * the input list.
     * 
     * @param matrixList
     *            The matrixList contains the list of matrices on which
     *            horizontalDirectMultiply operation has to be performed.
     * @return NamedMatrix
     */
    NamedMatrix horizontalDirectMultiply(ArrayList<NamedMatrix> matrixList);

    /**
     * Return the product of the matrix with the specified scalar value.
     * 
     * @param scalar
     *            The scalar is a double value which has to be multiplied
     *            with each and every element of the input matrix.
     * @param matrix
     *            the matrix is the input matrix on which
     *            scalrMultiply operation has to be performed.
     * @return NamedMatrix
     */
    NamedMatrix scalarMultiply(double scalar, NamedMatrix matrix);

    /**
     * Returns the Kronecker product pf the matrices specified
     * in the input list.
     * 
     * @param matrixList
     *            The matrixList contains the list of matrices on which
     *            kroneckerMultiply operation has to be performed.
     * @return NamedMatrix
     */
    NamedMatrix kroneckerMultiply(ArrayList<NamedMatrix> matrixList);

    /**
     * Return the Cholesky decomposition A=LL^Tof the matrix. The returned list
     * of matrices will include both components of the decomposition, named “L”
     * and “Ltranspose” respectively.
     * 
     * @param matrix
     *            The matrix is the input matrix on which
     *            choleskyDecompose operation has to be performed.
     * @return NamedMatrix
     */
    ArrayList<NamedMatrix> choleskyDecompose(NamedMatrix matrix);

    /**
     * Return the inverse of the input matrix if possible. Throws an
     * exception for singular matrices.
     * 
     * @param matrix
     *            The matrix is the input matrix on which
     *            inverse operation has to be performed.
     * @return NamedMatrix
     */
    NamedMatrix invert(NamedMatrix matrix);

    /**
     * Return the rank of the matrix.
     * 
     * @param matrix
     *            The matrix is the input matrix on which
     *            rank operation has to be performed.
     * @return NamedMatrix
     */
    Integer rank(NamedMatrix matrix);

    /**
     * Return the trace of the matrix.
     * 
     * @param matrix
     *            The matrix is the input matrix on which
     *            trace operation has to be performed.
     * @return NamedMatrix
     */
    Double trace(NamedMatrix matrix);

    /**
     * Return true if the matrix is positive definite.
     * 
     * @param matrix
     *            The matrix is the input matrix on which
     *            isPositiveDefinite operation has to be performed.
     * @return NamedMatrix
     */
    Boolean isPositiveDefinite(NamedMatrix matrix);

    /**
     * Return the vec of the matrix.
     * 
     * @param matrix
     *            The matrix is the input matrix on which
     *            vec operation has to be performed.
     * @return NamedMatrix
     */
    NamedMatrix vec(NamedMatrix matrix);

    /**
     * Return the vech of the matrix.
     * 
     * @param matrix
     *            The matrix is the input matrix on which
     *            vech operation has to be performed.
     * @return NamedMatrix
     */
    NamedMatrix vech(NamedMatrix matrix);
}
