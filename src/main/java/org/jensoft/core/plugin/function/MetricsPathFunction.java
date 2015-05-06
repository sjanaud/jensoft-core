/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function;

import java.awt.Font;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.glyphmetrics.AbstractMetricsPath;
import org.jensoft.core.glyphmetrics.GeometryPath;
import org.jensoft.core.glyphmetrics.GlyphGeometry;
import org.jensoft.core.glyphmetrics.GlyphMetric;
import org.jensoft.core.glyphmetrics.GlyphUtil;
import org.jensoft.core.glyphmetrics.Marker;
import org.jensoft.core.glyphmetrics.Side;
import org.jensoft.core.glyphmetrics.StylePosition;
import org.jensoft.core.plugin.function.source.FunctionNature;
import org.jensoft.core.plugin.function.source.SourceFunction;

/**
 * <code>MetricsPathFunction</code>
 * 
 * @author Sebastien Janaud
 */
public class MetricsPathFunction extends AbstractMetricsPath {

	/** glyphs metrics */
	private List<GlyphMetric> glyphMetrics;

	/** the source function for this metrics path function */
	private SourceFunction sourceFunction;

	/** the default decimal format to use for registered metrics */
	private DecimalFormat decimalFormat = new DecimalFormat("##.00");

	/** initialized flag */
	boolean initialized = false;

	/** function segments */
	private List<PathSegment> pathSegments;

	/** volatile metrics registered for this path */
	private List<GlyphMetric> volatileMetrics = new ArrayList<GlyphMetric>();

	/**
	 * create empty CurveMetricsPath
	 **/
	public MetricsPathFunction() {
		setProjectionNature(ProjectionNature.USER);
		glyphMetrics = new ArrayList<GlyphMetric>();
		pathSegments = new ArrayList<MetricsPathFunction.PathSegment>();
	}

	/**
	 * Creates a new <code>MetricsPathFunction</code>
	 * 
	 * @param source
	 *            the {@link SourceFunction} of this path function
	 **/

	public MetricsPathFunction(SourceFunction source) {
		this.sourceFunction = source;
		setProjectionNature(ProjectionNature.USER);
		glyphMetrics = new ArrayList<GlyphMetric>();
		pathSegments = new ArrayList<MetricsPathFunction.PathSegment>();
	}

	/**
	 * get {@link SourceFunction} for this path function
	 * 
	 * @return source function
	 */
	public SourceFunction getSource() {
		return sourceFunction;
	}

	/**
	 * set {@link SourceFunction} for this metrics path function
	 * <p>
	 * set the source source for this metrics path function and make a request
	 * to solve the geometry on the next demand.
	 * 
	 * @param source
	 *            the source to set in this path function
	 */
	public void setSource(SourceFunction source) {
		this.sourceFunction = source;
	}

	/**
	 * return true if the shape is empty
	 * 
	 * @param s
	 *            the shape
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
		return true;
	}

	/**
	 * create a path segment
	 * 
	 * @author Sebastien Janaud
	 */
	class PathSegment {

		/** segment start in user projection */
		private Point2D segmentUserStart;

		/** segment end in user projection */
		private Point2D segmentUserEnd;

		/** segment start in device projection */
		private Point2D segmentDeviceStart;

		/** segment end in device projection */
		private Point2D segmentDeviceEnd;

		/**
		 * create a new segment with specified projections coordinates
		 * 
		 * @param segmentUserStart
		 *            the start segment in user projection
		 * @param segmentUserEnd
		 *            the end segment in user projection
		 * @param segmentDeviceStart
		 *            the start segment in device projection
		 * @param segmentDeviceEnd
		 *            the end segment in device projection
		 */
		public PathSegment(Point2D segmentUserStart, Point2D segmentUserEnd, Point2D segmentDeviceStart, Point2D segmentDeviceEnd) {
			super();
			this.segmentUserStart = segmentUserStart;
			this.segmentUserEnd = segmentUserEnd;
			this.segmentDeviceStart = segmentDeviceStart;
			this.segmentDeviceEnd = segmentDeviceEnd;
		}

		/**
		 * get segment start in device projection
		 * 
		 * @return segment start in device projection
		 */
		public Point2D getSegmentDeviceStart() {
			return segmentDeviceStart;
		}

