/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.binder;

import java.awt.Graphics2D;
import java.awt.Shape;

import com.jensoft.core.plugin.gauge.core.GaugeMetricsPath;
import com.jensoft.core.plugin.gauge.core.RadialGauge;

/**
 * <code>PathBinder</code> takes the responsibility to bind geometry path to
 * owner
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public abstract class PathBinder {

	private GaugeMetricsPath metricsPath;

	/**
	 * @return the metricsPath
	 */
	public GaugeMetricsPath getMetricsPath() {
		return metricsPath;
	}

	/**
	 * @param metricsPath
	 *            the metricsPath to set
	 */
	public void setMetricsPath(GaugeMetricsPath metricsPath) {
		this.metricsPath = metricsPath;
	}

	/**
	 * Process to force calculate shape at runtime in device coordinate system.
	 * because projected center is required.
	 * 
	 * @param gauge
	 *            the gauge
	 * @return the given geometry shape
	 */
	public abstract Shape bindPath(RadialGauge gauge);

	/**
	 * debug paint path binder
	 * @param g2d
	 * @param gauge
	 */
	public void debug(Graphics2D g2d, RadialGauge gauge) {};
	

}
