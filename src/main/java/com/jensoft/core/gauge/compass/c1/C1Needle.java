/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.compass.c1;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.gauge.core.RadialGauge;
import com.jensoft.core.gauge.core.painter.NeedleGaugePainter;
import com.jensoft.core.gauge.timing.TimingEngine;
import com.jensoft.core.gauge.timing.TimingListener;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.Side;
import com.jensoft.core.palette.JennyPalette;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.palette.TexturePalette;
import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;

public class C1Needle extends NeedleGaugePainter {

    private int curentValue;

    private int oldvalue;

    public int getCurentValue() {
        return curentValue;
    }

    private int newValue;

    public void setCurentValue(int value) {
        System.out.println("SET NEW VALUE :" + value);
        System.out.println("OVERIDE CURENT :" + curentValue);

        if (timingEngine != null) {
            timingEngine.stopEngine();
        }

        newValue = value;
        timingEngine = new TimingEngine(1000);
        timingEngine.setResolution(200);
        timingEngine.addTimingListener(listener);
        // if(timingEngine == null){
        // timingEngine = new TimingEngine(1000);
        // timingEngine.setResolution(200);
        // timingEngine.addTimingListener(listener);
        // }
        timingEngine.startEngine();

        // amorti

    }

    private NeedleTimingListener listener = new NeedleTimingListener();

    class NeedleTimingListener implements TimingListener {

        private int delta;

        @Override
        public void engineStart() {
            // System.out.println("needle engine start");

        }

        @Override
        public void engineStop() {
            // System.out.println("needle engine stop");
            if (getGauge().getWindow2D() != null) {
                getGauge().getWindow2D().getDevice2D().repaintDevice();
            }
        }

        @Override
        public void startCycle() {

            // System.out.println("needle engine start cycle");

            delta = Math.abs(newValue - curentValue);
            // System.out.println("new value :"+newValue);
            // System.out.println("old value :"+curentValue);
            // System.out.println("delta :"+delta);

            oldvalue = curentValue;

        }

        @Override
        public void stopCycle() {
            System.out.println("needle engine stop cycle");

            // curentValue = newValue;
            getGauge().getWindow2D().getDevice2D().repaintDevice();

        }

        @Override
        public void timingEvent(float fraction) {
            System.out.println("needle timing event :" + fraction);

            if (newValue > curentValue) {
                curentValue = oldvalue + (int) (delta * fraction);

            }
            else {
                curentValue = oldvalue - (int) (delta * fraction);
            }
            System.out.println("set curent :" + curentValue);

            // System.out.println("Curent Value :"+curentValue);
            // System.out.println("gauge :"+getGauge());
            // System.out.println("gauge window:"+getGauge().getWindow2D());
            if (getGauge().getWindow2D() == null) {
                return;
            }
            getGauge().getWindow2D().getDevice2D().repaintDevice();

        }

    }

    private TimingEngine timingEngine;

    private void paintNeedle(Graphics2D g2d) {

        double centerX = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
        double centerY = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
        int radius = getGauge().getRadius() - 50;

        GeneralMetricsPath pathManager = getGauge().getMetricsManager();
        pathManager.setFontRenderContext(g2d.getFontRenderContext());

        getGauge().getMetricsManager().setWindow2d(getGauge().getWindow2D());

        // pathManager.setFontRenderContext(g2d.getFontRenderContext());
        // int startAngleDegreee = 200;
        // int endAngleDegree = -20;
        // Arc2D arc2d = new Arc2D.Double(centerX - radius ,centerY-radius ,
        // 2*radius, 2*radius, startAngleDegreee, endAngleDegree
        // - startAngleDegreee, Arc2D.OPEN);
        //
        // pathManager.append(arc2d);
        //
        //
        //
        // getGauge().getMetricsManager().setWindow2d(getGauge().getWindow2D());

        Point2D center = new Point2D.Double(centerX, centerY);

        Point2D pNeedle2 = getGauge().getMetricsManager().getRadialPoint(
                                                                         curentValue, 60, Side.SideLeft);// getGauge().getMetricsManager().getPoint(125);//new
                                                                                                         // Point2D.Double(centerX+radius
                                                                                                         // *
                                                                                                         // Math.cos(Math.toRadians(angle)),centerY-radius
                                                                                                         // *
                                                                                                         // Math.sin(Math.toRadians(angle)));
        Point2D pNeedle2TT = getGauge().getWindow2D().userToPixel(pNeedle2);
        Line2D lNeedle = new Line2D.Double(center.getX(), center.getY(),
                                           pNeedle2.getX(), pNeedle2.getY());
        // Line2D lNeedle = new
        // Line2D.Double(pNeedle1.getX(),pNeedle1.getY(),pNeedle2TT.getX(),pNeedle2TT.getY());

        BasicStroke stroke = new BasicStroke(4, BasicStroke.CAP_ROUND,
                                             BasicStroke.JOIN_ROUND);
        BasicStroke stroke2 = new BasicStroke(10, BasicStroke.CAP_ROUND,
                                              BasicStroke.JOIN_ROUND);
        Shape tShape = stroke.createStrokedShape(lNeedle);
        Shape tShape2 = stroke2.createStrokedShape(lNeedle);

        Area area = new Area(tShape);
        Area area2 = new Area(tShape2);

        // g2d.setStroke(new
        // BasicStroke(3,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));
        int r1 = JennyPalette.JENNY6.getRed();
        int g1 = JennyPalette.JENNY6.getGreen();
        int b1 = JennyPalette.JENNY6.getBlue();
        g2d.setColor(new Color(r1, g1, b1, 120));

        // g2d.setPaint(TexturePalette.getT3());
        // g2d.fill(area2);

        // g2d.fill(area);

        g2d.setColor(NanoChromatique.ORANGE);
        g2d.setStroke(new BasicStroke(1f));
        // g2d.draw(area2);

        // deco gradient
        int w0 = 25;
        int r = NanoChromatique.ORANGE.getRed();
        int g = NanoChromatique.ORANGE.getGreen();
        int b = NanoChromatique.ORANGE.getBlue();

        // Ellipse2D cacheCenter0 = new
        // Ellipse2D.Double(centerX-w0,centerY-w0,2*w0,2*w0);
        // RoundGradientPaint rgp0 = new RoundGradientPaint(centerX, centerY,
        // new Color(r,g,b,250),
        //
        // new Point2D.Double(5, 25), new Color(r,g,b,40));
        //
        //
        // g2d.setPaint(rgp0);
        // g2d.fill(cacheCenter0);

        // cache

        int w = 15;
        Ellipse2D cacheCenter = new Ellipse2D.Double(centerX - w, centerY - w,
                                                     2 * w, 2 * w);

        // RadialGradientPaint rgp2 = new RadialGradientPaint(new
        // Point(centerX,centerY), radius, fractions, colors)
        // g2d.setPaint(TexturePalette.getT3());
        // g2d.fill(cacheCenter);

        // g2d.setColor(FilPalette.FIL_GRIS4);

        Area aCache = new Area(cacheCenter);

        aCache.add(area2);

        g2d.setPaint(TexturePalette.getT3());
        g2d.fill(aCache);

        GeneralPath gp = new GeneralPath();
        gp.append(aCache.getPathIterator(null), false);

        g2d.setStroke(new BasicStroke(2f));
        g2d.setColor(NanoChromatique.BLUE);
        g2d.draw(gp);

        g2d.setPaint(TexturePalette.getT3());
        g2d.setColor(Color.BLACK);
        g2d.fill(aCache);

    }

