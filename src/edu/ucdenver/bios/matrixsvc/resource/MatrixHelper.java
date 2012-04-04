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


import edu.cudenver.bios.utils.Factor;
import edu.ucdenver.bios.matrixsvc.appliaction.MatrixConstants;
import edu.ucdenver.bios.matrixsvc.appliaction.MatrixLogger;
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
     *            The matrixList is the list of NamedMatrix which are to be
     *            converted RealMatrix
     * @return realMatrixList Returns List of RealMatrix. The NamedMatrix in the
     *         matrixlist are converted to RealMatrix and add to realMatrixList.
     *         This relaMatrixList is returned.
     */
    public List<RealMatrix> toRealMatrixList(
            final ArrayList<NamedMatrix> matrixList) {
        if (matrixList.isEmpty() || matrixList.size() < 2) {
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
     *            The matrix is a input matrix of type NamedMatrix which is to
     *            be converted to type RealMatrix.
     * @return RealMatrix Returns a RealMatrix which is obtained by converting
     *         the input matrix to a RealMatrix.
     */
    public RealMatrix toRealMatrix(final NamedMatrix matrix) {
        if (matrix == null) {
            logger.info(MatrixConstants.NO_INPUT_SPECIFIED);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    MatrixConstants.NO_INPUT_SPECIFIED);
        }
        double[][] d = matrix.getDataFromBlob();
        return new Array2DRowRealMatrix(d);
    }

    /**
     * This method takes a Real Matrix and converts it into a Named Matrix and
     * returns that Named Matrix.
     * 
     * @param matrix
     *            The matrix is a input matrix of type RealMatrix and is to be
     *            converted to a NamedMatrix.
     * @param name
     *            the name is a String, which is to be assigned to named matrix.
     * @return namedMatrix Returns a NamedMatrix which is obtained by converting
     *         the input matrix to NamedMatrix
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
     *            The betweenParticipantFactorList is a ArrayList of
     *            BetweenParticipantFactor which are to converted to an
     *            ArrayList of Factor
     * @return factorArrayList Returns a ArrayList of Factor which is obtained
     *         from converting ArrayList of BetweenParticipantFactor to
     *         ArrayList of Factor.
     */
    public ArrayList<Factor> betweenParticipantFactorsListToFactorList(
            final ArrayList<BetweenParticipantFactor> betweenParticipantFactorList) {
        ArrayList<Factor> factorArrayList = new ArrayList<Factor>();
        for (BetweenParticipantFactor betweenParticipantFactor : betweenParticipantFactorList) {
            Factor factor = betweenParticipantFactorToFactor(betweenParticipantFactor);
            factorArrayList.add(factor);
        }
        return factorArrayList;
    }

    /**
     * This methods takes ArrayList of RepeatedMeasuresNode Objects and converts
     * it to ArrayList of Factor object.
     * 
     * @param repeatedMeasuresNodesList
     *            The repeatedMeasuresNodesList is a ArrayList of
     *            RepeatedMeasuresNode which are to converted to an ArrayList of
     *            Factor
     * @return factorArrayList Returns a ArrayList of Factor which is obtained
     *         from converting ArrayList of RepeatedMeasuresNode to ArrayList of
     *         Factor.
     */
    public ArrayList<Factor> repeatedMeasuresNodesListToFactorList(
            final ArrayList<RepeatedMeasuresNode> repeatedMeasuresNodesList) {
        ArrayList<Factor> factorArrayList = new ArrayList<Factor>();
        for (RepeatedMeasuresNode node : repeatedMeasuresNodesList) {
            factorArrayList.add(repeatedMeasuresNodeToFactor(node));
        }
        return factorArrayList;
    }

    /**
     * This Method takes a BetweenParticipantFactor object and converts it to a
     * Factor Object.
     * 
     * @param betweenParticipantFactor
     *            The betweenParticipantFactor is a object of
     *            BetweenParticipantFactor which is to be converted to a object
     *            of type Factor
     * @return factor Returns factor which is a object of class Factor, obtained
     *         by converting BetweenParticipantFactor object to Factor object
     */
    public Factor betweenParticipantFactorToFactor(
            final BetweenParticipantFactor betweenParticipantFactor) {
        List<Category> categoryList = betweenParticipantFactor
                .getCategoryList();
        int size = categoryList.size();
        double[] values = new double[size];
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
     *            The node is a object of RepeatedMeasuresNode which is to be
     *            converted to a Factor object
     * @return factor Returns a Factor object, which is obtained by converting,
     *         RepeatedMeasuresNode object to a Factor object.
     */
    public Factor repeatedMeasuresNodeToFactor(final RepeatedMeasuresNode node) {
        List<Spacing> spacingList = node.getSpacingList();
        int size = spacingList.size();
        double[] values = new double[size];
        int index = 0;
        for (Spacing spacing : spacingList) {
            values[index] = spacing.getValue();
            index++;
        }
        Factor factor = new Factor(node.getDimension(), values);
        return factor;
    }
}
