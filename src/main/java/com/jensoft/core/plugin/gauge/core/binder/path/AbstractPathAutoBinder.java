/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.binder.path;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.GeometryPath;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.gauge.core.RadialGauge;
import com.jensoft.core.plugin.gauge.core.binder.PathBinder;

/**
 * <code>PathAutoBinder</code> binds automatically a path segment that intersect
 * the gauge circle model.
 * 
 * <p>
 * implements {@link #createPath()} method to create the path segment that links
 * intersection point 1 and path intersection point 2 according to geometry of
 * your choice.
 * </p>
 * <p>
 * clockwise or anti clockwise direction.
 * </p>
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public abstract class AbstractPathAutoBinder extends PathBinder {

	/** binder radius */
	protected int radius;

	/** polar radius */
	protected int polarRadius;

	/** polar angle degree */
	protected float polarDegree;

	/** direction */
	protected Direction direction = Direction.Clockwise;

	
	protected double x0, y0, r0;
	protected double x1, y1, r1;
	protected Arc2D arc0;
	protected Arc2D arc1;

	protected Point2D intersectionPointStart;
	protected double theta1Radian1;
	protected Point2D intersectionPointEnd;
	protected double theta1Radian2;

	/**
	 * <code>Direction<code> defines the clockwise or anti clockwise direction
	 * 
	 */
	public enum Direction {
		Clockwise, AntiClockwise;
	}

	/**
	 * create binder for auto arc with default clockwise direction
	 * 
	 * @param radius
	 * @param polarRadius
	 * @param polarDegree
	 * 
	 */
	public AbstractPathAutoBinder(int radius, int polarRadius, float polarDegree) {
		this.radius = radius;
		this.polarRadius = polarRadius;
		this.polarDegree = polarDegree;
	}

	/**
	 * create binder for auto arc with given parameters
	 * 
	 * @param radius
	 * @param polarRadius
	 * @param polarDegree
	 * @param direction
	 */
	public AbstractPathAutoBinder(int radius, int polarRadius, float polarDegree, Direction direction) {
		super();
		this.radius = radius;
		this.polarRadius = polarRadius;
		this.polarDegree = polarDegree;
		this.direction = direction;
	}

	/**
	 * given the polar angle radian of point P(px,py) which is on the circle
	 * define by its center C(refX,refY)
	 * 
	 * @param refX
	 * @param refY
	 * @param px
	 * @param py
	 * @return polar angle radian
	 */
	private double getPolarAngle(double refX, double refY, double px, double py) {
		double tethaRadian = -1;
		if ((px - refX) > 0 && (refY - py) >= 0) {
			tethaRadian = Math.atan((refY - py) / (px - refX));
		} else if ((px - refX) > 0 && (refY - py) < 0) {
			tethaRadian = Math.atan((refY - py) / (px - refX)) + 2 * Math.PI;
		} else if ((px - refX) < 0) {
			tethaRadian = Math.atan((refY - py) / (px - refX)) + Math.PI;
		} else if ((px - refX) == 0 && (refY - py) > 0) {
			tethaRadian = Math.PI / 2;
		} else if ((px - refX) == 0 && (refY - py) < 0) {
			tethaRadian = 3 * Math.PI / 2;
		}
		return tethaRadian;
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
		solveIntersectionPoints();
		return createPath();
	}

	/**
	 * create the shape according to the binder
	 * 
	 * @return bind shape
	 */
	public abstract Shape createPath();

	/**
	 * solve the arc0 (gauge arc) and arc1(path arc) intersection
	 */
	private void solveIntersectionPoints() {
		RadialGauge gauge = getMetricsPath().getBody().getGauge();

		// define first circle which is gauge outline circle
		x0 = gauge.getCenterDevice().getX();
		y0 = gauge.getCenterDevice().getY();
		r0 = gauge.getRadius();
		arc0 = new Arc2D.Double(x0 - r0, y0 - r0, 2 * r0, 2 * r0, 0, 360, Arc2D.OPEN);

		// define the second circle with given parameters
		x1 = x0 + polarRadius * Math.cos(Math.toRadians(polarDegree));
		y1 = y0 - polarRadius * Math.sin(Math.toRadians(polarDegree));
		r1 = radius;

		arc1 = new Arc2D.Double(x1 - r1, y1 - r1, 2 * r1, 2 * r1, 0, 360, Arc2D.OPEN);

		if (polarDegree != 0 && polarDegree != 180) {
			// Ax²+Bx+B = 0
			double N = (r1 * r1 - r0 * r0 - x1 * x1 + x0 * x0 - y1 * y1 + y0 * y0) / (2 * (y0 - y1));
			double A = Math.pow((x0 - x1) / (y0 - y1), 2) + 1;
			double B = 2 * y0 * (x0 - x1) / (y0 - y1) - 2 * N * (x0 - x1) / (y0 - y1) - 2 * x0;
			double C = x0 * x0 + y0 * y0 + N * N - r0 * r0 - 2 * y0 * N;
			double delta = Math.sqrt(B * B - 4 * A * C);

			if (delta < 0) {
				System.out.println("no solution");
			} else if (delta >= 0) {

				// p1
				double p1x = (-B - delta) / (2 * A);
				double p1y = N - p1x * (x0 - x1) / (y0 - y1);
				intersectionPointStart = new Point2D.Double(p1x, p1y);

				// p2
				double p2x = (-B + delta) / (2 * A);
				double p2y = N - p2x * (x0 - x1) / (y0 - y1);
				intersectionPointEnd = new Point2D.Double(p2x, p2y);

				theta1Radian1 = getPolarAngle(x1, y1, p1x, p1y);
				theta1Radian2 = getPolarAngle(x1, y1, p2x, p2y);

			}
		} else if (polarDegree == 0 || polarDegree == 180) {
			// polar degree = 0|180 -> y0=y1
			// Ay²+By + C = 0;
			double x = (r1 * r1 - r0 * r0 - x1 * x1 + x0 * x0) / (2 * (x0 - x1));
			double A = 1;
			double B = -2 * y1;
			double C = x1 * x1 + x * x - 2 * x1 * x + y1 * y1 - r1 * r1;
			double delta = Math.sqrt(B * B - 4 * A * C);

			if (delta < 0) {
				System.out.println("no solution");
			} else if (delta >= 0) {

				// p1
				double p1x = x;
				double p1y = (-B - delta) / 2 * A;
				intersectionPointStart = new Point2D.Double(p1x, p1y);

				// p2
				double p2x = x;
				double p2y = (-B + delta) / 2 * A;
				intersectionPointEnd = new Point2D.Double(p2x, p2y);

				theta1Radian1 = getPolarAngle(x1, y1, p1x, p1y);
				theta1Radian2 = getPolarAngle(x1, y1, p2x, p2y);

			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.gauge.core.binder.PathBinder#debug(java.awt.
	 * Graphics2D, com.jensoft.core.plugin.gauge.core.RadialGauge)
	 */
	@Override
	public void debug(Graphics2D g2d, RadialGauge gauge) {

		solveIntersectionPoints();

		g2d.setColor(NanoChromatique.BLACK);
		g2d.draw(arc0);

		g2d.setColor(NanoChromatique.YELLOW.brighter());
		g2d.draw(arc1);

		g2d.setColor(NanoChromatique.YELLOW.brighter());
		g2d.setColor(NanoChromatique.YELLOW.brighter());
		g2d.setFont(InputFonts.getSansation(14));
		g2d.drawString("PATH BINDER : " + this.getClass().getSimpleName(), 20, 60);
		g2d.drawString("GAUGE RADIUS : " + getMetricsPath().getBody().getGauge().getRadius(), 20, 80);
		g2d.drawString("BINDER RADIUS : " + radius, 20, 100);
		g2d.drawString("BINDER POLAR RADIUS : " + polarRadius, 20, 120);
		g2d.drawString("BINDER POLAR DEGREE : " + polarDegree, 20, 140);
		g2d.drawString("BINDER DIRECTION : " + direction.name(), 20, 160);

		Color color = null;
		if (direction == Direction.Clockwise)
			color = NanoChromatique.BLUE.brighter();
		else
			color = NanoChromatique.RED.brighter();

		if (intersectionPointStart == null || intersectionPointEnd == null)
			return;

		g2d.setColor(color);
		g2d.fill(new Ellipse2D.Double(intersectionPointStart.getX() - 3, intersectionPointStart.getY() - 3, 6, 6));
		g2d.fill(new Ellipse2D.Double(intersectionPointEnd.getX() - 3, intersectionPointEnd.getY() - 3, 6, 6));
		drawPath(g2d, createPath(), color);

		g2d.setColor(NanoChromatique.BLUE.brighter());
		g2d.setFont(InputFonts.getSansation(14));
		g2d.drawString("I1", (int) intersectionPointStart.getX() + 5, (int) intersectionPointStart.getY());

		g2d.setColor(NanoChromatique.RED.brighter());
		g2d.drawString("I2", (int) intersectionPointEnd.getX() + 5, (int) intersectionPointEnd.getY());

	}

	/**
	 * draw the given path with given color
	 * 
	 * @param g2d
	 * @param path
	 * @param c
	 */
	private void drawPath(Graphics2D g2d, Shape path, Color c) {
		if (path == null)
			return;
		g2d.setColor(c);
		g2d.draw(path);
		GeometryPath geom = new GeometryPath(path);

		Shape s1 = creatTickDirection(path, geom.lengthOfPath() / 2, 4);
		Shape s2 = creatTickDirection(path, geom.lengthOfPath() / 4, 3);
		Shape s3 = creatTickDirection(path, geom.lengthOfPath() * 3 / 4, 3);
		if (s1 != null)
			g2d.fill(s1);
		if (s2 != null)
			g2d.fill(s2);
		if (s3 != null)
			g2d.fill(s3);

	}

	/**
	 * create tick direction according to path direction
	 * 
	 * @param shape
	 * @param length
	 * @param size
	 * @return tick shape
	 */
	private Shape creatTickDirection(Shape shape, float length, int size) {
		GeometryPath geom = new GeometryPath(shape);
		int div = size;
		if (length - div > 0 && length + 2 * div < geom.lengthOfPath()) {
			GeneralPath path = new GeneralPath();
			Point2D p1 = geom.pointAtLength(length + 2 * div);
			Point2D pl = geom.orthoLeftPointAtLength(length - div, div);
			Point2D pr = geom.orthoRightPointAtLength(length - div, div);
			path.moveTo(p1.getX(), p1.getY());
			path.lineTo(pr.getX(), pr.getY());
			path.lineTo(pl.getX(), pl.getY());
			path.closePath();
			return path;
		}
		return null;
	}
}
