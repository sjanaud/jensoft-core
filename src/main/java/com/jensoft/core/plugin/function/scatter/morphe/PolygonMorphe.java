/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.scatter.morphe;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

/**
 * <code>PolygonMorphe</code>
 * 
 * @author sebastien janaud
 * @since 1.0
 */
public class PolygonMorphe extends ScatterMorphe {

    private double radius;
    private double faceCount;

    double startAngleDegree = 60;

    public double getStartAngleDegree() {
        return startAngleDegree;
    }

    public void setStartAngleDegree(double startAngleDegree) {
        this.startAngleDegree = startAngleDegree;
    }

    public PolygonMorphe(double radius, double faceCount) {
        super();
        this.radius = radius;
        this.faceCount = faceCount;
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

    @Override
    public Shape getMorphe() {

        GeneralPath morphe = new GeneralPath();
        double centerX = radius;
        double centerY = radius;
        double angleStar = 360d / faceCount;
        for (int i = 0; i < faceCount; i++) {

            double pXExternal = centerX
                    + radius
                    * Math.cos(Math.toRadians(startAngleDegree + angleStar * i));
            double pYExternal = centerY
                    - radius
                    * Math.sin(Math.toRadians(startAngleDegree + angleStar * i));

            double pXExternal2 = centerX
                    + radius
                    * Math.cos(Math.toRadians(startAngleDegree + angleStar
                            * (i + 1)));
            double pYExternal2 = centerY
                    - radius
                    * Math.sin(Math.toRadians(startAngleDegree + angleStar
                            * (i + 1)));

            Line2D l = new Line2D.Double(pXExternal, pYExternal, pXExternal2,
                                         pYExternal2);
            morphe.append(l, true);

        }

        morphe.closePath();
        return morphe;
    }

}
