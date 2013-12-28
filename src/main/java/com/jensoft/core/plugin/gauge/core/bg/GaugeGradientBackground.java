/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.bg;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.jensoft.core.plugin.gauge.core.RadialGauge;

/**
 * <code>GaugeGradientBackground</code> takes the responsibility to paint gauge
 * background with gradient paint
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class GaugeGradientBackground extends GaugeBackground {

	private int radius = 0;

	private double shiftRadius = 0;
	private float shiftAlpha = 0;

	/**
	 * create gauge default gradient background
	 */
	public GaugeGradientBackground() {
	}

	/**
	 * create background for the given circle define by given parameters
	 * @param radius
	 * @param shiftRadius
	 * @param shiftAlpha
	 */
	public GaugeGradientBackground(int radius, double shiftRadius, float shiftAlpha) {
		super();
		this.radius = radius;
		this.shiftRadius = shiftRadius;
		this.shiftAlpha = shiftAlpha;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.gauge.core.bg.BackgroundGaugePainter#paintBackground
	 * (java.awt.Graphics2D, com.jensoft.core.plugin.gauge.core.RadialGauge)
	 */
	@Override
	public void paintBackground(Graphics2D g2d, RadialGauge radialGauge) {

		Point2D centerDef = radialGauge.getRadialPointAt(shiftRadius, shiftAlpha);

		// if radius is not set, take gauge radius as the default value
		if (radius == 0) {
			radius = radialGauge.getRadius();
		}
		
		RadialGradientPaint rgp = new RadialGradientPaint(centerDef, 3 * radius / 4, new float[] { 0f, 1f }, new Color[] { Color.BLACK, new Color(50, 50, 50) });

		Ellipse2D baseShape = new Ellipse2D.Double(centerDef.getX() - radius, centerDef.getY() - radius, 2 * radius, 2 * radius);
		g2d.setPaint(rgp);
		g2d.fill(baseShape);

	}

}
