/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stripe.painter;

import java.awt.Color;
import java.awt.Paint;

import com.jensoft.core.graphics.Shader;

/**
 * <code>StripePaint</code> define rendering properties to paint a stripe
 * 
 * @author Sebastien Janaud
 */
public class StripePaint {

    /** stripe base color */
    private Color stripeColor;

    /** shade fractions */
    private float[] shadeFractions;

    /** shade colors */
    private Color[] shadeColors;

    /** stripe paint */
    private Paint stripePaint;

    /**
     * create empty stripe paint
     */
    public StripePaint() {
    }

    /**
     * create band paint with specified band color
     * 
     * @param stripeColor
     *            the band color to set
     */
    public StripePaint(Color stripeColor) {
        super();
        this.stripeColor = stripeColor;
    }
    
    /**
     * create with specified shader
     * 
     * @param shader
     *            the shader
     */
    public StripePaint(Shader shader) {
        if (shader.getFractions().length != shader.getColors().length) {
            throw new IllegalArgumentException("length array does not match");
        }
        this.shadeFractions = shader.getFractions();
        this.shadeColors = shader.getColors();
    }

    /**
     * create with specified shading parameters
     * 
     * @param shadeFractions
     *            the shade fractions
     * @param shadeColors
     *            the shade colors
     */
    public StripePaint(float[] shadeFractions, Color[] shadeColors) {
        if (shadeFractions.length != shadeColors.length) {
            throw new IllegalArgumentException("length array does not match");
        }
        this.shadeFractions = shadeFractions;
        this.shadeColors = shadeColors;
    }

    /**
     * create stripe paint with specified paint
     * 
     * @param stripePaint
     *            the paint to set
     */
    public StripePaint(Paint stripePaint) {
        super();
        this.stripePaint = stripePaint;
    }

    /**
     * get stripe color
     * 
     * @return stripe color
     */
    public Color getStripeColor() {
        return stripeColor;
    }

    /**
     * set stripe color
     * 
     * @param stripeColor
     *            the stripe color to set
     */
    public void setStripeColor(Color stripeColor) {
        this.stripeColor = stripeColor;
    }

    /**
     * get stripe paint
     * 
     * @return stripe paint
     */
    public Paint getStripePaint() {
        return stripePaint;
    }

    /**
     * set stripe paint
     * 
     * @param stripePaint
     *            the stripe paint to set
     */
    public void setStripePaint(Paint stripePaint) {
        this.stripePaint = stripePaint;
    }

    /**
     * get shade fractions
     * 
     * @return shade fractions
     */
    public float[] getShadeFractions() {
        return shadeFractions;
    }

    /**
     * get shade colors
     * 
     * @return colors
     */
    public Color[] getShadeColors() {
        return shadeColors;
    }

    /**
     * set the shadow parameters
     * 
     * @param fractions
     *            the shade fractions to set
     * @param colors
     *            the shade colors to set
     */
    public void setShader(float[] fractions, Color[] colors) {
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("length array does not match");
        }
        shadeFractions = fractions;
        shadeColors = colors;
    }

}
