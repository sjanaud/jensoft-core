/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.zoom;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.zoom.wheel.ZoomWheelPlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.binding.X2DBinding;
/**
 * <code>ZoomWheelInflater</code>
 * 
 * @author Sebastien Janaud
 */
@X2DBinding(xsi="ZoomWheelPlugin",plugin=ZoomWheelPlugin.class)
public class ZoomWheelInflater extends AbstractX2DPluginInflater<ZoomWheelPlugin> {

      
    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public ZoomWheelPlugin inflate(Element pluginElement) {
        return new ZoomWheelPlugin();
    }
  
}
