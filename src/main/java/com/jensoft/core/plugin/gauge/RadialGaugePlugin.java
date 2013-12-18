/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge;

import java.awt.Graphics2D;

import com.jensoft.core.gauge.core.RadialGauge;
import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Dithering;
import com.jensoft.core.graphics.Interpolation;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

public class RadialGaugePlugin extends AbstractPlugin {

    private RadialGauge gauge;

    public RadialGaugePlugin(RadialGauge gauge) {
        this.gauge = gauge;
//        setInterpolation(Interpolation.Bicubic);
//        setAlphaInterpolation(AlphaInterpolation.Quality);
//        setAntialiasing(Antialiasing.On);
//        setFractionalMetrics(Fractional.On);
//        setTextAntialising(TextAntialiasing.On);
//        setDithering(Dithering.On);
        
        setInterpolation(Interpolation.Bicubic);
        System.out.println("ok");
        setAntialiasing(Antialiasing.On);
        setTextAntialising(TextAntialiasing.On);
        setDithering(Dithering.On);
    }

    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {

        if (windowPart != WindowPart.Device) {
            return;
        }

        gauge.setWindow2D(getWindow2D());

        if (gauge.getEnvelop() != null) {
            gauge.getEnvelop().paintGauge(g2d, gauge);
        }

        if (gauge.getBackground() != null) {
            gauge.getBackground().paintGauge(g2d, gauge);
        }

        if (gauge.getBody() != null) {
            gauge.getBody().paintGauge(g2d, gauge);
        }

//        if (gauge.getNeedle() != null) {
//            gauge.getNeedle().paintGauge(g2d, gauge);
//        }

        if (gauge.getEffect() != null) {
            gauge.getEffect().paintGauge(g2d, gauge);
        }

        if (gauge.getConstructor() != null) {
            gauge.getConstructor().paintGauge(g2d, gauge);
        }

    }

}
