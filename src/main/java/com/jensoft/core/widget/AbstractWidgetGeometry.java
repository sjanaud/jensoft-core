/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.widget;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>AbstractWidgetGeometry</code>
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractWidgetGeometry {

    /** sensible shape on this widget */
    private List<Shape> sensibleShapes = new ArrayList<Shape>();

    /**
     * create abstract geometry
     */
    public AbstractWidgetGeometry() {
    }

    /**
     * solve geometry for the specified widget bound
     * 
     * @param bound2D
     */
    protected abstract void solveGeometry(Rectangle2D widgetBound2D);

    /**
     * return true if the point defines by x and y coordinates is contains in
     * one of the sensible shape
     * 
     * @param x
     *            the x point coordinate
     * @param y
     *            the y point coordinate
     * @return
     */
    public boolean interceptSensibleShape(int x, int y) {
        for (Shape sensibleShape : sensibleShapes) {
            if (sensibleShape.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the sensibleShapes
     */
    public List<Shape> getSensibleShapes() {
        return sensibleShapes;
    }

    /**
     * clear sensible shape
     */
    public void clearSensibleShape() {
        sensibleShapes.clear();
    }

    /**
     * @param sensibleShapes
     *            the sensibleShapes to set
     */
    public void setSensibleShapes(List<Shape> sensibleShapes) {
        this.sensibleShapes = sensibleShapes;
    }

    /**
     * add sensible shape
     * 
     * @param sensibleShape
     *            the sensible shape to add
     */
    public void addSensibleShape(Shape sensibleShape) {
        sensibleShapes.add(sensibleShape);
    }

    /**
     * remove sensible shape
     * 
     * @param sensibleShape
     *            the sensible shape to remove
     */
    public void removeSensibleShape(Shape sensibleShape) {
        sensibleShapes.remove(sensibleShape);
    }
}
