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
import java.util.List;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.PieToolkit;
import com.jensoft.core.plugin.pie.painter.AbstractPiePainter;
import com.jensoft.core.plugin.pie.painter.PiePainter;

/**
 * <code>PieDefaultDraw</code>
 * <p>
 * Draw all pie slice with the given color and stroke
 * <p>
 * <img src="doc-files/PieDraw.png"> <br>
 * <img src="doc-files/PieDrawAndStroke.png"> <br>
 * 
 * <pre>
 * PieDefaultDraw draw = new PieDefaultDraw();
 * draw.setDrawColor(RosePalette.EMERALD);
 * draw.setDrawStroke(new BasicStroke(1.4f));
 * pie.setPieDraw(draw);
 * </pre>
 * 
 * @see PieToolkit
 * @see Pie
 * @see PiePlugin
 * @see AbstractPieDraw
 * @see AbstractPiePainter
 * @see PiePainter
 * @author Sebastien Janaud
 */
public class PieDefaultDraw extends AbstractPieDraw {

    /** draw color */
    private Color drawColor;

    /** stroke */
    private Stroke drawStroke;

    /** default stroke */
    private Stroke defaultStroke = new BasicStroke();

    /**
     * create default draw
     */
    public PieDefaultDraw() {
    }

    /**
     * create pie draw with the specified given parameters
     * 
     * @param drawColor
     *            the draw color
     * @param stroke
     *            the stroke
     */
    public PieDefaultDraw(Color drawColor, Stroke stroke) {
        super();
        this.drawColor = drawColor;
        drawStroke = stroke;
    }

    /**
     * create pie draw with the specified given parameters
     * 
     * @param drawColor
     *            the draw color
     */
    public PieDefaultDraw(Color drawColor) {
        super();
        this.drawColor = drawColor;
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
     * @see com.jensoft.core.plugin.pie.painter.draw.AbstractPieDraw#paintPieDraw(java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie)
     */
    @Override
    protected final void paintPieDraw(Graphics2D g2d, Pie pie) {

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

        List<PieSlice> sections = pie.getSlices();
        for (int i = 0; i < sections.size(); i++) {
            PieSlice s = sections.get(i);
            g2d.draw(s.getSlicePath());
        }
    }
}
