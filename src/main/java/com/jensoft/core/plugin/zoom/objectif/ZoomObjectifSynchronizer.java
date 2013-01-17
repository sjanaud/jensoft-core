/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.zoom.objectif;

import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.PluginEvent;

/**
 * <code>ZoomObjectifSynchronizer</code>
 * 
 * @author sebastien janaud
 */
public class ZoomObjectifSynchronizer implements ZoomObjectifListener {

    /** the zoom objectif plug ins to synchronize */
    private List<ZoomObjectifPlugin> zoomObjectifsList;

    /** enabled flag */
    private boolean enabled = true;

    /** dispatchingEvent flag */
    private boolean dispathingEvent = false;

    /**
     * create new synchronizer for the specified zoom objectif
     * 
     * @param zoomObjectifs
     */
    public ZoomObjectifSynchronizer(ZoomObjectifPlugin... zoomObjectifs) {
        zoomObjectifsList = new ArrayList<ZoomObjectifPlugin>();
        for (int i = 0; i < zoomObjectifs.length; i++) {
            ZoomObjectifPlugin objectif = zoomObjectifs[i];
            objectif.addZoomObjectifListener(this);
            zoomObjectifsList.add(objectif);
        }
    }

    /**
     * create ZoomObjectifSynchronizer synchronizer for the specified zoomObjectifs
     * <p>
     * list parameter is only read, an internal list is created and register given ZoomObjectifPlugin plug in instance.
     * 
     * @param zoomObjectifs
     *            the objectifs plug ins to synchronize when actions occurs
     */
    public ZoomObjectifSynchronizer(List<ZoomObjectifPlugin> zoomObjectifs) {
        zoomObjectifsList = new ArrayList<ZoomObjectifPlugin>();
        for (int i = 0; i < zoomObjectifs.size(); i++) {
            ZoomObjectifPlugin objectif = zoomObjectifs.get(i);
            objectif.addZoomObjectifListener(this);
            objectif.addPluginListener(this);
            zoomObjectifsList.add(objectif);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.PluginListener#pluginSelected(com.jensoft.sw2d.core.plugin.PluginEvent)
     */
    @Override
    public void pluginSelected(PluginEvent<ZoomObjectifPlugin> pluginEvent) {
        if (enabled && !dispathingEvent) {
            dispathingEvent = true;
            ZoomObjectifPlugin sourceLock = (ZoomObjectifPlugin) pluginEvent.getSource();
            for (ZoomObjectifPlugin targetLock : zoomObjectifsList) {
                if (!targetLock.equals(sourceLock)) {
                    targetLock.lockSelected();
                }
            }
            dispathingEvent = false;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.PluginListener#pluginUnlockSelected(com.jensoft.sw2d.core.plugin.PluginEvent)
     */
    @Override
    public void pluginUnlockSelected(PluginEvent<ZoomObjectifPlugin> pluginEvent) {
        if (enabled && !dispathingEvent) {
            dispathingEvent = true;
            ZoomObjectifPlugin sourceLock = (ZoomObjectifPlugin) pluginEvent.getSource();
            for (ZoomObjectifPlugin targetLock : zoomObjectifsList) {
                if (!targetLock.equals(sourceLock)) {
                    targetLock.unlockSelected();
                }
            }
            dispathingEvent = false;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.zoom.objectif.ZoomObjectifListener#zoomIn(com.jensoft.sw2d.core.plugin.zoom.objectif
     * .ZoomObjectifEvent)
     */
    @Override
    public void zoomIn(ZoomObjectifEvent pluginEvent) {
        if (enabled && !dispathingEvent) {
            dispathingEvent = true;
            ZoomObjectifPlugin sourceObjectif = (ZoomObjectifPlugin) pluginEvent.getSource();
            for (ZoomObjectifPlugin targetObjectif : zoomObjectifsList) {
                if (!targetObjectif.equals(sourceObjectif)) {
                    System.out.println("source process nature : " + sourceObjectif.getProcessNature());
                    targetObjectif.zoomIn(sourceObjectif.getProcessNature());
                }
            }
            dispathingEvent = false;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.zoom.objectif.ZoomObjectifListener#zoomOut(com.jensoft.sw2d.core.plugin.zoom.objectif
     * .ZoomObjectifEvent)
     */
    @Override
    public void zoomOut(ZoomObjectifEvent pluginEvent) {
        if (enabled && !dispathingEvent) {
            dispathingEvent = true;
            ZoomObjectifPlugin sourceObjectif = (ZoomObjectifPlugin) pluginEvent.getSource();
            for (ZoomObjectifPlugin targetObjectif : zoomObjectifsList) {
                if (!targetObjectif.equals(sourceObjectif)) {
                    targetObjectif.zoomOut(sourceObjectif.getProcessNature());
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
