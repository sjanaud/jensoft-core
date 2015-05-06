/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.bubble;

import org.jensoft.core.plugin.bubble.BubblePlugin;
import org.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;

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
