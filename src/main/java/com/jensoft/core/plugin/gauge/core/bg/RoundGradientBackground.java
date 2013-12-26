/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.bg;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.jensoft.core.plugin.gauge.core.RadialGauge;

public class RoundGradientBackground extends BackgroundGaugePainter {

    public RoundGradientBackground() {

    }

    @Override
    public void paintBackground(Graphics2D g2d, RadialGauge radialGauge) {
        double centerX = radialGauge.getWindow2D()
                .userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();
        double centerY = radialGauge.getWindow2D()
                .userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();
        int radius = radialGauge.getRadius();

        Ellipse2D baseShape = new Ellipse2D.Double(centerX - radius, centerY
                - radius, 2 * radius, 2 * radius);

        RadialGradientPaint rgp = new RadialGradientPaint(new Point2D.Double(
                                                                             centerX, centerY), 3 * radius / 4,
                                                          new float[] { 0f, 1f },
                                                          new Color[] { Color.BLACK, new Color(50, 50, 50) });

        g2d.setPaint(rgp);
        g2d.fill(baseShape);

    }

}
