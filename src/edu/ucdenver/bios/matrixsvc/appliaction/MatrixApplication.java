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
package edu.ucdenver.bios.matrixsvc.appliaction;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import edu.cudenver.bios.matrixsvc.application.MatrixLogger;
import edu.cudenver.bios.matrixsvc.resource.DefaultResource;
import edu.ucdenver.bios.matrixsvc.resource.ContrastServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixServerResource;
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
    public Restlet createInboundRoot() 
    {
        // Create a router Restlet that routes each call to a new instance of Resource.
        Router router = new Router(getContext());
        
        // Defines only one default route, self-identifies server
        router.attachDefault(DefaultResource.class);
        
        // Matrix Server Resource 
        router.attach("/matrix", MatrixServerResource.class);
        
        //Contrast Server Resource
        router.attach("/contrast", ContrastServerResource.class);
        
        return router;
    }
}
