/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pie.painter.effect;

/**
 * <code>CubicEffectKey</code>
 * 
 * @author Sebastien Janaud
 */
public class CubicEffectKey {

    /** start angle delta to add on the incidence */
    private int startAngleDelta = 45;

    /** start angle delta to subtract on the incidence */
    private int endAngleDelta = 90;

    /**
     * applied fraction on base radius to obtain the virtual radius for start
     * control
     */
    private float startControlFractionRadius = 0.5f;

    /**
     * applied fraction on base radius to obtain the virtual radius for end
     * control
     */
    private float endControlFractionRadius = 0.5f;

    /**
     * create key effect
     * 
     * @param startAngleDelta
     *            the start angle delta
     * @param startControlFractionRadius
     *            the start control fraction radius
     * @param endAngleDelta
     *            the end angle delta
     * @param endControlFractionRadius
     *            the end control fraction radius
     */
    public CubicEffectKey(int startAngleDelta, float startControlFractionRadius,
            int endAngleDelta, float endControlFractionRadius) {
        this.startAngleDelta = startAngleDelta;
        this.startControlFractionRadius = startControlFractionRadius;
        this.endAngleDelta = endAngleDelta;
        this.endControlFractionRadius = endControlFractionRadius;
    }

    /**
     * @return the startAngleDelta
     */
    public int getStartAngleDelta() {
        return startAngleDelta;
    }

    /**
     * @param startAngleDelta
     *            the startAngleDelta to set
     */
    public void setStartAngleDelta(int startAngleDelta) {
        this.startAngleDelta = startAngleDelta;
    }

    /**
     * @return the endAngleDelta
     */
    public int getEndAngleDelta() {
        return endAngleDelta;
    }

    /**
     * @param endAngleDelta
     *            the endAngleDelta to set
     */
    public void setEndAngleDelta(int endAngleDelta) {
        this.endAngleDelta = endAngleDelta;
    }

    /**
     * @return the startControlFractionRadius
     */
    public float getStartControlFractionRadius() {
        return startControlFractionRadius;
    }

    /**
     * @param startControlFractionRadius
     *            the startControlFractionRadius to set
     */
    public void setStartControlFractionRadius(float startControlFractionRadius) {
        this.startControlFractionRadius = startControlFractionRadius;
    }

    /**
     * @return the endControlFractionRadius
     */
    public float getEndControlFractionRadius() {
        return endControlFractionRadius;
    }

    /**
     * @param endControlFractionRadius
     *            the endControlFractionRadius to set
     */
    public void setEndControlFractionRadius(float endControlFractionRadius) {
        this.endControlFractionRadius = endControlFractionRadius;
    }

}
