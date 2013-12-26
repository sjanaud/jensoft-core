/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.env;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.gauge.core.RadialGauge;

/**
 * <code>EnvelopGaugePainter</code> defines an envelop that extends around the
 * gauge.
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public abstract class EnvelopGaugePainter {

	/**
	 * implements to paint gauge envelop
	 * 
	 * @param g2d
	 * @param radialGauge
	 */
	public abstract void paintEnvelop(Graphics2D g2d, RadialGauge radialGauge);

}