    private void paintDeco1(Graphics2D g2d) {

        double centerX = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
        double centerY = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
        int radius = 15;

        Donut2D donut1 = new Donut2D();
        donut1.setCenterX((int) centerX);
        donut1.setCenterY((int) centerY);
        donut1.setInnerRadius(10);
        donut1.setOuterRadius(15);
        donut1.setStartAngleDegree(32);
        donut1.setExplose(0);

        Donut2DSlice s1 = new Donut2DSlice("D1", new Color(255, 255, 255, 80));
        s1.setAlpha(1f);
        s1.setValue(10);

        Donut2DSlice s2 = new Donut2DSlice("D2", new Color(255, 255, 255, 80));
        s2.setValue(5);
        s2.setAlpha(0f);

        Donut2DSlice s3 = new Donut2DSlice("D3", new Color(255, 255, 255, 0));
        s3.setValue(10);
        s3.setAlpha(1f);
        Donut2DSlice s4 = new Donut2DSlice("D4", new Color(255, 255, 255, 0));
        s4.setValue(5);
        s4.setAlpha(0f);
        Donut2DSlice s5 = new Donut2DSlice("D5", new Color(255, 255, 255, 0));
        s5.setValue(10);
        s5.setAlpha(1f);
        Donut2DSlice s6 = new Donut2DSlice("D6", new Color(255, 255, 255, 0));
        s6.setValue(5);
        s6.setAlpha(0f);
        Donut2DSlice s7 = new Donut2DSlice("D7", new Color(255, 255, 255, 0));
        s7.setValue(10);
        s7.setAlpha(1f);
        Donut2DSlice s8 = new Donut2DSlice("D8", new Color(255, 255, 255, 0));
        s8.setValue(5);
        s8.setAlpha(0f);

        donut1.addSlice(s1);
        donut1.addSlice(s2);
        donut1.addSlice(s3);
        donut1.addSlice(s4);
        donut1.addSlice(s5);
        donut1.addSlice(s6);
        donut1.addSlice(s7);
        donut1.addSlice(s8);

        // donut.buildDonut();

        Point2D gcenter = new Point2D.Double(centerX, centerY);
        float gradius = (float) donut1.getOuterRadius();
        float[] dist = { 0.0f, 0.5f, 1.0f };
        int r = TangoPalette.CHAMELEON1.getRed();
        int g = TangoPalette.CHAMELEON1.getGreen();
        int b = TangoPalette.CHAMELEON1.getBlue();
        Color[] colors = { new Color(r, g, b, 100), new Color(r, g, b, 250),
                new Color(r, g, b, 100) };
        RadialGradientPaint p = new RadialGradientPaint(gcenter, gradius, dist,
                                                        colors);

        // RoundGradientPaint rgp2 = new RoundGradientPaint(centerX, centerY,
        // new Color(255,255,255,200),
        //
        // new Point2D.Double(5, donut1.getExternalRadius()), new
        // Color(255,255,255,0));
        donut1.solveGeometry();
        g2d.setPaint(p);
        List<Donut2DSlice> sections = donut1.getSlices();

        for (int j = 0; j < sections.size(); j++) {

            Donut2DSlice s = sections.get(j);

            g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                 java.awt.AlphaComposite.SRC_OVER, s.getAlpha()));

            g2d.fill(s.getSlicePath());
            g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                 java.awt.AlphaComposite.SRC_OVER, 1f));

        }
    }

    @Override
    public void paintNeedle(Graphics2D g2d, RadialGauge radialGauge) {
        paintNeedle(g2d);

    }

}
