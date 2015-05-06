/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.drawable.screw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import org.jensoft.core.drawable.Drawable;

public class Bolt implements Drawable {

    private double x;
    private double y;
    private double btrRadius;

    public Bolt(double x, double y, double btrRadius) {
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

        double btrInternal = btrRadius - btrRadius / 3;

        float fraction = (float) (btrInternal / btrRadius);
        float[] dist0 = { 0.0f, 0.8f, 0.9f, 1.0f };
        Color[] colors0 = { Color.LIGHT_GRAY, Color.LIGHT_GRAY,
                Color.LIGHT_GRAY, Color.DARK_GRAY };
        RadialGradientPaint p0 = new RadialGradientPaint(center,
                                                         (float) btrRadius, dist0, colors0, CycleMethod.NO_CYCLE

                );
        g2d.setPaint(p0);

        Point2D start0 = new Point2D.Double(center.getX() - btrRadius,
                                            center.getY());
        Point2D end0 = new Point2D.Double(center.getX() + btrRadius,
                                          center.getY());
        float[] dist00 = { 0.0f, 0.5f, 0.7f, 1.0f };
        Color[] colors00 = { Color.WHITE, Color.LIGHT_GRAY, Color.GRAY,
                Color.DARK_GRAY };
        LinearGradientPaint p00 = new LinearGradientPaint(start0, end0, dist00,
                                                          colors00);
        new Color(188, 184, 183);
        g2d.setPaint(p00);
        g2d.fill(a);
        // g2d.setColor(Color.GRAY);
        // g2d.draw(a);

        // BTR

        GeneralPath starMorphePath = new GeneralPath();
        GeneralPath c1 = new GeneralPath();
        GeneralPath c2 = new GeneralPath();
        GeneralPath c3 = new GeneralPath();
        GeneralPath c4 = new GeneralPath();
        double angleStar = 360d / 6;

        for (int i = 0; i <= 6; i++) {

            double px = x + btrInternal
                    * Math.cos(Math.toRadians(btrInternal + angleStar * i));
            double py = y - btrInternal
                    * Math.sin(Math.toRadians(btrInternal + angleStar * i));

            if (i == 0) {
                c1.moveTo(px, py);
            }
            if (i == 2) {
                c2.moveTo(px, py);
            }
            if (i == 3) {
                c3.moveTo(px, py);
            }
            if (i == 5) {
                c4.moveTo(px, py);
            }

            if (i == 0) {
                starMorphePath.moveTo(px, py);
            }

            if (i > 0) {
                starMorphePath.lineTo(px, py);

                if (i <= 2) {
                    c1.lineTo(px, py);
                }
                if (i >= 2 && i <= 3) {
                    c2.lineTo(px, py);
                }
                if (i >= 3) {
                    c3.lineTo(px, py);
                }
                if (i >= 5) {
                    c4.lineTo(px, py);
                }
            }

        }

        starMorphePath.closePath();

        float[] dist1 = { 0.0f, 0.2f, 1.0f };
        Color[] colors1 = { Color.DARK_GRAY, Color.GRAY, Color.BLACK };
        RadialGradientPaint p1 = new RadialGradientPaint(center,
                                                         (float) btrInternal, dist1, colors1, CycleMethod.NO_CYCLE

                );

        Point2D start = new Point2D.Double(center.getX(), center.getY()
                - btrRadius);
        Point2D end = new Point2D.Double(center.getX(), center.getY()
                + btrRadius);
        float[] dist = { 0.0f, 0.5f, 0.7f, 1.0f };
        Color[] colors = { Color.WHITE, Color.LIGHT_GRAY, Color.GRAY,
                Color.DARK_GRAY };
        LinearGradientPaint p2 = new LinearGradientPaint(start, end, dist,
                                                         colors);

        g2d.setStroke(new BasicStroke(1.4f, BasicStroke.CAP_ROUND,
                                      BasicStroke.JOIN_ROUND));

        g2d.setColor(Color.BLACK);
        g2d.draw(c1);

        g2d.setColor(Color.GRAY);
        g2d.draw(c2);

        g2d.setColor(Color.WHITE);
        g2d.draw(c3);

        g2d.setColor(Color.GRAY);
        g2d.draw(c4);

        g2d.setPaint(p2);
        g2d.fill(starMorphePath);

        g2d.setStroke(new BasicStroke(0.8f, BasicStroke.CAP_ROUND,
                                      BasicStroke.JOIN_ROUND));
        // g2d.setColor(Color.DARK_GRAY);
        // g2d.draw(starMorphePath);

    }

}
