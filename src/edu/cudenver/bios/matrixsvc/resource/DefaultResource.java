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
package edu.cudenver.bios.matrixsvc.resource;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;



/**
 * Default request resource.  Called from the URI /matrix
 * Simply returns a self-identifying message for the server
 * Copied from edu.cudenver.bios.powersvc.resource.DefaultResource
 * 
 * @author Jonathan Cohen
 */
public class DefaultResource extends ServerResource
{

    /**
     * Returns a full representation for a given variant.
     */
	@Get
    public Representation represent(Variant variant) {
        Representation representation = 
            new StringRepresentation("DefaultResource of Matrix REST Service", MediaType.TEXT_PLAIN);

        return representation;
    }
}
