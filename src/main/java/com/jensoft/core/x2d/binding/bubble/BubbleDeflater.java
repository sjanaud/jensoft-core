/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.bubble;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.bubble.BubblePlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import com.jensoft.core.x2d.binding.X2DBinding;

/**
 * <code>BubbleDeflater</code>
 * 
 * @author sebastien janaud
 * 
 */
@X2DBinding(xsi = "BubblePlugin", plugin = BubblePlugin.class)
public class BubbleDeflater extends AbstractX2DPluginDeflater<BubblePlugin> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate(BubblePlugin bubblePlugin) {
		Element pluginElement = createPluginRootElement();
		return pluginElement;
	}

}
