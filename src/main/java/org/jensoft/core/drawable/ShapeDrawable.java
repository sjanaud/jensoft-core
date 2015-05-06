/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

/**
 * Shape Drawable
 * 
 * @author Sebastien Janaud
 */
public class ShapeDrawable implements Drawable {

    private Shape shape;
    private Stroke defaultStroke = new BasicStroke();
    private Stroke stroke;
    private Color color;

    public ShapeDrawable() {
    }

    public ShapeDrawable(Shape shape) {
        this.shape = shape;
    }

    /**
     * @return the shape
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * @param shape
     *            the shape to set
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * @return the stroke
     */
    public Stroke getStroke() {
        return stroke;
    }

    /**
     * @param stroke
     *            the stroke to set
     */
    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color
     *            the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (shape != null && color != null) {
            g2d.setColor(color);
            if (stroke != null) {
                g2d.setStroke(stroke);
            }
            else {
                g2d.setStroke(defaultStroke);
            }
            g2d.draw(shape);
            g2d.setStroke(defaultStroke);
        }
    }

}
