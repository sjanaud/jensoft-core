package com.jensoft.core.plugin.gauge.core.binder.path;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.jensoft.core.palette.Alpha;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.gauge.core.RadialGauge;

public class PathCubicAutoBinder extends AbstractPathAutoBinder {

	/** the cubic curve which is bind by this binder */
	private CubicCurve2D intersectionCubicCurve;

	/** control offset radius */
	private double controlOffsetRadius = 10;

	/** control offset angle degree */
	private double controlOffsetAngleDegree = 10;

	/**
	 * create auto path arc binder according to the given parameters
	 * 
	 * @param radius
	 * @param polarRadius
	 * @param polarDegree
	 */
	public PathCubicAutoBinder(int radius, int polarRadius, float polarDegree) {
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
	public PathCubicAutoBinder(int radius, int polarRadius, float polarDegree, Direction direction) {
		super(radius, polarRadius, polarDegree, direction);
	}

	/**
	 * @return the intersectionCubicCurve
	 */
	public CubicCurve2D getIntersectionCubicCurve() {
		return intersectionCubicCurve;
	}

	/**
	 * @param intersectionCubicCurve
	 *            the intersectionCubicCurve to set
	 */
	public void setIntersectionCubicCurve(CubicCurve2D intersectionCubicCurve) {
		this.intersectionCubicCurve = intersectionCubicCurve;
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
	 * @return the controlOffsetAngleDegree
	 */
	public double getControlOffsetAngleDegree() {
		return controlOffsetAngleDegree;
	}

	/**
	 * @param controlOffsetAngleDegree
	 *            the controlOffsetAngleDegree to set
	 */
	public void setControlOffsetAngleDegree(double controlOffsetAngleDegree) {
		if (controlOffsetAngleDegree < 0)
			throw new IllegalArgumentException("control offset angle must be positive");
		this.controlOffsetAngleDegree = controlOffsetAngleDegree;
	}

	/**
	 * create quadratic segment from start to end point
	 * 
	 * @return quadratic segment
	 */
	private CubicCurve2D createQuadStart2End() {
		return new CubicCurve2D.Double(intersectionPointStart.getX(), intersectionPointStart.getY(), getControlPoint1().getX(), getControlPoint1().getY(), getControlPoint2().getX(), getControlPoint2().getY(), intersectionPointEnd.getX(), intersectionPointEnd.getY());
	}

	/**
	 * create quadratic segment from end to start point
	 * 
	 * @return quadratic segment
	 */
	private CubicCurve2D createQuadEnd2Start() {
		return new CubicCurve2D.Double(intersectionPointEnd.getX(), intersectionPointEnd.getY(), getControlPoint1().getX(), getControlPoint1().getY(), getControlPoint2().getX(), getControlPoint2().getY(), intersectionPointStart.getX(), intersectionPointStart.getY());
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
				intersectionCubicCurve = createQuadStart2End();
			} else if (direction == Direction.Clockwise) {
				intersectionCubicCurve = createQuadEnd2Start();
			}
		} else if (polarDegree >= 180 && polarDegree < 360) {
			if (direction == Direction.AntiClockwise) {
				intersectionCubicCurve = createQuadEnd2Start();
			} else if (direction == Direction.Clockwise) {
				intersectionCubicCurve = createQuadStart2End();
			}

		}
		return intersectionCubicCurve;
	}

	/**
	 * return the control point 1 according the cubic binder configuration
	 * 
	 * @return control point
	 */
	private Point2D getControlPoint1() {
		if (direction == Direction.Clockwise) {
			double x = x1 + (radius + controlOffsetRadius) * Math.cos(Math.toRadians(polarDegree) + Math.PI + Math.toRadians(controlOffsetAngleDegree));
			double y = y1 - (radius + controlOffsetRadius) * Math.sin(Math.toRadians(polarDegree) + Math.PI + Math.toRadians(controlOffsetAngleDegree));
			return new Point2D.Double(x, y);
		} else {
			double x = x1 + (radius + controlOffsetRadius) * Math.cos(Math.toRadians(polarDegree) + Math.PI - Math.toRadians(controlOffsetAngleDegree));
			double y = y1 - (radius + controlOffsetRadius) * Math.sin(Math.toRadians(polarDegree) + Math.PI - Math.toRadians(controlOffsetAngleDegree));
			return new Point2D.Double(x, y);
		}

	}

	/**
	 * return the control point 2 according the cubic binder configuration
	 * 
	 * @return control point 2
	 */
	private Point2D getControlPoint2() {
		if (direction == Direction.Clockwise) {
			double x = x1 + (radius + controlOffsetRadius) * Math.cos(Math.toRadians(polarDegree) + Math.PI - Math.toRadians(controlOffsetAngleDegree));
			double y = y1 - (radius + controlOffsetRadius) * Math.sin(Math.toRadians(polarDegree) + Math.PI - Math.toRadians(controlOffsetAngleDegree));
			return new Point2D.Double(x, y);
		} else {
			double x = x1 + (radius + controlOffsetRadius) * Math.cos(Math.toRadians(polarDegree) + Math.PI + Math.toRadians(controlOffsetAngleDegree));
			double y = y1 - (radius + controlOffsetRadius) * Math.sin(Math.toRadians(polarDegree) + Math.PI + Math.toRadians(controlOffsetAngleDegree));
			return new Point2D.Double(x, y);
		}

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

		g2d.setColor(NanoChromatique.BLUE);
		g2d.draw(new Ellipse2D.Double(getControlPoint1().getX() - 2, getControlPoint1().getY() - 2, 4, 4));
		g2d.drawString("C1", (int) getControlPoint1().getX(), (int) getControlPoint1().getY());

		g2d.setColor(NanoChromatique.RED);
		g2d.draw(new Ellipse2D.Double(getControlPoint2().getX() - 2, getControlPoint2().getY() - 2, 4, 4));
		g2d.drawString("C2", (int) getControlPoint2().getX(), (int) getControlPoint2().getY());

		g2d.setColor(new Alpha(NanoChromatique.GREEN, 80));
		g2d.draw(new Ellipse2D.Double(x1 - (r1 + controlOffsetRadius), y1 - (r1 + controlOffsetRadius), 2 * (r1 + controlOffsetRadius), 2 * (r1 + controlOffsetRadius)));
	}

}
