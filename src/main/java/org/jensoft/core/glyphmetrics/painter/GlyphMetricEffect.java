/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.glyphmetrics.painter;

import java.awt.Graphics2D;

import org.jensoft.core.glyphmetrics.GlyphMetric;

/**
 * Abstract definition for effect painting operation
 * 
 * @author Sebastien Janaud
 */
public abstract class GlyphMetricEffect extends AbstractGlyphPainter {

    /**
     * effect the specified glyph
     * 
     * @param g2d
     *            the graphics context
     * @param glyphMetric
     *            the glyph metrics
     */
    protected abstract void paintGlyphMetricEffect(Graphics2D g2d, GlyphMetric glyphMetric);

   
    /* (non-Javadoc)
     * @see com.jensoft.core.glyphmetrics.painter.AbstractGlyphPainter#paintGlyphMetric(java.awt.Graphics2D, com.jensoft.core.glyphmetrics.GlyphMetric)
     */
    @Override
    public final void paintGlyphMetric(Graphics2D g2d, GlyphMetric glyphMetric) {
        paintGlyphMetricEffect(g2d, glyphMetric);
    }

}
