/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.legend;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.legend.LegendPlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;

/**
 * <code>LegendDeflater</code>
 * @author sebastien janaud
 *
 */
public class LegendDeflater extends AbstractX2DPluginDeflater<LegendPlugin> {

	public LegendDeflater() {
		super();
		
	}

	public LegendDeflater(LegendPlugin plugin) {
		super(plugin);
		
	}

	/* (non-Javadoc)
	 * @see com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate() {
		Element pluginElement = createPluginRootElement();
		return pluginElement;
	}
	
	

}
