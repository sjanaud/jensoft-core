/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.glyphmetrics.painter.fill;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import org.jensoft.core.glyphmetrics.GlyphGeometry;
import org.jensoft.core.glyphmetrics.GlyphMetric;
import org.jensoft.core.glyphmetrics.painter.GlyphMetricFill;

/**
 * default glyph fill
 * 
 * @author Sebastien Janaud
 */
public class GlyphDefaultFill extends GlyphMetricFill {

    /** glyph fill color */
    private Color fillColor;

    /**
     * create default glyph fill with specified fill color
     * 
     * @param fillColor
     *            the fill color to set
     */
    public GlyphDefaultFill(Color fillColor) {
        super();
        this.fillColor = fillColor;
    }

    /**
     * get fill color
     * 
     * @return fill color
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * set fill color
     * 
     * @param fillColor
     *            the fill color to set
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.glyphmetrics.painter.GlyphMetricFill#paintGlyphMetricFill(java.awt.Graphics2D, com.jensoft.core.glyphmetrics.GlyphMetric)
     */
    @Override
    public void paintGlyphMetricFill(Graphics2D g2d, GlyphMetric glyphMetric) {
        if (fillColor == null) {
            return;
        }

        g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                             java.awt.AlphaComposite.SRC_OVER, 1f));
        List<GlyphGeometry> glyphShapes = glyphMetric.getMetricsGlyphGeometry();

        if (glyphShapes == null) {
            return;
        }

        g2d.setStroke(new BasicStroke(1f));
        for (GlyphGeometry glyphShape : glyphShapes) {

            g2d.setColor(fillColor);
            g2d.fill(glyphShape.getGlyphShape());
        }
    }

}
