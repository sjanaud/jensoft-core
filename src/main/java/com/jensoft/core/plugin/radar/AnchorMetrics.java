/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.radar;

/**
 * AnchorMetrics
 */
public final class AnchorMetrics extends RadarMetrics {

    /**
     * create anchor metrics not visible by Default
     */
    public AnchorMetrics() {
        super(RadarMetricsNature.AnchorMetrics);
        setVisible(false);
    }

}
