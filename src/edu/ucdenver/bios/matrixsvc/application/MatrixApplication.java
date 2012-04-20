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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package edu.ucdenver.bios.matrixsvc.application;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import edu.ucdenver.bios.matrixsvc.resource.ContrastGetBetweenGrandMeanContrastServerResource;
import edu.ucdenver.bios.matrixsvc.resource.ContrastGetBetweenInteractionContrastServerResource;
import edu.ucdenver.bios.matrixsvc.resource.ContrastGetBetweenMainEffectContrastServerResource;
import edu.ucdenver.bios.matrixsvc.resource.ContrastGetOrthogonalPolynomialCoefficientsServerResource;
import edu.ucdenver.bios.matrixsvc.resource.ContrastGetWithinGrandMeanContrastServerResource;
import edu.ucdenver.bios.matrixsvc.resource.ContrastGetWithinInteractionContrastServerResource;
import edu.ucdenver.bios.matrixsvc.resource.ContrastGetWithinMainEffectContrastServerResource;
import edu.ucdenver.bios.matrixsvc.resource.DefaultResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixAdditionServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixCholeskyDecomposeServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixElementwiseMultiplicationServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixHorizontalDirectMultiplyServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixInversionServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixIsPositiveDefiniteServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixKroneckerMultiplyServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixMultiplicationServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixScalarMultiplyResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixSubtractionServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixTraceServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixVecServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixVechServerResource;
/**
 * 
 * @author VIJAY AKULA
 *
 */
public class MatrixApplication extends Application
{
    
    /**
     * Class which dispatches http requests to the appropriate
     * handler class for the matrix service.
     *
     * @param parentContext the parent context
     * @throws Exception the exception
     */
    public MatrixApplication(final Context parentContext) throws Exception
    {
        super(parentContext);

        MatrixLogger.getInstance().info("Matrix service starting.");
    }
    
    /**
     * Define URI mappings for incoming matrix, contrast,
     * and detectable difference requests
     */
    @Override
    public Restlet createInboundRoot()
    {
        // Create a router Restlet that routes each call to a new
        //instance of Resource.
        Router router = new Router(getContext());
        
        // Defines only one default route, self-identifies server
        router.attachDefault(DefaultResource.class);
                
        //MatrixAdditionResource
        router.attach("/addition", MatrixAdditionServerResource.class);
        
        //MatrixSubstractionResource
        router.attach("/subtraction", MatrixSubtractionServerResource.class);
        
        //MatrixMultiplicationResource
        router.attach("/mult", MatrixMultiplicationServerResource.class);
        
        //MatrixElementwiseMultiplicationResource
        router.attach("/mult/elementWise", MatrixElementwiseMultiplicationServerResource.class);
        
        //MatrixKronckerMultiplyResource
        router.attach("/mult/kronecker", MatrixKroneckerMultiplyServerResource.class);
        
        //MatrixSclarMultiplyResource
        router.attach("/mult/scalar", MatrixScalarMultiplyResource.class);
        
        //MatrixHorizontalDirectMultiplyResource
        router.attach("/mult/horizontalDir", MatrixHorizontalDirectMultiplyServerResource.class);
        
        //MatrixHorizontalDirectMultiplyResource
        router.attach("/decomposition/cholesky", MatrixCholeskyDecomposeServerResource.class);
        
        //MatrixHorizontalDirectMultiplyResource
        router.attach("/inverse", MatrixInversionServerResource.class);
        
        //MatrixHorizontalDirectMultiplyResource
        router.attach("/trace", MatrixTraceServerResource.class);
        
        //MatrixHorizontalDirectMultiplyResource
        router.attach("/positiveDefinite", MatrixIsPositiveDefiniteServerResource.class);
        
        //MatrixHorizontalDirectMultiplyResource
        router.attach("/vec", MatrixVecServerResource.class);
        
        //MatrixHorizontalDirectMultiplyResource
        router.attach("/vech", MatrixVechServerResource.class);
                
        //ContrastGetBetweenInteractionContrastServerResource
        router.attach("/contrast/interaction/between",
                ContrastGetBetweenInteractionContrastServerResource.class);
        
        //ContrastGetWithinInteractionContrastServerResource
        router.attach("/contrast/interaction/within",
                ContrastGetWithinInteractionContrastServerResource.class);
        
        //ContrastGetBetweenMainEffectContrastServerResource
        router.attach("/contrast/main/between",
                ContrastGetBetweenMainEffectContrastServerResource.class);
        
        //ContrastGetWithinMainEffectContrastServerResource
        router.attach("/contrast/main/within",
                ContrastGetWithinMainEffectContrastServerResource.class);
        
        //ContrastGetBetweenGrandMeanContrastServerResource
        router.attach("/contrast/grandmean/between",
                ContrastGetBetweenGrandMeanContrastServerResource.class);
        
      //ContrastGetWithinGrandMeanContrastServerResource
        router.attach("/contrast/grandmean/within",
                ContrastGetWithinGrandMeanContrastServerResource.class);
        
        //ContrastGetOrthogonalPolynomialCoefficientsServerResource
        router.attach("/contrast/coefficients", 
                ContrastGetOrthogonalPolynomialCoefficientsServerResource.class);
        
        return router;
    }
}
