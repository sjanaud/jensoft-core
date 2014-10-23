/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.title.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.legend.title.Legend;

/**
 * legend painter defines legend painting operation
 * 
 * @author Sebastien Janaud
 */
public interface LegendPainter {

    /**
     * paint legend
     * 
     * @param g2d
     *            the graphics context
     * @param legend
     *            the legend to paint
     */
    void paintLegend(Graphics2D g2d, Legend legend);
}
