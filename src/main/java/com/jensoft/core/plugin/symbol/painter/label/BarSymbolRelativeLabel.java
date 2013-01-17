/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol.painter.label;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.symbol.BarSymbol;
import com.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;

/**
 * RelativeLabel draw a label with the given relative alignments parameters.<br>
 * <p>
 * label is positioned according to it vertical and horizontal alignment relative to bar bound shape.
 * </p>
 */
public class BarSymbolRelativeLabel extends AbstractBarLabel {

    /** the vertical alignment */
    private VerticalAlignment verticalAlignment = VerticalAlignment.Middle;

    /** the horizontal alignment */
    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.Middle;

    /** font to draw label */
    private Font font;

    /** draw color */
    private Color drawColor;

    /** fill color */
    private Color fillColor;

    /** default decimal format to format value */
    private DecimalFormat decimalFormat = new DecimalFormat("##.00");

    /** symbol label */
    private String label;

    /** label color */
    private Color labelColor;

    /** margin between sticker border and bar on x dimension */
    private int offsetX = 5;

    /** margin between sticker border and bar on y dimension */
    private int offsetY = 5;

    /** margin between sticker border and inner label on x dimension */
    private int textPaddingX = 5;

    /** margin between sticker border and inner label on y dimension */
    private int textPaddingY = 2;

    /** sticker round corner */
    private int outlineRound = 6;

    /** alpha transparency */
    private float alpha = 1f;

    /** outline stroke */
    private Stroke outlineStroke = new BasicStroke();

    /** fill shader */
    private Shader shader;

    /**
     * Vertical alignment constant
     */
    public enum VerticalAlignment {
        NorthTop("NorthTop"), NorthAcross("NorthAcross"), NorthBottom(
                "NorthBottom"), Middle("Middle"), SouthTop("SouthTop"), SouthAcross(
                "SouthAcross"), SouthBottom("SouthBottom");

        private String verticalAlign;

        private VerticalAlignment(String verticalAlign) {
            this.verticalAlign = verticalAlign;
        }

        /**
         * @return the verticalAlign
         */
        public String getVerticalAlign() {
            return verticalAlign;
        }

        public static VerticalAlignment parse(String align) {
            if (NorthTop.getVerticalAlign().equalsIgnoreCase(align)) {
                return NorthTop;
            }
            if (NorthAcross.getVerticalAlign().equalsIgnoreCase(align)) {
                return NorthAcross;
            }
            if (NorthBottom.getVerticalAlign().equalsIgnoreCase(align)) {
                return NorthBottom;
            }
            if (Middle.getVerticalAlign().equalsIgnoreCase(align)) {
                return Middle;
            }
            if (SouthTop.getVerticalAlign().equalsIgnoreCase(align)) {
                return SouthTop;
            }
            if (SouthAcross.getVerticalAlign().equalsIgnoreCase(align)) {
                return SouthAcross;
            }
            if (SouthBottom.getVerticalAlign().equalsIgnoreCase(align)) {
                return SouthBottom;
            }
            return VerticalAlignment.Middle;
        }
    }

    /**
     * Horizontal alignment constant
     */
    public enum HorizontalAlignment {
        WestLeft("WestLeft"), WestAcross("WestAcross"), WestRight("WestRight"), Middle(
                "Middle"), EastLeft("EastLeft"), EastAcross("EastAcross"), EastRight(
                "EastRight");

        private String horizontalAlign;

        private HorizontalAlignment(String horizontalAlign) {
            this.horizontalAlign = horizontalAlign;
        }

        /**
         * @return the horizontalAlign
         */
        public String getHorizontalAlign() {
            return horizontalAlign;
        }

        public static HorizontalAlignment parse(String align) {
            if (WestLeft.getHorizontalAlign().equalsIgnoreCase(align)) {
                return WestLeft;
            }
            if (WestAcross.getHorizontalAlign().equalsIgnoreCase(align)) {
                return WestAcross;
            }
            if (WestRight.getHorizontalAlign().equalsIgnoreCase(align)) {
                return WestRight;
            }
            if (Middle.getHorizontalAlign().equalsIgnoreCase(align)) {
                return Middle;
            }
            if (EastLeft.getHorizontalAlign().equalsIgnoreCase(align)) {
                return EastLeft;
            }
            if (EastAcross.getHorizontalAlign().equalsIgnoreCase(align)) {
                return EastAcross;
            }
            if (EastRight.getHorizontalAlign().equalsIgnoreCase(align)) {
                return EastRight;
            }
            return HorizontalAlignment.Middle;
        }
    }

