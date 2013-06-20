/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.zoom;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.zoom.objectif.ObjectifDefaultDeviceContext;
import com.jensoft.core.plugin.zoom.objectif.ObjectifX;
import com.jensoft.core.plugin.zoom.objectif.ObjectifY;
import com.jensoft.core.plugin.zoom.objectif.ZoomObjectifPlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.binding.X2DBinding;

/**
 * <code>ZoomObjectifInflater</code>
 * 
 * @author Sebastien Janaud
 */
@X2DBinding(xsi="ZoomObjectifPlugin",plugin=ZoomObjectifPlugin.class)
public class ZoomObjectifInflater extends AbstractX2DPluginInflater<ZoomObjectifPlugin> {

	
    
    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public ZoomObjectifPlugin inflate(Element pluginElement) {

        ZoomObjectifPlugin zoomObjectif = new ZoomObjectifPlugin();
        
        zoomObjectif.registerContext(new ObjectifDefaultDeviceContext());
        
        ObjectifX ox = new ObjectifX();
        ObjectifY oy = new ObjectifY();
        zoomObjectif.registerWidget(ox,oy);      

       
        return zoomObjectif;
    }

}
