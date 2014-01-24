/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.linesymbol;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.linesymbol.core.LineSymbol;
import com.jensoft.core.plugin.linesymbol.core.LineSymbolComponent;
import com.jensoft.core.plugin.linesymbol.core.LineSymbolGeometry;
import com.jensoft.core.plugin.linesymbol.core.LineSymbolGroup;
import com.jensoft.core.plugin.linesymbol.painter.LineSymbolPainter;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>LineSymbolPlugin</code> host <code>LineSymbol<code>
 * 
 * <br>
 * This button looks like this :
 *  
 * <br>
 * <br>
 * <center><img src="doc-files/LineSymbolPlugin.png"></center>
 * <br>this example show health symbol along time, symbol dimension is along Y dimension, time metrics on X
 * <br><br><br>
 * <code>LineSymbol defines line with a symbolic dimension
 * <ul>
 * <li>LineX define line along X , metrics on x , symbol on Y</li>
 * <li>LineY define line along Y , metrics on Y , symbol on X</li>
 * <ul>
 * 
 * @see LineSymbol
 * @see LineSymbolWidget
 * @see LineSymbolGeometry
 * @see LineSymbolPainter
 */
public class LineSymbolPlugin extends AbstractPlugin implements
        AbstractPlugin.OnMoveListener, AbstractPlugin.OnPressListener,
        AbstractPlugin.OnReleaseListener, AbstractPlugin.OnClickListener,
        AbstractPlugin.OnDragListener, AbstractPlugin.OnWheelListener {

    /** line nature for this plugin */
    private LineNature lineNature;

    public enum LineNature {
        LineX, LineY;
    };

    /** start scroll on x */
    private double startScrollX;

    /** start scroll on y */
    private double startScrollY;

    /** current scroll on x */
    private double currentScrollX;

    /** current scroll on y */
    private double currentScrollY;

    /** list of line components registered in the windows2d */
    private List<LineSymbolComponent> lineComponents = new ArrayList<LineSymbolComponent>();

    /** line symbol widget */
    private LineSymbolWidget lineSymbolWidget;

    /** lock scroll */
    private boolean lockScroll = false;

    /** North direction */
    public final static int NORTH = 0;

    /** South direction */
    public final static int SOUTH = 1;

    /** West direction */
    public final static int WEST = 2;

    /** East direction */
    public final static int EAST = 3;

    /**
     * create a LineSymbolPlugin
     */
    public LineSymbolPlugin() {
        setName("Line Symbol Plugin");
        setSelectable(true);
        setOnMoveListener(this);
        setOnClickListener(this);
        setOnDragListener(this);
        setOnMoveListener(this);
        setOnReleaseListener(this);
        setOnPressListener(this);
        lineSymbolWidget = new LineSymbolWidget();
        registerWidget(lineSymbolWidget);
    }

   
   

    /**
     * get the line symbol nature.
     * 
     * @return the line symbol.
     */
    public LineNature getLineNature() {
        return lineNature;
    }

    /**
     * set the line nature of this line symbol, set this to LineNature.LineX to
     * reference your symbol metrics on x axis and stack along y axis,
     * LineNature.LineY to reference your symbol metrics on y axis and stack
     * along x axis
     * 
     * @param lineNature
     */
    public void setLineNature(LineNature lineNature) {
        this.lineNature = lineNature;
    }

    /**
     * add line component in the window2D
     * 
     * @param lineComponent
     */
    public void addLineComponent(LineSymbolComponent lineComponent) {
        lineComponent.setHost(this);
        lineComponents.add(lineComponent);
    }

    /**
     * remove the line component registered in the window2D
     * 
     * @param lineComponent
     */
    public void removeLineComponent(LineSymbolComponent lineComponent) {
        lineComponents.remove(lineComponent);
    }

    /**
     * return the all registered line components in the window2D
     * 
     * @return the list of line components
     */
    public List<LineSymbolComponent> getLineComponents() {
        return lineComponents;
    }

    /**
     * get the x location of the specified line component
     * 
     * @param lineComponent
     * @return the x location of line
     */
    public double getComponentXPosition(LineSymbolComponent lineComponent) {

        Window2D wb2d = getWindow2D();
        List<LineSymbolComponent> barComponents = getLineComponents();

        // glues

        // double total = 0;
        // List<LineSymbolComponent> glues = new
        // ArrayList<LineSymbolComponent>();
        // for (LineSymbolComponent bc : barComponents) {
        // if (bc.getName().endsWith("Glue"))
        // glues.add(bc);
        // else
        // total = total + bc.getThickness();
        // }
        //
        // if (getWindow2D().getDevice2D().getDeviceWidth() > total) {
        //
        // double reste = getWindow2D().getDevice2D().getDeviceWidth() - total;
        // int gluesCount = glues.size();
        // if (gluesCount > 0) {
        // for (LineSymbolComponent glue : glues) {
        //
        // glue.setThickness(reste / gluesCount);
        // }
        // }
        // }

        double positionX = 0;

        List<LineSymbolComponent> bcomps = new ArrayList<LineSymbolComponent>();
        for (LineSymbolComponent bc : barComponents) {

            if (bc instanceof LineSymbolGroup) {
                bcomps.addAll(((LineSymbolGroup) bc).getLineComponents());
            }
            else {
                bcomps.add(bc);
            }
        }

        for (LineSymbolComponent bc : bcomps) {

            if (!bc.equals(lineComponent)) {
                positionX = positionX + bc.getThickness();
            }
            else {
                return positionX;
            }

        }
        return positionX;
    }

    /**
     * get the y location of the specified line component
     * 
     * @param lineComponent
     * @return the y location of line, a line X
     */
    public double getComponentYPosition(LineSymbolComponent lineComponent) {

        List<LineSymbolComponent> barComponents = getLineComponents();

        // double total = 0;
        // List<LineSymbolComponent> glues = new
        // ArrayList<LineSymbolComponent>();
        // for (LineSymbolComponent bc : barComponents) {
        // // if (bc.getName().endsWith("Glue"))
        // // glues.add(bc);
        // // else
        // total = total + bc.getThickness();
        // }
        //
        // // if (getWindow2D().getDevice2D().getDeviceHeight() > total) {
        // //
        // // double reste = getWindow2D().getDevice2D().getDeviceHeight()
        // // - total;
        // // int gluesCount = glues.size();
        // // if (gluesCount > 0) {
        // // for (LineSymbolComponent glue : glues) {
        // //
        // // glue.setThickness(reste / gluesCount);
        // // }
        // // }
        // // }

        double positionY = 0;

        List<LineSymbolComponent> bcomps = new ArrayList<LineSymbolComponent>();
        for (LineSymbolComponent bc : barComponents) {

            if (bc instanceof LineSymbolGroup) {
                bcomps.addAll(((LineSymbolGroup) bc).getLineComponents());
            }
            else {
                bcomps.add(bc);
            }
        }

        for (LineSymbolComponent bc : bcomps) {

            if (!bc.equals(lineComponent)) {
                positionY = positionY + bc.getThickness();
            }
            else {

                return positionY;
            }

        }

       // System.out.println("line  component : location " + lineComponent.getLocation());
        return positionY;
    }

    /**
     * resolve line geometry for the all registered line in this plugin
     */
    private void resolveLineGeometry() {

        if (getLineNature() == LineNature.LineX) {
            resolveLineXComponents();
        }

        if (getLineNature() == LineNature.LineY) {
            resolveLineYComponents();
        }
    }

    /**
     * resolve line geometry for the all registered x line nature in this plugin
     */
    private void resolveLineXComponents() {

        for (LineSymbolComponent barComponent : getLineComponents()) {
            resolveLineXComponent(barComponent);
        }
    }

    /**
     * resolve line geometry for the all registered y line nature in this plugin
     */
    private void resolveLineYComponents() {

        for (LineSymbolComponent barComponent : getLineComponents()) {
            resolveLineYComponent(barComponent);
        }
    }

    /**
     * resolve line geometry for the specified x line
     * 
     * @param lineComponent
     */
    private void resolveLineXComponent(LineSymbolComponent lineComponent) {

        lineComponent.setLineNature(LineNature.LineX);

        if (lineComponent instanceof LineSymbol) {

            if (lineComponent instanceof LineSymbolGroup) {
                resolveLineXGroup((LineSymbolGroup) lineComponent);
            }
            else {
                resolveLineX((LineSymbol) lineComponent);
            }

        }

    }

    /**
     * resolve line geometry for the specified x line
     * 
     * @param lineComponent
     */
    private void resolveLineYComponent(LineSymbolComponent lineComponent) {

        lineComponent.setLineNature(LineNature.LineY);

        if (lineComponent instanceof LineSymbol) {

            if (lineComponent instanceof LineSymbolGroup) {
                resolveLineYGroup((LineSymbolGroup) lineComponent);
            }
            else {
                resolveLineY((LineSymbol) lineComponent);
            }

        }

    }

    /**
     * resolve line group geometry for the specified x line group
     * 
     * @param lineGroup
     */
    private void resolveLineXGroup(LineSymbolGroup lineGroup) {
        List<LineSymbolComponent> bars = lineGroup.getLineComponents();
        for (LineSymbolComponent bc : bars) {
            resolveLineXComponent(bc);
        }
    }

    /**
     * resolve line group geometry for the specified y line group
     * 
     * @param lineGroup
     */
    private void resolveLineYGroup(LineSymbolGroup lineGroup) {
        List<LineSymbolComponent> bars = lineGroup.getLineComponents();
        for (LineSymbolComponent bc : bars) {
            resolveLineYComponent(bc);
        }
    }

    /**
     * resolve line symbol geometry for the specified x line
     * 
     * @param lineSymbol
     */
    private void resolveLineX(LineSymbol lineSymbol) {
        double yLocation = getComponentYPosition(lineSymbol);
        lineSymbol.setLocation(yLocation - divergence);
    }

    /**
     * resolve line symbol geometry for the specified y line
     * 
     * @param lineSymbol
     */
    private void resolveLineY(LineSymbol lineSymbol) {
        double xLocation = getComponentXPosition(lineSymbol);
        lineSymbol.setLocation(xLocation - divergence);

    }

    /**
     * paint the x line symbol
     * 
     * @param v2d
     *            the view
     * @param g2d
     *            the graphics context
     * @param ls
     *            the line symbol
     */
    protected void paintXLineSymbol(View2D v2d, Graphics2D g2d, LineSymbol ls) {

        if (ls.getLinePainter() != null) {
            ls.getLinePainter().paintLineSymbol(g2d, ls);
        }
        if (ls.getSymbolPainter() != null) {
            ls.getSymbolPainter().paintLineSymbol(g2d, ls);
        }

    }

    /**
     * paint the y line symbol
     * 
     * @param v2d
     *            the view
     * @param g2d
     *            the graphics context
     * @param ls
     *            the line symbol
     */
    protected void paintYLineSymbol(View2D v2d, Graphics2D g2d, LineSymbol ls) {

    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
     */
    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
        if (windowPart == WindowPart.Device) {
            // System.out.println("divergence : "+divergence);
            resolveLineGeometry();

            for (LineSymbolComponent lsc : getLineComponents()) {
                if (lsc instanceof LineSymbol) {
                    if (lsc.getLineNature() == LineNature.LineX) {
                        paintXLineSymbol(v2d, g2d, (LineSymbol) lsc);
                    }
                    else if (lsc.getLineNature() == LineNature.LineY) {
                        paintYLineSymbol(v2d, g2d, (LineSymbol) lsc);
                    }
                }
            }
        }
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnWheelListener#onWheel(java.awt.event.MouseWheelEvent)
     */
    @Override
    public void onWheel(MouseWheelEvent mwe) {

    }

 
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnDragListener#onDrag(java.awt.event.MouseEvent)
     */
    @Override
    public void onDrag(MouseEvent me) {
        if (!isLockSelected()) {
            return;
        }

        if (isLockPassive()) {
            return;
        }

        if (!isLockScroll()) {
            return;
        }

        scroll(me.getX(), me.getY());

        // fireScroll();
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnClickListener#onClick(java.awt.event.MouseEvent)
     */
    @Override
    public void onClick(MouseEvent me) {
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnReleaseListener#onRelease(java.awt.event.MouseEvent)
     */
    @Override
    public void onRelease(MouseEvent me) {
        handleControlReleased(me);
        stopScroll(me.getX(), me.getY());
        // fire scroll stop
    }

 
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnPressListener#onPress(java.awt.event.MouseEvent)
     */
    @Override
    public void onPress(MouseEvent me) {
        handleControlMove(me);

        startScroll(me.getX(), me.getY());
        // fireScroll start
    }

    /**
     * start scroll on specified start device location x and y, in the device
     * coordinate
     * 
     * @param x
     *            the start scroll x in device coordinate
     * @param y
     *            the start scroll y in device coordinate
     */
    public void startScroll(double x, double y) {
        startScrollX = x;
        startScrollY = y;

        lockScroll();

        //System.out.println("start scroll x " + startScrollX);
        //System.out.println("start scroll y " + startScrollY);

    }

    /**
     * return true if this plugin is in scroll operation
     * 
     * @return true if this plugin is in scroll operation
     */
    public boolean isLockScroll() {
        return lockScroll;
    }

    /**
     * unlock scroll
     */
    public void unlockScroll() {
        lockScroll = false;
    }

    /**
     * lock scroll
     */
    public void lockScroll() {
        lockScroll = true;
    }

    /**
     * run a new smooth scroll
     */
    class SmoothScroll extends Thread {

        /** direction is NORTH, EAST, SOUTH, WEST */
        private int direction;

        /**
         * create new smooth scroll
         * 
         * @param direction
         */
        public SmoothScroll(int direction) {
            super();
            this.direction = direction;
        }

        @Override
        public void run() {
            try {

                switch (direction) {

                    case NORTH:

                        interrupt();
                        break;

                    case SOUTH:

                        interrupt();
                        break;

                    case WEST:

                        interrupt();
                        break;

                    case EAST:

                        interrupt();
                        break;
                }
            }
            catch (Exception e) {
                Thread.currentThread().interrupt();
            }
            finally {

            }
        }

    }

    /**
     * scroll on specified current device x and y, in the device coordinate
     * 
     * @param x
     *            the current scroll x in device coordinate
     * @param y
     *            the current scroll y in device coordinate
     */
    public void scroll(double x, double y) {

        currentScrollX = x;
        currentScrollY = y;

        //System.out.println("scroll start x " + startScrollX);
        //System.out.println("scroll start y " + startScrollY);
        //System.out.println("scroll cur   x " + currentScrollX);
        //System.out.println("scroll cur   y " + currentScrollY);

        double deltaDeviceX = currentScrollX - startScrollX;
        double deltaDeviceY = currentScrollY - startScrollY;

        if (lineNature == LineNature.LineX) {
            divergence = divergence + (int) deltaDeviceY;
        }
        else if (lineNature == LineNature.LineY) {
            divergence = divergence + (int) deltaDeviceX;
        }

        // getWindow2D().getView2D().getDevice2D().repaintDevice();

        startScrollX = currentScrollX;
        startScrollY = currentScrollY;

    }

    /**
     * stop scroll on specified device x and y, in the device coordinate
     * 
     * @param x
     *            the stop scroll x in device coordinate
     * @param y
     *            the stop scroll y in device coordinate
     */
    public void stopScroll(double x, double y) {

        unlockScroll();
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnMoveListener#onMove(java.awt.event.MouseEvent)
     */
    @Override
    public void onMove(MouseEvent me) {
        handleControlMoved(me);
    }

    private void handleControlReleased(MouseEvent e) {
        // put action on released
    }

    private void handleControlMoved(MouseEvent e) {

        if (lineSymbolWidget == null) {
            return;
        }

        LineSymbolWidgetGeometry lineSymbolPadWidget = lineSymbolWidget
                .getControl();

        if (lineSymbolPadWidget == null) {
            return;
        }
        if (e == null) {
            return;
        }

        if (!lineSymbolWidget.getWidgetFolder().getBounds2D()
                .contains(e.getX(), e.getY())) {
            return;
        }

        lineSymbolPadWidget.unlockAllSensibleShape();

        if (lineSymbolPadWidget.getNorthSensibleShape() != null
                && lineSymbolPadWidget.getNorthSensibleShape().contains(
                                                                        new Point2D.Double(e.getX(), e.getY()))) {
            lineSymbolPadWidget.setLockNorthSensible(true);
        }
        if (lineSymbolPadWidget.getEastSensibleShape() != null
                && lineSymbolPadWidget.getEastSensibleShape().contains(
                                                                       new Point2D.Double(e.getX(), e.getY()))) {

            lineSymbolPadWidget.setLockEastSensible(true);
        }
        if (lineSymbolPadWidget.getSouthSensibleShape() != null
                && lineSymbolPadWidget.getSouthSensibleShape().contains(
                                                                        new Point2D.Double(e.getX(), e.getY()))) {

            lineSymbolPadWidget.setLockSouthSensible(true);
        }
        if (lineSymbolPadWidget.getWestSensibleShape() != null
                && lineSymbolPadWidget.getWestSensibleShape().contains(
                                                                       new Point2D.Double(e.getX(), e.getY()))) {

            lineSymbolPadWidget.setLockWestSensible(true);
        }

        // if(movePadWidget.getBaseShape().contains(new
        // Point2D.Double(e.getX(),e.getY())))
        // passiveTranslate();
        // else
        // unPassiveTranslate();

        getWindow2D().getView2D().getDevice2D().repaint();
    }

    /** divergence */
    private int divergence = 0;

    /** divergence constant increment */
    private int divergenceIncrement = 50;

    /**
     * inflate divergence with the divergence constant increment
     * 
     * @see #divergence
     * @see #divergenceIncrement
     */
    private void inflate() {
        divergence = divergence + divergenceIncrement;
        getWindow2D().getDevice2D().repaintDevice();
    }

    /**
     * deflate divergence with the divergence constant increment
     * 
     * @see #divergence
     * @see #divergenceIncrement
     */
    private void deflate() {
        divergence = divergence - divergenceIncrement;
        getWindow2D().getDevice2D().repaintDevice();
    }

    /**
     * handle control move
     * 
     * @param e
     *            the mouse event
     */
    private void handleControlMove(MouseEvent e) {

        if (lineSymbolWidget == null) {
            return;
        }

        LineSymbolWidgetGeometry control = lineSymbolWidget.getControl();

        if (control == null) {
            return;
        }

        if (control.getNorthSensibleShape() != null
                && control.getNorthSensibleShape().contains(
                                                            new Point2D.Double(e.getX(), e.getY()))) {
            inflate();
            // new SmoothScroll(NORTH).start();
            return;

        }
        else if (control.getEastSensibleShape() != null
                && control.getEastSensibleShape().contains(
                                                           new Point2D.Double(e.getX(), e.getY()))) {
            new SmoothScroll(EAST).start();
            return;

        }
        else if (control.getSouthSensibleShape() != null
                && control.getSouthSensibleShape().contains(
                                                            new Point2D.Double(e.getX(), e.getY()))) {
            deflate();
            // new SmoothScroll(SOUTH).start();
            return;
        }
        else if (control.getWestSensibleShape() != null
                && control.getWestSensibleShape().contains(
                                                           new Point2D.Double(e.getX(), e.getY()))) {
            //
            new SmoothScroll(WEST).start();
            return;

        }

    }

}
