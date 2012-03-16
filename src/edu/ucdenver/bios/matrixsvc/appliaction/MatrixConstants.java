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

package edu.ucdenver.bios.matrixsvc.appliaction;
/**
 * This class holds constants for the Matrix Services.  
 * @author VIJAY AKULA
 *
 */
public class MatrixConstants 
{
	    
	    //Error Msgs
		public static final String NO_INPUT_SPECIFIED = 
				"No input specified. ";
		public static final String MATRIX_LIST_ERROR=
				"Matrix list must contain at least 2 matrices to perform operation. ";
	    public static final String DIMENSION_ERROR = 
	    		"Dimension of given matrices are not equal. ";
	    public static final String ADDITION_NOTPOSSIBLE = 
	    		"Addition of matrices not possible. ";
	    public static final String SUBTRACTION_NOTPOSSIBLE = 
	    		"Subtraction of matrices not possible. ";
	    public static final String MULTIPLICATION_NOTPOSSIBLE = 
	    		"Multiplication not possible because specified matrices do not" +
	    		" have correct dimensions for multiplication. ";
	    public static final String ELEMENTWISE_MULTIPLICATION_NOTPOSSIBLE = 
	    		"Elementwise multiplication not possible because, ";
	    public static final String HORIZONTAL_DIRECT_MULTIPLY_ERROR = 
	    		"Row Dimensions in the given matrices list are not equal. Hence" +
	    		" Horizontal Direct Multiplication cannot be possible. ";
	    public static final String CHOLESKY_DECOMPOSITION_NOTPOSSIBLE = 
	    		"Cholesky decomposition not possible because, ";
	    public static final String MATRIX_INVERSION_NOTPOSSIBLE =
	    		"Matrix Inversion is not possible because, ";
	    public static final String IS_NOT_SQUARE = 
	    		"specified Matrix is not Square. ";
	    public static final String IS_NOT_SYMMETRIC = 
	    		"specified Matrix is not Symmetric. ";
	    public static final String RANK_NOTPOSSIBLE = 
	    		"Rank calculation not possible because, ";
	    public static final String TRACE_NOTPOSSIBLE = 
	    		"Trace calculation not possible because, ";
	    public static final String POSITIVE_DEFINITE_NOTPOSSIBLE = 
	    		"Positive Definite calculation not possible because, ";
	    public static final String VEC_MATRIX_NOTPOSSIBLE = 
	    		"Vec operation not possible because, ";
	    public static final String VECH_MATRIX_NOTPOSSIBLE = 
	    		"Vech operation not possible because, ";
	    // Values of the attribute "type" for <factorList> elements
	    public static final String BETWEEN = "between";
	    public static final String WITHIN = "within";
	    
	    // Values of the attribute "type" for <contrast> elements
	    public static final String GRAND_MEAN = "grandMean";
	    public static final String MAIN_EFFECT = "mainEffect";
	    public static final String INTERACTION = "interaction";
	    
	    // Matrix Names for Response objects
	    public static final String ADDITION_MATRIX_RETURN_NAME = "sum";
	    public static final String SUBTRACTION_MATRIX_RETURN_NAME = "difference";
	    public static final String MULTIPLICATION_MATRIX_RETURN_NAME = "product";
	    public static final String INVERSION_MATRIX_RETURN_NAME = "inverse";
	    public static final String SQ_ROOT_MATRIX_RETURN_NAME = "L";
	    public static final String TRANSPOSE_MATRIX_RETURN_NAME = "lTranspose";
	    public static final String VEC_MATRIX_RETURN_NAME = "vec";
	    public static final String VECH_MATRIX_RETURN_NAME = "vech";
	    
	    /**
	     *  This value is used in the Positive Definite calculation.
	     *  It could be used as follows: 
	     *  MatrixUtils.isPositiveDefinite( myMatrix, MatrixConstants.EIGEN_TOLERANCE )
	     */
	    public static Double EIGEN_TOLERANCE_DEFAULT = 1.0E-15;

}
