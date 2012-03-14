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
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
/**
 * 
 * @author VIJAY AKULA
 *
 */
public class MatrixHelper 
{
	private static Logger logger = MatrixLogger.getInstance();
	/**
	 * This function takes a list of Named Matrices and converts into
	 * a list of Real Matrices and returns a List of Real Matrices
	 * @param matrixList
	 * @return List of Real Matrices
	 */
	public List<RealMatrix> toRealMatrixList(List<NamedMatrix> matrixList)
	{
		if(matrixList == null || matrixList.size() < 2)
		{
        	String msg = "Matrix list must contain at least 2 matrices to perform operation.";
        	logger.info(msg);
         	throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
        }
		
		List<RealMatrix> realMatrixList = new ArrayList<RealMatrix>();
		for(NamedMatrix namedMatrix: matrixList)
		{
			realMatrixList.add(toRealMatrix(namedMatrix));
		}
		return realMatrixList;
	}
	
	/**
	 * This method takes Named Matrix and converts it into a RealMatrix
	 * and returns a Real Matrix
	 * @param matrix
	 * @return Real Matrix
	 */
	public RealMatrix toRealMatrix(NamedMatrix matrix)
	{
		if(matrix == null)
		{
			String 
			msg = "At least one matrix must be specified to perform this operation"; 
			logger.info(msg);
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
		}
		return new Array2DRowRealMatrix(matrix.getData());
	}
	
	/**
	 * This method takes a Real Matrix and converts it into a Named Matrix
	 * and returns that Named Matrix
	 * @param matrix
	 * @return Named Matrix
	 */
	public NamedMatrix toNamedMatrix(RealMatrix matrix, String Name)
	{
		NamedMatrix namedMatrix = new NamedMatrix();
		namedMatrix.setData(matrix.getData());
		namedMatrix.setName(Name);
		namedMatrix.setColumns(matrix.getColumnDimension());
		namedMatrix.setRows(matrix.getRowDimension());
		return null;
	}
}
