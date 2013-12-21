/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics;

import java.awt.Font;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>GeneralMetricsPath</code> <br>
 * 
 * <h4>Usage<h4>GeneralMetricsPath which draws path with metrics text<br>
 * with various label and appealing rendering.<br>
 * Application Domain : Gauge <h4>Description<h4>The general metrics path
 * interpretation and concept understanding should be defined by :<br>
 * A path which is defines by a geometry and the path represent locally metrics
 * values along the path between the defined dimension min and a max value which
 * are set.You can add along some label and text for reference metrics you wish,
 * render between min and max value proportional related to the given metric
 * value. <br>
 * <br> <h4>Properties</h4> properties summary for GeneralMetricsPath:<br>
 * <br>
 * <p>
 * (1) - Geometry the path is a built-in path that can be built with base
 * geometry such as arc, cubic curve, quadratic curve, line or any another shape
 * geometry which are append. The path projection which is set takes the
 * responsibility to transform path in the desired available coordinate system.
 * </p>
 * <p>
 * (2) Path Projection<br>
 * Two projections are available:<br>
 * local and user<br>
 * <br>
 * Local Path Projection {@link AbstractMetricsPath.ProjectionNature#DEVICE}
 * process and solve<br>
 * geometry in the component system coordinate and then path is view a
 * non-vector metrics<br>
 * path and then can not support use full related object such a zoom plug in or
 * translate<br>
 * plug in which work in the user coordinate support by window.<br>
 * <br>
 * <br>
 * User Path Projection {@link AbstractMetricsPath.ProjectionNature#DEVICE}
 * process and solve <br>
 * geometry in the user system coordinate and then this processing mode make
 * possible<br>
 * to obtain vector metrics path.<br>
 * </p>
 * <p>
 * (3) - Path Dimension min and max should be defined to assign a scalar
 * dimension to this path. the path has a length and then path can graduate with
 * metrics call {@link GlyphMetric} that represents label and text annotations
 * along the path.
 * </p>
 * <p>
 * (4) - You should add some {@link GlyphMetric} on the path and choosing some
 * rendering properties and rendering strategy (reverse glyph for example).
 * </p>
 * <h4>Path Building</h4>
 * <p>
 * Path building step:<br>
 * (1)create a path and choose projection. (2)define segment like arc, lines,
 * cubic, quadratic other shapes and append to the path, see
 * {@link #append(Shape)} or {@link #moveTo(double, double)} that add segment
 * entry to the path. (3)define min and max dimension value for the path.
 * (4)create metrics and add metrics glyph {@link #addMetric(GlyphMetric)} to
 * path. (5)solved metrics geometry should supplied by {@link #getMetrics()}
 * method moreover developer can be get the radial point to specified metrics
 * value on a path with method {@link #getRadialPoint(double, int, Side)}
 * </p>
 * <p>
 * {@link GeneralMetricsPath} gives a good accuracy to draw gauge path as shown.
 * <center><img src="doc-files/white-tachometer.png"></center><br>
 * <center><img src="doc-files/white-compass.png"></center><br>
 * <center><img src="doc-files/general-metrics1.png"></center><br>
 * </p>
 * 
 * @see AbstractMetricsPath
 * @see GlyphMetric
 * @see GlyphGeometry
 * @see GlyphMetricsToolkit
 * 
 * 
 * @author Sebastien Janaud
 */
public class GeneralMetricsPath extends AbstractMetricsPath {

	/** the minimum value in the user space */
	private double min;

	/** the maximum value in the user space */
	private double max;

	/** path metrics for this manager */
	private List<GlyphMetric> pmetrics;

	/** segment entries for this path manager */
	private List<SegmentEntry> entries;

	/** reverse all mode */
	private boolean reverseAll = false;

	/** delegate super geometry path for this metrics path */
	private GeometryPath geometry;

	/** length of path in the device space */
	private float lengthPathDevice;

	/** user length of path */
	private double userWidth;

	/** base unit between user and device */
	private double unitUserToDevice;

	/** volatile metrics registered for this path */
	private List<GlyphMetric> volatileMetrics = new ArrayList<GlyphMetric>();

	/**
	 * Creates a new instance of GeneralMetricsManager
	 **/
	public GeneralMetricsPath() {
		setProjectionNature(ProjectionNature.USER);
		pmetrics = new ArrayList<GlyphMetric>();
		entries = new ArrayList<SegmentEntry>();
	}
	
	/**
	 * set the ranges values of the path *
	 * 
	 * @param min
	 *            the minimum user metrics value for this path to set
	 * @param max
	 *            the maximum user metrics value for this path to set
	 *            
	 */
	public void setRange(double min, double max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * get the minimum value of the path
	 * 
	 * @return minimum user metrics for this path
	 */
	public double getMin() {
		return min;
	}

	/**
	 * set the minimum user value of the path *
	 * 
	 * @param min
	 *            the minimum user metrics for this path to set
	 */
	public void setMin(double min) {
		this.min = min;
	}

	/**
	 * get the maximum value of the path
	 * 
	 * @return maximum user metrics value for this path
	 */
	public double getMax() {
		return max;
	}

	/**
	 * set the maximum user value of the path
	 * 
	 * @param max
	 *            the maximum user metrics value for this path to set
	 */
	public void setMax(double max) {
		this.max = max;
	}

	/**
	 * indicates that if this general metrics is in mode reverse for all
	 * registred glyphs.<br>
	 * each glyph metrics will be reverse to be readable, that is to say text is
	 * read from left to right) if this mode is active, all displayed metrics
	 * text should be readable on the geometry without head contortion ;-) but
	 * sometimes, you would attempt an non inversion for any text, to make a
	 * compass for example, or gauge. you should be make you idea in trying
	 * effect by some demo where general path is used, on gauge for example.
	 * 
	 * @return true if all glyph be in reversing mode, false otherwise.
	 */
	public boolean isReverseAll() {
		return reverseAll;
	}

	/**
	 * set reverse all mode
	 * 
	 * @param reverseAll
	 *            the reverse all mode to set
	 */
	public void setReverseAll(boolean reverseAll) {
		this.reverseAll = reverseAll;
	}

	/**
	 * lock true for the reverse all mode
	 */
	public void lockReverseAll() {
		reverseAll = true;
	}

	/**
	 * lock false for the reverse all mode
	 */
	public void unlockReverseAll() {
		reverseAll = false;
	}

	/**
	 * init the segment path to the specified x y coordinate
	 * 
	 * @param x
	 * @param y
	 */
	public void moveTo(double x, double y) {
		entries.add(new MoveTo(x, y));
	}

	/**
	 * add a line segment path from the last x y added in the path to the new
	 * specified x y coordinate
	 * 
	 * @param x
	 * @param y
	 */
	public void lineTo(double x, double y) {
		entries.add(new LineTo(x, y));
	}

	/**
	 * add a quadratic segment path from the last x y added in the path to the
	 * new specified x y coordinate, with the control point cx cy coordinates
	 * 
	 * @param cx
	 * @param cy
	 * @param x
	 * @param y
	 */
	public void quadTo(double cx, double cy, double x, double y) {
		entries.add(new QuadTo(cx, cy, x, y));
	}

	/**
	 * add a cubic segment path from the last x y added in the path to the new
	 * specified x y coordinate, with the control point cx1 cy1 and cx2, cy2
	 * coordinates
	 * 
	 * @param cx1
	 * @param cy1
	 * @param cx2
	 * @param cy2
	 * @param x
	 * @param y
	 */
	public void curveTo(double cx1, double cy1, double cx2, double cy2, double x, double y) {
		entries.add(new CurveTo(cx1, cy1, cx2, cy2, x, y));
	}

	/**
	 * append a new segment shape to the path
	 * 
	 * @param shape
	 *            the shape to append in path
	 */
	public void append(Shape shape) {
		entries.add(new ShapeGeometry(shape));
	}

	/**
	 * reset the all segment which have been registered before.
	 */
	public void resetPath() {
		entries.clear();
	}

	/**
	 * the entry type
	 */
	enum EntryType {
		SEG_MOVETO, SEG_LINETO, SEG_QUADTO, SEG_CUBICTO, SEG_CLOSE, SEG_SHAPE;
	};

	/**
	 * register a new segment entry for this path manager
	 * 
	 * @param entry
	 */
	public void registerGeometrySegment(SegmentEntry entry) {
		entries.add(entry);
	}

	/**
	 * the abstract definition of a segment entry
	 */
	public abstract class SegmentEntry {

		/** the entry type */
		private EntryType type;

		/**
		 * get the type of the entry
		 * 
		 * @return the entry type of this segment
		 */
		public EntryType getType() {
			return type;
		}

		/**
		 * set the type of this segment
		 * 
		 * @param type
		 *            the type to set
		 */
		public void setType(EntryType type) {
			this.type = type;
		}

		/**
		 * valid this entry segment
		 * 
		 * @param path
		 *            the path to valid
		 */
		protected abstract void validEntry(GeneralPath path);

	}

	/**
	 * return true if the shape is empty
	 * 
	 * @param s
	 *            the shape to check
	 * @return true if path is empty, false otherwise
	 */
	public boolean isEmpty(Shape s) {

		PathIterator pi = s.getPathIterator(null);

		while (pi.isDone() == false) {
			double[] coordinates = new double[6];
			int type = pi.currentSegment(coordinates);

			if (type == PathIterator.SEG_MOVETO) {
				
				return false;
			}

			pi.next();
		}
		System.out.println("empty path ");
		return true;
	}

	/***
	 * define a shape segment entry
	 * 
	 * @author Sebastien Janaud
	 */
	public class ShapeGeometry extends SegmentEntry {

		/** shape entry */
		private Shape shape;

		/**
		 * create shape geometry
		 * 
		 * @param shape
		 *            the shape to set for this geometry
		 */
		public ShapeGeometry(Shape shape) {
			this.shape = shape;
			setType(EntryType.SEG_SHAPE);
		}

		/**
		 * valid segment of this segment shape entry
		 * 
		 * @param pi
		 * @param path
		 * @param nature
		 */
		public void validCurentSegment(PathIterator pi, GeneralPath path, ProjectionNature nature) {
			System.out.println("valid segment");
			
			double[] coordinates = new double[6];
			int type = pi.currentSegment(coordinates);
			
			switch (type) {

			case PathIterator.SEG_MOVETO:
				System.out.println("move to "+coordinates[0]+","+ coordinates[1]);
				Point2D pm = null;
				if (getProjectionNature() == ProjectionNature.USER) {
					pm = getWindow2d().userToPixel(new Point2D.Double(coordinates[0], coordinates[1]));
					//pm = getWindow2d().userToPixel(new Point2D.Double(coordinates[1], coordinates[0]));
				} else {
					pm = new Point2D.Double(coordinates[0], coordinates[1]);
				}

				if (isEmpty(path)) {
					path.moveTo(pm.getX(), pm.getY());
				} else {
					path.lineTo(pm.getX(), pm.getY());
				}
				break;
			case PathIterator.SEG_LINETO:
				Point2D pl;
				if (getProjectionNature() == ProjectionNature.USER) {
					pl = getWindow2d().userToPixel(new Point2D.Double(coordinates[0], coordinates[1]));
				} else {
					pl = new Point2D.Double(coordinates[0], coordinates[1]);
				}

				path.lineTo(pl.getX(), pl.getY());
				break;
			case PathIterator.SEG_QUADTO:
				Point2D pq1;
				Point2D pq2;
				if (getProjectionNature() == ProjectionNature.USER) {
					pq1 = getWindow2d().userToPixel(new Point2D.Double(coordinates[0], coordinates[1]));
					pq2 = getWindow2d().userToPixel(new Point2D.Double(coordinates[2], coordinates[3]));
				} else {
					pq1 = new Point2D.Double(coordinates[0], coordinates[1]);
					pq2 = new Point2D.Double(coordinates[2], coordinates[3]);
				}

				path.quadTo(pq1.getX(), pq1.getY(), pq2.getX(), pq2.getY());
				break;
			case PathIterator.SEG_CUBICTO:
				Point2D pc1;
				Point2D pc2;
				Point2D pc3;
				System.out.println("cubic to "+coordinates[0]+","+coordinates[1]+","+coordinates[2]+","+coordinates[3]+","+coordinates[4]+","+coordinates[5]);
				if (getProjectionNature() == ProjectionNature.USER) {
					pc1 = getWindow2d().userToPixel(new Point2D.Double(coordinates[0], coordinates[1]));
					pc2 = getWindow2d().userToPixel(new Point2D.Double(coordinates[2], coordinates[3]));
					pc3 = getWindow2d().userToPixel(new Point2D.Double(coordinates[4], coordinates[5]));
					
					//seems Arc2D get reverse path iterator!? --> send mail to Jim Grapham?
					//pc1 = getWindow2d().userToPixel(new Point2D.Double(coordinates[1], coordinates[0]));
					//pc2 = getWindow2d().userToPixel(new Point2D.Double(coordinates[3], coordinates[2]));
					//pc3 = getWindow2d().userToPixel(new Point2D.Double(coordinates[5], coordinates[4]));
					
				} else {
					
					pc1 = new Point2D.Double(coordinates[0], coordinates[1]);
					pc2 = new Point2D.Double(coordinates[2], coordinates[3]);
					pc3 = new Point2D.Double(coordinates[4], coordinates[5]);
				}

				path.curveTo(pc1.getX(), pc1.getY(), pc2.getX(), pc2.getY(), pc3.getX(), pc3.getY());
				break;
			case PathIterator.SEG_CLOSE:
				System.out.println("close to");
				path.closePath();
				break;
			default:
				break;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.glyphmetrics.GeneralMetricsPath.SegmentEntry#validEntry
		 * (java.awt.geom.GeneralPath)
		 */
		@Override
		protected void validEntry(GeneralPath path) {
			if (getProjectionNature() == ProjectionNature.DEVICE) {
				//path.append(shape, true);
				PathIterator pi = shape.getPathIterator(new AffineTransform());
				while (pi.isDone() == false) {
					validCurentSegment(pi, path, getProjectionNature());
					pi.next();
				}
			} else {
				PathIterator pi = shape.getPathIterator(new AffineTransform());
				while (pi.isDone() == false) {
					validCurentSegment(pi, path, getProjectionNature());
					pi.next();
				}

			}

		}

	}

	/**
	 * defines Close segment entry
	 * 
	 * @author Sebastien Janaud
	 */
	public class Close extends SegmentEntry {

		/**
		 * create close entry
		 */
		public Close() {
			setType(EntryType.SEG_CLOSE);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.glyphmetrics.GeneralMetricsPath.SegmentEntry#validEntry
		 * (java.awt.geom.GeneralPath)
		 */
		@Override
		protected void validEntry(GeneralPath path) {
			path.closePath();
		}
	}

	/**
	 * <p>
	 * defines Move To Segment entry
	 * </p>
	 * <p>
	 * Path should be initialize with an MoveTo entry.
	 * </p>
	 */
	public class MoveTo extends SegmentEntry {

		/** x coordinate for this move entry */
		double x;
		/** y coordinate for this move entry */
		double y;

		/**
		 * create move entry segment
		 * 
		 * @param x
		 *            the moveto x coordinate to set
		 * @param y
		 *            the moveto y coordinate to set
		 */
		public MoveTo(double x, double y) {
			this.x = x;
			this.y = y;
			setType(EntryType.SEG_MOVETO);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.glyphmetrics.GeneralMetricsPath.SegmentEntry#validEntry
		 * (java.awt.geom.GeneralPath)
		 */
		@Override
		protected void validEntry(GeneralPath path) {
			if (getProjectionNature() == ProjectionNature.USER) {
				Point2D p2dDevice = getWindow2d().userToPixel(new Point2D.Double(x, y));
				path.moveTo(p2dDevice.getX(), p2dDevice.getY());
			} else {
				path.moveTo(x, y);
			}
		}
	}

	/**
	 * defines Line segment entry
	 * 
	 * @author Sebastien Janaud
	 */
	public class LineTo extends SegmentEntry {

		/** the x coordinate for this line to entry */
		double x;

		/** the y coordinate for this line to entry */
		double y;

		/**
		 * create line To entry
		 * 
		 * @param x
		 *            the x coordinate for this Line to entry
		 * @param y
		 *            the y coordinate for this Line to entry
		 */
		public LineTo(double x, double y) {
			this.x = x;
			this.y = y;
			setType(EntryType.SEG_LINETO);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.glyphmetrics.GeneralMetricsPath.SegmentEntry#validEntry
		 * (java.awt.geom.GeneralPath)
		 */
		@Override
		protected void validEntry(GeneralPath path) {
			if (getProjectionNature() == ProjectionNature.USER) {
				Point2D p2dDevice = getWindow2d().userToPixel(new Point2D.Double(x, y));
				path.lineTo(p2dDevice.getX(), p2dDevice.getY());
			} else {
				path.lineTo(x, y);
			}

		}
	}

	/**
	 * defines Quadratic segment entry
	 * 
	 * @author Sebastien Janaud
	 */
	public class QuadTo extends SegmentEntry {

		/** x coordinate for the control of this quadratic segment entry */
		double xcontrol;

		/** y coordinate for the control of this quadratic segment entry */
		double ycontrol;

		/** x coordinate for this quadratic segment entry */
		double x;

		/** y coordinate for this quadratic segment entry */
		double y;

		/**
		 * create quadratic segment entry with specified quadratic parameters
		 * 
		 * @param xcontrol
		 *            the x control point coordinate of this quadratic entry to
		 *            set
		 * @param ycontrol
		 *            the y control point coordinate of this quadratic entry to
		 *            set
		 * @param x
		 *            the x point coordinate of this quadratic entry to set
		 * @param y
		 *            the y point coordinate of this quadratic entry to set
		 */
		public QuadTo(double xcontrol, double ycontrol, double x, double y) {
			this.xcontrol = xcontrol;
			this.ycontrol = ycontrol;
			this.x = x;
			this.y = y;
			setType(EntryType.SEG_QUADTO);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.glyphmetrics.GeneralMetricsPath.SegmentEntry#validEntry
		 * (java.awt.geom.GeneralPath)
		 */
		@Override
		protected void validEntry(GeneralPath path) {
			if (getProjectionNature() == ProjectionNature.USER) {
				Point2D p2dDeviceControl = getWindow2d().userToPixel(new Point2D.Double(xcontrol, ycontrol));
				Point2D p2dDevice = getWindow2d().userToPixel(new Point2D.Double(x, y));

				path.quadTo(p2dDeviceControl.getX(), p2dDeviceControl.getY(), p2dDevice.getX(), p2dDevice.getY());
			} else {
				path.quadTo(xcontrol, ycontrol, x, y);
			}

		}
	}

	/**
	 * defines Cubic segment entry
	 * 
	 * @author Sebastien Janaud
	 */
	public class CurveTo extends SegmentEntry {

		/** x coordinate for the first control of this cubic segment entry */
		double xcontrol1;

		/** y coordinate for the first control of this cubic segment entry */
		double ycontrol1;

		/** x coordinate for the second control of this cubic segment entry */
		double xcontrol2;

		/** y coordinate for the second control of this cubic segment entry */
		double ycontrol2;

		/** x coordinate for this cubic segment entry */
		double x;

		/** y coordinate for this cubic segment entry */
		double y;

		/**
		 * create cubic segment entry with specified parameters
		 * 
		 * @param xcontrol1
		 *            the x coordinate for the first control of this cubic
		 *            segment entry to set
		 * @param ycontrol1
		 *            the y coordinate for the first control of this cubic
		 *            segment entry to set
		 * @param xcontrol2
		 *            the x coordinate for the second control of this cubic
		 *            segment entry to set
		 * @param ycontrol2
		 *            the y coordinate for the second control of this cubic
		 *            segment entry to set
		 * @param x
		 *            the x coordinate for this cubic segment entry to set
		 * @param y
		 *            the x coordinate for this cubic segment entry to set
		 */
		public CurveTo(double xcontrol1, double ycontrol1, double xcontrol2, double ycontrol2, double x, double y) {
			this.xcontrol1 = xcontrol1;
			this.ycontrol1 = ycontrol1;
			this.xcontrol2 = xcontrol2;
			this.ycontrol2 = ycontrol2;
			this.x = x;
			this.y = y;
			setType(EntryType.SEG_CUBICTO);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.glyphmetrics.GeneralMetricsPath.SegmentEntry#validEntry
		 * (java.awt.geom.GeneralPath)
		 */
		@Override
		protected void validEntry(GeneralPath path) {
			if (getProjectionNature() == ProjectionNature.USER) {
				Point2D p2dDeviceControl1 = getWindow2d().userToPixel(new Point2D.Double(xcontrol1, ycontrol1));
				Point2D p2dDeviceControl2 = getWindow2d().userToPixel(new Point2D.Double(xcontrol2, ycontrol2));
				Point2D p2dDevice = getWindow2d().userToPixel(new Point2D.Double(x, y));

				path.curveTo(p2dDeviceControl1.getX(), p2dDeviceControl1.getY(), p2dDeviceControl2.getX(), p2dDeviceControl2.getY(), p2dDevice.getX(), p2dDevice.getY());
			} else {
				path.curveTo(xcontrol1, ycontrol1, xcontrol2, ycontrol2, x, y);
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.glyphmetrics.AbstractMetricsPath#createPathMetrics()
	 */
	@Override
	protected GeneralPath createPathMetrics() {
		System.out.println("create metrics path");
		GeneralPath path = new GeneralPath();
		// TODO check is first segment is move to
		for (SegmentEntry entry : entries) {
			System.out.println("process entry : "+entry);
			entry.validEntry(path);
		}
		// setPath(path);
		return path;
	}

	/**
	 * scale the manager between two space and assign delegate super geometry
	 * path for all method that have to use geometry.
	 */
	private void scalePath() {
		geometry = getOrCreateGeometry();
		lengthPathDevice = geometry.lengthOfPath();
		userWidth = max - min;
		unitUserToDevice = new Double(lengthPathDevice) / userWidth;
	}

	/**
	 * add pre initialized metric {@link GlyphMetric} to this general path
	 * 
	 * @param metric
	 */
	public void addMetric(GlyphMetric metric) {
		if (volatileMetrics.contains(metric)) {
			return;
		}
		volatileMetrics.add(metric);
	}

	/**
	 * get the device metrics point for the given metrics value
	 * 
	 * @param metricsValue
	 *            metrics value
	 * @return metrics device point {@link Point2D}
	 * @throws IllegalArgumentException
	 * @throws if
	 *             metrics value is out of dimension minimum and maximum bound
	 *             segment
	 */
	public Point2D getMetricsPoint(double metricsValue) {

		if (metricsValue < getMin() || metricsValue > getMax()) {
			throw new IllegalArgumentException("metrics value out of path range.");
		}

		scalePath();
		if (metricsValue == getMax()) {
			return geometry.pointAtLength(geometry.lengthOfPath());
		}

		double deviceLength = unitUserToDevice * metricsValue;
		Point2D p = geometry.pointAtLength((float) deviceLength);
		return p;
	}

	/**
	 * get radial point on this path with specified parameters
	 * 
	 * @param metricsValue
	 *            the user metrics value
	 * @param radius
	 *            the radius to compute radial point
	 * @param side
	 *            the side for the compute point
	 * @return radial point
	 */
	public Point2D getRadialPoint(double metricsValue, int radius, Side side) {
		System.out.println("get radial point for value : "+metricsValue);
		scalePath();
		double deviceLength = unitUserToDevice * metricsValue;
		Point2D p = geometry.pointAtLength((float) deviceLength);
		float metricAngle = geometry.angleAtLength((float) deviceLength);
		double px;
		double py;
		System.out.println("metrics angle : "+metricAngle);
		if (side == Side.SideRight) {
			px = p.getX() - radius * Math.sin(metricAngle);
			py = p.getY() + radius * Math.cos(metricAngle);
		} else {
			px = p.getX() + radius * Math.sin(metricAngle);
			py = p.getY() - radius * Math.cos(metricAngle);
		}
		return new Point2D.Double(px, py);
	}

	/**
	 * solve specified metrics
	 * 
	 * @param glyphMetrics
	 * @return the compute glyph metrics
	 */
	public GlyphMetric solveMetrics(GlyphMetric glyphMetrics) {
		scalePath();
		glyphMetrics.clearGlyphGeometry();
		if (getFontRenderContext() == null) {
			throw new NullPointerException("FontRenderContext should be supplied");
		}

		if (geometry.lengthOfPath() == 0) {
			return glyphMetrics;
		}

		double userVal = glyphMetrics.getValue();
		double deviceLength = unitUserToDevice * userVal;
		glyphMetrics.setLengthOnPath(deviceLength);
		glyphMetrics.setMetricPointRef(geometry.pointAtLength((float) deviceLength));
		glyphMetrics.setMetricGlyphMarker(new Marker(geometry.pointAtLength((float) deviceLength)));
		glyphMetrics.setMetricAngle(geometry.angleAtLength((float) deviceLength));
		if (isReverseAll()) {
			glyphMetrics.setLockReverse(isReverseAll());
		}

		Font f = glyphMetrics.getFont();

		GlyphVector glyphVector = f.createGlyphVector(getFontRenderContext(), glyphMetrics.getMetricsLabel());

		if (glyphMetrics.getStylePosition() == StylePosition.Tangent) {

			AffineTransform af = new AffineTransform();
			float gvWidth = GlyphUtil.getGlyphWidth(glyphVector);

			float startLength = (float) deviceLength - gvWidth / 2;
			float endLength = (float) deviceLength + gvWidth / 2;

			Point2D pointStart = geometry.pointAtLength(startLength);
			Point2D pointEnd = geometry.pointAtLength(endLength);
			glyphMetrics.setPointStart(pointStart);
			glyphMetrics.setPointEnd(pointEnd);

			if (pointStart == null || pointEnd == null) {
				return glyphMetrics;
			}

			boolean needRevert = glyphMetrics.isLockReverse();
			if (isAutoReverseGlyph()) {
				if (pointStart.getX() > pointEnd.getX()) {
					needRevert = true;
				}
			}

			for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {

				Point2D p = glyphVector.getGlyphPosition(j);
				float px = (float) p.getX();
				float py = (float) p.getY();
				Point2D pointGlyph;

				if (!needRevert) {
					pointGlyph = geometry.pointAtLength(startLength + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
				} else {
					pointGlyph = geometry.pointAtLength(endLength - GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
				}

				if (pointGlyph == null) {
					continue;
				}

				glyphMetrics.addGlyphPoint(pointGlyph);
				af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());

				float angle = 0;

				if (!needRevert) {
					angle = geometry.angleAtLength(startLength + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
				} else {
					angle = geometry.angleAtLength(endLength - GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
				}

				if (!needRevert) {
					af.rotate(angle);
				} else {
					af.rotate(angle + Math.PI);
				}

				af.translate(-px, -py + glyphVector.getVisualBounds().getHeight() / 2 - glyphMetrics.getDivergence());

				Shape glyph = glyphVector.getGlyphOutline(j);
				Shape glyphTransformed = af.createTransformedShape(glyph);

				Point2D srcNorth = new Point2D.Double(glyph.getBounds2D().getCenterX(), glyph.getBounds2D().getY());
				Point2D dstNorth = new Point2D.Double();

				Point2D srcSouth = new Point2D.Double(glyph.getBounds2D().getCenterX(), glyph.getBounds2D().getY() + glyph.getBounds2D().getHeight());
				Point2D dstSouth = new Point2D.Double();

				Point2D srcEast = new Point2D.Double(glyph.getBounds2D().getX() + glyph.getBounds2D().getWidth(), glyph.getBounds2D().getCenterY());
				Point2D dstEast = new Point2D.Double();

				Point2D srcWest = new Point2D.Double(glyph.getBounds2D().getX(), glyph.getBounds2D().getCenterY());
				Point2D dstWest = new Point2D.Double();

				af.transform(srcNorth, dstNorth);
				af.transform(srcSouth, dstSouth);
				af.transform(srcEast, dstEast);
				af.transform(srcWest, dstWest);

				GlyphGeometry metricGlyphGeometry = new GlyphGeometry(glyphTransformed, dstNorth, dstSouth, dstWest, dstEast);

				glyphMetrics.addMetricsGlyphGeometry(metricGlyphGeometry);

			}
		}
		if (glyphMetrics.getStylePosition() == StylePosition.Radial) {

			float gvWidth = GlyphUtil.getGlyphWidth(glyphVector);
			Point2D pStart = glyphMetrics.getRadialPoint(glyphMetrics.getDivergence());
			Point2D pEnd = glyphMetrics.getRadialPoint((int) (glyphMetrics.getDivergence() + gvWidth + 10));

			if (pStart == null || pEnd == null) {
				return glyphMetrics;
			}

			Line2D radialFragment;
			if (pStart.getX() > pEnd.getX()) {
				radialFragment = new Line2D.Double(pEnd.getX(), pEnd.getY(), pStart.getX(), pStart.getY());
			} else {
				radialFragment = new Line2D.Double(pStart.getX(), pStart.getY(), pEnd.getX(), pEnd.getY());
			}

			AffineTransform af = new AffineTransform();
			GeometryPath geometryRadialpath = new GeometryPath(radialFragment);

			for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {

				Point2D p = glyphVector.getGlyphPosition(j);
				float px = (float) p.getX();
				float py = (float) p.getY();

				Point2D pointGlyph = geometryRadialpath.pointAtLength(GlyphUtil.getGlyphWidthAtToken(glyphVector, j));

				if (pointGlyph == null) {
					continue;
				}

				glyphMetrics.addGlyphPoint(pointGlyph);
				Shape glyph = glyphVector.getGlyphOutline(j);

				float angle = geometryRadialpath.angleAtLength(GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
				af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
				af.rotate(angle);
				af.translate(-px, -py + glyphVector.getVisualBounds().getHeight() / 2);

				Shape glyphTransformed = af.createTransformedShape(glyph);

				Point2D srcNorth = new Point2D.Double(glyph.getBounds2D().getCenterX(), glyph.getBounds2D().getY());
				Point2D dstNorth = new Point2D.Double();

				Point2D srcSouth = new Point2D.Double(glyph.getBounds2D().getCenterX(), glyph.getBounds2D().getY() + glyph.getBounds2D().getHeight());
				Point2D dstSouth = new Point2D.Double();

				Point2D srcEast = new Point2D.Double(glyph.getBounds2D().getX() + glyph.getBounds2D().getWidth(), glyph.getBounds2D().getCenterY());
				Point2D dstEast = new Point2D.Double();

				Point2D srcWest = new Point2D.Double(glyph.getBounds2D().getX(), glyph.getBounds2D().getCenterY());
				Point2D dstWest = new Point2D.Double();

				af.transform(srcNorth, dstNorth);
				af.transform(srcSouth, dstSouth);
				af.transform(srcEast, dstEast);
				af.transform(srcWest, dstWest);

				GlyphGeometry metricGlyphGeometry = new GlyphGeometry(glyphTransformed, dstNorth, dstSouth, dstWest, dstEast);

				glyphMetrics.addMetricsGlyphGeometry(metricGlyphGeometry);

			}

		}

		if (glyphMetrics.getStylePosition() == StylePosition.Default) {

			float gvWidth = GlyphUtil.getGlyphWidth(glyphVector);
			Point2D pRadial = glyphMetrics.getRadialPoint(-glyphMetrics.getDivergence());

			Point2D pStart = new Point2D.Double(pRadial.getX() - gvWidth / 2, pRadial.getY());
			Point2D pEnd = new Point2D.Double(pRadial.getX() + gvWidth / 2, pRadial.getY());

			Line2D l = new Line2D.Double(pStart.getX(), pStart.getY(), pEnd.getX(), pEnd.getY());

			AffineTransform af = new AffineTransform();
			GeometryPath geometryRadialpath = new GeometryPath(l);

			for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {

				Point2D p = glyphVector.getGlyphPosition(j);
				float px = (float) p.getX();
				float py = (float) p.getY();

				Point2D pointGlyph = geometryRadialpath.pointAtLength(GlyphUtil.getGlyphWidthAtToken(glyphVector, j));

				if (pointGlyph == null) {
					continue;
				}

				Shape glyph = glyphVector.getGlyphOutline(j);

				float angle = geometryRadialpath.angleAtLength(GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
				af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
				af.rotate(angle);
				af.translate(-px, -py + glyphVector.getVisualBounds().getHeight() / 2);

				Shape glyphTransformed = af.createTransformedShape(glyph);

				Point2D srcNorth = new Point2D.Double(glyph.getBounds2D().getCenterX(), glyph.getBounds2D().getY());
				Point2D dstNorth = new Point2D.Double();

				Point2D srcSouth = new Point2D.Double(glyph.getBounds2D().getCenterX(), glyph.getBounds2D().getY() + glyph.getBounds2D().getHeight());
				Point2D dstSouth = new Point2D.Double();

				Point2D srcEast = new Point2D.Double(glyph.getBounds2D().getX() + glyph.getBounds2D().getWidth(), glyph.getBounds2D().getCenterY());
				Point2D dstEast = new Point2D.Double();

				Point2D srcWest = new Point2D.Double(glyph.getBounds2D().getX(), glyph.getBounds2D().getCenterY());
				Point2D dstWest = new Point2D.Double();

				af.transform(srcNorth, dstNorth);
				af.transform(srcSouth, dstSouth);
				af.transform(srcEast, dstEast);
				af.transform(srcWest, dstWest);

				GlyphGeometry metricGlyphGeometry = new GlyphGeometry(glyphTransformed, dstNorth, dstSouth, dstWest, dstEast);

				glyphMetrics.addMetricsGlyphGeometry(metricGlyphGeometry);

			}
		}

		return glyphMetrics;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.glyphmetrics.AbstractMetricsPath#getMetrics()
	 */
	@Override
	public List<GlyphMetric> getMetrics() {

		pmetrics.clear();

		scalePath();

		if (getFontRenderContext() == null) {
			throw new NullPointerException("FontRenderContext should be supplied");
		}

		if (geometry.lengthOfPath() == 0) {
			return pmetrics;
		}

		for (GlyphMetric vm : volatileMetrics) {
			if (vm.getValue() < getMin() || vm.getValue() > getMax()) {
				throw new IllegalArgumentException("metrics value out of path range :"+vm.getValue());
			}

			GlyphMetric m = new GlyphMetric();

			pmetrics.add(m);
			m.setValue(vm.getValue());

			double userVal = vm.getValue();
			double deviceLength = unitUserToDevice * userVal;
			m.setLengthOnPath(deviceLength);
			m.setMetricPointRef(geometry.pointAtLength((float) deviceLength));
			m.setMetricGlyphMarker(new Marker(geometry.pointAtLength((float) deviceLength)));
			m.setMetricAngle(geometry.angleAtLength((float) deviceLength));
			m.setStylePosition(vm.getStylePosition());
			m.setFont(vm.getFont());
			m.setDivergence(vm.getDivergence());
			m.setMetricsLabel(vm.getMetricsLabel());
			m.setMetricsNature(vm.getMetricsNature());
			m.setGlyphMetricDraw(vm.getGlyphMetricDraw());
			m.setGlyphMetricFill(vm.getGlyphMetricFill());
			m.setGlyphMetricEffect(vm.getGlyphMetricEffect());
			m.setGlyphMetricMarkerPainter(vm.getGlyphMetricMarkerPainter());
			m.setLockReverse(vm.isLockReverse());
			if (isReverseAll()) {
				m.setLockReverse(isReverseAll());
			}

			Font f = m.getFont();

			GlyphVector glyphVector = f.createGlyphVector(getFontRenderContext(), m.getMetricsLabel());

			if (m.getStylePosition() == StylePosition.Tangent) {

				AffineTransform af = new AffineTransform();
				float gvWidth = GlyphUtil.getGlyphWidth(glyphVector);

				float startLength = (float) deviceLength - gvWidth / 2;
				float endLength = (float) deviceLength + gvWidth / 2;

				Point2D pointStart = geometry.pointAtLength(startLength);
				Point2D pointEnd = geometry.pointAtLength(endLength);
				m.setPointStart(pointStart);
				m.setPointEnd(pointEnd);

				if (pointStart == null || pointEnd == null) {
					continue;
				}

				boolean needRevert = m.isLockReverse();
				if (isAutoReverseGlyph()) {
					if (pointStart.getX() > pointEnd.getX()) {
						needRevert = true;
					}
				}

				for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {

					Point2D p = glyphVector.getGlyphPosition(j);
					float px = (float) p.getX();
					float py = (float) p.getY();
					Point2D pointGlyph;

					if (!needRevert) {
						pointGlyph = geometry.pointAtLength(startLength + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
					} else {
						pointGlyph = geometry.pointAtLength(endLength - GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
					}

					if (pointGlyph == null) {
						continue;
					}

					m.addGlyphPoint(pointGlyph);

					af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());

					float angle = 0;

					if (!needRevert) {
						angle = geometry.angleAtLength(startLength + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
					} else {
						angle = geometry.angleAtLength(endLength - GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
					}

					if (!needRevert) {
						af.rotate(angle);
					} else {
						af.rotate(angle + Math.PI);
					}

					af.translate(-px, -py + glyphVector.getVisualBounds().getHeight() / 2 - m.getDivergence());

					Shape glyph = glyphVector.getGlyphOutline(j);
					Shape glyphTransformed = af.createTransformedShape(glyph);

					Point2D srcNorth = new Point2D.Double(glyph.getBounds2D().getCenterX(), glyph.getBounds2D().getY());
					Point2D dstNorth = new Point2D.Double();

					Point2D srcSouth = new Point2D.Double(glyph.getBounds2D().getCenterX(), glyph.getBounds2D().getY() + glyph.getBounds2D().getHeight());
					Point2D dstSouth = new Point2D.Double();

					Point2D srcEast = new Point2D.Double(glyph.getBounds2D().getX() + glyph.getBounds2D().getWidth(), glyph.getBounds2D().getCenterY());
					Point2D dstEast = new Point2D.Double();

					Point2D srcWest = new Point2D.Double(glyph.getBounds2D().getX(), glyph.getBounds2D().getCenterY());
					Point2D dstWest = new Point2D.Double();

					af.transform(srcNorth, dstNorth);
					af.transform(srcSouth, dstSouth);
					af.transform(srcEast, dstEast);
					af.transform(srcWest, dstWest);

					GlyphGeometry metricGlyphGeometry = new GlyphGeometry(glyphTransformed, dstNorth, dstSouth, dstWest, dstEast);

					m.addMetricsGlyphGeometry(metricGlyphGeometry);

				}
			}
			if (m.getStylePosition() == StylePosition.Radial) {

				float gvWidth = GlyphUtil.getGlyphWidth(glyphVector);
				Point2D pStart = m.getRadialPoint(m.getDivergence());
				Point2D pEnd = m.getRadialPoint((int) (m.getDivergence() + gvWidth + 10));

				if (pStart == null || pEnd == null) {
					continue;
				}

				Line2D radialFragment;
				if (pStart.getX() > pEnd.getX()) {
					radialFragment = new Line2D.Double(pEnd.getX(), pEnd.getY(), pStart.getX(), pStart.getY());
				} else {
					radialFragment = new Line2D.Double(pStart.getX(), pStart.getY(), pEnd.getX(), pEnd.getY());
				}

				AffineTransform af = new AffineTransform();
				GeometryPath geometryRadialpath = new GeometryPath(radialFragment);

				for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {

					Point2D p = glyphVector.getGlyphPosition(j);
					float px = (float) p.getX();
					float py = (float) p.getY();

					Point2D pointGlyph = geometryRadialpath.pointAtLength(GlyphUtil.getGlyphWidthAtToken(glyphVector, j));

					if (pointGlyph == null) {
						continue;
					}

					m.addGlyphPoint(pointGlyph);
					Shape glyph = glyphVector.getGlyphOutline(j);

					float angle = geometryRadialpath.angleAtLength(GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
					af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
					af.rotate(angle);
					af.translate(-px, -py + glyphVector.getVisualBounds().getHeight() / 2);

					Shape glyphTransformed = af.createTransformedShape(glyph);

					// new with glyphgeometry
					Point2D srcNorth = new Point2D.Double(glyph.getBounds2D().getCenterX(), glyph.getBounds2D().getY());
					Point2D dstNorth = new Point2D.Double();

					Point2D srcSouth = new Point2D.Double(glyph.getBounds2D().getCenterX(), glyph.getBounds2D().getY() + glyph.getBounds2D().getHeight());
					Point2D dstSouth = new Point2D.Double();

					Point2D srcEast = new Point2D.Double(glyph.getBounds2D().getX() + glyph.getBounds2D().getWidth(), glyph.getBounds2D().getCenterY());
					Point2D dstEast = new Point2D.Double();

					Point2D srcWest = new Point2D.Double(glyph.getBounds2D().getX(), glyph.getBounds2D().getCenterY());
					Point2D dstWest = new Point2D.Double();

					af.transform(srcNorth, dstNorth);
					af.transform(srcSouth, dstSouth);
					af.transform(srcEast, dstEast);
					af.transform(srcWest, dstWest);

					GlyphGeometry metricGlyphGeometry = new GlyphGeometry(glyphTransformed, dstNorth, dstSouth, dstWest, dstEast);

					m.addMetricsGlyphGeometry(metricGlyphGeometry);

					// m.addGlyphShape(glyphTransformed);

				}

			}

			if (m.getStylePosition() == StylePosition.Default) {

				float gvWidth = GlyphUtil.getGlyphWidth(glyphVector);
				Point2D pRadial = m.getRadialPoint(-m.getDivergence());

				Point2D pStart = new Point2D.Double(pRadial.getX() - gvWidth / 2, pRadial.getY());
				Point2D pEnd = new Point2D.Double(pRadial.getX() + gvWidth / 2, pRadial.getY());

				Line2D l = new Line2D.Double(pStart.getX(), pStart.getY(), pEnd.getX(), pEnd.getY());

				AffineTransform af = new AffineTransform();
				GeometryPath geometryRadialpath = new GeometryPath(l);

				for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {

					Point2D p = glyphVector.getGlyphPosition(j);
					float px = (float) p.getX();
					float py = (float) p.getY();

					Point2D pointGlyph = geometryRadialpath.pointAtLength(GlyphUtil.getGlyphWidthAtToken(glyphVector, j));

					if (pointGlyph == null) {
						continue;
					}

					Shape glyph = glyphVector.getGlyphOutline(j);

					float angle = geometryRadialpath.angleAtLength(GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
					af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
					af.rotate(angle);
					af.translate(-px, -py + glyphVector.getVisualBounds().getHeight() / 2);

					Shape glyphTransformed = af.createTransformedShape(glyph);
					// Shape glyphTransformed =
					// af.createTransformedShape(glyphBound2D);

					Point2D srcNorth = new Point2D.Double(glyph.getBounds2D().getCenterX(), glyph.getBounds2D().getY());
					Point2D dstNorth = new Point2D.Double();

					Point2D srcSouth = new Point2D.Double(glyph.getBounds2D().getCenterX(), glyph.getBounds2D().getY() + glyph.getBounds2D().getHeight());
					Point2D dstSouth = new Point2D.Double();

					Point2D srcEast = new Point2D.Double(glyph.getBounds2D().getX() + glyph.getBounds2D().getWidth(), glyph.getBounds2D().getCenterY());
					Point2D dstEast = new Point2D.Double();

					Point2D srcWest = new Point2D.Double(glyph.getBounds2D().getX(), glyph.getBounds2D().getCenterY());
					Point2D dstWest = new Point2D.Double();

					af.transform(srcNorth, dstNorth);
					af.transform(srcSouth, dstSouth);
					af.transform(srcEast, dstEast);
					af.transform(srcWest, dstWest);

					GlyphGeometry metricGlyphGeometry = new GlyphGeometry(glyphTransformed, dstNorth, dstSouth, dstWest, dstEast);

					m.addMetricsGlyphGeometry(metricGlyphGeometry);

				}
			}

		}

		return pmetrics;
	}

}
