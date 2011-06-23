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

/**
 * This is a domain object for the parameters needed when performing
 * scalar mulitiplication.
 * @author Jonathan Cohen
 *
 */
public class ScalarMultiplicationParameters {
	private double scalarMultiplier;
	private NamedRealMatrix matrix = null;
	
	public double getScalarMultiplier() {
		return scalarMultiplier;
	}
	public void setScalarMultiplier(double scalarMultiplier) {
		this.scalarMultiplier = scalarMultiplier;
	}
	public NamedRealMatrix getMatrix() {
		return matrix;
	}
	public void setMatrix(NamedRealMatrix matrix) {
		this.matrix = matrix;
	}
	public void setMatrix(RealMatrix matrix) {
		this.matrix = new NamedRealMatrix( matrix );
	}
}
