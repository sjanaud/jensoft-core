/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.legend.title.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.legend.title.TitleLegend;

/**
 * <code>AbstractTitleLegendPainter</code> takes the responsibility to paint
 * title legend
 * 
 * @author sebastien janaud
 */
public abstract class AbstractTitleLegendPainter {

	
	/**
	 * override this method to paint paint the given title legend
	 * @param g2d graphics context
	 * @param legend title legend to paint
	 */
	public abstract void paintLegend(Graphics2D g2d, TitleLegend legend);

}
