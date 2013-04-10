/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.widget.button;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.pie.painter.label.AbstractPieSliceLabel.Style;
import com.jensoft.core.view.View2D;

/**
 * @author Sebastien Janaud
 */
public class ButtonWidget<P extends AbstractPlugin> extends AbstractButtonWidget<P> {

    /** round corners for background shape */
    private int outlineRound = 4;

    /** outline color */
    private Color outlineColor;

    /** outline stroke */
    private Stroke outlineStroke;

    /** fill color */
    private Color fillColor;

    /** shade fractions */
    private float[] shadeFractions;

    /** shade colors */
    private Color[] shadeColors;

    /** default stroke */
    private Stroke defaultStroke = new BasicStroke();

    /** default style */
    private Style style = Style.Both;

    /**
     * create widget button with specified parameter
     * 
     * @param id
     *            the button id
     * @param width
     *            the button width
     * @param height
     *            the button height
     * @param xIndex
     *            the button x folder index
     * @param yIndex
     *            the button y folder index
     */
    public ButtonWidget(String id, double width, double height, int xIndex,
            int yIndex) {
        super(id, width, height, xIndex, yIndex);
    }

    /**
     * @return the outlineRound
     */
    public int getOutlineRound() {
        return outlineRound;
    }

    /**
     * @param outlineRound
     *            the outlineRound to set
     */
    public void setOutlineRound(int outlineRound) {
        this.outlineRound = outlineRound;
    }

    /**
     * @return the outlineColor
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    /**
     * @param outlineColor
     *            the outlineColor to set
     */
    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
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

    /**
     * @return the fillColor
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * @param fillColor
     *            the fillColor to set
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * @return the style
     */
    public Style getStyle() {
        return style;
    }

    /**
     * @param style
     *            the style to set
     */
    public void setStyle(Style style) {
        this.style = style;
    }

    /**
     * set the shadow parameters
     * 
     * @param fractions
     * @param colors
     */
    public void setShader(float[] fractions, Color[] colors) {
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("length array does not match");
        }
        shadeFractions = fractions;
        shadeColors = colors;
    }

    /**
     * set the shader
     * 
     * @param shader
     *            the shader to set
     */
    public void setShader(Shader shader) {
        shadeFractions = shader.getFractions();
        shadeColors = shader.getColors();
    }

    /**
     * @return the shadeFractions
     */
    public float[] getShadeFractions() {
        return shadeFractions;
    }

    /**
     * @return the shadeColors
     */
    public Color[] getShadeColors() {
        return shadeColors;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.button.AbstractButtonWidget#paintButton(com.jensoft.core.view.View2D, java.awt.Graphics2D, java.awt.geom.Rectangle2D)
     */
    @Override
    public void paintButton(View2D v2d, Graphics2D g2d,
            Rectangle2D buttonDrawingRegion) {
        double x = buttonDrawingRegion.getX();
        double y = buttonDrawingRegion.getY();
        double width = buttonDrawingRegion.getWidth();
        double height = buttonDrawingRegion.getHeight();
        RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width,
                                                            height, outlineRound, outlineRound);

        if (style == Style.Fill || style == Style.Both) {

            if (fillColor != null && shadeFractions == null) {
                g2d.setColor(fillColor);
                g2d.fill(rect);
            }

            if (shadeFractions != null && shadeColors != null) {
                Point2D start2 = new Point2D.Double(rect.getX(), rect.getY()
                        + rect.getHeight());
                Point2D end2 = new Point2D.Double(rect.getX(), rect.getY());
                if (!start2.equals(end2)) {
                    LinearGradientPaint p2 = new LinearGradientPaint(start2,
                                                                     end2, shadeFractions, shadeColors);
                    g2d.setPaint(p2);
                    g2d.fill(rect);
                }
            }

        }

        if (style == Style.Stroke || style == Style.Both) {
            if (outlineColor != null) {
                if (outlineStroke != null) {
                    g2d.setStroke(outlineStroke);
                }
                g2d.setColor(outlineColor);
                g2d.draw(rect);
                g2d.setStroke(defaultStroke);
            }
        }

    }
    
    @Override
    public boolean isCompatiblePlugin() {
        return false;
    }

}
