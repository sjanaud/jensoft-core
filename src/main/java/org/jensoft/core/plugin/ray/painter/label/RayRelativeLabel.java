/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.ray.painter.label;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;

import org.jensoft.core.plugin.ray.Ray;
import org.jensoft.core.plugin.ray.Ray.RayNature;

/**
 * RelativeLabel draw a label with the given relative alignments parameters.<br>
 * <p>
 * label is positioned according to it vertical and horizontal alignment relative to ray bound shape.
 * </p>
 */
public class RayRelativeLabel extends AbstractRayLabel {

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
    private int marginExternalX = 5;

    /** margin between sticker border and bar on y dimension */
    private int marginExternalY = 5;

    /** margin between sticker border and inner label on x dimension */
    private int marginInternalX = 2;

    /** margin between sticker border and inner label on y dimension */
    private int marginInternalY = 2;

    /** sticker round corner */
    private int round = 6;

    /** alpha transparency */
    float alpha = 1f;

    /**
     * Vertical alignment constant
     */
    public enum VerticalAlignment {
        NorthTop, NorthAcross, NorthBottom, Middle, SouthTop, SouthAcross, SouthBottom;
    }

    /**
     * Horizontal alignment constant
     */
    public enum HorizontalAlignment {
        WestLeft, WestAcross, WestRight, Middle, EastLeft, EastAcross, EastRight;
    }

    /**
     * define a label with default alignments on vertical middle and horizontal
     * middle.
     */
    public RayRelativeLabel() {
    }

