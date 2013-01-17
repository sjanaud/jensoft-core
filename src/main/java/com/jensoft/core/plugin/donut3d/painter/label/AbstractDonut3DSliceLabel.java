/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut3d.painter.label;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.donut3d.Donut3D;
import com.jensoft.core.plugin.donut3d.Donut3DSlice;
import com.jensoft.core.plugin.donut3d.painter.Donut3DSlicePainter;

/**
 * <code>AbstractDonut3DSliceLabel</code>
 * <p>
 * Abstract definition of a slice label which defines some basics properties<br>
 * for painting slice label operation.
 * </p>
 * <ul>
 * <li>label</li>
 * <li>label color</li>
 * <li>label font</li>
 * <li>label padding x</li>
 * <li>label padding y</li>
 * <li>outline round</li>
 * <li>outline color</li>
 * <li>outline stroke</li>
 * <li>shader fractions</li>
 * <li>shader colors</li>
 * </ul>
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractDonut3DSliceLabel implements Donut3DSlicePainter {

    /** label to draw */
    private String label;

    /** label color */
    private Color labelColor;

    /** font */
    private Font labelFont;

    /** round corners for background shape */
    private int outlineRound = 6;

    /** outline color */
    private Color outlineColor;

    /** outline stroke */
    private Stroke outlineStroke;

    /** margin x between label and shape */
    private int labelPaddingX = 10;

    /** margin y between label and shape */
    private int labelPaddingY = 2;

    /** fill color */
    private Color fillColor;

    /** shade fractions */
    private float[] shadeFractions;

    /** shade colors */
    private Color[] shadeColors;

    /** default stroke */
    private Stroke defaultStroke = new BasicStroke();

    /** default style */
    private Style style = Style.Both;

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
     * set the shadow parameters
     * 
     * @param shader
     */
    public void setShader(Shader shader) {
        if (shader != null) {
            shadeFractions = shader.getFractions();
            shadeColors = shader.getColors();
        }
    }

    /**
     * @return the shadeFractions
     */
    public float[] getShadeFractions() {
        return shadeFractions;
    }

    /**
     * @param shadeFractions
     *            the shadeFractions to set
     */
    public void setShadeFractions(float[] shadeFractions) {
        this.shadeFractions = shadeFractions;
    }

    /**
     * @return the shadeColors
     */
    public Color[] getShadeColors() {
        return shadeColors;
    }

    /**
     * @param shadeColors
     *            the shadeColors to set
     */
    public void setShadeColors(Color[] shadeColors) {
        this.shadeColors = shadeColors;
    }

    /**
     * @return the defaultStroke
     */
    public Stroke getDefaultStroke() {
        return defaultStroke;
    }

    /**
     * @param defaultStroke
     *            the defaultStroke to set
     */
    public void setDefaultStroke(Stroke defaultStroke) {
        this.defaultStroke = defaultStroke;
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
     * defines a painting style for the label
     * 
     * @author Sebastien Janaud
     */
    public enum Style {
        Nothing, Stroke, Fill, Both;

        public static Style parseStyle(String style) {
            if (style == null) {
                return Both;
            }
            if (style.equalsIgnoreCase("Nothing")) {
                return Style.Nothing;
            }
            if (style.equalsIgnoreCase("Stroke")) {
                return Style.Stroke;
            }
            if (style.equalsIgnoreCase("Fill")) {
                return Style.Fill;
            }
            if (style.equalsIgnoreCase("Both")) {
                return Style.Both;
            }
            return Both;
        }
    }

    /**
     * label painting operation
     * <p>
     * override this method in sub classes for painting the label
     * </p>
     * 
     * @param g2d
     *            the graphics context
     * @param donut3d
     *            the donut3D that host slice
     * @param donutSlice
     *            the slice on which the label has to be painted
     */
    protected abstract void paintDonut3DSliceLabel(Graphics2D g2d, Donut3D donut3d,
            Donut3DSlice donutSlice);

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.donut3d.painter.SlicePainter#paintSlice(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.donut3d.Donut3D, com.jensoft.sw2d.core.plugin.donut3d.Donut3DSlice)
     */
    @Override
    public final void paintDonut3DSlice(Graphics2D g2d, Donut3D donut3d,
            Donut3DSlice donutSection) {
        paintDonut3DSliceLabel(g2d, donut3d, donutSection);
    }

}
