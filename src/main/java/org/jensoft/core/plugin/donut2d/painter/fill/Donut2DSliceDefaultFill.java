/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.painter.fill;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.donut2d.Donut2D;
import org.jensoft.core.plugin.donut2d.Donut2DSlice;

/**
 * <code>Donut2DSliceDefaultFill</code> fills slice with plain slice color
 */
public class Donut2DSliceDefaultFill extends AbstractDonut2DSliceFill {

    /**
     * create default fill
     */
    public Donut2DSliceDefaultFill() {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.painter.fill.AbstractDonut2DSliceFill#paintDonut2DSliceFill(java.awt.Graphics2D, com.jensoft.core.plugin.donut2d.Donut2D, com.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    public void paintDonut2DSliceFill(Graphics2D g2d, Donut2D donut2D, Donut2DSlice slice) {
        g2d.setColor(slice.getThemeColor());
        g2d.fill(slice.getSlicePath());
    }

}
