/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PieSlice;

/**
 * <code>PieSliceDefaultDraw</code>
 * <p>
 * Draw pie slice with the given color and stroke
 * <p>
 * <img src="doc-files/PieSliceDraw.png"> <br>
 * 
 * @author Sebastien Janaud
 */
public class PieSliceDefaultDraw extends AbstractPieSliceDraw {

    /** draw color */
    private Color drawColor;

    /** stroke color */
    private Stroke drawStroke;

    /** default stroke */
    private Stroke defaultStroke = new BasicStroke();

    /**
     * create empty default pie slice draw
     */
    public PieSliceDefaultDraw() {
    }

    /**
     * create default section draw
     * 
     * @param drawColor
     *            the draw color
     * @param drawStroke
     *            the stroke
     */
    public PieSliceDefaultDraw(Color drawColor, Stroke drawStroke) {
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

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.painter.draw.AbstractPieSliceDraw#paintPieSliceDraw(java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie, com.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    protected final void paintPieSliceDraw(Graphics2D g2d, Pie pie, PieSlice slice) {

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
