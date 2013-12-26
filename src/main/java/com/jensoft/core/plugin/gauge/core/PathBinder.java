/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core;

import java.awt.Shape;

/**
 * <code>PathBinder</code> takes the responsibility to bind geometry path to
 * owner
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public interface PathBinder {

	/**
	 * Process to force calculate shape at runtime in device coordinate system.
	 * because projected center is required.
	 * 
	 * @param gauge
	 *            the gauge
	 * @return the given geometry shape
	 */
	Shape bindPath(RadialGauge gauge);

}
