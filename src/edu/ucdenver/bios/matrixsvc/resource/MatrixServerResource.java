/*
 * Matrix Service for the GLIMMPSE Software System.  Processes
 * incoming HTTP requests for matrix operations like addition, substraction
 * kronecker multiplication and so on
 * Copyright (C) 2010 Regents of the University of Colorado.
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package edu.ucdenver.bios.matrixsvc.resource;

import java.util.ArrayList;

import java.util.List;

import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.NonSquareMatrixException;
import org.apache.commons.math.linear.NotPositiveDefiniteMatrixException;
import org.apache.commons.math.linear.NotSymmetricMatrixException;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.SingularValueDecompositionImpl;
import org.apache.commons.math.linear.CholeskyDecompositionImpl;
import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.matrix.MatrixUtils;
import edu.ucdenver.bios.matrixsvc.appliaction.MatrixConstants;
import edu.ucdenver.bios.matrixsvc.appliaction.MatrixLogger;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;

// to-do: Auto-generated Javadoc
/**
 * Resource for handling requests for element-wise multiplication calculations.
 * 
 * @author Vijay Akula
 * 
 */
public class MatrixServerResource extends ServerResource implements
        MatrixResource {
    /**
     * Instance of Logger class to dispaly the log msgs.
     */
    private static Logger logger = MatrixLogger.getInstance();
    /**
     * Instance of matrix Helper class.
     */
    private MatrixHelper matrixHelper = new MatrixHelper();

    /**
     * Implementation of sum of Matrices.
     * 
     * @param matrixList
     *            The matrixList is the list of matrices on which sum
     *            operation has to be performed.
     * @return namedMatrix Returns the result of addition of matrices as a
     *         NamedMatrix
     */
    @Override
    public NamedMatrix add(final ArrayList<NamedMatrix> matrixList) {
        final List<RealMatrix> realMatrixList = matrixHelper
                .toRealMatrixList(matrixList);
        RealMatrix matrixSum = null;
        boolean first = true;
        for (RealMatrix currentMatrix : realMatrixList) {
            if (first) {
                matrixSum = currentMatrix;
                first = false;

            } else if (matrixSum.getRowDimension() == currentMatrix
                    .getRowDimension()
                    && matrixSum.getColumnDimension() == currentMatrix
                            .getColumnDimension()) {
                matrixSum = matrixSum.add(currentMatrix);
            } else {
                String msg = MatrixConstants.DIMENSION_ERROR;
                msg.concat(MatrixConstants.ADDITION_NOTPOSSIBLE);
                logger.info(msg);
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                        msg);
            }
        }
        NamedMatrix matrix = matrixHelper.toNamedMatrix(matrixSum,
                MatrixConstants.ADDITION_MATRIX_RETURN_NAME);

        return matrix;
    }

    @Override
    /**
     * Implementation of differences of Martices
     * 
     * @param matrixList
     * The matrixList is the list of matrices on which
     * subtract operation has to be performed.
     * @return namedMatrix Returns a result of the
     * substraction as Named Matrix
     */
    public NamedMatrix subtract(final ArrayList<NamedMatrix> matrixList) {
        List<RealMatrix> realMatrixList = matrixHelper
                .toRealMatrixList(matrixList);
        RealMatrix differenceMatrix = null;
        boolean flag = true;
        for (RealMatrix currentMatrix : realMatrixList) {
            if (flag) {
                differenceMatrix = currentMatrix;
                flag = false;
            } else if (differenceMatrix.getRowDimension() == currentMatrix
                    .getRowDimension()
                    && differenceMatrix.getColumnDimension() == currentMatrix
                            .getColumnDimension()) {
                differenceMatrix = differenceMatrix.subtract(currentMatrix);
            } else {
                String msg = MatrixConstants.DIMENSION_ERROR;
                msg.concat(MatrixConstants.SUBTRACTION_NOTPOSSIBLE);
                logger.info(msg);
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                        msg);
            }
        }
        NamedMatrix matrix = matrixHelper.toNamedMatrix(differenceMatrix,
                MatrixConstants.SUBTRACTION_MATRIX_RETURN_NAME);
        return matrix;
    }

    /**
     * Implementation of multiplication of Matrices.
     * 
     * @param matrixList
     *            The matrixList is the list of matrices on which
     *            multiplication operation has to be performed.
     * @return namedMatrix Returns the result of multiplication
     * as a NamedMatrix
     */
    @Override
    public NamedMatrix multiply(final ArrayList<NamedMatrix> matrixList) {
        List<RealMatrix> realMatrixList = matrixHelper
                .toRealMatrixList(matrixList);
        RealMatrix productMatrix = null;
        boolean flag = true;
        for (RealMatrix currentMatrix : realMatrixList) {
            if (flag) {
                productMatrix = currentMatrix;
                flag = false;
            } else if (productMatrix.getColumnDimension() == currentMatrix
                    .getRowDimension()) {
                productMatrix = productMatrix.multiply(currentMatrix);
            } else {
                displayError(MatrixConstants.MULTIPLICATION_NOTPOSSIBLE, "");
            }
        }
        NamedMatrix matrix = matrixHelper.toNamedMatrix(productMatrix,
                MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);
        return matrix;
    }

    /**
     * Implementation of element wise multiplication.
     * 
     * @param matrixList
     *            The matrixList is the list of matrices on which
     *            elementWiseMultiply operation has to be performed.
     * @return namedMatrix Returns the result of element wise multiplication as
     *         a named matrix
     */
    @Override
    public NamedMatrix elementWiseMultiply(
            final ArrayList<NamedMatrix> matrixList) {
        List<RealMatrix> realMatrixList = matrixHelper
                .toRealMatrixList(matrixList);

        RealMatrix productMatrix = null;
        boolean flag = true;
        for (RealMatrix currentMatrix : realMatrixList) {
            if (flag) {
                productMatrix = currentMatrix;
                flag = false;
            } else if (productMatrix.getRowDimension() == currentMatrix
                    .getRowDimension()
                    && productMatrix.getColumnDimension() == currentMatrix
                            .getColumnDimension()) {
                productMatrix = MatrixUtils.getElementWiseProduct(
                        productMatrix, currentMatrix);
            } else {
                displayError(
                        MatrixConstants.ELEMENTWISE_MULTIPLICATION_NOTPOSSIBLE,
                        MatrixConstants.DIMENSION_ERROR);
            }
        }
        NamedMatrix matrix = matrixHelper.toNamedMatrix(productMatrix,
                MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);
        return matrix;
    }

    /**
     * Implementation of horizontal direct multiplication.
     * 
     * @param matrixList
     *            The matrixList is the list of matrices on which horizontal
     *            direct multiplication operation has to be performed.
     * @return namedMatrix Returns the result of horizontal direct multiply
     * as a Named Matrix
     */
    @Override
    public NamedMatrix horizontalDirectMultiply(
            final ArrayList<NamedMatrix> matrixList) {
        List<RealMatrix> realMatrixList = matrixHelper
                .toRealMatrixList(matrixList);
        RealMatrix productMatrix = null;
        boolean flag = true;
        for (RealMatrix currentMatrix : realMatrixList) {
            if (flag) {
                productMatrix = currentMatrix;
                flag = false;
            } else if (productMatrix.getRowDimension() == currentMatrix
                    .getRowDimension()) {
                productMatrix = MatrixUtils.getHorizontalDirectProduct(
                        productMatrix, currentMatrix);
            } else {
                displayError(MatrixConstants.HORIZONTAL_DIRECT_MULTIPLY_ERROR,
                        "");
            }
        }
        NamedMatrix matrix = matrixHelper.toNamedMatrix(productMatrix,
                MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);
        return matrix;
    }

    /**
     * Implementation of sclar matrix multiplication.
     * 
     * @param scalar
     *            It is double value which is to be multiplied to each
     *            and every element of the matrix.
     * @param matrix
     *            It is the matrix on which the scalar multiply operation
     *            has to be carried out.
     * @return namedMatrix Returns the result of scalar multiply as a
     *         NamedMatrix.
     */
    @Override
    public NamedMatrix scalarMultiply(final double scalar,
            final NamedMatrix matrix) {
        double multiplier = scalar;
        RealMatrix resultMatrix = matrixHelper.toRealMatrix(matrix);
        resultMatrix = resultMatrix.scalarMultiply(multiplier);
        NamedMatrix result = matrixHelper.toNamedMatrix(resultMatrix,
                MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);
        return result;
    }

    /**
     * Implementation of kronecker Multiplication.
     * 
     * @param matrixList
     *            The matrixList is the list of matrices on which kronecker
     *            Multiplication has to be carried out.
     * @return namedMatrix Returns the result of kronrcker multiply as a
     *         NamedMatrix.
     */
    @Override
    public NamedMatrix kroneckerMultiply(
            final ArrayList<NamedMatrix> matrixList) {
        List<RealMatrix> realMatrixList = matrixHelper
                .toRealMatrixList(matrixList);

        RealMatrix productMatrix = MatrixUtils
                .getKroneckerProduct(realMatrixList);

        NamedMatrix matrix = matrixHelper.toNamedMatrix(productMatrix,
                MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);

        return matrix;
    }

    /**
     * Implementation of Cholesky Decomposition.
     * 
     * @param matrix
     *            The matrixList is the list of matrices on which cholesky
     *            decomposition has to be carried out.
     * @return namedMatrixList Returns the result of cholesky decomposition
     * as a ArrayList of NamedMatrix.
     */
    @Override
    public ArrayList<NamedMatrix> choleskyDecompose(final NamedMatrix matrix) {
        if (matrix == null) {
            logger.info(MatrixConstants.NO_INPUT_SPECIFIED);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }

        RealMatrix realMatrix = matrixHelper.toRealMatrix(matrix);
        ArrayList<NamedMatrix> namedMatrixList = new ArrayList<NamedMatrix>();
        ArrayList<RealMatrix> realMatrixList = new ArrayList<RealMatrix>();
        try {
            CholeskyDecompositionImpl chdImp = new CholeskyDecompositionImpl(
                    realMatrix);
            realMatrixList.add(chdImp.getL());
            realMatrixList.add(chdImp.getLT());
            for (RealMatrix currentMatrix : realMatrixList) {
                namedMatrixList.add(matrixHelper.toNamedMatrix(currentMatrix,
                        ""));
            }
        } catch (NonSquareMatrixException notSquare) {
            displayError(MatrixConstants.CHOLESKY_DECOMPOSITION_NOTPOSSIBLE,
                    MatrixConstants.IS_NOT_SQUARE);
        } catch (NotSymmetricMatrixException notSymmetric) {
            displayError(MatrixConstants.CHOLESKY_DECOMPOSITION_NOTPOSSIBLE,
                    MatrixConstants.IS_NOT_SYMMETRIC);
        } catch (NotPositiveDefiniteMatrixException notPositiveDefinite) {
            displayError(MatrixConstants.CHOLESKY_DECOMPOSITION_NOTPOSSIBLE,
                    MatrixConstants.POSITIVE_DEFINITE_NOTPOSSIBLE);
        }
        return namedMatrixList;
    }

    /**
     * Implementation of inversion of matrix.
     * 
     * @param matrix
     *            matrix is the input matrix on which matrix inversion
     *            operation has to be carried out.
     * @return namedMatrix Returns the result of invert operation as a
     *         NamedMatrix.
     */
    @Override
    public NamedMatrix invert(final NamedMatrix matrix) {
        if (matrix == null) {
            displayError(MatrixConstants.MATRIX_INVERSION_NOTPOSSIBLE,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        RealMatrix inverseMatrix = null;
        RealMatrix inputMatrix = matrixHelper.toRealMatrix(matrix);
        if (inputMatrix.isSquare()) {
            if (inputMatrix.getDeterminant() != 0) {
                inverseMatrix = new LUDecompositionImpl(inputMatrix)
                        .getSolver().getInverse();
            }
        } else {
            displayError(MatrixConstants.MATRIX_INVERSION_NOTPOSSIBLE,
                    MatrixConstants.IS_NOT_SQUARE);
        }

        NamedMatrix result = matrixHelper.toNamedMatrix(inverseMatrix,
                MatrixConstants.INVERSION_MATRIX_RETURN_NAME);

        return result;
    }

    /**
     * Implementation to find rank of matrix.
     * 
     * @param matrix
     *            matrix is the input matrix for which rank
     *            has to be found out.
     * @return rank Returns rank of the input matrix as a Integer value.
     */
    @Override
    public Integer rank(final NamedMatrix matrix) {
        if (matrix == null) {
            displayError(MatrixConstants.RANK_NOTPOSSIBLE,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        RealMatrix realMatrix = matrixHelper.toRealMatrix(matrix);
        Integer rank = null;
        SingularValueDecompositionImpl impl =
                new SingularValueDecompositionImpl(realMatrix);
        rank = impl.getRank();
        return rank;
    }

    /**
     * Implementation to find trace of a matrix.
     * 
     * @param matrix
     *            matrix is the input matrix for which trace has o be
     *            calculated.
     * @return trace Returns trace of the input matrix as a double value.
     */
    @Override
    public Double trace(final NamedMatrix matrix) {
        if (matrix == null) {
            displayError(MatrixConstants.TRACE_NOTPOSSIBLE,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        Double trace = null;
        RealMatrix realMatrix = matrixHelper.toRealMatrix(matrix);
        if (realMatrix.isSquare()) {
            trace = realMatrix.getTrace();
        } else {
            displayError(MatrixConstants.TRACE_NOTPOSSIBLE,
                    MatrixConstants.IS_NOT_SQUARE);
        }
        return trace;
    }

    /**
     * To find if the matrix is a Positive Definite Matrix or not returns true
     * if the matrix is a positive definite else returns false.
     * 
     * @param matrix
     *            matrix is the input matrix which it to be checked if it is a
     *            positive definite matrix or not.
     * @return isPositiveDefinite Returns true if the input matrix is positive
     *         definite else false.
     */
    @Override
    public Boolean isPositiveDefinite(final NamedMatrix matrix) {
        if (matrix == null) {
            displayError(MatrixConstants.POSITIVE_DEFINITE_NOTPOSSIBLE,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        RealMatrix realMatrix = matrixHelper.toRealMatrix(matrix);
        boolean isPositiveDefinite = false;
        if (realMatrix.isSquare()) {
            isPositiveDefinite = MatrixUtils.isPositiveDefinite(realMatrix,
                    MatrixConstants.EIGEN_TOLERANCE_DEFAULT);
        } else {
            displayError(MatrixConstants.POSITIVE_DEFINITE_NOTPOSSIBLE,
                    MatrixConstants.IS_NOT_SQUARE);
        }
        return isPositiveDefinite;
    }

    /**
     * This method is used to perform vec operation on a matrix if a matrix is
     * {1 2 3, 4 5 6, 7 8 9} then the output would be a colum matrix with
     * elements {1 4 7 2 5 8 3 6 9}.
     * 
     * @param matrix
     *            matrix is the input matrix on which vector operation has
     *            to be performed.
     * @return namedMatrix Returns the result as a column matrix which is a
     *         NamedMatrix.
     */
    @Override
    public NamedMatrix vec(final NamedMatrix matrix) {
        if (matrix == null) {
            displayError(MatrixConstants.VEC_MATRIX_NOTPOSSIBLE,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        RealMatrix realMatrix = MatrixUtils.getVecMatrix(matrixHelper
                .toRealMatrix(matrix));

        NamedMatrix vecMatrix = matrixHelper.toNamedMatrix(realMatrix,
                MatrixConstants.VEC_MATRIX_RETURN_NAME);
        return vecMatrix;
    }

    /**
     * This method is used to perform vech operation on a matrix if a matrix
     * is {1 2 3, 4 5 6, 7 8 9} then the output would be a colum matrix with
     * elements {1 4 7 5 8 9}.
     * 
     * @param matrix
     *            matrix is the input matrix on which vector from the columns
     *            of the lower tringular elements of a matrix.
     * @return namedMatrix Returns the result as a column matrix which is a
     *         NamedMatrix.
     */
    @Override
    public NamedMatrix vech(final NamedMatrix matrix) {
        if (matrix == null) {
            displayError(MatrixConstants.VECH_MATRIX_NOTPOSSIBLE,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        RealMatrix realMatrix = matrixHelper.toRealMatrix(matrix);
        if (MatrixUtils.isSymmetric(realMatrix)) {
            realMatrix = MatrixUtils.getVechMatrix(realMatrix);
        } else {
            displayError(MatrixConstants.VECH_MATRIX_NOTPOSSIBLE,
                    MatrixConstants.IS_NOT_SYMMETRIC);
        }
        NamedMatrix vechMatrix = matrixHelper.toNamedMatrix(realMatrix,
                MatrixConstants.VECH_MATRIX_RETURN_NAME);
        return vechMatrix;
    }

    /**
     * This method is used to display errors if any.
     * 
     * @param preMsg
     *            the premsg is the first string in the error message to be
     *            displayed.
     * @param posMsg
     *            the posmsg is the second string in the error message to be
     *            displayed.
     */
    public final void displayError(final String preMsg, final String posMsg) {
        String msg = null;
        msg = preMsg.concat(posMsg);
        logger.info(msg);
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
    }
}
