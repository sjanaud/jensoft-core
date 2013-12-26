/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.bg;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.gauge.core.RadialGauge;


/**
 * <code>BackgroundGaugePainter</code> takes the responsibility to paint gauge background.
 * 
 * <p>
 * background painting process is after the envelop painting and just before gauge metrics path
 * </p>
 * 
 * @since 1.0
 * @author sebastien janaud
 *
 */
public abstract class BackgroundGaugePainter {

	
	/**
	 * implements this method to create background painter
	 * @param g2d
	 * @param radialGauge
	 */
    public abstract void paintBackground(Graphics2D g2d, RadialGauge radialGauge);


}
