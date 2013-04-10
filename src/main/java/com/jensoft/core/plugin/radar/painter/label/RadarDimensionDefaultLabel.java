/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.radar.painter.label;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import com.jensoft.core.plugin.radar.Radar;
import com.jensoft.core.plugin.radar.RadarDimension;

/**
 * DimensionLabel
 */
public class RadarDimensionDefaultLabel extends AbstractRadarDimensionLabel {

    /** label to draw */
    private String label;

    /** label color */
    private Color labelColor;

    /** font */
    private Font labelFont;

    /** offset radius */
    private int offsetRadius = 30;

    /** label outline round */
    private int outlineRound = 6;

    /** label padding x */
    private int labelPaddingX = 10;

    /** label padding y */
    private int labelPaddingY = 2;

    /** outline color */
    private Color outlineColor;

    /** outline stroke */
    private Stroke outlineStroke;

    /** fill color */
    private Color fillColor;

    /** shade fractions */
    private float[] shadeFractions;

    /** shade colors */
    private Color[] shadeColors;

    /** default style */
    private Style style = Style.Both;

    /** default stroke */
    private Stroke defaultStroke = new BasicStroke();

    /**
     * create default radar label with given parameters
     * 
     * @param label
     *            the label to set
     * @param labelColor
     *            the color to set
     * @param labelFont
     *            the font to set
     */
    public RadarDimensionDefaultLabel(String label, Color labelColor,
            Font labelFont) {
        super();
        this.label = label;
        this.labelColor = labelColor;
        this.labelFont = labelFont;
    }

    /**
     * create default radar label with given parameters
     * 
     * @param label
     *            the label to set
     * @param labelColor
     *            the color to set
     */
    public RadarDimensionDefaultLabel(String label, Color labelColor) {
        this(label, labelColor, null);
    }

    /**
     * create default radar label with given parameters
     * 
     * @param label
     *            the label to set
     */
    public RadarDimensionDefaultLabel(String label) {
        this(label, null, null);
    }

    /**
     * @return the style
     */
    public Style getStyle() {
        return style;
    }

    /**
     * @param style
     *            the style to set
     */
    public void setStyle(Style style) {
        this.style = style;
    }

    /**
     * @return the outlineRound
     */
    public int getOutlineRound() {
        return outlineRound;
    }

    /**
     * @param outlineRound
     *            the outlineRound to set
     */
    public void setOutlineRound(int outlineRound) {
        this.outlineRound = outlineRound;
    }

    /**
     * @return the labelPaddingX
     */
    public int getLabelPaddingX() {
        return labelPaddingX;
    }

    /**
     * @param labelPaddingX
     *            the labelPaddingX to set
     */
    public void setLabelPaddingX(int labelPaddingX) {
        this.labelPaddingX = labelPaddingX;
    }

    /**
     * @return the labelPaddingY
     */
    public int getLabelPaddingY() {
        return labelPaddingY;
    }

    /**
     * @param labelPaddingY
     *            the labelPaddingY to set
     */
    public void setLabelPaddingY(int labelPaddingY) {
        this.labelPaddingY = labelPaddingY;
    }

    /**
     * @return the outlineColor
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    /**
     * @param outlineColor
     *            the outlineColor to set
     */
    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    /**
     * @return the outlineStroke
     */
    public Stroke getOutlineStroke() {
        return outlineStroke;
    }

    /**
     * @param outlineStroke
     *            the outlineStroke to set
     */
    public void setOutlineStroke(Stroke outlineStroke) {
        this.outlineStroke = outlineStroke;
    }

    /**
     * @return the fillColor
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * @param fillColor
     *            the fillColor to set
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
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

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     *            the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the labelColor
     */
    public Color getLabelColor() {
        return labelColor;
    }

    /**
     * @param labelColor
     *            the labelColor to set
     */
    public void setLabelColor(Color labelColor) {
        this.labelColor = labelColor;
    }

    /**
     * @return the labelFont
     */
    public Font getLabelFont() {
        return labelFont;
    }

    /**
     * @param labelFont
     *            the labelFont to set
     */
    public void setLabelFont(Font labelFont) {
        this.labelFont = labelFont;
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
     * @see com.jensoft.core.plugin.radar.painter.label.AbstractRadarDimensionLabel#paintDimensionLabel(java.awt.Graphics2D, com.jensoft.core.plugin.radar.Radar, com.jensoft.core.plugin.radar.RadarDimension)
     */
    @Override
    protected void paintDimensionLabel(Graphics2D g2d, Radar radar,
            RadarDimension radarDimension) {

        if (label == null) {
            return;
        }

        if (labelFont != null) {
            g2d.setFont(labelFont);
        }

        FontMetrics fm = g2d.getFontMetrics();
        int widthText = fm.stringWidth(label);
        int heightText = fm.getHeight();
        int ascentText = fm.getAscent();
        int descentText = fm.getDescent();

        Point2D pointRef = radar.getDimensionPointAtRadius(radarDimension,
                                                           radar.getRadius() + offsetRadius);
        int x = (int) pointRef.getX();
        int y = (int) pointRef.getY();

        if (radarDimension.getAngleDegree() >= 270 && radarDimension
                .getAngleDegree() <= 360
                || radarDimension.getAngleDegree() >= 0 && radarDimension
                        .getAngleDegree() <= 90) {
            y = (y + fm.getAscent() / 2);
        }
        else {// 90-->270
            x = (x - widthText);
            y = (y + fm.getAscent() / 2);
        }

        RoundRectangle2D rect = new RoundRectangle2D.Double(x - labelPaddingX,
                                                            y - ascentText - labelPaddingY, widthText + 2
                                                                    * labelPaddingX,
                                                            ascentText + descentText + 2 * labelPaddingY, outlineRound,
                                                            outlineRound);

        if (style == Style.Fill || style == Style.Both) {

            if (fillColor != null && shadeFractions == null) {
                g2d.setColor(fillColor);
                g2d.fill(rect);
            }

            if (fillColor == null && shadeFractions == null) {
                g2d.setColor(radar.getThemeColor());
                g2d.fill(rect);
            }

            Point2D start2 = new Point2D.Double(rect.getX(), rect.getY()
                    + rect.getHeight());
            Point2D end2 = new Point2D.Double(rect.getX(), rect.getY());

            if (shadeFractions != null && shadeColors != null) {

                if (!start2.equals(end2)) {
                    LinearGradientPaint p2 = new LinearGradientPaint(start2,
                                                                     end2, shadeFractions, shadeColors);

                    g2d.setPaint(p2);
                    g2d.fill(rect);
                }
            }

        }

        if (style == Style.Stroke || style == Style.Both) {
            if (outlineColor != null) {
                g2d.setColor(outlineColor);
            }
            else {
                g2d.setColor(radar.getThemeColor().brighter());
            }
            if (outlineStroke != null) {
                g2d.setStroke(outlineStroke);
            }

            g2d.draw(rect);

        }

        g2d.setColor(radar.getThemeColor());
        g2d.setStroke(defaultStroke);

        g2d.setColor(radar.getThemeColor().brighter());
        if (labelColor != null) {
            g2d.setColor(labelColor);
        }

        g2d.drawString(label, x, y);
    }

}
