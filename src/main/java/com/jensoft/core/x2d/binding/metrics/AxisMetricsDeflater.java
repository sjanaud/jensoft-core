/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.metrics;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.metrics.AxisMetricsPlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;

/**
 * <code>GridDeflater</code>
 * 
 * @author sebastien janaud
 * 
 */
public class AxisMetricsDeflater extends AbstractX2DPluginDeflater<AxisMetricsPlugin> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate(AxisMetricsPlugin plugin) {
		Element pluginElement = createPluginRootElement();
		return pluginElement;
	}

}