		/**
		 * set segment start in device projection
		 * 
		 * @param segmentStart
		 *            the segment start to set
		 */
		public void setSegmentDeviceStart(Point2D segmentStart) {
			segmentDeviceStart = segmentStart;
		}

		/**
		 * get segment end in device projection
		 * 
		 * @return segment end in device projection
		 */
		public Point2D getSegmentDeviceEnd() {
			return segmentDeviceEnd;
		}

		/**
		 * set segment end in device projection
		 * 
		 * @param segmentEnd
		 *            the segment end to set
		 */
		public void setSegmentDeviceEnd(Point2D segmentEnd) {
			segmentDeviceEnd = segmentEnd;
		}

		/**
		 * return true if segment range contains specified user value, false
		 * otherwise
		 * 
		 * @param value
		 *            the user value coordinate to test
		 * @return true if segment range contains specified user  value,
		 *         false otherwise
		 */
		public boolean match(double value) {
			if(sourceFunction.getNature() == FunctionNature.XFunction){
				return value >= segmentUserStart.getX() && value <= segmentUserEnd.getX();
			}else{
				return value >= segmentUserStart.getY() && value <= segmentUserEnd.getY();
			}
			
		}

		/**
		 * get segment point for the specified value in user projection
		 * 
		 * @param value
		 *            the user value in user projection to evaluate
		 * @return evaluate point for specified x in user projection
		 */
		public Point2D getUserPoint(double value) {
			if(sourceFunction.getNature() == FunctionNature.XFunction){
				double userY = getCoefficient() * value + getConstant();
				return new Point2D.Double(value, userY);	
			}else{
				double userX = getCoefficient() * value + getConstant();
				return new Point2D.Double(userX, value);
			}
			
		}

		/**
		 * get start point of this segment in user projection
		 * 
		 * @return start point of this segment in user projection
		 */
		public Point2D getSegmentUserStart() {
			return segmentUserStart;
		}

		/**
		 * set start point of this segment in user projection
		 * 
		 * @param segmentUserStart
		 *            the segment start in user projection
		 */
		public void setSegmentUserStart(Point2D segmentUserStart) {
			this.segmentUserStart = segmentUserStart;
		}

		/**
		 * get end point of this segment in user projection
		 * 
		 * @return end point of this segment in user projection
		 */
		public Point2D getSegmentUserEnd() {
			return segmentUserEnd;
		}

		/**
		 * set end point of this segment in user projection
		 * 
		 * @param segmentUserEnd
		 *            the segment end in user projection
		 */
		public void setSegmentUserEnd(Point2D segmentUserEnd) {
			this.segmentUserEnd = segmentUserEnd;
		}

		/**
		 * get the length of this segment in device projection
		 * 
		 * @return length of this segment in device projection
		 */
		public double deviceLength() {
			return Point2D.distance(segmentDeviceStart.getX(), segmentDeviceStart.getY(), segmentDeviceEnd.getX(), segmentDeviceEnd.getY());
		}

		/**
		 * <p>
		 * get A slope coefficient of this segment, depends on function nature
		 * </p>
		 * <p>
		 * y = Ax + B (x function) or x = Ay + B (y function)
		 * </p>
		 * 
		 * @return coefficient of this segment
		 */
		public double getCoefficient() {
			if(sourceFunction.getNature() == FunctionNature.XFunction){
				return (segmentUserEnd.getY() - segmentUserStart.getY()) / (segmentUserEnd.getX() - segmentUserStart.getX());
			}else{
				return (segmentUserEnd.getX() - segmentUserStart.getX()) / (segmentUserEnd.getY() - segmentUserStart.getY());
			}
			
		}

		/**
		 * <p>
		 * get B constant, the y-intercept of this segment
		 * </p>
		 * <p>
		 * y = Ax + B
		 * </p>
		 * 
		 * @return the y-intercept of this segment
		 */
		public double getConstant() {
			if(sourceFunction.getNature() == FunctionNature.XFunction){
				return segmentUserStart.getY() - getCoefficient() * segmentUserStart.getX();	
			}else{
				return segmentUserStart.getX() - getCoefficient() * segmentUserStart.getY();
			}
		}

