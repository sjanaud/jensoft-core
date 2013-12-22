/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics;

import java.awt.Font;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.glyphmetrics.painter.GlyphMetricDraw;
import com.jensoft.core.glyphmetrics.painter.GlyphMetricEffect;
import com.jensoft.core.glyphmetrics.painter.GlyphMetricFill;
import com.jensoft.core.glyphmetrics.painter.GlyphMetricMarkerPainter;
import com.jensoft.core.glyphmetrics.painter.MetricsPathPainter;
import com.jensoft.core.plugin.function.MetricsPathFunction;
import com.jensoft.core.plugin.metrics.format.IMetricsFormat;

/**
 * <code>GlyphMetric</code> represent a glyph associate to metric along the
 * path.
 * <p>
 * <center><img src="doc-files/white-tachometer.png"></center><br>
 * <center><img src="doc-files/white-compass.png"></center><br>
 * <center><img src="doc-files/general-metrics1.png"></center><br>
 * </p>
 * 
 * @see AbstractMetricsPath
 * @see GeneralMetricsPath
 * @see GlyphMetricsNature
 * @see GlyphMetricFill
 * @see MetricsPathPainter
 * @author Sebastien Janaud
 */
public class GlyphMetric {

	/** the device length value */
	private double lengthOnPath;

	/** the user metric value along the path */
	private double value;

	/** the glyph metric font */
	private Font font = new Font("Dialog", Font.PLAIN, 12);

	/** metric style position */
	private StylePosition stylePosition = StylePosition.Default;

	/*** metric nature */
	private GlyphMetricsNature metricNature = GlyphMetricsNature.Median;

	/** metric radial delta */
	private int divergence = 10;

	/** metric marker point reference */
	private Point2D metricPointRef;

	/** angle path */
	private float metricAngle;

	/** glyph start location */
	private Point2D pointStart;

	/** glyph end location */
	private Point2D pointEnd;

	/** metrics glyphs geometries */
	private List<GlyphGeometry> metricsGlyphGeometry;

	/** glyphs locations */
	private List<Point2D> glyphsPoints;

	/** the glyph metric draw */
	private GlyphMetricDraw glyphMetricDraw;

	/** the glyph metric fill */
	private GlyphMetricFill glyphMetricFill;

	/** the glyph metric effect */
	private GlyphMetricEffect glyphMetricEffect;

	/** the glyph metric marker */
	private GlyphMetricMarkerPainter glyphMetricMarkerPainter;

	// classic metric properties

	/** classic metrics marker color */
	// private Color metricsMarkerColor;

	/** metric label color */
	// private Color metricsLabelColor;

	/** metric format */
	private IMetricsFormat format;

	/** metric label */
	private String metricsLabel;

	/** glyph marker */
	private Marker metricGlyphMarker;

	/** lock reverse */
	private boolean lockReverse = false;

	/** flag utility for visible state */
	private boolean isVisible = true;

	/**
	 * Constructor.
	 */
	public GlyphMetric() {

	}

	/**
	 * get the metric marker
	 * 
	 * @return metric marker
	 */
	public Marker getMetricGlyphMarker() {
		return metricGlyphMarker;
	}

	/**
	 * set the metric glyph marker
	 * 
	 * @param metricGlyphMarker
	 */
	public void setMetricGlyphMarker(Marker metricGlyphMarker) {
		this.metricGlyphMarker = metricGlyphMarker;
	}

	public void setLockReverse(boolean lockReverse) {
		this.lockReverse = lockReverse;
	}

	public boolean isLockReverse() {
		return lockReverse;
	}

	/**
	 * lock reverse the glyph from the current position
	 */
	public void lockReverse() {
		lockReverse = true;
	}

	/**
	 * unlock reverse the glyph from the current position
	 */
	public void unlockReverse() {
		lockReverse = false;
	}

	/**
	 * get the glyph angle
	 * 
	 * @return the metric glyph angle
	 */
	public float getMetricAngle() {
		return metricAngle;
	}

	/**
	 * set the metric glyph angle
	 * 
	 * @param metricAngle
	 */
	public void setMetricAngle(float metricAngle) {
		this.metricAngle = metricAngle;
	}

	/**
	 * get the radial point
	 * 
	 * @param div
	 *            the divergence from the path
	 * @param side
	 *            the side relative to the path
	 * @return the radial point
	 */
	public Point2D getRadialPoint(int div, Side side) {
		if (metricPointRef == null) {
			return null;
		}

		double px;
		double py;
		if (side == Side.SideRight) {
			px = metricPointRef.getX() - div * Math.sin(metricAngle);
			py = metricPointRef.getY() + div * Math.cos(metricAngle);
		} else {
			px = metricPointRef.getX() + div * Math.sin(metricAngle);
			py = metricPointRef.getY() - div * Math.cos(metricAngle);
		}
		return new Point2D.Double(px, py);
	}

	

	/**
	 * get the orthogonal left point
	 * 
	 * @param div
	 *            the divergence from the path
	 * @return the ortho point
	 */
	public Point2D getOrthoLeftPoint(int div) {
		return getOrthoLeftPoint(div, 0);
	}

