/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics.painter.fill;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.glyphmetrics.GlyphGeometry;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.painter.GlyphMetricFill;

/**
 * glyphFill
 * 
 * @author Sebastien Janaud
 */
public class GlyphFill extends GlyphMetricFill {

    /** fill color start */
    private Color themeColor1;

    /** fill color end */
    private Color themeColor2;

    /**
     * create the glyph fill with specified start and end colors
     * 
     * @param themeColor1
     *            the start color
     * @param themeColor2
     *            the end color
     */
    public GlyphFill(Color themeColor1, Color themeColor2) {
        super();
        this.themeColor1 = themeColor1;
        this.themeColor2 = themeColor2;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.glyphmetrics.painter.GlyphMetricFill#paintGlyphMetricFill(java.awt.Graphics2D, com.jensoft.core.glyphmetrics.GlyphMetric)
     */
    @Override
    public void paintGlyphMetricFill(Graphics2D g2d, GlyphMetric glyphMetric) {

        if (glyphMetric == null) {
            return;
        }

        g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                             java.awt.AlphaComposite.SRC_OVER, 1f));

        // List<Shape> glyphShapes = glyphMetric.getGlyphShapes();
        g2d.setStroke(new BasicStroke(1f));

        List<GlyphGeometry> metricsGlyphGeometry = glyphMetric
                .getMetricsGlyphGeometry();

        if (metricsGlyphGeometry == null) {
            return;
        }
        for (GlyphGeometry legendGlyph : metricsGlyphGeometry) {

            Point2D start = new Point2D.Double(legendGlyph.getNorthTransform()
                    .getX(), legendGlyph.getNorthTransform().getY());
            Point2D end = new Point2D.Double(legendGlyph.getSouthTransform()
                    .getX(), legendGlyph.getSouthTransform().getY());

            if (!start.equals(end)) {
                float[] dist = { 0.0f, 1.0f };

                Color[] colors = { themeColor1, themeColor2 };

                LinearGradientPaint pg = new LinearGradientPaint(start, end,
                                                                 dist, colors);
                g2d.setPaint(pg);
                g2d.fill(legendGlyph.getGlyphShape());
            }
        }

        // if(glyphShapes == null)
        // return;
        //
        // for (Shape glyphShape : glyphShapes) {
        //
        // Rectangle2D boun2D = glyphShape.getBounds2D();
        //
        // Point2D start = new Point2D.Double(boun2D.getX(), boun2D.getY());
        // Point2D end = new Point2D.Double(boun2D.getX(), boun2D.getY()+
        // boun2D.getHeight());
        // float[] dist = { 0.0f, 1.0f };
        //
        // Color[] colors = {themeColor1,themeColor2};
        //
        // if(!start.equals(end)){
        // LinearGradientPaint p = new LinearGradientPaint(start, end, dist,
        // colors);
        //
        // g2d.setPaint(p);
        // g2d.fill(glyphShape);
        // }
        //
        //
        // }

    }

}
