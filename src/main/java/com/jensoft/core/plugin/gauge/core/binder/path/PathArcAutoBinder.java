/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.binder.path;

import java.awt.Shape;
import java.awt.geom.Arc2D;

/**
 * <code>PathAutoBinder</code> binds automatically an arc path segment that intersect
 * the gauge circle model.
 
 * <p>
 * clockwise or anti clockwise direction.
 * </p>
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class PathArcAutoBinder extends AbstractPathAutoBinder {

	/** the arc which is bind by this binder */
	private Arc2D intersectionArc;

	/**
	 * create auto path arc binder according to the given parameters
	 * 
	 * @param radius
	 * @param polarRadius
	 * @param polarDegree
	 */
	public PathArcAutoBinder(int radius, int polarRadius, float polarDegree) {
		super(radius, polarRadius, polarDegree);
	}

	/**
	 * create auto path arc binder according to the given parameters
	 * 
	 * @param radius
	 * @param polarRadius
	 * @param polarDegree
	 * @param direction
	 */
	public PathArcAutoBinder(int radius, int polarRadius, float polarDegree, Direction direction) {
		super(radius, polarRadius, polarDegree, direction);
	}

	/**
	 * @return the intersectionArc
	 */
	public Arc2D getIntersectionArc() {
		return intersectionArc;
	}

	/**
	 * @param intersectionArc
	 *            the intersectionArc to set
	 */
	public void setIntersectionArc(Arc2D intersectionArc) {
		this.intersectionArc = intersectionArc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.gauge.core.binder.path.PathAutoBinder#createPath
	 * ()
	 */
	@Override
	public Shape createPath() {
		if (polarDegree > 0 && polarDegree < 180) {
			if (theta1Radian2 > theta1Radian1) {
				double extendsDegree = Math.toDegrees(theta1Radian2) - Math.toDegrees(theta1Radian1);
				if (direction == Direction.AntiClockwise) {
					intersectionArc = new Arc2D.Double(x1 - r1, y1 - r1, 2 * r1, 2 * r1, Math.toDegrees(theta1Radian1), extendsDegree, Arc2D.OPEN);
				} else if (direction == Direction.Clockwise) {
					intersectionArc = new Arc2D.Double(x1 - r1, y1 - r1, 2 * r1, 2 * r1, Math.toDegrees(theta1Radian2), -extendsDegree, Arc2D.OPEN);
				}

			} else {
				double extendsDegree = Math.abs(Math.toDegrees(2 * Math.PI + theta1Radian2) - Math.toDegrees(theta1Radian1));
				if (direction == Direction.AntiClockwise) {
					intersectionArc = new Arc2D.Double(x1 - r1, y1 - r1, 2 * r1, 2 * r1, Math.toDegrees(theta1Radian1), extendsDegree, Arc2D.OPEN);
				} else if (direction == Direction.Clockwise) {
					intersectionArc = new Arc2D.Double(x1 - r1, y1 - r1, 2 * r1, 2 * r1, Math.toDegrees(theta1Radian2), -extendsDegree, Arc2D.OPEN);
				}

			}
		} else if (polarDegree > 180 && polarDegree < 360) {
			if (theta1Radian2 > theta1Radian1) {
				double extendsDegree = (360 - (Math.toDegrees(theta1Radian2) - Math.toDegrees(theta1Radian1)));
				if (direction == Direction.AntiClockwise) {
					intersectionArc = new Arc2D.Double(x1 - r1, y1 - r1, 2 * r1, 2 * r1, Math.toDegrees(theta1Radian2), extendsDegree, Arc2D.OPEN);
				} else if (direction == Direction.Clockwise) {
					intersectionArc = new Arc2D.Double(x1 - r1, y1 - r1, 2 * r1, 2 * r1, Math.toDegrees(theta1Radian1), -extendsDegree, Arc2D.OPEN);
				}

			} else {
				double extendsDegree = (Math.toDegrees(theta1Radian1) - Math.toDegrees(theta1Radian2));
				if (direction == Direction.AntiClockwise) {
					intersectionArc = new Arc2D.Double(x1 - r1, y1 - r1, 2 * r1, 2 * r1, Math.toDegrees(theta1Radian2), extendsDegree, Arc2D.OPEN);
				} else if (direction == Direction.Clockwise) {
					intersectionArc = new Arc2D.Double(x1 - r1, y1 - r1, 2 * r1, 2 * r1, Math.toDegrees(theta1Radian1), -extendsDegree, Arc2D.OPEN);
				}

			}
		} else if (polarDegree == 0) {
			double extendsDegree = Math.toDegrees(theta1Radian2) - Math.toDegrees(theta1Radian1);
			if (direction == Direction.AntiClockwise) {
				intersectionArc = new Arc2D.Double(x1 - r1, y1 - r1, 2 * r1, 2 * r1, Math.toDegrees(theta1Radian1), extendsDegree, Arc2D.OPEN);
			} else if (direction == Direction.Clockwise) {
				intersectionArc = new Arc2D.Double(x1 - r1, y1 - r1, 2 * r1, 2 * r1, Math.toDegrees(theta1Radian2), -extendsDegree, Arc2D.OPEN);
			}
		}

		else if (polarDegree == 180) {
			double extendsDegree = 360 - Math.toDegrees(theta1Radian2) + Math.toDegrees(theta1Radian1);
			if (direction == Direction.AntiClockwise) {
				intersectionArc = new Arc2D.Double(x1 - r1, y1 - r1, 2 * r1, 2 * r1, Math.toDegrees(theta1Radian2), extendsDegree, Arc2D.OPEN);
			} else if (direction == Direction.Clockwise) {
				intersectionArc = new Arc2D.Double(x1 - r1, y1 - r1, 2 * r1, 2 * r1, Math.toDegrees(theta1Radian1), -extendsDegree, Arc2D.OPEN);
			}
		}
		return intersectionArc;
	}

}
