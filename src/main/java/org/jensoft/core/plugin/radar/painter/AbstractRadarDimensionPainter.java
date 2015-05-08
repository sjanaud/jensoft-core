/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.radar.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.radar.Radar;
import org.jensoft.core.plugin.radar.RadarDimension;

public abstract class AbstractRadarDimensionPainter implements
        RadarDimensionPainter {

  
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.radar.painter.RadarDimensionPainter#paintRadarDimension(java.awt.Graphics2D, org.jensoft.core.plugin.radar.Radar, org.jensoft.core.plugin.radar.RadarDimension)
     */
    @Override
    public void paintRadarDimension(Graphics2D g2d, Radar radar,
            RadarDimension radarDimension) {
    }

}
