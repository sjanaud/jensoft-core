/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.plot.painter.anchor;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.jensoft.core.plugin.plot.spline.AbstractPlot;
import org.jensoft.core.plugin.plot.spline.PlotAnchor;

/**
 * <code>PlotAnchorDefaultPainter</code>
 * 
 * @author sebastien janaud
 * 
 */
public class PlotAnchorDefaultPainter extends AbstractPlotAnchorPainter {

	public PlotAnchorDefaultPainter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jensoft.core.plugin.plot.painter.draw.AbstractPlotDraw#drawPlot(java
	 * .awt.Graphics2D, org.jensoft.core.plugin.plot.spline.AbstractPlot)
	 */
	@Override
	public final void paintPlotAnchors(Graphics2D g2d, AbstractPlot plot) {
		if (plot.getPlotDrawColor() != null)
			g2d.setColor(plot.getPlotDrawColor());
		else
			g2d.setColor(plot.getHost().getProjection().getThemeColor());
		List<PlotAnchor> anchors = plot.getAnchorsPoints();
		if (anchors != null) {
			int count = 0;
			for (PlotAnchor plotAnchor : anchors) {
				Rectangle2D rect = new Rectangle2D.Double(plotAnchor.getDevicePoint().getX() - 2, plotAnchor.getDevicePoint().getY() - 2, 4, 4);
				g2d.draw(rect);
			}
		}

	}

}
