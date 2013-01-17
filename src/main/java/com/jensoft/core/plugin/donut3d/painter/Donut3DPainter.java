/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut3d.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.donut3d.Donut3D;

/**
 * tagging interface for donut3D painting operation that all donut3D painter must be extends.
 * 
 * @author Sebastien Janaud
 */
public interface Donut3DPainter {

    /**
     * donut3D painting operation
     * 
     * @param g2d
     *            the graphics context
     * @param donut3D
     *            the donut 3D to paint
     */
    void paintDonut3D(Graphics2D g2d, Donut3D donut3D);
}
