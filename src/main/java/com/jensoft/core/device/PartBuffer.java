/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.device;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * part is a localize buffered image that can be reuse
 */
public class PartBuffer {

    private double x;
    private double y;
    private double width;
    private double height;
    private BufferedImage part;
    private Graphics2D graphics2D;

    public PartBuffer(double x, double y, int w, int h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;

        part = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        graphics2D = part.createGraphics();
    }

    public void dispose() {
        graphics2D.dispose();
    }

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public BufferedImage getBuffer() {
        return part;
    }

    public void setPart(BufferedImage part) {
        this.part = part;
    }

}
