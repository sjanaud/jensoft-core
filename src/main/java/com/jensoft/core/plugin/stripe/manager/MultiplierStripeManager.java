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
import com.jensoft.core.plugin.stripe.StripePlugin.StripeOrientation;
import com.jensoft.core.plugin.stripe.painter.StripePaint;

/**
 * <code>DynamicStripeManager</code> defines a dynamic stripe flow
 * 
 * @see StripeManager
 * @see AbstractStripeManager
 * @see Stripe
 * @see StripeOrientation
 * @author Sebastien Janaud
 */
public class MultiplierStripeManager extends AbstractStripeManager {

    /** stripe reference */
    private double ref = 0;

    /** stripe interval */
    private double interval = 0;

    /** contain the last generate stripes */
    private List<Stripe> stripes = new ArrayList<Stripe>();

    /**
     * @param stripeOrientation
     * @param ref
     * @param interval
     */
    public MultiplierStripeManager(StripeOrientation stripeOrientation, double ref,
            double interval) {
        super.setStripeOrientation(stripeOrientation);
        this.ref = ref;
        this.interval = interval;
    }

    /**
     * add a stripe into {@link #stripes} if the stripe has not been registered yet
     * 
     * @param stripe
     *            the stripe to add
     */
    private void addStripe(Stripe stripe) {
        boolean flag = false;
        for (Stripe b : stripes) {
            if (stripe.equals(b)) {
                flag = true;
            }
        }
        if (!flag) {
            stripes.add(stripe);
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.stripe.manager.StripeManager#getStripes()
     */
    @Override
    public List<Stripe> getStripes() {

        stripes.clear();

        if (getStripeOrientation() == StripeOrientation.Vertical) {

            int stripeNumber = (int) ((getProjection().getMaxX() - getProjection().getMinX()) / interval);
            if (stripeNumber < 0 || stripeNumber > getStripeMaxNumber()) {
                stripes.clear();
                return stripes;
            }

            int countPalette = 0;
            int maxCount = getStripePalette().getPalette().size() - 1;
            int count1 = 0;
            boolean flag1 = true;
            while (flag1) {

                if (ref + count1 * interval >= getProjection().getMinX() - interval
                        && ref + count1 * interval <= getProjection().getMaxX()
                                + interval) {

                    Point2D p2dUserStripeStart = new Point2D.Double(ref + count1
                            * interval, 0);
                    Point2D p2dDeviceStripeStart = getProjection().userToPixel(
                                                                           p2dUserStripeStart);

                    Point2D p2dUserStripeEnd = new Point2D.Double(ref + count1
                            * interval + interval, 0);
                    Point2D p2dDeviceStripeEnd = getProjection().userToPixel(
                                                                         p2dUserStripeEnd);

                    Stripe stripe = new Stripe(StripeOrientation.Vertical);
                    stripe.setDeviceInterval(p2dDeviceStripeEnd.getX()
                            - p2dDeviceStripeStart.getX());
                    stripe.setDeviceStart(p2dDeviceStripeStart.getX());

                    stripe.setUserInterval(interval);
                    stripe.setUserStart(p2dUserStripeStart.getX());
                    stripe.setUserEnd(p2dUserStripeEnd.getX());

                    StripePaint p = getStripePalette()
                            .getPaintPalette(countPalette);
                    stripe.setPaint(p);

                    addStripe(stripe);

                }

                if (countPalette++ == maxCount) {
                    countPalette = 0;
                }

                if (ref + count1 * interval > getProjection().getMaxX()) {
                    flag1 = false;
                }

                count1++;

            }

            countPalette = 1;
            int count2 = 1;
            boolean flag2 = true;
            while (flag2) {

                if (ref - count2 * interval >= getProjection().getMinX() - interval
                        && ref - count2 * interval <= getProjection().getMaxX()
                                + interval) {
                    Point2D p2dUserStripeStart = new Point2D.Double(ref - count2
                            * interval, 0);
                    Point2D p2dDeviceStripeStart = getProjection().userToPixel(
                                                                  p2dUserStripeStart);

                    Point2D p2dUserStripeEnd = new Point2D.Double(ref - count2
                            * interval - interval, 0);
                    Point2D p2dDeviceStripeEnd = getProjection().userToPixel(
                                                                         p2dUserStripeEnd);

                    Stripe stripe = new Stripe(StripeOrientation.Vertical);
                    stripe.setDeviceInterval(p2dDeviceStripeStart.getX()
                            - p2dDeviceStripeEnd.getX());
                    stripe.setDeviceStart(p2dDeviceStripeStart.getX());
                    stripe.setDeviceEnd(p2dDeviceStripeEnd.getX());

                    stripe.setUserInterval(interval);
                    stripe.setUserStart(p2dUserStripeStart.getX());
                    stripe.setUserEnd(p2dUserStripeEnd.getX());

                    StripePaint p = getStripePalette()
                            .getPaintPalette(countPalette);
                    stripe.setPaint(p);

                    addStripe(stripe);

                }

                if (countPalette++ == maxCount) {
                    countPalette = 0;
                }

                if (ref - count2 * interval < getProjection().getMinX()) {
                    flag2 = false;
                }

                count2++;

            }

        }
        if (getStripeOrientation() == StripeOrientation.Horizontal) {

            int stripeNumber = (int) ((getProjection().getMaxY() - getProjection().getMinY()) / interval);
            if (stripeNumber < 0 || stripeNumber > getStripeMaxNumber()) {
                stripes.clear();
                return stripes;
            }

            int countPalette = 0;
            int maxCount = getStripePalette().getPalette().size() - 1;
            int count1 = 0;
            boolean flag1 = true;
            while (flag1) {

                if (ref + count1 * interval >= getProjection().getMinY() - interval
                        && ref + count1 * interval <= getProjection().getMaxY()
                                + interval) {
                    Point2D p2dUserStripeStart = new Point2D.Double(0, ref
                            + count1 * interval);
                    Point2D p2dDeviceStripeStart = getProjection().userToPixel(
                                                                             p2dUserStripeStart);

                    Point2D p2dUserStripeEnd = new Point2D.Double(0, ref + count1
                            * interval + interval);
                    Point2D p2dDeviceStripeEnd = getProjection().userToPixel(
                                                                         p2dUserStripeEnd);

                    Stripe stripe = new Stripe(StripeOrientation.Horizontal);

                    stripe.setDeviceInterval(p2dDeviceStripeEnd.getY()
                            - p2dDeviceStripeStart.getY());
                    stripe.setDeviceStart(p2dDeviceStripeStart.getY());
                    stripe.setDeviceEnd(p2dDeviceStripeEnd.getY());

                    stripe.setUserInterval(interval);
                    stripe.setUserStart(p2dUserStripeStart.getY());
                    stripe.setUserEnd(p2dUserStripeEnd.getY());

                    StripePaint p = getStripePalette()
                            .getPaintPalette(countPalette);
                    stripe.setPaint(p);

                    addStripe(stripe);
                }

                if (countPalette++ == maxCount) {
                    countPalette = 0;
                }

                if (ref + count1 * interval > getProjection().getMaxY()) {
                    flag1 = false;
                }

                count1++;

            }

            countPalette = 1;
            int count2 = 1;
            boolean flag2 = true;
            while (flag2) {

                if (ref - count2 * interval > getProjection().getMinY() - interval
                        && ref - count2 * interval < getProjection().getMaxY() + interval) {
                    Point2D p2dUserStripeStart = new Point2D.Double(0, ref
                            - count2 * interval);
                    Point2D p2dDeviceStripeStart = getProjection().userToPixel(p2dUserStripeStart);

                    Point2D p2dUserStripeEnd = new Point2D.Double(0, ref - count2
                            * interval - interval);
                    Point2D p2dDeviceStripeEnd = getProjection().userToPixel(
                                                                           p2dUserStripeEnd);

                    Stripe stripe = new Stripe(StripeOrientation.Horizontal);
                    stripe.setDeviceInterval(p2dDeviceStripeStart.getY()
                            - p2dDeviceStripeEnd.getY());
                    stripe.setDeviceStart(p2dDeviceStripeStart.getY());

                    stripe.setUserInterval(interval);
                    stripe.setUserStart(p2dUserStripeStart.getY());
                    stripe.setUserEnd(p2dUserStripeEnd.getY());

                    StripePaint p = getStripePalette()
                            .getPaintPalette(countPalette);
                    stripe.setPaint(p);

                    addStripe(stripe);

                }

                if (countPalette++ == maxCount) {
                    countPalette = 0;
                }

                if (ref - count2 * interval < getProjection().getMinY()) {
                    flag2 = false;
                }

                count2++;

            }

        }

        if (stripes.size() > getStripeMaxNumber()) {
            stripes.clear();
        }

        return stripes;
    }

}
