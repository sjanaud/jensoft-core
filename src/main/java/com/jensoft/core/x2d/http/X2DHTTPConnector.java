/**

 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.http;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jensoft.core.x2d.X2D;

/**
 * <code>X2DHTTPBase64Connector<code>
 * <p>
 * X2DHTTPBase64Connector is a base Servlet based on X2D {@link X2D}
 * which consumes the post request post parameter 'view' and image on {@link ServletOutputStream}
 * 
 * @author Sebastien Janaud
 */
public class X2DHTTPConnector extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** x2d processor */
    private X2D x2dProcessor;

    /**
     * Default constructor.
     */
    public X2DHTTPConnector() {
        x2dProcessor = new X2D();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String xmlViewSource = request.getParameter("view");            
            //System.out.println("X2D PNG Connector process View : "+xmlViewSource);            
            x2dProcessor.registerX2DSource(xmlViewSource);            
            x2dProcessor.getView().createViewEmitter().emitPNGImageOnStream(response.getOutputStream());
            response.setContentType("image/png");
        }
        catch (Exception e) {
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();
            String msg = e.getMessage();
            out.println("<x2d-error>" + msg + "</x2d-error>");
            out.close();
        }
    }

}
