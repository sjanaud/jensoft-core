/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray.painter.axis;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.ray.Ray;
import com.jensoft.core.plugin.ray.painter.AbstractRayPainter;
import com.jensoft.core.window.WindowPart;

/**
 * abstract definition of operation ray axis label painting
 */
public abstract class AbstractRayAxisLabel extends AbstractRayPainter {

    /**
     * paint the ray label
     * 
     * @param g2d
     *            the graphics context
     * @param ray
     *            the ray
     */
    protected abstract void paintRayAxisLabel(Graphics2D g2d, Ray ray,
            WindowPart windowPart);

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.ray.painter.AbstractRayPainter#paintRay(java.awt.Graphics2D, com.jensoft.core.plugin.ray.Ray, com.jensoft.core.window.WindowPart)
     */
    @Override
    public final void paintRay(Graphics2D g2d, Ray ray, WindowPart windowPart) {
        paintRayAxisLabel(g2d, ray, windowPart);
    }

}
