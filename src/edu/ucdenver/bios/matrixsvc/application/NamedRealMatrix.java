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
package edu.ucdenver.bios.matrixsvc.application;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;

// TODO: Auto-generated Javadoc
/**
 * This is a wrapper for the {@link Array2DRowRealMatrix} 
 * which adds a 'name' attribute for the matrix.
 * @author Jonathan Cohen
 *
 */
public class NamedRealMatrix extends Array2DRowRealMatrix {
    
    /** The Constant serialVersionUID. */
    public static final long serialVersionUID = NamedRealMatrix.class.hashCode();
    
    /** The name. */
    private String name = null;

    /**
     * Instantiates a new named real matrix.
     */
    public NamedRealMatrix(){
        super();
    }
    
    /**
     * Instantiates a new named real matrix.
     *
     * @param matrix the matrix
     */
    public NamedRealMatrix(RealMatrix matrix){
        super();
        this.data = matrix.getData();
    }
    
    /**
     * Instantiates a new named real matrix.
     *
     * @param numRows the num rows
     * @param numColumns the num columns
     * @param name the name
     */
    public NamedRealMatrix(int numRows, int numColumns, String name){
        super(numRows, numColumns);
        this.name = name;
    }
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }
}
