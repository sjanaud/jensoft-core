/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.drawable.basic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.QuadCurve2D;

import org.jensoft.core.drawable.Drawable;

/**
 * QStar is define by the center of qstar, the external radius, the internal
 * radius and the branches count.
 */
public class QStar implements Drawable {

    private double centerX;
    private double centerY;
    private double internalRadius;
    private double externalRadius;
    private double branchCount;

    double startAngleDegree = 60;

    public double getStartAngleDegree() {
        return startAngleDegree;
    }

    public void setStartAngleDegree(double startAngleDegree) {
        this.startAngleDegree = startAngleDegree;
    }

    public QStar(double x, double y, double internalRadius,
            double externalRadius, double branchCount) {
        super();
        centerX = x;
        centerY = y;
        this.internalRadius = internalRadius;
        this.externalRadius = externalRadius;
        this.branchCount = branchCount;
    }

    public QStar(double internalRadius, double externalRadius,
            double branchCount) {
        super();

        this.internalRadius = internalRadius;
        this.externalRadius = externalRadius;
        this.branchCount = branchCount;
    }

    public double getX() {
        return centerX;
    }

    public void setX(double x) {
        centerX = x;
    }

    public double getY() {
        return centerY;
    }

    public void setY(double y) {
        centerY = y;
    }

    public double getInternalRadius() {
        return internalRadius;
    }

    public void setInternalRadius(double internalRadius) {
        this.internalRadius = internalRadius;
    }

    public double getExternalRadius() {
        return externalRadius;
    }

    public void setExternalRadius(double externalRadius) {
        this.externalRadius = externalRadius;
    }

    public double getBranchCount() {
        return branchCount;
    }

    public void setBranchCount(double branchCount) {
        this.branchCount = branchCount;
    }

    private Shape starMorphe;

    public void createMorphe() {

        GeneralPath starMorphePath = new GeneralPath();

        double angleStar = 360d / branchCount;

        for (int i = 0; i < branchCount; i++) {

            double pXExternal = centerX
                    + externalRadius
                    * Math.cos(Math.toRadians(startAngleDegree + angleStar * i));
            double pYExternal = centerY
                    - externalRadius
                    * Math.sin(Math.toRadians(startAngleDegree + angleStar * i));

            double pXInternal = centerX
                    + internalRadius
                    * Math.cos(Math.toRadians(startAngleDegree + angleStar * i
                            + angleStar / 2d));
            double pYInternal = centerY
                    - internalRadius
                    * Math.sin(Math.toRadians(startAngleDegree + angleStar * i
                            + angleStar / 2d));

            double pXExternal2 = centerX
                    + externalRadius
                    * Math.cos(Math.toRadians(startAngleDegree + angleStar
                            * (i + 1)));
            double pYExternal2 = centerY
                    - externalRadius
                    * Math.sin(Math.toRadians(startAngleDegree + angleStar
                            * (i + 1)));

            QuadCurve2D l = new QuadCurve2D.Double(pXExternal, pYExternal,
                                                   pXInternal, pYInternal, pXExternal2, pYExternal2);
            starMorphePath.append(l, true);

        }

        starMorphePath.closePath();
        starMorphe = starMorphePath;

    }

    public Shape getStarMorphe() {
        createMorphe();
        return starMorphe;
    }

    @Override
    public void draw(Graphics2D g2d) {
        createMorphe();
        g2d.setColor(Color.RED);
        g2d.draw(starMorphe);

    }

}
