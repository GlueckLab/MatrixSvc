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
/**
 * @author VIJAY AKULA
 */
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.restlet.resource.ClientResource;

import edu.ucdenver.bios.matrixsvc.resource.ContrastGetBetweenGrandMeanContrastServerResource;
import edu.ucdenver.bios.matrixsvc.resource.ContrastGetBetweenInteractionContrastServerResource;
import edu.ucdenver.bios.matrixsvc.resource.ContrastGetBetweenMainEffectContrastServerResource;
import edu.ucdenver.bios.matrixsvc.resource.ContrastGetWithinGrandMeanContrastServerResource;
import edu.ucdenver.bios.matrixsvc.resource.ContrastGetWithinInteractionContrastServerResource;
import edu.ucdenver.bios.matrixsvc.resource.ContrastGetWithinMainEffectContrastServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixResource;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.Category;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNode;
import edu.ucdenver.bios.webservice.common.domain.Spacing;

public class ContrastServerResourcesTestCases extends TestCase{
    /**
     * ArrayList of BetweenParticipantFactor to store all the
     * BetweenParticipantFactor objects and convert them to Factor
     * ArrayList.
     */
    private ArrayList<BetweenParticipantFactor> betweenParticipantList = null;
    /**
     * ArrayList of BetweenParticipantFactor to store all the
     * RepeatedMeasuresNode objects and convert them to Factor
     * ArrayList.
     */
    private ArrayList<RepeatedMeasuresNode> repeatedMeasuresNodeList = null;
    /**
     * ArrayList of BetweenParticipantFactor to store all the
     * BetweenParticipantFactor objects and convert them to Factor
     * ArrayList.
     */
    private ArrayList<BetweenParticipantFactor> betweenParticipantTestFactorList = null;
    /**
     * ArrayList of BetweenParticipantFactor to store all the
     * RepeatedMeasuresNode objects and convert them to Factor
     * ArrayList.
     */
    private ArrayList<RepeatedMeasuresNode> repeatedMeasuresNodeTestFactorList = null;
    
    /**
     * Instance of a RepeatedeMeasuresNode class.
     */
    private RepeatedMeasuresNode node = new RepeatedMeasuresNode();
    
    /**
     * Instance of a BetweenParticipantFactor class.
     */
    private BetweenParticipantFactor participant = new BetweenParticipantFactor();
        
    /**
     * This method is exectes after the test case is instantiated.
     */
    ClientResource clientResource = null;
    MatrixResource matrixResource = null;
    
    public final void setUp()
    {
        betweenParticipantList = generateBetweenParticipantFactorList(3);
        System.out.println(betweenParticipantList.toString());
        participant = betweenParticipantList.get(0);
        System.out.println(participant.toString());
        betweenParticipantTestFactorList = generateBetweenParticipantFactorList(2);
        System.out.println(betweenParticipantTestFactorList.toString());
        repeatedMeasuresNodeList = generateRepeatedMeasureNodeList(3);
        System.out.println(repeatedMeasuresNodeList.toString());
        node = repeatedMeasuresNodeList.get(0);
        System.out.println(node.toString());
        repeatedMeasuresNodeTestFactorList = generateRepeatedMeasureNodeList(2);
        System.out.println(repeatedMeasuresNodeTestFactorList.toString());
        
        
        try
        {
            clientResource = new ClientResource("http://localhost:8080/power/power"); 
            //clientResource = new ClientResource("http://sph-bi-sakhadeo:8080/power/power"); 
            matrixResource = clientResource.wrap(MatrixResource.class);
        }
        catch (Exception e)
        {
            System.err.println("Failed to connect to server: " + e.getMessage());
            fail();
        }

    }
    /**
     * This method is used to generate BetweenParticipantFactors list
     * @param x
     * the x is size of the list to be generated.
     */
    public ArrayList<BetweenParticipantFactor> generateBetweenParticipantFactorList(int x)
    {
        ArrayList<BetweenParticipantFactor> arrayList = new ArrayList<BetweenParticipantFactor>();
        for(int i = 0; i < x; i++)
        {
            Category category1 = new Category();
            category1.setCategory("Category1");
            category1.setId(1);
            Category category2 = new Category();
            category2.setCategory("Category2");
            category2.setId(2);
            Category category3 = new Category();
            category3.setCategory("Category3");
            category3.setId(3);
            
            ArrayList<Category> categoryList = new ArrayList<Category>();
            categoryList.add(category1);
            categoryList.add(category2);
            categoryList.add(category3);
            
            BetweenParticipantFactor participant =
                    new BetweenParticipantFactor();
            participant.setCategoryList(categoryList);
            participant.setPredictorName("Participant "+(i+1));
            
            arrayList.add(participant);
        }
        return arrayList;
    }
    /**
     * This method is used to generate List of RepeatedMeasuresNode list
     * @param x
     * the x is size of the Repeated Measures Node list.
     */
    public ArrayList<RepeatedMeasuresNode> generateRepeatedMeasureNodeList(int x)
    {
        ArrayList<RepeatedMeasuresNode> arrayList = new ArrayList<RepeatedMeasuresNode>();
        for(int i = 0; i < x; i++)
        {
            RepeatedMeasuresNode node = new RepeatedMeasuresNode();
            node.setId(i);
            node.setNumberOfMeasurements(i+1);
            node.setDimension("Node"+(i+1));
            node.setSpacingList(generateSpacingList(i+1));
            arrayList.add(node);
        }
        return arrayList;
    }
    /**
     * This method is to generate spacingList for
     * RepeatedMeasureNode object.
     * @param x
     * the x is size of the spacingList.
     * @return spacingList Returns a List of Integers
     */
    public ArrayList<Spacing> generateSpacingList(int x)
    {
        ArrayList<Spacing> spacingList =
                new ArrayList<Spacing>();
        for(int i = 0; i < x; i++)
        {
            Spacing spacing = new Spacing();
            spacing.setId(i+1);
            spacing.setValue((int) Math.pow(i, 2));
            spacingList.add(spacing);
        }
        return spacingList;
    }
    
