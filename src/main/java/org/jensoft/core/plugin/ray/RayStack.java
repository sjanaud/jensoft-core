/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.ray;

import java.awt.Color;

import org.jensoft.core.palette.color.ColorPalette;
import org.jensoft.core.plugin.ray.painter.draw.AbstractRayDraw;
import org.jensoft.core.plugin.ray.painter.effect.AbstractRayEffect;
import org.jensoft.core.plugin.ray.painter.fill.AbstractRayFill;

/**
 * RayStack is a fragment of StackedRay an register on StackedRay by <code>addStack</code> method.
 * 
 * @see StackedRay
 * @see RayPlugin
 */
public class RayStack {

    /** the host of this stack */
    private StackedRay host;

    /** the stack name */
    private String stackName;

    /** stack theme color */
    private Color themeColor;

    /** stack value */
    private double value;

    /** stack normalized value */
    private double normalizedValue;

    /** the generated ray of this stack */
    private Ray ray;

    /** ray draw */
    private AbstractRayDraw rayDraw;

    /** ray fill */
    private AbstractRayFill rayFill;

    /** ray effect */
    private AbstractRayEffect rayEffect;

    /**
     * create stack
     * 
     * @param stackName
     *            this stack ray name
     * @param themeColor
     *            this stack theme color
     * @param value
     *            this stack value
     */
    public RayStack(String stackName, Color themeColor, double value) {
        this.stackName = stackName;
        this.themeColor = themeColor;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Ray Stack [stackName=" + stackName + ", value=" + value
                + ", normalizedValue=" + normalizedValue + "]";
    }

    /**
     * get the stack name
     * 
     * @return the stack name
     */
    public String getStackName() {
        return stackName;
    }

    /**
     * set the stack name
     * 
     * @param stackName
     *            the stack name to set
     */
    public void setStackName(String stackName) {
        this.stackName = stackName;
    }

    /**
     * get stack theme color
     * 
     * @return stack theme color
     */
    public Color getThemeColor() {
        if (themeColor == null) {
            themeColor = ColorPalette.getRandomColor();
        }
        return themeColor;
    }

    /**
     * set stack theme color
     * 
     * @param themeColor
     *            stack theme color to set
     */
    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
    }

    /**
     * get stack value
     * 
     * @return stack value
     */
    public double getValue() {
        return value;
    }

    /**
     * set stack value
     * 
     * @param value
     *            the stack value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * get stack normalize value
     * 
     * @return normalize value
     */
    public double getNormalizedValue() {
        return normalizedValue;
    }

    /**
     * set stack normalize value
     * 
     * @param normalizedValue
     *            the stack normalize value to set
     */
    public void setNormalizedValue(double normalizedValue) {
        this.normalizedValue = normalizedValue;
    }

    /**
     * get the generated ray of this stack
     * 
     * @return the generated stack
     */
    public Ray getRay() {
        return ray;
    }

    /**
     * set the generated ray
     * 
     * @param ray
     *            the generated ray to set
     */
    public void setRay(Ray ray) {
        this.ray = ray;
    }

    /**
     * get stacked ray host of this stack
     * 
     * @return stacked ray host
     */
    public StackedRay getHost() {
        return host;
    }

    /**
     * set stacked ray host
     * 
     * @param host
     *            the stacked ray host to set
     */
    public void setHost(StackedRay host) {
        this.host = host;
    }

    /**
     * get the ray draw
     * 
     * @return the ray draw
     */
    public AbstractRayDraw getRayDraw() {
        return rayDraw;
    }

    /**
     * set the ray draw
     * 
     * @param rayDraw
     *            the ray draw to set
     */
    public void setRayDraw(AbstractRayDraw rayDraw) {
        this.rayDraw = rayDraw;
    }

    /**
     * get the ray fill
     * 
     * @return the ray fill
     */
    public AbstractRayFill getRayFill() {
        return rayFill;
    }

    /**
     * set the ray fill
     * 
     * @param rayFill
     *            the ray fill to set
     */
    public void setRayFill(AbstractRayFill rayFill) {
        this.rayFill = rayFill;
    }

    /**
     * get the ray effect
     * 
     * @return the ray effect
     */
    public AbstractRayEffect getRayEffect() {
        return rayEffect;
    }

    /**
     * set the ray effect
     * 
     * @param rayEffect
     *            the ray effect to set
     */
    public void setRayEffect(AbstractRayEffect rayEffect) {
        this.rayEffect = rayEffect;
    }

}
