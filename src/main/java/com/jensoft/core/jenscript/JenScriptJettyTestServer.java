/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.jenscript;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * X2D Jetty server for test jenscript
 * 
 * @author Sebastien Janaud
 */
public class JenScriptJettyTestServer {

    public static String contextPath = "/";
    public static String webAppPath = "src/main/java/com/jensoft/sw2d/core/jenscript";
    public static String descriptor = "src/main/java/com/jensoft/sw2d/core/jenscript/WEB-INF/web.xml";
    public static Server server;

    public static void startJettyServer() {
        server = new Server();
        SocketConnector connector = new SocketConnector();

        // Set some timeout options to make debugging easier.
        connector.setMaxIdleTime(1000 * 60 * 60);
        connector.setSoLingerTime(-1);
        connector.setPort(8888);
        server.setConnectors(new Connector[] { connector });

        WebAppContext bb = new WebAppContext();
        bb.setServer(server);
        bb.setContextPath(contextPath);
        bb.setWar(webAppPath);
        bb.setDescriptor(descriptor);

        server.addHandler(bb);

        try {
            System.out.println(">>> STARTING EMBEDDED X2D SERVER, PRESS ANY KEY TO STOP");
            server.start();
            System.in.read();
            System.out.println(">>> STOPPING EMBEDDED X2D SERVER");
            stopJettyServer();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }

    public static void stopJettyServer() {
        if (server != null) {
            try {
                server.stop();
                server.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        JenScriptJettyTestServer.startJettyServer();
    }
}
