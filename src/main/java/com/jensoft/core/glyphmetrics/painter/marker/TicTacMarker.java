/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics.painter.marker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.Side;
import com.jensoft.core.glyphmetrics.painter.GlyphMetricMarkerPainter;

public class TicTacMarker extends GlyphMetricMarkerPainter {

    /** theme color */
    private Color tictacThemeColor;

    /** divergence right */
    private int divergenceRight = 5;

    /** divergence left */
    private int divergenceLeft = 5;

    /** width size */
    private int size = 6;

    /**
     * create marker with specified parameters
     * 
     * @param tictacThemeColor
     */
    public TicTacMarker(Color tictacThemeColor) {
        super();
        this.tictacThemeColor = tictacThemeColor;
    }

    /**
     * create marker with specified parameters
     * 
     * @param tictacThemeColor
     * @param size
     */
    public TicTacMarker(Color tictacThemeColor, int size) {
        super();
        this.tictacThemeColor = tictacThemeColor;
        this.size = size;
    }

    /**
     * create marker with specified parameters
     * 
     * @param tictacThemeColor
     * @param size
     * @param divergence
     */
    public TicTacMarker(Color tictacThemeColor, int size, int divergence) {
        super();
        this.tictacThemeColor = tictacThemeColor;
        this.size = size;
        divergenceLeft = divergence;
        divergenceRight = divergence;
    }

    /**
     * create marker with specified parameters
     * 
     * @param tictacThemeColor
     * @param size
     * @param divergenceRight
     * @param divergenceLeft
     */
    public TicTacMarker(Color tictacThemeColor, int size, int divergenceRight,
            int divergenceLeft) {
        super();
        this.tictacThemeColor = tictacThemeColor;
        this.size = size;
        this.divergenceLeft = divergenceLeft;
        this.divergenceRight = divergenceRight;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the tictacThemeColor
     */
    public Color getTictacThemeColor() {
        return tictacThemeColor;
    }

    /**
     * @param tictacThemeColor
     *            the tictacThemeColor to set
     */
    public void setTictacThemeColor(Color tictacThemeColor) {
        this.tictacThemeColor = tictacThemeColor;
    }
    
    /**
     * @param divergence
     *            the divergence to set for left and right divergence
     */
    public void setDivergence(int divergence) {
        this.divergenceLeft = divergence;
        this.divergenceRight = divergence;
    }

    /**
     * @return the divergenceRight
     */
    public int getDivergenceRight() {
        return divergenceRight;
    }

    /**
     * @param divergenceRight
     *            the divergenceRight to set
     */
    public void setDivergenceRight(int divergenceRight) {
        this.divergenceRight = divergenceRight;
    }

    /**
     * @return the divergenceLeft
     */
    public int getDivergenceLeft() {
        return divergenceLeft;
    }

    /**
     * @param divergenceLeft
     *            the divergenceLeft to set
     */
    public void setDivergenceLeft(int divergenceLeft) {
        this.divergenceLeft = divergenceLeft;
    }

    @Override
    public void paintGlyphMetricMarker(Graphics2D g2d, GlyphMetric glyphMetric) {

        double pxLeft;
        double pyLeft;

        Point2D pLeft = glyphMetric.getRadialPoint(divergenceLeft,
                                                   Side.SideLeft);
        if (pLeft == null) {
            return;
        }

        pxLeft = pLeft.getX();
        pyLeft = pLeft.getY();

        double pxRight;
        double pyRight;

        Point2D pRight = glyphMetric.getRadialPoint(divergenceRight,
                                                    Side.SideRight);
        if (pRight == null) {
            return;
        }

        pxRight = pRight.getX();
        pyRight = pRight.getY();

        Line2D l = new Line2D.Double(pxLeft, pyLeft, pxRight, pyRight);

        g2d.setStroke(new BasicStroke(new Float(size), BasicStroke.CAP_ROUND,
                                      BasicStroke.JOIN_ROUND));

        BasicStroke bs = new BasicStroke(new Float(size),
                                         BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        Shape st = bs.createStrokedShape(l);

        // double pcenterX = pxLeft + (pxLeft - pxRight) / 2;
        // double pcenterY = pyLeft + (pyLeft - pyRight) / 2;
        // Point2D pCenter = new Point2D.Double(pcenterX, pcenterY);
        // float[] dist0 = { 0f, 1f };
        // Color[] colors0 = { Color.WHITE, JennyPalette.JENNY6 };
        // RadialGradientPaint p0 = new RadialGradientPaint(pCenter, (float) 6,
        // dist0, colors0, CycleMethod.NO_CYCLE
        //
        // );
        // g2d.setPaint(p0);

        g2d.setColor(tictacThemeColor);
        g2d.fill(st);
        g2d.setStroke(new BasicStroke(4f));
        g2d.setColor(new Color(255, 255, 255, 60));

        g2d.draw(st);

    }

}
