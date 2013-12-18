/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.compass.c1;

import java.awt.Color;
import java.util.Random;

import com.jensoft.core.gauge.background.TextureBackground;
import com.jensoft.core.gauge.core.RadialGauge;
import com.jensoft.core.gauge.envelop.CiseroEnvelop;
import com.jensoft.core.gauge.glass.Glass1;
import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.palette.TexturePalette;

public class C1CompassGauge extends RadialGauge {

    public C1CompassGauge() {
        super(0, 0, 120);

        
        CiseroEnvelop e1 = new CiseroEnvelop();
        setEnvelop(e1);

        TextureBackground textureBackgriund = new TextureBackground(
                                                                    TexturePalette.getT0());
        setBackground(textureBackgriund);

        C1Body b1 = new C1Body();
        setBody(b1);

        Glass1 effect = new Glass1();
        setEffect(effect);

      

        setConstructor(new C1Constructor());
        Thread demo = new Thread(needleAnimator, "needle animator");
        // demo.start();

     

    }

    private C1Needle needle;
    Random random = new Random();
    Runnable needleAnimator = new Runnable() {

        @Override
        public void run() {
            while (true) {

                int i = random.nextInt(100) + 20;
                System.out.println("set new value :" + i);
                needle.setCurentValue(i);
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
