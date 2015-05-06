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
 * abstract definition of slice painter
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractPieSlicePainter implements PieSlicePainter {

  
  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.painter.PieSlicePainter#paintPieSlice(java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie, com.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    public void paintPieSlice(Graphics2D g2d, Pie pie, PieSlice slice) {
    }

}
