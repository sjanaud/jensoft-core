/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.drawable;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 * <code>ImageDrawable</code>
 * 
 * @author Sebastien Janaud
 */
public class ImageDrawable implements Drawable {

    /** image to draw */
    private Image image;

    /** top left x coordinate */
    private int x = 0;

    /** top left y coordinate */
    private int y = 0;

    /**
     * create image drawable
     * 
     * @param image
     */
    public ImageDrawable(Image image) {
        this.image = image;
    }

    /**
     * create image drawable
     * 
     * @param image
     * @param x
     * @param y
     */
    public ImageDrawable(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @param image
     *            the image to set
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x
     *            the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y
     *            the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.drawable.Drawable#draw(java.awt.Graphics2D)
     */
    @Override
    public void draw(Graphics2D g2d) {
        if (image == null) {
            return;
        }
        g2d.drawImage(image, x, y, null);
    }

}
