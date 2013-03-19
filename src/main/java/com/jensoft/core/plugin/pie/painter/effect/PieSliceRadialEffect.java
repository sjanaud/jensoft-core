/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.effect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.Pie.PieNature;
import com.jensoft.core.plugin.pie.PieSlice;

/**
 * <code>PieEffect2Section</code>
 * 
 * @author Sebastien Janaud
 */
public class PieSliceRadialEffect extends AbstractPieSliceEffect {

    /** simple shader start color */
    private Color startColor;

    /** simple shader end color */
    private Color endColor;

    /** offset radius */
    private int offsetRadius = 0;

    /** shader colors */
    private Color[] colors;

    /** shader fractions */
    private float[] fractions;

    /** focus radius */
    private int focusRadius = 0;

    /** focus angle */
    private int focusAngle = 270;

    /**
     * create Pie Effect
     */
    public PieSliceRadialEffect() {
    }

    /**
     * create pie effect with specified parameters
     * 
     * @param startColor
     *            the start color to set
     * @param endColor
     *            the end color to set
     * @param offsetRadius
     *            the offset radius to set
     */
    public PieSliceRadialEffect(Color startColor, Color endColor, int offsetRadius) {
        if (offsetRadius < 0) {
            throw new IllegalArgumentException(
                                               "offset radius should be greater than 0");
        }
        this.startColor = startColor;
        this.endColor = endColor;
        this.offsetRadius = offsetRadius;
    }

    /**
     * create Pie Effect
     * 
     * @param startColor
     *            the start color
     * @param endColor
     *            the end color
     */
    public PieSliceRadialEffect(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }

    /**
     * create pie effect with specified parameter
     * 
     * @param offsetRadius
     *            the offset radius to set
     */
    public PieSliceRadialEffect(int offsetRadius) {
        if (offsetRadius < 0) {
            throw new IllegalArgumentException(
                                               "offset radius should be greater than 0");
        }
        this.offsetRadius = offsetRadius;
    }

    /**
     * set gradient shader parameters
     * 
     * @param fractions
     *            the step fractions to set
     * @param colors
     *            the colors step
     */
    public void setShader(float[] fractions, Color[] colors) {
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("length array does not match");
        }

        this.fractions = fractions;
        this.colors = colors;
    }

    /**
     * @return the focusRadius
     */
    public int getFocusRadius() {
        return focusRadius;
    }

    /**
     * @param focusRadius
     *            the focusRadius to set
     */
    public void setFocusRadius(int focusRadius) {
        this.focusRadius = focusRadius;
    }

    /**
     * @return the focusAngle
     */
    public int getFocusAngle() {
        return focusAngle;
    }

    /**
     * @param focusAngle
     *            the focusAngle to set
     */
    public void setFocusAngle(int focusAngle) {
        this.focusAngle = focusAngle;
    }

    /**
     * @return the startColor
     */
    public Color getStartColor() {
        return startColor;
    }

    /**
     * @param startColor
     *            the startColor to set
     */
    public void setStartColor(Color startColor) {
        this.startColor = startColor;
    }

    /**
     * @return the endColor
     */
    public Color getEndColor() {
        return endColor;
    }

    /**
     * @return the offsetRadius
     */
    public int getOffsetRadius() {
        return offsetRadius;
    }

    /**
     * @param offsetRadius
     *            the offsetRadius to set
     */
    public void setOffsetRadius(int offsetRadius) {
        if (offsetRadius < 0) {
            throw new IllegalArgumentException(
                                               "offset radius should be greater than 0");
        }
        this.offsetRadius = offsetRadius;
    }

    /**
     * @param endColor
     *            the endColor to set
     */
    public void setEndColor(Color endColor) {
        this.endColor = endColor;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.pie.painter.effect.AbstractPieSliceEffect#paintSectionEffect(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.pie.Pie, com.jensoft.sw2d.core.plugin.pie.PieSlice)
     */
    @Override
    public void paintPieSliceEffect(Graphics2D g2d, Pie pie, PieSlice slice) {

        double centerX = pie.getCenterX();
        double centerY = pie.getCenterY();
        double deltaDegree = slice.getPercent() * 360;
        double medianDegree = slice.getStartAngleDegree() + deltaDegree
                / 2;
        if (medianDegree > 360) {
            medianDegree = medianDegree - 360;
        }

        Point2D c = null;
        if (pie.getPieNature() == PieNature.PieUser) {
            c = pie.getHostPlugin().getWindow2D()
                    .userToPixel(new Point2D.Double(centerX, centerY));
        }
        if (pie.getPieNature() == PieNature.PieDevice) {
            c = new Point2D.Double(centerX, centerY);
        }

        centerX = c.getX() + slice.getDivergence()
                * Math.cos(Math.toRadians(medianDegree));
        centerY = c.getY() - slice.getDivergence()
                * Math.sin(Math.toRadians(medianDegree));

        Point2D cent = new Point2D.Double(centerX, centerY);
        double radius = pie.getRadius() - offsetRadius;

        double focusX = c.getX() + focusRadius
                * Math.cos(Math.toRadians(focusAngle));
        double focusY = c.getY() - focusRadius
                * Math.sin(Math.toRadians(focusAngle));
        Point2D focus = new Point2D.Double(focusX, focusY);

        Ellipse2D e1 = new Ellipse2D.Double(centerX - radius, centerY - radius,
                                            2 * radius, 2 * radius);

        RadialGradientPaint p;
        if (colors == null && fractions == null) {
            float[] dist = { 0.0f, 1.0f };
            Color cStart = new Color(255, 255, 255, 180);
            //Color cEnd = new Color(40, 40, 40, 70);
            Color cEnd = new Color(255, 255, 255, 20);

            if (startColor != null) {
                cStart = startColor;
            }
            if (endColor != null) {
                cEnd = endColor;
            }

            Color[] cols = { cStart, cEnd };
            p = new RadialGradientPaint(cent, (float) radius, focus, dist,
                                        cols, CycleMethod.NO_CYCLE);
        }
        else {
            p = new RadialGradientPaint(cent, (float) radius, focus, fractions,
                                        colors, CycleMethod.NO_CYCLE);
        }

        g2d.setPaint(p);
        Area a1 = new Area(e1);
        Area a2 = new Area(slice.getSlicePath());
        a2.intersect(a1);

        g2d.fill(a2);

    }
}
