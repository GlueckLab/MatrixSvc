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
import edu.cudenver.bios.utils.Factor;
import edu.ucdenver.bios.matrixsvc.resource.ContrastServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixHelper;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.Category;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNode;
import edu.ucdenver.bios.webservice.common.domain.Spacing;

public class ContrastServerResourcesTestCases extends TestCase{
    /**
     * Instance of Contrast Server Resource class.
     */
    private ContrastServerResource resource = 
            new ContrastServerResource();
    /**
     * Instance of a Matrix Helper class.
     */
    private MatrixHelper matrixHelper = 
            new MatrixHelper();
    /**
     * ArrayList of BetweenParticipantFactor to store all the
     * BetweenParticipantFactor objects and convert them to Factor
     * ArrayList.
     */
    private ArrayList<BetweenParticipantFactor> betweenParticipantList =
            new ArrayList<BetweenParticipantFactor>();
    /**
     * ArrayList of BetweenParticipantFactor to store all the
     * RepeatedMeasuresNode objects and convert them to Factor
     * ArrayList.
     */
    private ArrayList<RepeatedMeasuresNode> repeatedMeasuresNodeList = 
            new ArrayList<RepeatedMeasuresNode>();
    /**
     * Instance of a RepeatedeMeasuresNode class.
     */
    private RepeatedMeasuresNode node1 = new RepeatedMeasuresNode();
    
    /**
     * Instance of a BetweenParticipantFactor class.
     */
    private BetweenParticipantFactor participant1 = new BetweenParticipantFactor();
        
    /**
     * This method is exectes after the test case is instantiated.
     */
    
    
    private ArrayList<Factor> betweenParticipantFullFactorList =
            new ArrayList<Factor>();
    
    
    private ArrayList<Factor> repeatedMeasuresNodeFullFactorList =
            new ArrayList<Factor>();
    
    private ArrayList<Factor> betweenParticipantTestFactorList =
            new ArrayList<Factor>();
    
    
    private ArrayList<Factor> repeatedMeasuresNodeTestFactorList =
            new ArrayList<Factor>();
    
    private Factor betweenParticipantTestFactor;
    
    private Factor repeatedMeaeasuresNodeTestFactor;
    
    public final void setUp()
    {
        generateBetweenParticipantFactorList(3);
        participant1 = betweenParticipantList.get(0);
        betweenParticipantFullFactorList = 
                matrixHelper.betweenParticipantFactorsListToFactorList(
                        betweenParticipantList);
        betweenParticipantTestFactor =
                matrixHelper.betweenParticipantFactorToFactor(participant1);
        betweenParticipantList.clear();
        generateBetweenParticipantFactorList(2);
        betweenParticipantTestFactorList = 
                matrixHelper.betweenParticipantFactorsListToFactorList(
                        betweenParticipantList);
        
        generateRepeatedMeasureNodeList(3);
        node1 = repeatedMeasuresNodeList.get(0);
        repeatedMeasuresNodeFullFactorList =
                matrixHelper.repeatedMeasuresNodesListToFactorList(
                        repeatedMeasuresNodeList);
        repeatedMeaeasuresNodeTestFactor =
                matrixHelper.repeatedMeasuresNodeToFactor(node1);
        repeatedMeasuresNodeList.clear();
        generateRepeatedMeasureNodeList(2);
        repeatedMeasuresNodeTestFactorList =
                matrixHelper.repeatedMeasuresNodesListToFactorList(
                        repeatedMeasuresNodeList);
    }
    /**
     * This method is used to generate BetweenParticipantFactors list
     * @param x
     * the x is size of the list to be generated.
     */
    public void generateBetweenParticipantFactorList(int x)
    {
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
            
            List<Category> categoryList = new ArrayList<Category>();
            categoryList.add(category1);
            categoryList.add(category2);
            categoryList.add(category3);
            
            BetweenParticipantFactor participant =
                    new BetweenParticipantFactor();
            participant.setCategoryList(categoryList);
            participant.setId(i);
            participant.setPredictorName("Participant "+(i+1));
            
            betweenParticipantList.add(participant);
        }
    }
    /**
     * This method is used to generate List of RepeatedMeasuresNode list
     * @param x
     * the x is size of the Repeated Measures Node list.
     */
    public void generateRepeatedMeasureNodeList(int x)
    {
        for(int i = 0; i < x; i++)
        {
            RepeatedMeasuresNode node = new RepeatedMeasuresNode();
            node.setDimension("Node"+(i+1));
            node.setSpacingList(generateSpacingList(i+1));
            repeatedMeasuresNodeList.add(node);
        }
    }
    /**
     * This method is to generate spacingList for
     * RepeatedMeasureNode object.
     * @param x
     * the x is size of the spacingList.
     * @return spacingList Returns a List of Integers
     */
    public List<Spacing> generateSpacingList(int x)
    {
        List<Spacing> spacingList =
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
        
    }
    /**
     * This is the test case to verify getWithinInteractionContrast method
     * in ContrastServerResource class.
     */
    public void testGetWithinInteractionContrast()
    {
        
    }
    /**
     * This is the test case to verify getMainEffectContrast method
     * in ContrastServerResource class.
     */
    public void testGetMainEffectContrast(ArrayList<BetweenParticipantFactor>
    betweenParticipantFullFactorList,
            BetweenParticipantFactor betweenParticipantTestFactor)
    {
        
    }
    /**
     * 
     * @param repeatedMeasuresNodeFullFactorList
     * @param repeatedMeaeasuresNodeTestFactor
     */
    public void testGetMainEffectContrast(ArrayList<RepeatedMeasuresNode>
    repeatedMeasuresNodeFullFactorList, RepeatedMeasuresNode repeatedMeaeasuresNodeTestFactor)
    {
        
    }
    /**
     * This is the test case to verify getBetweenGrandMeanContrast method
     * in ContrastServerResource class.
     */
    public void testGetBetweenGrandMeanContrast()
    {
        
    }
    /**
     * This is the test case to verify getWithinGrandMeanContrast method
     * in ContrastServerResource class.
     */
    public void testGetWithinGrandMeanContrast()
    {
        
    }
    
    /**
     * This is the test case to verfiy getOrthogonalPolynomialCoefficiens
     * method in ContrastServerResource class.
     */
    public void testGetOrthogonalPolynomialCoefficients()
    {
        
    }
}
