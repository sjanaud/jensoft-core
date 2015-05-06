/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.stripe.painter;

import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.jensoft.core.plugin.stripe.Stripe;
import org.jensoft.core.plugin.stripe.StripePlugin.StripeOrientation;
import org.jensoft.core.plugin.stripe.manager.StripeManager;

/**
 * <code>StripeDefaultPainter</code>
 * 
 * @author Sebastien Janaud
 */
public class StripeDefaultPainter extends AbstractStripePainter {

    /**
     * create band painter
     */
    public StripeDefaultPainter() {
    }

    /**
     * paint band operation
     * 
     * @param g2d
     *            the graphics2D context
     */
    @Override
    public void doPaintStripes(Graphics2D g2d) {
        StripeManager manager = getManager();
        if (manager == null) {
			return;
		}
        g2d.setComposite(java.awt.AlphaComposite.getInstance( java.awt.AlphaComposite.SRC_OVER, 1f));
                                                            
        if (manager.getStripeOrientation() == StripeOrientation.Vertical) {

            List<Stripe> stripes = manager.getStripes();

            for (int i = 0; i < stripes.size(); i++) {
                Stripe stripe = stripes.get(i);
                double gd = stripe.deviceStart;

                Shape stripeShape = new Rectangle2D.Double(gd, 0,
                                                         stripe.getDeviceInterval(), getManager().getProjection()
                                                                 .getDevice2D().getDeviceHeight());

                StripePaint bandPaint = stripe.getPaint();
                if (bandPaint.getStripeColor() != null) {
                    g2d.setColor(bandPaint.getStripeColor());
                }
                if (bandPaint.getStripePaint() != null) {
                    g2d.setPaint(bandPaint.getStripePaint());
                }
                if (bandPaint.getShadeFractions() != null
                        && bandPaint.getShadeColors() != null
                        && bandPaint.getShadeFractions().length == bandPaint
                                .getShadeColors().length) {
                    Point2D start = new Point2D.Double(stripeShape.getBounds2D()
                            .getX(), stripeShape.getBounds2D().getCenterY());
                    Point2D end = new Point2D.Double(stripeShape.getBounds2D()
                            .getX() + stripeShape.getBounds2D().getWidth(),
                                                     stripeShape.getBounds2D().getCenterY());

                    if (!start.equals(end)) {
                        LinearGradientPaint shader = new LinearGradientPaint(
                                                                             start, end, bandPaint.getShadeFractions(),
                                                                             bandPaint.getShadeColors());

                        g2d.setPaint(shader);

                    }
                }

                g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                     java.awt.AlphaComposite.SRC_OVER, getAlpha()));

                g2d.fill(stripeShape);

                g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                     java.awt.AlphaComposite.SRC_OVER, 1f));
                if (stripe.getAnnotation() != null) {
                    g2d.drawString(stripe.getAnnotation(), (int) gd + 2, 10);
                }
            }

        }

        if (manager.getStripeOrientation() == StripeOrientation.Horizontal) {

            List<Stripe> stripes = manager.getStripes();

            for (int i = 0; i < stripes.size(); i++) {
                Stripe stripe = stripes.get(i);
                double gd = stripe.deviceStart;

				Shape stripeShape = new Rectangle2D.Double(0, gd, getManager()
                        .getProjection().getDevice2D().getDeviceWidth(),
                                                         Math.abs(stripe.getDeviceInterval()));

                StripePaint bandPaint = stripe.getPaint();
                if (bandPaint.getStripeColor() != null) {
                    g2d.setColor(bandPaint.getStripeColor());
                }
                if (bandPaint.getStripePaint() != null) {
                    g2d.setPaint(bandPaint.getStripePaint());
                }

                if (bandPaint.getShadeFractions() != null
                        && bandPaint.getShadeColors() != null
                        && bandPaint.getShadeFractions().length == bandPaint
                                .getShadeColors().length) {
                    Point2D start = new Point2D.Double(stripeShape.getBounds2D()
                            .getCenterX(), stripeShape.getBounds2D().getY());
                    Point2D end = new Point2D.Double(stripeShape.getBounds2D()
                            .getCenterX(), stripeShape.getBounds2D().getY()
                            + stripeShape.getBounds2D().getHeight());

                    if (!start.equals(end)) {
                        LinearGradientPaint shader = new LinearGradientPaint(
                                                                             start, end, bandPaint.getShadeFractions(),
                                                                             bandPaint.getShadeColors());

                        g2d.setPaint(shader);

                    }
                }

                g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                     java.awt.AlphaComposite.SRC_OVER, getAlpha()));

                g2d.fill(stripeShape);

                g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                     java.awt.AlphaComposite.SRC_OVER, 1f));

                if (stripe.getAnnotation() != null) {
                    g2d.drawString(stripe.getAnnotation(), 10, (int) gd - 2);
                }
            }

        }
    }
}
