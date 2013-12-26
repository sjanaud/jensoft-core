/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.needle;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.gauge.core.GaugeMetricsPath;

public abstract class GaugeNeedlePainter  {


	public abstract void paintNeedle(Graphics2D g2d, GaugeMetricsPath gaugeMetricsPath);

	
}
