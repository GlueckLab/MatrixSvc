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

import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNode;

/**
 * 
 * @author Vijay Akula
 *
 */
public interface ContrastResource 
{
	/**
	 * Return a polynomial contrast to test the interaction of the specified 
	 * factors.  Note that both the full set of factors and subset of factors 
	 * to be tested must be specified to ensure a contrast matrix of  
	 * appropriate dimension.
	 * @param fullFactorList
	 * @param testFactorList
	 * @return
	 */
	public NamedMatrix getBetweenInteractionContrast( 
			ArrayList<BetweenParticipantFactor> fullFactorList,
			ArrayList<BetweenParticipantFactor> testFactorList);
	
	/**
	 * Return a polynomial contrast to test the interaction of the specified 
	 * factors.  Note that both the full set of factors and subset of factors 
	 * to be tested must be specified to ensure a contrast matrix of 
	 * appropriate dimension.
	 * @param fullFactorList
	 * @param testFactorList
	 * @return
	 */
	public NamedMatrix getWithinInteractionContrast(
			ArrayList<RepeatedMeasuresNode> fullFactorList,
			ArrayList<RepeatedMeasuresNode> testFactorList);
	
	/**
	 * Return a polynomial contrast to test the main effect of the specified 
	 * factor.  Note that both the full set of factors and subset of factors 
	 * to be tested must be specified to ensure a contrast matrix of 
	 * appropriate dimension.
	 * @param fullFactorialList
	 * @param testFactor
	 * @return
	 */
	public NamedMatrix getMainEffectContrast (
			ArrayList<BetweenParticipantFactor> fullFactorialList,
			BetweenParticipantFactor testFactor);
	
	/**
	 * Return a polynomial contrast to test the main effect of the specified 
	 * factor.  Note that both the full set of factors and subset of factors 
	 * to be tested must be specified to ensure a contrast matrix of 
	 * appropriate dimension.
	 * @param fullFactorList
	 * @param testFactor
	 * @return
	 */
	public NamedMatrix getMainEffectConstract(
			ArrayList<RepeatedMeasuresNode> fullFactorList,
			RepeatedMeasuresNode testFactor);
	
	/**
	 * Return a polynomial contrast to test the grand mean for the 
	 * specified list of factors.
	 * @param fullFactorList
	 * @return
	 */
	public NamedMatrix getBetweenGrandMeanContrast(
			ArrayList<BetweenParticipantFactor> fullFactorList);
	
	/**
	 * Return a polynomial contrast to test the grand mean for the 
	 * specified list of factors.
	 * @param fullFactorList
	 * @return
	 */
	
	public NamedMatrix getWithinGrandMeanContrast(
			ArrayList<RepeatedMeasuresNode> fullFactorList);
	
	/**
	 * Computes orthogonal polynomial contrasts for the specified data values.
	 * @param x
	 * @param maxDegree
	 * @return
	 */
	public NamedMatrix getOrthogonalPolynomialCoefficients(
			double[] x, int maxDegree);

}
