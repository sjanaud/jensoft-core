/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.zoom;

import org.jensoft.core.plugin.zoom.lens.LensDefaultDeviceContext;
import org.jensoft.core.plugin.zoom.lens.LensX;
import org.jensoft.core.plugin.zoom.lens.LensY;
import org.jensoft.core.plugin.zoom.lens.ZoomLensPlugin;
import org.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;

/**
 * <code>ZoomObjectifInflater</code>
 * 
 * @author Sebastien Janaud
 */
@X2DBinding(xsi="ZoomObjectifPlugin",plugin=ZoomLensPlugin.class)
public class ZoomObjectifInflater extends AbstractX2DPluginInflater<ZoomLensPlugin> {

	
    
    /* (non-Javadoc)
     * @see org.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public ZoomLensPlugin inflate(Element pluginElement) {

        ZoomLensPlugin zoomObjectif = new ZoomLensPlugin();
        
        zoomObjectif.registerContext(new LensDefaultDeviceContext());
        
        LensX ox = new LensX();
        LensY oy = new LensY();
        zoomObjectif.registerWidget(ox,oy);      

       
        return zoomObjectif;
    }

}
