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
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.PieToolkit;
import com.jensoft.core.plugin.pie.painter.AbstractPieSlicePainter;
import com.jensoft.core.plugin.pie.painter.PieSlicePainter;

/**
 * <code>PieBoundLabel</code>
 * <p>
 * Pie Bound label lay out pie slice labels on pie slice bounding frame rectangle<br>
 * <p>
 * <br>
 * <br>
 * <img src="doc-files/PieBoundLabel.png"> <br>
 * <br>
 * create bound label on slice
 * 
 * <pre>
 * float[] fractions = { 0f, 0.5f, 1f };
 * Color[] colors = { new Color(0, 0, 0, 100), new Color(0, 0, 0, 255), new Color(0, 0, 0, 255) };
 * Stroke s = new BasicStroke(2);
 * PieBoundLabel label = PieToolkit.createBoundLabel(&quot;Symbian&quot;, ColorPalette.WHITE, InputFonts.getNeuropol(10));
 * label.setStyle(Style.Both);
 * label.setOutlineStroke(s);
 * label.setShader(fractions, colors);
 * label.setOutlineColor(RosePalette.REDWOOD);
 * label.setOutlineRound(20);
 * slice.addSliceLabel(label1);
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
public class PieBoundLabel extends AbstractPieSliceLabel {

    public PieBoundLabel() {
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
    public PieBoundLabel(String label, Color labelColor, Font labelFont) {
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
    public PieBoundLabel(String label, Color labelColor) {
        this(label, labelColor, null);
    }

    /**
     * create default pie label with given parameters
     * 
     * @param label
     *            the label to set
     */
    public PieBoundLabel(String label) {
        this(label, null, null);
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.painter.label.AbstractPieSliceLabel#paintPieLabel(java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie, com.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    protected void paintPieLabel(Graphics2D g2d, Pie pie, PieSlice pieSection) {

        Rectangle2D r2d = pieSection.getSlicePath().getBounds2D();
        double percent = pieSection.getPercent() * 100;

        String s = getDecimalFormat().format(percent) + "%";

        if (getLabel() != null) {
            s = getLabel();
        }

        if (getLabelFont() != null) {
            g2d.setFont(getLabelFont());
        }

        FontMetrics fm = g2d.getFontMetrics();
        int widthText = fm.stringWidth(s);
        int heightText = fm.getHeight();
        int ascentText = fm.getAscent();
        int descentText = fm.getDescent();
        if (getLabelColor() != null) {
            g2d.setColor(getLabelColor());
        }
        else {
            g2d.setColor(pieSection.getThemeColor());
        }

        if (getLabel() != null) {
            s = getLabel();
        }

        if (getLabelFont() != null) {
            g2d.setFont(getLabelFont());
        }

        double centerX = r2d.getCenterX();
        double centerY = r2d.getCenterY();

        float x = (float) (centerX - widthText / 2);
        float y = (float) (centerY + fm.getAscent() / 2);

        g2d.setColor(pieSection.getThemeColor());
        g2d.setStroke(getDefaultStroke());

        // //debug
        // g2d.setColor(Color.RED);
        // Rectangle2D r1 = new Rectangle2D.Double(px1,py1,2,2);
        // //g2d.draw(r1);
        // Rectangle2D r2 = new Rectangle2D.Double(px2,py2,2,2);
        // //g2d.draw(r2);
        // Rectangle2D r3 = new Rectangle2D.Double(px3,py3,2,2);
        // g2d.draw(r3);

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

            if (getFillColor() == null && getShadeFractions() == null) {
                g2d.setColor(pieSection.getThemeColor());
                g2d.fill(rect);
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

        // g2d.drawString(s,x,y);

    }

}
