/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics.painter.marker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.Side;
import com.jensoft.core.glyphmetrics.painter.GlyphMetricMarkerPainter;

/**
 * default line marker on path at point glyph
 * 
 * @author Sebastien Janaud
 */
public class DefaultMarker extends GlyphMetricMarkerPainter {

	/** marker color */
	private Color markerColor;

	/** marker stroke */
	private Stroke markerStroke;

	/** marker divergence */
	private int divergence = 2;

	/**
	 * define default marker with specified color
	 * 
	 * @param markerColor
	 *            the marker color
	 */
	public DefaultMarker(Color markerColor) {
		this.markerColor = markerColor;
	}

	/**
	 * create default marker with specified color and divergence
	 * 
	 * @param markerColor
	 *            marker color
	 * @param divergence
	 *            divergence
	 */
	public DefaultMarker(Color markerColor, int divergence) {
		super();
		this.markerColor = markerColor;
		this.divergence = divergence;
	}

	/**
	 * create default marker with specified color and divergence
	 * 
	 * @param markerColor
	 *            marker color
	 * @param markerStroke
	 *            marker stroke
	 * @param divergence
	 *            divergence
	 */
	public DefaultMarker(Color markerColor, Stroke markerStroke, int divergence) {
		super();
		this.markerColor = markerColor;
		this.markerStroke = markerStroke;
		this.divergence = divergence;
	}

	/**
	 * get marker color
	 * 
	 * @return marker color
	 */
	public Color getMarkerColor() {
		return markerColor;
	}

	/**
	 * set marker color
	 * 
	 * @param markerColor
	 *            the marker color to set
	 */
	public void setMarkerColor(Color markerColor) {
		this.markerColor = markerColor;
	}

	/**
	 * get marker stroke
	 * 
	 * @return marker stroke
	 */
	public Stroke getMarkerStroke() {
		return markerStroke;
	}

	/**
	 * set marker stroke
	 * 
	 * @param markerStroke
	 *            marker stroke to set
	 */
	public void setMarkerStroke(Stroke markerStroke) {
		this.markerStroke = markerStroke;
	}

	/**
	 * get the divergence
	 * 
	 * @return divergence
	 */
	public int getDivergence() {
		return divergence;
	}

	/**
	 * set the divergence
	 * 
	 * @param divergence
	 *            the divergence to set
	 */
	public void setDivergence(int divergence) {
		this.divergence = divergence;
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

		if (markerColor == null) {
			return;
		}

		g2d.setColor(markerColor);

		if (markerStroke != null) {
			g2d.setStroke(markerStroke);
		}

		Point2D p = glyphMetric.getMetricPointRef();

		if (p == null) {
			return;
		}

		double px;
		double py;
		px = glyphMetric.getRadialPoint(divergence, Side.SideLeft).getX();
		py = glyphMetric.getRadialPoint(divergence, Side.SideLeft).getY();

		double px1;
		double py1;
		px1 = glyphMetric.getRadialPoint(divergence, Side.SideRight).getX();
		py1 = glyphMetric.getRadialPoint(divergence, Side.SideRight).getY();

		Line2D l = new Line2D.Double(px, py, px1, py1);

		g2d.setStroke(new BasicStroke(2f));
		g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1f));
		g2d.draw(l);

	}

}
