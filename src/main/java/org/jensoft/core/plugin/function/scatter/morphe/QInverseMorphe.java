/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.scatter.morphe;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.QuadCurve2D;

/**
 * <code>QInverseMorphe</code>
 * 
 * @author sebastien janaud
 * @since 1.0
 */
public class QInverseMorphe extends ScatterMorphe {

    private double externalRadius;
    private double branchCount;

    double startAngleDegree = 90;

    public double getStartAngleDegree() {
        return startAngleDegree;
    }

    public void setStartAngleDegree(double startAngleDegree) {
        this.startAngleDegree = startAngleDegree;
    }

    public QInverseMorphe(double externalRadius, double branchCount) {
        super();
        this.externalRadius = externalRadius;
        this.branchCount = branchCount;
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

    @Override
    public Shape getMorphe() {

        GeneralPath morphe = new GeneralPath();
        double angleStar = 360d / branchCount;
        double centerX = externalRadius;
        double centerY = externalRadius;
        for (int i = 0; i < branchCount; i++) {

            double pXExternal = centerX
                    + externalRadius
                    * Math.cos(Math.toRadians(startAngleDegree + angleStar * i));
            double pYExternal = centerY
                    - externalRadius
                    * Math.sin(Math.toRadians(startAngleDegree + angleStar * i));

            double pXInternal = centerX
                    + externalRadius
                    * Math.cos(Math.toRadians(startAngleDegree + angleStar * i
                            + angleStar / 2d));
            double pYInternal = centerY
                    - externalRadius
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
            morphe.append(l, true);

        }
        morphe.closePath();
        return morphe;
    }

}
