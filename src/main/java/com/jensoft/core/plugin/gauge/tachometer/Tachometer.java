/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.tachometer;

import java.util.Random;

import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.bg.RoundGradientBackground;
import com.jensoft.core.plugin.gauge.core.env.CiseroEnvelop;
import com.jensoft.core.plugin.gauge.core.glass.GaugeGlass;

public class Tachometer extends RadialGauge {

    public Tachometer() {
        super(0, 0, 100);

        CiseroEnvelop e1 = new CiseroEnvelop();
        setEnvelop(e1);

        RoundGradientBackground bg1 = new RoundGradientBackground();
        setBackground(bg1);

        TachometerBody b1 = new TachometerBody();
        setBody(b1);

        GaugeGlass g1 = new GaugeGlass.Glass1();
        GaugeGlass g2 = new GaugeGlass.Glass2();
        GaugeGlass g3 = new GaugeGlass.Glass3();
        GaugeGlass g4 = new GaugeGlass.Glass4();
 
        
        addEffect(g1,g2,g3,g4);
        

        TachometerLabel constructor = new TachometerLabel();
        setConstructor(constructor);

        // Thread demo = new Thread(needleAnimator,"needle animator");
        // demo.start();

      

    }

   
    Random random = new Random();
    Runnable needleAnimator = new Runnable() {

        @Override
        public void run() {
            while (true) {

                int i = random.nextInt(200) + 20;
                System.out.println("set new value :" + i);
               //needle.setCurentValue(i);
                if (getWindow2D() != null) {
                    getWindow2D().getDevice2D().repaintDevice();
                }

                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    };

}
