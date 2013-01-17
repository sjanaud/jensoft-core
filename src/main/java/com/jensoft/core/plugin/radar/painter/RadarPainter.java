/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.radar.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.radar.Radar;

public interface RadarPainter {

    void paintRadar(Graphics2D g2d, Radar radar);
}
