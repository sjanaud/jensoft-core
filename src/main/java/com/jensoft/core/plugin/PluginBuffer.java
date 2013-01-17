/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin;

import java.awt.image.BufferedImage;

import com.jensoft.core.window.WindowBound;
import com.jensoft.core.window.WindowPart;

/**
 * defines a plug in buffer.
 * a plug in buffer keeps a buffered image host plug in for the specified part
 * with specified dimension and a window bound.
 * 
 * @author Sebastien Janaud
 */
public class PluginBuffer {

    private WindowPart part;
    private BufferedImage buffer;
    private WindowBound windowBound;
    private int windowPartWidth;
    private int windowPartHeight;

    public PluginBuffer() {
    }

    /**
     * @return the part
     */
    public WindowPart getPart() {
        return part;
    }

    /**
     * @param part
     *            the part to set
     */
    public void setPart(WindowPart part) {
        this.part = part;
    }

    /**
     * @return the buffer
     */
    public BufferedImage getBuffer() {
        return buffer;
    }

    /**
     * @param buffer
     *            the buffer to set
     */
    public void setBuffer(BufferedImage buffer) {
        this.buffer = buffer;
    }

    /**
     * @return the windowBound
     */
    public WindowBound getWindowBound() {
        return windowBound;
    }

    /**
     * @param windowBound
     *            the windowBound to set
     */
    public void setWindowBound(WindowBound windowBound) {
        this.windowBound = windowBound;
    }

    /**
     * @return the windowPartWidth
     */
    public int getWindowPartWidth() {
        return windowPartWidth;
    }

    /**
     * @param windowPartWidth
     *            the windowPartWidth to set
     */
    public void setWindowPartWidth(int windowPartWidth) {
        this.windowPartWidth = windowPartWidth;
    }

    /**
     * @return the windowPartHeight
     */
    public int getWindowPartHeight() {
        return windowPartHeight;
    }

    /**
     * @param windowPartHeight
     *            the windowPartHeight to set
     */
    public void setWindowPartHeight(int windowPartHeight) {
        this.windowPartHeight = windowPartHeight;
    }

}
