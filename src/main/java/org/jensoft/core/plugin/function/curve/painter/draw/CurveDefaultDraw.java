/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.curve.painter.draw;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

import org.jensoft.core.plugin.function.curve.Curve;

/**
 * @author Sebastien Janaud
 */
public class CurveDefaultDraw extends AbstractCurveDraw {

    /** outline color, default dark gray */
    private Color outlineColor;

    /** default basic stroke */
    private Stroke outlineStroke = new BasicStroke();

    /**
     * create a curve draw
     */
    public CurveDefaultDraw() {
    }

    /**
     * create a curve draw with the specified color
     * 
     * @param outlineColor
     *            the outline color to set
     */
    public CurveDefaultDraw(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    /**
     * create a curve draw with specified stroke
     * 
     * @param outlineStroke
     */
    public CurveDefaultDraw(Stroke outlineStroke) {
        super();
        this.outlineStroke = outlineStroke;
    }

    /**
     * create a curve draw with the specified color and stroke
     * 
     * @param outlineColor
     *            the outline color to set
     * @param outlineStroke
     *            the outline stroke to set
     */
    public CurveDefaultDraw(Color outlineColor, Stroke outlineStroke) {
        super();
        this.outlineColor = outlineColor;
        this.outlineStroke = outlineStroke;
    }

    /**
     * get the outline color
     * 
     * @return the outline color
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    /**
     * set the outline stroke
     * 
     * @param outlineColor
     */
    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    /**
     * get the outline stroke
     * 
     * @return the outline stroke
     */
    public Stroke getOutlineStroke() {
        return outlineStroke;
    }

    /**
     * set the outline stroke
     * 
     * @param outlineStrok
     */
    public void setOutlineStroke(Stroke outlineStrok) {
        outlineStroke = outlineStrok;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.function.curve.painter.draw.AbstractCurveDraw#drawCurve(java.awt.Graphics2D, com.jensoft.core.plugin.function.curve.CurveFunction)
     */
    @Override
    protected void drawCurve(Graphics2D g2d, Curve curve) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));               

        g2d.setStroke(outlineStroke);

        if (outlineColor != null) {
            g2d.setColor(outlineColor);
        }
        else if (curve.getThemeColor() != null) {
            g2d.setColor(curve.getThemeColor());
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        Shape s = curve.getPathFunction().getOrCreateGeometry().getPath();

        g2d.draw(s);

    }

}
