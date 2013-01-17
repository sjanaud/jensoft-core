/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut3d.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.donut3d.Donut3D;
import com.jensoft.core.plugin.donut3d.Donut3DSlice;

/**
 * <code>Donut3DSlicePainter</code> tagging interface for slice painting operation that all donut3D slice painter must
 * be extends
 * 
 * @author Sebastien Janaud
 */
public interface Donut3DSlicePainter {

    /**
     * painting operation on specified slice
     * 
     * @param g2d
     *            the graphics context
     * @param donut3D
     *            the donut3D that host the slice
     * @param donutSlice
     *            the slice
     */
    public void paintDonut3DSlice(Graphics2D g2d, Donut3D donut3D, Donut3DSlice donutSlice);
}
