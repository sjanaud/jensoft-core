/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.projection;

/**
 * defines a window bound
 * 
 * @author Sebastien Janaud
 */
public class ProjectionBound {

    /** minimum x */
    private double minX;
    /** maximum x */
    private double maxX;
    /** minimum y */
    private double minY;
    /** maximum y */
    private double maxY;

    /**
     * create window bound with specified bound limits
     * 
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     */
    public ProjectionBound(double minX, double maxX, double minY, double maxY) {
        if (minX > maxX) {
            throw new IllegalArgumentException(
                                               "maximum x should be greater or equal than minimum x");
        }
        if (minY > maxY) {
            throw new IllegalArgumentException(
                                               "maximum y should be greater or equal than minimum y");
        }
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    /**
     * get minimum x value
     */
    public double getMinX() {
        return minX;
    }

    /**
     * get maximum x value
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * get minimum y value
     */
    public double getMinY() {
        return minY;
    }

    /**
     * get maximum y value
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * get bound with
     * 
     * @return bound width
     */
    public double getWidth() {
        return maxX - minX;
    }

    /**
     * get bound height
     * 
     * @return bound height
     */
    public double getHeight() {
        return maxY - minY;
    }

}
