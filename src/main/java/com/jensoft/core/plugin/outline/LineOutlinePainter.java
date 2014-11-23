/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.outline;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import com.jensoft.core.view.ViewPart;

/**
 * OutlinePainter takes the responsibility of projection outline painting
 */
public class LineOutlinePainter extends AbstractOutlinePainter {

    private boolean north = false;
    private boolean west = false;
    private boolean south = false;
    private boolean east = false;
    private Color northColor = Color.DARK_GRAY;
    private Color westColor = Color.DARK_GRAY;
    private Color southColor = Color.DARK_GRAY;
    private Color eastColor = Color.DARK_GRAY;

    public LineOutlinePainter(boolean north, boolean west, boolean south,
            boolean east) {
        this.north = north;
        this.west = west;
        this.south = south;
        this.east = east;
    }

    public LineOutlinePainter(boolean north, Color northColor, boolean west,
            Color westColor, boolean south, Color southColor, boolean east,
            Color eastColor) {
        this.north = north;
        this.northColor = northColor;
        this.west = west;
        this.westColor = westColor;
        this.south = south;
        this.southColor = southColor;
        this.east = east;
        this.eastColor = eastColor;
    }

    public LineOutlinePainter(Color outlineColor, boolean north, boolean west,
            boolean south, boolean east) {
        this(north, outlineColor, west, outlineColor, south, outlineColor,
             east, outlineColor);
    }

    public LineOutlinePainter(Color outlineColor) {
        this(true, outlineColor, true, outlineColor, true, outlineColor, true,
             outlineColor);
    }

    /**
     * set color for all segment
     */
    @Override
    public void setOutlineColor(Color color) {
        super.setOutlineColor(color);
        northColor = color;
        westColor = color;
        southColor = color;
        eastColor = color;
    }

    private BasicStroke outlineStroke = new BasicStroke(1.4f);

    @Override
    public void doPaintOutline(Component c, Graphics2D g2d, int startX,
            int startY, int weigh, ViewPart viewPart) {

        g2d.setStroke(outlineStroke);
        if (viewPart == ViewPart.East && east) {
            Line2D l2dVertical = new Line2D.Double(startX, startY, startX,
                                                   startY - weigh);
            g2d.setColor(eastColor);
            g2d.setStroke(outlineStroke);
            g2d.draw(l2dVertical);

        }

        if (viewPart == ViewPart.West && west) {
            Line2D l2dVertical = new Line2D.Double(startX, startY, startX,
                                                   startY - weigh);
            g2d.setColor(westColor);
            g2d.setStroke(outlineStroke);
            g2d.draw(l2dVertical);

        }
        if (viewPart == ViewPart.North && north) {
            Line2D l2dHorizontal = new Line2D.Double(startX, startY, startX
                    + weigh, startY);
            g2d.setColor(northColor);
            g2d.setStroke(outlineStroke);
            g2d.draw(l2dHorizontal);

        }
        if (viewPart == ViewPart.South && south) {
            Line2D l2dHorizontal = new Line2D.Double(startX, startY, startX
                    + weigh, startY);
            g2d.setColor(southColor);
            g2d.setStroke(outlineStroke);
            g2d.draw(l2dHorizontal);

        }
    }

}
