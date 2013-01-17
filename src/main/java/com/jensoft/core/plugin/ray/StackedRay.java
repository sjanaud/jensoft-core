/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>StackedRay<code> defines a Ray with stack
 */
public class StackedRay extends Ray {

    /** the stacks registry */
    private List<RayStack> stacks;

    /**
     * create stack ray
     */
    public StackedRay() {
        stacks = new ArrayList<RayStack>();
    }

    /**
     * get the stack base for the specified stack
     * 
     * @param stack
     *            the stack to find base
     * @return the stack base
     */
    public double getStackBase(RayStack stack) {

        double base = super.getRayBase();
        for (RayStack s : stacks) {
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
     * add a ray stack on this ray
     * 
     * @param rStack
     *            the stack to add
     */
    public void addStack(RayStack rStack) {

        if (rStack == null) {
            throw new IllegalArgumentException("stack can not be null.");
        }

        if (rStack.getValue() < 0) {
            throw new IllegalArgumentException(
                                               "stack value value should be greater than 0");
        }

        rStack.setHost(this);
        stacks.add(rStack);

    }

    /**
     * normalize registered stacks on this ray
     */
    public void normalize() {
        double deltaValue = Math.abs(getRayValue());
        double stacksValue = 0;
        for (RayStack s : stacks) {
            stacksValue = stacksValue + s.getValue();
        }

        for (RayStack s : stacks) {
            s.setNormalizedValue(s.getValue() * deltaValue / stacksValue);
        }

    }

    /**
     * get the stack registry
     * 
     * @return the stack registry
     */
    public List<RayStack> getStacks() {
        return stacks;
    }

    /**
     * set the stack registry
     * 
     * @param stacks
     *            the stack registry to set
     */
    public void setStacks(List<RayStack> stacks) {
        this.stacks = stacks;
    }

}
