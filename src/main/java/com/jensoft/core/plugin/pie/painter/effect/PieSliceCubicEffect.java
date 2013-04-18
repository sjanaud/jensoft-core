/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.effect;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.Pie.PieNature;
import com.jensoft.core.plugin.pie.PieSlice;

/**
 * <code>PieSliceCubicEffect</code> create shadowed cubic shape on pie.
 * 
 * @author Sebastien Janaud
 */
public class PieSliceCubicEffect extends AbstractPieSliceEffect {

    /** start color */
    private Color startColor;

    /** end color */
    private Color endColor;

    /** start angle delta to add on the incidence */
    private int startAngleDelta = 45;

    /** start angle delta to subtract on the incidence */
    private int endAngleDelta = 90;

    /**
     * applied fraction on base radius to obtain the virtual radius for start
     * control
     */
    private float startControlFractionRadius = 0.5f;

    /**
     * applied fraction on base radius to obtain the virtual radius for end
     * control
     */
    private float endControlFractionRadius = 0.5f;

    /** incidence angle degree */
    private int incidenceAngleDegree = 90;

    /** offset radius */
    private int offsetRadius = 5;

   
    /**
     * create default Pie Effect
     */
    public PieSliceCubicEffect() {
    }

    /**
     * create Pie Effect
     * 
     * @param startColor
     *            the start color
     * @param endColor
     *            the end color
     * @param incidenceAngleDegree
     *            the incidence to set
     */
    public PieSliceCubicEffect(Color startColor, Color endColor,
            int incidenceAngleDegree) {
        super();
        this.startColor = startColor;
        this.endColor = endColor;
        this.incidenceAngleDegree = incidenceAngleDegree;
    }

    /**
     * create Pie Effect
     * 
     * @param startColor
     *            the start color
     * @param endColor
     *            the end color
     */
    public PieSliceCubicEffect(Color startColor, Color endColor) {
        super();
        this.startColor = startColor;
        this.endColor = endColor;
    }

    /**
     * create Pie Effect
     * 
     * @param incidenceAngleDegree
     *            the incidence to set
     */
    public PieSliceCubicEffect(int incidenceAngleDegree) {
        super();
        this.incidenceAngleDegree = incidenceAngleDegree;
    }

    /**
     * set frame for this effect
     * 
     * @param frame
     */
    public void setFrame(CubicEffectKey frame) {
        setStartAngleDelta(frame.getStartAngleDelta());
        setStartControlFractionRadius(frame.getStartControlFractionRadius());
        setEndAngleDelta(frame.getEndAngleDelta());
        setEndControlFractionRadius(frame.getEndControlFractionRadius());
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
        this.offsetRadius = offsetRadius;
    }

    /**
     * @return the startAngleDelta
     */
    public int getStartAngleDelta() {
        return startAngleDelta;
    }

    /**
     * @param startAngleDelta
     *            the startAngleDelta to set
     */
    public void setStartAngleDelta(int startAngleDelta) {
        if (startAngleDelta < -60 || startAngleDelta > 150) {
            throw new IllegalArgumentException(
                                               "startAngleDelta out of range [-100,100]");
        }
        this.startAngleDelta = startAngleDelta;
    }

    /**
     * @return the endAngleDelta
     */
    public int getEndAngleDelta() {
        return endAngleDelta;
    }

    /**
     * @param endAngleDelta
     *            the endAngleDelta to set
     */
    public void setEndAngleDelta(int endAngleDelta) {
        if (endAngleDelta < -100 || endAngleDelta > 150) {
            throw new IllegalArgumentException(
                                               "endAngleDelta out of range [-100,100]");
        }
        this.endAngleDelta = endAngleDelta;
    }

    /**
     * @return the startControlFractionRadius
     */
    public float getStartControlFractionRadius() {
        return startControlFractionRadius;
    }

    /**
     * @param startControlFractionRadius
     *            the startControlFractionRadius to set
     */
    public void setStartControlFractionRadius(float startControlFractionRadius) {
        if (startControlFractionRadius < 0 || startControlFractionRadius > 1) {
            throw new IllegalArgumentException(
                                               "startControlFractionRadius out of range [0,1]");
        }

        this.startControlFractionRadius = startControlFractionRadius;
    }

    /**
     * @return the endControlFractionRadius
     */
    public float getEndControlFractionRadius() {
        return endControlFractionRadius;
    }

