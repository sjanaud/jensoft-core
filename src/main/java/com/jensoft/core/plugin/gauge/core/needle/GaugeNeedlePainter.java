/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.needle;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.gauge.core.GaugeMetricsPath;

/**
 * <code>GaugeNeedlePainter</code> takes the responsibility to paint a needle
 * which is based on anchors binders declared in gauge path metrics
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public abstract class GaugeNeedlePainter {

	/**
	 * paint needle for the given gauge metrics path anchor configuration
	 * 
	 * @param g2d
	 * @param gaugeMetricsPath
	 */
	public abstract void paintNeedle(Graphics2D g2d, GaugeMetricsPath gaugeMetricsPath);

}
