/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.device;

/**
 * <code>Device</code>
 * @author Sebastien Janaud
 * @since 1.0
 */
public interface Device {

    /**
     * get the device origin X
     * 
     * @return the device origin X
     */
    public int getOriginX();

    /**
     * get the device origin Y
     * 
     * @return the device origin Y
     */
    public int getOriginY();

    /**
     * get the device width
     * 
     * @return the device width
     */
    public int getDeviceWidth();

    /**
     * get the device height
     * 
     * @return the device height
     */
    public int getDeviceHeight();

    /**
     * repaint the device
     */
    public void repaintDevice();

    /**
     * repaint the device with specified clip rectangle
     */
    public void repaintDevice(int x, int y, int w, int h);

    /**
     * get the {@link DeviceMenuManager}
     */
    public DeviceMenuManager getDeviceMenuManager();

}
