/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.pie;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;

/**
 * <code>PieDeflater</code>
 * @author sebastien janaud
 *
 */
public class PieDeflater extends AbstractX2DPluginDeflater<PiePlugin> {

	public PieDeflater() {
		super();
		setXSIType("PiePlugin");
	}

	public PieDeflater(PiePlugin plugin) {
		super(plugin);
		setXSIType("PiePlugin");
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
