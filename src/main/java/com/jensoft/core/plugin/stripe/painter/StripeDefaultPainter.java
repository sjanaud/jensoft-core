/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stripe.painter;

import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import com.jensoft.core.plugin.stripe.Stripe;
import com.jensoft.core.plugin.stripe.StripePlugin.StripeOrientation;
import com.jensoft.core.plugin.stripe.manager.StripeManager;

/**
 * BandDefaultPainter is the painter for Grid define by the layout manager
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
    public void doPaintBand(Graphics2D g2d) {
        StripeManager manager = getLayout();
        if (manager == null) {
            return;
        }
        g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                             java.awt.AlphaComposite.SRC_OVER, 1f));
        if (manager.getStripeOrientation() == StripeOrientation.Vertical) {

            List<Stripe> bands = manager.getStripes();

            for (int i = 0; i < bands.size(); i++) {
                Stripe band = bands.get(i);
                double gd = band.deviceStart;

                Shape bandShape = new Rectangle2D.Double(gd, 0,
                                                         band.getDeviceInterval(), getLayout().getWindow2D()
                                                                 .getDevice2D().getDeviceHeight());

                StripePaint bandPaint = band.getPaint();
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
                    Point2D start = new Point2D.Double(bandShape.getBounds2D()
                            .getX(), bandShape.getBounds2D().getCenterY());
                    Point2D end = new Point2D.Double(bandShape.getBounds2D()
                            .getX() + bandShape.getBounds2D().getWidth(),
                                                     bandShape.getBounds2D().getCenterY());

                    if (!start.equals(end)) {
                        LinearGradientPaint shader = new LinearGradientPaint(
                                                                             start, end, bandPaint.getShadeFractions(),
                                                                             bandPaint.getShadeColors());

                        g2d.setPaint(shader);

                    }
                }

                g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                     java.awt.AlphaComposite.SRC_OVER, getAlpha()));

                g2d.fill(bandShape);

                g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                     java.awt.AlphaComposite.SRC_OVER, 1f));
                if (band.getAnnotation() != null) {
                    g2d.drawString(band.getAnnotation(), (int) gd + 2, 10);
                }
            }

        }

        if (manager.getStripeOrientation() == StripeOrientation.Horizontal) {

            List<Stripe> grids = manager.getStripes();

            for (int i = 0; i < grids.size(); i++) {
                Stripe band = grids.get(i);
                double gd = band.deviceStart;

                Shape bandShape = new Rectangle2D.Double(0, gd, getLayout()
                        .getWindow2D().getDevice2D().getDeviceWidth(),
                                                         Math.abs(band.getDeviceInterval()));

                StripePaint bandPaint = band.getPaint();
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
                    Point2D start = new Point2D.Double(bandShape.getBounds2D()
                            .getCenterX(), bandShape.getBounds2D().getY());
                    Point2D end = new Point2D.Double(bandShape.getBounds2D()
                            .getCenterX(), bandShape.getBounds2D().getY()
                            + bandShape.getBounds2D().getHeight());

                    if (!start.equals(end)) {
                        LinearGradientPaint shader = new LinearGradientPaint(
                                                                             start, end, bandPaint.getShadeFractions(),
                                                                             bandPaint.getShadeColors());

                        g2d.setPaint(shader);

                    }
                }

                g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                     java.awt.AlphaComposite.SRC_OVER, getAlpha()));

                g2d.fill(bandShape);

                g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                     java.awt.AlphaComposite.SRC_OVER, 1f));

                if (band.getAnnotation() != null) {
                    g2d.drawString(band.getAnnotation(), 10, (int) gd - 2);
                }
            }

        }
    }
}
