/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.inflater.zoom;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.zoom.wheel.ZoomWheelPlugin;
import com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.inflater.X2DInflater;
/**
 * <code>ZoomWheelInflater</code>
 * 
 * @author Sebastien Janaud
 */
@X2DInflater(xsi="ZoomWheelPlugin")
public class ZoomWheelInflater extends AbstractX2DPluginInflater<ZoomWheelPlugin> {

    /**
     * create zoom wheel inflater
     */
    public ZoomWheelInflater() {
        setPlugin(new ZoomWheelPlugin());
        setXSIType("ZoomWheelPlugin");
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.jet.inflater.AbstractPluginInflater#inflate(org
     * .w3c.dom.Element)
     */
    @Override
    public void inflate(Element pluginElement) {
        
    }

  
}
