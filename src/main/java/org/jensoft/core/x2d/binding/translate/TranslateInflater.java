/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.translate;

import java.awt.Color;

import org.jensoft.core.palette.color.Alpha;
import org.jensoft.core.palette.color.RosePalette;
import org.jensoft.core.plugin.translate.TranslateCompassWidget;
import org.jensoft.core.plugin.translate.TranslateDefaultDeviceContext;
import org.jensoft.core.plugin.translate.TranslatePlugin;
import org.jensoft.core.plugin.translate.TranslateX;
import org.jensoft.core.plugin.translate.TranslateY;
import org.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;
/**
 * <code>TranslateInflater</code>
 * 
 * @author Sebastien Janaud
 */
@X2DBinding(xsi="TranslatePlugin",plugin=TranslatePlugin.class)
public class TranslateInflater extends AbstractX2DPluginInflater<TranslatePlugin> {

    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public TranslatePlugin inflate(Element pluginElement) {
        TranslatePlugin translate = new TranslatePlugin();
        translate.registerContext(new TranslateDefaultDeviceContext());
        
        //compass widget
        TranslateCompassWidget compass = new TranslateCompassWidget();      
        compass.setRingFillColor(new Alpha(RosePalette.EMERALD, 150));
        compass.setRingDrawColor(Color.WHITE);
        compass.setRingNeedleFillColor(new Alpha(RosePalette.EMERALD, 150));
        compass.setRingNeedleDrawColor(Color.WHITE);
        
        // tx and ty widget
        TranslateX tx = new TranslateX();
        TranslateY ty = new TranslateY();       
        
        translate.registerWidget(compass,tx,ty);
       
        
        return translate;
    }

  
}
