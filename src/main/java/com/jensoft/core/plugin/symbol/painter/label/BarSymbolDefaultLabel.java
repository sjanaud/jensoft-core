/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol.painter.label;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;

import com.jensoft.core.plugin.symbol.BarSymbol;
import com.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;

/**
 * ClassicLabel is a basic label
 * the reference position for this label is the top of the bar.
 */
public class BarSymbolDefaultLabel extends AbstractBarLabel {

    /** the vertical offset */
    private int verticalOffset = 0;

    /** horizontal offset */
    private int horizontalOffset = 0;

    /** font label */
    private Font font;

    /** label color */
    private Color labelColor;

    /** fill color */
    private Color fillColor;

    /** draw color */
    private Color drawColor;

    /** internal decimal format */
    private DecimalFormat decimalFormat = new DecimalFormat("##.00");

    /** label */
    private String label;

    /** round corner */
    private int roundCorner = 6;

    /**
     * create empty label
     */
    public BarSymbolDefaultLabel() {
    }

    /**
     * create default label with specified parameters
     * @param label
     * @param labelColor
     */
    public BarSymbolDefaultLabel(String label, Color labelColor) {
        super();
        this.label = label;
        this.labelColor = labelColor;
    }

    /**
     * create classic label with specified parameters
     * 
     * @param verticalOffset
     *            the vertical offset
     * @param horizontalOffset
     *            the horizontal offset
     */
    public BarSymbolDefaultLabel(int verticalOffset, int horizontalOffset) {
        this.verticalOffset = verticalOffset;
        this.horizontalOffset = horizontalOffset;
    }

    /**
     * create classic label with specified parameters
     * 
     * @param verticalOffset
     *            the vertical offset
     * @param horizontalOffset
     *            the horizontal offset
     * @param label
     *            the label
     */
    public BarSymbolDefaultLabel(int verticalOffset, int horizontalOffset,
            String label) {
        this.verticalOffset = verticalOffset;
        this.horizontalOffset = horizontalOffset;
        this.label = label;
    }

    /**
     * create classic label with specified parameters
     * 
     * @param verticalOffset
     *            the vertical offset
     * @param horizontalOffset
     *            the horizontal offset
     * @param label
     *            the label
     * @param labelColor
     *            the label color
     */
    public BarSymbolDefaultLabel(int verticalOffset, int horizontalOffset,
            String label, Color labelColor) {
        this.verticalOffset = verticalOffset;
        this.horizontalOffset = horizontalOffset;
        this.label = label;
        this.labelColor = labelColor;
    }

    /**
     * create classic with specified parameters
     * 
     * @param verticalOffset
     *            the vertical offset
     * @param horizontalOffset
     *            the horizontal offset
     * @param label
     *            the label
     * @param labelColor
     *            the label color
     * @param fillColor
     *            the fill background color
     */
    public BarSymbolDefaultLabel(int verticalOffset, int horizontalOffset,
            String label, Color labelColor, Color fillColor) {
        this.verticalOffset = verticalOffset;
        this.horizontalOffset = horizontalOffset;
        this.label = label;
        this.labelColor = labelColor;
        this.fillColor = fillColor;
    }

    /**
     * create classic with specified parameters
     * 
     * @param label
     *            the label
     * @param labelColor
     *            the label color
     * @param fillColor
     *            the fill background color
     */
    public BarSymbolDefaultLabel(String label, Color labelColor, Color fillColor) {
        this.label = label;
        this.labelColor = labelColor;
        this.fillColor = fillColor;
    }

    /**
     * create classic with specified parameters
     * 
     * @param verticalOffset
     *            the vertical offset
     * @param horizontalOffset
     *            the horizontal offset
     * @param label
     *            the label
     * @param labelColor
     *            the label color
     * @param fillColor
     *            the fill background color
     * @param drawColor
     *            the background draw outline color
     */
    public BarSymbolDefaultLabel(int verticalOffset, int horizontalOffset,
            String label, Color labelColor, Color fillColor, Color drawColor) {
        this.verticalOffset = verticalOffset;
        this.horizontalOffset = horizontalOffset;
        this.label = label;
        this.labelColor = labelColor;
        this.fillColor = fillColor;
        this.drawColor = drawColor;
    }

    /**
     * create classic with specified parameters
     * 
     * @param verticalOffset
     *            the vertical offset
     * @param horizontalOffset
     *            the horizontal offset
     * @param labelColor
     *            the label color
     * @param fillColor
     *            the fill background color
     * @param drawColor
     *            the background draw outline color
     */
    public BarSymbolDefaultLabel(int verticalOffset, int horizontalOffset,
            Color labelColor, Color fillColor, Color drawColor) {
        this.verticalOffset = verticalOffset;
        this.horizontalOffset = horizontalOffset;
        this.labelColor = labelColor;
        this.fillColor = fillColor;
        this.drawColor = drawColor;
    }

    /**
     * create classic with specified parameters
     * 
     * @param label
     *            the label
     * @param labelColor
     *            the label color
     * @param fillColor
     *            the fill background color
     * @param drawColor
     *            the background draw outline color
     */
    public BarSymbolDefaultLabel(String label, Color labelColor,
            Color fillColor, Color drawColor) {
        this.label = label;
        this.labelColor = labelColor;
        this.fillColor = fillColor;
        this.drawColor = drawColor;
    }

    /**
     * get the font
     * 
     * @return font
     */
    public Font getFont() {
        return font;
    }

    /**
     * set the font
     * 
     * @param font
     *            the font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * @return the roundCorner
     */
    public int getRoundCorner() {
        return roundCorner;
    }

