/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.glyphmetrics.painter.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import org.jensoft.core.glyphmetrics.GlyphGeometry;
import org.jensoft.core.glyphmetrics.GlyphMetric;
import org.jensoft.core.glyphmetrics.painter.GlyphMetricDraw;

/**
 * glyph default draw
 * 
 * @author Sebastien Janaud
 */
public class GlyphDefaultDraw extends GlyphMetricDraw {

    /** outline glyph draw color */
    private Color drawColor;

    /**
     * create glyph draw with specified draw color
     * 
     * @param drawColor
     */
    public GlyphDefaultDraw(Color drawColor) {
        super();
        this.drawColor = drawColor;
    }

    /**
     * get the draw color
     * 
     * @return draw color
     */
    public Color getDrawColor() {
        return drawColor;
    }

    /**
     * set draw color
     * 
     * @param drawColor
     *            the draw color to set
     */
    public void setDrawColor(Color drawColor) {
        this.drawColor = drawColor;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.glyphmetrics.painter.GlyphMetricDraw#paintGlyphMetricDraw(java.awt.Graphics2D, com.jensoft.core.glyphmetrics.GlyphMetric)
     */
    @Override
    public void paintGlyphMetricDraw(Graphics2D g2d, GlyphMetric glyphMetric) {

        List<GlyphGeometry> glyphShapes = glyphMetric.getMetricsGlyphGeometry();

        if (glyphShapes == null) {
            return;
        }

        g2d.setStroke(new BasicStroke(1f));
        for (GlyphGeometry glyphShape : glyphShapes) {

            g2d.setColor(drawColor);
            g2d.draw(glyphShape.getGlyphShape());
        }
    }

}
