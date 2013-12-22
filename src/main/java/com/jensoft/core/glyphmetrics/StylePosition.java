/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics;

import com.jensoft.core.glyphmetrics.painter.GlyphMetricFill;
import com.jensoft.core.glyphmetrics.painter.MetricsPathPainter;

/**
 * metrics style are Tangent Radial Orthogonal or default
 * 
 * @see GlyphMetric
 * @see AbstractMetricsPath
 * @see GeneralMetricsPath
 * @see GlyphMetricsNature
 * @see GlyphMetricFill
 * @see MetricsPathPainter
 * @author Sebastien Janaud
 */
public enum StylePosition {
    Tangent("tangent"),
    Radial("radial"),
    Default("default");

    private String stylePosition;

    private StylePosition(String stylePosition) {
        this.stylePosition = stylePosition;
    }

    /**
     * @return the stylePosition
     */
    public String getStylePosition() {
        return stylePosition;
    }

    /**
     * parse specified style string representation into {@link StylePosition}
     * 
     * @param style
     *            the style to parse
     * @return the parsed {@link StylePosition}
     */
    public static StylePosition parse(String style) {
        if (Tangent.getStylePosition().equalsIgnoreCase(style)) {
            return Tangent;
        }
        if (Radial.getStylePosition().equalsIgnoreCase(style)) {
            return Radial;
        }
        if (Default.getStylePosition().equalsIgnoreCase(style)) {
            return Default;
        }
        return null;
    }

};