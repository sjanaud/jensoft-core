/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.plot.painter.anchor;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.plot.painter.AbstractPlotPainter;
import org.jensoft.core.plugin.plot.spline.AbstractPlot;

/**
 * <code>PlotDefaultDraw</code>
 * 
 * @author sebastien janaud
 * 
 */
public abstract class AbstractPlotAnchorPainter extends AbstractPlotPainter {

	public AbstractPlotAnchorPainter() {
	}
	
	protected abstract void paintPlotAnchors(Graphics2D g2d, AbstractPlot plot);

	/* (non-Javadoc)
	 * @see org.jensoft.core.plugin.plot.painter.AbstractPlotPainter#paintPlot(java.awt.Graphics2D, org.jensoft.core.plugin.plot.spline.AbstractPlot)
	 */
	@Override
	public final void paintPlot(Graphics2D g2d, AbstractPlot plot) {
		paintPlotAnchors(g2d, plot);
	}
	
	

}
