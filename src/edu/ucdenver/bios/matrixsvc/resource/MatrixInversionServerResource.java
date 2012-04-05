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

import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.RealMatrix;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.matrixsvc.application.MatrixConstants;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;

/**
 * 
 * @author VIJAY AKULA
 *
 */
public class MatrixInversionServerResource extends ServerResource
implements MatrixInversionResource{
    /**
     * Instance of matrix Helper class.
     */
    private MatrixHelper matrixHelper = new MatrixHelper();
    
    /**
     * Instance of matrix display logger error
     */
    private DisplayLoggerError display = new DisplayLoggerError();
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
            display.displayError(MatrixConstants.MATRIX_INVERSION_NOTPOSSIBLE,
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
            display.displayError(MatrixConstants.MATRIX_INVERSION_NOTPOSSIBLE,
                    MatrixConstants.IS_NOT_SQUARE);
        }

        NamedMatrix result = matrixHelper.toNamedMatrix(inverseMatrix,
                MatrixConstants.INVERSION_MATRIX_RETURN_NAME);

        return result;
    }
}
