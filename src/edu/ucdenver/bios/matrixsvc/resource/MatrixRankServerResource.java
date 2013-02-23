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
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.matrixsvc.application.MatrixConstants;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;

/**
 * The Implementation of MatrixRankResource
 * @author VIJAY AKULA
 *
 */
public class MatrixRankServerResource extends ServerResource
implements MatrixRankResource{
    /**
     * Instance of matrix Helper class.
     */
    private MatrixHelper matrixHelper = new MatrixHelper();
    
    /**
     * Instance of matrix display logger error
     */
    private DisplayLoggerError display = new DisplayLoggerError();
    
    /**
     * Implementation to find rank of matrix.
     * 
     * @param matrix
     *            matrix is the input matrix for which rank
     *            has to be found out.
     * @return rank Returns rank of the input matrix as a Integer value.
     */
    @Post
    public Integer rank(final NamedMatrix matrix) {
        if (matrix == null) {
            display.displayError(MatrixConstants.RANK_NOTPOSSIBLE,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        RealMatrix realMatrix = matrixHelper.toRealMatrix(matrix);
        Integer rank = null;
        SingularValueDecomposition impl =
                new SingularValueDecomposition(realMatrix);
        rank = impl.getRank();
        return rank;
    }
}
