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

import java.util.List;

import org.apache.commons.math.linear.RealMatrix;
import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.matrixsvc.application.MatrixConstants;
import edu.ucdenver.bios.matrixsvc.application.MatrixLogger;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrixList;

/**
 * The Implementation of MatrixSubstractionResource.
 * @author VIJAY AKULA
 *
 */
public class MatrixSubtractionServerResource extends ServerResource
implements MatrixSubtractionResource{
    /**
     * Instance of Logger class to dispaly the log msgs.
     */
    private static Logger logger = MatrixLogger.getInstance();
    /**
     * Instance of matrix Helper class.
     */
    private MatrixHelper matrixHelper = new MatrixHelper();
    /**
     * Implementation of differences of Martices
     * 
     * @param matrixList
     * The matrixList is the list of matrices on which
     * subtract operation has to be performed.
     * @return namedMatrix Returns a result of the
     * substraction as Named Matrix
     */
    @Post
    public NamedMatrix subtract(final NamedMatrixList matrixList) {
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
}
