/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view.background;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.view.View2D;

/**
 * <code>RoundViewFill</code> paints the view background with given shader or stroking properties.
 * 
 * @author Sebastien Janaud
 */
public class RoundViewFill extends BackgroundPainter {

    /** the outline round corner */
    private int outlineRound = 20;

    /** padding x */
    private int paddingX = 2;

    /** padding y */
    private int paddingY = 2;

    /** shader for fill background */
    private Shader shader;

    /** outline color */
    private Color outlineColor;

    /** outline stroke */
    private Stroke outlineStroke;

    /**
     * create empty background
     */
    public RoundViewFill() {
    }

    /**
     * create the background with specified parameters
     * 
     * @param shader
     *            the shader to fill the background
     */
    public RoundViewFill(Shader shader) {
        super();
        this.shader = shader;
    }

    /**
     * create the background with specified parameters
     * 
     * @param shader
     *            the shader to fill the background
     * @param outlineColor
     *            the color to stroke the view outline
     */
    public RoundViewFill(Shader shader, Color outlineColor) {
        super();
        this.shader = shader;
        this.outlineColor = outlineColor;
    }

    /**
     * create the background with specified parameters
     * 
     * @param shader
     *            the shader to fill the background
     * @param outlineColor
     *            the view outline color
     * @param outlineStroke
     *            the view outline stroke
     */
    public RoundViewFill(Shader shader, Color outlineColor, Stroke outlineStroke) {
        super();
        this.shader = shader;
        this.outlineColor = outlineColor;
        this.outlineStroke = outlineStroke;
    }

    /**
     * create the background with specified parameters
     * 
     * @param shader
     *            the shader to fill the background
     * @param outlineColor
     *            the view outline color
     * @param outlineStroke
     *            the view outline stroke
     * @param outlineRound
     *            the view outline corner radius
     */
    public RoundViewFill(Shader shader, Color outlineColor, Stroke outlineStroke, int outlineRound) {
        super();
        this.shader = shader;
        this.outlineColor = outlineColor;
        this.outlineStroke = outlineStroke;
        this.outlineRound = outlineRound;
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
     * @return the paddingX
     */
    public int getPaddingX() {
        return paddingX;
    }

    /**
     * @param paddingX
     *            the paddingX to set
     */
    public void setPaddingX(int paddingX) {
        this.paddingX = paddingX;
    }

    /**
     * @return the paddingY
     */
    public int getPaddingY() {
        return paddingY;
    }

    /**
     * @param paddingY
     *            the paddingY to set
     */
    public void setPaddingY(int paddingY) {
        this.paddingY = paddingY;
    }

    /**
     * @return the shader
     */
    public Shader getShader() {
        return shader;
    }

    /**
     * @param shader
     *            the shader to set
     */
    public void setShader(Shader shader) {
        this.shader = shader;
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

   
    /* (non-Javadoc)
     * @see com.jensoft.core.view.background.BackgroundPainter#paintViewBackground(com.jensoft.core.view.View2D, java.awt.Graphics2D)
     */
    @Override
    public final void paintViewBackground(View2D view, Graphics2D g2d) {

        int width = view.getWidth();
        int height = view.getHeight();
        RenderingHints qualityHints = new RenderingHints(
                                                         RenderingHints.KEY_RENDERING,
                                                         RenderingHints.VALUE_RENDER_QUALITY);
        qualityHints.put(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_DITHERING,
                         RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHints(qualityHints);
        RoundRectangle2D roundBackground = new RoundRectangle2D.Double(
                                                                       paddingX, paddingY, width-1 - 2 * paddingX,
                                                                       height-1 - 2 * paddingY, outlineRound,
                                                                       outlineRound);

        if (shader != null) {
            LinearGradientPaint lgp = new LinearGradientPaint(
                                                              new Point2D.Double(width / 2, 0),
                                                              new Point2D.Double(
                                                                                 width / 2, height),
                                                              shader.getFractions(),
                                                              shader.getColors());
            g2d.setPaint(lgp);
            g2d.fill(roundBackground);
        }

        if (outlineColor != null) {
            g2d.setColor(outlineColor);

            if (outlineStroke != null) {
                g2d.setStroke(outlineStroke);
            }
            g2d.draw(roundBackground);
        }

    }

}
