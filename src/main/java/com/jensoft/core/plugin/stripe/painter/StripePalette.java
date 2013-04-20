/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stripe.painter;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.stripe.Stripe;
import com.jensoft.core.plugin.stripe.StripePlugin;
import com.jensoft.core.plugin.stripe.manager.AbstractStripeManager;
import com.jensoft.core.plugin.stripe.manager.StripeManager;

/**
 * <code>StripePalette</code> defines the paint collection for stripes
 * 
 * @see StripeManager
 * @see AbstractStripeManager
 * @see Stripe
 * @see StripePlugin
 * @author Sebastien Janaud
 */
public class StripePalette {

    /** paint palette */
    private List<StripePaint> palette;

    /**
     * create stripe palette
     */
    public StripePalette() {
        palette = new ArrayList<StripePaint>();
    }

    /**
     * get the paint palette
     * 
     * @return paints
     */
    public List<StripePaint> getPalette() {
        return palette;
    }

    /**
     * add paint to this palette
     * 
     * @param paint
     */
    public void addPaint(StripePaint paint) {
        palette.add(paint);
    }

    /**
     * add paint to this palette
     * 
     * @param color
     */
    public void addPaint(Color color) {
        palette.add(new StripePaint(color));
    }

   /**
    * add paint to this palette
    * @param shadeFractions
    * @param shadeColors
    */
    public void addPaint(float[] shadeFractions, Color[] shadeColors) {
        palette.add(new StripePaint(shadeFractions, shadeColors));
    }

    /**
     * add paint to this palette
     * 
     * @param paint
     */
    public void addPaint(Paint paint) {
        palette.add(new StripePaint(paint));
    }

    /**
     * get paint for the specified index
     * 
     * @param index
     *            the paint index
     * @return the paint for the specified index
     */
    public StripePaint getPaintPalette(int index) {
        return palette.get(index);
    }

}
