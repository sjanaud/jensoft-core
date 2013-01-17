/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray.painter.effect;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.ray.Ray;
import com.jensoft.core.plugin.ray.painter.AbstractRayPainter;
import com.jensoft.core.window.WindowPart;

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

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.ray.painter.AbstractRayPainter#paintRay(
     * java.awt.Graphics2D, com.jensoft.sw2d.core.plugin.ray.Ray)
     */
    @Override
    public final void paintRay(Graphics2D g2d, Ray ray, WindowPart windowPart) {
        paintRayEffect(g2d, ray);
    }

}
