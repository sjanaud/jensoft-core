/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol.painter.axis;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.symbol.BarSymbol;
import com.jensoft.core.plugin.symbol.BarSymbolGroup;
import com.jensoft.core.plugin.symbol.SymbolComponent;
import com.jensoft.core.plugin.symbol.SymbolPlugin;
import com.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>DefaultAxisSymbol<code> know how to paint bar or group symbol in window axis part<br>
 * 
 * <ul>
 * 	<li>only draw symbol in south window part for vertical symbol</li>
 *  <li>only draw symbol in window west part for horizontal symbol</li>
 * </ul>
 */
public class BarDefaultAxisLabel extends AbstractBarAxisLabel {

    /** symbol label */
    private String text;

    /** symbol color */
    private Color textColor;

    /** symbol font */
    private Font font;

    /** offset x to add on label x location */
    private int offsetX = 0;

    /** offset y to add on label y location */
    private int offsetY = 0;

    /** internal margin x between label and sticker bound */
    private int textPaddingX = 4;

    /** internal margin y between label and sticker bound */
    private int textPaddingY = 2;

    /** round for round rectangle sticker */
    private int outlineRound = 10;

    /** color for draw sticker border */
    private Color drawColor;

    /** color for fill sticker area */
    private Color fillColor;

    /** alpha transparency */
    private float alpha = 1f;

    /** fill shader */
    private Shader shader;

    /** outline stroke */
    private Stroke outlineStroke = new BasicStroke();

    /**
     * default axis symbol label
     */
    public BarDefaultAxisLabel() {
    }

