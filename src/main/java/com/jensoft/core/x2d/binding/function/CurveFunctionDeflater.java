/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.function;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.function.FunctionPlugin.AreaFunction;
import com.jensoft.core.plugin.function.FunctionPlugin.CurveFunction;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import com.jensoft.core.x2d.binding.X2DBinding;

/**
 * <code>CurveFunctionPlugin</code>
 * @author sebastien janaud
 *
 */
@X2DBinding(xsi="CurvePlugin",plugin=AreaFunction.class)
public class CurveFunctionDeflater extends AbstractX2DPluginDeflater<CurveFunction> {

	/* (non-Javadoc)
	 * @see com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate(CurveFunction plugin) {
		Element pluginElement = createPluginRootElement();
		return pluginElement;
	}
	
	

}
