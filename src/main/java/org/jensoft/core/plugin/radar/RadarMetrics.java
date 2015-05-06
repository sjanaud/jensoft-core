/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.radar;

import org.jensoft.core.glyphmetrics.GlyphMetric;

/**
 * <code>RadarMetrics</code>
 * <p>
 * extended metrics for radar usage
 * </p>
 * 
 * @see GlyphMetric
 * @since 1.0
 * 
 * @author sebastien janaud
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
