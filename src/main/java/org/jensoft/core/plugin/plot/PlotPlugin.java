/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.plot;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.plugin.plot.spline.AbstractPlot;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

/**
 * <code>PlotPlugin</code> <br>
 * 
 * 
 * @since 1.0
 * @author Sebastien Janaud
 */
public class PlotPlugin extends AbstractPlugin{

	private List<AbstractPlot> plots = new ArrayList<AbstractPlot>();

	/**
	 * Create plot plugin
	 */
	public PlotPlugin() {
	}

	
	/* (non-Javadoc)
	 * @see org.jensoft.core.plugin.AbstractPlugin#paintPlugin(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
	 */
	@Override
	protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {
		if (viewPart != ViewPart.Device)
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
