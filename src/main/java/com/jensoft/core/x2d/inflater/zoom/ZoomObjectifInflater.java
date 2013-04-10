/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.inflater.zoom;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.zoom.objectif.ObjectifDefaultDeviceContext;
import com.jensoft.core.plugin.zoom.objectif.ObjectifX;
import com.jensoft.core.plugin.zoom.objectif.ObjectifY;
import com.jensoft.core.plugin.zoom.objectif.ZoomObjectifPlugin;
import com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.inflater.X2DInflater;

/**
 * <code>ZoomObjectifInflater</code>
 * 
 * @author Sebastien Janaud
 */
@X2DInflater(xsi="ZoomObjectifPlugin")
public class ZoomObjectifInflater extends AbstractX2DPluginInflater<ZoomObjectifPlugin> {

    /**
     * create zoom objectif inflater
     */
    public ZoomObjectifInflater() {
        setPlugin(new ZoomObjectifPlugin());
        setXSIType("ZoomObjectifPlugin");
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public void inflate(Element pluginElement) {

        ZoomObjectifPlugin zoomObjectif = new ZoomObjectifPlugin();
        
        zoomObjectif.registerContext(new ObjectifDefaultDeviceContext());
        
        ObjectifX ox = new ObjectifX();
        ObjectifY oy = new ObjectifY();
        zoomObjectif.registerWidget(ox,oy);      

        setPlugin(zoomObjectif);
    }

}
