/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.label;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.text.DecimalFormat;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.PieToolkit;
import com.jensoft.core.plugin.pie.painter.AbstractPieSlicePainter;
import com.jensoft.core.plugin.pie.painter.PieSlicePainter;

/**
 * <code>AbstractPieSliceLabel</code>
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
 * @see PieToolkit
 * @see AbstractPieSliceLabel
 * @see AbstractPieSlicePainter
 * @see PieSlicePainter
 * @see Pie
 * @see PiePlugin
 * @author Sebastien Janaud
 */
public abstract class AbstractPieSliceLabel extends AbstractPieSlicePainter {

    /** label to draw */
    private String label;

    /** label color */
    private Color labelColor;

    /** font */
    private Font labelFont;

    /** margin x between label and shape */
    private int labelPaddingX = 10;

    /** margin y between label and shape */
    private int labelPaddingY = 2;

    /** round corners for background shape */
    private int outlineRound = 20;

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

    /** default stroke */
    private Stroke defaultStroke = new BasicStroke();

    /** default style */
    private Style style = Style.Both;

    /** default decimal format */
    private DecimalFormat decimalFormat = new DecimalFormat("##.##");

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
     * return a copy of label shader
     * @return shader copy
     */
    public Shader getShader(){
    	return new Shader(shadeFractions, shadeColors);
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
     * @return the decimalFormat
     */
    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    /**
     * @param decimalFormat
     *            the decimalFormat to set
     */
    public void setDecimalFormat(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    /**
     * @param defaultStroke
     *            the defaultStroke to set
     */
    public void setDefaultStroke(Stroke defaultStroke) {
        this.defaultStroke = defaultStroke;
    }

    /**
     * defines style to render label
     * 
     * @author Sebastien Janaud
     */
    public enum Style {
        Nothing("Nothing"), Stroke("Stroke"), Fill("Fill"), Both("Both");

        private String styleName;

        private Style(String style) {
            styleName = style;
        }

        /**
         * @return the styleName
         */
        public String getStyleName() {
            return styleName;
        }

        /**
         * parse the string style into {@link Style}
         * 
         * @param style
         *            the style string to parse
         * @return the style
         */
        public static Style parseStyle(String style) {
            if (Style.Nothing.getStyleName().equalsIgnoreCase(style)) {
                return Style.Nothing;
            }
            if (Style.Stroke.getStyleName().equalsIgnoreCase(style)) {
                return Style.Stroke;
            }
            if (Style.Fill.getStyleName().equalsIgnoreCase(style)) {
                return Style.Fill;
            }
            if (Style.Both.getStyleName().equalsIgnoreCase(style)) {
                return Style.Both;
            }
            return Style.Both;
        }
    }

    /**
     * override this method to paint a pie label for a specified slice
     * 
     * @param g2d
     *            the graphics context
     * @param pie
     *            the host pie for the given slice
     * @param slice
     *            the slice concern by this label
     */
    protected abstract void paintPieLabel(Graphics2D g2d, Pie pie,
            PieSlice slice);

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.painter.AbstractPieSlicePainter#paintPieSlice(java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie, com.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    public final void paintPieSlice(Graphics2D g2d, Pie pie, PieSlice slice) {
        g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                             java.awt.AlphaComposite.SRC_OVER, slice.getAlpha()));
        if (slice.getPercent() * 100 > pie.getPassiveLabelAtMinPercent()) {
            paintPieLabel(g2d, pie, slice);
        }
        g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                             java.awt.AlphaComposite.SRC_OVER, 1));
    }

}
