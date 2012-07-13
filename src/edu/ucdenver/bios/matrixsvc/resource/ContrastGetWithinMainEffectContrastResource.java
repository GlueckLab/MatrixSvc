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
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNode;

/**
 * The Interface ContrastGetWithinMainEffectContrastResource.
 * @author VIJAY AKULA
 *
 */
public interface ContrastGetWithinMainEffectContrastResource {
    /**
     * Return a polynomial contrast to test the main effect of the specified
     * factor. Note that both the full set of factors and subset of factors to
     * be tested must be specified to ensure a contrast matrix of appropriate
     * dimension.
     * 
     * @param fullFactorList
     *            The fullFactorList is the ArrayList of RepeatedMeasuresNode
     *            objects which are to be converted to Factor Objects and a full
     *            contrast collection is generated using this fullFactorList.
     * @param testFactor
     *            The testFactor is an object of RepeatedMeasuresNode which is
     *            converted to a Factor object and a main effect contrast is
     *            obtained based on this factor object.
     * 
     * @return NamedMatrix Returns a main effect contrast matrix based on the
     *         testFator object. The contrast matrix is of type NamedMatrix
     */
    @Post
    NamedMatrix getWithinMainEffectConstract(
            ArrayList<RepeatedMeasuresNode> fullFactorList,
            RepeatedMeasuresNode testFactor);
}
