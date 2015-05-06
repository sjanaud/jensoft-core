/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.ray.painter.effect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jensoft.core.plugin.ray.Ray;
import org.jensoft.core.plugin.ray.Ray.RayNature;

/**
 * define a ray effect type 1
 */
public class RayEffect1 extends AbstractRayEffect {

    /**
     * create ray effect
     */
    public RayEffect1() {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.ray.painter.effect.AbstractRayEffect#paintRayEffect(java.awt.Graphics2D, com.jensoft.core.plugin.ray.Ray)
     */
    @Override
    public void paintRayEffect(Graphics2D g2d, Ray ray) {
        if (ray.getRayNature() == RayNature.XRay) {
            fillXRay(g2d, ray);
        }
        if (ray.getRayNature() == RayNature.YRay) {
            fillYRay(g2d, ray);
        }
    }

    /**
     * fill ray X
     * 
     * @param g2d
     *            the graphics context
     * @param ray
     *            the ray to fill
     */
    private void fillXRay(Graphics2D g2d, Ray ray) {
        Rectangle2D boun2D2 = ray.getRayShape();

        Rectangle2D shapeEffect = new Rectangle2D.Double(boun2D2.getX(),
                                                         boun2D2.getY(), boun2D2.getWidth() / 2, boun2D2.getHeight());

        Point2D start = null;
        Point2D end = null;
        if (ray.isAscent()) {
            start = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
            end = new Point2D.Double(boun2D2.getX() + boun2D2.getWidth(),
                                     boun2D2.getY() + boun2D2.getHeight());
        }
        else if (ray.isDescent()) {
            start = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
            end = new Point2D.Double(boun2D2.getX(), boun2D2.getY()
                    + boun2D2.getHeight());
        }
        float[] dist = { 0.0f, 1.0f };
        Color[] colors = { new Color(255, 255, 255, 120),
                new Color(255, 255, 255, 80) };

        LinearGradientPaint p2 = new LinearGradientPaint(start, end, dist,
                                                         colors);

        g2d.setPaint(p2);

        g2d.fill(shapeEffect);

    }

    /**
     * fill ray Y
     * 
     * @param g2d
     *            the graphics context
     * @param ray
     *            the ray to fill
     */
    private void fillYRay(Graphics2D g2d, Ray ray) {

        Rectangle2D boun2D2 = ray.getRayShape();

        Rectangle2D shapeEffect = new Rectangle2D.Double(boun2D2.getX(),
                                                         boun2D2.getY(), boun2D2.getWidth(), boun2D2.getHeight() / 2);

        Point2D start = null;
        Point2D end = null;
        if (ray.isAscent()) {
            start = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
            end = new Point2D.Double(boun2D2.getX(), boun2D2.getY()
                    + boun2D2.getHeight() / 2);
        }
        else if (ray.isDescent()) {
            start = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
            end = new Point2D.Double(boun2D2.getX(), boun2D2.getY()
                    + boun2D2.getHeight() / 2);
        }
        float[] dist = { 0.0f, 1.0f };
        Color[] colors = { new Color(255, 255, 255, 120),
                new Color(255, 255, 255, 80) };

        if (!start.equals(end)) {
            LinearGradientPaint p2 = new LinearGradientPaint(start, end, dist,
                                                             colors);

            g2d.setPaint(p2);

            g2d.fill(shapeEffect);
        }
    }
}