    /**
     * This is the test case to verify getBetweenInteractionContrast method
     * in ContrastServerResource class.
     */
    public void testGetBetweenInteractionContrast()
    {
        ContrastGetBetweenInteractionContrastServerResource resource
        = new ContrastGetBetweenInteractionContrastServerResource();
        NamedMatrix namedMatrix = resource.getBetweenInteractionContrast(
                betweenParticipantList, betweenParticipantTestFactorList);
        System.out.println("Rows "+namedMatrix.getRows());
        System.out.println("Columns "+namedMatrix.getColumns());
        assertEquals(27, namedMatrix.getColumns());
        
        
    }
    /**
     * This is the test case to verify getWithinInteractionContrast method
     * in ContrastServerResource class.
     */
    public void testGetWithinInteractionContrast()
    {
        ContrastGetWithinInteractionContrastServerResource resource =
                new ContrastGetWithinInteractionContrastServerResource();
        NamedMatrix namedMatrix = resource.getWithinInteractionContrast(
                repeatedMeasuresNodeList, repeatedMeasuresNodeTestFactorList);
        System.out.println("Rows "+namedMatrix.getRows());
        System.out.println("Columns "+namedMatrix.getRows());
        assertEquals(27, namedMatrix.getColumns());
    }
    /**
     * This is the test case to verify getMainEffectContrast method
     * in ContrastServerResource class.
     */
    public void testGetBetweenMainEffectContrastBetweenParticipantFactors()
    {
        ContrastGetBetweenMainEffectContrastServerResource resource =
                new ContrastGetBetweenMainEffectContrastServerResource();
        NamedMatrix namedMatrix = resource.getBetweenMainEffectContrast(
                betweenParticipantList, participant );
        System.out.println("Rows "+namedMatrix.getRows());
        System.out.println("Columns "+namedMatrix.getColumns());
        assertEquals(27, namedMatrix.getColumns());
    }
    /**
     * 
     * @param repeatedMeasuresNodeFullFactorList
     * @param repeatedMeaeasuresNodeTestFactor
     */
    public void testGetWithinMainEffectContrastRepeatedMeasuresNode()
    {
        ContrastGetWithinMainEffectContrastServerResource resource
        = new ContrastGetWithinMainEffectContrastServerResource();
        NamedMatrix namedMatrix = resource.getWithinMainEffectConstract(
                repeatedMeasuresNodeList, node);
        System.out.println("Rows "+namedMatrix.getRows());
        System.out.println("Columns "+namedMatrix.getRows());
        assertEquals(27, namedMatrix.getColumns());
    }
    /**
     * This is the test case to verify getBetweenGrandMeanContrast method
     * in ContrastServerResource class.
     */
    public void testGetBetweenGrandMeanContrast()
    {
        ContrastGetBetweenGrandMeanContrastServerResource resource =
                new ContrastGetBetweenGrandMeanContrastServerResource();
        NamedMatrix namedMatrix = resource.getBetweenGrandMeanContrast(
                betweenParticipantList);
        System.out.println("Rows "+namedMatrix.getRows());
        System.out.println("Columns "+namedMatrix.getColumns());
        assertEquals(27, namedMatrix.getColumns());
        
    }
    /**
     * This is the test case to verify getWithinGrandMeanContrast method
     * in ContrastServerResource class.
     */
    public void testGetWithinGrandMeanContrast()
    {
        ContrastGetWithinGrandMeanContrastServerResource resource =
                new ContrastGetWithinGrandMeanContrastServerResource();
        NamedMatrix namedMatrix = resource.getWithinGrandMeanContrast(
                repeatedMeasuresNodeList);
        System.out.println("Rows "+namedMatrix.getRows());
        System.out.println("Columns "+namedMatrix.getRows());
        assertEquals(27, namedMatrix.getColumns());
    }
    
 
}
