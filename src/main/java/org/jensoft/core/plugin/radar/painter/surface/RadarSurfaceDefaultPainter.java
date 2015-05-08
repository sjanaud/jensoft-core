/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.radar.painter.surface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;

import org.jensoft.core.plugin.radar.Radar;
import org.jensoft.core.plugin.radar.RadarMetrics;
import org.jensoft.core.plugin.radar.RadarSurface;
import org.jensoft.core.plugin.radar.RadarSurfaceAnchor;
import org.jensoft.core.plugin.radar.painter.AbstractRadarSurfacePainter;

/**
 * RadarSurfaceDefaultPainter
 */
public class RadarSurfaceDefaultPainter extends AbstractRadarSurfacePainter {

    /** outline surface stroke */
    private Color outlineColor;

    /** outline surface stroke */
    private Stroke outlineStroke;

    /** outline fill color */
    private Color fillColor;

    /** surface style */
    private SurfaceStyle style = SurfaceStyle.Both;

    /** outline surface default stroke */
    private Stroke defaultStroke = new BasicStroke();

    public enum SurfaceStyle {
        Stroke, Fill, Both;
    }

    /**
     * @param outlineColor
     * @param fillColor
     */
    public RadarSurfaceDefaultPainter(Color outlineColor, Color fillColor) {
        super();
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
    }

    /**
     * @param outlineColor
     * @param outlineStroke
     * @param fillColor
     */
    public RadarSurfaceDefaultPainter(Color outlineColor, Stroke outlineStroke,
            Color fillColor) {
        super();
        this.outlineColor = outlineColor;
        this.outlineStroke = outlineStroke;
        this.fillColor = fillColor;
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.radar.painter.AbstractRadarSurfacePainter#paintRadarSurface(java.awt.Graphics2D, org.jensoft.core.plugin.radar.Radar, org.jensoft.core.plugin.radar.RadarSurface)
     */
    @Override
    public final void paintRadarSurface(Graphics2D g2d, Radar radar,
            RadarSurface radarSurface) {

        radarSurface.solveSurfaceGeometry();
        if(radarSurface.getSurfacePath() == null){
            return;
        }
        
        // PAINT SURFACE
        if (style == SurfaceStyle.Fill || style == SurfaceStyle.Both) {

            if (fillColor != null) {
                g2d.setColor(fillColor);
            }
            else {
                g2d.setColor(radar.getThemeColor());
            }

            g2d.fill(radarSurface.getSurfacePath());
        }

        if (style == SurfaceStyle.Stroke || style == SurfaceStyle.Both) {

            if (outlineColor != null) {
                g2d.setColor(outlineColor);
            }
            else {
                g2d.setColor(radar.getThemeColor());
            }

            g2d.draw(radarSurface.getSurfacePath());
        }

        List<RadarSurfaceAnchor> anchors = radarSurface.getAnchors();

        // PAINT ANCHOR
        for (int i = 0; i < anchors.size(); i++) {
            RadarSurfaceAnchor anchor = anchors.get(i);
            RadarMetrics radarMetrics = anchor.getRadarMetrics();
            if(radarMetrics == null || !anchor.isMetricsEnable()){
                continue;
            }
            anchor.getDimension().solveMetrics(radarMetrics);

            if (radarMetrics.getGlyphMetricMarkerPainter() != null) {
                radarMetrics.getGlyphMetricMarkerPainter().paintGlyphMetric(
                                                                            g2d, radarMetrics);
            }

            if (radarMetrics.getGlyphMetricFill() != null) {
                radarMetrics.getGlyphMetricFill().paintGlyphMetric(g2d,
                                                                   radarMetrics);
            }

            if (radarMetrics.getGlyphMetricDraw() != null) {
                radarMetrics.getGlyphMetricDraw().paintGlyphMetric(g2d,
                                                                   radarMetrics);
            }
        }

    }

}
