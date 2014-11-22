/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.bubble.painter.effect;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import com.jensoft.core.plugin.bubble.Bubble;
import com.jensoft.core.plugin.bubble.painter.BubbleEffect;

/**
 * @author Sebastien Janaud
 */
public class BubbleEffect3 extends BubbleEffect {

    @Override
    public void paintBubble(Graphics2D g2d, Bubble bubble) {
        double centerX = bubble.getHost().getProjection()
                .userToPixel(new Point2D.Double(bubble.getX(), 0)).getX();
        double centerY = bubble.getHost().getProjection()
                .userToPixel(new Point2D.Double(0, bubble.getY())).getY();
        int radius = (int) (bubble.getRadius() - 5);

        int startAngleDegreee = 30;
        int endAngleDegree = 175;

        g2d.setStroke(new BasicStroke(0.4f));

        g2d.setColor(Color.WHITE);

        Arc2D arc2d = new Arc2D.Double(centerX - radius, centerY - radius,
                                       2 * radius, 2 * radius, startAngleDegreee, endAngleDegree
                                               - startAngleDegreee, Arc2D.OPEN);

        Point2D p1 = new Point2D.Double(centerX + radius
                * Math.cos(Math.toRadians(endAngleDegree)), centerY - radius
                * Math.sin(Math.toRadians(endAngleDegree)));

        Point2D p2 = new Point2D.Double(centerX + radius
                * Math.cos(Math.toRadians(startAngleDegreee)), centerY - radius
                * Math.sin(Math.toRadians(startAngleDegreee)));

        Point2D ctrl1 = new Point2D.Double(centerX - radius / 3, centerY
                - radius / 2);

        Point2D ctrl2 = new Point2D.Double(centerX + radius / 3, centerY);

        CubicCurve2D cubicCurve = new CubicCurve2D.Double(p1.getX(), p1.getY(),
                                                          ctrl1.getX(), ctrl1.getY(), ctrl2.getX(), ctrl2.getY(),
                                                          p2.getX(), p2.getY());

        GeneralPath path = new GeneralPath();

        path.append(arc2d, false);

        path.append(cubicCurve, true);

        path.closePath();

        Point2D pG1 = new Point2D.Double(path.getBounds().getX()
                + path.getBounds().getWidth(), path.getBounds().getY()
                + path.getBounds().getHeight() / 2);
        Point2D pG2 = new Point2D.Double(path.getBounds().getX(), path
                .getBounds().getY() + path.getBounds().getHeight() / 2);

        GradientPaint gPaint = new GradientPaint(

                                                 pG1, new Color(255, 255, 255, 120),

                                                 pG2, new Color(255, 255, 255, 30)

                );

        g2d.setPaint(gPaint);

        g2d.fill(path);

    }

}
