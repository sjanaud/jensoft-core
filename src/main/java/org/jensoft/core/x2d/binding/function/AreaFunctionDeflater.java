/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.function;

import org.jensoft.core.plugin.function.FunctionPlugin.AreaFunction;
import org.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;

/**
 * <code>AreaFunctionDeflater</code>
 * @author sebastien janaud
 *
 */
@X2DBinding(xsi="AreaPlugin",plugin=AreaFunction.class)
public class AreaFunctionDeflater extends AbstractX2DPluginDeflater<AreaFunction> {


	/* (non-Javadoc)
	 * @see org.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate(AreaFunction plugin) {
		Element pluginElement = createPluginRootElement();
		return pluginElement;
	}
	
	

}
