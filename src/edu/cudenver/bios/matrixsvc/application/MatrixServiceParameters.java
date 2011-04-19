package edu.cudenver.bios.matrixsvc.application;

import org.apache.commons.math.linear.RealMatrix;

import java.util.ArrayList;

public class MatrixServiceParameters {
	
	private ArrayList<RealMatrix> matrixListFromRequest = null;
	private ArrayList<RealMatrix> matrixListForResponse = null;
	private Double scalarMultiplier = null;
	private Double trace = null;
	private Double rank = null;
	
	public void addMatrixToRequestList(RealMatrix matrix){
		if(matrix != null){
			matrixListFromRequest.add(matrix);
		}
	}
	
	public void addMatrixToResponseList(RealMatrix matrix){
		if(matrix != null){
			matrixListForResponse.add(matrix);
		}
	}
	
	public ArrayList<RealMatrix> getMatrixListFromRequest() {
		return matrixListFromRequest;
	}
	public void setMatrixListFromRequest(ArrayList<RealMatrix> matrixListFromRequest) {
		this.matrixListFromRequest = matrixListFromRequest;
	}
	public ArrayList<RealMatrix> getMatrixListForResponse() {
		return matrixListForResponse;
	}
	public void setMatrixListForResponse(ArrayList<RealMatrix> matrixListForResponse) {
		this.matrixListForResponse = matrixListForResponse;
	}
	public double getScalarMultiplier() {
		return scalarMultiplier;
	}
	public void setScalarMultiplier(double scalarMultiplier) {
		this.scalarMultiplier = scalarMultiplier;
	}
	public double getTrace() {
		return trace;
	}
	public void setTrace(double trace) {
		this.trace = trace;
	}
	public double getRank() {
		return rank;
	}
	public void setRank(double rank) {
		this.rank = rank;
	}
	
	
	
}

