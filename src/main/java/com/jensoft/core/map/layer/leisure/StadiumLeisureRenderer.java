/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.leisure;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import com.jensoft.core.map.primitive.Node;

public class StadiumLeisureRenderer implements LeisureRenderer {

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

        // System.out.println("draw name :"+name);
        //
        // g2d.drawString(name, 10, 10);
        //
        // g2d.setColor(Color.RED);
        // g2d.draw(poly.getBounds2D());
        // int minx = (int)poly.getBounds2D().getMinX();
        // System.out.println("min x :"+minx);
        // int miny = (int)poly.getBounds2D().getMinY();
        // g2d.drawString(name, minx , miny);
        // g2d.drawString(name,centerX , miny);
        // g2d.drawString(name,centerX , centerY);

        // g2d.setColor(region.getRegionColor());
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
        // region.getAlphaTransparence()));
        // g2d.fill(poly);
        // g2d.setColor(region.getRegionOutlineColor());
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
        // 1f));

        // TEXTURE
        // BufferedImage bi = new BufferedImage(20,
        // 20,BufferedImage.TYPE_INT_RGB);
        // Graphics2D big2d = bi.createGraphics();
        // big2d.setColor(new Color(169,202,174));
        // big2d.fillRect(0, 0, 20, 20);
        //
        // big2d.setColor(new Color(117,171,125));
        // Line2D l1 = new Line2D.Double(6,6,14,6);
        // Line2D l2 = new Line2D.Double(10,2,10,16);
        //
        //
        // big2d.draw(l1);
        // big2d.draw(l2);
        //
        // Rectangle r = new Rectangle(0, 0, 20, 20);
        //
        // g2d.setPaint(new TexturePaint(bi, r));

        g2d.setColor(Color.GREEN);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    0.4f));
        g2d.fill(poly);
        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));
        // g2d.setColor(FilPalette.FIL_MARRON5);
        // g2d.draw(poly);

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
