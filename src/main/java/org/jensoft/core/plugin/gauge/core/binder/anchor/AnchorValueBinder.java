/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.gauge.core.binder.anchor;

import java.awt.geom.Point2D;

import org.jensoft.core.glyphmetrics.Side;
import org.jensoft.core.plugin.gauge.core.RadialGauge;
import org.jensoft.core.plugin.gauge.core.binder.AnchorBinder;

/**
 * <code>AnchorValueBinder</code> binds to a value anchor solved from path and
 * value according to the given side of path and radial offset from path
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class AnchorValueBinder extends AnchorBinder {

	private int radialOffset;
	private Side side;

	/**
	 * create binder to the gauge center (default radius = 0)
	 */
	public AnchorValueBinder() {
		radialOffset = 0;
		side = Side.SideLeft;
	}

	/**
	 * create base anchor with given anchor parameters
	 * 
	 * @param radius
	 * @param angleDegree
	 */
	public AnchorValueBinder(int offsetRadiusFromPath, Side side) {
		super();
		this.radialOffset = offsetRadiusFromPath;
		this.side = side;
	}

	/**
	 * @return the radialOffset
	 */
	public int getRadialOffset() {
		return radialOffset;
	}

	/**
	 * @param radialOffset
	 *            the radialOffset to set
	 */
	public void setRadialOffset(int radialOffset) {
		this.radialOffset = radialOffset;
	}

	/**
	 * @return the side
	 */
	public Side getSide() {
		return side;
	}

	/**
	 * @param side
	 *            the side to set
	 */
	public void setSide(Side side) {
		this.side = side;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.gauge.core.binder.AnchorBinder#bindAnchor(com
	 * .jensoft.core.plugin.gauge.core.RadialGauge)
	 */
	@Override
	public Point2D bindAnchor(RadialGauge gauge) {
		Point2D needle = getMetricsPath().getRadialPoint(getMetricsPath().getCurrentValue(), radialOffset, side);
		return needle;
	}

}
