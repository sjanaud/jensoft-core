/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.painter.fill;

import org.jensoft.core.plugin.donut2d.painter.fill.Donut2DSliceRadialFill.GradientFillType;

/**
 * <code>Donut2DRadialFill</code> fill all slices with radial effect based on the {@link Donut2DSliceRadialFill}
 * 
 * @author Sebastien Janaud
 */
public class Donut2DRadialFill extends Donut2DCompatibleFill {

    /**
     * create pie radial fill
     */
    public Donut2DRadialFill() {
        super(new Donut2DSliceRadialFill());
    }

    /**
     * @return the gradientBehavior
     */
    public GradientFillType getGradientBehavior() {
        return ((Donut2DSliceRadialFill) getSliceFill()).getGradientBehavior();
    }

    /**
     * @param gradientBehavior
     *            the gradientBehavior to set
     */
    public void setGradientBehavior(GradientFillType gradientBehavior) {
        ((Donut2DSliceRadialFill) getSliceFill()).setGradientBehavior(gradientBehavior);
    }

}
