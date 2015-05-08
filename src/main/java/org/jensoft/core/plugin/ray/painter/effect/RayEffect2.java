/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.ray.painter.effect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jensoft.core.plugin.ray.Ray;
import org.jensoft.core.plugin.ray.Ray.RayNature;
import org.jensoft.core.projection.Projection;

public class RayEffect2 extends AbstractRayEffect {

    public RayEffect2() {

    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.ray.painter.effect.AbstractRayEffect#paintRayEffect(java.awt.Graphics2D, org.jensoft.core.plugin.ray.Ray)
     */
    @Override
    public void paintRayEffect(Graphics2D g2d, Ray ray) {
        if (ray.getRayNature() == RayNature.XRay) {
            paintEffectXRay_ef2(g2d, ray);
        }
        if (ray.getRayNature() == RayNature.YRay) {
            paintEffectYRay_ef2(g2d, ray);
        }
    }

    private void paintEffectXRay_ef2(Graphics2D g2d, Ray ray) {

        Projection w2d = ray.getHost().getProjection();

        Point2D p2dUser = null;
        if (ray.isAscent()) {
            p2dUser = new Point2D.Double(0, ray.getRayBase()
                    + ray.getRayValue());
        }
        if (ray.isDescent()) {
            p2dUser = new Point2D.Double(0, ray.getRayBase()
                    - ray.getRayValue());
        }
        Point2D p2ddevice = w2d.userToPixel(p2dUser);

        Point2D p2dUserBase = new Point2D.Double(0, ray.getRayBase());
        Point2D p2ddeviceBase = w2d.userToPixel(p2dUserBase);

        double x = ray.getRayShape().getX();
        double y = (int) p2ddevice.getY();
        if (ray.isDescent()) {
            y = (int) p2ddeviceBase.getY();
        }
        double width = ray.getRayShape().getWidth();
        double height = Math.abs(p2ddeviceBase.getY() - p2ddevice.getY());

        Shape shapeEffect = null;
        Rectangle2D barRec = new Rectangle2D.Double(x, y, width, height);
        shapeEffect = barRec;

        Rectangle2D boun2D2 = shapeEffect.getBounds2D();

        Point2D start = null;
        Point2D end = null;
        if (ray.isAscent()) {
            start = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
            end = new Point2D.Double(boun2D2.getX(), boun2D2.getY()
                    + boun2D2.getHeight());
        }
        else if (ray.isDescent()) {
            start = new Point2D.Double(boun2D2.getX(), boun2D2.getY()
                    + boun2D2.getHeight());
            end = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
        }

        float[] dist = { 0.0f, 0.33f, 0.66f, 1.0f };
        Color[] colors = { new Color(255, 255, 255, 180),
                new Color(255, 255, 255, 0), new Color(40, 40, 40, 0),
                new Color(40, 40, 40, 100) };
        LinearGradientPaint p2 = new LinearGradientPaint(start, end, dist,
                                                         colors);

        g2d.setPaint(p2);

        g2d.fill(shapeEffect);

    }

    private void paintEffectYRay_ef2(Graphics2D g2d, Ray ray) {

        Projection w2d = ray.getHost().getProjection();

        Point2D p2dUser = null;
        if (ray.isAscent()) {
            p2dUser = new Point2D.Double(ray.getRayBase() + ray.getRayValue(),
                                         0);
        }
        if (ray.isDescent()) {
            p2dUser = new Point2D.Double(ray.getRayBase() - ray.getRayValue(),
                                         0);
        }

        Point2D p2ddevice = w2d.userToPixel(p2dUser);

        Point2D p2dUserBase = new Point2D.Double(ray.getRayBase(), 0);
        Point2D p2ddeviceBase = w2d.userToPixel(p2dUserBase);

        double y = ray.getRayShape().getY();
        double x = (int) p2ddeviceBase.getX();
        if (ray.isAscent()) {
            x = (int) p2ddeviceBase.getX();
        }
        if (ray.isDescent()) {
            x = (int) p2ddevice.getX();
        }

        double height = ray.getRayShape().getHeight();
        double width = Math.abs(p2ddevice.getX() - p2ddeviceBase.getX());

        Shape shapeEffect = null;

        Rectangle2D barRec = new Rectangle2D.Double(x, y, width, height);
        shapeEffect = barRec;

        Rectangle2D boun2D2 = shapeEffect.getBounds2D();

        Point2D start = null;
        Point2D end = null;
        if (ray.isAscent()) {
            start = new Point2D.Double(boun2D2.getX() + boun2D2.getWidth(),
                                       boun2D2.getY());
            end = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
        }
        else if (ray.isDescent()) {
            start = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
            end = new Point2D.Double(boun2D2.getX() + boun2D2.getWidth(),
                                     boun2D2.getY());
        }

        float[] dist = { 0.0f, 0.33f, 0.66f, 1.0f };
        Color[] colors = { new Color(255, 255, 255, 180),
                new Color(255, 255, 255, 0), new Color(40, 40, 40, 0),
                new Color(40, 40, 40, 100) };
        LinearGradientPaint p2 = new LinearGradientPaint(start, end, dist,
                                                         colors);

        g2d.setPaint(p2);
        g2d.fill(shapeEffect);

    }
}
