/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray.painter.effect;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.ray.Ray;
import com.jensoft.core.plugin.ray.painter.AbstractRayPainter;
import com.jensoft.core.view.ViewPart;

/**
 * abstract definition of operation ray effect painting
 */
public abstract class AbstractRayEffect extends AbstractRayPainter {

    /**
     * paint the ray effect
     * 
     * @param g2d
     *            the graphics context
     * @param ray
     *            the ray
     */
    protected abstract void paintRayEffect(Graphics2D g2d, Ray ray);

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.ray.painter.AbstractRayPainter#paintRay(java.awt.Graphics2D, com.jensoft.core.plugin.ray.Ray, com.jensoft.core.view.ViewPart)
     */
    @Override
    public final void paintRay(Graphics2D g2d, Ray ray, ViewPart viewPart) {
        paintRayEffect(g2d, ray);
    }

}
