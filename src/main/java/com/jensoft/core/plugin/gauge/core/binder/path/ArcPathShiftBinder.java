/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.binder.path;

import java.awt.Shape;
import java.awt.geom.Arc2D;

import com.jensoft.core.plugin.gauge.core.RadialGauge;

/**
 * <code>ArcPathShiftBinder</code> binds a centered arc which is define by
 * radius, start angle degree end extend angle degree.
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class ArcPathShiftBinder extends ArcPathBinder {

	/**shift radius*/
	private int shiftRadius;
	
	/**shift angle degree*/
	private float shiftAngleDegree;

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
	public ArcPathShiftBinder(int radius, float startAngleDegree, float extendsAngleDegree, int shiftRadius, float shiftAngleDegree) {
		super(radius, startAngleDegree, extendsAngleDegree);
		this.shiftRadius = shiftRadius;
		this.shiftAngleDegree = shiftAngleDegree;
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
		double shiftCenterX = centerX + shiftRadius*Math.cos(Math.toRadians(shiftAngleDegree));
		double shiftCenterY = centerY - shiftRadius*Math.sin(Math.toRadians(shiftAngleDegree));
		Arc2D arc = new Arc2D.Double(shiftCenterX - getRadius(), shiftCenterY - getRadius(), 2 * getRadius(), 2 * getRadius(), getStartAngleDegree(), getExtendsAngleDegree(), Arc2D.OPEN);
		return arc;
	}
}
