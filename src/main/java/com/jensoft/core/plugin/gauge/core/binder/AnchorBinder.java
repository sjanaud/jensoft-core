/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.binder;

import java.awt.geom.Point2D;

import com.jensoft.core.plugin.gauge.core.RadialGauge;

/**
 * <code>AnchorBinder</code> takes the responsibility to bind anchor point to owner
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public interface AnchorBinder {

	
	/**
	 * Process to force calculate anchor at runtime in device coordinate system.
	 * because projected center is required and anchor is refer to this center.
	 * @param gauge
	 * 			the gauge
	 * @return the given anchor point
	 */
	Point2D bindAnchor(RadialGauge gauge);
	
}
