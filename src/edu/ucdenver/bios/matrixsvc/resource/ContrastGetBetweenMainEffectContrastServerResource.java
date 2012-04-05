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

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.matrix.OrthogonalPolynomialContrast;
import edu.cudenver.bios.matrix.OrthogonalPolynomialContrastCollection;
import edu.cudenver.bios.matrix.OrthogonalPolynomials;
import edu.cudenver.bios.utils.Factor;
import edu.ucdenver.bios.matrixsvc.application.MatrixConstants;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;

/**
 * 
 * @author VIJAY AKULA
 *
 */
public class ContrastGetBetweenMainEffectContrastServerResource
extends ServerResource implements ContrastGetBetweenMainEffectContrastResource {
    /**
     * Instance of DisplayLoggerError class
     */
    DisplayLoggerError display = new DisplayLoggerError();
    /**
     * Instance of MatrixHelper class
     */
    MatrixHelper matrixHelper = new MatrixHelper();
    /**
     * Gets the main effect contrast.
     * 
     * @param fullFactorList
     *            the full factor list is the list of all the Between
     *            Participant Factors.
     * @param testFactor
     *            the test facotr list is the list of Between Participant
     *            factors for which Interaction Contrast is generated
     * @return namedMatrix Return a polynomial contrast to test the main effect
     *         of the specified factor. Note that both the full set of factors
     *         and subset of factors to be tested must be specified to ensure a
     *         contrast matrix of appropriate dimension.
     */
    @Override
    public NamedMatrix getBetweenMainEffectContrast(
            final ArrayList<BetweenParticipantFactor> fullFactorList,
            final BetweenParticipantFactor testFactor) {
        if (fullFactorList == null || testFactor == null) {
            display.displayError("", MatrixConstants.NO_INPUT_SPECIFIED);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        ArrayList<Factor> fullFactorArrayList = matrixHelper
                .betweenParticipantFactorsListToFactorList(fullFactorList);

        Factor factor = matrixHelper
                .betweenParticipantFactorToFactor(testFactor);

        OrthogonalPolynomialContrastCollection contrastCollection = OrthogonalPolynomials
                .betweenSubjectContrast(fullFactorArrayList);

        OrthogonalPolynomialContrast contrast = contrastCollection
                .getMainEffectContrast(factor);

        NamedMatrix namedMatrix = matrixHelper.toNamedMatrix(
                contrast.getContrastMatrix(), "");

        return namedMatrix;
    }
}