    /**
     * @param endControlFractionRadius
     *            the endControlFractionRadius to set
     */
    public void setEndControlFractionRadius(float endControlFractionRadius) {
        if (endControlFractionRadius < 0 || endControlFractionRadius > 1) {
            throw new IllegalArgumentException(
                                               "endControlFractionRadius out of range [0,1]");
        }
        this.endControlFractionRadius = endControlFractionRadius;
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

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.painter.effect.AbstractPieSliceEffect#paintPieSliceEffect(java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie, com.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    public final void paintPieSliceEffect(Graphics2D g2d, Pie pie, PieSlice pieSection) {

        double centerX = pie.getCenterX();
        double centerY = pie.getCenterY();

        Point2D c = null;
        if (pie.getPieNature() == PieNature.PieUser) {
            c = pie.getHostPlugin().getWindow2D()
                    .userToPixel(new Point2D.Double(centerX, centerY));
        }
        if (pie.getPieNature() == PieNature.PieDevice) {
            c = new Point2D.Double(centerX, centerY);
        }

        double deltaDegree = pieSection.getPercent() * 360;
        double medianDegree = pieSection.getStartAngleDegree() + deltaDegree
                / 2;
        if (medianDegree > 360) {
            medianDegree = medianDegree - 360;
        }

        centerX = c.getX() + pieSection.getDivergence()
                * Math.cos(Math.toRadians(medianDegree));
        centerY = c.getY() - pieSection.getDivergence()
                * Math.sin(Math.toRadians(medianDegree));

        double radius = pie.getRadius() - offsetRadius;

        double controlStartX = centerX
                + startControlFractionRadius
                * radius
                * Math.cos(Math.toRadians(incidenceAngleDegree
                        + startAngleDelta));
        double controlStartY = centerY
                - startControlFractionRadius
                * radius
                * Math.sin(Math.toRadians(incidenceAngleDegree
                        + startAngleDelta));

        double controlEndX = centerX
                + endControlFractionRadius
                * radius
                * Math.cos(Math.toRadians(incidenceAngleDegree - endAngleDelta));
        double controlEndY = centerY
                - endControlFractionRadius
                * radius
                * Math.sin(Math.toRadians(incidenceAngleDegree - endAngleDelta));

        Point2D controlStart = new Point2D.Double(controlStartX, controlStartY);
        Point2D controlStop = new Point2D.Double(controlEndX, controlEndY);

        int extendsDegrees = 140;
        int startAngleDegree = incidenceAngleDegree - 70;
        int endAngleDegree = startAngleDegree + extendsDegrees;

        Arc2D arc2d = new Arc2D.Double(centerX - radius, centerY - radius,
                                       2 * radius, 2 * radius, startAngleDegree, endAngleDegree
                                               - startAngleDegree, Arc2D.OPEN);

        Point2D start = new Point2D.Double(centerX + radius
                * Math.cos(Math.toRadians(endAngleDegree)), centerY - radius
                * Math.sin(Math.toRadians(endAngleDegree)));

        Point2D end = new Point2D.Double(centerX + radius
                * Math.cos(Math.toRadians(startAngleDegree)), centerY - radius
                * Math.sin(Math.toRadians(startAngleDegree)));

        g2d.setColor(Color.RED);

        // debug anchors
        // g2d.draw(new Rectangle2D.Double(controlStart.getX(),
        // controlStart.getY(), 2, 2));
        // g2d.draw(new Rectangle2D.Double(controlStop.getX(),
        // controlStop.getY(), 2, 2));

        CubicCurve2D cubicCurve = new CubicCurve2D.Double(start.getX(),
                                                          start.getY(), controlStart.getX(), controlStart.getY(),
                                                          controlStop.getX(), controlStop.getY(), end.getX(),
                                                          end.getY());

        GeneralPath path = new GeneralPath();
        path.append(arc2d, false);
        path.append(cubicCurve, true);
        path.closePath();
        // g2d.draw(path);

        Point2D pG1 = new Point2D.Double(path.getBounds().getX()
                + path.getBounds().getWidth(), path.getBounds().getY()
                + path.getBounds().getHeight() / 2);
        Point2D pG2 = new Point2D.Double(path.getBounds().getX(), path
                .getBounds().getY() + path.getBounds().getHeight() / 2);

        Color cStart = new Color(255, 255, 255, 20);
        Color cEnd = new Color(255, 255, 255, 120);

        if (startColor != null) {
            cStart = startColor;
        }
        if (endColor != null) {
            cEnd = endColor;
        }

        if (!pG1.equals(pG2)) {
            GradientPaint gPaint = new GradientPaint(pG1, cStart, pG2, cEnd);
            g2d.setPaint(gPaint);
            Area a1 = new Area(path);
            Area a2 = new Area(pieSection.getSlicePath());
            a2.intersect(a1);
            g2d.fill(a2);

        }

    }

}
