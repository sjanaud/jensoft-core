/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pie.painter.effect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import org.jensoft.core.plugin.pie.Pie;
import org.jensoft.core.plugin.pie.PieSlice;
import org.jensoft.core.plugin.pie.Pie.PieNature;

/**
 * <code>PieSliceRadialEffect</code>
 * 
 * @author Sebastien Janaud
 */
public class PieSliceRadialEffect extends AbstractPieSliceEffect {

    
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
     * create pie radial effect
     */
    public PieSliceRadialEffect() {
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

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.painter.effect.AbstractPieSliceEffect#paintPieSliceEffect(java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie, com.jensoft.core.plugin.pie.PieSlice)
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
        if (pie.getPieNature() == PieNature.User) {
            c = pie.getHostPlugin().getProjection()
                    .userToPixel(new Point2D.Double(centerX, centerY));
        }
        if (pie.getPieNature() == PieNature.Device) {
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
            float[] dist = { 0.0f, 0.8f,1.0f };
            Color c1 = new Color(255, 255, 255, 0);            
            Color c2 = new Color(255, 255, 255, 120); 
            Color c3 = new Color(255, 255, 255, 200);

            
            Color[] cols = { c1, c2, c3};
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
