/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.inflater.translate;

import java.awt.Color;

import org.w3c.dom.Element;

import com.jensoft.core.palette.Alpha;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.plugin.translate.TranslateCompassWidget;
import com.jensoft.core.plugin.translate.TranslateDefaultDeviceContext;
import com.jensoft.core.plugin.translate.TranslatePlugin;
import com.jensoft.core.plugin.translate.TranslateX;
import com.jensoft.core.plugin.translate.TranslateY;
import com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.inflater.X2DInflater;
/**
 * <code>TranslateInflater</code>
 * 
 * @author Sebastien Janaud
 */
@X2DInflater(xsi="TranslatePlugin")
public class TranslateInflater extends AbstractX2DPluginInflater<TranslatePlugin> {

    /**
     * create translate inflater
     */
    public TranslateInflater() {
        setPlugin(new TranslatePlugin());
        setXSIType("TranslatePlugin");
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.jet.inflater.AbstractPluginInflater#inflate(org
     * .w3c.dom.Element)
     */
    @Override
    public void inflate(Element pluginElement) {
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
        setPlugin(translate);
    }

  
}
