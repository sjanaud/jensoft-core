/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.compass;

import java.awt.Color;
import java.util.Random;

import com.jensoft.core.drawable.text.TextPath.TextPosition;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.palette.TexturePalette;
import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.bg.TextureBackground;
import com.jensoft.core.plugin.gauge.core.env.CiseroEnvelop;
import com.jensoft.core.plugin.gauge.core.glass.GaugeGlass;
import com.jensoft.core.plugin.gauge.core.glass.GaugeGlass.GlassTextPath;

public class Compass2 extends RadialGauge {

    public Compass2() {
        super(0, 0, 110);

       

        CiseroEnvelop e1 = new CiseroEnvelop();
        setEnvelop(e1);

        Color vstart = new Color(71, 72, 76);
		Color vend = new Color(11, 12, 16);
		Color hstart =new Color(71, 72, 76);
		Color hend = new Color(11, 12, 16);
        TextureBackground textureBackground = new TextureBackground(TexturePalette.getInterlacedCarbonTextureBase(5, hstart, hend, vstart, vend));
        setBackground(textureBackground);

        CompassBody b1 = new CompassBody();
        setBody(b1);

       
       //GaugeGlass glass = new GaugeGlass.GlassCubicEffect();
       //GaugeGlass glass = new GaugeGlass.GlassLinearEffect();
       // GaugeGlass glass = new GaugeGlass.GlassRadialEffect();
       // GaugeGlass glass = new GaugeGlass.Donut2DGlass();
        GaugeGlass glass = new GaugeGlass.GlassLinearEffect();
        GaugeGlass glass2 = new GaugeGlass.GlassLabel();
        addEffect(glass,glass2);
        
        GlassTextPath glassTextPath = new GaugeGlass.GlassTextPath();
        glassTextPath.setArcDef(0, 180);
        glassTextPath.setText("Sailing API");
        glassTextPath.setOffsetLeft(0);
        glassTextPath.setOffsetRight(30);
        glassTextPath.setTextPosition(TextPosition.Left);
        glassTextPath.setTextFont(InputFonts.getSreda(12));
        glassTextPath.setShader(new float[]{0f,1f}, new Color[]{NanoChromatique.WHITE,NanoChromatique.YELLOW.brighter()});
        
        addEffect(glassTextPath);
        
       


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
