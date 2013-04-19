/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.metrics;

/**
 * <code>X2DMetricsElement</code>
 * 
 * @author Sebastien Janaud
 */
public interface X2DMetricsElement {

    public static String ELEMENT_METRICS_AXIS = "axis";

    
    public static String ELEMENT_METRICS_AXIS_SPACING = "axis-spacing";
    public static String ELEMENT_METRICS_AXIS_LINE_PAINT = "line-paint";
    public static String ELEMENT_METRICS_AXIS_LINE_COLOR = "line-color";
    public static String ELEMENT_METRICS_AXIS_MARKER_COLOR = "marker-color";
    public static String ELEMENT_METRICS_AXIS_TEXT_COLOR = "label-color";
    public static String ELEMENT_METRICS_AXIS_TEXT_FONT = "label-font";
    public static String ELEMENT_METRICS_AXIS_FORMAT = "metrics-format";

    public static String ELEMENT_METRICS_STATIC_COUNT = "metrics-count";

    public static String ELEMENT_METRICS_FLOW_START = "flow-start";
    public static String ELEMENT_METRICS_FLOW_END = "flow-end";
    public static String ELEMENT_METRICS_FLOW_INTERVAL = "flow-interval";

    public static String ELEMENT_METRICS_FREE = "free-metric";
    public static String ELEMENT_METRICS_FREE_VALUE = "value";
    public static String ELEMENT_METRICS_FREE_TEXT = "text";

    public static String ELEMENT_METRICS_MULTIPLIER_REF = "ref";
    public static String ELEMENT_METRICS_MULTIPLIER_MULTIPLIER = "multiplier";

    public static String ELEMENT_METRICS_MULTI_MULTIPLIER_REF = "ref";
    public static String ELEMENT_METRICS_MULTI_MULTIPLIER_MAJOR_MULTIPLIER = "major-multiplier";
    public static String ELEMENT_METRICS_MULTI_MULTIPLIER_MEDIAN_MULTIPLIER = "median-multiplier";
    public static String ELEMENT_METRICS_MULTI_MULTIPLIER_MINOR_MULTIPLIER = "minor-multiplier";

}
