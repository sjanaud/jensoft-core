/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PieSlice;

/**
 * abstract definition of slice painter
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractPieSlicePainter implements PieSlicePainter {

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.pie.painter.SlicePainter#paintSlice(java
     * .awt.Graphics2D, com.jensoft.sw2d.core.plugin.pie.Pie,
     * com.jensoft.sw2d.core.plugin.pie.PieSlice)
     */
    @Override
    public void paintPieSlice(Graphics2D g2d, Pie pie, PieSlice slice) {
    }

}
