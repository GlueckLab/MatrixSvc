/*
 * Power Service for the GLIMMPSE Software System.  Processes
 * incoming HTTP requests for power, sample size, and detectable
 * difference
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
package edu.cudenver.bios.matrixsvc.representation;

import edu.cudenver.bios.matrix.OrthogonalPolynomialContrast;
import edu.cudenver.bios.matrix.OrthogonalPolynomialContrastCollection;
import edu.cudenver.bios.matrixsvc.application.MatrixConstants;
import edu.cudenver.bios.matrixsvc.application.NamedRealMatrix;
import edu.cudenver.bios.utils.Factor;

import org.restlet.data.MediaType;
import org.restlet.resource.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonathan Cohen
 *
 */
public class OrthogonalPolynomialContrastXmlRepresentation extends DomRepresentation 
{
	/**
	 * Create an XML representation for a Orthogonal Polynomial Contrast 
	 * XML response entity body. 
	 * 
	 * @param opContrastColl is a @see OrthogonalPolynomialContrastCollection.
	 * @throws IOException
	 */
	public OrthogonalPolynomialContrastXmlRepresentation(
			OrthogonalPolynomialContrastCollection opContrastColl) 
	throws IOException
	{
	    super(MediaType.APPLICATION_XML);
	    Document doc = getDocument();
	    Element rootElement = createXml(opContrastColl, doc);
	    doc.appendChild(rootElement);
	    doc.normalizeDocument();
	}

	private static Element createXml(OrthogonalPolynomialContrastCollection contrastColl, Document _doc){
		Element rootElem = _doc.createElement(MatrixConstants.TAG_ORTHOG_POLY_CONTRAST_LIST);
		
		//Get the list of contrasts
		ArrayList<OrthogonalPolynomialContrast> contrasts = 
			new ArrayList<OrthogonalPolynomialContrast> (contrastColl.getContrastList());
		
		//List of Factors
		List<Factor> factors = null;
		
		//Matrix
		NamedRealMatrix matrix = null;

		//Iterate over Contrasts
		for(OrthogonalPolynomialContrast c: contrasts)
		{
			matrix = new NamedRealMatrix( c.getContrastMatrix() );
			
			Element contrastElement = _doc.createElement(MatrixConstants.TAG_CONTRAST);
			if( c.getType().equals(OrthogonalPolynomialContrast.ContrastType.GRAND_MEAN))
			{
				matrix.setName(MatrixConstants.GRAND_MEAN);
				contrastElement.setAttribute(MatrixConstants.ATTR_TYPE, MatrixConstants.GRAND_MEAN);
			}
			else if( c.getType().equals(OrthogonalPolynomialContrast.ContrastType.MAIN_EFFECT))
			{
				factors = c.getFactorList();
				matrix.setName(MatrixConstants.MAIN_EFFECT);
				contrastElement.setAttribute(MatrixConstants.ATTR_TYPE, MatrixConstants.MAIN_EFFECT);
			}
			else if( c.getType().equals(OrthogonalPolynomialContrast.ContrastType.INTERACTION))
			{
				factors = c.getFactorList();
				matrix.setName(MatrixConstants.INTERACTION);
				contrastElement.setAttribute(MatrixConstants.ATTR_TYPE, MatrixConstants.INTERACTION);
			}
			
			//Create XML for the matrix
			Node matrixNode = MatrixXmlRepresentation.createMatrixNodeFromRealMatrix(matrix, _doc);
			
			//Append matrix node to contrast element
			contrastElement.appendChild(matrixNode);
			
			//Not all contrasts have a Factor list (Grand Mean does not)
			if(factors != null && factors.size() > 0)
			{
				//Create factorList element
				Element factorListElement = _doc.createElement(MatrixConstants.TAG_FACTOR_LIST);
				
				//loop through factors, creating a node for each
				Element factorNode = null;
				for(Factor f : factors)
				{
					factorNode = createFactorXml(f, _doc);
					
					//add factor node to factorList element
					factorListElement.appendChild(factorNode);
				}
				//append factorList as child to contrast element
				contrastElement.appendChild(factorListElement);
			}
			//append Contrast element to root
			rootElem.appendChild(contrastElement);
		}
		return rootElem;
	}
	
	private static Element createFactorXml(Factor factor, Document _doc){
		Element factorElement = _doc.createElement(MatrixConstants.TAG_FACTOR);
		factorElement.setAttribute(MatrixConstants.ATTR_NAME, factor.getName());
		
		//Get the values of this factor
		double[] values = factor.getValues();
		Element valNode = null;
		
		for(int v = 0; v < values.length; v++)
		{
			valNode = _doc.createElement(MatrixConstants.TAG_V);
			valNode.setTextContent(Double.toString(values[v]) );
			factorElement.appendChild(valNode);
		}
		return factorElement;
	}
}
