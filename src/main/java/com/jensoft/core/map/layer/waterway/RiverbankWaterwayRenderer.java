/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.waterway;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import com.jensoft.core.map.primitive.Node;

public class RiverbankWaterwayRenderer implements WaterwayRenderer {

    @Override
    public boolean paintWaterway(Graphics2D g2d, Waterway waterway) {

        Polygon poly = new Polygon();

        for (Node n : waterway.getPrimitive().getNodes()) {
            poly.addPoint((int) n.getProjection().getX(), (int) n
                    .getProjection().getY());
        }
        // for(Point2D pt : waterway.getProjectionLimits()) {
        //
        //
        // poly.addPoint((int)pt.getX(),(int)pt.getY());
        // }

        int centerX = (int) poly.getBounds2D().getCenterX();
        int centerY = (int) poly.getBounds2D().getCenterY();

        String name = waterway.getName();

        g2d.setFont(new Font("verdana", Font.PLAIN, 10));
        FontMetrics fm = g2d.getFontMetrics();
        int w = fm.stringWidth(name);

        g2d.setColor(new Color(181, 208, 208));
        g2d.fill(poly);

        g2d.setColor(Color.WHITE);
        g2d.drawString(name, centerX - w / 2 - 1, centerY);
        g2d.drawString(name, centerX - w / 2 + 1, centerY);
        g2d.drawString(name, centerX - w / 2, centerY - 1);
        g2d.drawString(name, centerX - w / 2, centerY + 1);

        g2d.setColor(Color.BLACK);
        g2d.drawString(name, centerX - w / 2, centerY);

        return false;
    }

}
