/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.glyphmetrics.painter.marker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import org.jensoft.core.glyphmetrics.GlyphMetric;
import org.jensoft.core.glyphmetrics.Side;
import org.jensoft.core.glyphmetrics.painter.GlyphMetricMarkerPainter;

/**
 * <code>RectangleMarker</code>
 * 
 * @author sebastien janaud
 * 
 */
public class RectangleMarker extends GlyphMetricMarkerPainter {

	/** start radial paint color */
	private Color startColor = Color.WHITE;

	/** end radial paint color */
	private Color endColor = Color.BLACK;

	/** divergence orthogonal */
	private int divergenceOrtho = 10;

	/** divergence radial */
	private int divergenceRadial = 5;

	/** global radial shift */
	private int globalRadialShift = 0;

	/**
	 * create rectangle marker with given parameters
	 * 
	 * @param startColor
	 * @param endColor
	 */
	public RectangleMarker(Color startColor, Color endColor) {
		super();
		this.startColor = startColor;
		this.endColor = endColor;
	}

	/**
	 * create rectangle marker with given parameters
	 * 
	 * @param startColor
	 * @param endColor
	 * @param divergenceOrtho
	 * @param divergenceRadial
	 */
	public RectangleMarker(Color startColor, Color endColor, int divergenceOrtho, int divergenceRadial) {
		super();
		this.startColor = startColor;
		this.endColor = endColor;
		this.divergenceOrtho = divergenceOrtho;
		this.divergenceRadial = divergenceRadial;
	}

	/**
	 * create rectangle marker with given parameters
	 * 
	 * @param startColor
	 * @param endColor
	 * @param divergenceOrtho
	 * @param divergenceRadial
	 * @param globalRadialShift
	 */
	public RectangleMarker(Color startColor, Color endColor, int divergenceOrtho, int divergenceRadial, int globalRadialShift) {
		super();
		this.startColor = startColor;
		this.endColor = endColor;
		this.divergenceOrtho = divergenceOrtho;
		this.divergenceRadial = divergenceRadial;
		this.globalRadialShift = globalRadialShift;
	}
	
	

	public Color getStartColor() {
		return startColor;
	}

	public void setStartColor(Color startColor) {
		this.startColor = startColor;
	}

	public Color getEndColor() {
		return endColor;
	}

	public void setEndColor(Color endColor) {
		this.endColor = endColor;
	}

	public int getDivergenceOrtho() {
		return divergenceOrtho;
	}

	public void setDivergenceOrtho(int divergenceOrtho) {
		this.divergenceOrtho = divergenceOrtho;
	}

	public int getDivergenceRadial() {
		return divergenceRadial;
	}

	public void setDivergenceRadial(int divergenceRadial) {
		this.divergenceRadial = divergenceRadial;
	}

	public int getGlobalRadialShift() {
		return globalRadialShift;
	}

	public void setGlobalRadialShift(int globalRadialShift) {
		this.globalRadialShift = globalRadialShift;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.glyphmetrics.painter.GlyphMetricMarkerPainter#
	 * paintGlyphMetricMarker(java.awt.Graphics2D,
	 * com.jensoft.core.glyphmetrics.GlyphMetric)
	 */
	@Override
	public void paintGlyphMetricMarker(Graphics2D g2d, GlyphMetric glyphMetric) {

		Point2D pLeft = glyphMetric.getOrthoLeftPoint(divergenceOrtho, globalRadialShift + divergenceRadial);
		if (pLeft == null) {
			return;
		}

		Point2D pRight = glyphMetric.getOrthoRightPoint(divergenceOrtho, globalRadialShift + divergenceRadial);
		if (pRight == null) {
			return;
		}

		Point2D pLeft2 = glyphMetric.getOrthoLeftPoint(divergenceOrtho, globalRadialShift - divergenceRadial);
		if (pLeft2 == null) {
			return;
		}

		Point2D pRight2 = glyphMetric.getOrthoRightPoint(divergenceOrtho, globalRadialShift - divergenceRadial);
		if (pRight2 == null) {
			return;
		}

		GeneralPath path = new GeneralPath();
		path.moveTo(pLeft.getX(), pLeft.getY());
		path.lineTo(pRight.getX(), pRight.getY());
		path.lineTo(pRight2.getX(), pRight2.getY());
		path.lineTo(pLeft2.getX(), pLeft2.getY());
		path.closePath();

		Point2D pStart = glyphMetric.getRadialPoint(globalRadialShift + divergenceRadial, Side.SideLeft);
		Point2D pEnd = glyphMetric.getRadialPoint(globalRadialShift + divergenceRadial, Side.SideRight);

		LinearGradientPaint lgp = new LinearGradientPaint(pStart, pEnd, new float[] { 0, 1 }, new Color[] { startColor, endColor });

		g2d.setPaint(lgp);
		g2d.fill(path);
	}

}
