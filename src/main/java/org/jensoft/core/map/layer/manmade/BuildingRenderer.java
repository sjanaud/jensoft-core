/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.manmade;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jensoft.core.map.primitive.Node;

public class BuildingRenderer implements ManMadeRenderer {

    private Font font = new Font("Sansation", Font.PLAIN, 10);

    @Override
    public boolean paintManMade(Graphics2D g2d, ManMade manMade) {

        Polygon poly = new Polygon();
        for (Node n : manMade.getPrimitive().getNodes()) {
            poly.addPoint((int) n.getProjection().getX(), (int) n
                    .getProjection().getY());
        }

        // g2d.setColor(new Color(204,153,153));
        //g2d.setColor(new Color(221, 155, 155));
        g2d.setColor(new Color(193, 176, 175));
        g2d.fill(poly);
        g2d.setColor(new Color(138, 108, 149));
        g2d.draw(poly);
        g2d.setFont(font);
        String name = manMade.getName();
        if (name != null && name.length() > 0) {

            float wrappingWidth = (float) poly.getBounds2D().getWidth();

            List<TextLayout> layouts = getLayouts(wrappingWidth, name,
                                                  g2d.getFontRenderContext());
            g2d.setColor(java.awt.Color.black); // or a property
            // g2d.draw(poly.getBounds2D());

            int penX = (int) poly.getBounds2D().getX();
            int penY = (int) poly.getBounds2D().getY();
            Point pen = new Point(penX, penY);

            g2d.setFont(font);

            Iterator it0 = layouts.iterator();
            float hLayouts = 0;
            while (it0.hasNext()) {
                TextLayout layout = (TextLayout) it0.next();
                hLayouts += layout.getBounds().getHeight();
            }

            pen.y = pen.y + (int) (poly.getBounds2D().getHeight() - hLayouts)
                    / 2;
            Iterator it = layouts.iterator();
            while (it.hasNext()) {
                TextLayout layout = (TextLayout) it.next();

                // System.out.println("ascent :"+layout.getAscent());
                pen.x = (int) (poly.getBounds2D().getX() + (wrappingWidth - layout
                        .getAdvance()) / 2);
                pen.y += layout.getAscent();

                g2d.setColor(java.awt.Color.white);
                layout.draw(g2d, pen.x + 1, pen.y);
                layout.draw(g2d, pen.x - 1, pen.y);
                layout.draw(g2d, pen.x, pen.y + 1);
                layout.draw(g2d, pen.x, pen.y - 1);

                g2d.setColor(new Color(34, 89, 255));
                layout.draw(g2d, pen.x, pen.y);
                pen.y += layout.getDescent();
            }

        }

        // ////////////////////NAME FOR BUILDING
        // int centerX = (int) poly.getBounds2D().getCenterX();
        // int centerY = (int) poly.getBounds2D().getCenterY();
        //
        // String name = manMade.getName();
        //
        // g2d.setFont(new Font("verdana",Font.PLAIN,10));
        // FontMetrics fm = g2d.getFontMetrics();
        // int w = fm.stringWidth(name);
        //
        //
        //
        //
        //
        // g2d.setColor(Color.WHITE);
        // g2d.drawString(name,centerX - w/2 - 1, centerY);
        // g2d.drawString(name,centerX - w/2 + 1, centerY);
        // g2d.drawString(name,centerX - w/2 , centerY - 1);
        // g2d.drawString(name,centerX - w/2 , centerY + 1);
        //
        // g2d.setColor(Color.BLACK);
        // g2d.drawString(name,centerX - w/2, centerY);

        return false;
    }

    private List<TextLayout> getLayouts(float wrappingWidth, String text,
            FontRenderContext frc) {
        List<TextLayout> layouts = new ArrayList<TextLayout>();

        AttributedString attrStr = new AttributedString(text);
        attrStr.addAttribute(TextAttribute.FONT, font, 0, text.length());
        LineBreakMeasurer measurer = new LineBreakMeasurer(
                                                           attrStr.getIterator(), frc);

        while (measurer.getPosition() < text.length()) {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }

        return layouts;
    }

}
