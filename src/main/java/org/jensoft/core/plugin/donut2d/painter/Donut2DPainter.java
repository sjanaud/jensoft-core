/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.donut2d.Donut2D;

/**
 * <code>Donut2DPainter</code>
 * <p>
 * tagging interface for donut2D painting operation
 * </p>
 */
public interface Donut2DPainter {

    /**
     * paint donut 2D
     * 
     * @param g2d
     *            the graphics context
     * @param donut2D
     *            the donut 2D to paint
     */
    public void paintDonut2D(Graphics2D g2d, Donut2D donut2D);
}
