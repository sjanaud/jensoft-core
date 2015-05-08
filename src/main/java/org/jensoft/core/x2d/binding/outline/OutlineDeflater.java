/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.outline;

import org.jensoft.core.plugin.outline.OutlinePlugin;
import org.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;

/**
 * <code>OutlineDeflater</code>
 * 
 * @author sebastien janaud
 * 
 */
@X2DBinding(xsi="OutlinePlugin",plugin=OutlinePlugin.class)
public class OutlineDeflater extends AbstractX2DPluginDeflater<OutlinePlugin> implements X2DOutlineElement {

	
	/* (non-Javadoc)
	 * @see org.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate(org.jensoft.core.plugin.AbstractPlugin)
	 */
	@Override
	public Element deflate(OutlinePlugin plugin) {
		Element pluginElement = createPluginRootElement();
		if(getPlugin().getThemeColor() != null){
			Element outlineColorElement = createColorElement(getX2dDocument(), ELEMENT_OUTLINE_COLOR, getPlugin().getThemeColor());
			pluginElement.appendChild(outlineColorElement);
		}
		return pluginElement;
	}

}
