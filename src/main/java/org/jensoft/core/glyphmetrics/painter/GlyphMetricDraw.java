/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.glyphmetrics.painter;

import java.awt.Graphics2D;

import org.jensoft.core.glyphmetrics.GlyphMetric;

/**
 * Abstract definition for glyph draw operation
 * 
 * @author Sebastien Janaud
 */
public abstract class GlyphMetricDraw extends AbstractGlyphPainter {

    /**
     * draw the specified glyph metrics
     * 
     * @param g2d
     *            the graphics context
     * @param glyphMetric
     *            the glyph metrics to draw
     */
    abstract protected void paintGlyphMetricDraw(Graphics2D g2d, GlyphMetric glyphMetric);

   
    /* (non-Javadoc)
     * @see com.jensoft.core.glyphmetrics.painter.AbstractGlyphPainter#paintGlyphMetric(java.awt.Graphics2D, com.jensoft.core.glyphmetrics.GlyphMetric)
     */
    @Override
    public final void paintGlyphMetric(Graphics2D g2d, GlyphMetric glyphMetric) {
        paintGlyphMetricDraw(g2d, glyphMetric);
    }

}
