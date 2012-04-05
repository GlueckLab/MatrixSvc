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

package edu.ucdenver.bios.matrixsvc.application;


// TODO: Auto-generated Javadoc
// to-do: Auto-generated Javadoc

/**
 * This class holds all the constanst used in the matrix service.
 * 
 * @author VIJAY AKULA
 */
public final class MatrixConstants {
    
    /**
     * Instantiates a new matrix constants.
     */
    private MatrixConstants()
    {
    }
    // Error Msgs
    /** The Constant NO_INPUT_SPECIFIED. */
    public static final String NO_INPUT_SPECIFIED = "No input specified. ";

    /** The Constant MATRIX_LIST_ERROR. */
    public static final String MATRIX_LIST_ERROR =
            "Matrix list must contain at least 2 "
    + "matrices to perform operation. ";

    /** The Constant DIMENSION_ERROR. */
    public static final String DIMENSION_ERROR =
            "Dimension of given matrices are not equal. ";

    /** The Constant ADDITION_NOTPOSSIBLE. */
    public static final String ADDITION_NOTPOSSIBLE =
            "Addition of matrices not possible. ";

    /** The Constant SUBTRACTION_NOTPOSSIBLE. */
    public static final String SUBTRACTION_NOTPOSSIBLE =
            "Subtraction of matrices not possible. ";

    /** The Constant MULTIPLICATION_NOTPOSSIBLE. */
    public static final String MULTIPLICATION_NOTPOSSIBLE =
            "Multiplication not possible because specified matrices do not"
            + " have correct dimensions for multiplication. ";

    /** The Constant ELEMENTWISE_MULTIPLICATION_NOTPOSSIBLE. */
    public static final String ELEMENTWISE_MULTIPLICATION_NOTPOSSIBLE =
            "Elementwise multiplication not possible because, ";

    /** The Constant HORIZONTAL_DIRECT_MULTIPLY_ERROR. */
    public static final String HORIZONTAL_DIRECT_MULTIPLY_ERROR =
            "Row Dimensions in the given matrices list are not equal. Hence"
            + " Horizontal Direct Multiplication cannot be possible. ";

    /** The Constant CHOLESKY_DECOMPOSITION_NOTPOSSIBLE. */
    public static final String CHOLESKY_DECOMPOSITION_NOTPOSSIBLE =
            "Cholesky decomposition not possible because, ";

    /** The Constant MATRIX_INVERSION_NOTPOSSIBLE. */
    public static final String MATRIX_INVERSION_NOTPOSSIBLE =
            "Matrix Inversion is not possible because, ";

    /** The Constant IS_NOT_SQUARE. */
    public static final String IS_NOT_SQUARE =
            "specified Matrix is not Square. ";

    /** The Constant IS_NOT_SYMMETRIC. */
    public static final String IS_NOT_SYMMETRIC =
            "specified Matrix is not Symmetric. ";

    /** The Constant RANK_NOTPOSSIBLE. */
    public static final String RANK_NOTPOSSIBLE =
            "Rank calculation not possible because, ";

    /** The Constant TRACE_NOTPOSSIBLE. */
    public static final String TRACE_NOTPOSSIBLE =
            "Trace calculation not possible because, ";

    /** The Constant POSITIVE_DEFINITE_NOTPOSSIBLE. */
    public static final String POSITIVE_DEFINITE_NOTPOSSIBLE =
            "Positive Definite calculation not possible because, ";

    /** The Constant VEC_MATRIX_NOTPOSSIBLE. */
    public static final String VEC_MATRIX_NOTPOSSIBLE =
            "Vec operation not possible because, ";

    /** The Constant VECH_MATRIX_NOTPOSSIBLE. */
    public static final String VECH_MATRIX_NOTPOSSIBLE =
            "Vech operation not possible because, ";

    // Values of the attribute "type" for <factorList> elements
    /** The Constant BETWEEN. */
    public static final String BETWEEN = "between";

    /** The Constant WITHIN. */
    public static final String WITHIN = "within";

    // Values of the attribute "type" for <contrast> elements
    /** The Constant GRAND_MEAN. */
    public static final String GRAND_MEAN = "grandMean";

    /** The Constant MAIN_EFFECT. */
    public static final String MAIN_EFFECT = "mainEffect";

    /** The Constant INTERACTION. */
    public static final String INTERACTION = "interaction";

    // Matrix Names for Response objects
    /** The Constant ADDITION_MATRIX_RETURN_NAME. */
    public static final String ADDITION_MATRIX_RETURN_NAME = "sum";

    /** The Constant SUBTRACTION_MATRIX_RETURN_NAME. */
    public static final String SUBTRACTION_MATRIX_RETURN_NAME = "difference";

    /** The Constant MULTIPLICATION_MATRIX_RETURN_NAME. */
    public static final String MULTIPLICATION_MATRIX_RETURN_NAME = "product";

    /** The Constant INVERSION_MATRIX_RETURN_NAME. */
    public static final String INVERSION_MATRIX_RETURN_NAME = "inverse";

    /** The Constant SQ_ROOT_MATRIX_RETURN_NAME. */
    public static final String SQ_ROOT_MATRIX_RETURN_NAME = "L";

    /** The Constant TRANSPOSE_MATRIX_RETURN_NAME. */
    public static final String TRANSPOSE_MATRIX_RETURN_NAME = "lTranspose";

    /** The Constant VEC_MATRIX_RETURN_NAME. */
    public static final String VEC_MATRIX_RETURN_NAME = "vec";

    /** The Constant VECH_MATRIX_RETURN_NAME. */
    public static final String VECH_MATRIX_RETURN_NAME = "vech";

    /**
     * This value is used in the Positive Definite calculation.
     * It could be used as follows:
     * MatrixUtils.isPositiveDefinite(
     * myMatrix, MatrixConstants.EIGEN_TOLERANCE)
     */
    public static final Double EIGEN_TOLERANCE_DEFAULT = 1.0E-15;
    
    public static final String VERSION = "2.0.0";

}
