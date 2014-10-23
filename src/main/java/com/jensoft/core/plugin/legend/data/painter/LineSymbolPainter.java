/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.data.painter;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.plugin.legend.data.DataLegend.Item;

/**
 * <code>LineSymbolPainter</code> paints a line symbol for data legend item
 * 
 * @author sebastien janaud
 * 
 */
public class LineSymbolPainter extends AbstractDataItemSymbolPainter {

	private Stroke stroke = new BasicStroke();

	/**
	 * create basic line symbol painter
	 */
	public LineSymbolPainter() {
		super();
	}

	/**
	 * create line symbol painter with given stroke line
	 * 
	 * @param stroke
	 */
	public LineSymbolPainter(Stroke stroke) {
		super();
		this.stroke = stroke;
	}

	/**
	 * get line symbol stroke
	 * 
	 * @return line stroke
	 */
	public Stroke getStroke() {
		return stroke;
	}

	/**
	 * set line symbol stroke
	 * 
	 * @param line
	 *            stroke
	 */
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.legend.data.AbstractDataItemSymbolPainter#
	 * paintItemSymbol(java.awt.Graphics2D, java.awt.geom.Rectangle2D,
	 * com.jensoft.core.plugin.legend.data.DataLegend.Item)
	 */
	@Override
	public void paintItemSymbol(Graphics2D g2d, Rectangle2D symbolBound, Item item) {
		g2d.setColor(item.getColor());
		g2d.setStroke(stroke);
		Line2D line = new Line2D.Double(symbolBound.getX(), symbolBound.getY() + symbolBound.getHeight() / 2, symbolBound.getX() + symbolBound.getWidth(), symbolBound.getY() + symbolBound.getHeight() / 2);
		g2d.draw(line);
	}

}
