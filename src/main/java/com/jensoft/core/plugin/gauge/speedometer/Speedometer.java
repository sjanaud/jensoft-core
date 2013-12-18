/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.speedometer;

import java.util.Random;

import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.env.CiseroEnvelop;
import com.jensoft.core.plugin.gauge.core.glass.Glass1;

public class Speedometer extends RadialGauge {

    public Speedometer() {
        super(0, 0, 90);

        CiseroEnvelop e1 = new CiseroEnvelop();
        setEnvelop(e1);

        SpeedometerBody b1 = new SpeedometerBody();
        setBody(b1);

        Glass1 effect = new Glass1();
        setEffect(effect);

       
        SpeedometerLabel v1c = new SpeedometerLabel();
        setConstructor(v1c);
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
