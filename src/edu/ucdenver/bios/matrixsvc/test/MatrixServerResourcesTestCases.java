/*
 * Matrix Service for the GLIMMPSE Software System.  Processes
 * incoming HTTP requests for matrix operations like addition, substraction
 * kronecker multiplication and so on
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

package edu.ucdenver.bios.matrixsvc.test;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gson.Gson;

import edu.ucdenver.bios.matrixsvc.resource.MatrixServerResource;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;

// to-do: Auto-generated Javadoc
/**
 * TestCases for MatrixServerResource class.
 *
 * @author VIJAY AKULA
 */
public class MatrixServerResourcesTestCases extends TestCase {
    /**
     * Instance of Matrix Helper Class.
     */
    private MatrixServerResource resource = new MatrixServerResource();
    /**
     * ArrayList of NamedMatrix to store same dimension matrix.
     */
    private ArrayList<NamedMatrix> sameDimensionNamedMatrixList;
    /**
     * ArrayList of NamedMatrix to store different dimension matrices.
     */
    private ArrayList<NamedMatrix> differentDimensionNamedMatrixList;
    /**
     * Instance of a Named Matrix.
     */
    private NamedMatrix namedMatrix1 = new NamedMatrix();
    /**
     * Instance of a Named Matrix.
     */
    private NamedMatrix namedMatrix4 = new NamedMatrix();

    /**
     * This method is exectes after the test case is instantiated.
     */
    public final void setUp() {
        final int rows = 2;
        final int columns = 3;
        sameDimensionNamedMatrixList = new ArrayList<NamedMatrix>();
        namedMatrix1.setData(generateData(rows, columns));
        sameDimensionNamedMatrixList.add(namedMatrix1);
        NamedMatrix namedMatrix2 = new NamedMatrix();
        namedMatrix2.setData(generateData(rows, columns));
        sameDimensionNamedMatrixList.add(namedMatrix2);
        differentDimensionNamedMatrixList = new ArrayList<NamedMatrix>();
        NamedMatrix namedMatrix3 = new NamedMatrix();
        final int rows3 = 2;
        final int columns3 = 3;
        namedMatrix3.setData(generateData(rows3, columns3));
        differentDimensionNamedMatrixList.add(namedMatrix3);
        final int rows4 = 3;
        final int columns4 = 3;
        namedMatrix4.setData(generateData(rows4, columns4));
        differentDimensionNamedMatrixList.add(namedMatrix4);
    }

