/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.bubble.painter.fill;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;

import org.jensoft.core.plugin.bubble.Bubble;
import org.jensoft.core.plugin.bubble.painter.BubbleFill;

/**
 * BubbleFill1
 * 
 * @see BubbleFill
 * @author Sebastien Janaud
 */
public class BubbleFill1 extends BubbleFill {

    /** start color */
    private Color startColor;

    /** end color */
    private Color endColor;

    /** incidence angle degree */
    private int incidenceAngleDegree = 90;

    /** shade fractions */
    private float[] shadeFractions;

    /** shade colors */
    private Color[] shadeColors;

    /**
     * create shader fill with specified colors
     * 
     * @param startColor
     *            the shade start color
     * @param endColor
     *            the shade end color
     */
    public BubbleFill1(Color startColor, Color endColor) {
        super();
        this.startColor = startColor;
        this.endColor = endColor;
    }

    /**
     * create shader fill with specified colors and incidence
     * 
     * @param startColor
     *            the shade start color
     * @param endColor
     *            the shade end color
     * @param incidenceAngleDegree
     *            the shade incidence angle degree
     */
    public BubbleFill1(Color startColor, Color endColor,
            int incidenceAngleDegree) {
        super();
        this.startColor = startColor;
        this.endColor = endColor;
        this.incidenceAngleDegree = incidenceAngleDegree;
    }

    /**
     * create shader fill with specified shade fractions and colors
     * 
     * @param shadeFractions
     *            the shade fractions
     * @param shadeColors
     *            the shade colors
     */
    public BubbleFill1(float[] shadeFractions, Color[] shadeColors) {
        super();
        this.shadeFractions = shadeFractions;
        this.shadeColors = shadeColors;
    }

    /**
     * create shader fill with specified shade fractions, colors and incidence
     * 
     * @param shadeFractions
     *            the shade fractions
     * @param shadeColors
     *            the shade colors
     * @param incidenceAngleDegree
     *            the incidence angle degree to set
     */
    public BubbleFill1(float[] shadeFractions, Color[] shadeColors,
            int incidenceAngleDegree) {
        super();
        this.shadeFractions = shadeFractions;
        this.shadeColors = shadeColors;
        this.incidenceAngleDegree = incidenceAngleDegree;
    }

    /**
     * set the shadow parameters
     * 
     * @param fractions
     * @param colors
     */
    public void setShader(float[] fractions, Color[] colors) {
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("length array does not match");
        }
        shadeFractions = fractions;
        shadeColors = colors;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.bubble.painter.BubbleFill#paintBubble(java.awt.Graphics2D, com.jensoft.core.plugin.bubble.Bubble)
     */
    @Override
    public void paintBubble(Graphics2D g2d, Bubble bubble) {

        double centerX = bubble.getX();
        double centerY = bubble.getY();
        double radius = bubble.getRadius();

        double startX = centerX + radius
                * Math.cos(Math.toRadians(incidenceAngleDegree));
        double startY = centerY - radius
                * Math.sin(Math.toRadians(incidenceAngleDegree));

        double endX = centerX + radius
                * Math.cos(Math.toRadians(incidenceAngleDegree + 180));
        double endY = centerY - radius
                * Math.sin(Math.toRadians(incidenceAngleDegree + 180));

        Point2D start = new Point2D.Double(startX, startY);
        Point2D end = new Point2D.Double(endX, endY);

        if (start.equals(end)) {
            return;
        }

        Color[] colors = null;
        float[] fractions = null;

        if (startColor != null && endColor != null) {
            Color[] scolor = { startColor, endColor };
            float[] sfractions = { 0f, 1f };
            colors = scolor;
            fractions = sfractions;
        }

        if (shadeFractions != null && shadeColors != null) {
            colors = shadeColors;
            fractions = shadeFractions;
        }

        if (fractions != null && colors != null) {
            LinearGradientPaint p = new LinearGradientPaint(start, end,
                                                            fractions, colors);
            g2d.setPaint(p);
            g2d.fill(bubble.getBubbleShape());
        }
    }

}
