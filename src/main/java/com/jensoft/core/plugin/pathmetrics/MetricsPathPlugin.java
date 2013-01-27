/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pathmetrics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import com.jensoft.core.glyphmetrics.AbstractMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.painter.draw.GlyphDefaultDraw;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphDefaultFill;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.DefaultMarker;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.graphics.AlphaInterpolation;
import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Dithering;
import com.jensoft.core.graphics.Fractional;
import com.jensoft.core.graphics.Interpolation;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>MetricsPathPlugin</code>
 * <p>
 * MetricsPathPlugin is an utility plugin to design metrics path before integration in gauge or curve typed plugin
 * </p>
 * 
 * @see AbstractMetricsPath
 * @see MetricsPath
 * @see GlyphMetric
 */
public class MetricsPathPlugin extends AbstractPlugin {

    private AbstractMetricsPath metricsPath;
    private GlyphDefaultFill fillWhite = new GlyphDefaultFill(Color.WHITE);
    private GlyphFill gradientWhiteRed = new GlyphFill(Color.WHITE, Color.RED);
    private DefaultMarker markerWhite = new DefaultMarker(Color.WHITE);
    private TicTacMarker tictacMarker = new TicTacMarker(
                                                         TangoPalette.SCARLETRED3);
    private GlyphDefaultDraw drawWhite = new GlyphDefaultDraw(Color.WHITE);

    /**
     * create metrics path plugin with specified metrics path
     * 
     * @param metricPath
     *            the metrics path to set
     */
    public MetricsPathPlugin(AbstractMetricsPath metricPath) {
        metricsPath = metricPath;
        setAntialiasing(Antialiasing.On);
        setFractionalMetrics(Fractional.On);
        setInterpolation(Interpolation.Bicubic);
        setTextAntialising(TextAntialiasing.On);
        setAlphaInterpolation(AlphaInterpolation.Quality);
        setDithering(Dithering.On);
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.sw2d.core.view.View2D,
     * java.awt.Graphics2D, com.jensoft.sw2d.core.window.WindowPart)
     */
    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {

        if (windowPart != WindowPart.Device) {
            return;
        }

        metricsPath.setSolveGeometryRequest(true);
        metricsPath.setWindow2d(getWindow2D());
        metricsPath.setFontRenderContext(g2d.getFontRenderContext());

        // paint path
        if (metricsPath.getPathPainter() != null) {
            metricsPath.getPathPainter().paintPath(g2d, metricsPath);
        }

        // paint glyph
        List<GlyphMetric> metrics = metricsPath.getMetrics();
        for (GlyphMetric glyphMetric : metrics) {

            if (glyphMetric.getGlyphMetricMarkerPainter() != null) {
                glyphMetric.getGlyphMetricMarkerPainter().paintGlyphMetric(g2d,
                                                                           glyphMetric);
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
