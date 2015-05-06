/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.plot.painter.draw;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.plot.painter.AbstractPlotPainter;
import org.jensoft.core.plugin.plot.spline.AbstractPlot;

/**
 * <code>PlotDefaultDraw</code>
 * 
 * @author sebastien janaud
 * 
 */
public abstract class AbstractPlotDraw extends AbstractPlotPainter {

	public AbstractPlotDraw() {
	}
	
	protected abstract void drawPlot(Graphics2D g2d, AbstractPlot plot);

	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.plot.painter.AbstractPlotPainter#paintPlot(java.awt.Graphics2D, com.jensoft.core.plugin.plot.spline.AbstractPlot)
	 */
	@Override
	public final void paintPlot(Graphics2D g2d, AbstractPlot plot) {
		drawPlot(g2d, plot);
	}
	
	

}
