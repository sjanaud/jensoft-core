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
 * <code>StarMorphe</code>
 * 
 * @author sebastien janaud
 * @since 1.0
 */
public class StarMorphe extends ScatterMorphe {

    private int internalRadius;
    private int externalRadius;
    private int branchCount;

    double startAngleDegree = 60;

    public double getStartAngleDegree() {
        return startAngleDegree;
    }

    public void setStartAngleDegree(double startAngleDegree) {
        this.startAngleDegree = startAngleDegree;
    }

    public StarMorphe(int internalRadius, int externalRadius, int branchCount) {
        super();
        this.internalRadius = internalRadius;
        this.externalRadius = externalRadius;
        this.branchCount = branchCount;
    }

    public double getInternalRadius() {
        return internalRadius;
    }

    public void setInternalRadius(int internalRadius) {
        this.internalRadius = internalRadius;
    }

    public double getExternalRadius() {
        return externalRadius;
    }

    public void setExternalRadius(int externalRadius) {
        this.externalRadius = externalRadius;
    }

    public double getBranchCount() {
        return branchCount;
    }

    public void setBranchCount(int branchCount) {
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
                    + internalRadius
                    * Math.cos(Math.toRadians(startAngleDegree + angleStar * i
                            + angleStar / 2d));
            double pYInternal = centerY
                    - internalRadius
                    * Math.sin(Math.toRadians(startAngleDegree + angleStar * i
                            + angleStar / 2d));

            Line2D l = new Line2D.Double(pXExternal, pYExternal, pXInternal,
                                         pYInternal);
            morphe.append(l, true);

        }

        morphe.closePath();
        return morphe;

    }

}
