/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.radar;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import org.jensoft.core.glyphmetrics.StylePosition;
import org.jensoft.core.glyphmetrics.painter.GlyphMetricFill;
import org.jensoft.core.glyphmetrics.painter.GlyphMetricMarkerPainter;
import org.jensoft.core.plugin.Toolkit;
import org.jensoft.core.plugin.radar.painter.dimension.DimensionDefaultPainter;
import org.jensoft.core.plugin.radar.painter.label.RadarDimensionDefaultLabel;
import org.jensoft.core.plugin.radar.painter.radar.RadarDefaultPainter;
import org.jensoft.core.plugin.radar.painter.surface.RadarSurfaceDefaultPainter;

/**
 * RadarToolkit
 * 
 * @author Sebastien Janaud
 */
public class RadarToolkit extends Toolkit {

    /**
     * create compatible radar view
     * 
     * @return radar view
     */
    public static RadarView createCompatibleView() {
        RadarView radarView = new RadarView();
        return radarView;
    }

    /**
     * create radar with specified given parameters and a default painter
     * <p>
     * default radar nature system coordinate is User.
     * </p>
     * 
     * @param centerX
     *            the radar center x in radar system coordinate nature
     * @param centerY
     *            the radar center y in radar system coordinate nature
     * @param radius
     *            the radar radius in pixel
     * @return radar
     */
    public static Radar createRadar(int centerX, int centerY, int radius) {
        Radar radar = new Radar(centerX, centerY, radius);
        radar.setRadarPainter(new RadarDefaultPainter());
        return radar;
    }

    /**
     * create radar dimension with specified given parameters
     * 
     * @param name
     *            the dimension name
     * @param angleDegree
     *            the dimension angle degree
     * @param min
     *            the dimension minimum value
     * @param max
     *            the dimension maximum value
     * @return radar dimension
     */
    public static RadarDimension createDimension(String name,
            double angleDegree, double min, double max) {
        RadarDimension dimension = new RadarDimension(name, angleDegree, min,
                                                      max);
        dimension.setDimensionPainter(new DimensionDefaultPainter());
        return dimension;
    }

    /**
     * create radar dimension with specified given parameters
     * 
     * @param name
     *            the dimension name
     * @param lineColor
     *            the dimension line Color
     * @param angleDegree
     *            the dimension angle degree
     * @param min
     *            the dimension minimum value
     * @param max
     *            the dimension maximum value
     * @return radar dimension
     */
    public static RadarDimension createDimension(String name, Color lineColor,
            double angleDegree, double min, double max) {
        RadarDimension dimension = new RadarDimension(name, lineColor,
                                                      angleDegree, min, max);
        dimension.setDimensionPainter(new DimensionDefaultPainter());
        return dimension;
    }

    /**
     * push specified dimensions into specified radar
     * 
     * @param radar
     *            the host radar
     * @param dimensions
     *            the dimensions to push
     */
    public static void pushDimensions(Radar radar, RadarDimension... dimensions) {
        for (int i = 0; i < dimensions.length; i++) {
            radar.addDimension(dimensions[i]);
        }
    }

    /**
     * push specified dimensions into specified radar
     * 
     * @param radar
     *            the host radar
     * @param dimensions
     *            the dimensions to push
     */
    public static void pushDimensions(Radar radar,
            List<RadarDimension> dimensions) {
        for (RadarDimension radarDimension : dimensions) {
            radar.addDimension(radarDimension);
        }
    }

    /**
     * create surface with specified given parameters
     * 
     * @param name
     *            the surface name
     * @param outlineColor
     *            the surface outline color
     * @param fillColor
     *            the surface fill color
     * @return surface
     */
    public static RadarSurface createSurface(String name, Color outlineColor,
            Color fillColor) {
        RadarSurface surface = new RadarSurface(name);
        surface.setSurfacePainter(new RadarSurfaceDefaultPainter(outlineColor,
                                                                 fillColor));
        return surface;
    }

    /**
     * push specified anchors into specified surface
     * 
     * @param surface
     *            the host surface
     * @param anchors
     *            the anchors to push
     */
    public static void pushAnchors(RadarSurface surface,
            RadarSurfaceAnchor... anchors) {
        for (int i = 0; i < anchors.length; i++) {
            surface.addSurfaceAnchor(anchors[i]);
        }
    }

    /**
     * push specified anchors into specified surface
     * 
     * @param surface
     *            the host surface
     * @param anchors
     *            the anchors to push
     */
    public static void pushAnchors(RadarSurface surface,
            List<RadarSurfaceAnchor> anchors) {
        for (RadarSurfaceAnchor anchor : anchors) {
            surface.addSurfaceAnchor(anchor);
        }
    }

    /**
     * return dimension default label
     * 
     * @param label
     *            the dimension label
     * @param labelColor
     *            the dimension label color
     * @param outlineColor
     *            the dimension outline color
     * @param fillShaderFraction
     *            the fill shader fractions
     * @param fillShaderColor
     *            the fill shader colors
     * @param outlineRound
     *            the outline round
     * @return default dimension default label
     */
    public static RadarDimensionDefaultLabel createDimensionDefaultLabel(
            String label, Color labelColor, Color outlineColor,
            float[] fillShaderFraction, Color[] fillShaderColor,
            int outlineRound) {
        RadarDimensionDefaultLabel l = new RadarDimensionDefaultLabel(label,
                                                                      labelColor);
        l.setOutlineColor(outlineColor);
        l.setShader(fillShaderFraction, fillShaderColor);
        l.setOutlineRound(outlineRound);
        return l;
    }

