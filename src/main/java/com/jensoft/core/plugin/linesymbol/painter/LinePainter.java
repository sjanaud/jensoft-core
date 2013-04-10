/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.linesymbol.painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import com.jensoft.core.plugin.linesymbol.core.LineSymbolComponent;

/**
 * <code>LinePainter</code> pain the line symbol
 */
public class LinePainter extends AbstractLinePainter {

    /** line geometry theme color */
    private Color themeColor = Color.WHITE;

    /** line geometry stroke */
    private Stroke lineStroke = new BasicStroke(1f);

    public LinePainter(Color themeColor) {
        super();
        this.themeColor = themeColor;
    }

    public LinePainter(Color themeColor, Stroke lineStroke) {
        super();
        this.themeColor = themeColor;
        this.lineStroke = lineStroke;
    }

   
    /**
     * @return the themeColor
     */
    public Color getThemeColor() {
        return themeColor;
    }

    /**
     * @param themeColor
     *            the themeColor to set
     */
    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
    }

    /**
     * @return the lineStroke
     */
    public Stroke getLineStroke() {
        return lineStroke;
    }

    /**
     * @param lineStroke
     *            the lineStroke to set
     */
    public void setLineStroke(Stroke lineStroke) {
        this.lineStroke = lineStroke;
    }

    @Override
    public void paintLineSymbol(Graphics2D g2d,
            LineSymbolComponent lineSymbolComponent) {

        Line2D baseLine = lineSymbolComponent.getGeometry().getBaseLine();

        g2d.setColor(themeColor);
        g2d.setStroke(lineStroke);
        g2d.draw(baseLine);

        g2d.setStroke(new BasicStroke());
    }

}