		@Override
		public String toString() {
			return "PathSegment [segmentUserStart=" + segmentUserStart + ", segmentUserEnd=" + segmentUserEnd + "]";
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			PathSegment other = (PathSegment) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (segmentUserEnd == null) {
				if (other.segmentUserEnd != null) {
					return false;
				}
			} else if (!segmentUserEnd.equals(other.segmentUserEnd)) {
				return false;
			}
			if (segmentUserStart == null) {
				if (other.segmentUserStart != null) {
					return false;
				}
			} else if (!segmentUserStart.equals(other.segmentUserStart)) {
				return false;
			}
			return true;
		}

		private MetricsPathFunction getOuterType() {
			return MetricsPathFunction.this;
		}

	}

	/**
	 * get the curve segment that contains the specified x in user projection
	 * system
	 * 
	 * @param userX
	 *            the x value in user projection
	 * @return the curve segment that contains the specified x in user
	 *         projection system
	 */
	public PathSegment getPathSegment(double userX) {
		for (PathSegment cs : pathSegments) {
			if (cs.match(userX)) {
				return cs;
			}
		}
		return null;
	}

	/**
	 * get the length in device coordinate at the specified segment
	 * 
	 * @param curveSegment
	 *            the curve segment
	 * @return the length in device coordinate at the specified segment
	 */
	public double getLengthAtSegment(PathSegment curveSegment) {
		double length = 0;
		for (PathSegment cs : pathSegments) {

			length = length + cs.deviceLength();

			if (cs.equals(curveSegment)) {
				return length;
			}
		}
		return 0;
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.glyphmetrics.AbstractMetricsPath#createPathMetrics()
	 */
	@Override
	protected GeneralPath createPathMetrics() {
		if (getProjection() == null) {
			throw new NullPointerException("Projection should have been to set before invoke solving geometry");
		}
		GeneralPath path = new GeneralPath();
		if (sourceFunction == null) {
			return path;
		}
		List<Point2D> entries;
		if (sourceFunction.getNature() == FunctionNature.XFunction) {
			entries = sourceFunction.solveFunction(getProjection().getMinX(), getProjection().getMaxX());
		} else {
			entries = sourceFunction.solveFunction(getProjection().getMinY(), getProjection().getMaxY());
		}

		List<Point2D> src = new ArrayList<Point2D>();
		// try {
		// Point2D evaluateMinX = source.evaluate(getProjection().getMinX());
		// if (evaluateMinX != null) {
		// src.add(evaluateMinX);
		// }
		// }
		// catch (Exception e) {
		// // try to get the point before max otherwise
		// //Point2D next = source.previous(getProjection().getMaxX());
		// //if(next != null)
		// // src.add(next);
		// }

		src.addAll(entries);

		// try {
		// //try to be exact evaluate y at the max x
		// Point2D evaluateMaxX = source.evaluate(getWindow2d().getMaxX());
		// if (evaluateMaxX != null) {
		// src.add(evaluateMaxX);
		// }
		//
		// }
		// catch (Exception e) {
		// // try to get the point after max otherwise
		// //Point2D next = source.next(getWindow2d().getMaxX());
		// //if(next != null)
		// // src.add(next);
		// }

		// create segments
		pathSegments.clear();

		for (int i = 0; i < src.size(); i++) {
			Point2D sourceFunctionEntry = src.get(i);
			Point2D segmentDeviceEnd = getProjection().userToPixel(sourceFunctionEntry);

			if (i == 0) {
				path.moveTo(segmentDeviceEnd.getX(), segmentDeviceEnd.getY());
			} else {
				Point2D segmentUserStart = src.get(i - 1);
				Point2D segmentDeviceStart = getProjection().userToPixel(segmentUserStart);

				PathSegment cs = new PathSegment(segmentUserStart, sourceFunctionEntry, segmentDeviceStart, segmentDeviceEnd);
				pathSegments.add(cs);

				path.lineTo(segmentDeviceEnd.getX(), segmentDeviceEnd.getY());
			}

		}

		return path;
	}

	/**
	 * add a glyph metric to this curve metrics path path
	 * 
	 * @param metric
	 *            the glyph metrics to add
	 */
	public void addMetrics(GlyphMetric metric) {
		volatileMetrics.add(metric);
	}

	/**
	 * remove a glyph metric to this curve metrics path path
	 * 
	 * @param metric
	 *            the glyph metrics to remove
	 */
	public void removeMetrics(GlyphMetric metric) {
		volatileMetrics.remove(metric);
	}

	/**
	 * clear all registered metrics on this curve metrics
	 */
	public void clearMetrics() {
		volatileMetrics.clear();
	}

	/**
	 * get radial point for the specified user value at the specified side of
	 * this metrics path
	 * 
	 * @param userValue
	 *            the user value
	 * @param radius
	 *            the radius divergence for the point
	 * @param side
	 *            teh path side
	 * @return radial point for the specified user value at the specified side
	 *         of this metrics path
	 */
	public Point2D getRadialPoint(double userValue, int radius, Side side) {

		getOrCreateGeometry();

		PathSegment cs = getPathSegment(userValue);
		Point2D userPoint = cs.getUserPoint(userValue);
		Point2D devicePoint = getProjection().userToPixel(userPoint);

		double delta = Point2D.distance(devicePoint.getX(), devicePoint.getY(), cs.getSegmentDeviceEnd().getX(), cs.getSegmentDeviceEnd().getY());

		double deviceLength = getLengthAtSegment(cs) - delta;

		Point2D p = getOrCreateGeometry().pointAtLength((float) deviceLength);
		float metricAngle = getOrCreateGeometry().angleAtLength((float) deviceLength);
		double px;
		double py;
		if (side == Side.SideRight) {
			px = p.getX() - radius * Math.sin(metricAngle);
			py = p.getY() + radius * Math.cos(metricAngle);
		} else {

			px = p.getX() + radius * Math.sin(metricAngle);
			py = p.getY() - radius * Math.cos(metricAngle);
		}
		return new Point2D.Double(px, py);
	}

	/***
	 * get the device metrics for the path manager
	 */
	@Override
	public List<GlyphMetric> getMetrics() {

		glyphMetrics.clear();

		if (sourceFunction == null) {
			return glyphMetrics;
		}

		// geometry = getGeometry();

		if (getFontRenderContext() == null) {
			throw new NullPointerException("FontRenderContext should be supplied");
		}

		if (getOrCreateGeometry().lengthOfPath() == 0) {
			return glyphMetrics;
		}

		for (GlyphMetric vm : volatileMetrics) {

			GlyphMetric m = new GlyphMetric();

			glyphMetrics.add(m);
			m.setValue(vm.getValue());

			PathSegment cs = getPathSegment(vm.getValue());
			if (cs == null) {
				continue;
			}

			Point2D userPoint = cs.getUserPoint(vm.getValue());
			Point2D devicePoint = getProjection().userToPixel(userPoint);

			double delta = Point2D.distance(devicePoint.getX(), devicePoint.getY(), cs.getSegmentDeviceEnd().getX(), cs.getSegmentDeviceEnd().getY());
			double deviceLength = getLengthAtSegment(cs) - delta;

			m.setLengthOnPath(deviceLength);
			m.setMetricPointRef(getOrCreateGeometry().pointAtLength((float) deviceLength));
			m.setMetricGlyphMarker(new Marker(getOrCreateGeometry().pointAtLength((float) deviceLength)));
			m.setMetricAngle(getOrCreateGeometry().angleAtLength((float) deviceLength));
			m.setStylePosition(vm.getStylePosition());
			m.setFont(vm.getFont());

			m.setDivergence(vm.getDivergence());

			if (vm.getMetricsLabel() != null) {
				m.setMetricsLabel(vm.getMetricsLabel());
			} else {
				if (m.getFormat() != null) {
					m.setMetricsLabel(m.getFormat().format(userPoint.getY()));
				} else {
					try {
						m.setMetricsLabel(decimalFormat.format(userPoint.getY()));
					} catch (Exception e) {
						m.setMetricsLabel("");
					}
				}
			}
			m.setMetricsNature(vm.getMetricsNature());
			m.setGlyphMetricDraw(vm.getGlyphMetricDraw());
			m.setGlyphMetricFill(vm.getGlyphMetricFill());
			m.setGlyphMetricEffect(vm.getGlyphMetricEffect());
			m.setGlyphMetricMarkerPainter(vm.getGlyphMetricMarkerPainter());
			m.setLockReverse(vm.isLockReverse());

			Font f = m.getFont();

			GlyphVector glyphVector = f.createGlyphVector(getFontRenderContext(), m.getMetricsLabel());

			if (m.getStylePosition() == StylePosition.Tangent) {

				AffineTransform af = new AffineTransform();
				float gvWidth = GlyphUtil.getGlyphWidth(glyphVector);

				float startLength = (float) deviceLength - gvWidth / 2;
				float endLength = (float) deviceLength + gvWidth / 2;

				Point2D pointStart = getOrCreateGeometry().pointAtLength(startLength);
				Point2D pointEnd = getOrCreateGeometry().pointAtLength(endLength);
				m.setPointStart(pointStart);
				m.setPointEnd(pointEnd);

				// Point2D pointZero = geometry.pointAtLength(0);
				// System.out.println("point test :"+pointZero);
				// System.out.println("point start :"+pointStart);
				// System.out.println("point end :"+pointEnd);

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
					Point2D glyphPoint;

					if (!needRevert) {
						glyphPoint = getOrCreateGeometry().pointAtLength(startLength + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
					} else {
						glyphPoint = getOrCreateGeometry().pointAtLength(endLength - GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
					}

					if (glyphPoint == null) {
						continue;
					}

					m.addGlyphPoint(glyphPoint);

					af.setToTranslation(glyphPoint.getX(), glyphPoint.getY());

					float angle = 0;
					if (!needRevert) {
						angle = getOrCreateGeometry().angleAtLength(startLength + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
					} else {
						angle = getOrCreateGeometry().angleAtLength(endLength - GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
					}

					if (!needRevert) {
						af.rotate(angle);
					} else {
						af.rotate(angle + Math.PI);
					}

					af.translate(-px, -py + glyphVector.getVisualBounds().getHeight() / 2 - m.getDivergence());

					Shape glyph = glyphVector.getGlyphOutline(j);
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

					Point2D glyphPoint = geometryRadialpath.pointAtLength(GlyphUtil.getGlyphWidthAtToken(glyphVector, j));

					if (glyphPoint == null) {
						continue;
					}

					m.addGlyphPoint(glyphPoint);

					Shape glyph = glyphVector.getGlyphOutline(j);

					float angle = geometryRadialpath.angleAtLength(GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
					af.setToTranslation(glyphPoint.getX(), glyphPoint.getY());
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

				}

			}

			if (m.getStylePosition() == StylePosition.Default) {
				float gvWidth = GlyphUtil.getGlyphWidth(glyphVector);
				Point2D pRadial = m.getRadialPoint(m.getDivergence());
				if (pRadial != null) {
					// System.out.println("Pradial = "+pRadial);
					Point2D pStart = new Point2D.Double(pRadial.getX() - gvWidth / 2, pRadial.getY());
					Point2D pEnd = new Point2D.Double(pRadial.getX() + gvWidth / 2, pRadial.getY());

					Line2D l = new Line2D.Double(pStart.getX(), pStart.getY(), pEnd.getX(), pEnd.getY());

					AffineTransform af = new AffineTransform();
					GeometryPath geometryRadialpath = new GeometryPath(l);

					for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {

						Point2D p = glyphVector.getGlyphPosition(j);
						float px = (float) p.getX();
						float py = (float) p.getY();

						Point2D glyphPoint = geometryRadialpath.pointAtLength(GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
						// System.out.println("point glyph :"+pointGlyph);
						if (glyphPoint == null) {
							continue;
						}

						m.addGlyphPoint(glyphPoint);

						Shape glyph = glyphVector.getGlyphOutline(j);

						float angle = geometryRadialpath.angleAtLength(GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
						af.setToTranslation(glyphPoint.getX(), glyphPoint.getY());
						af.rotate(angle);
						af.translate(-px, -py);

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

					}
				}
			}

		}

		return glyphMetrics;
	}

}
