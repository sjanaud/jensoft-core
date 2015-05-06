/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pie.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.pie.Pie;

/**
 * tagging interface for pie painting operation
 * 
 * @author Sebastien Janaud
 */
public interface PiePainter {

    /**
     * pie painting operation
     * 
     * @param g2d
     *            the graphics context
     * @param pie
     *            the pie
     */
    void paintPie(Graphics2D g2d, Pie pie);
}