	/**
	 * get the orthogonal left point shift
	 * 
	 * @param divOrtho
	 *            the divergence from the path
	 * @param divRadial
	 * @return the ortho point
	 */
	public Point2D getOrthoLeftPoint(int divOrtho, int divRadial) {
		if (metricPointRef == null) {
			return null;
		}

		double px;
		double py;
		px = metricPointRef.getX() + divRadial * Math.sin(metricAngle) + divOrtho * Math.sin(metricAngle + Math.PI / 2);
		py = metricPointRef.getY() - divRadial * Math.cos(metricAngle) - divOrtho * Math.cos(metricAngle + Math.PI / 2);
		return new Point2D.Double(px, py);
	}

	/**
	 * get the orthogonal right point
	 * 
	 * @param divOrtho
	 *            the divergence from the path
	 * @return the ortho point
	 */
	public Point2D getOrthoRightPoint(int divOrtho) {
		return getOrthoRightPoint(divOrtho, 0);
	}

	/**
	 * get the orthogonal right point shift
	 * 
	 * @param divOrtho
	 *            the divergence from the path
	 * @param divRadial
	 * @return the ortho point
	 */
	public Point2D getOrthoRightPoint(int divOrtho, int divRadial) {
		if (metricPointRef == null) {
			return null;
		}

		double px;
		double py;
		px = metricPointRef.getX() + divRadial * Math.sin(metricAngle) + divOrtho * Math.sin(metricAngle - Math.PI / 2);
		py = metricPointRef.getY() - divRadial * Math.cos(metricAngle) - divOrtho * Math.cos(metricAngle - Math.PI / 2);
		return new Point2D.Double(px, py);
	}

	/**
	 * get the radial point
	 * 
	 * @param divergence
	 *            the divergence from the path relative to the internal side of
	 *            this metrics
	 * @return the radial point
	 */
	public Point2D getRadialPoint(int divergence) {

		// System.out.println("metrics point Ref : "+metricPointRef);

		if (metricPointRef == null) {
			return null;
		}

		// System.out.println("getRadialPoint divergence = "+divergence);
		// System.out.println("getRadialPoint metricPointRef = "+metricPointRef);

		double px = metricPointRef.getX() + divergence * Math.sin(metricAngle);
		double py = metricPointRef.getY() - divergence * Math.cos(metricAngle);

		return new Point2D.Double(px, py);
	}

	/**
	 * get the marker metric point reference
	 * 
	 * @return the reference
	 */
	public Point2D getMetricPointRef() {
		return metricPointRef;
	}

	/**
	 * set the marker metric reference
	 * 
	 * @param metricPointRef
	 */
	public void setMetricPointRef(Point2D metricPointRef) {
		this.metricPointRef = metricPointRef;
	}

	/**
	 * get the divergence of the metric from the path
	 * 
	 * @return the divergence
	 */
	public int getDivergence() {
		return divergence;
	}

	/**
	 * set the divergence
	 * 
	 * @param divergence
	 */
	public void setDivergence(int divergence) {
		this.divergence = divergence;
	}

	/**
	 * @return the metricsGlyphGeometry
	 */
	public List<GlyphGeometry> getMetricsGlyphGeometry() {
		return metricsGlyphGeometry;
	}

	/**
	 * clear all glyph geometry
	 */
	public void clearGlyphGeometry() {
		if (metricsGlyphGeometry != null) {
			metricsGlyphGeometry.clear();
		}
	}

	/**
	 * @param metricsGlyphGeometry
	 *            the metricsGlyphGeometry to set
	 */
	public void setMetricsGlyphGeometry(List<GlyphGeometry> metricsGlyphGeometry) {
		this.metricsGlyphGeometry = metricsGlyphGeometry;
	}

	/**
	 * add metric glyph geometry
	 * 
	 * @param metricGlyphGeometry
	 *            the metric glyph geometry to add
	 */
	public void addMetricsGlyphGeometry(GlyphGeometry metricGlyphGeometry) {
		if (metricsGlyphGeometry == null) {
			metricsGlyphGeometry = new ArrayList<GlyphGeometry>();
		}
		metricsGlyphGeometry.add(metricGlyphGeometry);
	}

	/**
	 * add a glyph point
	 * 
	 * @param glyphPoint
	 */
	public void addGlyphPoint(Point2D glyphPoint) {
		if (glyphsPoints == null) {
			glyphsPoints = new ArrayList<Point2D>();
		}

		glyphsPoints.add(glyphPoint);
	}

	/**
	 * get the glyphs points
	 * 
	 * @return the glyphs points
	 */
	public List<Point2D> getGlyphsPoints() {
		return glyphsPoints;
	}

	/**
	 * get the style position
	 * 
	 * @return the style position
	 */
	public StylePosition getStylePosition() {
		return stylePosition;
	}

	/**
	 * set the style position
	 * 
	 * @param style
	 *            the style position
	 */
	public void setStylePosition(StylePosition style) {
		stylePosition = style;
	}

