/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.bg;

import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.BackgroundGaugePainter;

public class TextureBackground extends BackgroundGaugePainter {

    private TexturePaint texturePaint;

    public TextureBackground(TexturePaint texturePaint) {
        super();
        this.texturePaint = texturePaint;
    }

    @Override
    public void paintBackground(Graphics2D g2d, RadialGauge radialGauge) {

        if (texturePaint == null) {
            return;
        }

        double centerX = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
        double centerY = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
        int radius = getGauge().getRadius();

        Ellipse2D baseShape = new Ellipse2D.Double(centerX - radius, centerY
                - radius, 2 * radius, 2 * radius);

        g2d.setPaint(texturePaint);
        g2d.fill(baseShape);

    }

}