    /**
     * @param roundCorner
     *            the roundCorner to set
     */
    public void setRoundCorner(int roundCorner) {
        this.roundCorner = roundCorner;
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
     * get label color
     * 
     * @return label color
     */
    public Color getLabelColor() {
        return labelColor;
    }

    /**
     * @param labelColor
     *            the label color to set
     */
    public void setLabelColor(Color labelColor) {
        this.labelColor = labelColor;
    }

    /**
     * @return fill color
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * set fill color
     * 
     * @param fillColor
     *            the fill color to set
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
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
     * get vertical offset
     * 
     * @return vertical offset
     */
    public int getVerticalOffset() {
        return verticalOffset;
    }

    /**
     * set vertical offset
     * 
     * @param verticalOffset
     *            the vertical offset to set
     */
    public void setVerticalOffset(int verticalOffset) {
        this.verticalOffset = verticalOffset;
    }

    /**
     * get horizontal offset
     * 
     * @return horizontal offset
     */
    public int getHorizontalOffset() {
        return horizontalOffset;
    }

    /**
     * set horizontal offset
     * 
     * @param horizontalOffset
     *            the horizontal offset to set
     */
    public void setHorizontalOffset(int horizontalOffset) {
        this.horizontalOffset = horizontalOffset;
    }

    @Override
    public void paintBarLabel(Graphics2D g2d, BarSymbol bar) {
        if (bar.getHost().getNature() == SymbolNature.Vertical) {
            paintVLabel(g2d, bar);
        }
        if (bar.getHost().getNature() == SymbolNature.Horizontal) {
            paintHLabel(g2d, bar);
        }
    }

    /**
     * paint vertical label
     * 
     * @param g2d
     *            the graphics context
     * @param bar
     *            the bar symbol
     */
    private void paintVLabel(Graphics2D g2d, BarSymbol bar) {

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

        Shape barShape = bar.getBarShape();
        double centerX = barShape.getBounds2D().getCenterX() + horizontalOffset;

        int marginY = 2;
        int marginX = 2;
        double y = barShape.getBounds2D().getY();
        if (bar.isAscent()) {
            y = barShape.getBounds2D().getY() - (fm.getDescent() + marginY)
                    - verticalOffset;
        }
        if (bar.isDescent()) {
            y = barShape.getBounds2D().getY()
                    + barShape.getBounds2D().getHeight()
                    + (fm.getAscent() + marginY) + verticalOffset;
        }

        RoundRectangle2D recLabel = new RoundRectangle2D.Double(centerX
                - labelWidth / 2 - marginX, y - fm.getAscent() - marginY,
                                                                labelWidth + 2 * marginX, fm.getDescent()
                                                                        + fm.getAscent() + 2
                                                                        * marginY, roundCorner, roundCorner);

        if (getFillColor() != null) {
            g2d.setColor(getFillColor());
            g2d.fill(recLabel);
        }
        if (getDrawColor() != null) {
            g2d.setColor(getDrawColor());
            g2d.draw(recLabel);
        }

        if (getLabelColor() == null) {
            if (bar.getThemeColor() != null) {
                setLabelColor(bar.getThemeColor());
            }
            else {
                setLabelColor(Color.RED);
            }
        }

        g2d.setColor(getLabelColor());
        // g2d.draw(recLabel);

        if (bar.isAscent()) {
            g2d.drawString(sVal, (int) (centerX - labelWidth / 2), (int) y);
        }

        if (bar.isDescent()) {
            g2d.drawString(sVal, (int) (centerX - labelWidth / 2), (int) y);
        }

    }

    /**
     * paint horizontal label
     * 
     * @param g2d
     *            the graphics context
     * @param bar
     *            the bar symbol
     */
    private void paintHLabel(Graphics2D g2d, BarSymbol bar) {
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
        int marginY = 2;
        int marginX = 2;
        Shape barShape = bar.getBarShape();
        double pointX = barShape.getBounds2D().getX()
                + barShape.getBounds2D().getWidth() + horizontalOffset;
        if (bar.isAscent()) {
            pointX = barShape.getBounds2D().getX()
                    + barShape.getBounds2D().getWidth() + marginX
                    + horizontalOffset;
        }
        if (bar.isDescent()) {
            pointX = barShape.getBounds2D().getX() - marginX - horizontalOffset
                    - labelWidth;
        }

        double pointY = barShape.getBounds2D().getCenterY() + verticalOffset
                + fm.getAscent() / 2;

        RoundRectangle2D recLabel = new RoundRectangle2D.Double(pointX
                - labelWidth / 2 - marginX,
                                                                pointY - fm.getAscent() - marginY, labelWidth + 2
                                                                        * marginX,
                                                                fm.getDescent() + fm.getAscent() + 2 * marginY,
                                                                roundCorner,
                                                                roundCorner);

        if (getFillColor() != null) {
            g2d.setColor(getFillColor());
            g2d.fill(recLabel);
        }
        if (getDrawColor() != null) {
            g2d.setColor(getDrawColor());
            g2d.draw(recLabel);
        }

        if (getLabelColor() == null) {
            if (bar.getThemeColor() != null) {
                setLabelColor(bar.getThemeColor());
            }
            else {
                setLabelColor(Color.RED);
            }
        }

        g2d.setColor(getLabelColor());
        // g2d.draw(recLabel);

        if (bar.isAscent()) {
            g2d.drawString(sVal, (int) (pointX - labelWidth / 2),
                           (int) pointY);
        }

        if (bar.isDescent()) {
            g2d.drawString(sVal, (int) (pointX - labelWidth / 2),
                           (int) pointY);
        }

    }

}