    /**
     * define a label with default alignments on vertical middle and horizontal
     * middle.
     */
    public BarSymbolRelativeLabel() {
    }

    /**
     * special constructor with color set and default alignment
     * 
     * @param drawColor
     *            the color to draw outline sticker
     * @param fillColor
     *            the color to fill sticker
     * @param labelColor
     *            the color to draw label
     */
    public BarSymbolRelativeLabel(Color drawColor, Color fillColor,
            Color labelColor) {
        super();
        this.drawColor = drawColor;
        this.fillColor = fillColor;
        this.labelColor = labelColor;
    }

    /**
     * create a label with color set and given alignment
     * 
     * @param verticalAlignment
     *            the vertical alignment to set *
     * @param horizontalAlignment
     *            the horizontal alignment to set *
     * @param drawColor
     *            the color to draw outline sticker
     * @param fillColor
     *            the color to fill sticker
     * @param labelColor
     *            the color to draw label
     */
    public BarSymbolRelativeLabel(VerticalAlignment verticalAlignment,
            HorizontalAlignment horizontalAlignment, Color drawColor,
            Color fillColor, Color labelColor) {
        super();
        this.verticalAlignment = verticalAlignment;
        this.horizontalAlignment = horizontalAlignment;
        this.drawColor = drawColor;
        this.fillColor = fillColor;
        this.labelColor = labelColor;
    }

    /**
     * create a label with color set and given alignment
     * 
     * @param verticalAlignment
     *            the vertical alignment to set *
     * @param horizontalAlignment
     *            the horizontal alignment to set *
     * @param drawColor
     *            the color to draw outline sticker
     * @param fillColor
     *            the color to fill sticker
     * @param labelColor
     *            the color to draw label
     */
    public BarSymbolRelativeLabel(VerticalAlignment verticalAlignment,
            HorizontalAlignment horizontalAlignment, Color drawColor,
            Color fillColor, Color labelColor, float alpha) {
        this(verticalAlignment, horizontalAlignment, drawColor, fillColor,
             labelColor);
        this.alpha = alpha;
    }

    /**
     * special constructor for label properties
     * 
     * @param label
     *            the label to set
     * @param labelColor
     *            the label color
     * @param font
     *            the label font
     */
    public BarSymbolRelativeLabel(String label, Color labelColor, Font font) {
        super();
        this.label = label;
        this.labelColor = labelColor;
        this.font = font;
    }

    /**
     * define a label with relative given alignments
     * 
     * @param verticalAlignment
     *            the vertical alignment
     * @param horizontalAlignment
     *            the horizontal alignment
     */
    public BarSymbolRelativeLabel(VerticalAlignment verticalAlignment,
            HorizontalAlignment horizontalAlignment) {
        this.verticalAlignment = verticalAlignment;
        this.horizontalAlignment = horizontalAlignment;
    }

    /**
     * @return the verticalAlignment
     */
    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    /**
     * @param verticalAlignment
     *            the verticalAlignment to set
     */
    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    /**
     * @return the horizontalAlignment
     */
    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    /**
     * @param horizontalAlignment
     *            the horizontalAlignment to set
     */
    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
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
     * @return the font
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
     * @return the labelColor
     */
    public Color getLabelColor() {
        return labelColor;
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
     * @param labelColor
     *            the labelColor to set
     */
    public void setTextColor(Color labelColor) {
        this.labelColor = labelColor;
    }

    /**
     * @return the draw color
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
     * @return the fill color
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * @param fillColor
     *            the fill color to set
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     *            the label to set
     */
    public void setText(String label) {
        this.label = label;
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
     * @return the testPaddingY
     */
    public int getTextPaddingY() {
        return textPaddingY;
    }

    /**
     * @param testPaddingY
     *            the testPaddingY to set
     */
    public void setTextPaddingY(int testPaddingY) {
        textPaddingY = testPaddingY;
    }

    /**
     * @return the outlineRound
     */
    public int getOutlineRound() {
        return outlineRound;
    }

    /**
     * @param outlineRound
     *            the outline round to set
     */
    public void setOutlineRound(int outlineRound) {
        this.outlineRound = outlineRound;
    }

    @Override
    public void paintBarLabel(Graphics2D g2d, BarSymbol bar) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    alpha));

