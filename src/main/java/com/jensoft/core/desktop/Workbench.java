/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.desktop;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class Workbench extends JFrame {

    private WorkbenchEngine workbenchEngine;

    private PerspectiveFolder perspectiveFolder;

    public Workbench() {

        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }

        workbenchEngine = new WorkbenchEngine();
        perspectiveFolder = new PerspectiveFolder();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WorkbenchWindowListener());

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(perspectiveFolder, BorderLayout.CENTER);
    }

    public void registerPerspective(Perspective perspective) {
        perspectiveFolder.registerPerspective(perspective);

    }

    /**
     * open the specified perspective
     * 
     * @param perspectiveClass
     */
    public void openPerspective(Class perspectiveClass) {
        perspectiveFolder.openPerspective(perspectiveClass);
    }

    /**
     * open the specified viewset from an implicit perspective
     * 
     * @param viewsetClass
     */
    public void openViewset(Class viewsetClass) {
        for (Perspective p : perspectiveFolder.getPerspectives()) {
            if (p.isRegisterViewset(viewsetClass)) {
                p.setCurrentViewset(viewsetClass);
                openPerspective(p.getClass());
                return;
            }
        }
        throw new IllegalArgumentException(
                                           "Viewset seems to be not registered in workbench perspectives");
    }

    public PerspectiveFolder getPerspectiveFolder() {
        return perspectiveFolder;
    }

    public Perspective getPerspective(Class perspectiveClass) {
        return getPerspectiveFolder().getPerspective(perspectiveClass);
    }

    class WorkbenchEngine extends Thread {

        @Override
        public void run() {

            try {
                while (!isInterrupted()) {

                    // System.out.println("worbench sleep");
                    sleep(10000);
                }
            }
            catch (Exception e) {
                System.out.println("engine interupt");
                Thread.currentThread().interrupt();
            }

            System.out.println("workbench engine stop");
        }

    }

    public void start() {
        System.out.println("workbench start()");
        pack();
        setSize(1024, 768);

        workbenchEngine.start();
    }

    private Runnable workbenchCloser = new Runnable() {

        @Override
        public void run() {

            try {
                while (workbenchEngine.isAlive()) {
                    System.out.println("wait for closing...");
                    Thread.sleep(100);
                }

            }
            catch (InterruptedException e) {
                System.out.println("closer interupt");
                Thread.currentThread().interrupt();
            }
            System.exit(0);

        }
    };

    public void stopWorkbench() {
        System.out.println("workbench stop()");

        workbenchEngine.interrupt();
        Thread closer = new Thread(workbenchCloser);
        closer.start();

    }

    class WorkbenchWindowListener implements WindowListener {

        @Override
        public void windowActivated(WindowEvent e) {
            // System.out.println("workbench activated");

        }

        @Override
        public void windowClosed(WindowEvent e) {
            // System.out.println("workbench closed");

        }

        @Override
        public void windowClosing(WindowEvent e) {
            // System.out.println("workbench closing");
            stopWorkbench();

        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            // System.out.println("workbench deactivated");

        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            // System.out.println("workbench desiconified");

        }

        @Override
        public void windowIconified(WindowEvent e) {
            // System.out.println("workbench iconified");

        }

        @Override
        public void windowOpened(WindowEvent e) {
            // System.out.println("workbench opened");

        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                Workbench w = new Workbench();
                w.start();

            }
        });

    }

}
