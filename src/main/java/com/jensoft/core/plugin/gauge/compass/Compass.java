/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.compass;

import java.util.Random;

import com.jensoft.core.palette.TexturePalette;
import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.bg.TextureBackground;
import com.jensoft.core.plugin.gauge.core.env.CiseroEnvelop;
import com.jensoft.core.plugin.gauge.core.glass.GaugeGlass;

public class Compass extends RadialGauge {

    public Compass() {
        super(0, 0, 110);

       

        CiseroEnvelop e1 = new CiseroEnvelop();
        setEnvelop(e1);

        TextureBackground textureBackground = new TextureBackground(TexturePalette.getPerforatedCircleSurface());
        setBackground(textureBackground);

        CompassBody b1 = new CompassBody();
        setBody(b1);

       
       //GaugeGlass glass = new GaugeGlass.GlassCubicEffect();
       //GaugeGlass glass = new GaugeGlass.GlassLinearEffect();
       // GaugeGlass glass = new GaugeGlass.GlassRadialEffect();
       // GaugeGlass glass = new GaugeGlass.Donut2DGlass();
        GaugeGlass glass = new GaugeGlass.GlassLabel();
        
        addEffect(glass);
        
       


        CompassLabel constructor = new CompassLabel();
       //setConstructor(constructor);

    }

   
    Random random = new Random();
    Runnable needleAnimator = new Runnable() {

        @Override
        public void run() {
            while (true) {

                int i = random.nextInt(200) + 20;
                System.out.println("set new value :" + i);
               // needle.setCurentValue(i);
                if (getWindow2D() != null) {
                    getWindow2D().getDevice2D().repaintDevice();
                }

                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    };

}
