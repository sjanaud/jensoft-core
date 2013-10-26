/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics;

import com.jensoft.core.glyphmetrics.painter.GlyphMetricFill;
import com.jensoft.core.glyphmetrics.painter.MetricsPathPainter;
import com.jensoft.core.plugin.function.MetricsPathFunction;

/**
 * metrics side, right or left side along the path
 * 
 * @see GlyphMetric
 * @see AbstractMetricsPath
 * @see GeneralMetricsPath
 * @see MetricsPathFunction
 * @see GlyphMetricsNature
 * @see GlyphMetricFill
 * @see MetricsPathPainter
 * @author Sebastien Janaud
 */
public enum Side {
    SideLeft, SideRight;
};
