/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.grid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

/**
 * <code>Grid</code>
 * 
 * @author sebastien janaud
 */
public class Grid {

    /** grid orientation */
    private GridOrientation gridOrientation;

    /** grid value in user coordinate */
    private double gridUserValue;

    /** grid value in device coordinate */
    private double gridDeviceValue;

    /** grid stroke */
    private Stroke gridStroke;

    /** grid default stroke */
    private Stroke GRID_STROK_DEFAULT = new BasicStroke();

    /** grid color */
    private Color gridColor;

    /** annotation label */
    private String annotation;

    /** fraction for annotation */
    private float annotationFraction = 1 / 10f;

    /**
     * define grid orientation, Vertical or Horizontal
     */
    public enum GridOrientation {
        Vertical("vertical"), Horizontal("horizontal");

        private String gridOrientation;

        private GridOrientation(String orientation) {
            gridOrientation = orientation;
        }

        /**
         * @return the gridOrientation
         */
        public String getGridOrientation() {
            return gridOrientation;
        }

        /**
         * parse the grid orientation
         * @param orientation representation of orientation
         * @return grid orientation
         */
        public static GridOrientation parse(String orientation) {
            if (Vertical.getGridOrientation().equalsIgnoreCase(orientation)) {
                return Vertical;
            }
            if (Horizontal.getGridOrientation().equalsIgnoreCase(orientation)) {
                return Horizontal;
            }
            return null;
        }

    }

    /**
     * create empty grid
     */
    public Grid() {
    }

    /**
     * create grid with specified orientation
     * 
     * @param gridOrientation
     */
    public Grid(GridOrientation gridOrientation) {
        this.gridOrientation = gridOrientation;
    }

    /**
     * get fraction for annotation
     * 
     * @return annotation fraction
     */
    public float getAnnotationFraction() {
        return annotationFraction;
    }

    /**
     * set fraction for annotation
     * 
     * @param annotationFraction
     *            the fraction annotation to set
     */
    public void setAnnotationFraction(float annotationFraction) {
        this.annotationFraction = annotationFraction;
    }

    /**
     * @return the gridOrientation
     */
    public GridOrientation getGridOrientation() {
        return gridOrientation;
    }

    /**
     * @param gridOrientation
     *            the gridOrientation to set
     */
    public void setGridOrientation(GridOrientation gridOrientation) {
        this.gridOrientation = gridOrientation;
    }

    /**
     * @return the gridUserValue
     */
    public double getGridUserValue() {
        return gridUserValue;
    }

    /**
     * @param gridUserValue
     *            the gridUserValue to set
     */
    public void setGridUserValue(double gridUserValue) {
        this.gridUserValue = gridUserValue;
    }

    /**
     * @return the gridValue
     */
    public double getGridDeviceValue() {
        return gridDeviceValue;
    }

    /**
     * @param gridValue
     *            the gridValue to set
     */
    public void setGridDeviceValue(double gridValue) {
        gridDeviceValue = gridValue;
    }

    /**
     * @return the gridStroke
     */
    public Stroke getGridStroke() {
        return gridStroke;
    }

    /**
     * @return the gridColor
     */
    public Color getGridColor() {
        return gridColor;
    }

    /**
     * get grid stroke
     * 
     * @return grid stroke
     */
    public Stroke getStroke() {
        if (gridStroke == null) {
            return GRID_STROK_DEFAULT;
        }
        else {
            return gridStroke;
        }
    }

    /**
     * set grid stroke
     * 
     * @param stroke
     *            the grid stroke to set
     */
    public void setGridStroke(Stroke stroke) {
        gridStroke = stroke;
    }

    /**
     * set grid color
     * 
     * @param gridColor
     *            the color to set
     */
    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }

    /**
     * get annotation
     * 
     * @return annotation
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * set annotation
     * 
     * @param annotation
     *            the annotation to set
     */
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Grid other = (Grid) obj;
        if (gridOrientation != other.gridOrientation) {
            return false;
        }
        if (Double.doubleToLongBits(gridDeviceValue) != Double
                .doubleToLongBits(other.gridDeviceValue)) {
            return false;
        }
        return true;
    }

}
