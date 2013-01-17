/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.radar;

import com.jensoft.core.glyphmetrics.GlyphMetric;

/**
 * extended metrics for radar usage
 */
public class RadarMetrics extends GlyphMetric {

    /** radar metrics nature */
    private RadarMetricsNature radarMetricsNature;

    /**
     * metrics nature
     */
    public enum RadarMetricsNature {
        DimensionMetrics, AnchorMetrics;
    }

    /**
     * create radar metrics
     * 
     * @param radarMetricsNature
     */
    public RadarMetrics(RadarMetricsNature radarMetricsNature) {
        this.radarMetricsNature = radarMetricsNature;
    }

    /**
     * @return the radarMetricsNature
     */
    public RadarMetricsNature getRadarMetricsNature() {
        return radarMetricsNature;
    }

}
