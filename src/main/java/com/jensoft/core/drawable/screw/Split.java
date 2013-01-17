/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.drawable.screw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import com.jensoft.core.drawable.Drawable;

public class Split implements Drawable {

    private double flatRadius;
    private double internalRadius;
    private double x;
    private double y;
    private double angleDegree = 45;
    private double deltaDegree = 14;

    public Split(double x, double y, double flatRadius) {

        this.x = x;
        this.y = y;
        this.flatRadius = flatRadius;
    }

    public Split(double x, double y, double flatRadius, double angleDegree) {

        this.x = x;
        this.y = y;
        this.flatRadius = flatRadius;
        this.angleDegree = angleDegree;
    }

    @Override
    public void draw(Graphics2D g2d) {

        Ellipse2D bodyFlat = new Ellipse2D.Double(getX() - flatRadius, getY()
                - flatRadius, 2 * flatRadius, 2 * flatRadius);

        Area a = new Area(bodyFlat);

        Point2D center = new Point2D.Double(getX(), getY());

        float fraction = 0f;
        float[] dist0 = { fraction, 0.8f, 1.0f };
        Color[] colors0 = { Color.LIGHT_GRAY, Color.GRAY, Color.BLACK };
        RadialGradientPaint p0 = new RadialGradientPaint(center,
                                                         (float) flatRadius, dist0, colors0, CycleMethod.NO_CYCLE);

        // g2d.setColor(Color.BLACK);
        // g2d.setStroke(new BasicStroke(1f));
        // g2d.draw(a);

        g2d.setPaint(p0);
        // g2d.setColor(Color.LIGHT_GRAY);
        g2d.fill(a);

        internalRadius = flatRadius - 2;// 3*flatRadius/4;

        Arc2D arc1 = new Arc2D.Double(x - internalRadius, y - internalRadius,
                                      2 * internalRadius, 2 * internalRadius, angleDegree
                                              - deltaDegree, 2 * deltaDegree, Arc2D.OPEN);
        Arc2D arc2 = new Arc2D.Double(x - internalRadius, y - internalRadius,
                                      2 * internalRadius, 2 * internalRadius, angleDegree
                                              - deltaDegree + 180, 2 * deltaDegree, Arc2D.OPEN);

        GeneralPath path = new GeneralPath();
        path.append(arc1, true);
        path.append(arc2, true);
        path.closePath();

        double p1x = x + internalRadius
                * Math.cos(Math.toRadians(angleDegree - deltaDegree));
        double p1y = y - internalRadius
                * Math.sin(Math.toRadians(angleDegree - deltaDegree));

        double p2x = x + internalRadius
                * Math.cos(Math.toRadians(angleDegree + 180 + deltaDegree));
        double p2y = y - internalRadius
                * Math.sin(Math.toRadians(angleDegree + 180 + deltaDegree));

        double p3x = x + internalRadius
                * Math.cos(Math.toRadians(angleDegree + deltaDegree));
        double p3y = y - internalRadius
                * Math.sin(Math.toRadians(angleDegree + deltaDegree));

        double p4x = x + internalRadius
                * Math.cos(Math.toRadians(angleDegree + 180 - deltaDegree));
        double p4y = y - internalRadius
                * Math.sin(Math.toRadians(angleDegree + 180 - deltaDegree));

        double pStartX = p1x + (p2x - p1x) / 2;
        double pStartY = p1y + (p2y - p1y) / 2;

        double pEndX = p3x + (p4x - p3x) / 2;
        double pEndY = p3y + (p4y - p3y) / 2;

        Point2D start = new Point2D.Double(pStartX, pStartY);
        Point2D end = new Point2D.Double(pEndX, pEndY);
        float[] dist = { 0.0f, 0.5f, 1.0f };
        Color[] colors = { Color.DARK_GRAY, Color.WHITE, Color.DARK_GRAY };
        LinearGradientPaint p = new LinearGradientPaint(start, end, dist,
                                                        colors);
        g2d.setPaint(p);
        g2d.fill(path);
        // g2d.setColor(Color.RED);
        // g2d.draw(path);
        // g2d.draw(a);
    }

    public double getFlatRadius() {
        return flatRadius;
    }

    public void setFlatRadius(double flatRadius) {
        this.flatRadius = flatRadius;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
