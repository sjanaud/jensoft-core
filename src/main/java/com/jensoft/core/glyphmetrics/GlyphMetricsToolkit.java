/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics;

import java.awt.Color;
import java.awt.Font;

import com.jensoft.core.glyphmetrics.painter.GlyphMetricFill;
import com.jensoft.core.glyphmetrics.painter.GlyphMetricMarkerPainter;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphDefaultFill;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.plugin.Toolkit;

/**
 * @author Sebastien Janaud
 */
public class GlyphMetricsToolkit extends Toolkit {

    public static GlyphMetric createGlyphMetrics(double value) {
        GlyphMetric metric = new GlyphMetric();
        metric.setValue(value);
        return metric;
    }

    public static GlyphMetric createGlyphMetrics(double value, String label) {
        GlyphMetric metric = new GlyphMetric();
        metric.setValue(value);
        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Median);
        metric.setMetricsLabel(label);
        metric.setDivergence(10);
        metric.setGlyphMetricFill(new GlyphDefaultFill(Color.DARK_GRAY));
        // metric.setGlyphMetricMarkerPainter(new
        // RoundMarker(FilPalette.GREEN5,Color.white));

        return metric;
    }

    public static GlyphMetric createGlyphMetrics(double value, String label,
            Font font) {
        GlyphMetric metric = new GlyphMetric();
        metric.setValue(value);
        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Median);
        metric.setMetricsLabel(label);
        metric.setDivergence(10);
        metric.setGlyphMetricFill(new GlyphDefaultFill(Color.DARK_GRAY));
        // metric.setGlyphMetricMarkerPainter(new
        // RoundMarker(FilPalette.GREEN5,Color.white));
        metric.setFont(font);
        return metric;
    }

    public static GlyphMetric createGlyphMetrics(double value, String label,
            Font font, StylePosition stylePosition) {
        GlyphMetric metric = new GlyphMetric();
        metric.setValue(value);
        metric.setStylePosition(stylePosition);
        metric.setMetricsNature(GlyphMetricsNature.Median);
        metric.setMetricsLabel(label);
        metric.setDivergence(10);
        metric.setGlyphMetricFill(new GlyphDefaultFill(Color.DARK_GRAY));
        metric.setFont(font);
        return metric;
    }

    public static GlyphMetric createGlyphMetrics(double value, String label,
            Font font, StylePosition stylePosition, Color startColor,
            Color endColor) {
        GlyphMetric metric = new GlyphMetric();
        metric.setValue(value);
        metric.setStylePosition(stylePosition);
        metric.setMetricsNature(GlyphMetricsNature.Median);
        metric.setMetricsLabel(label);
        metric.setDivergence(10);
        metric.setGlyphMetricFill(new GlyphDefaultFill(Color.DARK_GRAY));
        metric.setFont(font);
        return metric;
    }

    public static GlyphMetric createGlyphMetrics(double value, String label,
            Font font, StylePosition stylePosition, GlyphFill glyphFill) {
        GlyphMetric metric = new GlyphMetric();
        metric.setValue(value);
        metric.setStylePosition(stylePosition);
        metric.setMetricsNature(GlyphMetricsNature.Median);
        metric.setMetricsLabel(label);
        metric.setDivergence(10);
        metric.setGlyphMetricFill(glyphFill);
        metric.setFont(font);
        return metric;
    }

    public static GlyphMetric createGlyphMetrics(double value, String label,
            Font font, StylePosition stylePosition,
            GlyphMetricFill glyphMetricFill,
            GlyphMetricMarkerPainter glyphMetricMarkerPainter) {
        GlyphMetric metric = new GlyphMetric();
        metric.setValue(value);
        metric.setStylePosition(stylePosition);
        metric.setMetricsNature(GlyphMetricsNature.Median);
        metric.setMetricsLabel(label);
        metric.setDivergence(10);
        metric.setGlyphMetricFill(glyphMetricFill);
        metric.setGlyphMetricMarkerPainter(glyphMetricMarkerPainter);
        metric.setFont(font);
        return metric;
    }

}
