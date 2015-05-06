/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.painter.effect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import org.jensoft.core.graphics.Shader;
import org.jensoft.core.plugin.donut2d.Donut2D;
import org.jensoft.core.plugin.donut2d.Donut2DSlice;
import org.jensoft.core.plugin.donut2d.Donut2D.Donut2DNature;

/**
 * <code>Donut2DSliceLinearEffect</code> defines linear shadow effect on donut2D slice.
 * <p>
 * you can redefine shader by calling {@link #setShader(float[], Color[])} or {@link #setShader(Shader)} methods
 * </p>
 * <br>
 * <br>
 * the defaults shader is defines by following colors and fractions.
 * 
 * <pre>
 * 
 * 
 * 
 * 
 * // color base start
 * private Color cStart = new Color(60, 60, 60, 180);
 * 
 * // color base end
 * private Color cEnd = new Color(255, 255, 255, 180);
 * 
 * // default shader fractions
 * private float[] fractions = { 0.0f, 0.49f, 0.51f, 1.0f };
 * 
 * // default shader colors
 * private Color[] colors = { cStart, new Color(255, 255, 255, 0),
 *         new Color(255, 255, 255, 0), cEnd };
 * </pre>
 * 
 * @author Sebastien Janaud
 */
public class Donut2DSliceLinearEffect extends AbstractDonut2DSliceEffect {

    /** start color */
    private Color startColor;

    /** end color */
    private Color endColor;

    /** offset radius */
    private int offsetRadius = 3;

    /** gradient incidence angle degree */
    private int incidenceAngleDegree = 90;

    /** shade fractions */
    private float[] shadeFractions;

    /** shade colors */
    private Color[] shadeColors;

    /** color base start */
    private Color cStart = new Color(60, 60, 60, 180);

    /** color base end */
    private Color cEnd = new Color(255, 255, 255, 180);

    /** default shader fractions */
    private float[] fractions = { 0.0f, 0.49f, 0.51f, 1.0f };

    /** default shader colors */
    private Color[] colors = { cStart, new Color(255, 255, 255, 0),
            new Color(255, 255, 255, 0), cEnd };


    /**
     * create default effect
     */
    public Donut2DSliceLinearEffect() {
    }

    /**
     * create effect with the specified given parameters
     * 
     * @param startColor
     *            the start color to set
     * @param endColor
     *            the end color to set
     * @param offsetRadius
     *            the offset radius
     */
    public Donut2DSliceLinearEffect(Color startColor, Color endColor, int offsetRadius) {

        if (offsetRadius < 0) {
            throw new IllegalArgumentException(
                                               "offset radius should be greater than 0");
        }
        this.startColor = startColor;
        this.endColor = endColor;
        this.offsetRadius = offsetRadius;
    }

    public Donut2DSliceLinearEffect(Color startColor, Color endColor,
            int offsetRadius, int incidenceAngleDegree) {

        if (offsetRadius < 0) {
            throw new IllegalArgumentException(
                                               "offset radius should be greater than 0");
        }
        this.startColor = startColor;
        this.endColor = endColor;
        this.offsetRadius = offsetRadius;
        this.incidenceAngleDegree = incidenceAngleDegree;
    }

    /**
     * create effect with the specified given parameters
     * 
     * @param startColor
     *            the start color to set
     * @param endColor
     *            the end color to set
     */
    public Donut2DSliceLinearEffect(Color startColor, Color endColor) {
        this(startColor, endColor, 0);
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
     * @param endColor
     *            the endColor to set
     */
    public void setEndColor(Color endColor) {
        this.endColor = endColor;
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
     * @return the incidenceAngleDegree
     */
    public int getIncidenceAngleDegree() {
        return incidenceAngleDegree;
    }

    /**
     * @param incidenceAngleDegree
     *            the incidenceAngleDegree to set
     */
    public void setIncidenceAngleDegree(int incidenceAngleDegree) {
        this.incidenceAngleDegree = incidenceAngleDegree;
    }

    /**
     * set the shader parameters
     * 
     * @param fractions
     *            the shader fractions to set
     * @param colors
     *            the colors fractions to set
     */
    public void setShader(float[] fractions, Color[] colors) {
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("length array does not match");
        }
        shadeFractions = fractions;
        shadeColors = colors;
    }

    /**
     * set the shader
     * 
     * @param shader
     *            the shader to set
     */
    public void setShader(Shader shader) {
        if (shader != null) {
            shadeFractions = shader.getFractions();
            shadeColors = shader.getColors();
        }
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.painter.effect.AbstractDonut2DSliceEffect#paintDonut2DSliceEffect(java.awt.Graphics2D, com.jensoft.core.plugin.donut2d.Donut2D, com.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    protected void paintDonut2DSliceEffect(Graphics2D g2d, Donut2D donut2D,
            Donut2DSlice slice) {

        double deltaDegree = slice.getPercent() * 360;

        double centerX = donut2D.getCenterX();
        double centerY = donut2D.getCenterY();

        double medianDegree = slice.getStartAngleDegree() + deltaDegree
                / 2;
        if (medianDegree > 360) {
            medianDegree = medianDegree - 360;
        }

        Point2D c = null;
        if (donut2D.getNature() == Donut2DNature.User) {
            c = donut2D.getHostPlugin().getProjection()
                    .userToPixel(new Point2D.Double(centerX, centerY));
        }
        if (donut2D.getNature() == Donut2DNature.Device) {
            c = new Point2D.Double(centerX, centerY);
        }

        centerX = c.getX() + slice.getDivergence()
                * Math.cos(Math.toRadians(medianDegree));
        centerY = c.getY() - slice.getDivergence()
                * Math.sin(Math.toRadians(medianDegree));

        

        double outerRadius = donut2D.getOuterRadius() - offsetRadius;
        double innerRadius = donut2D.getInnerRadius() + offsetRadius;
       
        Ellipse2D outerEllipse = new Ellipse2D.Double(centerX - outerRadius, centerY
                - outerRadius, 2 * outerRadius, 2 * outerRadius);
        Ellipse2D innerEllipse = new Ellipse2D.Double(centerX - innerRadius, centerY
                                                      - innerRadius, 2 * innerRadius, 2 * innerRadius);

        double startX = centerX + outerRadius
                * Math.cos(Math.toRadians(incidenceAngleDegree));
        double startY = centerY - outerRadius
                * Math.sin(Math.toRadians(incidenceAngleDegree));

        double endX = centerX + outerRadius
                * Math.cos(Math.toRadians(incidenceAngleDegree + 180));
        double endY = centerY - outerRadius
                * Math.sin(Math.toRadians(incidenceAngleDegree + 180));

        Point2D start = new Point2D.Double(startX, startY);
        Point2D end = new Point2D.Double(endX, endY);

        if (start.equals(end)) {
            return;
        }

        if (startColor != null && endColor != null && shadeFractions == null) {
            colors = new Color[] { startColor, endColor };
            fractions = new float[] { 0f, 1f };
        }

        if (shadeFractions != null && shadeColors != null) {
            colors = shadeColors;
            fractions = shadeFractions;
        }

        LinearGradientPaint shader = new LinearGradientPaint(start, end,
                                                             fractions, colors);
        g2d.setPaint(shader);
        Area outerArea = new Area(outerEllipse);
        Area innerArea = new Area(innerEllipse);
        outerArea.subtract(innerArea);
        Area sliceArea = new Area(slice.getSlicePath());
        sliceArea.intersect(outerArea);
        g2d.fill(sliceArea);

    }

}
