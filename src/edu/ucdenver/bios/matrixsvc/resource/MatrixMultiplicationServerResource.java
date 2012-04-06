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
import java.util.List;

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
public class MatrixMultiplicationServerResource extends ServerResource
implements MatrixMultiplicationResource{
    /**
     * Instance of matrix Helper class.
     */
    private MatrixHelper matrixHelper = new MatrixHelper();
    
    /**
     * Instance of matrix display logger error
     */
    private DisplayLoggerError display = new DisplayLoggerError();
    /**
     * Implementation of multiplication of Matrices.
     * 
     * @param matrixList
     *            The matrixList is the list of matrices on which
     *            multiplication operation has to be performed.
     * @return namedMatrix Returns the result of multiplication
     * as a NamedMatrix
     */
    @Post
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
                display.displayError(MatrixConstants.MULTIPLICATION_NOTPOSSIBLE, "");
            }
        }
        NamedMatrix matrix = matrixHelper.toNamedMatrix(productMatrix,
                MatrixConstants.MULTIPLICATION_MATRIX_RETURN_NAME);
        return matrix;
    }


}
