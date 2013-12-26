/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.env;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import com.jensoft.core.device.PartBuffer;
import com.jensoft.core.drawable.screw.Posidrive;
import com.jensoft.core.drawable.screw.Torx;
import com.jensoft.core.plugin.gauge.core.RadialGauge;

public class ClassicEnvelop extends EnvelopGaugePainter {

    public ClassicEnvelop() {

    }

    private PartBuffer envelopPart;

  

    @Override
    public void paintEnvelop(Graphics2D g2d, RadialGauge radialGauge) {
        System.out.println("paint cisero env.");
        double centerX = radialGauge.getWindow2D()
                .userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();
        double centerY = radialGauge.getWindow2D()
                .userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();
        int radius = radialGauge.getRadius();
        int deltaInternal = 4;
        int deltaExternal = 40;
        int radiusInternal = radius + deltaInternal;
        int radiusExternal = radius + deltaExternal;

        radius = radiusExternal;
        if (envelopPart == null) {

            // System.out.println("null");
            envelopPart = new PartBuffer(centerX - radius / 2, centerY - radius
                    / 2, 2 * radius, 2 * radius);
            Graphics2D g2dPart = envelopPart.getBuffer().createGraphics();
            g2dPart.setRenderingHints(g2d.getRenderingHints());
            g2dPart.translate(-centerX + radius, -centerY + radius);
            // base

            Ellipse2D eInternal = new Ellipse2D.Double(
                                                       centerX - radiusInternal, centerY - radiusInternal,
                                                       2 * radiusInternal, 2 * radiusInternal);
            Ellipse2D eExternal = new Ellipse2D.Double(
                                                       centerX - radiusExternal, centerY - radiusExternal,
                                                       2 * radiusExternal, 2 * radiusExternal);

            Point2D start = new Point2D.Double(centerX, centerY - radius);
            Point2D end = new Point2D.Double(centerX, centerY + radius);
            float[] dist = { 0.0f, 0.5f, 1.0f };
            Color[] colors = { Color.DARK_GRAY, Color.LIGHT_GRAY, Color.BLACK };
            LinearGradientPaint p = new LinearGradientPaint(start, end, dist,
                                                            colors);

            g2dPart.setPaint(p);
            g2dPart.fill(eExternal);

            Point2D start2 = new Point2D.Double(centerX, centerY - radius);
            Point2D end2 = new Point2D.Double(centerX, centerY + radius);
            float[] dist2 = { 0.0f, 1.0f };
            Color[] colors2 = { Color.BLACK, Color.WHITE };
            LinearGradientPaint p2 = new LinearGradientPaint(start2, end2,
                                                             dist2, colors2);

            g2dPart.setPaint(p2);
            g2dPart.fill(eInternal);

            // cerclage
            int deltaInternal2 = 0;
            int deltaExternal2 = 4;
            int radiusInternal2 = radiusInternal + deltaInternal2;
            int radiusExternal2 = radiusExternal - deltaExternal2;
            Ellipse2D eInternal2 = new Ellipse2D.Double(centerX
                    - radiusInternal2, centerY - radiusInternal2,
                                                        2 * radiusInternal2, 2 * radiusInternal2);
            Ellipse2D eExternal2 = new Ellipse2D.Double(centerX
                    - radiusExternal2, centerY - radiusExternal2,
                                                        2 * radiusExternal2, 2 * radiusExternal2);

            Area aI = new Area(eInternal2);
            Area aE = new Area(eExternal2);

            aE.subtract(aI);

            g2dPart.setColor(Color.RED);
            // g2d.draw(aE);

            double p1IX = centerX + radiusInternal2
                    * Math.cos(Math.toRadians(-10));
            double p1IY = centerY - radiusInternal2
                    * Math.sin(Math.toRadians(-10));

            double p1EX = centerX + radiusExternal2
                    * Math.cos(Math.toRadians(-10));
            double p1EY = centerY - radiusExternal2
                    * Math.sin(Math.toRadians(-10));
            // Line2D eTestP1 = new Line2D.Double(p1IX-2,p1IY-2,p1EX-2,p1EY-2);
            // g2d.draw(eTestP1);

            double p2IX = centerX + radiusInternal2
                    * Math.cos(Math.toRadians(190));
            double p2IY = centerY - radiusInternal2
                    * Math.sin(Math.toRadians(190));

            double p2EX = centerX + radiusExternal2
                    * Math.cos(Math.toRadians(190));
            double p2EY = centerY - radiusExternal2
                    * Math.sin(Math.toRadians(190));
            // Line2D eTestP2 = new Line2D.Double(p2IX-2,p2IY-2,p2EX-2,p2EY-2);
            // g2d.draw(eTestP2);

            Arc2D a1 = new Arc2D.Double(centerX - radiusInternal2, centerY
                    - radiusInternal2, 2 * radiusInternal2,
                                        2 * radiusInternal2, -10, 200, Arc2D.OPEN);
            // g2d.draw(a1);

            Arc2D a2 = new Arc2D.Double(centerX - radiusExternal2, centerY
                    - radiusExternal2, 2 * radiusExternal2,
                                        2 * radiusExternal2, 190, -200, Arc2D.OPEN);
            // g2d.draw(a2);

            GeneralPath path = new GeneralPath();
            path.append(a1, false);
            path.lineTo(p2IX, p2IY);
            path.append(a2, true);
            path.closePath();

            Point2D start3 = new Point2D.Double(centerX, centerY
                    - radiusExternal2);
            Point2D end3 = new Point2D.Double(centerX, centerY
                    + radiusExternal2);
            float[] dist3 = { 0.0f, 1.0f };
            Color[] colors3 = { Color.WHITE, Color.BLACK.brighter() };
            LinearGradientPaint p3 = new LinearGradientPaint(start3, end3,
                                                             dist3, colors3);
            g2dPart.setPaint(p3);
            g2dPart.fill(aE);

            // g2d.setColor(Color.LIGHT_GRAY);
            Point2D start4 = new Point2D.Double(centerX, centerY
                    - radiusExternal2);
            Point2D end4 = new Point2D.Double(centerX, centerY);
            float[] dist4 = { 0.0f, 1.0f };
            Color[] colors4 = { Color.LIGHT_GRAY, Color.DARK_GRAY };
            LinearGradientPaint p4 = new LinearGradientPaint(start4, end4,
                                                             dist4, colors4);
            g2dPart.setPaint(p4);
            g2dPart.fill(path);

            int deltaRadius = radiusExternal2 - radiusInternal2;
            double starPositionRadius = radiusInternal2 + deltaRadius / 2;

            g2dPart.setPaint(null);
            double pxstar1 = centerX + starPositionRadius
                    * Math.cos(Math.toRadians(90));
            double pystar1 = centerY - starPositionRadius
                    * Math.sin(Math.toRadians(90));
            Torx t1 = new Torx(pxstar1, pystar1, 12);
            t1.draw(g2dPart);

            double pxstar2 = centerX + starPositionRadius
                    * Math.cos(Math.toRadians(225));
            double pystar2 = centerY - starPositionRadius
                    * Math.sin(Math.toRadians(225));
            Torx t2 = new Torx(pxstar2, pystar2, 12);
            t2.draw(g2dPart);

            double pxstar3 = centerX + starPositionRadius
                    * Math.cos(Math.toRadians(-45));
            double pystar3 = centerY - starPositionRadius
                    * Math.sin(Math.toRadians(-45));
            Torx t3 = new Torx(pxstar3, pystar3, 12);
            t3.draw(g2dPart);

            double pxstar4 = centerX + starPositionRadius
                    * Math.cos(Math.toRadians(45));
            double pystar4 = centerY - starPositionRadius
                    * Math.sin(Math.toRadians(45));
            Torx c4 = new Torx(pxstar4, pystar4, 12);
            c4.draw(g2dPart);

            double pxstar5 = centerX + starPositionRadius
                    * Math.cos(Math.toRadians(135));
            double pystar5 = centerY - starPositionRadius
                    * Math.sin(Math.toRadians(135));
            Torx f5 = new Torx(pxstar5, pystar5, 12);
            f5.draw(g2dPart);

            double pxstar6 = centerX + starPositionRadius
                    * Math.cos(Math.toRadians(270));
            double pystar6 = centerY - starPositionRadius
                    * Math.sin(Math.toRadians(270));
            Torx b6 = new Torx(pxstar6, pystar6, 12);
            b6.draw(g2dPart);

            double pxstar7 = centerX + starPositionRadius
                    * Math.cos(Math.toRadians(0));
            double pystar7 = centerY - starPositionRadius
                    * Math.sin(Math.toRadians(0));
            Posidrive b7 = new Posidrive(pxstar7, pystar7, 12);
            b7.draw(g2dPart);

            double pxstar8 = centerX + starPositionRadius
                    * Math.cos(Math.toRadians(180));
            double pystar8 = centerY - starPositionRadius
                    * Math.sin(Math.toRadians(180));
            Torx b8 = new Torx(pxstar8, pystar8, 12);
            b8.draw(g2dPart);

            g2dPart.drawImage(envelopPart.getBuffer(), (int) centerX - radius,
                              (int) centerY - radius, 2 * radius, 2 * radius, null);

        }
        else {
            // System.out.println(" paint from cache ENVELOP");
            g2d.drawImage(envelopPart.getBuffer(), (int) centerX - radius,
                          (int) centerY - radius, 2 * radius, 2 * radius, null);
        }

    }

}
