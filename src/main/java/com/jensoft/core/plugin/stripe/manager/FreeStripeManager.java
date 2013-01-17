/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stripe.manager;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.stripe.Stripe;
import com.jensoft.core.plugin.stripe.StripePlugin;
import com.jensoft.core.plugin.stripe.StripePlugin.StripeOrientation;
import com.jensoft.core.plugin.stripe.painter.StripePaint;

/**
 * <code>FreeStripeManager</code> defines a free stripes manager
 * 
 * @see StripeManager
 * @see AbstractStripeManager
 * @see Stripe
 * @see StripePlugin
 * @author Sebastien Janaud
 */
public class FreeStripeManager extends AbstractStripeManager {

    //TODO keep only one list, propose new pattern to add free stripe with stripe object
    private List<Stripe> stripes = new ArrayList<Stripe>();
    private List<Stripe> deviceStripes = new ArrayList<Stripe>();

    /**
     * create free stripe manager
     * 
     * @param stripOrientation
     */
    public FreeStripeManager(StripeOrientation stripOrientation) {
        super.setStripeOrientation(stripOrientation);
    }

    /**
     * add a stripe with given parameters
     * 
     * @param stripStart
     * @param stripEnd
     * @param stripePaint
     */
    public void addStripe(double stripStart, double stripEnd, StripePaint stripePaint) {
        Stripe band = new Stripe();
        band.setUserStart(stripStart);
        band.setUserEnd(stripEnd);
        band.setBandPaint(stripePaint);
        stripes.add(band);
    }

    /**
     * add the given stripe
     * 
     * @param stripe
     */
    public void addStripe(Stripe stripe) {
        stripes.add(stripe);
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.stripe.manager.StripeManager#getStripes()
     */
    @Override
    public List<Stripe> getStripes() {
        deviceStripes.clear();
        for (Stripe b : stripes) {

            if (getStripeOrientation() == StripeOrientation.Vertical) {
                Point2D p2dUserStripeStart = new Point2D.Double(b.getUserStart(), 0);
                Point2D p2dDeviceStripeStart = getWindow2D().userToPixel(p2dUserStripeStart);

                Point2D p2dUserStripeEnd = new Point2D.Double(b.getUserEnd(), 0);
                Point2D p2dDeviceStripeEnd = getWindow2D().userToPixel(p2dUserStripeEnd);

                Stripe stripe = new Stripe(StripeOrientation.Vertical);

                stripe.setDeviceInterval(p2dDeviceStripeEnd.getX() - p2dDeviceStripeStart.getX());
                stripe.setDeviceStart(p2dDeviceStripeStart.getX());
                stripe.setDeviceEnd(p2dDeviceStripeEnd.getX());

                stripe.setUserInterval(b.getUserEnd() - b.getUserStart());
                stripe.setUserStart(b.getUserStart());
                stripe.setUserEnd(b.getUserEnd());

                stripe.setPaint(b.getBandPaint());
                deviceStripes.add(stripe);

            }
            else if (getStripeOrientation() == StripeOrientation.Horizontal) {

                Point2D p2dUserStripeStart = new Point2D.Double(0, b.getUserStart());
                Point2D p2dDeviceStripeStart = getWindow2D().userToPixel(p2dUserStripeStart);

                Point2D p2dUserStripeEnd = new Point2D.Double(0, b.getUserEnd());
                Point2D p2dDeviceStripeEnd = getWindow2D().userToPixel(p2dUserStripeEnd);

                Stripe stripe = new Stripe(StripeOrientation.Horizontal);
                stripe.setDeviceInterval(p2dDeviceStripeStart.getY() - p2dDeviceStripeEnd.getY());
                stripe.setDeviceStart(p2dDeviceStripeStart.getY());

                stripe.setUserInterval(b.getUserEnd() - b.getUserStart());
                stripe.setUserStart(b.getUserStart());
                stripe.setUserEnd(b.getUserEnd());

                stripe.setPaint(b.getBandPaint());
                deviceStripes.add(stripe);

            }
        }
        return deviceStripes;

    }
}
