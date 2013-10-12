/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.plot;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.plot.spline.AbstractPlot;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>PlotPlugin</code> <br>
 * 
 * @author Sebastien Janaud
 */
public class PlotPlugin extends AbstractPlugin{

	private List<AbstractPlot> plots = new ArrayList<AbstractPlot>();

	/**
	 * Create plot plugin
	 */
	public PlotPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view
	 * .View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
	 */
	@Override
	protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
		if (windowPart != WindowPart.Device)
			return;

		for (AbstractPlot plot : plots) {
			plot.solvePlotAnchors();
			plot.solvePlot();
			
			if(plot.getPlotDraw() != null){
				plot.getPlotDraw().paintPlot(g2d, plot);
			}
			
//			if(plot.getPlotLabel() != null){
//				plot.getPlotLabel().paintPlot(g2d, plot);
//			}
			
			if(plot.getPlotAnchorsPainter() != null){
				plot.getPlotAnchorsPainter().paintPlot(g2d, plot);
			}
			
		}
	}

	/**
	 * register the given plot
	 * 
	 * @param plot
	 *            the plot to paint
	 */
	public void addPlot(AbstractPlot plot) {
		plot.setHost(this);
		plots.add(plot);
	}

}
