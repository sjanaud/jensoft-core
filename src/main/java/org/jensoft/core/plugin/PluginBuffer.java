/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin;

import java.awt.image.BufferedImage;

import org.jensoft.core.projection.ProjectionBound;
import org.jensoft.core.view.ViewPart;

/**
 * <code>PluginBuffer</code> defines a plug in buffer.
 * 
 * <p>
 * a plug in buffer keeps a buffered image host plug in for the specified part
 * with specified dimension and a p^rojection bound.
 * </p>
 * 
 * @since 1.0
 * @author Sebastien Janaud
 */
public class PluginBuffer {

    private ViewPart part;
    private BufferedImage buffer;
    private ProjectionBound projectionBound;
    private int projectionPartWidth;
    private int projectionPartHeight;

    public PluginBuffer() {
    }

    /**
     * @return the part
     */
    public ViewPart getPart() {
        return part;
    }

    /**
     * @param part
     *            the part to set
     */
    public void setPart(ViewPart part) {
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
     * @return the projectionBound
     */
    public ProjectionBound getProjectionBound() {
        return projectionBound;
    }

    /**
     * @param projectionBound
     *            the projection bound to set
     */
    public void setProjectionBound(ProjectionBound projectionBound) {
        this.projectionBound = projectionBound;
    }

    /**
     * get projection part width
     * @return the projection part width
     */
    public int getProjectionPartWidth() {
        return projectionPartWidth;
    }

    /**
     * set projection part width
     * @param projectionPartWidth
     *            the projection part width to set
     */
    public void setProjectionPartWidth(int projectionPartWidth) {
        this.projectionPartWidth = projectionPartWidth;
    }

    /**
     * get projection part height
     * @return the windowPartHeight
     */
    public int getProjectionPartHeight() {
        return projectionPartHeight;
    }

    /**
     * set projection part height
     * @param projectionPartHeight
     *            the projection part height to set
     */
    public void setProjectionPartHeight(int projectionPartHeight) {
        this.projectionPartHeight = projectionPartHeight;
    }

}