    /**
     * create default bar axis label with the specified offset
     * 
     * @param offsetX
     *            the offset x to add on solve label X location
     * @param offsetY
     *            the offset y to add on solve label Y location
     */
    public BarDefaultAxisLabel(int offsetX, int offsetY) {
        super();
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    /**
     * create the label with specified given symbol properties and default
     * offset (0,0)
     * 
     * @param symbol
     *            the symbol to set
     * @param symbolColor
     *            the color to draw symbol
     */
    public BarDefaultAxisLabel(String symbol, Color symbolColor) {
        super();
        text = symbol;
        textColor = symbolColor;
    }

    /**
     * create a axis symbol label with specified parameters
     * 
     * @param symbol
     *            the symbol to set
     * @param symbolColor
     *            the color to draw symbol
     * @param offsetX
     *            the offset on x axis
     * @param offsetY
     *            the offset on y axis
     */
    public BarDefaultAxisLabel(String symbol, Color symbolColor, int offsetX,
            int offsetY) {
        super();
        text = symbol;
        textColor = symbolColor;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    /**
     * @return the symbol
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the alpha
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * @param alpha
     *            the alpha to set
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    /**
     * @return the textColor
     */
    public Color getTextColor() {
        return textColor;
    }

    /**
     * @param textColor
     *            the textColor to set
     */
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    /**
     * @return the symbolFont
     */
    public Font getFont() {
        return font;
    }

    /**
     * @param font
     *            the font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * @return the offsetX
     */
    public int getOffsetX() {
        return offsetX;
    }

    /**
     * @param offsetX
     *            the offsetX to set
     */
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    /**
     * @return the offsetY
     */
    public int getOffsetY() {
        return offsetY;
    }

    /**
     * @param offsetY
     *            the offsetY to set
     */
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
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
     * @return the textPaddingX
     */
    public int getTextPaddingX() {
        return textPaddingX;
    }

    /**
     * @param textPaddingX
     *            the textPaddingX to set
     */
    public void setTextPaddingX(int textPaddingX) {
        this.textPaddingX = textPaddingX;
    }

    /**
     * @return the internalMarginY
     */
    public int getTextPaddingY() {
        return textPaddingY;
    }

    /**
     * @param textPaddingY
     *            the textPaddingY to set
     */
    public void setTextPaddingY(int textPaddingY) {
        this.textPaddingY = textPaddingY;
    }

    /**
     * @return the round
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

    @Override
    public void paintAxisLabel(Graphics2D g2d, BarSymbol bar,
            WindowPart windowPart) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    alpha));

        if (bar.getHost().getNature() == SymbolNature.Vertical) {
            paintVSymbol(g2d, bar, windowPart);
        }
        if (bar.getHost().getNature() == SymbolNature.Horizontal) {
            paintHSymbol(g2d, bar, windowPart);
        }

        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));
    }

   /**
    * paint vertical symbol
    * @param g2d
    * @param bar
    * @param windowPart
    */
    private void paintVSymbol(Graphics2D g2d, BarSymbol bar,
            WindowPart windowPart) {

        SymbolPlugin bsp = bar.getHost();
        View2D v2d = bar.getHost().getWindow2D().getView2D();

        if (font == null) {
            setFont(new Font("Dialog", Font.PLAIN, 12));
        }

        g2d.setFont(getFont());
        FontMetrics fm = g2d.getFontMetrics();

        if (windowPart == WindowPart.South) {

            int margin = v2d.getPlaceHolderAxisWest();

            if (bar instanceof BarSymbol && !(bar instanceof BarSymbolGroup)) {

                BarSymbol bs = bar;

                String symbol = getText();
                if (symbol == null) {
                    symbol = bs.getSymbol();
                }

                if (symbol != null) {
                    double barWidth = bar.getThickness();

                    int symbolWidth = fm.stringWidth(symbol);
                    // System.out.println("symbol name : "+bs.getName());
                    float pt = (float) (bs.getLocationX() + margin + barWidth
                            / 2 - symbolWidth / 2);

                    // System.out.println("pt:"+bs.getLocationX());

                    int x = (int) (pt + offsetX);
                    int y = fm.getAscent() + offsetY;

                    RoundRectangle2D roundRect = new RoundRectangle2D.Double(x
                            - textPaddingX, y - fm.getAscent() - textPaddingY,
                                                                             symbolWidth + 2 * textPaddingX,
                                                                             fm.getHeight() + 2
                                                                                     * textPaddingY, outlineRound,
                                                                             outlineRound);

                    if (fillColor != null && shader == null) {
                        g2d.setColor(fillColor);
                        g2d.fill(roundRect);
                    }

                    if (shader != null) {
                        Point2D start = new Point2D.Double(
                                                           roundRect.getCenterX(), roundRect.getY());
                        Point2D end = new Point2D.Double(
                                                         roundRect.getCenterX(), roundRect.getY()
                                                                 + roundRect.getHeight());
                        if (start != null && end != null && !start.equals(end)) {
                            LinearGradientPaint lgp = new LinearGradientPaint(
                                                                              start, end, shader.getFractions(),
                                                                              shader.getColors());
                            g2d.setPaint(lgp);
                            g2d.fill(roundRect);
                        }
                    }

                    if (drawColor != null) {
                        g2d.setColor(drawColor);
                        g2d.draw(roundRect);
                    }

                    if (getTextColor() != null) {
                        g2d.setColor(getTextColor());
                    }
                    else {
                        g2d.setColor(bar.getThemeColor());
                    }

                    g2d.drawString(symbol, x, y);

                }

            }
            else if (bar instanceof BarSymbolGroup) {
                BarSymbolGroup group = (BarSymbolGroup) bar;
                List<BarSymbol> components = group.getSymbolComponents();

                if (components.size() > 0) {
                    SymbolComponent firstComponent = components.get(0);
                    double positionX = firstComponent.getLocationX();
                    double widthComponent = group.getThickness();

                    String symbol = getText();
                    if (symbol == null) {
                        symbol = group.getSymbol();
                    }

                    if (symbol != null) {

                        int symbolWidth = fm.stringWidth(symbol);
                        float pt = (float) (positionX + margin + widthComponent
                                / 2 - symbolWidth / 2);

                        int x = (int) (pt + offsetX);
                        int y = fm.getAscent() + offsetY;

                        RoundRectangle2D roundRect = new RoundRectangle2D.Double(
                                                                                 x - textPaddingX, y - fm.getAscent()
                                                                                         - textPaddingY, symbolWidth
                                                                                         + 2
                                                                                         * textPaddingX, fm.getHeight()
                                                                                         + 2
                                                                                         * textPaddingY, outlineRound,
                                                                                 outlineRound);

                        if (fillColor != null) {
                            g2d.setColor(fillColor);
                            g2d.fill(roundRect);
                        }

                        if (shader != null) {
                            Point2D start = new Point2D.Double(
                                                               roundRect.getCenterX(), roundRect.getY());
                            Point2D end = new Point2D.Double(
                                                             roundRect.getCenterX(), roundRect.getY()
                                                                     + roundRect.getHeight());
                            if (start != null && end != null
                                    && !start.equals(end)) {
                                LinearGradientPaint lgp = new LinearGradientPaint(
                                                                                  start, end, shader.getFractions(),
                                                                                  shader.getColors());
                                g2d.setPaint(lgp);
                                g2d.fill(roundRect);
                            }
                        }

                        if (drawColor != null) {
                            g2d.setColor(drawColor);
                            g2d.draw(roundRect);
                        }

                        if (getTextColor() != null) {
                            g2d.setColor(getTextColor());
                        }
                        else {
                            g2d.setColor(group.getThemeColor());
                        }

                        g2d.drawString(symbol, x, y);
                    }
                }

            }

        }

    }

   /**
    * paint horizontal symbol
    * @param g2d
    * @param bar
    * @param windowPart
    */
    private void paintHSymbol(Graphics2D g2d, BarSymbol bar,
            WindowPart windowPart) {

        SymbolPlugin bsp = bar.getHost();
        View2D v2d = bar.getHost().getWindow2D().getView2D();

        if (font == null) {
            setFont(new Font("Dialog", Font.PLAIN, 12));
        }

        g2d.setFont(getFont());
        FontMetrics fm = g2d.getFontMetrics();

        if (windowPart == WindowPart.West) {

            int margin = v2d.getPlaceHolderAxisWest();

            if (bar instanceof BarSymbol && !(bar instanceof BarSymbolGroup)) {

                String symbol = getText();
                if (symbol == null) {
                    symbol = bar.getSymbol();
                }

                double barWidth = bar.getThickness();

                g2d.setColor(bar.getThemeColor());
                if (symbol != null) {

                    int symbolWidth = fm.stringWidth(symbol);
                    float pt = (float) (bar.getLocationY() + barWidth / 2 + fm
                            .getAscent() / 2);

                    int x = margin - symbolWidth - textPaddingX + offsetX - 4;
                    int y = (int) (pt + offsetY);

                    RoundRectangle2D roundRect = new RoundRectangle2D.Double(x
                            - textPaddingX, y - fm.getAscent() - textPaddingY,
                                                                             symbolWidth + 2 * textPaddingX,
                                                                             fm.getHeight() + 2
                                                                                     * textPaddingY, outlineRound,
                                                                             outlineRound);

                    if (fillColor != null) {
                        g2d.setColor(fillColor);
                        g2d.fill(roundRect);
                    }

                    if (drawColor != null) {
                        g2d.setColor(drawColor);
                        g2d.draw(roundRect);
                    }

                    if (getTextColor() != null) {
                        g2d.setColor(getTextColor());
                    }
                    else {
                        g2d.setColor(bar.getThemeColor());
                    }

                    g2d.drawString(symbol, x, pt + offsetY);

                }

            }
            else if (bar instanceof BarSymbolGroup) {

                BarSymbolGroup group = (BarSymbolGroup) bar;
                List<BarSymbol> components = group.getSymbolComponents();

                if (components.size() > 0) {
                    SymbolComponent firstComponent = components.get(0);
                    double positionY = firstComponent.getLocationY();
                    double heightComponent = group.getThickness();
                    String symbol = getText();
                    if (symbol == null) {
                        symbol = group.getSymbol();
                    }

                    if (symbol != null) {

                        int symbolWidth = fm.stringWidth(symbol);
                        float pt = (float) (positionY + heightComponent / 2 + fm
                                .getAscent() / 2);

                        int x = margin - symbolWidth + offsetX - textPaddingX - 4;
                        int y = (int) (pt + offsetY);

                        RoundRectangle2D roundRect = new RoundRectangle2D.Double(
                                                                                 x - textPaddingX, y - fm.getAscent()
                                                                                         - textPaddingY, symbolWidth
                                                                                         + 2
                                                                                         * textPaddingX, fm.getHeight()
                                                                                         + 2
                                                                                         * textPaddingY, outlineRound,
                                                                                 outlineRound);

                        if (fillColor != null) {
                            g2d.setColor(fillColor);
                            g2d.fill(roundRect);
                        }

                        if (drawColor != null) {
                            g2d.setColor(drawColor);
                            if (outlineStroke != null) {
                                g2d.setStroke(getOutlineStroke());
                            }
                            g2d.draw(roundRect);
                        }

                        if (getTextColor() != null) {
                            g2d.setColor(getTextColor());
                        }
                        else {
                            g2d.setColor(group.getThemeColor());
                        }

                        g2d.drawString(symbol, x, pt + offsetY);
                    }
                }
            }

        }

    }
}
