/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.donut3d;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;

/**
 * <code>Donut3DDeflater</code>
 * @author sebastien janaud
 *
 */
public class Donut3DDeflater extends AbstractX2DPluginDeflater<PiePlugin> {

	public Donut3DDeflater() {
		super();
		setXSIType("Donut3DPlugin");
	}

	public Donut3DDeflater(PiePlugin plugin) {
		super(plugin);
		setXSIType("Donut3DPlugin");
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