	/**
	 * get the glyph font
	 * 
	 * @return the glyph font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * set the glyph font
	 * 
	 * @param font
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * get the metric nature
	 * 
	 * @return the metric nature
	 */
	public GlyphMetricsNature getMetricsNature() {
		return metricNature;
	}

	public void setMetricsNature(GlyphMetricsNature metricNature) {
		this.metricNature = metricNature;
	}

	// /**
	// * get the theme color for marker
	// * @return marker color
	// */
	// public Color getMetricsMarkerColor() {
	// return metricsMarkerColor;
	// }
	//
	//
	// /**
	// * set the theme marker color
	// * @param metricsMarkerColor
	// */
	// public void setMetricsMarkerColor(Color metricsMarkerColor) {
	// this.metricsMarkerColor = metricsMarkerColor;
	// }

	/**
	 * get the the length on path for this metrics
	 * 
	 * @return length on path
	 */
	public double getLengthOnPath() {
		return lengthOnPath;
	}

	/**
	 * set the length on path for this metrics
	 * 
	 * @param lengthOnPath
	 */
	public void setLengthOnPath(double lengthOnPath) {
		this.lengthOnPath = lengthOnPath;
	}

	/**
	 * get the user value of this metrics
	 * 
	 * @return user value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * set the user value for this metrics
	 * 
	 * @param value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	// /**
	// * get the label theme color
	// * @return label color
	// */
	// public Color getMetricsLabelColor() {
	// return metricsLabelColor;
	// }
	//
	// /**
	// * set the label theme color
	// * @param metricsLabelColor
	// */
	// public void setMetricsLabelColor(Color metricsLabelColor) {
	// this.metricsLabelColor = metricsLabelColor;
	// }

	/**
	 * get the metrics format of
	 * 
	 * @return format
	 */
	public IMetricsFormat getFormat() {
		return format;
	}

	/**
	 * set the metrics format
	 * 
	 * @param format
	 */
	public void setFormat(IMetricsFormat format) {
		this.format = format;
	}

	/**
	 * get the metrics label
	 * 
	 * @return metrics label
	 */
	public String getMetricsLabel() {
		return metricsLabel;
	}

	/**
	 * set the metrics label
	 * 
	 * @param metricsLabel
	 */
	public void setMetricsLabel(String metricsLabel) {
		this.metricsLabel = metricsLabel;
	}

	/**
	 * get the start point of this glyph metrics *
	 * 
	 * @return the start point
	 */
	public Point2D getPointStart() {
		return pointStart;
	}

	/**
	 * set the start point of this glyph metrics *
	 * 
	 * @param pointStart
	 *            the start point
	 */
	public void setPointStart(Point2D pointStart) {
		this.pointStart = pointStart;
	}

	/**
	 * get the end point of this glyph metrics *
	 * 
	 * @return the end point
	 */
	public Point2D getPointEnd() {
		return pointEnd;
	}

	/**
	 * set the end point of this glyph metrics
	 * 
	 * @param pointEnd
	 *            the end point
	 */
	public void setPointEnd(Point2D pointEnd) {
		this.pointEnd = pointEnd;
	}

	/**
	 * get the metric draw
	 * 
	 * @return the metric draw
	 */
	public GlyphMetricDraw getGlyphMetricDraw() {
		return glyphMetricDraw;
	}

	/**
	 * set the metric draw
	 * 
	 * @param glyphMetricDraw
	 */
	public void setGlyphMetricDraw(GlyphMetricDraw glyphMetricDraw) {
		this.glyphMetricDraw = glyphMetricDraw;
	}

	/**
	 * get glyph metric fill
	 * 
	 * @return the metric fill
	 */
	public GlyphMetricFill getGlyphMetricFill() {
		return glyphMetricFill;
	}

	/**
	 * set the metric fill
	 * 
	 * @param glyphMetricFill
	 */
	public void setGlyphMetricFill(GlyphMetricFill glyphMetricFill) {
		this.glyphMetricFill = glyphMetricFill;
	}

	/**
	 * get the metric effect
	 * 
	 * @return the metric effect
	 */
	public GlyphMetricEffect getGlyphMetricEffect() {
		return glyphMetricEffect;
	}

	/**
	 * set the metric effect
	 * 
	 * @param glyphMetricEffect
	 */
	public void setGlyphMetricEffect(GlyphMetricEffect glyphMetricEffect) {
		this.glyphMetricEffect = glyphMetricEffect;
	}

	/**
	 * get the metric marker
	 * 
	 * @return glyphMetricMarkerPainter
	 */
	public GlyphMetricMarkerPainter getGlyphMetricMarkerPainter() {
		return glyphMetricMarkerPainter;
	}

	/**
	 * set the metric marker
	 * 
	 * @param glyphMetricMarkerPainter
	 */
	public void setGlyphMetricMarkerPainter(GlyphMetricMarkerPainter glyphMetricMarkerPainter) {
		this.glyphMetricMarkerPainter = glyphMetricMarkerPainter;
	}

	/**
	 * @return the isVisible
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible
	 *            the isVisible to set
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

}
