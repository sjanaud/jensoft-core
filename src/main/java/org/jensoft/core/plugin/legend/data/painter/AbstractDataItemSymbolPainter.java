/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.legend.data.painter;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.jensoft.core.plugin.legend.data.DataLegend;

/**
 * <code>AbstractDataItemSymbolPainter</code> takes the responsibility to paint
 * the data symbol from data legend
 * 
 * @since 1.1
 * @author sebastien janaud
 * 
 */
public abstract class AbstractDataItemSymbolPainter {

	/**
	 * implements this method to customize an item symbol
	 * 
	 * @param g2d
	 *            graphics context
	 * @param symbolBound
	 *            the clipping region
	 * @param item
	 *            item to paint
	 */
	public abstract void paintItemSymbol(Graphics2D g2d, Rectangle2D symbolBound, DataLegend.Item item);

	/**
	 * paint data item symbol
	 * {@link #paintItemSymbol(Graphics2D, Rectangle2D, org.jensoft.core.plugin.legend.data.DataLegend.Item)}
	 * 
	 * @param g2d
	 * @param symbolBound
	 * @param item
	 */
	public final void paintSymbol(Graphics2D g2d, Rectangle2D symbolBound, DataLegend.Item item) {
		paintItemSymbol(g2d, symbolBound, item);
	}

}
