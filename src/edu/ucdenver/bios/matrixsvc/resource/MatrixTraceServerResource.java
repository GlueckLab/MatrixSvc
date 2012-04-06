/*
 * Matrix Service for the GLIMMPSE Software System.  Processes
 * incoming HTTP requests for matrix operations like addition, substraction
 * kronecker multiplication and so on
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 */
package edu.ucdenver.bios.matrixsvc.resource;

import org.apache.commons.math.linear.RealMatrix;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.matrixsvc.application.MatrixConstants;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;

/**
 * 
 * @author VIJAY AKULA
 *
 */
public class MatrixTraceServerResource
extends ServerResource implements MatrixTraceResource{
    /**
     * Instance of matrix Helper class.
     */
    private MatrixHelper matrixHelper = new MatrixHelper();
    /**
     * Instance of MatrixDisplayLoggerError
     */
    private DisplayLoggerError display = new DisplayLoggerError();
    /**
     * Implementation to find trace of a matrix.
     * 
     * @param matrix
     *            matrix is the input matrix for which trace has o be
     *            calculated.
     * @return trace Returns trace of the input matrix as a double value.
     */
    @Post
    public Double trace(final NamedMatrix matrix) {
        if (matrix == null) {
            display.displayError(MatrixConstants.TRACE_NOTPOSSIBLE,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        Double trace = null;
        RealMatrix realMatrix = matrixHelper.toRealMatrix(matrix);
        if (realMatrix.isSquare()) {
            trace = realMatrix.getTrace();
        } else {
            display.displayError(MatrixConstants.TRACE_NOTPOSSIBLE,
                    MatrixConstants.IS_NOT_SQUARE);
        }
        return trace;
    }
}
