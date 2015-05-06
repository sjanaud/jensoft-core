/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.ray.painter.fill;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.ray.Ray;
import org.jensoft.core.plugin.ray.painter.AbstractRayPainter;
import org.jensoft.core.view.ViewPart;

/**
 * abstract definition of operation ray fill painting
 */
public abstract class AbstractRayFill extends AbstractRayPainter {

    /**
     * paint the ray fill
     * 
     * @param g2d
     *            the graphics context
     * @param ray
     *            the ray
     */
    protected abstract void paintRayFill(Graphics2D g2d, Ray ray);

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.ray.painter.AbstractRayPainter#paintRay(java.awt.Graphics2D, com.jensoft.core.plugin.ray.Ray, com.jensoft.core.view.ViewPart)
     */
    @Override
    public final void paintRay(Graphics2D g2d, Ray ray, ViewPart viewPart) {
        paintRayFill(g2d, ray);
    }

}
