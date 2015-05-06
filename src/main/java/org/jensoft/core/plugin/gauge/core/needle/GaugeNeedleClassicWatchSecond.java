/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.gauge.core.needle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.jensoft.core.glyphmetrics.GeometryPath;
import org.jensoft.core.palette.color.NanoChromatique;
import org.jensoft.core.plugin.gauge.core.GaugeMetricsPath;

/**
 * <code>GaugeNeedleClassicWatchSecond</code> is like the needle that shows second on a watch.
 * 
 * <p>
 * second watch style
 * <p>
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class GaugeNeedleClassicWatchSecond extends GaugeNeedlePainter {

	/** needle base color */
	private Color baseColor = NanoChromatique.RED;

	/** toward offset needle */
	private int towardOffset = 6;

	/** base circle radius */
	private int baseCircleRadius = 3;

	/** base circle color */
	private Color baseCircleColor = NanoChromatique.RED;

	/**
	 * create default red needle
	 */
	public GaugeNeedleClassicWatchSecond() {
	}

	/**
	 * create needle with the given color
	 * 
	 * @param baseColor
	 */
	public GaugeNeedleClassicWatchSecond(Color baseColor) {
		this.baseColor = baseColor;
	}

	/**
	 * @return the baseColor
	 */
	public Color getBaseColor() {
		return baseColor;
	}

	/**
	 * @param baseColor
	 *            the baseColor to set
	 */
	public void setBaseColor(Color baseColor) {
		this.baseColor = baseColor;
	}

	/**
	 * @return the towardOffset
	 */
	public int getTowardOffset() {
		return towardOffset;
	}

	/**
	 * @param towardOffset
	 *            the towardOffset to set
	 */
	public void setTowardOffset(int towardOffset) {
		this.towardOffset = towardOffset;
	}

	/**
	 * @return the baseCircleRadius
	 */
	public int getBaseCircleRadius() {
		return baseCircleRadius;
	}

	/**
	 * @param baseCircleRadius
	 *            the baseCircleRadius to set
	 */
	public void setBaseCircleRadius(int baseCircleRadius) {
		this.baseCircleRadius = baseCircleRadius;
	}

	/**
	 * @return the baseCircleColor
	 */
	public Color getBaseCircleColor() {
		return baseCircleColor;
	}

	/**
	 * @param baseCircleColor
	 *            the baseCircleColor to set
	 */
	public void setBaseCircleColor(Color baseCircleColor) {
		this.baseCircleColor = baseCircleColor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.gauge.core.needle.GaugeNeedlePainter#paintNeedle
	 * (java.awt.Graphics2D,
	 * com.jensoft.core.plugin.gauge.core.GaugeMetricsPath)
	 */
	@Override
	public void paintNeedle(Graphics2D g2d, GaugeMetricsPath gaugeMetricsPath) {

		Point2D needleBase = gaugeMetricsPath.getNeedleBaseAnchorBinder().bindAnchor(gaugeMetricsPath.getBody().getGauge());
		Point2D needleValue = gaugeMetricsPath.getNeedleValueAnchorBinder().bindAnchor(gaugeMetricsPath.getBody().getGauge());

		Line2D needleLineBase = new Line2D.Double(needleBase, needleValue);
		GeometryPath geomPath1 = new GeometryPath(needleLineBase);
		double centerX = needleBase.getX();
		double centerY = needleBase.getY();
		double px, py;
		px = centerX + towardOffset * Math.sin(geomPath1.angleAtLength(0) + 3 * Math.PI / 2);
		py = centerY - towardOffset * Math.cos(geomPath1.angleAtLength(0) + 3 * Math.PI / 2);
		Line2D needleLineBaseExtends = new Line2D.Double(px, py, needleValue.getX(), needleValue.getY());

		g2d.setColor(baseColor);
		g2d.setStroke(new BasicStroke(2.2f));
		g2d.draw(needleLineBaseExtends);

		g2d.setColor(baseCircleColor);
		Ellipse2D e = new Ellipse2D.Double(centerX - baseCircleRadius, centerY - baseCircleRadius, 2 * baseCircleRadius, 2 * baseCircleRadius);
		g2d.fill(e);

	}

}
