/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.glyphmetrics.painter;

import java.awt.Graphics2D;

import org.jensoft.core.glyphmetrics.GlyphMetric;

/**
 * Abstract definition for painting glyph operation
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractGlyphPainter implements GlyphMetricPainter {

    
    /* (non-Javadoc)
     * @see com.jensoft.core.glyphmetrics.painter.GlyphMetricPainter#paintGlyphMetric(java.awt.Graphics2D, com.jensoft.core.glyphmetrics.GlyphMetric)
     */
    @Override
    public void paintGlyphMetric(Graphics2D g2d, GlyphMetric glyphMetric) {
    }

}
