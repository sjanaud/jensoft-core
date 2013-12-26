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
 * background with texture paint
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class GaugeTextureBackground extends BackgroundGaugePainter {

	/** texture paint */
	private TexturePaint texturePaint;

	/**
	 * create gauge texture paint with the given texture
	 * 
	 * @param texturePaint
	 */
	public GaugeTextureBackground(TexturePaint texturePaint) {
		super();
		this.texturePaint = texturePaint;
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
		double centerX = radialGauge.getWindow2D().userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();
		double centerY = radialGauge.getWindow2D().userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();
		int radius = radialGauge.getRadius();
		Ellipse2D baseShape = new Ellipse2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
		g2d.setPaint(texturePaint);
		g2d.fill(baseShape);
	}
}
