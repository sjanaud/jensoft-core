/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.radar.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.radar.Radar;

public abstract class AbstractRadarPainter implements RadarPainter {

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.radar.painter.RadarPainter#paintRadar(java.awt.Graphics2D, com.jensoft.core.plugin.radar.Radar)
     */
    @Override
    public void paintRadar(Graphics2D g2d, Radar radar) {
    }

}
