/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.area.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.function.area.Area;

/**
 * this interface defines a painting operation on area curve
 * 
 * @author Sebastien Janaud
 */
public interface AreaPainter {

    /**
     * define a painting operation on area curve
     * 
     * @param g2d
     *            the graphics context
     * @param areaCurve
     *            the area curve
     */
    public void paintArea(Graphics2D g2d, Area areaCurve);

}
