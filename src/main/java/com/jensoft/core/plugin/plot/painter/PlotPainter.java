/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.plot.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.plot.spline.AbstractPlot;

/**
 * tagging interface for plot painting operation
 * 
 * @author Sebastien Janaud
 */
public interface PlotPainter {

    /**
     * plot painting operation
     * 
     * @param g2d
     *            the graphics context
     * @param plot
     *            the plot
     */
    void paintPlot(Graphics2D g2d, AbstractPlot plot);
}
