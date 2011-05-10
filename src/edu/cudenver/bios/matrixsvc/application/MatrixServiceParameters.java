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
package edu.cudenver.bios.matrixsvc.application;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is the domain object for the matrix services. 
 * @author Jonathan Cohen
 */
public class MatrixServiceParameters implements Serializable{
	public static final long serialVersionUID = MatrixServiceParameters.class.hashCode();
	
	//This list holds matrices from the request 
	private ArrayList<NamedRealMatrix> matrixListFromRequest = null;
	
	//This list holds matrices for the response
	private ArrayList<NamedRealMatrix> matrixListForResponse = null;
	
	//This double is the scalar multiplier for the scalar multiplication service
	private Double scalarMultiplier = null;
	
	//This double is the calculated trace
	private Double trace = null;
	
	//This integer is the calculated rank
	private Integer rank = null;
	
	//This boolean indicates whether the input matrix is positive definite
	boolean positiveDefinite = false;
	
	public Boolean isPositiveDefinite() {
		return positiveDefinite;
	}

	public void setPositiveDefinite(boolean positiveDefinite) {
		this.positiveDefinite = positiveDefinite;
	}

	/**
	 * This is a convenience method for adding a matrix to the list of incoming
	 * matrices from the request object.
	 * @param matrix
	 */
	public void addMatrixToRequestList(NamedRealMatrix matrix){
		if(matrix != null){
			matrixListFromRequest.add(matrix);
		}
	}
	
	/**
	 * This is a convenience method for adding a matrix to the list of outgoing
	 * matrices for the response object.
	 * @param matrix is a 
	 * @see org.apache.commons.math.linear.RealMatrix
	 */
	public void addMatrixToResponseList(NamedRealMatrix matrix){
		if(matrix != null){
			matrixListForResponse.add(matrix);
		}
	}
	
	/**
	 * Getter for the list of matrices found in the request object.
	 * @return ArrayList<RealMatrix>
	 */
	public ArrayList<NamedRealMatrix> getMatrixListFromRequest() {
		return matrixListFromRequest;
	}
	
	/**
	 * Setter for the list of matrices found in the request object.
	 * @param matrixListFromRequest ArrayList<RealMatrix>
	 */
	public void setMatrixListFromRequest(ArrayList<NamedRealMatrix> matrixListFromRequest) {
		this.matrixListFromRequest = matrixListFromRequest;
	}
	
	/**
	 * Getter for the list of matrices for the response.
	 * @TODO it may contain 1..n matrices depending on operation 
	 * (check if this is true - can it be null or empty?)
	 * @return ArrayList<RealMatrix>
	 */
	public ArrayList<NamedRealMatrix> getMatrixListForResponse() {
		return matrixListForResponse;
	}
	
	/**
	 * Setter for the list of matrices for the response.
	 * @param matrixListForResponse
	 */
	public void setMatrixListForResponse(ArrayList<NamedRealMatrix> matrixListForResponse) {
		this.matrixListForResponse = matrixListForResponse;
	}
	
	/**
	 * Getter for the scalar multiplier used for the scalar 
	 * multiplication service
	 * @return double
	 */
	public double getScalarMultiplier() {
		return scalarMultiplier;
	}
	
	/**
	 * Setter for the scalar multiplier used for the scalar 
	 * multiplication service
	 * @param scalarMultiplier double
	 */
	public void setScalarMultiplier(double scalarMultiplier) {
		this.scalarMultiplier = scalarMultiplier;
	}
	
	/**
	 * Getter for the calculated trace.
	 * @return double
	 */
	public double getTrace() {
		return trace;
	}
	
	/**
	 * Setter for the calculated trace.
	 * @param trace double
	 */
	public void setTrace(double trace) {
		this.trace = trace;
	}
	
	/**
	 * Getter for the calculated rank.
	 * @return int
	 */
	public int getRank() {
		return rank.intValue();
	}
	
	/**
	 * Setter for the calculated rank.
	 * @param rank int
	 */
	public void setRank(int rank) {
		this.rank = new Integer( rank );
	}
	
}

