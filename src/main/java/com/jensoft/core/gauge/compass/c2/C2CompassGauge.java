/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.compass.c2;

import java.awt.Color;
import java.util.Random;

import com.jensoft.core.gauge.background.TextureBackground;
import com.jensoft.core.gauge.core.RadialGauge;
import com.jensoft.core.gauge.envelop.CiseroEnvelop;
import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.JennyPalette;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.palette.TexturePalette;

public class C2CompassGauge extends RadialGauge {

    public C2CompassGauge() {
        super(0, 0, 110);

       

        CiseroEnvelop e1 = new CiseroEnvelop();
        setEnvelop(e1);

        TextureBackground textureBackground = new TextureBackground(TexturePalette.getT3());
        setBackground(textureBackground);

        C2Body b1 = new C2Body();
        setBody(b1);

        C2Glass glass = new C2Glass();
        setEffect(glass);

    
       

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
