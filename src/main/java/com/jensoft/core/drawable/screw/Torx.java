/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.drawable.screw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.jensoft.core.drawable.basic.Star;

public class Torx extends Star {

    double torxRadius;

    public Torx(double x, double y, double radius) {

        super(x, y, 2 * radius / 3 - 2, radius - 2, 6);
        torxRadius = radius;
    }

    @Override
    public void draw(Graphics2D g2d) {
        createMorphe();

        Ellipse2D bodyTorx = new Ellipse2D.Double(getX() - torxRadius, getY()
                - torxRadius, 2 * torxRadius, 2 * torxRadius);

        Shape sStar = getStarMorphe();

        Area a = new Area(bodyTorx);
        Area b = new Area(sStar);

        a.subtract(b);
        Point2D center = new Point2D.Double(getX(), getY());
        float radius0 = (float) torxRadius;

        float fraction = (float) (getInternalRadius() / torxRadius);
        float[] dist0 = { fraction, 1.0f };
        Color[] colors0 = { Color.WHITE, Color.BLACK };
        RadialGradientPaint p0 = new RadialGradientPaint(center, radius0,
                                                         dist0, colors0, CycleMethod.NO_CYCLE

                );
        g2d.setPaint(p0);
        g2d.fill(a);

        float radius = (float) getExternalRadius();

        float[] dist = { 0.0f, 0.2f, 1.0f };
        Color[] colors = { Color.DARK_GRAY, Color.GRAY, Color.BLACK };

        RadialGradientPaint p = new RadialGradientPaint(center, radius, dist,
                                                        colors, CycleMethod.NO_CYCLE

                );
        g2d.setPaint(p);
        g2d.fill(b);

        // g2d.setColor(Color.DARK_GRAY.darker());
        // g2d.draw(bodyTorx);

    }

}
