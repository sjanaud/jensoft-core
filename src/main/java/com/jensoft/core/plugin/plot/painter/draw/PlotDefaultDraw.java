/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.plot.painter.draw;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.plot.spline.AbstractPlot;

/**
 * <code>PlotDefaultDraw</code>
 * 
 * @author sebastien janaud
 * 
 */
public class PlotDefaultDraw extends AbstractPlotDraw {

	public PlotDefaultDraw() {
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.plot.painter.draw.AbstractPlotDraw#drawPlot(java.awt.Graphics2D, com.jensoft.core.plugin.plot.spline.AbstractPlot)
	 */
	@Override
	public final void drawPlot(Graphics2D g2d, AbstractPlot plot) {
		if (plot.getPlotDrawColor() != null)
			g2d.setColor(plot.getPlotDrawColor());
		else
			g2d.setColor(plot.getHost().getWindow2D().getThemeColor());
		
		g2d.draw(plot.getPlotPath());
	}
	
	

}
