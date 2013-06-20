/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.donut2d;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.donut2d.Donut2DPlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import com.jensoft.core.x2d.binding.X2DBinding;

/**
 * <code>Donut2DDeflater</code>
 * 
 * @author sebastien janaud
 * 
 */
@X2DBinding(xsi = "Donut2DPlugin", plugin = Donut2DPlugin.class)
public class Donut2DDeflater extends AbstractX2DPluginDeflater<Donut2DPlugin> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate(Donut2DPlugin donut2dPlugin) {
		Element pluginElement = createPluginRootElement();
		return pluginElement;
	}

}
