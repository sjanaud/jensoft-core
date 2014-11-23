/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.outline;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import com.jensoft.core.device.Device;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>OutlinePlugin</code> 
 * 
 * @since 1.0
 * 
 * @author Sebastien Janaud
 */
public class OutlinePlugin extends AbstractOutlinePlugin {

    /** outline painter */
    private AbstractOutlinePainter painter;

    /** outline color */
    private Color themeColor;

    /** inflating flag */
    private boolean inflating = false;

    /** inflate rectangle */
    private Rectangle2D inflateOutline;

    /**
     * create default outline plugin
     */
    public OutlinePlugin() {
        setName("OutlinePlugin");
        setPriority(-1000);
    }

    /**
     * create outline with specified color
     * 
     * @param themeColor
     *            the outline color to set
     */
    public OutlinePlugin(Color themeColor) {
        this();
        this.themeColor = themeColor;
    }

    /**
     * get outline theme color
     */
    @Override
    public Color getThemeColor() {
        return themeColor;
    }

    /**
     * set outline theme color
     * 
     * @param themeColor
     *            the color to set
     */
    @Override
    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
    }

    /**
     * get the outline painter
     * 
     * @return the painter
     */
    public AbstractOutlinePainter getPainter() {
        return painter;
    }

    /**
     * set the painter for this layout
     * 
     * @param painter
     */
    public void setPainter(AbstractOutlinePainter painter) {
        this.painter = painter;
    }

    /**
     * @return the inflating
     */
    public boolean isInflating() {
        return inflating;
    }

    /**
     * @param inflating
     *            the inflating to set
     */
    public void setInflating(boolean inflating) {
        this.inflating = inflating;
    }

    /**
     * inflate outline
     */
    public InflateOutline inflateOutline() {
        if (isInflating()) {
            return null;
        }

        InflateOutline i = new InflateOutline();
        i.start();
        return i;
    }

    /**
     * inflate outline
     * 
     * @param startDelay
     *            start delay before starting inflate
     */
    public InflateOutline inflateOutline(int startDelay) {
        if (isInflating()) {
            return null;
        }

        InflateOutline i = new InflateOutline(startDelay);
        i.start();
        return i;
    }

    /**
     * deflate outline
     * 
     */
    public DeflateOutline deflateOutline() {
        if (isInflating()) {
            return null;
        }

        DeflateOutline d = new DeflateOutline();
        d.start();
        return d;
    }

    /**
     * deflate outline
     * 
     * @param startDelay
     *            start delay before starting inflate
     */
    public DeflateOutline deflateOutline(int startDelay) {
        if (isInflating()) {
            return null;
        }

        DeflateOutline d = new DeflateOutline(startDelay);
        d.start();
        return d;
    }

    /**
     * <code>InflateOutline</code> inflate outline animator
     */
    public class InflateOutline extends Thread {

        private int startDelay = 20;

        /**
         * create default inflate outline
         */
        public InflateOutline() {
        }

        /**
         * create inflate outline with specified start delay
         * 
         * @param startDelay
         *            the start delay
         */
        public InflateOutline(int startDelay) {
            this.startDelay = startDelay;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            setInflating(true);
            try {
                Projection w2d = getProjection();

                w2d.getView().repaintView();
                Thread.sleep(startDelay);

                Device d2d = w2d.getDevice2D();
                int step = 20;
                int width = d2d.getDeviceWidth();
                int height = d2d.getDeviceHeight();
                double deltaWidth = width / 2 / step;
                double deltaHeight = height / 2 / step;
                for (int i = 0; i <= step; i++) {

                    double deltaWidthStep = deltaWidth * i;
                    double deltaHeightStep = deltaHeight * i;

                    inflateOutline = new Rectangle2D.Double(width / 2
                            - deltaWidthStep, height / 2 - deltaHeightStep,
                                                            2 * deltaWidthStep, 2 * deltaHeightStep);
                    w2d.getView().repaintDevice(inflateOutline.getBounds());
                    Thread.sleep(20);
                }

                setInflating(false);
                w2d.getView().repaintView();
                inflateOutline = null;

            }
            catch (InterruptedException e) {
                inflateOutline = null;
                setInflating(false);
                Thread.currentThread().interrupt();
            }

        }
    }

    /**
     * <code>DeflateOutline</code> deflate outline animator
     */
    public class DeflateOutline extends Thread {

        private int startDelay = 20;

        /**
         * create default deflate outline
         */
        public DeflateOutline() {
        }

        /**
         * create deflate outline with specified start delay
         * 
         * @param startDelay
         *            the start delay
         */
        public DeflateOutline(int startDelay) {
            this.startDelay = startDelay;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            setInflating(true);
            try {
                Projection w2d = getProjection();
                if (w2d == null) {
                    interrupt();
                }
                w2d.getView().repaintView();
                Thread.sleep(startDelay);
                Device d2d = w2d.getDevice2D();
                int step = 20;
                int width = d2d.getDeviceWidth();
                int height = d2d.getDeviceHeight();
                double deltaWidth = width / 2 / step;
                double deltaHeight = height / 2 / step;
                for (int i = step; i >= 0; i--) {
                    double deltaWidthStep = deltaWidth * i;
                    double deltaHeightStep = deltaHeight * i;
                    Rectangle oldInflate = null;
                    if (inflateOutline != null) {
                        oldInflate = inflateOutline.getBounds();
                    }
                    inflateOutline = new Rectangle2D.Double(width / 2
                            - deltaWidthStep, height / 2 - deltaHeightStep,
                                                            2 * deltaWidthStep, 2 * deltaHeightStep);
                    if (oldInflate != null) {
                        w2d.getView().repaintDevice(oldInflate);
                    }
                    else {
                        w2d.getView().repaintDevice();
                    }
                    Thread.sleep(20);
                }

                setInflating(false);
                w2d.getView().repaintView();
                inflateOutline = null;

            }
            catch (InterruptedException e) {
                inflateOutline = null;
                setInflating(false);
                Thread.currentThread().interrupt();
            }

        }
    }

    /**
     * paint inflating
     * 
     * @param v2d
     *            the view 2D
     * @param g2d
     *            the graphics context
     * @param viewPart
     *            the view part
     */
    private void paintInflating(View view, Graphics2D g2d, ViewPart viewPart) {
           
        if (viewPart != ViewPart.Device) {
            return;
        }
        if (inflateOutline != null) {
            if (themeColor != null) {
                g2d.setColor(themeColor);
            }
            else {
                g2d.setColor(getProjection().getThemeColor());
            }

            g2d.draw(inflateOutline);
        }
    }

   
   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.outline.AbstractOutlinePlugin#doPaintOutline(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    public void doPaintOutline(View view, Graphics2D g2d, ViewPart viewPart) {

        if (getPaintBehavior() == PaintBehavior.NoPaint) {
            return;
        }
        if (getPaintBehavior() == PaintBehavior.PaintIfHostProjectionActive
                && !getProjection().isLockActive()) {
            return;
        }

        if (painter == null) {
            if (themeColor != null) {
                painter = new LineOutlinePainter(themeColor);
            }
            else {
                painter = new LineOutlinePainter(getProjection().getThemeColor());
            }

        }
        else {
            if (themeColor != null) {
                painter.setOutlineColor(themeColor);
            }
            else {
                painter.setOutlineColor(getProjection().getThemeColor());
            }
        }

        if (isInflating() && viewPart == ViewPart.Device) {
            paintInflating(view, g2d, viewPart);
        }
        else {

            if (!isInflating()) {
                if (viewPart == ViewPart.South) {
                    JComponent south = view.getViewPartComponent(ViewPart.South);
                    painter.doPaintOutline(south, g2d,
                    		view.getPlaceHolderAxisWest() - 1, 0,
                                           south.getWidth() - view.getPlaceHolderAxisEast()
                                                   - view.getPlaceHolderAxisWest() + 1,
                                           ViewPart.South);
                }
                if (viewPart == ViewPart.West) {
                    JComponent west = view.getViewPartComponent(ViewPart.West);
                    painter.doPaintOutline(west, g2d, west.getWidth() - 1,
                                           west.getHeight(), west.getHeight(), ViewPart.West);
                }
                if (viewPart == ViewPart.East) {
                    JComponent east = view.getViewPartComponent(ViewPart.East);
                    painter.doPaintOutline(east, g2d, 0, east.getHeight(),
                                           east.getHeight(), ViewPart.East);
                }
                if (viewPart == ViewPart.North) {
                    JComponent north = view.getViewPartComponent(ViewPart.North);
                    painter.doPaintOutline(north, g2d,
                    		view.getPlaceHolderAxisWest() - 1,
                                           north.getHeight() - 1,
                                           north.getWidth() - view.getPlaceHolderAxisEast()
                                                   - view.getPlaceHolderAxisWest() + 1,
                                           ViewPart.North);
                }
            }
        }

    }

}
