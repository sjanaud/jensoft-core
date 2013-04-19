/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.function;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;

/**
 * <code>ScatterFunctionDeflater</code>
 * @author sebastien janaud
 *
 */
public class ScatterFunctionDeflater extends AbstractX2DPluginDeflater<PiePlugin> {

	public ScatterFunctionDeflater() {
		super();
		setXSIType("ScatterFunctionPlugin");
	}

	public ScatterFunctionDeflater(PiePlugin plugin) {
		super(plugin);
		setXSIType("ScatterFunctionPlugin");
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
