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

import org.apache.commons.math.linear.RealMatrix;
import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;


import edu.cudenver.bios.matrix.OrthogonalPolynomialContrast;
import edu.cudenver.bios.matrix.OrthogonalPolynomialContrastCollection;
import edu.cudenver.bios.matrix.OrthogonalPolynomials;
import edu.cudenver.bios.utils.Factor;
import edu.ucdenver.bios.matrixsvc.application.MatrixConstants;
import edu.ucdenver.bios.matrixsvc.application.MatrixLogger;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNode;

// to-do: Auto-generated Javadoc
/**
 * The Class ContrastServerResource.
 * 
 * @author VIJAY AKULA
 */
public class ContrastServerResource extends ServerResource implements
        ContrastResource {
    /**
     * Instance of Logger class to dispaly the log msgs.
     */
    private static Logger logger = MatrixLogger.getInstance();
    /**
     * Instance of matrix Helper.
     */
    private MatrixHelper matrixHelper = new MatrixHelper();

    /**
     * Gets the Interaction contrast of the Between participant factor list
     * specified
     * 
     * @param fullFactorList
     *            the full factor list is the list of all the Between
     *            Participant Factors
     * @param testFactorList
     *            the test facotr list is the list of Between Participant
     *            factors for which Interaction Contrast is generated
     * @return namedMatrix Returns a polynomial contrast to test the interaction
     *         of the specified factors. Note that both the full set of factors
     *         and subset of factors to be tested must be specified to ensure a
     *         contrast matrix of appropriate dimension.
     */
    @Override
    public NamedMatrix getBetweenInteractionContrast(
            final ArrayList<BetweenParticipantFactor> fullFactorList,
            final ArrayList<BetweenParticipantFactor> testFactorList) {
        if (fullFactorList == null || testFactorList == null) {
            logger.info(MatrixConstants.NO_INPUT_SPECIFIED);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        ArrayList<Factor> fullFactorArrayList = matrixHelper
                .betweenParticipantFactorsListToFactorList(fullFactorList);

        ArrayList<Factor> testFactorArrayList = matrixHelper
                .betweenParticipantFactorsListToFactorList(testFactorList);
        OrthogonalPolynomialContrastCollection contrastCollection = OrthogonalPolynomials
                .betweenSubjectContrast(fullFactorArrayList);

        OrthogonalPolynomialContrast contrast = contrastCollection
                .getInteractionContrast(testFactorArrayList);

        NamedMatrix namedMatrix = matrixHelper.toNamedMatrix(
                contrast.getContrastMatrix(), "");

        return namedMatrix;
    }

    /**
     * Gets the Interaction contrast of the Repeated Measures Node list
     * specified
     * 
     * @param fullFactorList
     *            the full factor list is the list of all the Repeated Measures
     *            Node
     * @param testFactorList
     *            the test facotr list is the list of Repeated Measures Node
     *            factors for which Interaction Contrast is generated
     * @return namedMatrix Return a polynomial contrast to test the interaction
     *         of the specified factors. Note that both the full set of factors
     *         and subset of factors to be tested must be specified to ensure a
     *         contrast matrix of appropriate dimension.
     */
    @Override
    public NamedMatrix getWithinInteractionContrast(
            final ArrayList<RepeatedMeasuresNode> fullFactorList,
            final ArrayList<RepeatedMeasuresNode> testFactorList) {
        if (fullFactorList == null || testFactorList == null) {
            logger.info(MatrixConstants.NO_INPUT_SPECIFIED);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        ArrayList<Factor> fullFactorArrayList = matrixHelper
                .repeatedMeasuresNodesListToFactorList(fullFactorList);

        ArrayList<Factor> testFactorArrayList = matrixHelper
                .repeatedMeasuresNodesListToFactorList(testFactorList);
        OrthogonalPolynomialContrastCollection contrastCollection = OrthogonalPolynomials
                .withinSubjectContrast(fullFactorArrayList);

        OrthogonalPolynomialContrast contrast = contrastCollection
                .getInteractionContrast(testFactorArrayList);

        NamedMatrix namedMatrix = matrixHelper.toNamedMatrix(
                contrast.getContrastMatrix(), "");

        return namedMatrix;
    }

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
            logger.info(MatrixConstants.NO_INPUT_SPECIFIED);
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

    /**
     * Gets the main effect constract.
     * 
     * @param fullFactorList
     *            the full factor list is the list of all the Repeated Measures
     *            Node
     * @param testFactor
     *            the test facotr list is the list of Repeated Measures Node
     *            factors for which Interaction Contrast is generated
     * @return namedMatrix Return a polynomial contrast to test the main effect
     *         of the specified factor. Note that both the full set of factors
     *         and subset of factors to be tested must be specified to ensure a
     *         contrast matrix of appropriate dimension.
     */
    @Override
    public NamedMatrix getWithinMainEffectConstract(
            final ArrayList<RepeatedMeasuresNode> fullFactorList,
            final RepeatedMeasuresNode testFactor) {
        if (fullFactorList == null || testFactor == null) {
            logger.info(MatrixConstants.NO_INPUT_SPECIFIED);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        ArrayList<Factor> fullFactorArrayList = matrixHelper
                .repeatedMeasuresNodesListToFactorList(fullFactorList);

        Factor factor = matrixHelper.repeatedMeasuresNodeToFactor(testFactor);

        OrthogonalPolynomialContrastCollection contrastCollection = OrthogonalPolynomials
                .withinSubjectContrast(fullFactorArrayList);

        OrthogonalPolynomialContrast contrast = contrastCollection
                .getMainEffectContrast(factor);

        NamedMatrix namedMatrix = matrixHelper.toNamedMatrix(
                contrast.getContrastMatrix(), "");

        return namedMatrix;
    }

    /**
     * Gets the between grand mean contrast.
     * 
     * @param fullFactorList
     *            the full factor list is the list of all the Between
     *            Participant Factors.
     * @return Named Matrix
     */
    @Override
    public NamedMatrix getBetweenGrandMeanContrast(
            final ArrayList<BetweenParticipantFactor> fullFactorList) {
        if (fullFactorList == null) {
            logger.info(MatrixConstants.NO_INPUT_SPECIFIED);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        ArrayList<Factor> fullFactorArrayList = matrixHelper
                .betweenParticipantFactorsListToFactorList(fullFactorList);
        OrthogonalPolynomialContrastCollection contrastCollection = OrthogonalPolynomials
                .betweenSubjectContrast(fullFactorArrayList);

        OrthogonalPolynomialContrast contrast = contrastCollection
                .getGrandMean();

        NamedMatrix namedMatrix = matrixHelper.toNamedMatrix(
                contrast.getContrastMatrix(), "");

        return namedMatrix;
    }

    /**
     * Gets the within grand mean contrast.
     * 
     * @param fullFactorList
     *            the full factor list
     * @return Named Matrix
     */
    @Override
    public NamedMatrix getWithinGrandMeanContrast(
            final ArrayList<RepeatedMeasuresNode> fullFactorList) {
        if (fullFactorList == null) {
            logger.info(MatrixConstants.NO_INPUT_SPECIFIED);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        ArrayList<Factor> fullFactorArrayList = matrixHelper
                .repeatedMeasuresNodesListToFactorList(fullFactorList);

        OrthogonalPolynomialContrastCollection contrastCollection = OrthogonalPolynomials
                .withinSubjectContrast(fullFactorArrayList);

        OrthogonalPolynomialContrast contrast = contrastCollection
                .getGrandMean();

        NamedMatrix namedMatrix = matrixHelper.toNamedMatrix(
                contrast.getContrastMatrix(), "");

        return namedMatrix;
    }

    /**
     * Gets the orthogonal polynomial coefficients.
     * 
     * @param x
     *            the x
     * @param maxDegree
     *            the max degree
     * @return Named Matrix
     */
    @Override
    public NamedMatrix getOrthogonalPolynomialCoefficients(final double[] x,
            final int maxDegree) {
        if (x == null) {
            logger.info(MatrixConstants.NO_INPUT_SPECIFIED);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        RealMatrix realMatrix = OrthogonalPolynomials
                .orthogonalPolynomialCoefficients(x, maxDegree);
        NamedMatrix namedMatrix = matrixHelper.toNamedMatrix(realMatrix, "");
        return namedMatrix;
    }
}