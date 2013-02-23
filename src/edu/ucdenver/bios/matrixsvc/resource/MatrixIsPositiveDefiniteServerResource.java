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

import org.apache.commons.math3.linear.RealMatrix;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.matrix.MatrixUtils;
import edu.ucdenver.bios.matrixsvc.application.MatrixConstants;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;

/**
 * The Implementation of MatrixIsPositiveDefiniteResource.
 * @author VIJAY AKULA
 *
 */
public class MatrixIsPositiveDefiniteServerResource
extends ServerResource implements MatrixIsPositiveDefiniteResource{
    /**
     * Instance of matrix Helper class.
     */
    private MatrixHelper matrixHelper = new MatrixHelper();
    
    /**
     * Instance of matrix display logger error
     */
    private DisplayLoggerError display = new DisplayLoggerError();
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
    @Post
    public Boolean isPositiveDefinite(final NamedMatrix matrix) {
        if (matrix == null) {
            display.displayError(MatrixConstants.POSITIVE_DEFINITE_NOTPOSSIBLE,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        RealMatrix realMatrix = matrixHelper.toRealMatrix(matrix);
        boolean isPositiveDefinite = false;
        if (realMatrix.isSquare()) {
            isPositiveDefinite = MatrixUtils.isPositiveDefinite(realMatrix,
                    MatrixConstants.EIGEN_TOLERANCE_DEFAULT);
        } else {
            display.displayError(MatrixConstants.POSITIVE_DEFINITE_NOTPOSSIBLE,
                    MatrixConstants.IS_NOT_SQUARE);
        }
        return isPositiveDefinite;
    }

}
