/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.point;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Point2D;

/**
 * <code>Point</code>
 *
 * @since 1.0
 * 
 * @author sebastien janaud
 */
public class Point {

    public int type;
    public Point2D value;

    private Stroke GRID_STROK;
    private Stroke GRID_STROK_DEFAULT = new BasicStroke();

    private Color color;
    private Color colorDefault = Color.DARK_GRAY;

    public Point(int type) {
        this.type = type;
    }

    public Color getColor() {
        if (color == null) {
            return colorDefault;
        }
        else {
            return color;
        }
    }

    public void setColor(Color p) {
        color = p;

    }

}
