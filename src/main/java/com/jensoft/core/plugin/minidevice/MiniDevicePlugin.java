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
 * MiniDevicePlugin embed a widget display of the current activate projection in
 * initial scale mode.
 */
public class MiniDevicePlugin extends AbstractPlugin implements Device {

    /** the outline color of the current projection bound */
    private Color miniCurrentDeviceDrawColor;

    /** the outline color of the initial projection bound */
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

    /** the private device widget projection*/
    private Projection.Linear miniDeviceProjection;

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
     * get the outline color of the initial projection bound
     * 
     * @return the outline color for initial projection bound
     */
    public Color getMiniInitialDeviceDrawColor() {
        return miniInitialDeviceDrawColor;
    }

    /**
     * set the outline color for the initial projection bound
     * 
     * @param miniInitialDeviceDrawColor
     */
    public void setMiniInitialDeviceDrawColor(Color miniInitialDeviceDrawColor) {
        this.miniInitialDeviceDrawColor = miniInitialDeviceDrawColor;
    }

    /**
     * get the outline of the current projection bound
     * 
     * @return the outline color for the current projection bound
     */
    public Color getMiniCurrentDeviceDrawColor() {
        return miniCurrentDeviceDrawColor;
    }

    /**
     * set the outline color for the current projection bound
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
     * return the private projection associate with the device widget which is a
     * representation of the initial and active bound of the projection.
     * 
     * @return the device widget private projection
     */
    public Projection getPrivateProjection() {
        if (miniDeviceProjection == null) {
            miniDeviceProjection = new Projection.Linear();
        }

        miniDeviceProjection.setDevice2D(this);
        if (getProjection() instanceof Projection.Linear) {
            Projection.Linear wl = (Projection.Linear) getProjection();
            double iminX = wl.getInitialMinX();
            double imaxX = wl.getInitialMaxX();
            double iminY = wl.getInitialMinY();
            double imaxY = wl.getInitialMaxY();

            miniDeviceProjection.bound(iminX, imaxX, iminY, imaxY);

            double minX = getProjection().getMinX();
            double maxX = getProjection().getMaxX();
            double minY = getProjection().getMinY();
            double maxY = getProjection().getMaxY();

            miniDeviceProjection.bound(minX, maxX, minY, maxY);
        }

        return miniDeviceProjection;
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {
        if (viewPart != ViewPart.Device) {
            return;
        }
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.device.Device#repaintDevice(int, int, int, int)
     */
    @Override
    public void repaintDevice(int x, int y, int w, int h) {

    }

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#repaintDevice()
     */
    @Override
    public void repaintDevice() {

    }

}
