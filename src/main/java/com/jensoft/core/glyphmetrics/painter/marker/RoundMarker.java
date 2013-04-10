/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics.painter.marker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.painter.GlyphMetricMarkerPainter;

/**
 * define a round marker on path at point glyph
 * 
 * @author Sebastien Janaud
 */
public class RoundMarker extends GlyphMetricMarkerPainter {

    /** marker fill color */
    private Color markerFillColor;

    /** marker draw color */
    private Color markerDrawColor;

    /** round marker radius, default is 3, in pixel */
    private int radius = 3;

    /**
     * create round marker with specified fill color
     * 
     * @param markerFillColor
     *            the marker fill color
     */
    public RoundMarker(Color markerFillColor) {
        super();
        this.markerFillColor = markerFillColor;
    }

    /**
     * create round marker with specified fill and draw color
     * 
     * @param markerFillColor
     *            the marker fill color
     * @param markerDrawColor
     *            the marker draw color
     */
    public RoundMarker(Color markerFillColor, Color markerDrawColor) {
        super();
        this.markerFillColor = markerFillColor;
        this.markerDrawColor = markerDrawColor;
    }

    /**
     * create round marker with specified fill and draw color and marker radius
     * 
     * @param markerFillColor
     *            the marker fill color
     * @param markerDrawColor
     *            the marker draw color to set
     * @param radius
     *            the marker radius
     */
    public RoundMarker(Color markerFillColor, Color markerDrawColor, int radius) {
        super();
        this.markerFillColor = markerFillColor;
        this.markerDrawColor = markerDrawColor;
        this.radius = radius;
    }

    /**
     * get marker fill color
     * 
     * @return marker fill color
     */
    public Color getMarkerFillColor() {
        return markerFillColor;
    }

    /**
     * set marker fill color
     * 
     * @param markerFillColor
     *            the marker fill color
     */
    public void setMarkerFillColor(Color markerFillColor) {
        this.markerFillColor = markerFillColor;
    }

    /**
     * get marker draw color
     * 
     * @return marker draw color
     */
    public Color getMarkerDrawColor() {
        return markerDrawColor;
    }

    /**
     * set marker draw color
     * 
     * @param markerDrawColor
     *            the draw color to set
     */
    public void setMarkerDrawColor(Color markerDrawColor) {
        this.markerDrawColor = markerDrawColor;
    }

    /**
     * get marker radius
     * 
     * @return marker radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * set marker radius
     * 
     * @param radius
     *            the radius to set
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.glyphmetrics.painter.GlyphMetricMarkerPainter#paintGlyphMetricMarker(java.awt.Graphics2D, com.jensoft.core.glyphmetrics.GlyphMetric)
     */
    @Override
    public void paintGlyphMetricMarker(Graphics2D g2d, GlyphMetric glyphMetric) {
        g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                             java.awt.AlphaComposite.SRC_OVER, 1f));

        Point2D p = glyphMetric.getMetricPointRef();

        if (p == null) {
            return;
        }

        if (markerFillColor != null || markerDrawColor != null) {
            Ellipse2D marker = new Ellipse2D.Double(p.getX() - radius, p.getY()
                    - radius, 2 * radius, 2 * radius);

            if (markerFillColor != null) {
                g2d.setColor(markerFillColor);
                g2d.fill(marker);
            }
            if (markerDrawColor != null) {
                g2d.setColor(markerDrawColor);
                g2d.draw(marker);
            }
        }

    }

}
