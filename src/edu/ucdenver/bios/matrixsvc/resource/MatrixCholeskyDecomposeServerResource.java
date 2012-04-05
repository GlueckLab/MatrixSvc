/*
 * Matrix Service for the GLIMMPSE Software System.  Processes
 * incoming HTTP requests for matrix operations like addition, substraction
 * kronecker multiplication and so on
 * 
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

import org.apache.commons.math.linear.CholeskyDecompositionImpl;
import org.apache.commons.math.linear.NonSquareMatrixException;
import org.apache.commons.math.linear.NotPositiveDefiniteMatrixException;
import org.apache.commons.math.linear.NotSymmetricMatrixException;
import org.apache.commons.math.linear.RealMatrix;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.matrixsvc.application.MatrixConstants;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;

/**
 * 
 * @author VIJAY AKULA
 *
 */
public class MatrixCholeskyDecomposeServerResource extends ServerResource
implements MatrixCholeskyDecomposeResource{
    /**
     * Instance of matrix Helper class.
     */
    private MatrixHelper matrixHelper = new MatrixHelper();
    
    /**
     * Instance of matrix display logger error
     */
    private DisplayLoggerError display = new DisplayLoggerError();
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
            display.displayError("",MatrixConstants.NO_INPUT_SPECIFIED);
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
            display.displayError(MatrixConstants.CHOLESKY_DECOMPOSITION_NOTPOSSIBLE,
                    MatrixConstants.IS_NOT_SQUARE);
        } catch (NotSymmetricMatrixException notSymmetric) {
            display.displayError(MatrixConstants.CHOLESKY_DECOMPOSITION_NOTPOSSIBLE,
                    MatrixConstants.IS_NOT_SYMMETRIC);
        } catch (NotPositiveDefiniteMatrixException notPositiveDefinite) {
            display.displayError(MatrixConstants.CHOLESKY_DECOMPOSITION_NOTPOSSIBLE,
                    MatrixConstants.POSITIVE_DEFINITE_NOTPOSSIBLE);
        }
        return namedMatrixList;
    }

}
