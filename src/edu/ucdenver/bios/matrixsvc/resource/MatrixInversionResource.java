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

import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;

/**
 * 
 * @author VIJAY AKULA
 *
 */
public interface MatrixInversionResource {
    /**
     * Return the inverse of the input matrix if possible. Throws an
     * exception for singular matrices.
     * 
     * @param matrix
     *            The matrix is the input matrix on which
     *            inverse operation has to be performed.
     * @return NamedMatrix
     */
    NamedMatrix invert(NamedMatrix matrix);
}
