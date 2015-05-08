/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.gauge.core.binder.path;

import java.awt.Shape;
import java.awt.geom.Arc2D;

import org.jensoft.core.plugin.gauge.core.RadialGauge;
import org.jensoft.core.plugin.gauge.core.binder.PathBinder;

/**
 * <code>PathArcManualBinder</code> binds a an arc which is define by radius,
 * start angle degree end extend angle degree and possibly shifted from gauge
 * center with shift radius and shift angle degree.
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class PathArcManualBinder extends PathBinder {

	/** binder radius */
	private int radius;

	/** binder start angle degree */
	private float startAngleDegree;

	/** binder extends angle degree */
	private float extendsAngleDegree;

	/** shift radius */
	private int shiftRadius = 0;

	/** shift angle degree */
	private float shiftAngleDegree = 0;

	/**
	 * create binder for center the arc define by given values.
	 * 
	 * @param radius
	 * @param startAngleDegree
	 * @param extendsAngleDegree
	 */
	public PathArcManualBinder(int radius, float startAngleDegree, float extendsAngleDegree) {
		super();
		this.radius = radius;
		this.startAngleDegree = startAngleDegree;
		this.extendsAngleDegree = extendsAngleDegree;
	}

	/**
	 * create binder for shifted arc define by given values.
	 * 
	 * @param radius
	 * @param startAngleDegree
	 * @param extendsAngleDegree
	 * @param shiftRadius
	 * @param shiftAngleDegree
	 * 
	 */
	public PathArcManualBinder(int radius, float startAngleDegree, float extendsAngleDegree, int shiftRadius, float shiftAngleDegree) {
		this(radius, startAngleDegree, extendsAngleDegree);
		this.shiftRadius = shiftRadius;
		this.shiftAngleDegree = shiftAngleDegree;
	}

	/**
	 * get binder radius
	 * 
	 * @return binder radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * set binder radius
	 * 
	 * @param radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * set start angle degree
	 * 
	 * @return start angle degree
	 */
	public float getStartAngleDegree() {
		return startAngleDegree;
	}

	/**
	 * set start angle degree
	 * 
	 * @param startAngleDegree
	 */
	public void setStartAngleDegree(float startAngleDegree) {
		this.startAngleDegree = startAngleDegree;
	}

	/**
	 * get extends angle degree
	 * 
	 * @return extends angle degree
	 */
	public float getExtendsAngleDegree() {
		return extendsAngleDegree;
	}

	/**
	 * set extends angle radius
	 * 
	 * @param extendsAngleDegree
	 */
	public void setExtendsAngleDegree(float extendsAngleDegree) {
		this.extendsAngleDegree = extendsAngleDegree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jensoft.core.plugin.gauge.core.binder.PathBinder#bindPath(org.jensoft
	 * .core.plugin.gauge.core.RadialGauge)
	 */
	@Override
	public Shape bindPath(RadialGauge gauge) {
		double centerX = gauge.getCenterDevice().getX();
		double centerY = gauge.getCenterDevice().getY();
		double shiftCenterX = centerX + shiftRadius * Math.cos(Math.toRadians(shiftAngleDegree));
		double shiftCenterY = centerY - shiftRadius * Math.sin(Math.toRadians(shiftAngleDegree));
		Arc2D arc = new Arc2D.Double(shiftCenterX - getRadius(), shiftCenterY - getRadius(), 2 * getRadius(), 2 * getRadius(), getStartAngleDegree(), getExtendsAngleDegree(), Arc2D.OPEN);
		return arc;
	}
}
