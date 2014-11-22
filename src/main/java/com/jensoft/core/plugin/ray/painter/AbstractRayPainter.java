/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.ray.Ray;
import com.jensoft.core.view.ViewPart;

/**
 * base class to define a operation ray painting
 * 
 * @see RayPainter
 */
public abstract class AbstractRayPainter implements RayPainter {

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.ray.painter.RayPainter#paintRay(java.awt.Graphics2D, com.jensoft.core.plugin.ray.Ray, com.jensoft.core.view.ViewPart)
     */
    @Override
    public abstract void paintRay(Graphics2D g2d, Ray ray, ViewPart viewPart);

}
