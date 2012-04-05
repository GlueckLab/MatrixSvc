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

import org.apache.commons.math.linear.RealMatrix;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.matrixsvc.application.MatrixConstants;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;

/**
 * 
 * @author VIJAY AKULA
 *
 */
public class MatrixScalarMultiplyServerResource
extends ServerResource implements MatrixScalarMultiplyResource{
    /**
     * Instance of matrix Helper class.
     */
    private MatrixHelper matrixHelper = new MatrixHelper();


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
}
