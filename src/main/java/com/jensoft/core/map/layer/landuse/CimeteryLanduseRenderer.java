/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.landuse;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class CimeteryLanduseRenderer implements LanduseRenderer {

    @Override
    public boolean paintLanduse(Graphics2D g2d, Landuse region) {

        Polygon poly = new Polygon();
        for (Point2D pt : region.getProjectionPositions()) {
            poly.addPoint((int) pt.getX(), (int) pt.getY());
        }

        int centerX = (int) poly.getBounds2D().getCenterX();
        int centerY = (int) poly.getBounds2D().getCenterY();

        String name = region.getName();

        g2d.setFont(new Font("verdana", Font.PLAIN, 10));
        FontMetrics fm = g2d.getFontMetrics();
        int w = fm.stringWidth(name);

        BufferedImage bi = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D big2d = bi.createGraphics();
        big2d.setColor(new Color(169, 202, 174));
        big2d.fillRect(0, 0, 20, 20);

        big2d.setColor(new Color(117, 171, 125));
        Line2D l1 = new Line2D.Double(6, 6, 14, 6);
        Line2D l2 = new Line2D.Double(10, 2, 10, 16);

        big2d.draw(l1);
        big2d.draw(l2);

        Rectangle r = new Rectangle(0, 0, 20, 20);

        g2d.setPaint(new TexturePaint(bi, r));

        g2d.fill(poly);

        g2d.setColor(Color.WHITE);
        g2d.drawString(name, centerX - w / 2 - 1, centerY);
        g2d.drawString(name, centerX - w / 2 - 2, centerY);
        g2d.drawString(name, centerX - w / 2 + 1, centerY);
        g2d.drawString(name, centerX - w / 2 + 2, centerY);
        g2d.drawString(name, centerX - w / 2, centerY - 1);
        g2d.drawString(name, centerX - w / 2, centerY - 2);
        g2d.drawString(name, centerX - w / 2, centerY + 1);
        g2d.drawString(name, centerX - w / 2, centerY + 2);

        g2d.setColor(Color.BLACK);
        g2d.drawString(name, centerX - w / 2, centerY);

        return false;
    }

}