    /**
     * This method generated data needed for an example matrix.
     * 
     * @param rows
     *            - number of rows in a matrix to be generated.
     * @param columns
     *            - number of columns in a matrix to be generated.
     * @return double[][]
     */
    private double[][] generateData(final int rows, final int columns) {
        double[][] data = new double[rows][columns];
        int value = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[i][j] = value;
                value = value + 1;
            }
        }
        return data;
    }

    /**
     * The test to verify if the add method in MatrixServerResource
     * is performed correctly if valid inputs are given.
     */
    @Test
    public final void testValidAddition() {
        NamedMatrix result = resource.add(sameDimensionNamedMatrixList);
        final double[][] data = {{2, 4, 6 }, {8, 10, 12 } };
        assertNotNull(result);
        assertArrayEquals(data, result.getData());
    }

    /**
     * The test to verify if the add method in MatrixServerResource
     * is performed correctly if invalid inputs are given.
     */
    public final void testInvalidAddition() {
        NamedMatrix result = null;
        try {
            result = resource.add(differentDimensionNamedMatrixList);
            fail();
            }
        catch (Exception e) {
            assertTrue("Throws Exception at add function in Matrix Helper",
                    result == null);
            System.out.println("Throws Exception at"
                    + " add function in Matrix Server Resources Class");
        }
    }

    /**
     * The test to verify if the subtract method in MatrixServerResource
     * is performed correctly if valid inputs are given.
     */
    public final void testValidSubstraction() {
        NamedMatrix result = resource.subtract(sameDimensionNamedMatrixList);
        final double[][] data = {{0, 0, 0 }, {0, 0, 0 } };
        assertNotNull(result);
        assertArrayEquals(data, result.getData());
    }

    /**
     * The test to verify if the subtract method in MatrixServerResource
     * is performed correctly if invalid inputs are given.
     */
    public final void testInvalidSubstraction() {
        NamedMatrix result = null;
        try {
            result = resource.subtract(differentDimensionNamedMatrixList);
            fail();
        } catch (Exception e) {
            assertTrue("Throws Exception at substraction "
            + "function in Matrix Server Resource class",
                    result == null);
            System.out.println("Throws Exception at subtract "
                    + "function in Matrix Server Resources Class");
        }
    }

    /**
     * The test to verify if the elementwiseMultiply method in
     * MatrixServerResource is performed correctly if valid
     * inputs are given.
     */
    public final void testValidElementwiseMultiplication() {
        NamedMatrix result = resource
                .elementWiseMultiply(sameDimensionNamedMatrixList);
        final double[][] data = {{1, 4, 9 }, {16, 25, 36 } };
        assertArrayEquals(data, result.getData());
        assertNotNull(result);
    }

    /**
     * The test to verify if the elementwiseMultiply method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidElementwiseMultiplication() {
        NamedMatrix result = null;
        try {
            result = resource
                    .elementWiseMultiply(differentDimensionNamedMatrixList);
            fail();
        } catch (Exception e) {
            assertTrue(
                    "Throws Exception at elementwiseMultiply "
                    + "function in Matrix Server Resources Class",
                    result == null);
            System.out
                    .println("Throws Exception at elementwiseMultiply "
                    + "function in Matrix Server Resources Class");
        }
    }

    /**
     * The test to verify if the multiply method in
     * MatrixServerResource is performed correctly if valid
     * inputs are given.
     */
    public final void testValidMultiplication() {
        NamedMatrix result = resource
                .multiply(differentDimensionNamedMatrixList);
        final double[][] data = {{30, 36, 42 }, {66, 81, 96 } };
        assertArrayEquals(data, result.getData());
        assertNotNull(result);
    }

    /**
     * The test to verify if the multiply method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidMultiplication() {
        NamedMatrix result = null;
        try {
            result = resource.multiply(sameDimensionNamedMatrixList);
            fail();
        } catch (Exception e) {
            assertTrue("Throws Exception at multiply "
                    + "function in Matrix Server Resources Class",
                    result == null);
            System.out.println("Throws Exception at multiply function"
                    + " in Matrix Server Resources Class");
        }
    }

    /**
     * The test to verify if the horizontalDirectMultiply method in
     * MatrixServerResource is performed correctly if valid
     * inputs are given.
     */
    public final void testValidHorizontalDirectMultiply() {
        NamedMatrix result = resource
                .horizontalDirectMultiply(sameDimensionNamedMatrixList);
        final double[][] data = {{1, 2, 3, 2, 4, 6, 3, 6, 9 },
                {16, 20, 24, 20, 25, 30, 24, 30, 36 } };
        assertArrayEquals(data, result.getData());
        assertNotNull(result);
    }

    /**
     * The test to verify if the horizontalDirectMultiply method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidHorizontalDirectMultiply() {
        NamedMatrix result = null;
        try {
            result = resource.horizontalDirectMultiply(
                    differentDimensionNamedMatrixList);
            fail();
        } catch (Exception e) {
            assertTrue("Throws Exception at "
                    + "horizontalDirectProduct function in"
                    + " Matrix Server Resources Class", result == null);
            System.out.println("Throws Exception at horizontalDirectProduct "
                    + "function in Matrix Server Resources Class");
        }
    }

    /**
     * The test to verify if the scalarMultiply method in
     * MatrixServerResource is performed correctly if valid
     * inputs are given.
     */
    public final void testValidScalarMultiply() {
        NamedMatrix result = resource.scalarMultiply(2.0, namedMatrix1);
        final double[][] data = {{2, 4, 6 }, {8, 10, 12 } };
        assertArrayEquals(data, result.getData());
        assertNotNull(result);
    }

    /**
     * The test to verify if the kroneckerMultiply method in
     * MatrixServerResource is performed correctly if valid
     * inputs are given.
     */
    public final void testValidKroneckerMultiply() {
        NamedMatrix resultSameDimension = resource
                .kroneckerMultiply(sameDimensionNamedMatrixList);
        final double[][] dataSameDimension = {{1, 2, 3, 2, 4, 6, 3, 6, 9 },
                {4, 5, 6, 8, 10, 12, 12, 15, 18 },
                {4, 8, 12, 5, 10, 15, 6, 12, 18 },
                {16, 20, 24, 20, 25, 30, 24, 30, 36 } };
        assertArrayEquals(dataSameDimension, resultSameDimension.getData());
        assertNotNull(resultSameDimension);

        NamedMatrix resultDifferentDimension = resource
                .kroneckerMultiply(differentDimensionNamedMatrixList);
        final double[][] dataDifferentDimension = {{1, 2, 3, 2, 4, 6, 3, 6, 9 },
                {4, 5, 6, 8, 10, 12, 12, 15, 18 },
                {7, 8, 9, 14, 16, 18, 21, 24, 27 },
                {4, 8, 12, 5, 10, 15, 6, 12, 18 },
                {16, 20, 24, 20, 25, 30, 24, 30, 36 },
                {28, 32, 36, 35, 40, 45, 42, 48, 54 } };
        assertArrayEquals(dataDifferentDimension,
                resultDifferentDimension.getData());
        assertNotNull(resultDifferentDimension);
    }

    /**
     * The test to verify if the choleskyDecompose method in
     * MatrixServerResource is performed correctly if valid
     * inputs are given.
     */
    public final void testValidCholeskyDecompose() {
        NamedMatrix singularMatrix = new NamedMatrix();
        final double[][] data = {{4, 2, -2 }, {2, 10, 2 }, {-2, 2, 5 } };
        singularMatrix.setData(data);
        ArrayList<NamedMatrix> resultArrayList = resource
                .choleskyDecompose(singularMatrix);
        final double[][] dataL = {{2, 0, 0 }, {1, 3, 0 },
                {-1, 1, Math.sqrt(3) } };
        final double[][] dataLTranspose = {{2, 1, -1 }, {0, 3, 1 },
                {0, 0, Math.sqrt(3) } };

        assertArrayEquals(dataL, resultArrayList.get(0).getData());
        assertArrayEquals(dataLTranspose, resultArrayList.get(1).getData());
    }

    /**
     * The test to verify if the choleskyDecompose method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidCholeskyDecompose() {
        ArrayList<NamedMatrix> namedMatrixList = null;
        try {
            namedMatrixList = new ArrayList<NamedMatrix>();
            namedMatrixList = resource.choleskyDecompose(namedMatrix1);
            fail();
        } catch (Exception e) {
            namedMatrixList = null;
            assertTrue("Throws Exception at " + "choleskyDecompose function in"
                    + " Matrix Server Resources Class",
                    namedMatrixList == null);
            System.out.println("Throws Exception at choleskyDecompose "
                    + "function in Matrix Server Resources Class");
        }
    }

    /**
     * The test to verify if the invert method in
     * MatrixServerResource is performed correctly if valid
     * inputs are given.
     */
    private final void testValidInvert() {
        NamedMatrix squareMatrix = new NamedMatrix();
        final double[][] data = {{2, 1 }, {3, 2 } };
        squareMatrix.setData(data);
        NamedMatrix inverse = resource.invert(squareMatrix);
        final double[][] inverseData = {{2, -1 }, {-3, 2 } };

        assertArrayEquals(inverseData, inverse.getData());
        assertNotNull(inverse);
    }

    /**
     * The test to verify if the invert method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidInvert() {
        NamedMatrix inverseMatrix = null;
        try {
            inverseMatrix = resource.invert(namedMatrix1);
            fail();
        } catch (Exception e) {
            assertTrue("Throws Exception at " + "invert function in"
                    + " Matrix Server Resources Class",
                    inverseMatrix == null);
            System.out.println("Throws Exception at invert "
                    + "function in Matrix Server Resources Class");
        }
    }

    /**
     * The test to verify if the rank method in
     * MatrixServerResource is performed correctly if valid
     * inputs are given.
     */
    public final void testValidRank() {
        NamedMatrix rankMatrix = new NamedMatrix();
        final double[][] data = {{2, -1, 3 }, {2, -1, 3 }, {0, 2, -1 },
                {1, 1, 4 } };
        rankMatrix.setData(data);
        int rank = resource.rank(rankMatrix);
        final int matrixRank = 3;
        assertEquals(matrixRank, rank);
        assertNotNull(rank);
    }

    /**
     * The test to verify if the rank method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidRank() {

    }

    /**
     * The test to verify if the trace method in
     * MatrixServerResource is performed correctly if valid
     * inputs are given.
     */
    public final void testValidTrace() {
        double trace = resource.trace(namedMatrix4);
        final double expectedTrace = 15.0;
        assertEquals(expectedTrace, trace);
        assertNotNull(trace);
    }

    /**
     * The test to verify if the trace method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidTrace() {
        double trace;
        try {
            trace = resource.trace(namedMatrix1);
            fail();
        } catch (Exception e) {
            assertTrue("Throws Exception at " + "trace function in"
                    + " Matrix Server Resources Class", true);
            System.out.println("Throws Exception at trace "
                    + "function in Matrix Server Resources Class");
        }
    }

    /**
     * The test to verify if the isPositiveDefinite method in
     * MatrixServerResource is performed correctly if valid
     * inputs are given.
     */
    public final void testValidIsPositiveDefinite() {
        NamedMatrix positiveDefiniteMatrix = new NamedMatrix();
        final double[][] data = {{2, -1, 0 }, {-1, 2, -1 }, {0, -1, 2 } };
        positiveDefiniteMatrix.setData(data);
        boolean isPositiveDefinite = resource
                .isPositiveDefinite(positiveDefiniteMatrix);

        boolean flag = true;
        assertEquals(flag, isPositiveDefinite);
    }

    /**
     * The test to verify if the isPositiveDefinite method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidIsPositiveDefinite() {
        boolean flag = false;
        try {
            flag = resource.isPositiveDefinite(namedMatrix1);
            flag = true;
            fail();
        } catch (Exception e) {
            assertTrue("Throws Exception at "
                    + "isPositiveDefinite function in"
                    + " Matrix Server Resources Class", !flag);
            System.out.println("Throws Exception at isPositiveDefinite "
                    + "function in Matrix Server Resources Class");
        }
    }

    /**
     * The test to verify if the vec method in
     * MatrixServerResource is performed correctly if valid
     * inputs are given.
     */
    public final void testValidVec() {
        NamedMatrix vecMatrix = resource.vec(namedMatrix1);
        final double[][] data = {{1 }, {4 }, {2 }, {5 }, {3 }, {6 } };
        assertArrayEquals(data, vecMatrix.getData());
    }

    /**
     * The test to verify if the vec method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidVec() {

    }

    /**
     * The test to verify if the vech method in
     * MatrixServerResource is performed correctly if valid
     * inputs are given.
     */
    public final void testValidVech() {
        NamedMatrix symmetricMatrix = new NamedMatrix();
        final double[][] data = {{1, 2, 3 }, {2, 4, 6 }, {3, 6, 5 } };
        symmetricMatrix.setData(data);

        NamedMatrix vechMatric = resource.vech(symmetricMatrix);
        final double[][] expectedData = {{1 }, {2 }, {3 }, {4 }, {6 }, {5 } };
        assertArrayEquals(expectedData, vechMatric.getData());
    }

    /**
     * The test to verify if the vech method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidVech() {
        NamedMatrix vech = null;
        try {
            vech = resource.vech(namedMatrix4);
            Gson gson = new Gson();
            String json = gson.toJson(vech);
            System.out.println(json);

        } catch (Exception e) {
            assertTrue("Throws Exception at " + "vech function in"
                    + " Matrix Server Resources Class", vech == null);
            System.out.println("Throws Exception at vech "
                    + "function in Matrix Server Resources Class");
        }
    }
}
