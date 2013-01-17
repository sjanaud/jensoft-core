/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.linesymbol.raysymbol;

/**
 * RayEntry define a bar along the line symbol
 */
public class RayEntry {

    /** the ray start */
    private double rayStart;
    /** the ray end */
    private double rayEnd;

    /**
     * create new ray from start to end
     * 
     * @param rayStart
     * @param rayEnd
     */
    public RayEntry(double rayStart, double rayEnd) {
        super();
        this.rayStart = rayStart;
        this.rayEnd = rayEnd;
    }

    /**
     * @return the rayStart
     */
    public double getRayStart() {
        return rayStart;
    }

    /**
     * @param rayStart
     *            the rayStart to set
     */
    public void setRayStart(double rayStart) {
        this.rayStart = rayStart;
    }

    /**
     * @return the rayEnd
     */
    public double getRayEnd() {
        return rayEnd;
    }

    /**
     * @param rayEnd
     *            the rayEnd to set
     */
    public void setRayEnd(double rayEnd) {
        this.rayEnd = rayEnd;
    }

}
