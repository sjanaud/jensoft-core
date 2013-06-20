/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.legend;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.legend.LegendPlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import com.jensoft.core.x2d.binding.X2DBinding;

/**
 * <code>LegendDeflater</code>
 * @author sebastien janaud
 *
 */
@X2DBinding(xsi="LegendPlugin",plugin=LegendPlugin.class)
public class LegendDeflater extends AbstractX2DPluginDeflater<LegendPlugin> {

	/* (non-Javadoc)
	 * @see com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate(LegendPlugin plugin) {
		Element pluginElement = createPluginRootElement();
		return pluginElement;
	}
	
}
