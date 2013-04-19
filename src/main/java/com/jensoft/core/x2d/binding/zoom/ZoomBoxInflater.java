/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.zoom;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.zoom.box.ZoomBoxDefaultDeviceContext;
import com.jensoft.core.plugin.zoom.box.ZoomBoxDonutWidget;
import com.jensoft.core.plugin.zoom.box.ZoomBoxPlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.binding.X2DInflater;
/**
 * <code>ZoomBoxInflater</code>
 * 
 * @author Sebastien Janaud
 */
@X2DInflater(xsi="ZoomBoxPlugin")
public class ZoomBoxInflater extends AbstractX2DPluginInflater<ZoomBoxPlugin> {

    /**
     * create zoom wheel inflater
     */
    public ZoomBoxInflater() {
        setPlugin(new ZoomBoxPlugin());
        setXSIType("ZoomBoxPlugin");
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public void inflate(Element pluginElement) {
        ZoomBoxPlugin box = new ZoomBoxPlugin();
        box.registerContext(new ZoomBoxDefaultDeviceContext());
        box.registerWidget(new ZoomBoxDonutWidget());
        setPlugin(box);
    }

  
}
