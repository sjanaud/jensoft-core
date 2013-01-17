/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stripe.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.stripe.manager.StripeManager;

/**
 * AbstractBandPainter defines the abstract base method to paint the Band.
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractStripePainter {

    /** the band layout */
    private StripeManager layout;

    /** band alpha */
    private float alpha = 1f;

    /**
     * set the band layout for this painter
     * 
     * @param layout
     */
    public void setBandManager(StripeManager layout) {
        this.layout = layout;
    }

    /**
     * get the layout manager of this painter
     * 
     * @return BandLayoutManager
     */
    public StripeManager getLayout() {
        return layout;
    }

    /**
     * get band alpha
     * 
     * @return alpha
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * set band alpha
     * 
     * @param alpha
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    /**
     * implements this method in sub classe and paint the band
     * 
     * @param g2d
     */
    public abstract void doPaintBand(Graphics2D g2d);
}