    /**
     * create label with specified given parameters
     * 
     * @param verticalAlignment
     * @param horizontalAlignment
     * @param drawColor
     * @param fillColor
     * @param label
     * @param labelColor
     */
    public RayRelativeLabel(VerticalAlignment verticalAlignment,
            HorizontalAlignment horizontalAlignment, Color drawColor,
            Color fillColor, String label, Color labelColor) {
        super();
        this.verticalAlignment = verticalAlignment;
        this.horizontalAlignment = horizontalAlignment;
        this.drawColor = drawColor;
        this.fillColor = fillColor;
        this.label = label;
        this.labelColor = labelColor;
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
    public RayRelativeLabel(Color drawColor, Color fillColor, Color labelColor) {
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
    public RayRelativeLabel(VerticalAlignment verticalAlignment,
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
    public RayRelativeLabel(VerticalAlignment verticalAlignment,
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
    public RayRelativeLabel(String label, Color labelColor, Font font) {
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
    public RayRelativeLabel(VerticalAlignment verticalAlignment,
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
    public void setLabelColor(Color labelColor) {
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
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the marginExternalX
     */
    public int getMarginExternalX() {
        return marginExternalX;
    }

    /**
     * @param marginExternalX
     *            the marginExternalX to set
     */
    public void setMarginExternalX(int marginExternalX) {
        this.marginExternalX = marginExternalX;
    }

    /**
     * @return the marginExternalY
     */
    public int getMarginExternalY() {
        return marginExternalY;
    }

    /**
     * @param marginExternalY
     *            the marginExternalY to set
     */
    public void setMarginExternalY(int marginExternalY) {
        this.marginExternalY = marginExternalY;
    }

    /**
     * @return the marginInternalX
     */
    public int getMarginInternalX() {
        return marginInternalX;
    }

    /**
     * @param marginInternalX
     *            the marginInternalX to set
     */
    public void setMarginInternalX(int marginInternalX) {
        this.marginInternalX = marginInternalX;
    }

    /**
     * @return the marginInternalY
     */
    public int getMarginInternalY() {
        return marginInternalY;
    }

    /**
     * @param marginInternalY
     *            the marginInternalY to set
     */
    public void setMarginInternalY(int marginInternalY) {
        this.marginInternalY = marginInternalY;
    }

    /**
     * @return the round
     */
    public int getRound() {
        return round;
    }

    /**
     * @param round
     *            the round to set
     */
    public void setRound(int round) {
        this.round = round;
    }

    @Override
    public void paintRayLabel(Graphics2D g2d, Ray bar) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    alpha));

        if (bar.getRayNature() == RayNature.XRay) {
            paintXRayLabel(g2d, bar);
        }
        if (bar.getRayNature() == RayNature.YRay) {
            paintYRayLabel(g2d, bar);
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
    private void paintYRayLabel(Graphics2D g2d, Ray bar) {

        double value = bar.getRayValue();

        String sVal = decimalFormat.format(value);
        if (getLabel() != null) {
            sVal = getLabel();
        }

        drawStickers(g2d, bar);

        if (getLabelColor() == null) {
            setLabelColor(Color.DARK_GRAY);
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
    private void paintXRayLabel(Graphics2D g2d, Ray bar) {

        double value = bar.getRayValue();
        String sVal = decimalFormat.format(value);
        if (getLabel() != null) {
            sVal = getLabel();
        }

        drawStickers(g2d, bar);

        if (getLabelColor() == null) {
            setLabelColor(Color.DARK_GRAY);
        }

        g2d.setColor(getLabelColor());
        g2d.drawString(sVal, (int) getX(g2d, bar), (int) getY(g2d, bar));

    }

    /**
     * draw label background sticker
     * 
     * @param g2d
     *            the graphics context
     * @param ray
     *            the ray
     */
    private void drawStickers(Graphics2D g2d, Ray ray) {

        if (getFillColor() != null || getDrawColor() != null) {
            if (font == null) {
                setFont(new Font("Dialog", Font.PLAIN, 12));
            }

            g2d.setFont(getFont());
            FontMetrics fm = g2d.getFontMetrics();

            RoundRectangle2D sticker = new RoundRectangle2D.Double((int) getX(
                                                                              g2d, ray) - marginInternalX,
                                                                   (int) getY(g2d, ray)
                                                                           - fm.getAscent() - marginInternalY,
                                                                   getLabelWidth(g2d, ray)
                                                                           + 2 * marginInternalX,
                                                                   fm.getHeight() + 2 * marginInternalY, round, round);

            if (getFillColor() != null) {
                g2d.setColor(getFillColor());
                g2d.fill(sticker);
            }

            if (getDrawColor() != null) {
                g2d.setColor(getDrawColor());
                g2d.draw(sticker);
            }
        }
    }

    /**
     * get label width
     * 
     * @param g2d
     *            the graphics context
     * @param ray
     *            the ray symbol
     * @return label width
     */
    public double getLabelWidth(Graphics2D g2d, Ray ray) {
        if (font == null) {
            setFont(new Font("Dialog", Font.PLAIN, 12));
        }

        g2d.setFont(getFont());
        FontMetrics fm = g2d.getFontMetrics();

        double value = ray.getRayValue();

        String sVal = decimalFormat.format(value);
        if (getLabel() != null) {
            sVal = getLabel();
        }

        int labelWidth = fm.stringWidth(sVal);
        return labelWidth;
    }

    /**
     * get the x location for label ray symbol according to horizontal alignment
     * 
     * @param g2d
     *            the graphics context
     * @param ray
     *            the ray symbol
     */
    private double getX(Graphics2D g2d, Ray ray) {
        double x = 0;
        Shape rayShape = ray.getRayShape();

        if (horizontalAlignment == HorizontalAlignment.WestLeft) {
            double west = rayShape.getBounds2D().getX();
            x = west - getLabelWidth(g2d, ray) - marginExternalX
                    - marginInternalX;
        }
        else if (horizontalAlignment == HorizontalAlignment.WestAcross) {
            double west = rayShape.getBounds2D().getX();
            x = west - getLabelWidth(g2d, ray) / 2;
        }
        else if (horizontalAlignment == HorizontalAlignment.WestRight) {
            double west = rayShape.getBounds2D().getX();
            x = west + marginExternalX + marginInternalX;
        }
        else if (horizontalAlignment == HorizontalAlignment.Middle) {
            double centerX = rayShape.getBounds2D().getCenterX();
            x = centerX - getLabelWidth(g2d, ray) / 2;
        }
        else if (horizontalAlignment == HorizontalAlignment.EastLeft) {
            double east = rayShape.getBounds2D().getX()
                    + rayShape.getBounds2D().getWidth();
            x = east - getLabelWidth(g2d, ray) - marginExternalX
                    - marginInternalX;
        }
        else if (horizontalAlignment == HorizontalAlignment.EastAcross) {
            double east = rayShape.getBounds2D().getX()
                    + rayShape.getBounds2D().getWidth();
            x = east - getLabelWidth(g2d, ray) / 2;
        }
        else if (horizontalAlignment == HorizontalAlignment.EastRight) {
            double east = rayShape.getBounds2D().getX()
                    + rayShape.getBounds2D().getWidth() + marginExternalX;
            x = east + marginInternalX;
        }
        return x;
    }

    /**
     * get the y location for label bar symbol according to vertical alignment
     * 
     * @param g2d
     *            the graphics context
     * @param ray
     *            the ray symbol
     */
    private double getY(Graphics2D g2d, Ray ray) {
        double y = 0;
        if (font == null) {
            setFont(new Font("Dialog", Font.PLAIN, 12));
        }

        g2d.setFont(getFont());
        FontMetrics fm = g2d.getFontMetrics();
        Shape rayShape = ray.getRayShape();

        if (verticalAlignment == VerticalAlignment.NorthTop) {
            y = rayShape.getBounds2D().getY()
                    - (fm.getDescent() + marginExternalY + marginInternalY);
        }
        else if (verticalAlignment == VerticalAlignment.NorthAcross) {
            y = rayShape.getBounds2D().getY() + fm.getAscent() / 2;
        }
        else if (verticalAlignment == VerticalAlignment.NorthBottom) {
            y = rayShape.getBounds2D().getY()
                    + (fm.getHeight() + marginExternalY + marginInternalY);
        }
        else if (verticalAlignment == VerticalAlignment.Middle) {
            y = rayShape.getBounds2D().getY()
                    + rayShape.getBounds2D().getHeight() / 2 + fm.getAscent()
                    / 2;
        }
        else if (verticalAlignment == VerticalAlignment.SouthTop) {
            y = rayShape.getBounds2D().getY()
                    + rayShape.getBounds2D().getHeight()
                    - (fm.getAscent() + marginExternalY + marginInternalY);
        }
        else if (verticalAlignment == VerticalAlignment.SouthAcross) {
            y = rayShape.getBounds2D().getY()
                    + rayShape.getBounds2D().getHeight() + fm.getAscent() / 2;
        }
        else if (verticalAlignment == VerticalAlignment.SouthBottom) {
            y = rayShape.getBounds2D().getY()
                    + rayShape.getBounds2D().getHeight()
                    + (fm.getAscent() + marginExternalY + marginInternalY);
        }

        return y;
    }

}
