/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.glyphmetrics.painter;

import java.awt.Graphics2D;

import org.jensoft.core.glyphmetrics.GlyphMetric;

/**
 * Abstract definition for glyph marker painting operation
 * 
 * @author Sebastien Janaud
 */
public abstract class GlyphMetricMarkerPainter extends AbstractGlyphPainter {

    /**
     * paint glyph metrics marker
     * 
     * @param g2d
     *            the graphics context
     * @param glyphMetric
     *            the glyph metrics
     */
    protected abstract void paintGlyphMetricMarker(Graphics2D g2d, GlyphMetric glyphMetric);

   
    /* (non-Javadoc)
     * @see org.jensoft.core.glyphmetrics.painter.AbstractGlyphPainter#paintGlyphMetric(java.awt.Graphics2D, org.jensoft.core.glyphmetrics.GlyphMetric)
     */
    @Override
    public void paintGlyphMetric(Graphics2D g2d, GlyphMetric glyphMetric) {
        paintGlyphMetricMarker(g2d, glyphMetric);
    }

}
