/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics.painter;

import java.awt.Graphics2D;

import com.jensoft.core.glyphmetrics.AbstractMetricsPath;

/**
 * tagging interface that defines painting path operation
 * 
 * @author Sebastien Janaud
 */
public interface MetricsPathPainter {

    /**
     * path painting operation
     * 
     * @param g2d
     *            the graphics context
     * @param metricsPath
     *            the metrics path
     */
    public void paintPath(Graphics2D g2d, AbstractMetricsPath metricsPath);
}
