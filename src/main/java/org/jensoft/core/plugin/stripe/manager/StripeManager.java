/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.stripe.manager;

import java.util.List;

import org.jensoft.core.plugin.stripe.Stripe;
import org.jensoft.core.plugin.stripe.StripePlugin;
import org.jensoft.core.plugin.stripe.StripePlugin.StripeOrientation;
import org.jensoft.core.projection.Projection;

/**
 * <code>StripeManager</code> defines the interface for stripe manager
 * 
 * @see StripePlugin
 * @see StripeOrientation
 * @see Stripe
 * @author Sebastien Janaud
 */
public interface StripeManager {

    /**
     * a manager define a policy to create stripe
     * 
     * @return the generated stripes
     */
    public List<Stripe> getStripes();

    /**
     * set the projection
     * 
     * @param projection
     *            the projection to set
     */
    public void setProjection(Projection projection);

    /**
     * get projection
     * 
     * @return projection
     */
    public Projection getProjection();

    /**
     * set the stripe orientation
     * 
     * @param orientation
     *            the orientation to set
     */
    public void setStripeOrientation(StripeOrientation orientation);

    /**
     * get stripe orientation
     * 
     * @return the stripe orientation
     */
    public StripeOrientation getStripeOrientation();

}
