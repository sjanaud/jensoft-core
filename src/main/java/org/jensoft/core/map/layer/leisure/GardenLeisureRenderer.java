/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.leisure;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import org.jensoft.core.map.primitive.Node;

public class GardenLeisureRenderer implements LeisureRenderer {

    @Override
    public boolean paintLeisure(Graphics2D g2d, Leisure leisure) {

        Polygon poly = new Polygon();
        for (Node n : leisure.getPrimitive().getNodes()) {
            poly.addPoint((int) n.getProjection().getX(), (int) n
                    .getProjection().getY());
        }
        int centerX = (int) poly.getBounds2D().getCenterX();
        int centerY = (int) poly.getBounds2D().getCenterY();

        String name = leisure.getName();

        g2d.setFont(new Font("verdana", Font.PLAIN, 10));
        FontMetrics fm = g2d.getFontMetrics();
        int w = fm.stringWidth(name);

        g2d.setColor(new Color(186, 226, 131));
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
