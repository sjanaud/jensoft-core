/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.radar;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;

/**
 * RadarView
 */
public class RadarView extends View2D {

    /** uid */
    private static final long serialVersionUID = -8598481995145789973L;

    /** window2D associate to this view and radar plugin */
    private Window2D.Linear radarWindow2D;

    /** radar plugin */
    private RadarPlugin radarPlugin;

    public RadarView() {

        setPlaceHolderAxisNorth(60);
        setPlaceHolderAxisSouth(60);
        setPlaceHolderAxisWest(60);
        setPlaceHolderAxisEast(60);

        createWindow();
        registerPlugin();
    }

    private void createWindow() {
        radarWindow2D = new Window2D.Linear(-1, 1, -1, 1);
        radarWindow2D.setName("compatible pie window");
        registerWindow2D(radarWindow2D);
    }

    private void registerPlugin() {
        radarPlugin = new RadarPlugin();
        radarPlugin.setPriority(100);
        radarWindow2D.registerPlugin(radarPlugin);
    }

    /**
     * register plugin
     * 
     * @param plugin
     *            the plugin to add
     */
    public void registerPlugin(AbstractPlugin plugin) {
        radarWindow2D.registerPlugin(plugin);
    }

    /**
     * add radar
     * 
     * @param radar
     *            the radar to add
     */
    public void addRadar(Radar radar) {
        radarPlugin.addRadar(radar);
    }

    /**
     * @return the radar window
     */
    public Window2D.Linear getWindow2D() {
        return radarWindow2D;
    }

    /**
     * @return the radar plugin
     */
    public RadarPlugin getRadarPlugin() {
        return radarPlugin;
    }

}
