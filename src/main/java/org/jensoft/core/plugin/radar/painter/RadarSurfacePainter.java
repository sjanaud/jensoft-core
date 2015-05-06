/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.radar.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.radar.Radar;
import org.jensoft.core.plugin.radar.RadarSurface;

public interface RadarSurfacePainter {

    void paintRadarSurface(Graphics2D g2d, Radar radar,
            RadarSurface radarSurface);
}
