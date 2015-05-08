/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.zoom;

import org.jensoft.core.plugin.zoom.box.ZoomBoxDefaultDeviceContext;
import org.jensoft.core.plugin.zoom.box.ZoomBoxDonutWidget;
import org.jensoft.core.plugin.zoom.box.ZoomBoxPlugin;
import org.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;
/**
 * <code>ZoomBoxInflater</code>
 * 
 * @author Sebastien Janaud
 */
@X2DBinding(xsi="ZoomBoxPlugin",plugin=ZoomBoxPlugin.class)
public class ZoomBoxInflater extends AbstractX2DPluginInflater<ZoomBoxPlugin> {

       
    /* (non-Javadoc)
     * @see org.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public ZoomBoxPlugin inflate(Element pluginElement) {
        ZoomBoxPlugin box = new ZoomBoxPlugin();
        box.registerContext(new ZoomBoxDefaultDeviceContext());
        box.registerWidget(new ZoomBoxDonutWidget());
        return box;
    }

  
}
