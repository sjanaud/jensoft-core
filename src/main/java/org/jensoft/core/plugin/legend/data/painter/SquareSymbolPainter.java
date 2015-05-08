/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.legend.data.painter;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.jensoft.core.plugin.legend.data.DataLegend.Item;

/**
 * <code>SquareSymbolPainter</code> paints a square symbol for data legend item
 * 
 * @since 1.1
 * @author sebastien janaud
 * 
 */
public class SquareSymbolPainter extends AbstractDataItemSymbolPainter {

	
	/**
	 * create square data legend symbol
	 */
	public SquareSymbolPainter() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.jensoft.core.plugin.legend.data.AbstractDataItemSymbolPainter#paintItemSymbol(java.awt.Graphics2D, java.awt.geom.Rectangle2D, org.jensoft.core.plugin.legend.data.DataLegend.Item)
	 */
	@Override
	public void paintItemSymbol(Graphics2D g2d,Rectangle2D symbolBound, Item item) {
		g2d.setColor(item.getColor());
		Rectangle2D square = new Rectangle2D.Double(symbolBound.getCenterX()-2,symbolBound.getCenterY()-2, 4, 4);
		g2d.fill(square);
	}

}
