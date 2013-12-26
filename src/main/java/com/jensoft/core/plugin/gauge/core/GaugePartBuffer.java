/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import com.jensoft.core.device.PartBuffer;

/**
 * <code>GaugePartBuffer</code>
 * 
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class GaugePartBuffer extends PartBuffer {

	private Graphics2D g2dPart;

	/**
	 * create a gauge part buffer to be reuse
	 * @param gauge
	 */
	public GaugePartBuffer(RadialGauge gauge) {
		super(gauge.getWindow2D().userToPixel(new Point2D.Double(gauge.getX(), 0)).getX()-2*gauge.getRadius(), gauge.getWindow2D().userToPixel(new Point2D.Double(0, gauge.getY())).getY()-2*gauge.getRadius(), 4 * gauge.getRadius(), 4 * gauge.getRadius());
		g2dPart = getBuffer().createGraphics();
		g2dPart.translate(-gauge.getWindow2D().userToPixel(new Point2D.Double(gauge.getX(), 0)).getX() + 2*gauge.getRadius(), -gauge.getWindow2D().userToPixel(new Point2D.Double(0, gauge.getY())).getY() + 2*gauge.getRadius());
	}

	/**
	 * get the graphics context of the buffered image to paint. the graphics is
	 * already translate for the gauge projection
	 * 
	 * @return graphics context
	 */
	public Graphics2D getGraphics() {
		return g2dPart;
	}

}
