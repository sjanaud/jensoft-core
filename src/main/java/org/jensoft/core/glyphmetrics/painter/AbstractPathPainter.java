/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.glyphmetrics.painter;

import java.awt.Graphics2D;

import org.jensoft.core.glyphmetrics.AbstractMetricsPath;

/**
 * Abstract definition for painting path operation
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractPathPainter implements MetricsPathPainter {

    
    /* (non-Javadoc)
     * @see org.jensoft.core.glyphmetrics.painter.MetricsPathPainter#paintPath(java.awt.Graphics2D, org.jensoft.core.glyphmetrics.AbstractMetricsPath)
     */
    @Override
    public void paintPath(Graphics2D g2d, AbstractMetricsPath metricsPath) {
    }

}
