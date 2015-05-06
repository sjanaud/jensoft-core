/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.legend.data.painter;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import org.jensoft.core.plugin.legend.data.DataLegend;

/**
 * <code>DefaultDataLegendBackgroundPainter</code> defines the default to paint
 * data legend background such stroking, filling, textured operations.
 * 
 * @since 2.0
 * 
 * @author sebastien janaud
 * 
 */
public class DefaultDataLegendBackgroundPainter extends AbstractDataLegendBackgroundPainter {

	/** corner radius */
	private int radius = 0;

	/** background alpha, 0 to 1 */
	private float alpha = 1f;

	/** stroke */
	private BasicStroke stroke = new BasicStroke();

	/** outline color */
	private Color outlineColor;

	/** fill color */
	private Color fillColor;

	/** texture */
	private TexturePaint texture;

	/**
	 * create default DefaultDataLegendBackgroundPainter
	 */
	public DefaultDataLegendBackgroundPainter() {
	}

	/**
	 * get alpha
	 * 
	 * @return
	 */
	public float getAlpha() {
		return alpha;
	}

	/**
	 * set alpha
	 * 
	 * @param alpha
	 */
	public void setAlpha(float alpha) {
		if (alpha > 1)
			alpha = 1f;
		if (alpha < 0)
			alpha = 0f;
		this.alpha = alpha;
	}

	/**
	 * get corner radius
	 * 
	 * @return corner radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * set corner radius
	 * 
	 * @param radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * get stroke to use to draw outline
	 * 
	 * @return stroke
	 */
	public BasicStroke getStroke() {
		return stroke;
	}

	/**
	 * set stroke to use to draw outline
	 * 
	 * @param stroke
	 */
	public void setStroke(BasicStroke stroke) {
		this.stroke = stroke;
	}

	/**
	 * get outline color
	 * 
	 * @return outline color
	 */
	public Color getOutlineColor() {
		return outlineColor;
	}

	/**
	 * set outline color
	 * 
	 * @param outlineColor
	 */
	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	/**
	 * get fill color
	 * 
	 * @return fill color
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * set fill color
	 * 
	 * @param fillColor
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	/**
	 * get texture
	 * 
	 * @return texture
	 */
	public TexturePaint getTexture() {
		return texture;
	}

	/**
	 * set texture
	 * 
	 * @param texture
	 */
	public void setTexture(TexturePaint texture) {
		this.texture = texture;
	}

	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.legend.data.painter.AbstractDataLegendBackgroundPainter#paintDataLegendBackground(java.awt.Graphics2D, java.awt.geom.Rectangle2D, com.jensoft.core.plugin.legend.data.DataLegend)
	 */
	@Override
	public void paintDataLegendBackground(Graphics2D g2d, Rectangle2D backgroundBound, DataLegend legend) {
		float acomposite = ((AlphaComposite)g2d.getComposite()).getAlpha();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		RoundRectangle2D rect = new RoundRectangle2D.Double(backgroundBound.getX(), backgroundBound.getY(), backgroundBound.getWidth(), backgroundBound.getHeight(), this.radius, this.radius);
		if (fillColor != null) {
			g2d.setColor(fillColor);
			g2d.fill(rect);
		}
		if (texture != null) {
			g2d.setPaint(texture);
			g2d.fill(rect);
		}
		if (outlineColor != null) {
			g2d.setStroke(this.stroke);
			g2d.setColor(outlineColor);
			g2d.draw(rect);
		}
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, acomposite));
	}

}
