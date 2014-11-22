/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.bubble.painter.effect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.plugin.bubble.Bubble;
import com.jensoft.core.plugin.bubble.painter.BubbleEffect;

/**
 * @author Sebastien Janaud
 */
public class BubbleEffect2 extends BubbleEffect {

    @Override
    public void paintBubble(Graphics2D g2d, Bubble bubble) {
        Point2D p2dUserCenter = new Point2D.Double(bubble.getX(), bubble.getY());
        Point2D p2dDeviceCenter = bubble.getHost().getProjection()
                .userToPixel(p2dUserCenter);

        int marginX = 10;
        int marginY = 2;

        double radiusX = bubble.getRadius() - marginX;
        double radiusY = bubble.getRadius() - marginY;
        Ellipse2D bubbleShape = new Ellipse2D.Double(p2dDeviceCenter.getX()
                - radiusX, p2dDeviceCenter.getY() - radiusY, 2 * radiusX,
                                                     2 * radiusY);

        Rectangle2D boun2D = bubbleShape.getBounds2D();
        Point2D start = new Point2D.Double(boun2D.getX(), boun2D.getY());
        Point2D end = new Point2D.Double(boun2D.getX(), boun2D.getY()
                + boun2D.getHeight());

        float[] dist = { 0.0f, 0.2f, 0.8f, 1.0f };
        Color[] colors = { new Color(255, 255, 255, 200),
                new Color(255, 255, 255, 0), new Color(40, 40, 40, 0),
                new Color(40, 40, 40, 150) };

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist,
                                                        colors);

        g2d.setPaint(p);

        g2d.fill(bubbleShape);
    }

}
