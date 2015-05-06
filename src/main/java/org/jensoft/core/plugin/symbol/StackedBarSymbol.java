/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.symbol;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.plugin.symbol.painter.label.AbstractBarLabel;

/**
 * <code>StackedBarSymbol</code>
 */
public class StackedBarSymbol extends BarSymbol {

    /** stacks registry */
    private List<Stack> stacks = new ArrayList<Stack>();

    /**
     * create a new stacked bar symbol
     */
    public StackedBarSymbol() {
        super("barname", "barsymbol");
    }

    /**
     * create a new stacked bar symbol
     * 
     * @param symbol
     *            the symbol and name to set
     */
    public StackedBarSymbol(String symbol) {
        super(symbol, symbol);
    }

    /**
     * create a new stacke bar symbol with specified given parameters
     * 
     * @param name
     *            the name to set
     * @param symbol
     *            the symbol to set
     */
    public StackedBarSymbol(String name, String symbol) {
        super(name, symbol);
    }

    /**
     * get the base of the specified stack
     * 
     * @param stack
     *            the stack
     * @return the base of specified stack
     */
    public double getStackBase(Stack stack) {

        double base = super.getBase();
        for (Stack s : stacks) {
            if (stack.equals(s)) {
                return base;
            }

            if (isAscent()) {
                base = base + s.getNormalizedValue();
            }
            else if (isDescent()) {
                base = base - s.getNormalizedValue();
            }
        }

        return base;
    }

    /**
     * create and add a new stack with specified parameters
     * 
     * @param name
     *            the stack name to set
     * @param themeColor
     *            the theme color to set
     * @param proportionValue
     *            the proportion value
     */
    public void addStack(String name, Color themeColor, double proportionValue) {
        if (proportionValue < 0) {
            throw new IllegalArgumentException(
                                               "stack proportion value should be greater than 0");
        }

        Stack stack = new Stack(name, themeColor, proportionValue);
        stack.setHostSymbol(this);
        stacks.add(stack);

    }

    /**
     * create and add a new stack with specified parameters
     * 
     * @param name
     *            the stack name to set
     * @param themeColor
     *            the theme color to set
     * @param proportionValue
     *            the proportion value
     * @param barLabel
     *            the bar label to set
     */
    public void addStack(String name, Color themeColor, double proportionValue,
            AbstractBarLabel barLabel) {
        if (proportionValue < 0) {
            throw new IllegalArgumentException(
                                               "stack proportion value should be greater than 0");
        }

        Stack stack = new Stack(name, themeColor, proportionValue);
        stack.setBarLabel(barLabel);
        stack.setHostSymbol(this);
        stacks.add(stack);

    }

    /**
     * add the specified stack in this stacked symbol
     * 
     * @param stack
     *            the stack to add
     */
    public void addStack(Stack stack) {
        if (stack.getValue() < 0) {
            throw new IllegalArgumentException(
                                               "stack proportion value should be greater than 0");
        }

        stack.setHostSymbol(this);
        stacks.add(stack);

    }

    /**
     * normalization of the stack value
     */
    public void normalize() {
        double deltaValue = Math.abs(getValue());
        double stacksSumValue = 0;
        for (Stack s : stacks) {
            stacksSumValue = stacksSumValue + s.getStackValue();
        }

        for (Stack s : stacks) {
            s.setNormalizedValue(s.getStackValue() * deltaValue
                    / stacksSumValue);
        }

    }

    /**
     * get stacks registry
     * 
     * @return stacks registry
     */
    public List<Stack> getStacks() {
        return stacks;
    }

}
