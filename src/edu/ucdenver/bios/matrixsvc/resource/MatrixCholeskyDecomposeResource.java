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

import java.util.ArrayList;

import org.restlet.resource.Post;

import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;

/**
 * The Interface MatrixCholeskyDecomposeResource.
 * @author VIJAY AKULA
 *
 */
public interface MatrixCholeskyDecomposeResource {
    /**
     * Return the Cholesky decomposition A=LL^Tof the matrix. The returned list
     * of matrices will include both components of the decomposition, named “L”
     * and “Ltranspose” respectively.
     * 
     * @param matrix
     *            The matrix is the input matrix on which
     *            choleskyDecompose operation has to be performed.
     * @return NamedMatrix
     */
    @Post
    ArrayList<NamedMatrix> choleskyDecompose(NamedMatrix matrix);
}
