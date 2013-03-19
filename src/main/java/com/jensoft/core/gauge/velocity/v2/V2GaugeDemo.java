/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.velocity.v2;

import com.jensoft.core.demo.nature.JenSoftAPIDemo;
import com.jensoft.core.demo.ui.ViewFrameUI;
import com.jensoft.core.gauge.RadialGaugePlugin;
import com.jensoft.core.plugin.zoom.wheel.ZoomWheelPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;

@JenSoftAPIDemo
public class V2GaugeDemo extends View2D {

    private static final long serialVersionUID = 156889765687899L;

   

    public V2GaugeDemo() {
    super();

     

        setPlaceHolderAxisSouth(80);
        setPlaceHolderAxisWest(120);
        setPlaceHolderAxisEast(120);

        //view.setDeviceBackground(Color.WHITE);

        Window2D w2d = new Window2D.Linear(-3000, 3000, -2500, 2500);
        w2d.setName("velocity gauge window");

        V2Gauge gauge = new V2Gauge();
        RadialGaugePlugin layout = new RadialGaugePlugin(gauge);

        w2d.registerPlugin(layout);
        w2d.registerPlugin(new ZoomWheelPlugin());
        registerWindow2D(w2d);
        
        Thread t = new Thread(){
            public void run() {
                try {
                    Thread.sleep(600);
                    repaintDevice();
                }
                catch (InterruptedException e) {
                   
                }
            };
        };
        t.start();

        
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        final ViewFrameUI demoFrame = new ViewFrameUI(new V2GaugeDemo());       
    }

}
