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
package edu.cudenver.bios.matrixsvc.representation;

import org.restlet.data.MediaType;
import org.restlet.resource.DomRepresentation;
import org.w3c.dom.Document;

import java.io.IOException;

/**
 * XML representation of a single node (trace, rank, positive definite.)  
 *
 * @author Jonathan Cohen
 */
public class SingleValueRepresentation extends DomRepresentation
{
    /**
     * Create an XML representation of the specified element.
     * 
     * @param element is a String representation of the element
     * @throws IOException
     */
    public SingleValueRepresentation(String element) throws IOException
    {
    	 super(MediaType.APPLICATION_XML);
         
         Document doc = getDocument();
         
         doc.normalizeDocument();
    }
}
