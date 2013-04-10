/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.fill;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PieSlice;

/**
 * <code>PieSliceDefaultFill</code>
 * 
 * @author Sebastien Janaud
 */
public class PieSliceDefaultFill extends AbstractPieSliceFill {

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.painter.fill.AbstractPieSliceFill#paintPieSliceFill(java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie, com.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    protected final void paintPieSliceFill(Graphics2D g2d, Pie pie, PieSlice pieSection) {
        g2d.setColor(pieSection.getThemeColor());
        g2d.fill(pieSection.getSlicePath());
    }

}
