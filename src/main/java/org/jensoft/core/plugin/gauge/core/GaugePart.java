package org.jensoft.core.plugin.gauge.core;

import java.awt.Graphics2D;

public abstract class GaugePart {
	
	
	/** gauge host this metrics path */
	private RadialGauge gauge;
	
	/**
	 * get host gauge of this path
	 * 
	 * @return host gauge
	 */
	public RadialGauge getGauge() {
		return gauge;
	}

	/**
	 * set host gauge
	 * 
	 * @param gauge
	 */
	public void setGauge(RadialGauge gauge) {
		this.gauge = gauge;
	}
	
	/** gaupe part buffer */
	private GaugePartBuffer partBuffer;

	/**
	 * get background part buffer
	 * 
	 * @return background part buffer
	 */
	public GaugePartBuffer getPartBuffer() {
		return partBuffer;
	}

	/**
	 * set background part buffer
	 * 
	 * @param partBuffer
	 */
	public void setPartBuffer(GaugePartBuffer partBuffer) {
		this.partBuffer = partBuffer;
	}

	/**
	 * invalidate this part
	 * 
	 */
	public abstract void invalidate();
	
	/**
	 * Paint this part of the given gauge 
	 * 
	 * @param g2d
	 * @param radialGauge
	 */
	public abstract void paintPart(Graphics2D g2d, RadialGauge radialGauge);
	
}
