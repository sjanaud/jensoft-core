/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray.painter.fill;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.palette.color.ColorPalette;
import com.jensoft.core.plugin.ray.Ray;
import com.jensoft.core.plugin.ray.Ray.RayNature;

/**
 * define ray fill type 1
 */
public class RayFill1 extends AbstractRayFill {

    /**
     * create ray fill 1
     */
    public RayFill1() {

    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.ray.painter.fill.AbstractRayFill#paintRayFill(java.awt.Graphics2D, com.jensoft.core.plugin.ray.Ray)
     */
    @Override
    public void paintRayFill(Graphics2D g2d, Ray ray) {
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

        // Graphics2D partGraphics =bar.getPart().getGraphics2D();

        Rectangle2D boun2D = ray.getRayShape().getBounds2D();

        // g1
        Point2D start = new Point2D.Double(boun2D.getX(), boun2D.getCenterY());
        Point2D end = new Point2D.Double(boun2D.getX() + boun2D.getWidth(),
                                         boun2D.getCenterY());
        float[] dist = { 0.0f, 0.5f, 1.0f };

        Color cBase = ray.getThemeColor();
        if (cBase == null) {
            cBase = ray.getHost().getProjection().getThemeColor();
        }

        Color brighther1 = ColorPalette.brighter(cBase, 0.8f);
        // brighther1 = ColorPalette.brighter(brighther1, 0.8f);

        Color[] colors = { brighther1, cBase, brighther1 };

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist,
                                                        colors);

        g2d.setPaint(p);
        // partGraphics.setPaint(p);

        g2d.fill(ray.getRayShape());
        // partGraphics.fill(bar.getBarShape());
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

        Color cBase = ray.getThemeColor();
        Color brighther1 = ColorPalette.brighter(cBase, 0.8f);

        Rectangle2D boun2D = ray.getRayShape().getBounds2D();

        Point2D start = new Point2D.Double(boun2D.getCenterX(), boun2D.getY());
        Point2D end = new Point2D.Double(boun2D.getCenterX(), boun2D.getY()
                + boun2D.getHeight());
        float[] dist = { 0.0f, 0.5f, 1.0f };
        Color[] colors = { brighther1, cBase, brighther1 };

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist,
                                                        colors);

        g2d.setPaint(p);

        g2d.fill(ray.getRayShape());
    }

}
