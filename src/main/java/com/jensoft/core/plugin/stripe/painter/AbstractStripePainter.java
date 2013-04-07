/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stripe.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.stripe.manager.StripeManager;

/**
 * <code>AbstractStripePainter</code> defines the abstract base method to paint stripe.
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractStripePainter {

    /** the stripe manager */
    private StripeManager manager;

    /** stripe alpha */
    private float alpha = 1f;

    /**
     * set the stripe manager for this painter
     * 
     * @param manager
     */
    public void setBandManager(StripeManager manager) {
        this.manager = manager;
    }

    /**
     * get the layout manager of this painter
     * 
     * @return StripeManager
     */
    public StripeManager getManager() {
        return manager;
    }

    /**
     * get stripe alpha
     * 
     * @return alpha
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * set stripe alpha
     * 
     * @param alpha
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    /**
     * implements this method in sub class and paint the stripe
     * 
     * @param g2d
     */
    public abstract void doPaintStripes(Graphics2D g2d);
}
