/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.core2;

import java.awt.Graphics2D;
import java.util.List;

import com.jensoft.core.graphics.AlphaInterpolation;
import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Dithering;
import com.jensoft.core.graphics.Fractional;
import com.jensoft.core.graphics.Interpolation;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

public class Radial2GaugePlugin extends AbstractPlugin {

    private AbstractRadialGauge gauge;

    public Radial2GaugePlugin(AbstractRadialGauge gauge) {
        this.gauge = gauge;
        setInterpolation(Interpolation.Bicubic);
        setAlphaInterpolation(AlphaInterpolation.Quality);
        setAntialiasing(Antialiasing.On);
        setFractionalMetrics(Fractional.On);
        setTextAntialising(TextAntialiasing.On);
        setDithering(Dithering.On);
    }

    private void paintBody(View2D v2d, Graphics2D g2d, Body body) {

    }

    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {

        if (windowPart != WindowPart.Device) {
            return;
        }

        gauge.setWindow(getWindow2D());

        List<Body> bodies = gauge.getBodies();
        for (Body body : bodies) {
            paintBody(v2d, g2d, body);
        }

    }

}
