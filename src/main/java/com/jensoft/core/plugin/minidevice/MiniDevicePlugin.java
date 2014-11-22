/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.minidevice;

import java.awt.Color;
import java.awt.Graphics2D;

import com.jensoft.core.device.Device;
import com.jensoft.core.device.DeviceMenuManager;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * MiniDevicePlugin embed a widget display of the current activate window in
 * initial scale mode.
 */
public class MiniDevicePlugin extends AbstractPlugin implements Device {

    /** the outline color of the current window bound */
    private Color miniCurrentDeviceDrawColor;

    /** the outline color of the initial window bound */
    private Color miniInitialDeviceDrawColor;

    /** x ratio dimension for the device widget */
    int miniDeviceRatioX = 5;

    /** y ratio dimension for the device widget */
    int miniDeviceRatioY = 5;

    /** x pixel dimension for the device widget */
    int miniDevicePixelX = 120;

    /** y pixel dimension for the device widget */
    int miniDevicePixelY = 80;

    /** the device widget dimension type */
    private DimensionType dimentionType = DimensionType.Ratio;

    /** the private device widget window 2D */
    private Projection.Linear miniDeviceWindow2D;

    private MiniDeviceWidget miniDeviceWidget;

    /**
     * widget can be with fixed dimension in pixel or ratio dimension of the
     * device2D host
     */
    public enum DimensionType {
        Ratio, Fix;
    }

    /**
     * MiniDevice Layout
     */
    public MiniDevicePlugin() {
        setName("MiniDeviceLayout");
        createControls();

    }

    // @Override
    // public void createWidgets(WidgetRegistry widgetRegistry) {
    //
    // miniDeviceWidget = new MiniDeviceWidget();
    // miniDeviceWidget.setxIndex(100);
    // miniDeviceWidget.setyIndex(0);
    // widgetRegistry.register(miniDeviceWidget);
    // }

    /**
     * set the widget dimension
     * 
     * @param dimType
     * @param x
     * @param y
     */
    public void setDimensionType(DimensionType dimType, int x, int y) {
        dimentionType = dimType;
        if (dimType == DimensionType.Ratio) {
            miniDeviceRatioX = x;
            miniDeviceRatioY = y;
        }
        else if (dimType == DimensionType.Fix) {
            miniDevicePixelX = x;
            miniDevicePixelY = y;
        }

    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.device.Device2D#getDeviceMenuManager()
     */
    @Override
    public DeviceMenuManager getDeviceMenuManager() {
        return null;
    }

    public DimensionType getDimentionType() {
        return dimentionType;
    }

    public int getMiniDeviceRatioX() {
        return miniDeviceRatioX;
    }

    public void setMiniDeviceRatioX(int miniDeviceRatioX) {
        this.miniDeviceRatioX = miniDeviceRatioX;
    }

    public int getMiniDeviceRatioY() {
        return miniDeviceRatioY;
    }

    public void setMiniDeviceRatioY(int miniDeviceRatioY) {
        this.miniDeviceRatioY = miniDeviceRatioY;
    }

    public int getMiniDevicePixelX() {
        return miniDevicePixelX;
    }

    public void setMiniDevicePixelX(int miniDevicePixelX) {
        this.miniDevicePixelX = miniDevicePixelX;
    }

    public int getMiniDevicePixelY() {
        return miniDevicePixelY;
    }

    public void setMiniDevicePixelY(int miniDevicePixelY) {
        this.miniDevicePixelY = miniDevicePixelY;
    }

    /**
     * create controls for this tool
     */
    public void createControls() {

    }

    /**
     * get the outline color of the initial window bound
     * 
     * @return the outline color for initial window bound
     */
    public Color getMiniInitialDeviceDrawColor() {
        return miniInitialDeviceDrawColor;
    }

    /**
     * set the outline color for the initial window bound
     * 
     * @param miniInitialDeviceDrawColor
     */
    public void setMiniInitialDeviceDrawColor(Color miniInitialDeviceDrawColor) {
        this.miniInitialDeviceDrawColor = miniInitialDeviceDrawColor;
    }

    /**
     * get the outline of the current window bound
     * 
     * @return the outline color for the current window bound
     */
    public Color getMiniCurrentDeviceDrawColor() {
        return miniCurrentDeviceDrawColor;
    }

    /**
     * set the outline color for the current window bound
     * 
     * @param miniDeviceDrawColor
     */
    public void setMiniCurrentDeviceDrawColor(Color miniDeviceDrawColor) {
        miniCurrentDeviceDrawColor = miniDeviceDrawColor;
    }

    /**
     * get the device widget height
     */
    @Override
    public int getDeviceHeight() {
        return (int) miniDeviceWidget.getWidgetFolder().getHeight();
    }

    /**
     * get the device widget width
     */
    @Override
    public int getDeviceWidth() {
        return (int) miniDeviceWidget.getWidgetFolder().getWidth();
    }

    /**
     * get the device widget x origin
     */
    @Override
    public int getOriginX() {
        return (int) miniDeviceWidget.getWidgetFolder().getX();
    }

    /**
     * get the device widget y origin
     */
    @Override
    public int getOriginY() {
        return (int) miniDeviceWidget.getWidgetFolder().getY();
    }

    /**
     * return the private window associate with the device widget which is a
     * representation of the initial and active bound of the window.
     * 
     * @return the device widget private window
     */
    public Projection getPrivateWindow() {
        if (miniDeviceWindow2D == null) {
            miniDeviceWindow2D = new Projection.Linear();
            miniDeviceWindow2D.setName("minidevicewindow");
        }

        miniDeviceWindow2D.setDevice2D(this);
        // miniDeviceWindow2D.setProjection(getWindow2D()
        // .getProjection());
        if (getProjection() instanceof Projection.Linear) {
            Projection.Linear wl = (Projection.Linear) getProjection();
            double iminX = wl.getInitialMinX();
            double imaxX = wl.getInitialMaxX();
            double iminY = wl.getInitialMinY();
            double imaxY = wl.getInitialMaxY();

            miniDeviceWindow2D.bound(iminX, imaxX, iminY, imaxY);

            double minX = getProjection().getMinX();
            double maxX = getProjection().getMaxX();
            double minY = getProjection().getMinY();
            double maxY = getProjection().getMaxY();

            miniDeviceWindow2D.bound(minX, maxX, minY, maxY);
        }

        return miniDeviceWindow2D;
    }

    @Override
    protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {
        if (viewPart != ViewPart.Device) {
            return;
        }

        // System.out.println("paint mini device ");
        // miniDeviceWidget.paint(v2d, g2d);
    }

    @Override
    public void repaintDevice(int x, int y, int w, int h) {

    }

    @Override
    public void repaintDevice() {

    }

}
