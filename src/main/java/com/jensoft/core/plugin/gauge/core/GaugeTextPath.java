/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core;

import java.awt.Color;
import java.awt.Graphics2D;

import com.jensoft.core.drawable.text.TextPath;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.gauge.core.binder.PathBinder;

/**
 * <code>GaugeTextPath</code> takes the responsibility to draw text along path
 * define in gauge environment.
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class GaugeTextPath extends TextPath {

	/** gauge host this metrics path */
	private RadialGauge gauge;

	/** gauge part buffer to paint reuse */
	private GaugePartBuffer partBuffer;

	/** path binder */
	private PathBinder pathBinder;

	/** debug flag arc for paint arc */
	private boolean debugPath = false;

	/** debug arc color */
	private Color debugPathColor = NanoChromatique.RED;

	public GaugeTextPath() {
		super();
	}

	public PathBinder getPathBinder() {
		return pathBinder;
	}

	public void setPathBinder(PathBinder pathBinder) {
		this.pathBinder = pathBinder;
	}

	public RadialGauge getGauge() {
		return gauge;
	}

	public void setGauge(RadialGauge gauge) {
		this.gauge = gauge;
	}

	/**
	 * @param partBuffer
	 *            the partBuffer to set
	 */
	public void setPartBuffer(GaugePartBuffer partBuffer) {
		this.partBuffer = partBuffer;
	}

	/**
	 * get part buffer of this gauge text path
	 * 
	 * @return
	 */
	public GaugePartBuffer getPartBuffer() {
		return partBuffer;
	}

	/**
	 * create part buffer of this text path from original context.
	 * 
	 * @param g2d
	 */
	public void createPartBuffer(Graphics2D g2d) {
		partBuffer = new GaugePartBuffer(getGauge());

		Graphics2D g2dPart = partBuffer.getGraphics();
		g2dPart.setRenderingHints(g2d.getRenderingHints());
		// setFontRenderContext(g2d.getFontRenderContext());

		if (debugPath) {
			g2dPart.setColor(debugPathColor);
			// g2dPart.draw(getOrCreateGeometry().getPath());
			// or
			g2dPart.draw(getPathBinder().bindPath(getGauge()));
		}

		draw(g2dPart);
	}

}
