/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.bg;

import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.jensoft.core.plugin.gauge.core.RadialGauge;

/**
 * <code>GaugeTextureBackground</code> takes the responsibility to paint gauge
 * background with texture paint.
 * 
 * <p>
 * by default, the background area is the circle delimited by the gauge arc
 * circle. If you need to define another point which is not the center, you have
 * to provide the radial coordinate of the center point center
 * background(shiftRadius,shiftAlpha). A circle c(center, radius) will be fill
 * by given texture.
 * <p>
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class GaugeTextureBackground extends GaugeBackground {

	/** texture paint */
	private TexturePaint texturePaint;

	/**radius*/
	private int radius = 0;

	/**center point radius in polar coordinate from gauge center*/
	private double shiftRadius = 0;
	
	/**center point angle degree in polar coordinate from zero on right by convention*/
	private float shiftAlpha = 0;

	/**
	 * create gauge texture paint with the given texture
	 * 
	 * @param texturePaint
	 */
	public GaugeTextureBackground(TexturePaint texturePaint) {
		super();
		this.texturePaint = texturePaint;
	}

	/**
	 * create texture background with texture and given circle definition
	 * parameters
	 * 
	 * @param texturePaint
	 * @param radius
	 * @param shiftRadius
	 * @param shiftAlpha
	 */
	public GaugeTextureBackground(TexturePaint texturePaint, int radius, double shiftRadius, float shiftAlpha) {
		super();
		this.texturePaint = texturePaint;
		this.radius = radius;
		this.shiftRadius = shiftRadius;
		this.shiftAlpha = shiftAlpha;
	}

	/**
	 * @return the texturePaint
	 */
	public TexturePaint getTexturePaint() {
		return texturePaint;
	}

	/**
	 * @param texturePaint
	 *            the texturePaint to set
	 */
	public void setTexturePaint(TexturePaint texturePaint) {
		this.texturePaint = texturePaint;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @param radius
	 *            the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * @return the shiftRadius
	 */
	public double getShiftRadius() {
		return shiftRadius;
	}

	/**
	 * @param shiftRadius
	 *            the shiftRadius to set
	 */
	public void setShiftRadius(double shiftRadius) {
		this.shiftRadius = shiftRadius;
	}

	/**
	 * @return the shiftAlpha
	 */
	public float getShiftAlpha() {
		return shiftAlpha;
	}

	/**
	 * @param shiftAlpha
	 *            the shiftAlpha to set
	 */
	public void setShiftAlpha(float shiftAlpha) {
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
		if (texturePaint == null) {
			return;
		}

		Point2D centerDef = radialGauge.getRadialPointAt(shiftRadius, shiftAlpha);

		// if radius is not set, take gauge radius as the default value
		if (radius == 0) {
			radius = radialGauge.getRadius();
		}

		Ellipse2D baseShape = new Ellipse2D.Double(centerDef.getX() - radius, centerDef.getY() - radius, 2 * radius, 2 * radius);
		g2d.setPaint(texturePaint);
		g2d.fill(baseShape);
	}
}
