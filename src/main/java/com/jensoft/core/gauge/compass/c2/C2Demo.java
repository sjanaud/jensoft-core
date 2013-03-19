/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.compass.c2;

import com.jensoft.core.demo.nature.JenSoftAPIDemo;
import com.jensoft.core.demo.ui.ViewFrameUI;
import com.jensoft.core.gauge.RadialGaugePlugin;
import com.jensoft.core.plugin.translate.TranslateDefaultDeviceContext;
import com.jensoft.core.plugin.translate.TranslatePlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;

@JenSoftAPIDemo
public class C2Demo extends View2D {

    private static final long serialVersionUID = 156889765687899L;

   

    public C2Demo() {
    super();

        setPlaceHolderAxisSouth(80);
        setPlaceHolderAxisWest(120);
        setPlaceHolderAxisEast(120);

        // view.setDeviceBackground(Color.WHITE);

        Window2D w2d = new Window2D.Linear(-3000, 3000, -2500, 2500);
        w2d.setName("velocity gauge window");

        C2CompassGauge gauge = new C2CompassGauge();
        RadialGaugePlugin layout = new RadialGaugePlugin(gauge);

        w2d.registerPlugin(layout);
        
        TranslatePlugin translate = new TranslatePlugin();
        translate.registerContext(new TranslateDefaultDeviceContext());
        w2d.registerPlugin(translate);

        registerWindow2D(w2d);

       
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        final ViewFrameUI demoFrame = new ViewFrameUI(new C2Demo());
        

    }

}
