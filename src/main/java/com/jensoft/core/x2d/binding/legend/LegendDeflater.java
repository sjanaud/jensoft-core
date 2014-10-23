/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.legend;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.legend.title.TitleLegendPlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import com.jensoft.core.x2d.binding.X2DBinding;

/**
 * <code>LegendDeflater</code>
 * @author sebastien janaud
 *
 */
@X2DBinding(xsi="LegendPlugin",plugin=TitleLegendPlugin.class)
public class LegendDeflater extends AbstractX2DPluginDeflater<TitleLegendPlugin> {

	/* (non-Javadoc)
	 * @see com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate(TitleLegendPlugin plugin) {
		Element pluginElement = createPluginRootElement();
		return pluginElement;
	}
	
}