        if (bar.getHost().getNature() == SymbolNature.Vertical) {
            paintVLabel(g2d, bar);
        }
        if (bar.getHost().getNature() == SymbolNature.Horizontal) {
            paintHLabel(g2d, bar);
        }

        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    /**
     * paint horizontal label
     * 
     * @param g2d
     *            the graphics context
     * @param bar
     *            the bar
     */
    private void paintHLabel(Graphics2D g2d, BarSymbol bar) {

        double value = bar.getValue();

        String sVal = decimalFormat.format(value);
        if (getLabel() != null) {
            sVal = getLabel();
        }

        drawStickers(g2d, bar);

        if (getLabelColor() == null) {
            setTextColor(Color.DARK_GRAY);
        }

        g2d.setColor(getLabelColor());
        g2d.drawString(sVal, (int) getX(g2d, bar), (int) getY(g2d, bar));

    }

    /**
     * paint vertical label
     * 
     * @param g2d
     *            the graphics context
     * @param bar
     *            the bar
     */
    private void paintVLabel(Graphics2D g2d, BarSymbol bar) {

        double value = bar.getValue();
        String sVal = decimalFormat.format(value);
        if (getLabel() != null) {
            sVal = getLabel();
        }

        drawStickers(g2d, bar);

        if (getLabelColor() == null) {
            setTextColor(Color.DARK_GRAY);
        }

        g2d.setColor(getLabelColor());
        g2d.drawString(sVal, (int) getX(g2d, bar), (int) getY(g2d, bar));

    }

    /**
     * draw label background sticker
     * 
     * @param g2d
     *            the graphics context
     * @param bar
     *            the bar symbol
     */
    private void drawStickers(Graphics2D g2d, BarSymbol bar) {

        if (getFillColor() != null || getDrawColor() != null
                || getShader() != null) {
            if (font == null) {
                setFont(new Font("Dialog", Font.PLAIN, 12));
            }

            g2d.setFont(getFont());
            FontMetrics fm = g2d.getFontMetrics();

            RoundRectangle2D sticker = new RoundRectangle2D.Double((int) getX(
                                                                              g2d, bar) - textPaddingX, (int) getY(g2d,
                                                                                                                   bar)
                    - fm.getAscent() - textPaddingY, getLabelWidth(g2d, bar)
                    + 2 * textPaddingX, fm.getHeight() + 2 * textPaddingY,
                                                                   outlineRound, outlineRound);

            if (getFillColor() != null && getShader() == null) {
                g2d.setColor(getFillColor());
                g2d.fill(sticker);
            }

            if (getShader() != null) {
                Point2D start = new Point2D.Double(sticker.getCenterX(),
                                                   sticker.getY());
                Point2D end = new Point2D.Double(sticker.getCenterX(),
                                                 sticker.getY() + sticker.getHeight());
                if (start != null && end != null && !start.equals(end)) {
                    LinearGradientPaint lgp = new LinearGradientPaint(start,
                                                                      end, getShader().getFractions(), getShader()
                                                                              .getColors());
                    g2d.setPaint(lgp);
                    g2d.fill(sticker);
                }
            }

            if (getDrawColor() != null) {
                g2d.setColor(getDrawColor());

                if (outlineStroke != null) {
                    g2d.setStroke(getOutlineStroke());
                }
                g2d.draw(sticker);
            }
        }
    }

    /**
     * get label width
     * 
     * @param g2d
     *            the graphics context
     * @param bar
     *            the bar symbol
     * @return
     */
    public double getLabelWidth(Graphics2D g2d, BarSymbol bar) {
        if (font == null) {
            setFont(new Font("Dialog", Font.PLAIN, 12));
        }

        g2d.setFont(getFont());
        FontMetrics fm = g2d.getFontMetrics();

        double value = bar.getValue();

        String sVal = decimalFormat.format(value);
        if (getLabel() != null) {
            sVal = getLabel();
        }

        int labelWidth = fm.stringWidth(sVal);
        return labelWidth;
    }

