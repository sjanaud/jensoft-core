/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.velocity.v1;

import java.awt.Color;

import com.jensoft.core.catalog.nature.JenSoftAPIDemo;
import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.plugin.gauge.RadialGaugePlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;

@JenSoftAPIDemo
public class V1GaugeDemo extends View2D {

    private static final long serialVersionUID = 156889765687899L;

   

    public V1GaugeDemo() {
    super();

        setPlaceHolderAxisSouth(80);
        setPlaceHolderAxisWest(120);
        setPlaceHolderAxisEast(120);

        setDeviceBackground(Color.BLACK);

        Window2D w2d = new Window2D.Linear(-3000, 3000, -2500, 2500);
        w2d.setName("velocity gauge window");

        V1Gauge gauge = new V1Gauge();
        RadialGaugePlugin layout = new RadialGaugePlugin(gauge);

        w2d.registerPlugin(layout);

        registerWindow2D(w2d);

      
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        final ViewFrameUI demoFrame = new ViewFrameUI(new V1GaugeDemo());
       
    }

}
