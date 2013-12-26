/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.binder.path;

import java.awt.Shape;
import java.awt.geom.Arc2D;

import com.jensoft.core.plugin.gauge.core.RadialGauge;
import com.jensoft.core.plugin.gauge.core.binder.PathBinder;

/**
 * <code>GaugeArcPathBinder</code> binds a centered arc which is define by
 * radius, start angle degree end extend angle degree.
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class ArcPathBinder extends PathBinder {

	private int radius;
	private float startAngleDegree;
	private float extendsAngleDegree;

	/**
	 * create binder for center the arc define by given values.
	 * 
	 * @param radius
	 * @param startAngleDegree
	 * @param extendsAngleDegree
	 */
	public ArcPathBinder(int radius, float startAngleDegree, float extendsAngleDegree) {
		super();
		this.radius = radius;
		this.startAngleDegree = startAngleDegree;
		this.extendsAngleDegree = extendsAngleDegree;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public float getStartAngleDegree() {
		return startAngleDegree;
	}

	public void setStartAngleDegree(float startAngleDegree) {
		this.startAngleDegree = startAngleDegree;
	}

	public float getExtendsAngleDegree() {
		return extendsAngleDegree;
	}

	public void setExtendsAngleDegree(float extendsAngleDegree) {
		this.extendsAngleDegree = extendsAngleDegree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.gauge.core.binder.PathBinder#bindPath(com.jensoft
	 * .core.plugin.gauge.core.RadialGauge)
	 */
	@Override
	public Shape bindPath(RadialGauge gauge) {
		double centerX = gauge.getCenterDevice().getX();
		double centerY = gauge.getCenterDevice().getY();
		Arc2D arc = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegree, extendsAngleDegree, Arc2D.OPEN);
		return arc;
	}

}
