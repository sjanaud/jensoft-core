/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.glyphmetrics.painter;

import java.awt.Graphics2D;

import org.jensoft.core.glyphmetrics.GlyphMetric;

/**
 * <code>GlyphMetricPainter</code> tagging interface that defines painting glyph operation
 * 
 * @author Sebastien Janaud
 */
public interface GlyphMetricPainter {

    /**
     * glyph painting operation
     * @param g2d
     * @param glyphMetric
     */
    public void paintGlyphMetric(Graphics2D g2d, GlyphMetric glyphMetric);
}
