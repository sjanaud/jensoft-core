/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.zoom.box;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.PluginEvent;
import com.jensoft.core.projection.Projection;

/**
 * <code>ZoomBoxSynchronizer</code>
 * 
 * @author sebastien janaud
 */
public class ZoomBoxSynchronizer implements ZoomBoxListener {

    /** the zoom boxes to synchronize */
    private List<ZoomBoxPlugin> boxesList;

    /** enabled flag */
    private boolean enabled = true;

    /** dispatchingEvent flag */
    private boolean dispathingEvent = false;

    /**
     * create Synchronizer for specified zoom boxes
     * 
     * @param boxes
     */
    public ZoomBoxSynchronizer(ZoomBoxPlugin... boxes) {

        boxesList = new ArrayList<ZoomBoxPlugin>();
        for (int i = 0; i < boxes.length; i++) {
            ZoomBoxPlugin zoomBoxPlugin = boxes[i];
            zoomBoxPlugin.addZoomBoxListener(this);
            zoomBoxPlugin.addPluginListener(this);
            boxesList.add(zoomBoxPlugin);
        }

    }

    /**
     * create ZoomBox synchronizer for the specified boxes
     * <p>
     * list parameter is only read, an internal list is created and register given ZoomBox plug in instance.
     * 
     * @see ZoomBoxSynchronizer#boxesList
     * @param boxes
     *            the boxes plugins to synchronize when actions occurs
     */
    public ZoomBoxSynchronizer(List<ZoomBoxPlugin> boxes) {
        boxesList = new ArrayList<ZoomBoxPlugin>();
        for (int i = 0; i < boxes.size(); i++) {
            ZoomBoxPlugin zoomBoxPlugin = boxes.get(i);
            zoomBoxPlugin.addZoomBoxListener(this);
            zoomBoxPlugin.addPluginListener(this);
            boxesList.add(zoomBoxPlugin);
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.PluginListener#pluginSelected(com.jensoft.core.plugin.PluginEvent)
     */
    @Override
    public void pluginSelected(PluginEvent<ZoomBoxPlugin> pluginEvent) {
        if (enabled && !dispathingEvent) {
            dispathingEvent = true;
            ZoomBoxPlugin zoomBoxPlugin = (ZoomBoxPlugin) pluginEvent.getSource();
            for (ZoomBoxPlugin zbp : boxesList) {
                if (!zbp.equals(zoomBoxPlugin)) {
                    zbp.lockSelected();
                }
            }
            dispathingEvent = false;
        }
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.PluginListener#pluginUnlockSelected(com.jensoft.core.plugin.PluginEvent)
     */
    @Override
    public void pluginUnlockSelected(PluginEvent<ZoomBoxPlugin> pluginEvent) {
        if (enabled && !dispathingEvent) {
            dispathingEvent = true;
            ZoomBoxPlugin zoomBoxPlugin = (ZoomBoxPlugin) pluginEvent.getSource();
            for (ZoomBoxPlugin zbp : boxesList) {
                if (!zbp.equals(zoomBoxPlugin)) {
                    zbp.unlockSelected();
                }
            }
            dispathingEvent = false;
        }
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.zoom.box.ZoomBoxListener#zoomStart(com.jensoft.core.plugin.zoom.box.ZoomBoxEvent)
     */
    @Override
    public void zoomStart(ZoomBoxEvent pluginEvent) {
        if (enabled && !dispathingEvent) {
            dispathingEvent = true;
            ZoomBoxPlugin zoomBoxPlugin = (ZoomBoxPlugin) pluginEvent.getSource();
            for (ZoomBoxPlugin zbp : boxesList) {
                if (!zbp.equals(zoomBoxPlugin)) {
                    Projection w2d = zbp.getProjection();
                    
                    //interpretation base on press in user system
                    Point2D userBoxStartSource = zoomBoxPlugin .getBoxStartUserPoint();
                    Point2D deviceStartTarget = w2d.userToPixel(userBoxStartSource);
                    zbp.processZoomStart(new Point2D.Double((int) deviceStartTarget.getX(),(int) deviceStartTarget.getY()));
                    
                    //interpretation based on press in pixel coordinate
                    Point2D deviceBoxStartSource = zoomBoxPlugin .getBoxStartDevicePoint();
                    zbp.processZoomStart(deviceBoxStartSource);
                    
                    w2d.getDevice2D().repaintDevice();
                }
            }
            dispathingEvent = false;
        }
    }
    
    

   

	/* (non-Javadoc)
     * @see com.jensoft.core.plugin.zoom.box.ZoomBoxListener#zoomBounded(com.jensoft.core.plugin.zoom.box.ZoomBoxEvent)
     */
    @Override
    public void zoomBounded(ZoomBoxEvent pluginEvent) {
        if (enabled && !dispathingEvent) {
            dispathingEvent = true;
            ZoomBoxPlugin zoomBoxPlugin = (ZoomBoxPlugin) pluginEvent.getSource();
            for (ZoomBoxPlugin zbp : boxesList) {
                if (!zbp.equals(zoomBoxPlugin)) {
                    Projection w2d = zbp.getProjection();
                    
                    //interpretation base on press in user system
                    Point2D userBoxCurrentSource = zoomBoxPlugin.getBoxCurrentUserPoint();
                    Point2D deviceCurrentTarget = w2d.userToPixel(userBoxCurrentSource);
                    zbp.processZoomBound(new Point2D.Double((int) deviceCurrentTarget.getX(),(int) deviceCurrentTarget.getY()));
                    
                    //interpretation based on press in pixel coordinate
                    Point2D deviceBoxCurrentSource = zoomBoxPlugin .getBoxCurrentDevicePoint();
                    zbp.processZoomBound(deviceBoxCurrentSource);
                    
                    w2d.getDevice2D().repaintDevice();
                }
            }
            dispathingEvent = false;
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.zoom.box.ZoomBoxListener#zoomIn(com.jensoft.core.plugin.zoom.box.ZoomBoxEvent)
     */
    @Override
    public void zoomIn(ZoomBoxEvent pluginEvent) {
        if (enabled && !dispathingEvent) {
            dispathingEvent = true;
            ZoomBoxPlugin zoomBoxPlugin = (ZoomBoxPlugin) pluginEvent.getSource();
            for (ZoomBoxPlugin zbp : boxesList) {
                if (!zbp.equals(zoomBoxPlugin)) {
                    zbp.processZoomIn();
                    zbp.fireZoomIn();
                }
            }
            dispathingEvent = false;
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.zoom.box.ZoomBoxListener#zoomOut(com.jensoft.core.plugin.zoom.box.ZoomBoxEvent)
     */
    @Override
    public void zoomOut(ZoomBoxEvent pluginEvent) {
        if (enabled && !dispathingEvent) {
            dispathingEvent = true;
            ZoomBoxPlugin zoomBoxPlugin = (ZoomBoxPlugin) pluginEvent.getSource();
            for (ZoomBoxPlugin zbp : boxesList) {
                if (!zbp.equals(zoomBoxPlugin)) {
                    zbp.processZoomOut();
                    zbp.fireZoomOut();
                }
            }
            dispathingEvent = false;
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.zoom.box.ZoomBoxListener#zoomHistory(com.jensoft.core.plugin.zoom.box.ZoomBoxEvent)
     */
    @Override
    public void zoomHistory(ZoomBoxEvent pluginEvent) {
        if (enabled && !dispathingEvent) {
            dispathingEvent = true;
            ZoomBoxPlugin zoomBoxPlugin = (ZoomBoxPlugin) pluginEvent.getSource();
            for (ZoomBoxPlugin zbp : boxesList) {
                if (!zbp.equals(zoomBoxPlugin)) {
                    zbp.processZoomHistory(zoomBoxPlugin.getCurentBoxIndex());
                    zbp.fireZoomHistory();
                }
            }
            dispathingEvent = false;
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.zoom.box.ZoomBoxListener#zoomClearHistory(com.jensoft.core.plugin.zoom.box.ZoomBoxEvent)
     */
    @Override
    public void zoomClearHistory(ZoomBoxEvent pluginEvent) {
        if (enabled && !dispathingEvent) {
            dispathingEvent = true;
            ZoomBoxPlugin zoomBoxPlugin = (ZoomBoxPlugin) pluginEvent.getSource();
            for (ZoomBoxPlugin zbp : boxesList) {
                if (!zbp.equals(zoomBoxPlugin)) {
                    zbp.processZoomClearHistory();
                    zbp.fireZoomClearHistory();
                }
            }
            dispathingEvent = false;
        }
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
