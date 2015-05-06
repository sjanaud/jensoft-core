/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.function;

import org.jensoft.core.plugin.function.FunctionPlugin.AreaFunction;
import org.jensoft.core.plugin.function.FunctionPlugin.CurveFunction;
import org.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;

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
