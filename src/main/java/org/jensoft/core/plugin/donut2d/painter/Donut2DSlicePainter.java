/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.donut2d.Donut2D;
import org.jensoft.core.plugin.donut2d.Donut2DSlice;

/**
 * <code>Donut2DSlicePainter</code>
 * <p>
 * tagging interface for donut2D slice painting operation
 * </p>
 */
public interface Donut2DSlicePainter {

    /**
     * paint donut2D slice
     * 
     * @param g2d
     *            the graphics context
     * @param donut2D
     *            the donut2D that host slice
     * @param slice
     *            the donut2D slice to paint
     */
    public void paintDonut2DSlice(Graphics2D g2d, Donut2D donut2D, Donut2DSlice slice);
}
