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
 * <code>AbstractDataLegendBackgroundPainter</code> takes the responsibility to paint
 * data legend background
 * 
 * @since 2.0
 * @author sebastien janaud
 * 
 */
public abstract class AbstractDataLegendBackgroundPainter {

	/**
	 * implements this method to customize an item symbol
	 * 
	 * @param g2d
	 *            graphics context
	 * @param backgroundBound
	 *            the clipping region
	 * @param legend
	 *            legend
	 */
	public abstract void paintDataLegendBackground(Graphics2D g2d, Rectangle2D backgroundBound, DataLegend legend);

	/**
	 * paint data legend background
	 * @param g2d
	 * @param backgroundBound
	 * @param legend
	 */
	public final void paintBackground(Graphics2D g2d, Rectangle2D backgroundBound, DataLegend legend) {
		paintDataLegendBackground(g2d, backgroundBound, legend);
	}

}
