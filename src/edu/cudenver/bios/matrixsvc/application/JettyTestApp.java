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

import com.noelios.restlet.ext.servlet.ServerServlet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author Jonathan Cohen
 *
 */
public class JettyTestApp
{
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8001);
        
        ContextHandler content = new ContextHandler("/");
        content.setResourceBase("C://jetty//webapps");
        
        WebAppContext matrix = new WebAppContext();
        matrix.setContextPath("/matrix");
        matrix.setWar("C://jetty//webapps//matrix.war");
        server.setHandler(matrix);
 
        WebAppContext power = new WebAppContext();
        power.setContextPath("/power");
        power.setWar("C://jetty//webapps//power.war");
        
        WebAppContext file = new WebAppContext();
        file.setContextPath("/file");
        file.setWar("C://jetty//webapps//file.war");
        
        WebAppContext chart = new WebAppContext();
        chart.setContextPath("/chart");
        chart.setWar("C://jetty//webapps//chart.war");
        
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        context.setContextPath("/");
//        context.addServlet(new ServletHolder(new ServerServlet()),"/*");
        
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] {  matrix, power, file, chart, content });
        server.setHandler(contexts);
        
        server.start();
        server.join();
    }
}
