/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.painter.fill;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;
import com.jensoft.core.plugin.donut2d.Donut2D.Donut2DNature;

/**
 * <code>Donut2DSliceRadialFill</code> defines a donut2D slice fill operation with radial transition color starting from
 * inner radius to outer radius
 * with a specified {@link GradientFillType}.
 * 
 * @see GradientFillType
 * @see AbstractDonut2DSliceFill
 * @author Sebastien Janaud
 */
public class Donut2DSliceRadialFill extends AbstractDonut2DSliceFill {

    /** gradient transition behavior */
    private GradientFillType gradientFillType = GradientFillType.D_SC_D;

    /**
     * create default radial slice fill with default {@link GradientFillType#D_SC_D}
     */
    public Donut2DSliceRadialFill() {
    }

    /**
     * @return the gradientBehavior
     */
    public GradientFillType getGradientBehavior() {
        return gradientFillType;
    }

    /**
     * @param gradientBehavior
     *            the gradientBehavior to set
     */
    public void setGradientBehavior(GradientFillType gradientBehavior) {
        this.gradientFillType = gradientBehavior;
    }

    /**
     * create default radial slice fill with specified gradient behavior
     * 
     * @param gradientBehavior
     *            the gradient transition behavior to set
     */
    public Donut2DSliceRadialFill(GradientFillType gradientBehavior) {
        super();
        this.gradientFillType = gradientBehavior;
    }

    /**
     * the gradient behavior defines radial transition color during slice fill operation.
     * <p>
     * for example, the radial fill can be start on slice inner radius with slice color darker to slice color on median
     * radius and end on outer radius with again slice color darker this transition is {@link GradientFillType#D_SC_D}
     * </p>
     * <p>
     * the pattern gradient behavior is C1_C2_C3.
     * </p>
     * <p>
     * C1, C2 and C3 defines respectively start color on inner radius,color on median radius, end color on outer radius.
     * Color pattern can be, D for slice darker color, B for slice color brighter and SC for slice color
     * </p>
     * 
     * @author Sebastien Janaud
     */
    public enum GradientFillType {
        B_D_B("B_D_B"),
        B_SC_B("B_SC_B"),
        D_SC_D("D_SC_D"),
        D_B_D("D_B_D"),
        SC_B_SC("SC_B_SC"),
        SC_D_SC("SC_D_SC");

        /** gradient behavior name */
        private String donut2DFillType;

        /**
         * create gradient behavior
         * 
         * @param donut2DFillType
         */
        private GradientFillType(String donut2DFillType) {
            this.donut2DFillType = donut2DFillType;
        }

        /**
         * @return the gradientBehavior
         */
        public String getDonut2DFillType() {
            return donut2DFillType;
        }

        /**
         * parse specified behavior in gradient behavior
         * 
         * @param fillType
         *            the behavior string to parse
         * @return gradient behavior
         */
        public static GradientFillType parse(String fillType) {
            if (B_D_B.getDonut2DFillType().equalsIgnoreCase(fillType)) {
                return B_D_B;
            }
            if (B_SC_B.getDonut2DFillType().equalsIgnoreCase(fillType)) {
                return B_SC_B;
            }
            if (D_SC_D.getDonut2DFillType().equalsIgnoreCase(fillType)) {
                return D_SC_D;
            }
            if (D_B_D.getDonut2DFillType().equalsIgnoreCase(fillType)) {
                return D_B_D;
            }
            if (SC_B_SC.getDonut2DFillType().equalsIgnoreCase(fillType)) {
                return SC_B_SC;
            }
            if (SC_D_SC.getDonut2DFillType().equalsIgnoreCase(fillType)) {
                return SC_D_SC;
            }
            return SC_B_SC;
        }

    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.donut2d.painter.fill.AbstractDonut2DSliceFill#paintDonut2DSliceFill(java.awt.Graphics2D
     * , com.jensoft.sw2d.core.plugin.donut2d.Donut2D, com.jensoft.sw2d.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    protected final void paintDonut2DSliceFill(Graphics2D g2d, Donut2D pie, Donut2DSlice pieSection) {

        g2d.setColor(pieSection.getThemeColor());

        double centerX = pie.getCenterX();
        double centerY = pie.getCenterY();
        double deltaDegree = pieSection.getPercent() * 360;
        double medianDegree = pieSection.getStartAngleDegree() + deltaDegree
                / 2;
        if (medianDegree > 360) {
            medianDegree = medianDegree - 360;
        }

        Point2D c = null;
        if (pie.getNature() == Donut2DNature.Donut2DUser) {
            c = pie.getHostPlugin().getWindow2D()
                    .userToPixel(new Point2D.Double(centerX, centerY));
        }
        if (pie.getNature() == Donut2DNature.Donut2DDevice) {
            c = new Point2D.Double(centerX, centerY);
        }

        centerX = c.getX() + pieSection.getDivergence()
                * Math.cos(Math.toRadians(medianDegree));
        centerY = c.getY() - pieSection.getDivergence()
                * Math.sin(Math.toRadians(medianDegree));

        Point2D cent = new Point2D.Double(centerX, centerY);
        double or = pie.getOuterRadius();
        double ir = pie.getInnerRadius();
        double mr = ir + (or - ir) / 2;

        double orf = 1;
        double mrf = mr / or;
        double irf = ir / or;

        float[] dist = { 0.0f, (float) irf, (float) mrf, (float) orf };

        
        RadialGradientPaint p = new RadialGradientPaint(cent, (float) or,
                                                        dist, getColors(pieSection.getThemeColor()), CycleMethod.NO_CYCLE);

        g2d.setPaint(p);
        g2d.fill(pieSection.getSlicePath());
    }
    
    private Color[] getColors(Color sliceColor){
        if (gradientFillType == GradientFillType.B_D_B) {
            Color cStart = sliceColor.brighter();
            Color cMedian = sliceColor.darker();
            Color cEnd = sliceColor.brighter();

            Color[] cols = { cStart, cStart, cMedian, cEnd };
            return cols;
        }
        if (gradientFillType == GradientFillType.B_SC_B) {
            Color cStart = sliceColor.brighter();
            Color cMedian = sliceColor;
            Color cEnd = sliceColor.brighter();

            Color[] cols = { cStart, cStart, cMedian, cEnd };
            return cols;
        }
        if (gradientFillType == GradientFillType.D_SC_D) {
            Color cStart = sliceColor.darker();
            Color cMedian = sliceColor;
            Color cEnd = sliceColor.darker();

            Color[] cols = { cStart, cStart, cMedian, cEnd };
            return cols;
        }
        if (gradientFillType == GradientFillType.D_B_D) {
            Color cStart = sliceColor.darker();
            Color cMedian = sliceColor.brighter();
            Color cEnd = sliceColor.darker();

            Color[] cols = { cStart, cStart, cMedian, cEnd };
            return cols;
        }
        if (gradientFillType == GradientFillType.SC_B_SC) {
            Color cStart = sliceColor;
            Color cMedian = sliceColor.brighter();
            Color cEnd = sliceColor;

            Color[] cols = { cStart, cStart, cMedian, cEnd };
            return cols;
        }
        if (gradientFillType == GradientFillType.SC_D_SC) {
            Color cStart = sliceColor;
            Color cMedian = sliceColor.darker();
            Color cEnd = sliceColor;

            Color[] cols = { cStart, cStart, cMedian, cEnd };
            return cols;
        }
        Color cStart = sliceColor.darker();
        Color cMedian = sliceColor;
        Color cEnd = sliceColor.darker();

        Color[] cols = { cStart, cStart, cMedian, cEnd };
        return cols;
    }

}
