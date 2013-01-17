/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stripe;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.stripe.manager.AbstractStripeManager;
import com.jensoft.core.plugin.stripe.manager.DynamicStripeManager;
import com.jensoft.core.plugin.stripe.manager.FlowStripeManager;
import com.jensoft.core.plugin.stripe.manager.FreeStripeManager;
import com.jensoft.core.plugin.stripe.manager.StripeManager;
import com.jensoft.core.plugin.stripe.painter.AbstractStripePainter;
import com.jensoft.core.plugin.stripe.painter.StripeDefaultPainter;
import com.jensoft.core.plugin.stripe.painter.StripePalette;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>StripePlugin</code> takes the responsibility to paints stripes <br>
 * <center><img src="doc-files/stripe1.png"></center> <br>
 * <br>
 * 
 * @see StripePalette
 * @see StripeManager
 * @see Stripe
 * @see StripeDefaultPainter
 * @author Sebastien Janaud
 */
public abstract class StripePlugin<M extends AbstractStripeManager> extends AbstractPlugin {

    /** stripe manager */
    private M stripeManager;

    /** stripe painter */
    private AbstractStripePainter stripePainter = new StripeDefaultPainter();

    /**
     * define band orientation, Vertical or Horizontal
     */
    public enum StripeOrientation {
        Vertical("vertical"), Horizontal("horizontal");

        private String bandOrientation;

        private StripeOrientation(String orientation) {
            bandOrientation = orientation;
        }

        /**
         * @return the bandOrientation
         */
        public String getBandOrientation() {
            return bandOrientation;
        }

        public static StripeOrientation parse(String orientation) {
            if (Vertical.getBandOrientation().equals(orientation)) {
                return Vertical;
            }
            if (Horizontal.getBandOrientation().equals(orientation)) {
                return Horizontal;
            }
            return null;
        }
    }

    /**
     * create abstract stripe plugin
     * 
     * @param stripeManager
     */
    public StripePlugin(M stripeManager) {
        this.stripeManager = stripeManager;
    }

    /**
     * <code>DynGridPlugin</code>
     * 
     * @author Sebastien Janaud
     */
    public static class DynStripe extends StripePlugin<DynamicStripeManager> {

        public static class H extends DynStripe {

            /**
             * create horizontal Dynamic Stripe
             * 
             * @param ref
             * @param interval
             */
            public H(double ref, double interval) {
                super(ref, interval, StripeOrientation.Horizontal);
            }
        }

        public static class V extends DynStripe {

            /**
             * create vertical Dynamic Stripe
             * 
             * @param ref
             * @param interval
             */
            public V(double ref, double interval) {
                super(ref, interval, StripeOrientation.Vertical);
            }
        }

        /**
         * create dynamic stripe plug in with specified given parameters
         * 
         * @param ref
         * @param interval
         * @param stripeOrientation
         */
        public DynStripe(double ref, double interval, StripeOrientation stripeOrientation) {
            super(new DynamicStripeManager(stripeOrientation, ref, interval));
        }

    }

    public static class FlowStripe extends StripePlugin<FlowStripeManager> {

        public static class H extends FlowStripe {

            /**
             * create horizontal Dynamic Stripe
             * 
             * @param start
             * @param end
             * @param interval
             */
            public H(double start, double end, double interval) {
                super(start, end, interval, StripeOrientation.Horizontal);
            }
        }

        public static class V extends FlowStripe {

            /**
             * create vertical Dynamic Stripe
             * 
             * @param start
             * @param end
             * @param interval
             */
            public V(double start, double end, double interval) {
                super(start, end, interval, StripeOrientation.Vertical);
            }
        }

        /**
         * create flow stripe plug in with specified given parameters
         * 
         * @param start
         * @param end
         * @param interval
         * @param stripeOrientation
         */
        public FlowStripe(double start, double end, double interval, StripeOrientation stripeOrientation) {
            super(new FlowStripeManager(stripeOrientation, start, end, interval));
        }
    }

    /**
     * <code>FreeStripe</code> plug in takes the responsibility to free stripes
     * 
     * @author sebastien janaud
     */
    public static class FreeStripe extends StripePlugin<FreeStripeManager> {

        public static class H extends FreeStripe {

            /**
             * create horizontal Free Stripe
             */
            public H() {
                super(StripeOrientation.Horizontal);
            }
        }

        public static class V extends FreeStripe {

            /**
             * create vertical Free Stripe
             */
            public V() {
                super(StripeOrientation.Vertical);
            }
        }

        /**
         * create free stripe plug in
         * 
         * @param stripeOrientation
         */
        public FreeStripe(StripeOrientation stripeOrientation) {
            super(new FreeStripeManager(stripeOrientation));
        }
        
        /**
         * add the given stripe
         * @param stripe
         */
        public void addStripe(Stripe stripe){
            getStripeManager().addStripe(stripe);
        }
    }

    /**
     * get stripe manager
     * 
     * @return stripe manager
     */
    public M getStripeManager() {
        return stripeManager;
    }

    /**
     * set stripe manager
     * 
     * @param stripeManager
     *            the band layout manager to set
     */
    public void setStripeManager(M stripeManager) {
        this.stripeManager = stripeManager;
    }

    /**
     * set alpha
     * 
     * @param alpha
     *            the alpha to set
     */
    public void setAlpha(float alpha) {
        getPainter().setAlpha(alpha);
    }
    
    /**
     * set the stripe palette
     * @param palette
     */
    public void setStripePalette(StripePalette palette){
        getStripeManager().setStripePalette(palette);
    }

    /**
     * get band painter
     * 
     * @return band painter
     */
    public AbstractStripePainter getPainter() {
        return stripePainter;
    }

    /**
     * set band painter
     * 
     * @param bandLayoutPainter
     *            the band layout painter to set
     */
    public void setPainter(AbstractStripePainter bandLayoutPainter) {
        this.stripePainter = bandLayoutPainter;
    }

    /**
     * paint stripes
     * 
     * @param v2d
     * @param g2d
     */
    protected void paintStripes(View2D v2d, Graphics2D g2d) {
        stripeManager.setWindow2D(getWindow2D());
        stripePainter.setBandManager(stripeManager);
        stripePainter.doPaintBand(g2d);
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.sw2d.core.view.View2D,
     * java.awt.Graphics2D, com.jensoft.sw2d.core.window.WindowPart)
     */
    @Override
    public final void paintPlugin(View2D v2d, Graphics2D g2d,
            WindowPart windowPart) {
        if (windowPart == WindowPart.Device) {
            paintStripes(v2d, g2d);
        }
    }

}