    /**
     * get the x location for label bar symbol according to horizontal alignment
     * 
     * @param g2d
     *            the graphics context
     * @param bar
     *            the bar symbol
     */
    private double getX(Graphics2D g2d, BarSymbol bar) {
        double x = 0;
        Shape barShape = bar.getBarShape();

        if (horizontalAlignment == HorizontalAlignment.WestLeft) {
            double west = barShape.getBounds2D().getX();
            x = west - getLabelWidth(g2d, bar) - offsetX - textPaddingX;
        }
        else if (horizontalAlignment == HorizontalAlignment.WestAcross) {
            double west = barShape.getBounds2D().getX();
            x = west - getLabelWidth(g2d, bar) / 2;
        }
        else if (horizontalAlignment == HorizontalAlignment.WestRight) {
            double west = barShape.getBounds2D().getX();
            x = west + offsetX + textPaddingX;
        }
        else if (horizontalAlignment == HorizontalAlignment.Middle) {
            double centerX = barShape.getBounds2D().getCenterX();
            x = centerX - getLabelWidth(g2d, bar) / 2;
        }
        else if (horizontalAlignment == HorizontalAlignment.EastLeft) {
            double east = barShape.getBounds2D().getX()
                    + barShape.getBounds2D().getWidth();
            x = east - getLabelWidth(g2d, bar) - offsetX - textPaddingX;
        }
        else if (horizontalAlignment == HorizontalAlignment.EastAcross) {
            double east = barShape.getBounds2D().getX()
                    + barShape.getBounds2D().getWidth();
            x = east - getLabelWidth(g2d, bar) / 2;
        }
        else if (horizontalAlignment == HorizontalAlignment.EastRight) {
            double east = barShape.getBounds2D().getX()
                    + barShape.getBounds2D().getWidth() + offsetX;
            x = east + textPaddingX;
        }
        return x;
    }

    /**
     * get the y location for label bar symbol according to vertical alignment
     * 
     * @param g2d
     *            the graphics context
     * @param bar
     *            the bar symbol
     */
    private double getY(Graphics2D g2d, BarSymbol bar) {
        double y = 0;
        if (font == null) {
            setFont(new Font("Dialog", Font.PLAIN, 12));
        }

        g2d.setFont(getFont());
        FontMetrics fm = g2d.getFontMetrics();
        Shape barShape = bar.getBarShape();

        if (verticalAlignment == VerticalAlignment.NorthTop) {
            y = barShape.getBounds2D().getY()
                    - (fm.getDescent() + offsetY + textPaddingY);
        }
        else if (verticalAlignment == VerticalAlignment.NorthAcross) {
            y = barShape.getBounds2D().getY() + fm.getAscent() / 2;
        }
        else if (verticalAlignment == VerticalAlignment.NorthBottom) {
            y = barShape.getBounds2D().getY()
                    + (fm.getHeight() + offsetY + textPaddingY);
        }
        else if (verticalAlignment == VerticalAlignment.Middle) {
            y = barShape.getBounds2D().getY()
                    + barShape.getBounds2D().getHeight() / 2 + fm.getAscent()
                    / 2;
        }
        else if (verticalAlignment == VerticalAlignment.SouthTop) {
            y = barShape.getBounds2D().getY()
                    + barShape.getBounds2D().getHeight()
                    - (fm.getAscent() + offsetY + textPaddingY);
        }
        else if (verticalAlignment == VerticalAlignment.SouthAcross) {
            y = barShape.getBounds2D().getY()
                    + barShape.getBounds2D().getHeight() + fm.getAscent() / 2;
        }
        else if (verticalAlignment == VerticalAlignment.SouthBottom) {
            y = barShape.getBounds2D().getY()
                    + barShape.getBounds2D().getHeight()
                    + (fm.getAscent() + offsetY + textPaddingY);
        }

        return y;
    }

}
