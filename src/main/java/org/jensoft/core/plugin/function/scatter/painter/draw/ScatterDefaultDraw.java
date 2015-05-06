/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.scatter.painter.draw;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jensoft.core.plugin.function.scatter.Scatter.ScatterPoint;
import org.jensoft.core.plugin.function.scatter.painter.ScatterDraw;

/**
 * ScatterDefaultDraw knows how to draw scatter
 */
public class ScatterDefaultDraw extends ScatterDraw {

    /** outline scatter color */
    private Color outlineColor;

    /**
     * create empty draw color
     */
    public ScatterDefaultDraw() {
    }

    /**
     * create scatter draw color with specified draw outline color
     * 
     * @param outlineColor
     */
    public ScatterDefaultDraw(Color outlineColor) {
        super();
        this.outlineColor = outlineColor;
    }

    /**
     * get outline color
     * 
     * @return outline color
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    /**
     * set outline color
     * 
     * @param outlineColor
     *            the outline color to set
     */
    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    @Override
    public void paintScatter(Graphics2D g2d, ScatterPoint scatter) {

        Color cBase = scatter.getThemeColor();
        if (outlineColor != null) {
            cBase = outlineColor;
        }
        else if (scatter.getThemeColor() != null) {
            cBase = scatter.getThemeColor();
        }
        if (cBase != null) {
            g2d.setColor(cBase);
            g2d.draw(scatter.getPrimitiveShape());
        }
    }

}
