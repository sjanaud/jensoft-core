/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.zoom.wheel;

import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.PluginEvent;

/**
 * <code>ZoomWheelSynchronizer</code>
 * 
 * @author sebastien janaud
 */
public class ZoomWheelSynchronizer implements ZoomWheelListener {

    /** the zoom wheel plug ins to synchronize */
    private List<ZoomWheelPlugin> zoomWheelList;

    /** enabled flag */
    private boolean enabled = true;

    /** dispatchingEvent flag */
    private boolean dispathingEvent = false;

    /**
     * create new synchronizer for the specified zoom wheels
     * 
     * @param zoomWheels
     */
    public ZoomWheelSynchronizer(ZoomWheelPlugin... zoomWheels) {
        zoomWheelList = new ArrayList<ZoomWheelPlugin>();
        for (int i = 0; i < zoomWheels.length; i++) {
            ZoomWheelPlugin wheel = zoomWheels[i];
            wheel.addZoomWheelListener(this);
            zoomWheelList.add(wheel);
        }
    }

    /**
     * create ZoomWheelPlugin synchronizer for the specified zoomWheels
     * <p>
     * list parameter is only read, an internal list is created and register given ZoomWheelPlugin plug in instance.
     * 
     * @see ZoomWheelSynchronizer#zoomWheelList
     * @param zoomWheels
     *            the zoomWheels plugins to synchronize when actions occurs
     */
    public ZoomWheelSynchronizer(List<ZoomWheelPlugin> zoomWheels) {
        zoomWheelList = new ArrayList<ZoomWheelPlugin>();
        for (int i = 0; i < zoomWheels.size(); i++) {
            ZoomWheelPlugin wheel = zoomWheels.get(i);
            wheel.addZoomWheelListener(this);
            wheel.addPluginListener(this);
            zoomWheelList.add(wheel);
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.PluginListener#pluginSelected(com.jensoft.sw2d.core.plugin.PluginEvent)
     */
    @Override
    public void pluginSelected(PluginEvent<ZoomWheelPlugin> pluginEvent) {

    }

   
    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.PluginListener#pluginUnlockSelected(com.jensoft.sw2d.core.plugin.PluginEvent)
     */
    @Override
    public void pluginUnlockSelected(PluginEvent<ZoomWheelPlugin> pluginEvent) {

    }

   
    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.zoom.wheel.ZoomWheelListener#zoomIn(com.jensoft.sw2d.core.plugin.zoom.wheel.ZoomWheelEvent)
     */
    @Override
    public void zoomIn(ZoomWheelEvent pluginEvent) {
        if (enabled && !dispathingEvent) {
            dispathingEvent = true;
            ZoomWheelPlugin sourceZoomWheelPlugin = (ZoomWheelPlugin) pluginEvent
                    .getSource();
            for (ZoomWheelPlugin targetZoomWheelPlugin : zoomWheelList) {
                if (!targetZoomWheelPlugin.equals(sourceZoomWheelPlugin)) {
                    targetZoomWheelPlugin.zoomIn();
                    targetZoomWheelPlugin.fireZoomIn();
                }
            }
            dispathingEvent = false;
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.zoom.wheel.ZoomWheelListener#zoomOut(com.jensoft.sw2d.core.plugin.zoom.wheel.ZoomWheelEvent)
     */
    @Override
    public void zoomOut(ZoomWheelEvent pluginEvent) {
        if (enabled && !dispathingEvent) {
            dispathingEvent = true;
            ZoomWheelPlugin sourceZoomWheelPlugin = (ZoomWheelPlugin) pluginEvent
                    .getSource();
            for (ZoomWheelPlugin targetZoomWheelPlugin : zoomWheelList) {
                if (!targetZoomWheelPlugin.equals(sourceZoomWheelPlugin)) {
                    targetZoomWheelPlugin.zoomOut();
                    targetZoomWheelPlugin.fireZoomOut();
                }
            }
            dispathingEvent = false;
        }
    }

}
