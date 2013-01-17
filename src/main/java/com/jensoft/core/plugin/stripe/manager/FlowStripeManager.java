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
 * <code>FlowStripeManager</code> defines a stripe flow
 * 
 * @see StripeManager
 * @see AbstractStripeManager
 * @see Stripe
 * @see StripeOrientation
 * @author Sebastien Janaud
 */
public class FlowStripeManager extends AbstractStripeManager {

    /** flow interval */
    private double flowInterval;

    /** start stripe flow */
    private double flowStart;

    /** end stripe flow */
    private double flowEnd;

    /** last generated stripes */
    private List<Stripe> deviceStripes = new ArrayList<Stripe>();

    /**
     * create a flow stripe with the specified parameters.
     * 
     * @param stripeOrientation
     *            the stripe orientation
     * @param flowStart
     *            the flow start
     * @param flowEnd
     *            the flow end
     * @param flowInterval
     *            the flow interval
     */
    public FlowStripeManager(StripeOrientation stripeOrientation, double flowStart,
            double flowEnd, double flowInterval) {
        super.setStripeOrientation(stripeOrientation);
        this.flowStart = flowStart;
        this.flowEnd = flowEnd;
        this.flowInterval = flowInterval;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.stripe.manager.StripeManager#getStripes()
     */
    @Override
    public List<Stripe> getStripes() {
        deviceStripes.clear();
        double interval = flowEnd - flowStart;
        int stripeNumber = (int) (interval / flowInterval);

        int countPalette = 0;
        int maxCount = getStripePalette().getPalette().size() - 1;

        if (stripeNumber > 0) {
            for (int i = 0; i <= stripeNumber; i++) {

                double g = flowStart + i * interval;

                if (getStripeOrientation() == StripeOrientation.Vertical) {
                    Point2D p2dUser = new Point2D.Double(g, 0);
                    Point2D p2ddevice = getWindow2D().userToPixel(p2dUser);

                    Point2D p2dUserWeigh = new Point2D.Double(g + interval, 0);
                    Point2D p2ddeviceWeigh = getWindow2D().userToPixel(
                                                                       p2dUserWeigh);

                    Stripe stripe = new Stripe(StripeOrientation.Vertical);
                    stripe.setDeviceInterval(p2ddeviceWeigh.getX()
                            - p2ddevice.getX());
                    stripe.deviceStart = p2ddevice.getX();

                    StripePaint p = getStripePalette()
                            .getPaintPalette(countPalette);

                    stripe.setPaint(p);

                    deviceStripes.add(stripe);

                    if (countPalette++ == maxCount) {
                        countPalette = 0;
                    }
                }
                else if (getStripeOrientation() == StripeOrientation.Horizontal) {

                    Point2D p2dUser = new Point2D.Double(0, g);
                    Point2D p2ddevice = getWindow2D().userToPixel(p2dUser);

                    Point2D p2dUserWeigh = new Point2D.Double(0, g + interval);
                    Point2D p2ddeviceWeigh = getWindow2D().userToPixel(
                                                                       p2dUserWeigh);

                    Stripe stripe = new Stripe(StripeOrientation.Horizontal);
                    stripe.setDeviceInterval(p2ddevice.getY()
                            - p2ddeviceWeigh.getY());
                    stripe.deviceStart = p2ddevice.getY();

                    StripePaint p = getStripePalette()
                            .getPaintPalette(countPalette);
                    stripe.setPaint(p);

                    deviceStripes.add(stripe);

                    if (countPalette++ == maxCount) {
                        countPalette = 0;
                    }
                }

            }
        }
        return deviceStripes;
    }

}
