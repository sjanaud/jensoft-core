/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.drawable.basic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

import com.jensoft.core.drawable.Drawable;

/**
 * Polygon is define by the center of polygon, the external radius and the
 * branches count.
 */
public class RegularPolygon implements Drawable {

    private double x;
    private double y;

    private double radius;
    private double faceCount;

    double startAngleDegree = 60;

    public double getStartAngleDegree() {
        return startAngleDegree;
    }

    public void setStartAngleDegree(double startAngleDegree) {
        this.startAngleDegree = startAngleDegree;
    }

    public RegularPolygon(double x, double y, double radius, double faceCount) {
        super();
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.faceCount = faceCount;
    }

    public RegularPolygon(double radius, double faceCount) {
        super();
        this.radius = radius;
        this.faceCount = faceCount;
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

    public double getExternalRadius() {
        return radius;
    }

    public void setExternalRadius(double externalRadius) {
        radius = externalRadius;
    }

    public double getBranchCount() {
        return faceCount;
    }

    public void setBranchCount(double branchCount) {
        faceCount = branchCount;
    }

    private Shape polygonMorphe;

    public void createMorphe() {

        GeneralPath starMorphePath = new GeneralPath();

        double angleStar = 360d / faceCount;
        for (int i = 0; i < faceCount; i++) {

            double pXExternal = x
                    + radius
                    * Math.cos(Math.toRadians(startAngleDegree + angleStar * i));
            double pYExternal = y
                    - radius
                    * Math.sin(Math.toRadians(startAngleDegree + angleStar * i));

            double pXExternal2 = x
                    + radius
                    * Math.cos(Math.toRadians(startAngleDegree + angleStar
                            * (i + 1)));
            double pYExternal2 = y
                    - radius
                    * Math.sin(Math.toRadians(startAngleDegree + angleStar
                            * (i + 1)));

            Line2D l = new Line2D.Double(pXExternal, pYExternal, pXExternal2,
                                         pYExternal2);
            starMorphePath.append(l, true);

        }

        starMorphePath.closePath();
        polygonMorphe = starMorphePath;

    }

    public Shape getStarMorphe() {
        createMorphe();
        return polygonMorphe;
    }

    @Override
    public void draw(Graphics2D g2d) {
        createMorphe();
        g2d.setColor(Color.RED);
        g2d.draw(polygonMorphe);

    }

}
