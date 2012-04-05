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
import org.restlet.resource.ClientResource;

import com.google.gson.Gson;

import edu.ucdenver.bios.matrixsvc.resource.MatrixAdditionServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixCholeskyDecomposeServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixElementwiseMultiplicationServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixHorizontalDirectMultiplyServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixInversionServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixIsPositiveDefiniteServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixKroneckerMultiplyServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixMultiplicationServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixRankServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixScalarMultiplyServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixSubtractionServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixTraceResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixTraceServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixVecServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixVechServerResource;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;

// to-do: Auto-generated Javadoc
/**
 * TestCases for MatrixServerResource class.
 *
 * @author VIJAY AKULA
 */
public class MatrixServerResourcesTestCases extends TestCase {
   
    ClientResource clientResource = null; 
    MatrixResource matrixResource = null;
/*    *//**
     * Instance of MatrixServerResource Class.
     *//*
    private MatrixServerResource resource = new MatrixServerResource();*/
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
        
        try
        {
            clientResource = new ClientResource("http://localhost:8080/matrix/matrix"); 
            matrixResource = clientResource.wrap(MatrixResource.class);
            System.out.println(matrixResource.toString());
        }
        catch (Exception e)
        {
            System.err.println("Failed to connect to server: " + e.getMessage());
            fail();
        }
        
        
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
        MatrixAdditionServerResource resource = new MatrixAdditionServerResource();
        NamedMatrix result = resource.add(sameDimensionNamedMatrixList);
        final double[][] data = {{2, 4, 6 }, {8, 10, 12 } };
        assertNotNull(result);
        assertArrayEquals(data, result.getData().getData());
    }

    /**
     * The test to verify if the add method in MatrixServerResource
     * is performed correctly if invalid inputs are given.
     */
    public final void testInvalidAddition() {
        NamedMatrix result = null;
        MatrixAdditionServerResource resource = new MatrixAdditionServerResource();
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
        MatrixSubtractionServerResource resource = new MatrixSubtractionServerResource();
        NamedMatrix result = resource.subtract(sameDimensionNamedMatrixList);
        final double[][] data = {{0, 0, 0 }, {0, 0, 0 } };
        assertNotNull(result);
        assertArrayEquals(data, result.getData().getData());
    }

    /**
     * The test to verify if the subtract method in MatrixServerResource
     * is performed correctly if invalid inputs are given.
     */
    public final void testInvalidSubstraction() {
        MatrixSubtractionServerResource resource = new MatrixSubtractionServerResource();
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
        MatrixElementwiseMultiplicationServerResource resource =
                new MatrixElementwiseMultiplicationServerResource();
        NamedMatrix result = resource
                .elementWiseMultiply(sameDimensionNamedMatrixList);
        final double[][] data = {{1, 4, 9 }, {16, 25, 36 } };
        assertArrayEquals(data, result.getData().getData());
        assertNotNull(result);
    }

    /**
     * The test to verify if the elementwiseMultiply method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidElementwiseMultiplication() {
        NamedMatrix result = null;
        MatrixElementwiseMultiplicationServerResource resource =
                new MatrixElementwiseMultiplicationServerResource();
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
        MatrixMultiplicationServerResource resource =
                new MatrixMultiplicationServerResource();
        NamedMatrix result = resource
                .multiply(differentDimensionNamedMatrixList);
        final double[][] data = {{30, 36, 42 }, {66, 81, 96 } };
        assertArrayEquals(data, result.getData().getData());
        assertNotNull(result);
    }

    /**
     * The test to verify if the multiply method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidMultiplication() {
        NamedMatrix result = null;
        MatrixMultiplicationServerResource resource =
                new MatrixMultiplicationServerResource();
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
        MatrixHorizontalDirectMultiplyServerResource resource=
                new MatrixHorizontalDirectMultiplyServerResource();
        NamedMatrix result = resource
                .horizontalDirectMultiply(sameDimensionNamedMatrixList);
        final double[][] data = {{1, 2, 3, 2, 4, 6, 3, 6, 9 },
                {16, 20, 24, 20, 25, 30, 24, 30, 36 } };
        assertArrayEquals(data, result.getData().getData());
        assertNotNull(result);
    }

    /**
     * The test to verify if the horizontalDirectMultiply method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidHorizontalDirectMultiply() {
        MatrixHorizontalDirectMultiplyServerResource resource=
                new MatrixHorizontalDirectMultiplyServerResource();
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
        MatrixScalarMultiplyServerResource resource =
                new MatrixScalarMultiplyServerResource();
        NamedMatrix result = resource.scalarMultiply(2.0, namedMatrix1);
        final double[][] data = {{2, 4, 6 }, {8, 10, 12 } };
        assertArrayEquals(data, result.getData().getData());
        assertNotNull(result);
    }

    /**
     * The test to verify if the kroneckerMultiply method in
     * MatrixServerResource is performed correctly if valid
     * inputs are given.
     */
    public final void testValidKroneckerMultiply() {
        MatrixKroneckerMultiplyServerResource resource =
                new MatrixKroneckerMultiplyServerResource();
        NamedMatrix resultSameDimension = resource
                .kroneckerMultiply(sameDimensionNamedMatrixList);
        final double[][] dataSameDimension = {{1, 2, 3, 2, 4, 6, 3, 6, 9 },
                {4, 5, 6, 8, 10, 12, 12, 15, 18 },
                {4, 8, 12, 5, 10, 15, 6, 12, 18 },
                {16, 20, 24, 20, 25, 30, 24, 30, 36 } };
        assertArrayEquals(dataSameDimension, resultSameDimension.getData().getData());
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
                resultDifferentDimension.getData().getData());
        assertNotNull(resultDifferentDimension);
    }

    /**
     * The test to verify if the choleskyDecompose method in
     * MatrixServerResource is performed correctly if valid
     * inputs are given.
     */
    public final void testValidCholeskyDecompose() {
        MatrixCholeskyDecomposeServerResource resource =
                new MatrixCholeskyDecomposeServerResource();
        NamedMatrix singularMatrix = new NamedMatrix();
        final double[][] data = {{4, 2, -2 }, {2, 10, 2 }, {-2, 2, 5 } };
        singularMatrix.setData(data);
        ArrayList<NamedMatrix> resultArrayList = resource
                .choleskyDecompose(singularMatrix);
        final double[][] dataL = {{2, 0, 0 }, {1, 3, 0 },
                {-1, 1, Math.sqrt(3) } };
        final double[][] dataLTranspose = {{2, 1, -1 }, {0, 3, 1 },
                {0, 0, Math.sqrt(3) } };

        assertArrayEquals(dataL, resultArrayList.get(0).getData().getData());
        assertArrayEquals(dataLTranspose, resultArrayList.get(1).getData().getData());
    }

    /**
     * The test to verify if the choleskyDecompose method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidCholeskyDecompose() {
        MatrixCholeskyDecomposeServerResource resource =
                new MatrixCholeskyDecomposeServerResource();
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
       public final void testValidInvert() {
        MatrixInversionServerResource resource =
                new MatrixInversionServerResource();
        NamedMatrix squareMatrix = new NamedMatrix();
        double[][] data = {{2, 1 }, {3, 2 } };
        squareMatrix.setData(data);
        NamedMatrix inverse = resource.invert(squareMatrix);
        double[][] resultData = inverse.getData().getData();
        final double[][] inverseData = {{2, -1 }, {-3, 2 } };
        double delta = 0.0000000000000011;
        for(int  i = 0; i < 2; i++)
        {
            for( int j = 0; j < 2; j++)
            {
                assertEquals(inverseData[i][j], resultData[i][j], delta);
            }
        }
        assertNotNull(inverse);
    }

    /**
     * The test to verify if the invert method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidInvert() {
        MatrixInversionServerResource resource=
                new MatrixInversionServerResource();
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
        MatrixRankServerResource resource =
                new MatrixRankServerResource();
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
        MatrixTraceServerResource resource =
                new MatrixTraceServerResource();
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
        MatrixTraceServerResource resource =
                new MatrixTraceServerResource();
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
        MatrixIsPositiveDefiniteServerResource resource =
                new MatrixIsPositiveDefiniteServerResource();
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
        MatrixIsPositiveDefiniteServerResource resource =
                new MatrixIsPositiveDefiniteServerResource();
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
        MatrixVecServerResource resource = new MatrixVecServerResource();
        NamedMatrix vecMatrix = resource.vec(namedMatrix1);
        final double[][] data = {{1 }, {4 }, {2 }, {5 }, {3 }, {6 } };
        assertArrayEquals(data, vecMatrix.getData().getData());
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
        MatrixVechServerResource resource = new MatrixVechServerResource();
        NamedMatrix symmetricMatrix = new NamedMatrix();
        final double[][] data = {{1, 2, 3 }, {2, 4, 6 }, {3, 6, 5 } };
        symmetricMatrix.setData(data);

        NamedMatrix vechMatric = resource.vech(symmetricMatrix);
        final double[][] expectedData = {{1 }, {2 }, {3 }, {4 }, {6 }, {5 } };
        assertArrayEquals(expectedData, vechMatric.getData().getData());
    }

    /**
     * The test to verify if the vech method in
     * MatrixServerResource is performed correctly if invalid
     * inputs are given.
     */
    public final void testInvalidVech() {
        MatrixVechServerResource resource = new MatrixVechServerResource();
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
    public void testRank()
    {
            NamedMatrix squareMatrix = new NamedMatrix();
            double[][] data = {{2, 1 }, {3, 2 } };
            squareMatrix.setData(data);
            squareMatrix.setColumns(2);
            squareMatrix.setRows(2);
            squareMatrix.setName("ABC");
            System.out.println(squareMatrix.toString());
            int rank = matrixResource.rank(squareMatrix);
            System.out.println("Rank  "+rank);
            /*final double[][] inverseData = {{2, -1 }, {-3, 2 } };
            double delta = 0.0000000000000011;
            for(int  i = 0; i < 2; i++)
            {
                for( int j = 0; j < 2; j++)
                {
                    assertEquals(inverseData[i][j], resultData[i][j], delta);
                }
            }
            assertNotNull(inverse);*/
    }
    
        
}
