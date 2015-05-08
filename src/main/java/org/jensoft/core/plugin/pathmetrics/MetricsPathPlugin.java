/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pathmetrics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import org.jensoft.core.glyphmetrics.AbstractMetricsPath;
import org.jensoft.core.glyphmetrics.GlyphMetric;
import org.jensoft.core.glyphmetrics.painter.draw.GlyphDefaultDraw;
import org.jensoft.core.glyphmetrics.painter.fill.GlyphDefaultFill;
import org.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import org.jensoft.core.glyphmetrics.painter.marker.DefaultMarker;
import org.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import org.jensoft.core.graphics.AlphaInterpolation;
import org.jensoft.core.graphics.Antialiasing;
import org.jensoft.core.graphics.Dithering;
import org.jensoft.core.graphics.Fractional;
import org.jensoft.core.graphics.Interpolation;
import org.jensoft.core.graphics.TextAntialiasing;
import org.jensoft.core.palette.color.TangoPalette;
import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

/**
 * <code>MetricsPathPlugin</code>
 * <p>
 * MetricsPathPlugin is an utility plugin to design metrics path before integration in gauge or curve typed plugin
 * </p>
 * 
 * @see AbstractMetricsPath
 * @see GlyphMetric
 * 
 * 
 * @since 1.0
 * 
 * @author sebastien janaud
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

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin#paintPlugin(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {
        if (viewPart != ViewPart.Device) {
            return;
        }
        metricsPath.setSolveGeometryRequest(true);
        metricsPath.setProjection(getProjection());
        metricsPath.setFontRenderContext(g2d.getFontRenderContext());
        // paint path
        if (metricsPath.getPathPainter() != null) {
            metricsPath.getPathPainter().paintPath(g2d, metricsPath);
        }
        // paint glyph
        List<GlyphMetric> metrics = metricsPath.getMetrics();
        for (GlyphMetric glyphMetric : metrics) {
            if (glyphMetric.getGlyphMetricMarkerPainter() != null) {
                glyphMetric.getGlyphMetricMarkerPainter().paintGlyphMetric(g2d, glyphMetric);
            }
            if (glyphMetric.getGlyphMetricFill() != null) {
                glyphMetric.getGlyphMetricFill().paintGlyphMetric(g2d,glyphMetric);
            }
            if (glyphMetric.getGlyphMetricDraw() != null) {
                glyphMetric.getGlyphMetricDraw().paintGlyphMetric(g2d, glyphMetric);
            }
        }

    }

}
