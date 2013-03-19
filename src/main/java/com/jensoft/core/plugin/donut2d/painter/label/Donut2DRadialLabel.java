/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.painter.label;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2D.Donut2DNature;
import com.jensoft.core.plugin.donut2d.Donut2DPlugin;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;
import com.jensoft.core.plugin.donut2d.Donut2DToolkit;
import com.jensoft.core.plugin.donut2d.painter.Donut2DSlicePainter;

/**
 * <code>Donut2DRadialLabel</code>
 * <p>
 * Donut32 Radial label lay out donut slice labels on donut slice median section extends degree<br>
 * <p>
 * <br>
 * <br>
 * <img src="doc-files/Donut2DRadialLabel.png"> <br>
 * <br>
 * Create a Donut3D Radial Label:
 * 
 * <pre>
 * float[] fractions = { 0f, 0.3f, 0.7f, 1f };
 * Color[] c = { new Color(0, 0, 0, 20), new Color(0, 0, 0, 150),
 *         new Color(0, 0, 0, 150), new Color(0, 0, 0, 20) };
 * Donut2DRadialLabel label1 = Donut2DToolkit.createRadialLabel(&quot;Symbian&quot;,
 *                                                              RosePalette.COALBLACK, InputFonts.getNeuropol(12), 30, 20,
 *                                                              Style.Both);
 * label1.setLabelColor(ColorPalette.WHITE);
 * label1.setOutlineColor(RosePalette.REDWOOD);
 * label1.setShader(fractions, c);
 * s1.setSliceLabel(label1);
 * </pre>
 * 
 * @see Donut2D
 * @see Donut2DPlugin
 * @see Donut2DSlicePainter
 * @see AbstractDonut2DSliceLabel
 * @see Donut2DToolkit
 * @author Sebastien Janaud
 */
public class Donut2DRadialLabel extends AbstractDonut2DSliceLabel {

    /** offset radius */
    private int offsetRadius = 30;

    public Donut2DRadialLabel() {
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
    public Donut2DRadialLabel(String label, Color labelColor, Font labelFont) {
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
    public Donut2DRadialLabel(String label, Color labelColor) {
        this(label, labelColor, null);
    }

    /**
     * create default pie label with given parameters
     * 
     * @param label
     *            the label to set
     */
    public Donut2DRadialLabel(String label) {
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

    @Override
    protected void paintDonut2DSliceLabel(Graphics2D g2d, Donut2D donut2D,
            Donut2DSlice donut2DSlice) {

        if (getLabel() == null) {
            return;
        }

        g2d.setColor(donut2DSlice.getThemeColor());

        Font f = InputFonts.getNoMove(12);

        if (getLabelFont() != null) {
            f = getLabelFont();
        }

        g2d.setFont(f);

        FontMetrics fm = g2d.getFontMetrics();
        int widthText = fm.stringWidth(getLabel());
        int ascentText = fm.getAscent();
        int descentText = fm.getDescent();

        double centerX = donut2D.getCenterX();
        double centerY = donut2D.getCenterY();

        Point2D c = null;
        if (donut2D.getNature() == Donut2DNature.Donut2DUser) {
            c = donut2D.getHostPlugin().getWindow2D()
                    .userToPixel(new Point2D.Double(centerX, centerY));
        }
        if (donut2D.getNature() == Donut2DNature.Donut2DDevice) {
            c = new Point2D.Double(centerX, centerY);
        }
        centerX = c.getX();
        centerY = c.getY();
        double radius = donut2D.getOuterRadius();

        double medianDegree = donut2DSlice.getMedianAngleDegree();
        if (medianDegree >= 360) {
            medianDegree = medianDegree - 360;
        }

        double px2 = c.getX()
                + (radius + offsetRadius + donut2DSlice.getDivergence())
                * Math.cos(Math.toRadians(medianDegree));
        double py2 = c.getY()
                - (radius + offsetRadius + donut2DSlice.getDivergence())
                * Math.sin(Math.toRadians(medianDegree));

        if (medianDegree >= 270 && medianDegree <= 360
                || medianDegree >= 0 && medianDegree <= 90) {
            py2 = (float) (py2 + fm.getAscent() / 2);
        }
        else {// 90-->270
            px2 = (float) (px2 - widthText);
            py2 = (float) (py2 + fm.getAscent() / 2);
        }

        g2d.setColor(donut2DSlice.getThemeColor());
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
                g2d.setColor(donut2DSlice.getThemeColor());
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
                g2d.setColor(donut2DSlice.getThemeColor().brighter());
            }
            if (getOutlineStroke() != null) {
                g2d.setStroke(getOutlineStroke());
            }

            g2d.draw(rect);
        }

        g2d.setColor(donut2DSlice.getThemeColor());
        g2d.setStroke(getDefaultStroke());

        g2d.setColor(donut2DSlice.getThemeColor().brighter());
        if (getLabelColor() != null) {
            g2d.setColor(getLabelColor());
        }
        g2d.drawString(getLabel(), x, y);
    }

}
