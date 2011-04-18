package edu.cudenver.bios.matrixsvc.application;

import org.apache.commons.math.linear.RealMatrix;

public class MatrixServiceParameters {
	private RealMatrix sum = null;
	private RealMatrix difference = null;
	private RealMatrix product = null;
	private RealMatrix A = null;
	private RealMatrix B = null;
	private RealMatrix inverse = null;
	private RealMatrix positiveDefinite = null;
	private RealMatrix L = null;
	private RealMatrix lTranspose = null;
	private RealMatrix vec = null;
	private RealMatrix vech = null;

	private int scalarMultiplier = -1;
	private int trace = -1;
	private int rank = -1;
	
	public RealMatrix getProduct() {
		return product;
	}
	public void setProduct(RealMatrix product) {
		this.product = product;
	}
	public RealMatrix getSum() {
		return sum;
	}
	public void setSum(RealMatrix sum) {
		this.sum = sum;
	}
	public RealMatrix getDifference() {
		return difference;
	}
	public void setDifference(RealMatrix difference) {
		this.difference = difference;
	}
	public RealMatrix getA() {
		return A;
	}
	public void setA(RealMatrix a) {
		A = a;
	}
	public RealMatrix getB() {
		return B;
	}
	public void setB(RealMatrix b) {
		B = b;
	}
	public RealMatrix getInverse() {
		return inverse;
	}
	public void setInverse(RealMatrix inverse) {
		this.inverse = inverse;
	}
	public RealMatrix getPositiveDefinite() {
		return positiveDefinite;
	}
	public void setPositiveDefinite(RealMatrix positiveDefinite) {
		this.positiveDefinite = positiveDefinite;
	}
	public RealMatrix getL() {
		return L;
	}
	public void setL(RealMatrix l) {
		L = l;
	}
	public RealMatrix getlTranspose() {
		return lTranspose;
	}
	public void setlTranspose(RealMatrix lTranspose) {
		this.lTranspose = lTranspose;
	}
	public RealMatrix getVec() {
		return vec;
	}
	public void setVec(RealMatrix vec) {
		this.vec = vec;
	}
	public RealMatrix getVech() {
		return vech;
	}
	public void setVech(RealMatrix vech) {
		this.vech = vech;
	}
	public int getScalarMultiplier() {
		return scalarMultiplier;
	}
	public void setScalarMultiplier(int scalarMultiplier) {
		this.scalarMultiplier = scalarMultiplier;
	}
	public int getTrace() {
		return trace;
	}
	public void setTrace(int trace) {
		this.trace = trace;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	 
}
