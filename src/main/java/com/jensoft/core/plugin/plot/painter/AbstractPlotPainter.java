/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.plot.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.plot.spline.AbstractPlot;


/**
 * Abstract definition for plot operation painting
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractPlotPainter implements PlotPainter {

	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.plot.painter.PlotPainter#paintPlot(java.awt.Graphics2D, com.jensoft.core.plugin.plot.spline.AbstractPlot)
	 */
	@Override
	public void paintPlot(Graphics2D g2d, AbstractPlot plot) {
				
	}

  

}
