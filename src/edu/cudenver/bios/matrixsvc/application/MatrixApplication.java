/*
 * Power Service for the GLIMMPSE Software System.  Processes
 * incoming HTTP requests for power, sample size, and detectable
 * difference
 * 
 * Copyright (C) 2011 Regents of the University of Colorado.  
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

import edu.cudenver.bios.matrixsvc.resource.*;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.Router;


/**
 * Main Restlet application class for the Matrix Service.
 * Defines URI mappings to the appropriate resource class.
 * 
 * @author Jonathan Cohen
 */
public class MatrixApplication extends Application
{   
    /**
     * Class which dispatches http requests to the appropriate
     * handler class for the matrix service.
     * 
     * @param parentContext
     */
    public MatrixApplication(Context parentContext) throws Exception
    {
        super(parentContext);

        MatrixLogger.getInstance().info("Matrix service starting.");
    }

    /**
     * Define URI mappings for incoming power, sample size,
     * and detectable difference requests
     */
    @Override
    public Restlet createRoot() 
    {
        // Create a router Restlet that routes each call to a new instance of Resource.
        Router router = new Router(getContext());
        
        // Defines only one default route, self-identifies server
        router.attachDefault(DefaultResource.class);

        /* attributes of matrix resources */
        // Matrix Addition resource 
        router.attach("/addition", MatrixAdditionResource.class);
        
        // Matrix Subtraction resource
        router.attach("/subtraction", MatrixSubtractionResource.class);
        
        // Multiplication resource
        router.attach("/mult", MatrixMultiplicationResource.class);
        
        // Scalar Multiplication resource
        router.attach("/mult/scalar", MatrixScalarMultiplicationResource.class);
        
        // Element-Wise Multiplication resource
        router.attach("/mult/elementWise", MatrixElementWiseMultiplicationResource.class);
        
        // Horizontal Direct Multiplication resource
        router.attach("/mult/horizontalDir", MatrixHorizontalDirectMultiplicationResource.class);
        
        // Kronecker Product Multiplication resource
        router.attach("/mult/kronecker", MatrixKroneckerProductResource.class);
        
        // Matrix Inversion resource
        router.attach("/inverse", MatrixInversionResource.class);
        
        // Matrix Rank resource
        router.attach("/rank", MatrixRankResource.class);
        
        // Matrix Trace resource
        router.attach("/trace", MatrixTraceResource.class);
        
        // Matrix Positive Definite resource
        router.attach("/positiveDefinite", MatrixPositiveDefiniteResource.class);
        
        // Matrix Orthogonal Polynomial Contrast resource
      //  router.attach("/contrast", MatrixContrastResource.class);
        
        //Matrix Cholesky Decomposition resource
        router.attach("/decomposition/cholesky", MatrixDecompositionCholeskyResource.class);
        
        //Matrix Vec resource
        router.attach("/vec", MatrixVecResource.class);
        
        //Matrix Vech resource
        router.attach("/vech", MatrixVechResource.class);
        
        return router;
    }
}

