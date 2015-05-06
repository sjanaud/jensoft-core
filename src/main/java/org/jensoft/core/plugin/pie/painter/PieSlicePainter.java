/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pie.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.pie.Pie;
import org.jensoft.core.plugin.pie.PieSlice;

/**
 * tagging interface to defines slice painter
 * 
 * @author Sebastien Janaud
 */
public interface PieSlicePainter {

    /**
     * paint the specified pie slice
     * 
     * @param g2d
     *            graphics context
     * @param pie
     *            the pie host for the given slice
     * @param pieSlice
     *            the specified pie slice
     */
    void paintPieSlice(Graphics2D g2d, Pie pie, PieSlice pieSlice);
}
