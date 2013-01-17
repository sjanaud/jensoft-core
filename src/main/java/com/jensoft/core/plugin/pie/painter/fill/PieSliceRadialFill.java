/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.fill;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.Pie.PieNature;

public class PieSliceRadialFill extends AbstractPieSliceFill {

    private Color startColor;
    private Color endColor;

    public PieSliceRadialFill() {
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.pie.painter.fill.AbstractPieSliceFill#paintPieSliceFill(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.pie.Pie, com.jensoft.sw2d.core.plugin.pie.PieSlice)
     */
    @Override
    protected void paintPieSliceFill(Graphics2D g2d, Pie pie, PieSlice slice) {

        g2d.setColor(slice.getThemeColor());

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
        Point2D focus = new Point2D.Double(centerX + 30, centerY + 30);
        Point2D cent = new Point2D.Double(centerX, centerY);
        double radius = pie.getRadius();
        float[] dist = { 0.0f, 1.0f };
        Color cStart = slice.getThemeColor().brighter();
        Color cEnd = slice.getThemeColor().darker();
        if (startColor != null) {
            cStart = startColor;
        }
        if (endColor != null) {
            cEnd = endColor;
        }
        Color[] cols = { cStart, cEnd };
        RadialGradientPaint p = new RadialGradientPaint(cent, (float) radius,
                                                        dist, cols, CycleMethod.NO_CYCLE);
        g2d.setPaint(p);
        g2d.fill(slice.getSlicePath());
    }

}
