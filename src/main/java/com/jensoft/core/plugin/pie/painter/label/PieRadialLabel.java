/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.label;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.Pie.PieNature;
import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.PieToolkit;
import com.jensoft.core.plugin.pie.painter.AbstractPieSlicePainter;
import com.jensoft.core.plugin.pie.painter.PieSlicePainter;

/**
 * <code>PieRadialLabel</code>
 * <p>
 * Pie Radial label lay out pie slice labels on pie slice median section extends degree<br>
 * <p>
 * <br>
 * <br>
 * <img src="doc-files/PieRadialLabel.png"> <br>
 * <br>
 * create radial label on slice
 * 
 * <pre>
 * float[] fractions = { 0f, 0.5f, 1f };
 * Color[] colors = { new Color(0, 0, 0, 100), new Color(0, 0, 0, 255), new Color(0, 0, 0, 255) };
 * Stroke s = new BasicStroke(2);
 * PieRadialLabel label = PieToolkit.createRadialLabel(&quot;Symbian&quot;, ColorPalette.WHITE, InputFonts.getNeuropol(12), 20);
 * label.setStyle(Style.Both);
 * label.setOutlineStroke(s);
 * label.setShader(fractions, colors);
 * label.setOutlineColor(RosePalette.REDWOOD);
 * label.setOutlineRound(20);
 * slice.addSliceLabel(label);
 * </pre>
 * 
 * @see PieToolkit
 * @see AbstractPieSliceLabel
 * @see AbstractPieSlicePainter
 * @see PieSlicePainter
 * @see Pie
 * @see PiePlugin
 * @author Sebastien Janaud
 */
public class PieRadialLabel extends AbstractPieSliceLabel {

    /** offset radius */
    private int offsetRadius = 20;

    public PieRadialLabel() {
    }

    /**
     * create default pie label with given parameters
     * 
     * @param label
     *            the label to set
     * @param labelColor
     *            the color to set
     * @param labelFont
     *            the font to set
     */
    public PieRadialLabel(String label, Color labelColor, Font labelFont) {
        super();
        super.setLabel(label);
        super.setLabelColor(labelColor);
        super.setLabelFont(labelFont);
    }

    /**
     * create default pie label with given parameters
     * 
     * @param label
     *            the label to set
     * @param labelColor
     *            the color to set
     */
    public PieRadialLabel(String label, Color labelColor) {
        this(label, labelColor, null);
    }

    /**
     * create default pie label with given parameters
     * 
     * @param label
     *            the label to set
     */
    public PieRadialLabel(String label) {
        this(label, null, null);
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

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.painter.label.AbstractPieSliceLabel#paintPieLabel(java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie, com.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    protected void paintPieLabel(Graphics2D g2d, Pie pie, PieSlice pieSection) {

        g2d.setColor(pieSection.getThemeColor());

        double percent = pieSection.getPercent() * 100;

        String s = getDecimalFormat().format(percent) + "%";

        if (getLabel() != null) {
            s = getLabel();
        }

        Font f = InputFonts.getElements(12);

        if (getLabelFont() != null) {
            f = getLabelFont();
        }

        g2d.setFont(f);

        FontMetrics fm = g2d.getFontMetrics();
        int widthText = fm.stringWidth(s);
        int heightText = fm.getHeight();
        int ascentText = fm.getAscent();
        int descentText = fm.getDescent();

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
        centerX = c.getX();
        centerY = c.getY();
        double radius = pie.getRadius();

        double medianDegree = pieSection.getMedianAngleDegree();
        if (medianDegree >= 360) {
            medianDegree = medianDegree - 360;
        }

        double px2 = c.getX()
                + (radius + offsetRadius + pieSection.getDivergence())
                * Math.cos(Math.toRadians(medianDegree));
        double py2 = c.getY()
                - (radius + offsetRadius + pieSection.getDivergence())
                * Math.sin(Math.toRadians(medianDegree));

        if (medianDegree >= 270 && medianDegree <= 360
                || medianDegree >= 0 && medianDegree <= 90) {
            py2 = (float) (py2 + fm.getAscent() / 2);
        }
        else {// 90-->270
            px2 = (float) (px2 - widthText);
            py2 = (float) (py2 + fm.getAscent() / 2);
        }

        g2d.setColor(pieSection.getThemeColor());
        g2d.setStroke(getDefaultStroke());

        float x, y;
        x = (float) px2;
        y = (float) py2;

        RoundRectangle2D rect = new RoundRectangle2D.Double(x - getLabelPaddingX(),
                                                            y - ascentText - getLabelPaddingY(), widthText + 2
                                                                    * getLabelPaddingX(),
                                                            ascentText + descentText + 2 * getLabelPaddingY(),
                                                            getOutlineRound(),
                                                            getOutlineRound());

        if (getStyle() == Style.Fill || getStyle() == Style.Both) {

            if (getFillColor() != null && getShadeFractions() == null) {
                g2d.setColor(getFillColor());
                g2d.fill(rect);
            }
            if (getFillColor() != null && getShadeFractions() == null) {
                g2d.setColor(pieSection.getThemeColor());
            }

            Point2D start2 = new Point2D.Double(rect.getX(), rect.getY()
                    + rect.getHeight());
            Point2D end2 = new Point2D.Double(rect.getX(), rect.getY());

            float[] dist2 = { 0f, 0.4f, 0.6f, 1.0f };
            Color cStart2 = new Color(40, 40, 40, 80);
            Color cStart2bis = new Color(40, 40, 40, 10);
            Color cEnd2bis = new Color(255, 255, 255, 10);
            Color cEnd2 = new Color(240, 240, 240, 80);

            Color[] colors2 = { cStart2, cStart2bis, cEnd2bis, cEnd2 };

            if (getShadeFractions() != null && getShadeColors() != null) {
                colors2 = getShadeColors();
                dist2 = getShadeFractions();
            }

            if (!start2.equals(end2)) {
                LinearGradientPaint p2 = new LinearGradientPaint(start2, end2,
                                                                 dist2, colors2);

                g2d.setPaint(p2);
                g2d.fill(rect);
            }
        }

        if (getStyle() == Style.Stroke || getStyle() == Style.Both) {
            if (getOutlineColor() != null) {
                g2d.setColor(getOutlineColor());
            }
            else {
                g2d.setColor(pieSection.getThemeColor().brighter());
            }
            if (getOutlineStroke() != null) {
                g2d.setStroke(getOutlineStroke());
            }

            g2d.draw(rect);
        }

        g2d.setColor(pieSection.getThemeColor());
        g2d.setStroke(getDefaultStroke());

        g2d.setColor(pieSection.getThemeColor().brighter());
        if (getLabelColor() != null) {
            g2d.setColor(getLabelColor());
        }
        g2d.drawString(s, x, y);

    }

}
