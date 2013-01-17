/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.railway;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import com.jensoft.core.map.primitive.Node;

public class RailRenderer implements RailwayRenderer {

    @Override
    public boolean paintRailway(Graphics2D g2d, Railway railway) {

        GeneralPath path = new GeneralPath();

        int count = 0;
        for (Node n : railway.getPrimitive().getNodes()) {

            Point2D p2d = n.getProjection();
            if (count == 0) {
                path.moveTo(p2d.getX(), p2d.getY());
            }
            else {
                path.lineTo(p2d.getX(), p2d.getY());
            }
            count++;
        }

        int centerX = (int) path.getBounds2D().getCenterX();
        int centerY = (int) path.getBounds2D().getCenterY();

        String name = railway.getName();

        g2d.setFont(new Font("verdana", Font.PLAIN, 10));
        FontMetrics fm = g2d.getFontMetrics();
        int w = fm.stringWidth(name);

        g2d.setColor(Color.MAGENTA);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    0.4f));
        g2d.setStroke(new BasicStroke(12f, BasicStroke.CAP_ROUND,
                                      BasicStroke.JOIN_BEVEL));
        g2d.draw(path);
        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));

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
