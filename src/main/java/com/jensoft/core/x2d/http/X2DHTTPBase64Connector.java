/**

 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jensoft.core.x2d.X2D;
import com.jensoft.core.x2d.X2DException;
import com.jensoft.core.x2d.lang.X2DError;

/**
 * <code>X2DHTTPBase64Connector<code>
 * <p>
 * X2DHTTPBase64Connector is a base Servlet based on X2D {@link X2D}
 * which consumes the post request post parameter 'view' and produces a base 64 string representation of the x2d XML source 'view' parameter
 * </p>
 * <p>
 * this HTTP connector can be extends to make an API Key client system with 
 * the given API key passed in request parameter.
 * </p>
 * write on HTTP output stream the following string :<br>
 * <p>
 * data:image/png;base64,[image base 64]
 * </p>
 * 
 * @author Sebastien Janaud
 */
public class X2DHTTPBase64Connector extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** x2d processor */
    private X2D x2dProcessor;

    /**
     * Default constructor.
     */
    public X2DHTTPBase64Connector() {
        x2dProcessor = new X2D();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String apiKey = request.getParameter("apikey"); 
            String viewKey = request.getParameter("viewkey"); 
            String xmlViewSource = request.getParameter("view");            
            //System.out.println("X2D Connector process View : "+xmlViewSource);            
            x2dProcessor.registerX2DSource(xmlViewSource);            
            String image = x2dProcessor.getView2D().createViewEmitter().emitAsImageBase64();
            response.setContentType("image/png");            
            PrintWriter out = response.getWriter();
            out.print(image);
            out.close();
        }
        catch (X2DException e) {
            response.setContentType("text/xml");
            response.setStatus(400);
            PrintWriter out = response.getWriter();
           
            List<X2DError> errors = e.getErrors();
            //System.out.println("error count : "+errors.size());
            out.println("<x2d-errors>");
            for (X2DError x2dError : errors) {
                out.println("<x2d-error>" + x2dError.getMessage() + "</x2d-error>");
            }
            out.println("</x2d-errors>");
            out.close();
        }
        catch (Exception e) {
            response.setContentType("text/xml");
            response.setStatus(400);
            PrintWriter out = response.getWriter();
            String msg = e.getMessage();
            out.println("<x2d-error>" + msg + "</x2d-error>");
            out.close();
        }
    }

}
