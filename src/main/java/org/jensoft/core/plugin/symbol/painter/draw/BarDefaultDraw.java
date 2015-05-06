/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.symbol.painter.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import org.jensoft.core.plugin.symbol.BarSymbol;

/**
 * Classic bar draw knows how to draw bar bar with given stroke and color.
 * <p>
 * if outline color is null, bar theme color is use to draw bar.
 * </p>
 */
public class BarDefaultDraw extends AbstractBarDraw {

    /** the outline color to draw bar */
    private Color outlineColor;

    /** the outline stroke to draw bar */
    private Stroke outlineStroke;

    /**
     * create a default bar draw with default color
     */
    public BarDefaultDraw() {
    }

    /**
     * create a bar draw with specified outline color
     * 
     * @param outlineColor
     *            the outline color
     */
    public BarDefaultDraw(Color outlineColor) {
        super();
        this.outlineColor = outlineColor;
    }

    /**
     * create a bar draw with specified color and stroke
     * 
     * @param outlineColor
     *            the bar outline color to set
     * @param outlineStroke
     *            the bar outline stroke to set
     */
    public BarDefaultDraw(Color outlineColor, Stroke outlineStroke) {
        super();
        this.outlineColor = outlineColor;
        this.outlineStroke = outlineStroke;
    }

    /**
     * @return the outlineStroke
     */
    public Stroke getOutlineStroke() {
        return outlineStroke;
    }

    /**
     * @param outlineStroke
     *            the outlineStroke to set
     */
    public void setOutlineStroke(Stroke outlineStroke) {
        this.outlineStroke = outlineStroke;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    @Override
    protected void paintBarDraw(Graphics2D g2d, BarSymbol bar) {

        // Graphics2D partGraphics =bar.getPart().getGraphics2D();

        Color cBase = bar.getThemeColor();
        if (outlineColor != null) {
            cBase = outlineColor;
        }
        if (outlineStroke == null) {
            outlineStroke = new BasicStroke(1f);
        }

        g2d.setColor(cBase);
        // partGraphics.setColor(cBase);
        g2d.setStroke(outlineStroke);
        if (bar != null && bar.getBarShape() != null)
        {
            g2d.draw(bar.getBarShape());
            // partGraphics.draw(bar.getBarShape());
        }
    }

}
