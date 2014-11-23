/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.radar.painter.radar;

import java.awt.Graphics2D;
import java.util.List;

import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.plugin.radar.Radar;
import com.jensoft.core.plugin.radar.RadarDimension;
import com.jensoft.core.plugin.radar.RadarSurface;
import com.jensoft.core.plugin.radar.painter.AbstractRadarPainter;

public class RadarDefaultPainter extends AbstractRadarPainter {

    @Override
    public void paintRadar(Graphics2D g2d, Radar radar) {
        for (RadarDimension dimension : radar.getDimensions()) {

            if (dimension.getDimensionPainter() != null) {
                dimension.getDimensionPainter().paintRadarDimension(g2d, radar,
                                                                    dimension);
            }

            // //paint glyph
            // List<GlyphMetric> metrics = metricsPath.getMetrics();
            // for(GlyphMetric glyphMetric : metrics){
            //
            // if(glyphMetric.getGlyphMetricMarkerPainter() != null)
            // glyphMetric.getGlyphMetricMarkerPainter().paintGlyphMetric(g2d,
            // glyphMetric);
            //
            // if(glyphMetric.getGlyphMetricFill() != null)
            // glyphMetric.getGlyphMetricFill().paintGlyphMetric(g2d,
            // glyphMetric);
            //
            // if(glyphMetric.getGlyphMetricDraw() != null)
            // glyphMetric.getGlyphMetricDraw().paintGlyphMetric(g2d,
            // glyphMetric);
            //
            // }
            //
            if (dimension.getDimensionLabel() != null) {
                dimension.getDimensionLabel().paintRadarDimension(g2d, radar,
                                                                  dimension);
            }

        }

        for (RadarSurface surface : radar.getSurfaces()) {
            if (surface.getSurfacePainter() != null) {
                surface.getSurfacePainter().paintRadarSurface(g2d, radar,
                                                              surface);
            }
        }

        for (RadarDimension dimension : radar.getDimensions()) {

            GeneralMetricsPath metricsPath = dimension.getMetricsPath();

            metricsPath.setProjection(radar.getHost().getProjection());
            metricsPath.setFontRenderContext(g2d.getFontRenderContext());

            // paint glyph
            List<GlyphMetric> metrics = metricsPath.getMetrics();
            for (GlyphMetric glyphMetric : metrics) {

                if (glyphMetric.getGlyphMetricMarkerPainter() != null) {
                    glyphMetric.getGlyphMetricMarkerPainter().paintGlyphMetric(
                                                                               g2d, glyphMetric);
                }

                if (glyphMetric.getGlyphMetricFill() != null) {
                    glyphMetric.getGlyphMetricFill().paintGlyphMetric(g2d,
                                                                      glyphMetric);
                }

                if (glyphMetric.getGlyphMetricDraw() != null) {
                    glyphMetric.getGlyphMetricDraw().paintGlyphMetric(g2d,
                                                                      glyphMetric);
                }

            }

        }
    }
}
