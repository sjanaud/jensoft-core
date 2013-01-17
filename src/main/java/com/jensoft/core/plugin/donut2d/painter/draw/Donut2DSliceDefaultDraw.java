/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.painter.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;

/**
 * <code>PieSliceDefaultDraw</code>
 * <p>
 * Draw donut2D slice with the given color and stroke
 * <p>
 * 
 * @author Sebastien Janaud
 */
public class Donut2DSliceDefaultDraw extends AbstractDonut2DSliceDraw {

    /** draw color */
    private Color drawColor;

    /** stroke color */
    private Stroke drawStroke;

    /** default stroke */
    private Stroke defaultStroke = new BasicStroke();

    /**
     * create empty default donut2D slice draw
     */
    public Donut2DSliceDefaultDraw() {
    }

    /**
     * create default section draw
     * 
     * @param drawColor
     *            the draw color
     * @param drawStroke
     *            the stroke
     */
    public Donut2DSliceDefaultDraw(Color drawColor, Stroke drawStroke) {
        super();
        this.drawColor = drawColor;
        this.drawStroke = drawStroke;
    }

    /**
     * @return the drawColor
     */
    public Color getDrawColor() {
        return drawColor;
    }

    /**
     * @param drawColor
     *            the drawColor to set
     */
    public void setDrawColor(Color drawColor) {
        this.drawColor = drawColor;
    }

    /**
     * @return the drawStroke
     */
    public Stroke getDrawStroke() {
        return drawStroke;
    }

    /**
     * @param drawStroke
     *            the drawStroke to set
     */
    public void setDrawStroke(Stroke drawStroke) {
        this.drawStroke = drawStroke;
    }

    @Override
    protected final void paintDonut2DSliceDraw(Graphics2D g2d, Donut2D donut2D, Donut2DSlice slice) {

        if (drawColor != null) {
            g2d.setColor(drawColor);
        }
        else {
            g2d.setColor(Color.WHITE);
        }

        if (drawStroke != null) {
            g2d.setStroke(drawStroke);
        }
        else {
            g2d.setStroke(defaultStroke);
        }

        g2d.draw(slice.getSlicePath());

    }
}
