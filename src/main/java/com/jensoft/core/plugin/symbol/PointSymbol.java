/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.symbol.painter.point.AbstractPointSymbolPainter;

/**
 * PointSymbol defines a point symbol where one of coordinates is scalar other
 * is symbolic. if symbol nature is vertical, x coordinate is the symbol and y
 * coordinate is the scalar dimension (symbol inflate along y dimension, the
 * value is y value) if symbol nature is horizontal, y coordinate is the symbol
 * and x coordinate is the scalar dimension (symbol inflate along x dimension,
 * the value is x value)
 * 
 * @author Sebastien Janaud
 */
public class PointSymbol extends SymbolComponent {

    /** the point value in user system coordinate */
    private double value;

    /** transformed value in device coordinate */
    private double deviceValue;

    /** the point of this symbol */
    private Point2D devicePoint;

    /** symbol point painter */
    private List<AbstractPointSymbolPainter> pointSymbolPainter = new ArrayList<AbstractPointSymbolPainter>();

    /** sensible radius in pixel */
    private int sensibleRadius = 10;

    /** sensible shape */
    private Shape sensibleShape;

    /**
     * create a point symbol
     */
    public PointSymbol() {
    }

    /**
     * create a point symbol with the specified value
     * 
     * @param value
     *            the user value for this point
     */
    public PointSymbol(double value) {
        this.value = value;
    }

    /**
     * get the value in the user system coordinate
     * 
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * set the value in the user system coordinate
     * 
     * @param value
     *            the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    // /**
    // * a point symbol has no thickness, so this return 0
    // */
    // @Override
    // public double getThickness() {
    // return 0;
    // }

    /**
     * @return the sensibleShape
     */
    public Shape getSensibleShape() {
        return sensibleShape;
    }

    /**
     * @param sensibleShape
     *            the sensibleShape to set
     */
    public void setSensibleShape(Shape sensibleShape) {
        this.sensibleShape = sensibleShape;
    }

    /**
     * get the transformed value coordinate in the device system
     * 
     * @return the deviceValue
     */
    public double getDeviceValue() {
        return deviceValue;
    }

    /**
     * set the transformed value coordinate in the device system
     * 
     * @param deviceValue
     *            the deviceValue to set
     */
    public void setDeviceValue(double deviceValue) {
        this.deviceValue = deviceValue;
    }

    /**
     * @return the devicePoint
     */
    public Point2D getDevicePoint() {
        return devicePoint;
    }

    /**
     * @param devicePoint
     *            the devicePoint to set
     */
    public void setDevicePoint(Point2D devicePoint) {
        this.devicePoint = devicePoint;
    }

    /**
     * @return the pointSymbolPainter
     */
    public List<AbstractPointSymbolPainter> getPointSymbolPainters() {
        return pointSymbolPainter;
    }

    /**
     * @param pointSymbolPainterList
     *            the pointSymbolPainter list to set
     */
    public void setPointSymbolPainters(List<AbstractPointSymbolPainter> pointSymbolPainterList) {
        pointSymbolPainter = pointSymbolPainterList;
    }

    /**
     * @param pointSymbolPainter
     *            the pointSymbolPainter to add
     */
    public void addPointSymbolPainter(AbstractPointSymbolPainter pointSymbolPainter) {
        this.pointSymbolPainter.add(pointSymbolPainter);
    }

    /**
     * @return the sensibleRadius
     */
    public int getSensibleRadius() {
        return sensibleRadius;
    }

    /**
     * @param sensibleRadius
     *            the sensibleRadius to set
     */
    public void setSensibleRadius(int sensibleRadius) {
        this.sensibleRadius = sensibleRadius;
    }

}
