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

import org.apache.commons.math.linear.RealMatrix;

import java.util.ArrayList;

/**
 * This is the domain object for the matrix services. 
 * @author Jonathan Cohen
 */
public class MatrixServiceParameters {
	//This list holds matrices from the request 
	private ArrayList<RealMatrix> matrixListFromRequest = null;
	
	//This list holds matrices for the response
	private ArrayList<RealMatrix> matrixListForResponse = null;
	
	//This double is the scalar multiplier for the scalar multiplication service
	private Double scalarMultiplier = null;
	
	//This double is the calculated trace
	private Double trace = null;
	
	//This double is the calculated rankt
	private Double rank = null;
	
	/**
	 * This is a convenience method for adding a matrix to the list of incoming
	 * matrices from the request object.
	 * @param matrix
	 */
	public void addMatrixToRequestList(RealMatrix matrix){
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
	public void addMatrixToResponseList(RealMatrix matrix){
		if(matrix != null){
			matrixListForResponse.add(matrix);
		}
	}
	
	/**
	 * Getter for the list of matrices found in the request object.
	 * @return ArrayList<RealMatrix>
	 */
	public ArrayList<RealMatrix> getMatrixListFromRequest() {
		return matrixListFromRequest;
	}
	
	/**
	 * Setter for the list of matrices found in the request object.
	 * @param matrixListFromRequest ArrayList<RealMatrix>
	 */
	public void setMatrixListFromRequest(ArrayList<RealMatrix> matrixListFromRequest) {
		this.matrixListFromRequest = matrixListFromRequest;
	}
	
	/**
	 * Getter for the list of matrices for the response.
	 * @return ArrayList<RealMatrix>
	 */
	public ArrayList<RealMatrix> getMatrixListForResponse() {
		return matrixListForResponse;
	}
	
	/**
	 * Setter for the list of matrices for the response.
	 * @param matrixListForResponse
	 */
	public void setMatrixListForResponse(ArrayList<RealMatrix> matrixListForResponse) {
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
	 * @return double
	 */
	public double getRank() {
		return rank;
	}
	
	/**
	 * Setter for the calculated rank.
	 * @param rank double
	 */
	public void setRank(double rank) {
		this.rank = rank;
	}
	
}

