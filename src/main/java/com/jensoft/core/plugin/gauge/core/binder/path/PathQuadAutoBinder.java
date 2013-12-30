package com.jensoft.core.plugin.gauge.core.binder.path;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

import com.jensoft.core.palette.Alpha;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.gauge.core.RadialGauge;

public class PathQuadAutoBinder extends AbstractPathAutoBinder {

	/** the quadratic curve which is bind by this binder */
	private QuadCurve2D intersectionQuadCurve;

	/** control offset radius */
	private double controlOffsetRadius = 10;

	/**
	 * create auto path arc binder according to the given parameters
	 * 
	 * @param radius
	 * @param polarRadius
	 * @param polarDegree
	 */
	public PathQuadAutoBinder(int radius, int polarRadius, float polarDegree) {
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
	public PathQuadAutoBinder(int radius, int polarRadius, float polarDegree, Direction direction) {
		super(radius, polarRadius, polarDegree, direction);
	}

	/**
	 * @return the intersectionQuadCurve
	 */
	public QuadCurve2D getIntersectionQuadCurve() {
		return intersectionQuadCurve;
	}

	/**
	 * @param intersectionQuadCurve
	 *            the intersectionQuadCurve to set
	 */
	public void setIntersectionQuadCurve(QuadCurve2D intersectionQuadCurve) {
		this.intersectionQuadCurve = intersectionQuadCurve;
	}

	/**
	 * @return the controlOffsetRadius
	 */
	public double getControlOffsetRadius() {
		return controlOffsetRadius;
	}

	/**
	 * @param controlOffsetRadius
	 *            the controlOffsetRadius to set
	 */
	public void setControlOffsetRadius(double controlOffsetRadius) {
		if (controlOffsetRadius < 0)
			throw new IllegalArgumentException("control offset radius must be positive");
		this.controlOffsetRadius = controlOffsetRadius;
	}

	/**
	 * create quadratic segment from start to end point
	 * 
	 * @return quadratic segment
	 */
	private QuadCurve2D createQuadStart2End() {
		return new QuadCurve2D.Double(intersectionPointStart.getX(), intersectionPointStart.getY(), getControlPoint().getX(), getControlPoint().getY(), intersectionPointEnd.getX(), intersectionPointEnd.getY());
	}

	/**
	 * create quadratic segment from end to start point
	 * 
	 * @return quadratic segment
	 */
	private QuadCurve2D createQuadEnd2Start() {
		return new QuadCurve2D.Double(intersectionPointEnd.getX(), intersectionPointEnd.getY(), getControlPoint().getX(), getControlPoint().getY(), intersectionPointStart.getX(), intersectionPointStart.getY());
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
		if (intersectionPointStart == null || intersectionPointEnd == null)
			return null;
		if (polarDegree >= 0 && polarDegree < 180) {
			if (direction == Direction.AntiClockwise) {
				intersectionQuadCurve = createQuadStart2End();
			} else if (direction == Direction.Clockwise) {
				intersectionQuadCurve = createQuadEnd2Start();
			}
		} else if (polarDegree >= 180 && polarDegree < 360) {
			if (direction == Direction.AntiClockwise) {
				intersectionQuadCurve = createQuadEnd2Start();
			} else if (direction == Direction.Clockwise) {
				intersectionQuadCurve = createQuadStart2End();
			}

		}
		return intersectionQuadCurve;
	}

	/**
	 * return the control point according the quadratic binder configuration
	 * 
	 * @return control point
	 */
	private Point2D getControlPoint() {
		double x = x1 + (radius + controlOffsetRadius) * Math.cos(Math.toRadians(polarDegree) + Math.PI);
		double y = y1 - (radius + controlOffsetRadius) * Math.sin(Math.toRadians(polarDegree) + Math.PI);
		return new Point2D.Double(x, y);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.gauge.core.binder.path.PathAutoBinder#debug(java
	 * .awt.Graphics2D, com.jensoft.core.plugin.gauge.core.RadialGauge)
	 */
	@Override
	public void debug(Graphics2D g2d, RadialGauge gauge) {
		super.debug(g2d, gauge);
		g2d.setColor(NanoChromatique.GREEN);

		g2d.draw(new Ellipse2D.Double(getControlPoint().getX() - 2, getControlPoint().getY() - 2, 4, 4));

		g2d.setColor(new Alpha(NanoChromatique.GREEN, 100));
		g2d.draw(new Ellipse2D.Double(x1 - (r1 + controlOffsetRadius), y1 - (r1 + controlOffsetRadius), 2 * (r1 + controlOffsetRadius), 2 * (r1 + controlOffsetRadius)));
	}

}
