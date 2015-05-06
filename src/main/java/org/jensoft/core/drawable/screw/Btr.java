/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.drawable.screw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import org.jensoft.core.drawable.Drawable;

public class Btr implements Drawable {

    private double x;
    private double y;
    private double btrRadius;

    public Btr(double x, double y, double btrRadius) {
        this.x = x;
        this.y = y;
        this.btrRadius = btrRadius;
    }

    @Override
    public void draw(Graphics2D g2d) {

        // BASE
        Ellipse2D bodyBTR = new Ellipse2D.Double(x - btrRadius, y - btrRadius,
                                                 2 * btrRadius, 2 * btrRadius);

        Area a = new Area(bodyBTR);

        Point2D center = new Point2D.Double(x, y);

        double btrInternal = btrRadius - 3;

        float fraction = (float) (btrInternal / btrRadius);
        float[] dist0 = { 0.5f, 1.0f };
        Color[] colors0 = { Color.WHITE, Color.BLACK };
        RadialGradientPaint p0 = new RadialGradientPaint(center,
                                                         (float) btrRadius, dist0, colors0, CycleMethod.NO_CYCLE

                );
        g2d.setPaint(p0);
        g2d.fill(a);

        // BTR

        GeneralPath starMorphePath = new GeneralPath();
        double angleStar = 360d / 6;

        for (int i = 0; i < 6; i++) {

            double px = x + btrInternal
                    * Math.cos(Math.toRadians(btrInternal + angleStar * i));
            double py = y - btrInternal
                    * Math.sin(Math.toRadians(btrInternal + angleStar * i));

            if (i == 0) {
                starMorphePath.moveTo(px, py);
            }
            else {
                starMorphePath.lineTo(px, py);
            }

        }

        starMorphePath.closePath();

        float[] dist1 = { 0.0f, 0.2f, 1.0f };
        Color[] colors1 = { Color.DARK_GRAY, Color.GRAY, Color.BLACK };
        RadialGradientPaint p1 = new RadialGradientPaint(center,
                                                         (float) btrInternal, dist1, colors1, CycleMethod.NO_CYCLE

                );
        g2d.setPaint(p1);
        g2d.fill(starMorphePath);

    }

}
