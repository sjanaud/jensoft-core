/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.binder;

import java.awt.geom.Point2D;

import com.jensoft.core.plugin.gauge.core.RadialGauge;

/**
 * <code>GaugeCenterAnchorBinder</code> binds to the gauge center.
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class GaugeCenterAnchorBinder implements AnchorBinder {

	
	/**
	 * create binder to the gauge center
	 */
	public GaugeCenterAnchorBinder(){
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.gauge.core.binder.AnchorBinder#bindAnchor(com
	 * .jensoft.core.plugin.gauge.core.RadialGauge)
	 */
	@Override
	public Point2D bindAnchor(RadialGauge gauge) {
		return gauge.getCenterDevice();
	}

}
