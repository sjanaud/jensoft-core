/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.donut2d;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;

/**
 * <code>Donut2DDeflater</code>
 * @author sebastien janaud
 *
 */
public class Donut2DDeflater extends AbstractX2DPluginDeflater<PiePlugin> {

	public Donut2DDeflater() {
		super();
		setXSIType("Donut2DPlugin");
	}

	public Donut2DDeflater(PiePlugin plugin) {
		super(plugin);
		setXSIType("Donut2DPlugin");
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
