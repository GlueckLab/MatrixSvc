/*
 * Matrix Service for the GLIMMPSE Software System.  Processes
 * incoming HTTP requests for matrix operations like addition, substraction
 * kronecker multiplication and so on
 * 
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package edu.ucdenver.bios.matrixsvc.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.cudenver.bios.matrixsvc.application.MatrixLogger;
import edu.cudenver.bios.utils.Factor;
import edu.ucdenver.bios.matrixsvc.appliaction.MatrixConstants;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.Category;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNode;
import edu.ucdenver.bios.webservice.common.domain.Spacing;

// to-do: Auto-generated Javadoc
/**
 * The Class MatrixHelper.
 * 
 * @author VIJAY AKULA
 */
public class MatrixHelper {

    /** The logger. */
    private static Logger logger = MatrixLogger.getInstance();

    /**
     * This function takes a list of Named Matrices and converts into a list of
     * Real Matrices and returns a List of Real Matrices.
     * 
     * @param matrixList
     *            the matrix list
     * @return List of Real Matrices
     */
    public List<RealMatrix> toRealMatrixList(
            final ArrayList<NamedMatrix> matrixList) {
        if (matrixList == null || matrixList.size() < 2) {
            logger.info(MatrixConstants.MATRIX_LIST_ERROR);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    MatrixConstants.MATRIX_LIST_ERROR);
        }

        List<RealMatrix> realMatrixList = new ArrayList<RealMatrix>();
        for (NamedMatrix namedMatrix : matrixList) {
            realMatrixList.add(toRealMatrix(namedMatrix));
        }
        return realMatrixList;
    }

    /**
     * This method takes Named Matrix and converts it into a RealMatrix and
     * returns a Real Matrix.
     * 
     * @param matrix
     *            the matrix
     * @return Real Matrix
     */
    public RealMatrix toRealMatrix(final NamedMatrix matrix) {
        if (matrix == null) {
            logger.info(MatrixConstants.NO_INPUT_SPECIFIED);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        return new Array2DRowRealMatrix(matrix.getData());
    }

    /**
     * This method takes a Real Matrix and converts it into a Named Matrix and
     * returns that Named Matrix.
     * 
     * @param matrix
     *            the matrix
     * @param name
     *            the name
     * @return Named Matrix
     */
    public NamedMatrix toNamedMatrix(final RealMatrix matrix, final String name) {
        NamedMatrix namedMatrix = new NamedMatrix();
        namedMatrix.setData(matrix.getData());
        namedMatrix.setName(name);
        namedMatrix.setColumns(matrix.getColumnDimension());
        namedMatrix.setRows(matrix.getRowDimension());
        return namedMatrix;
    }

    /**
     * This methods takes ArrayList of BetweenParticipantFactor Objects and
     * converts it to ArrayList of Factor object.
     * 
     * @param betweenParticipantFactorList
     *            the between participant factor list
     * @return the array list
     */
    public ArrayList<Factor> betweenParticipantFactorsListToFactorList(
            final ArrayList<BetweenParticipantFactor>
            betweenParticipantFactorList) {
        ArrayList<Factor> factorArrayList = new ArrayList<Factor>();
        for (BetweenParticipantFactor betweenParticipantFactor :
            betweenParticipantFactorList) {
            Factor factor =
                    betweenParticipantFactorToFactor(
                            betweenParticipantFactor);
            factorArrayList.add(factor);
        }
        return factorArrayList;
    }

    /**
     * This methods takes ArrayList of RepeatedMeasuresNode Objects and converts
     * it to ArrayList of Factor object.
     * 
     * @param repeatedMeasuresNodesArrayList
     *            the repeated measures nodes array list
     * @return the array list
     */
    public ArrayList<Factor> repeatedMeasuresNodesListToFactorList(
            final ArrayList<RepeatedMeasuresNode> repeatedMeasuresNodesArrayList) {
        ArrayList<Factor> factorArrayList = new ArrayList<Factor>();
        for (RepeatedMeasuresNode node : repeatedMeasuresNodesArrayList) {
            factorArrayList.add(repeatedMeasuresNodeToFactor(node));
        }
        return factorArrayList;
    }

    /**
     * This Method takes a BetweenParticipantFactor object and converts it to a
     * Factor Object.
     * 
     * @param betweenParticipantFactor
     *            the between participant factor
     * @return the factor
     */
    public Factor betweenParticipantFactorToFactor(
            final BetweenParticipantFactor betweenParticipantFactor) {
        double[] values = null;
        List<Category> categoryList = betweenParticipantFactor
                .getCategoryList();
        int size = categoryList.size();
        for (int i = 0; i < size; i++) {
            values[i] = i + 1;
        }
        Factor factor = new Factor(betweenParticipantFactor.getPredictorName(),
                values);
        return factor;
    }

    /**
     * This Method takes a RepeatedMeasureNode object and converts it to a
     * Factor Object.
     * 
     * @param node
     *            the node
     * @return Factor Object
     */
    public Factor repeatedMeasuresNodeToFactor(
            final RepeatedMeasuresNode node) {
        List<Spacing> spacingList = node.getSpacingList();
        double[] values = null;
        int index = 0;
        for (Spacing spacing : spacingList) {
            values[index] = spacing.getValue();
            index++;
        }
        Factor factor = new Factor(node.getDimension(), values);
        return factor;
    }

}
