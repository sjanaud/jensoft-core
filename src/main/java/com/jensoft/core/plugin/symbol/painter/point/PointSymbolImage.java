/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol.painter.point;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;

import com.jensoft.core.drawable.ImageDrawable;
import com.jensoft.core.plugin.symbol.PointSymbol;

/**
 * <code>PointSymbolImage<code>
 * 
 * @author Sebastien Janaud
 */
public class PointSymbolImage extends AbstractPointSymbolPainter {

    /** the image drawable */
    private ImageDrawable imageDrawable;

    /** the offset on x dimension */
    private int offsetX;

    /** the offset on y dimension */
    private int offsetY;

    /**
     * create default point image symbol
     * @param image
     *          the image to draw
     */
    public PointSymbolImage(Image image) {
        imageDrawable = new ImageDrawable(image);
    }
    
    
    /**
     * create a point image symbol with the given image offset
     * @param image
     * @param offsetX
     * @param offsetY
     */
    public PointSymbolImage(Image image, int offsetX, int offsetY) {
        imageDrawable = new ImageDrawable(image);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
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

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.painter.point.AbstractPointSymbolPainter#paintPointSymbol(java.awt.Graphics2D, com.jensoft.core.plugin.symbol.PointSymbol)
     */
    @Override
    protected void paintPointSymbol(Graphics2D g2d, PointSymbol point) {
        Point2D p = point.getDevicePoint();

        imageDrawable.setX((int) (p.getX() + offsetX));
        imageDrawable.setY((int) (p.getY() + offsetY));
        imageDrawable.draw(g2d);

    }

}
