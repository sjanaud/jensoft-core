/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.radar;

/**
 * <code>DimensionMetrics</code> defines a metrics on a dimension
 * 
 * @since 1.0
 * 
 * @author sebastien janaud
 */
public final class DimensionMetrics extends RadarMetrics {

	/**
	 * create dimension metrics
	 */
    public DimensionMetrics() {
        super(RadarMetricsNature.DimensionMetrics);
    }

}
