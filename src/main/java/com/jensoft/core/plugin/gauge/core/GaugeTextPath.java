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

	/** gauge body this metrics path */
	private GaugeBody body;

	/** gauge part buffer to paint reuse */
	private GaugePartBuffer partBuffer;

	/** path binder */
	private PathBinder pathBinder;

	/** debug flag arc for paint arc */
	private boolean debugPath = false;

	/** debug arc color */
	private Color debugPathColor = NanoChromatique.RED;

	/**
	 * create gauge text path
	 */
	public GaugeTextPath() {
		super();
	}

	/**
	 * get path binder of this gauge path
	 * 
	 * @return path binder
	 */
	public PathBinder getPathBinder() {
		return pathBinder;
	}

	/**
	 * set path binder of this gauge text path
	 * 
	 * @param pathBinder
	 */
	public void setPathBinder(PathBinder pathBinder) {
		this.pathBinder = pathBinder;
	}

	/**
	 * @return the body
	 */
	public GaugeBody getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(GaugeBody body) {
		this.body = body;
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
	 * @return part
	 */
	public GaugePartBuffer getPartBuffer() {
		return partBuffer;
	}
	
	/**
	 * invalidate the part buffer
	 */
	public void invalidate(){
		setPartBuffer(null);
	}


	/**
	 * create part buffer of this text path from original context.
	 * 
	 * @param g2d
	 */
	public void createPartBuffer(Graphics2D g2d) {
		partBuffer = new GaugePartBuffer(getBody().getGauge());

		Graphics2D g2dPart = partBuffer.getGraphics();
		g2dPart.setRenderingHints(g2d.getRenderingHints());

		if (debugPath) {
			g2dPart.setColor(debugPathColor);
			// g2dPart.draw(getOrCreateGeometry().getPath());
			// or
			g2dPart.draw(getPathBinder().bindPath(getBody().getGauge()));
		}

		draw(g2dPart);
	}
}
