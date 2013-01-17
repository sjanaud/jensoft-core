/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.radar.painter.dimension;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.radar.Radar;
import com.jensoft.core.plugin.radar.RadarDimension;
import com.jensoft.core.plugin.radar.painter.AbstractRadarDimensionPainter;

/**
 * AbstractDimensionPainter
 */
public abstract class AbstractDimensionPainter extends
        AbstractRadarDimensionPainter {

    /**
     * paint dimension
     * 
     * @param g2d
     * @param radar
     */
    protected abstract void paintDimension(Graphics2D g2d, Radar radar,
            RadarDimension radarDimension);

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.radar.painter.AbstractRadarDimensionPainter
     * #paintRadarDimension(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.radar.Radar,
     * com.jensoft.sw2d.core.plugin.radar.RadarDimension)
     */
    @Override
    public final void paintRadarDimension(Graphics2D g2d, Radar radar,
            RadarDimension radarDimension) {
        paintDimension(g2d, radar, radarDimension);
    }

}
