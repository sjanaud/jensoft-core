/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol;

import java.awt.Color;

import com.jensoft.core.plugin.symbol.painter.label.AbstractBarLabel;

/**
 * <code>Stack</code>
 * 
 * @author sebastien janaud
 */
public class Stack extends BarSymbol {

    /** stacked bar symbol host */
    private StackedBarSymbol hostSymbol;

    /** stack relative value */
    private double stackValue;

    /** stack normalized value */
    private double normalizedValue;

    /** enter flag */
    private boolean lockEnter = false;

    /**
     * create a new stack with specified given parameters
     * 
     * @param stackName
     *            the stack name to set
     * @param themeColor
     *            the theme color to set
     * @param value
     *            the relative value
     */
    public Stack(String stackName, Color themeColor, double value) {
        super();
        setName(stackName);
        setThemeColor(themeColor);
        stackValue = value;
    }

    /**
     * create a new stack with specified given parameters
     * 
     * @param stackName
     *            the stack name to set
     * @param themeColor
     *            the theme color to set
     * @param value
     *            the relative value
     * @param label
     *            the bar label to set
     */
    public Stack(String stackName, Color themeColor, double value,
            AbstractBarLabel label) {
        this(stackName, themeColor, value);
        setBarLabel(label);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Stack [hostSymbol=" + hostSymbol + ", stackValue=" + stackValue
                + ", normalizedValue=" + normalizedValue + ", lockEnter="
                + lockEnter + "]";
    }

    /**
     * @return the stackValue
     */
    public double getStackValue() {
        return stackValue;
    }

    /**
     * @param stackValue
     *            the stackValue to set
     */
    public void setStackValue(double stackValue) {
        this.stackValue = stackValue;
    }

    /**
     * get normalized value of this stack
     * 
     * @return normalized value of this stack
     */
    public double getNormalizedValue() {
        return normalizedValue;
    }

    /**
     * set the normalized value of this stack
     * 
     * @param normalizedValue
     *            the normalized value to set
     */
    public void setNormalizedValue(double normalizedValue) {
        this.normalizedValue = normalizedValue;
    }

    /**
     * get stacked bar symbol host for this stack
     * 
     * @return bar symbol host for this stack
     */
    public StackedBarSymbol getHostSymbol() {
        return hostSymbol;
    }

    /**
     * set stacked bar symbol host for this stack
     * 
     * @param hostSymbol
     *            the stacked bar symbol host to set
     */
    public void setHostSymbol(StackedBarSymbol hostSymbol) {
        this.hostSymbol = hostSymbol;
    }

    /**
     * return true if mouse has just enter in this ray, false otherwise
     * 
     * @return enter flag
     */
    @Override
    public boolean isLockEnter() {
        return lockEnter;
    }

    /**
     * lock ray enter flag
     */
    @Override
    public void lockEnter() {
        if (!isLockEnter()) {
            lockEnter = true;
        }
    }

    /**
     * set lock enter
     */
    public void setLockEnter(boolean lock) {
        lockEnter = lock;
    }

    /**
     * unlock ray enter
     */
    @Override
    public void unlockEnter() {
        if (isLockEnter()) {
            lockEnter = false;
        }
    }

}
