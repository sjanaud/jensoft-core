/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.window;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;

/**
 * WindowPartComponent
 * 
 * @author Sebastien Janaud
 */
public class WindowPartComponent extends JComponent implements MouseListener,
        MouseMotionListener, MouseWheelListener {

    /** serial version uid */
    private final static long serialVersionUID = 651449492959746328L;

    /** zone */
    private WindowPart windowPart;

    /** host view */
    private View2D v2d;

    /** lock plugin */
    boolean lockPlugins = true;

    /**
     * create window part component
     * 
     * @param windowPart
     *            the window part to set
     * @param v2d
     *            the view2D to set
     */
    public WindowPartComponent(WindowPart windowPart, View2D v2d) {
        this.windowPart = windowPart;
        this.v2d = v2d;
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    /**
     * unlock all plugins for this component
     */
    public void unlockPlugins() {
        lockPlugins = false;
    }

    /**
     * lock all plugins for this component
     */
    public void lockPlugins() {
        lockPlugins = true;
    }

    /**
     * Paint device backdrop
     * 
     * @param g2d
     *            the graphics2D context
     */
    private void paintBackdrop(Graphics2D g2d) {

        if (!isOpaque()) {
            return;
        }

        if (getBackground() != null) {

            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // System.out.println("mouse moved in window border component!!!");

    }

    /**
     * paint part component
     */
    @Override
    public void paintComponent(Graphics g) {

        if (!lockPlugins) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g.create();
        paintBackdrop(g2d);
        paintPlugins(g2d);
        g2d.dispose();

    }

    /**
     * Paint plugins
     * 
     * @param g2d
     *            the graphics2D context
     */
    protected void paintPlugins(Graphics2D g2d) {

        List<Window2D> windows2D = v2d.getRegisterWindow();

        // paint all window, exlude active window
        for (Window2D w2d : windows2D) {

            if (!w2d.isVisible()) {
                continue;
            }

            if (!w2d.equals(v2d.getActiveWindow())) {
                // System.out.println("paint NON ACTIVE window part for window :"+w2d.getName());
                List<AbstractPlugin> plugins = w2d.getPluginRegistry();
                Collections.sort(plugins,
                                 AbstractPlugin.getPriorityComparator());
                if (plugins != null) {
                    for (int j = 0; j < plugins.size(); j++) {
                        AbstractPlugin plugin = plugins.get(j);
                        // System.out.println("paint non active plugin : "+plugin.getName());
                        plugin.paint(v2d, g2d, windowPart);
                    }
                }
            }

        }

        // paint active window
        if (v2d.getActiveWindow() != null && v2d.getActiveWindow().isVisible()) {

            // System.out.println("paint ACTIVE window part for window :"+v2d.getActiveWindow().getName());
            List<AbstractPlugin> plugins = v2d.getActiveWindow()
                    .getPluginRegistry();
            if (plugins != null) {
                Collections.sort(plugins,
                                 AbstractPlugin.getPriorityComparator());
                for (int j = 0; j < plugins.size(); j++) {
                    AbstractPlugin plugin = plugins.get(j);
                    // System.out.println("paint active plugin : "+plugin.getName());
                    plugin.paint(v2d, g2d, windowPart);
                }
            }
        }

        try {
            v2d.getWidgetPlugin().paint(v2d, g2d, windowPart);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }

    }

}
