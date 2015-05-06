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
import org.jensoft.core.glyphmetrics.painter.GlyphMetricMarkerPainter;

/**
 * <code>TriangleMarker</code>
 * 
 * @author sebastien janaud
 * 
 */
public class TriangleMarker extends GlyphMetricMarkerPainter {

	/** start radial paint color */
	private Color startColor = Color.WHITE;

	/** end radial paint color */
	private Color endColor = Color.BLACK;

	/** divergence orthogonal */
	private int divergenceOrtho = 10;

	/** divergence radial */
	private int divergenceRadial = 10;

	/** global radial shift */
	private int globalRadialShift = 0;

	/** direction */
	private TriangleDirection direction = TriangleDirection.In;

	/**
	 * 
	 * triangle go to gauge center in/out
	 *
	 */
	public enum TriangleDirection {
		In, Out
	}

	/**
	 * create triangle marker with given parameters
	 * 
	 * @param startColor
	 * @param endColor
	 */
	public TriangleMarker(Color startColor, Color endColor) {
		super();
		this.startColor = startColor;
		this.endColor = endColor;
	}

	/**
	 * create triangle marker with given parameters
	 * 
	 * @param startColor
	 * @param endColor
	 * @param divergenceOrtho
	 * @param divergenceRadial
	 */
	public TriangleMarker(Color startColor, Color endColor, int divergenceOrtho, int divergenceRadial) {
		super();
		this.startColor = startColor;
		this.endColor = endColor;
		this.divergenceOrtho = divergenceOrtho;
		this.divergenceRadial = divergenceRadial;
	}

	/**
	 * create triangle marker with given parameters
	 * 
	 * @param startColor
	 * @param endColor
	 * @param divergenceOrtho
	 * @param divergenceRadial
	 * @param globalRadialShift
	 */
	public TriangleMarker(Color startColor, Color endColor, int divergenceOrtho, int divergenceRadial, int globalRadialShift) {
		super();
		this.startColor = startColor;
		this.endColor = endColor;
		this.divergenceOrtho = divergenceOrtho;
		this.divergenceRadial = divergenceRadial;
		this.globalRadialShift = globalRadialShift;
	}

	/**
	 * create triangle marker with given parameters
	 * 
	 * @param startColor
	 * @param endColor
	 * @param divergenceOrtho
	 * @param divergenceRadial
	 * @param globalRadialShift
	 * @param direction
	 */
	public TriangleMarker(Color startColor, Color endColor, int divergenceOrtho, int divergenceRadial, int globalRadialShift, TriangleDirection direction) {
		super();
		this.startColor = startColor;
		this.endColor = endColor;
		this.divergenceOrtho = divergenceOrtho;
		this.divergenceRadial = divergenceRadial;
		this.globalRadialShift = globalRadialShift;
		this.direction = direction;
	}

	public TriangleDirection getDirection() {
		return direction;
	}

	public void setDirection(TriangleDirection direction) {
		this.direction = direction;
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

		Point2D pLeft = glyphMetric.getOrthoLeftPoint(divergenceOrtho, globalRadialShift);
		if (pLeft == null) {
			return;
		}

		Point2D pRight = glyphMetric.getOrthoRightPoint(divergenceOrtho, globalRadialShift);
		if (pRight == null) {
			return;
		}

		Point2D pAnchor;
		if (direction == TriangleDirection.Out)
			pAnchor = glyphMetric.getRadialPoint(globalRadialShift + divergenceRadial);
		else
			pAnchor = glyphMetric.getRadialPoint(globalRadialShift - divergenceRadial);

		

		GeneralPath path = new GeneralPath();
		path.moveTo(pLeft.getX(), pLeft.getY());
		path.lineTo(pRight.getX(), pRight.getY());
		path.lineTo(pAnchor.getX(), pAnchor.getY());
		path.closePath();

		Point2D pStart = glyphMetric.getRadialPoint(globalRadialShift);
		Point2D pEnd = pAnchor;

		LinearGradientPaint lgp = new LinearGradientPaint(pStart, pEnd, new float[] { 0, 1 }, new Color[] { startColor, endColor });

		g2d.setPaint(lgp);

		g2d.fill(path);

		
		//DEBUG POINTS
		//g2d.setColor(Color.RED);
		// g2d.drawRect((int)pAnchor.getX(),(int)pAnchor.getY(),2,2);
		//g2d.setColor(Color.RED);
		// g2d.drawRect((int)pLeft.getX(),(int)pLeft.getY(),2,2);
		//g2d.setColor(Color.RED);
		//g2d.drawRect((int)pRight.getX(),(int)pRight.getY(),2,2);
		//g2d.setColor(Color.YELLOW);
		//g2d.drawRect((int) pStart.getX(), (int) pStart.getY(), 2, 2);
		//g2d.setColor(Color.CYAN);
		//g2d.drawRect((int) pEnd.getX(), (int) pEnd.getY(), 2, 2);
	}

}
