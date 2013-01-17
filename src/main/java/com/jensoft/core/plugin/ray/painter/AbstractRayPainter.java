/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.ray.Ray;
import com.jensoft.core.window.WindowPart;

/**
 * base class to define a operation ray painting
 * 
 * @see RayPainter
 */
public abstract class AbstractRayPainter implements RayPainter {

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.ray.painter.RayPainter#paintRay(java.awt
     * .Graphics2D, com.jensoft.sw2d.core.plugin.ray.Ray)
     */
    @Override
    public abstract void paintRay(Graphics2D g2d, Ray ray, WindowPart windowPart);

}
