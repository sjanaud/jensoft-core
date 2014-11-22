/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.morphe;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Star is define by the center of star, the external radius, the internal
 * radius and the branches count.
 */
public class Star extends Primitive {

    private double x;
    private double y;
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

    public Star(double x, double y, double internalRadius,
            double externalRadius, double branchCount) {
        super();
        this.x = x;
        this.y = y;
        this.internalRadius = internalRadius;
        this.externalRadius = externalRadius;
        this.branchCount = branchCount;
    }

    public Star(double internalRadius, double externalRadius, double branchCount) {
        super();
        this.internalRadius = internalRadius;
        this.externalRadius = externalRadius;
        this.branchCount = branchCount;
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

        if (getNature() == PrimitiveNature.DEVICE) {
            for (int i = 0; i < branchCount; i++) {

                double pXExternal = x
                        + externalRadius
                        * Math.cos(Math.toRadians(startAngleDegree + angleStar
                                * i));
                double pYExternal = y
                        - externalRadius
                        * Math.sin(Math.toRadians(startAngleDegree + angleStar
                                * i));

                double pXInternal = x
                        + internalRadius
                        * Math.cos(Math.toRadians(startAngleDegree + angleStar
                                * i + angleStar / 2d));
                double pYInternal = y
                        - internalRadius
                        * Math.sin(Math.toRadians(startAngleDegree + angleStar
                                * i + angleStar / 2d));

                Line2D l = new Line2D.Double(pXExternal, pYExternal,
                                             pXInternal, pYInternal);
                starMorphePath.append(l, true);

            }
        }
        else if (getNature() == PrimitiveNature.USER) {
            for (int i = 0; i < branchCount; i++) {

                Point2D u = getHost().getProjection().userToPixel(
                                                                new Point2D.Double(x, y));

                double pXExternal = u.getX()
                        + externalRadius
                        * Math.cos(Math.toRadians(startAngleDegree + angleStar
                                * i));
                double pYExternal = u.getY()
                        - externalRadius
                        * Math.sin(Math.toRadians(startAngleDegree + angleStar
                                * i));

                double pXInternal = u.getX()
                        + internalRadius
                        * Math.cos(Math.toRadians(startAngleDegree + angleStar
                                * i + angleStar / 2d));
                double pYInternal = u.getY()
                        - internalRadius
                        * Math.sin(Math.toRadians(startAngleDegree + angleStar
                                * i + angleStar / 2d));

                Line2D l = new Line2D.Double(pXExternal, pYExternal,
                                             pXInternal, pYInternal);
                starMorphePath.append(l, true);

            }
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
