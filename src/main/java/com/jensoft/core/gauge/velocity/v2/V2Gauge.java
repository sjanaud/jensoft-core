/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.velocity.v2;

import java.util.Random;

import com.jensoft.core.gauge.background.RoundGradientBackground;
import com.jensoft.core.gauge.core.RadialGauge;
import com.jensoft.core.gauge.envelop.CiseroEnvelop;
import com.jensoft.core.gauge.glass.Glass1;

public class V2Gauge extends RadialGauge {

    public V2Gauge() {
        super(0, 0, 100);

        CiseroEnvelop e1 = new CiseroEnvelop();
        setEnvelop(e1);

        RoundGradientBackground bg1 = new RoundGradientBackground();
        setBackground(bg1);

        V2Body b1 = new V2Body();
        setBody(b1);

        Glass1 effect = new Glass1();
        setEffect(effect);

        

        V2Constructor constructor = new V2Constructor();
        setConstructor(constructor);

        // Thread demo = new Thread(needleAnimator,"needle animator");
        // demo.start();

       // needle.setCurentValue(6);

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
