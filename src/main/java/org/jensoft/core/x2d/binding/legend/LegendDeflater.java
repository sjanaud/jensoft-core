/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.legend;

import org.jensoft.core.plugin.legend.title.TitleLegendPlugin;
import org.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;

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
