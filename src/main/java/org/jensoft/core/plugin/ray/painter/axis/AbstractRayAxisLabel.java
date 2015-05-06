/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.ray.painter.axis;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.ray.Ray;
import org.jensoft.core.plugin.ray.painter.AbstractRayPainter;
import org.jensoft.core.view.ViewPart;

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
            ViewPart viewPart);

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.ray.painter.AbstractRayPainter#paintRay(java.awt.Graphics2D, com.jensoft.core.plugin.ray.Ray, com.jensoft.core.view.ViewPart)
     */
    @Override
    public final void paintRay(Graphics2D g2d, Ray ray, ViewPart viewPart) {
        paintRayAxisLabel(g2d, ray, viewPart);
    }

}
