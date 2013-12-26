/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.glass;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.gauge.core.GaugePartBuffer;
import com.jensoft.core.plugin.gauge.core.RadialGauge;

/**
 * <code>GaugeGlassPainter</code> is the latest painted layer in the gauge.
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public abstract class GaugeGlassPainter {


	/** glass part buffer */
	private GaugePartBuffer partBuffer;
	
	/**
	 * get glass part buffer
	 * @return glass part buffer
	 */
	public GaugePartBuffer getPartBuffer() {
		return partBuffer;
	}

	/**
	 * set class part buffer
	 * @param partBuffer
	 */
	public void setPartBuffer(GaugePartBuffer partBuffer) {
		this.partBuffer = partBuffer;
	}
	
	/**
	 * paint glass on gauge
	 * @param g2d
	 * @param radialGauge
	 */
	public abstract void paintGlass(Graphics2D g2d, RadialGauge radialGauge);

}
