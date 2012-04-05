/*
 * Matrix Service for the GLIMMPSE Software System.  Processes
 * incoming HTTP requests for matrix operations like addition, substraction
 * kronecker multiplication and so on
 * 
 * Copyright (C) 2010 Regents of the University of Colorado.
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
package edu.ucdenver.bios.matrixsvc.resource;

import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.matrixsvc.application.MatrixLogger;

/**
 * 
 * @author VIJAY AKULA
 *
 */
public class DisplayLoggerError {
    /**
     * Instance of Logger class to dispaly the log msgs.
     */
    private static Logger logger = MatrixLogger.getInstance();
    
    /**
     * This method is used to display errors if any.
     * 
     * @param preMsg
     *            the premsg is the first string in the error message to be
     *            displayed.
     * @param posMsg
     *            the posmsg is the second string in the error message to be
     *            displayed.
     */
    public final void displayError(final String preMsg, final String posMsg) {
        String msg = null;
        msg = preMsg.concat(posMsg);
        logger.info(msg);
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
    }
}
