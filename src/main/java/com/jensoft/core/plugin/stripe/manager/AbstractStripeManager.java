/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stripe.manager;

import com.jensoft.core.plugin.stripe.Stripe;
import com.jensoft.core.plugin.stripe.StripePlugin;
import com.jensoft.core.plugin.stripe.StripePlugin.StripeOrientation;
import com.jensoft.core.plugin.stripe.painter.StripePalette;
import com.jensoft.core.projection.Projection;

/**
 * <code>AbstractStripeManager</code> abstract definition for stripe manager
 * 
 * @see StripePlugin
 * @see StripeOrientation
 * @see Stripe
 * @author Sebastien Janaud
 */
public abstract class AbstractStripeManager implements StripeManager {

    /** stripe palette */
    private StripePalette stripePalette;

    /** stripe orientation */
    private StripeOrientation stripeOrientation;

    /** window */
    private Projection window2D;

    /** max iteration for solve strip */
    private int iterationMax = 10000;

    /** max stripe number */
    private int stripeMaxNumber = 60;

    /**
     * create abstract stripe manager
     */
    public AbstractStripeManager() {
    }

    /**
     * set stripe orientation
     * 
     * @param stripOrientation
     */
    @Override
    public void setStripeOrientation(StripeOrientation stripOrientation) {
        this.stripeOrientation = stripOrientation;
    }

    /**
     * get stripe orientation
     */
    @Override
    public StripeOrientation getStripeOrientation() {
        return stripeOrientation;
    }

    /**
     * @return the iterationMax
     */
    public int getIterationMax() {
        return iterationMax;
    }

    /**
     * @param iterationMax
     *            the iterationMax to set
     */
    public void setIterationMax(int iterationMax) {
        this.iterationMax = iterationMax;
    }

    /**
     * @return the stripeMaxNumber
     */
    public int getStripeMaxNumber() {
        return stripeMaxNumber;
    }

    /**
     * @param stripeMaxNumber
     *            the stripeMaxNumber to set
     */
    public void setStripeMaxNumber(int stripeMaxNumber) {
        this.stripeMaxNumber = stripeMaxNumber;
    }

    /**
     * set window 2D
     * 
     * @param windo2D
     */
    @Override
    public void setWindow2D(Projection windo2D) {
        window2D = windo2D;
    }

    /**
     * get window 2D
     * 
     * @return window2D
     */
    @Override
    public Projection getWindow2D() {
        return window2D;
    }

    /**
     * get stripe palette
     * 
     * @return stripe palette
     */
    public StripePalette getStripePalette() {
        return stripePalette;
    }

    /**
     * set stripe palette
     * 
     * @param stripe
     *            the palette to set
     */
    public void setStripePalette(StripePalette stripe) {
        this.stripePalette = stripe;
    }

}
