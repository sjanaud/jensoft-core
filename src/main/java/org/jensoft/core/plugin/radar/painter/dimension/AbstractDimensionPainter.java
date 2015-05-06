/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.radar.painter.dimension;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.radar.Radar;
import org.jensoft.core.plugin.radar.RadarDimension;
import org.jensoft.core.plugin.radar.painter.AbstractRadarDimensionPainter;

/**
 * <code>AbstractDimensionPainter</code>
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

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.radar.painter.AbstractRadarDimensionPainter#paintRadarDimension(java.awt.Graphics2D, com.jensoft.core.plugin.radar.Radar, com.jensoft.core.plugin.radar.RadarDimension)
     */
    @Override
    public final void paintRadarDimension(Graphics2D g2d, Radar radar,
            RadarDimension radarDimension) {
        paintDimension(g2d, radar, radarDimension);
    }

}
