/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.watch;

import java.util.Random;

import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.env.CiseroEnvelop;
import com.jensoft.core.plugin.gauge.core.glass.GaugeGlass;

public class Watch extends RadialGauge {

    public Watch() {
        super(0, 0, 90);

        CiseroEnvelop e1 = new CiseroEnvelop(4);
        setEnvelop(e1);

        WatchBody b1 = new WatchBody();
        setBody(b1);

        GaugeGlass g1 = new GaugeGlass.Glass1();
        GaugeGlass g2 = new GaugeGlass.Glass2();
        GaugeGlass g3 = new GaugeGlass.Glass3();
        GaugeGlass g4 = new GaugeGlass.Glass4();
        
        addEffect(g1,g2,g3,g4);

       
        //WatchLabel v1c = new WatchLabel();
        //setConstructor(v1c);
        // Thread demo = new Thread(needleAnimator,"needle animator");
        // demo.start();
    }

    //private V1Needle needle;
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
