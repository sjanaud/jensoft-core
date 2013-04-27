/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.painter.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;

import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;

/**
 * <code>AbstractDonut2DDefaultDraw</code>
 * <p>
 * Abstract definition for donut2D draw operation
 * </p>
 */
public class Donut2DDefaultDraw extends AbstractDonut2DDraw {

	/** draw color */
	private Color outlineColor;

	/** draw stroke */
	private Stroke stroke = new BasicStroke();

	/**slice draw*/
	private Donut2DSliceDefaultDraw sliceDraw;
	/**
	 * create empty draw
	 */
	public Donut2DDefaultDraw() {
		sliceDraw = new Donut2DSliceDefaultDraw();
	}

	/**
	 * create donut draw with specified color
	 * 
	 * @param outlineColor
	 */
	public Donut2DDefaultDraw(Color outlineColor) {
		this();
		this.outlineColor = outlineColor;
		sliceDraw.setDrawColor(outlineColor);
	}

	/**
	 * create donut 2D draw with given color and outline
	 * 
	 * @param outlineColor
	 * @param stroke
	 */
	public Donut2DDefaultDraw(Color outlineColor, Stroke stroke) {
		this();
		this.outlineColor = outlineColor;
		this.stroke = stroke;
		sliceDraw.setDrawColor(outlineColor);
		sliceDraw.setDrawStroke(stroke);
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
	 *            the outline color to set
	 */
	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.donut2d.painter.draw.Donut2DDefaultDraw#
	 * paintDonut2DDraw(java.awt.Graphics2D,
	 * com.jensoft.core.plugin.donut2d.Donut2D)
	 */
	@Override
	public void paintDonut2DDraw(Graphics2D g2d, Donut2D donut2D) {
		
		g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1f));

		List<Donut2DSlice> sections = donut2D.getSlices();
		g2d.setStroke(new BasicStroke(2f));
		for (int j = 0; j < sections.size(); j++) {
			
			Donut2DSlice s = sections.get(j);
			
			sliceDraw.paintDonut2DSlice(g2d, donut2D, s);
			//s.setSliceDraw(sliceDraw);
			
			
			//g2d.setStroke(stroke);
			//g2d.setColor(outlineColor);
			//g2d.draw(s.getSlicePath());
		}
	}

}
