/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.palette;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * <code>TexturePalette</code>
 * 
 * @author Sebastien Janaud
 */
public class TexturePalette {

    public static TexturePaint getT0() {

        int width = 4;
        int height = 4;
        BufferedImage bi = new BufferedImage(width, height,
                                             BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();
        RenderingHints hints = new RenderingHints(
                                                  RenderingHints.KEY_ANTIALIASING,
                                                  RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_DITHERING,
                  RenderingHints.VALUE_DITHER_ENABLE);
        hints.put(RenderingHints.KEY_INTERPOLATION,
                  RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        hints.put(RenderingHints.KEY_RENDERING,
                  RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(hints);
        // #242424 #1C1C1C
        // #1D1D1D #151515
        // #1A1A1A #222222
        // #131313 #1B1B1B

        // 0,0 0,1
        // 1,0 1,1
        // 2,0 2,1
        // 3,0 3,1

        // Hex to color

        Color c00 = new Color(Integer.parseInt("242424", 16));
        Color c01 = new Color(Integer.parseInt("1C1C1C", 16));
        g2d.setColor(c00);
        g2d.fillRect(0, 0, 2, 1);
        g2d.setColor(c01);
        g2d.fillRect(2, 0, 2, 1);

        Color c10 = new Color(Integer.parseInt("1D1D1D", 16));
        Color c11 = new Color(Integer.parseInt("151515", 16));
        g2d.setColor(c10);
        g2d.fillRect(0, 1, 2, 1);
        g2d.setColor(c11);
        g2d.fillRect(2, 1, 2, 1);

        Color c20 = new Color(Integer.parseInt("1A1A1A", 16));
        Color c21 = new Color(Integer.parseInt("222222", 16));
        g2d.setColor(c20);
        g2d.fillRect(0, 2, 2, 1);
        g2d.setColor(c21);
        g2d.fillRect(2, 2, 2, 1);

        Color c30 = new Color(Integer.parseInt("131313", 16));
        Color c31 = new Color(Integer.parseInt("1B1B1B", 16));
        g2d.setColor(c30);
        g2d.fillRect(0, 3, 2, 1);
        g2d.setColor(c31);
        g2d.fillRect(2, 3, 2, 1);

        Rectangle r = new Rectangle(0, 0, width, height);
        return new TexturePaint(bi, r);
    }

    public static TexturePaint getT1() {

        int width = 10;

        int height = 10;
        BufferedImage bi = new BufferedImage(width, height,
                                             BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bi.createGraphics();

        RenderingHints hints = new RenderingHints(
                                                  RenderingHints.KEY_ANTIALIASING,
                                                  RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHints(hints);

        g2d.setColor(Color.DARK_GRAY);

        g2d.fillRect(0, 0, width, height);

        Polygon p1 = new Polygon();

        p1.addPoint(width / 2, 0);

        p1.addPoint(width / 2, height);

        p1.addPoint(0, height / 2);

        Polygon p2 = new Polygon();

        p2.addPoint(width / 2, 0);

        p2.addPoint(width, 0);

        p2.addPoint(width, height / 2);

        Polygon p3 = new Polygon();

        p3.addPoint(width, height / 2);

        p3.addPoint(width, height);

        p3.addPoint(width / 2, height);

        g2d.setColor(Color.BLACK);

        g2d.fill(p1);

        g2d.fill(p2);

        g2d.fill(p3);

        Rectangle r = new Rectangle(0, 0, width, height);

        return new TexturePaint(bi, r);
    }

    public static TexturePaint getT2() {

        int width = 15;

        int height = 15;

        int centerX = width / 2;

        int centerY = height / 2;

        double totalDegree = 360;

        int radius = 6;

        double count = 6;

        double startAngleDegree = 0;

        double angleDegree = totalDegree / count;

        BufferedImage bi = new BufferedImage(width, height,
                                             BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bi.createGraphics();

        RenderingHints hints = new RenderingHints(
                                                  RenderingHints.KEY_ANTIALIASING,
                                                  RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHints(hints);

        g2d.setColor(Color.DARK_GRAY);

        g2d.fillRect(0, 0, width, height);

        GeneralPath path = new GeneralPath();

        for (int i = 0; i < count; i++) {

            double angle = startAngleDegree + i * angleDegree;

            double px = centerX + radius * Math.cos(Math.toRadians(angle));

            double py = centerY - radius * Math.sin(Math.toRadians(angle));

            if (i == 0) {
                path.moveTo(px, py);
            }
            else {
                path.lineTo(px, py);
            }

        }

        path.closePath();

        g2d.setColor(Color.black);

        g2d.fill(path);

        Rectangle r = new Rectangle(0, 0, width, height);

        return new TexturePaint(bi, r);
    }

    public static TexturePaint getT3() {
        int width = 120;

        int height = 120;

        BufferedImage bi = new BufferedImage(width, height,
                                             BufferedImage.TYPE_INT_ARGB);

        Graphics2D big = bi.createGraphics();

        big.setColor(new Color(60, 60, 60));

        big.fillRect(0, 0, width, height);

        int w = 10;

        int h = 10;

        int x = 0;

        int y;

        // i var for y

        for (int i = -110; i < 200; i = i + 2 * h) {

            y = i;

            x = -w / 2;

            // j is for iteration

            for (int j = 0; j < 30; j++) {

                // System.out.println("x,y:"+x+";"+y);

                Rectangle2D rect1 = new Rectangle2D.Double(x, y, w, h);

                Point2D start1 = new Point2D.Float(x + w / 2, y);

                Point2D end1 = new Point2D.Float(x + w / 2, y + h);

                float[] dist1 = { 0.0f, 1.0f };

                Color[] colors1 = { Color.BLACK, Color.DARK_GRAY };

                LinearGradientPaint p1 =

                        new LinearGradientPaint(start1, end1, dist1, colors1);

                big.setPaint(p1);

                big.fill(rect1);

                Rectangle2D rect0 = new Rectangle2D.Double(x, y, w, h / 2);

                Point2D start0 = new Point2D.Float(x, y / 4);

                Point2D end0 = new Point2D.Float(x + w, y / 4);

                float[] dist0 = { 0.0f, 1.0f };

                Color[] colors0 = { Color.BLACK, Color.DARK_GRAY };

                LinearGradientPaint p0 =

                        new LinearGradientPaint(start0, end0, dist0, colors0);

                big.setPaint(p0);

                big.fill(rect0);

                // Rectangle2D rect = new Rectangle2D.Double(x,y,w,h);

                //

                // Point2D start = new Point2D.Float(x, y);

                // Point2D end = new Point2D.Float(x+w, y+h);

                // float[] dist = {0.0f, 0.5f, 1.0f};

                // Color[] colors = {Color.BLACK, Color.DARK_GRAY, Color.GRAY};

                // LinearGradientPaint p =

                // new LinearGradientPaint(start, end, dist, colors);

                //

                // big.setPaint(p);

                // big.fill(rect);

                //

                x = x + w / 2;

                y = y + h / 2;

            }

        }

        // /BACKUP COOL

        // int w = 10;

        // int h = 10;

        //

        // int x=0;

        // int y;

        // //i var for y

        // for (int i = -110; i < 200; i=i+2*h) {

        // y = i;

        // x=-w/2;

        // //j is for iteration

        // for (int j = 0; j < 30; j++) {

        // System.out.println("x,y:"+x+";"+y);

        //

        // Rectangle2D rect = new Rectangle2D.Double(x,y,w,h);

        //

        // Point2D start = new Point2D.Float(x, y);

        // Point2D end = new Point2D.Float(x+w, y+h);

        // float[] dist = {0.0f, 0.5f, 1.0f};

        // Color[] colors = {Color.BLACK, Color.DARK_GRAY, Color.LIGHT_GRAY};

        // LinearGradientPaint p =

        // new LinearGradientPaint(start, end, dist, colors);

        //

        // big.setPaint(p);

        // big.fill(rect);

        //

        // x = x+w/2;

        // y = y+h/2;

        //

        // }

        // }

        Rectangle r = new Rectangle(0, 0, width, height);

        return new TexturePaint(bi, r);

    }

    public static TexturePaint getT4() {

        int width = 10;

        int height = 10;
        BufferedImage bi = new BufferedImage(width, height,
                                             BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bi.createGraphics();

        RenderingHints hints = new RenderingHints(
                                                  RenderingHints.KEY_ANTIALIASING,
                                                  RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHints(hints);

        g2d.setColor(Color.DARK_GRAY);

        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.BLACK);

        Ellipse2D e2D = new Ellipse2D.Double(1, 1, 8, 8);
        g2d.fill(e2D);

        // Rectangle2D rec = new Rectangle2D.Double(0,0,5,10);
        // Area a1= new Area(e2D);
        // a1.subtract(new Area(rec));
        //
        // g2d.setColor(Color.LIGHT_GRAY);
        // g2d.fill(a1);

        Rectangle r = new Rectangle(0, 0, width, height);

        return new TexturePaint(bi, r);
    }

}
