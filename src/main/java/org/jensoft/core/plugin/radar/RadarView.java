/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.radar;

import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.projection.Projection;
import org.jensoft.core.view.View;

/**
 * <code>RadarView<code> defines a ready view to hold radar chart
 * 
 * @since 1.0
 *
 * @author sebastien janaud
 */
public class RadarView extends View {

    /** uid */
    private static final long serialVersionUID = -8598481995145789973L;

    /** projection associate to this view and radar plugin */
    private Projection.Linear radarProjection;

    /** radar plugin */
    private RadarPlugin radarPlugin;

    /**
     * create radar view
     */
    public RadarView() {
        setPlaceHolderAxisNorth(60);
        setPlaceHolderAxisSouth(60);
        setPlaceHolderAxisWest(60);
        setPlaceHolderAxisEast(60);
        createProjection();
        registerPlugin();
    }

    private void createProjection() {
        radarProjection = new Projection.Linear(-1, 1, -1, 1);
        radarProjection.setName("compatible radar projection");
        registerProjection(radarProjection);
    }

    private void registerPlugin() {
        radarPlugin = new RadarPlugin();
        radarPlugin.setPriority(100);
        radarProjection.registerPlugin(radarPlugin);
    }

    /**
     * register plugin
     * 
     * @param plugin
     *            the plugin to add
     */
    public void registerPlugin(AbstractPlugin plugin) {
        radarProjection.registerPlugin(plugin);
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
     * @return the radar projection
     */
    public Projection.Linear getProjection() {
        return radarProjection;
    }

    /**
     * @return the radar plugin
     */
    public RadarPlugin getRadarPlugin() {
        return radarPlugin;
    }

}
