/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.donut3d;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.donut3d.Donut3DPlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import com.jensoft.core.x2d.binding.X2DBinding;

/**
 * <code>Donut3DDeflater</code>
 * 
 * @author sebastien janaud
 * 
 */
@X2DBinding(xsi = "Donut3DPlugin", plugin = Donut3DPlugin.class)
public class Donut3DDeflater extends AbstractX2DPluginDeflater<Donut3DPlugin> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate(Donut3DPlugin plugin) {
		Element pluginElement = createPluginRootElement();
		return pluginElement;
	}

}