    /**
     * return dimension default label
     * 
     * @param label
     *            the dimension label
     * @param labelFont
     *            the label font
     * @param labelColor
     *            the dimension label color
     * @param outlineColor
     *            the dimension outline color
     * @param fillShaderFraction
     *            the fill shader fractions
     * @param fillShaderColor
     *            the fill shader colors
     * @param outlineRound
     *            the outline round
     * @return default dimension default label
     */
    public static RadarDimensionDefaultLabel createDimensionDefaultLabel(
            String label, Font labelFont, Color labelColor, Color outlineColor,
            float[] fillShaderFraction, Color[] fillShaderColor,
            int outlineRound) {
        RadarDimensionDefaultLabel l = new RadarDimensionDefaultLabel(label,
                                                                      labelColor);
        l.setLabelFont(labelFont);
        l.setOutlineColor(outlineColor);
        l.setShader(fillShaderFraction, fillShaderColor);
        l.setOutlineRound(outlineRound);
        return l;
    }

    /**
     * create radar surface anchor with specified given parameters
     * 
     * @param dimension
     *            the anchor dimension
     * @param label
     *            the radar metrics label
     * @param value
     *            the radar metrics value
     * @param stylePosition
     *            the radar style position
     * @param divergence
     *            the radar metrics divergence
     * @param glyphFill
     *            the radar metrics fill
     * @param glyphMarker
     *            the radar metrics marker
     * @param metricsFont
     * @return radar surface anchor
     */
    public static RadarSurfaceAnchor createSurfaceAnchor(
            RadarDimension dimension, String label, double value,
            StylePosition stylePosition, int divergence,
            GlyphMetricFill glyphFill, GlyphMetricMarkerPainter glyphMarker,
            Font metricsFont) {

        AnchorMetrics metric = new AnchorMetrics();
        metric.setValue(value);
        metric.setStylePosition(stylePosition);
        metric.setMetricsLabel(label);
        metric.setDivergence(divergence);
        metric.setGlyphMetricFill(glyphFill);
        metric.setGlyphMetricMarkerPainter(glyphMarker);
        metric.setFont(metricsFont);

        RadarSurfaceAnchor anchor = new RadarSurfaceAnchor(dimension, metric);

        return anchor;
    }

    /**
     * create radar surface anchor with specified given parameters
     * 
     * @param dimension
     *            the anchor dimension
     * @param anchorMetrics
     *            the anchorMetrics
     * @return radar surface anchor
     */
    public static RadarSurfaceAnchor createSurfaceAnchor(
            RadarDimension dimension, AnchorMetrics anchorMetrics) {
        RadarSurfaceAnchor anchor = new RadarSurfaceAnchor(dimension,
                                                           anchorMetrics);
        return anchor;
    }

    /**
     * create radar surface anchor metrics with specified given parameters
     * 
     * @param label
     *            the radar metrics label
     * @param value
     *            the radar metrics value
     * @param stylePosition
     *            the radar style position
     * @param divergence
     *            the radar metrics divergence
     * @param glyphFill
     *            the radar metrics fill
     * @param glyphMarker
     *            the radar metrics marker
     * @param metricsFont
     * @return radar metrics
     */
    public static AnchorMetrics createAnchorMetrics(String label, double value,
            StylePosition stylePosition, int divergence,
            GlyphMetricFill glyphFill, GlyphMetricMarkerPainter glyphMarker,
            Font metricsFont) {
        AnchorMetrics metric = new AnchorMetrics();
        metric.setValue(value);
        metric.setStylePosition(stylePosition);
        metric.setMetricsLabel(label);
        metric.setDivergence(divergence);
        metric.setGlyphMetricFill(glyphFill);
        metric.setGlyphMetricMarkerPainter(glyphMarker);
        metric.setFont(metricsFont);
        return metric;
    }

    /**
     * create radar dimension metrics with specified given parameters
     * 
     * @param label
     *            the radar metrics label
     * @param value
     *            the radar metrics value
     * @param stylePosition
     *            the radar style position
     * @param divergence
     *            the radar metrics divergence
     * @param glyphFill
     *            the radar metrics fill
     * @param glyphMarker
     *            the radar metrics marker
     * @param metricsFont
     * @return radar metrics
     */
    public static DimensionMetrics createDimensionMetrics(String label,
            double value, StylePosition stylePosition, int divergence,
            GlyphMetricFill glyphFill, GlyphMetricMarkerPainter glyphMarker,
            Font metricsFont) {
        DimensionMetrics metric = new DimensionMetrics();
        metric.setValue(value);
        metric.setStylePosition(stylePosition);
        metric.setMetricsLabel(label);
        metric.setDivergence(divergence);
        metric.setGlyphMetricFill(glyphFill);
        metric.setGlyphMetricMarkerPainter(glyphMarker);
        metric.setFont(metricsFont);
        return metric;
    }

    /**
     * push specified metrics into specified dimension
     * 
     * @param dimension
     *            the host dimension
     * @param dimensionMetrics
     *            the dimension metrics to push
     */
    public static void pushMetricsDimensions(RadarDimension dimension,
            DimensionMetrics... dimensionMetrics) {
        for (int i = 0; i < dimensionMetrics.length; i++) {
            dimension.addMetrics(dimensionMetrics[i]);
        }
    }

    /**
     * push specified metrics into specified dimension
     * 
     * @param dimension
     *            the host dimension
     * @param dimensionMetrics
     *            the dimension metrics to push
     */
    public static void pushMetricsDimensions(RadarDimension dimension,
            List<DimensionMetrics> dimensionMetrics) {
        for (DimensionMetrics diemsionMetric : dimensionMetrics) {
            dimension.addMetrics(diemsionMetric);
        }
    }

}
