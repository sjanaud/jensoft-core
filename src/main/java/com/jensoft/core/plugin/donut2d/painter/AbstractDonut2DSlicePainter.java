/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;

/**
 * <code>AbstractDonut2DSlicePainter</code>
 * <p>
 * abstract definition for donut2D slice painting operation
 * </p>
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractDonut2DSlicePainter implements
        Donut2DSlicePainter {

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.painter.Donut2DSlicePainter#paintDonut2DSlice(java.awt.Graphics2D, com.jensoft.core.plugin.donut2d.Donut2D, com.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    public void paintDonut2DSlice(Graphics2D g2d, Donut2D donut2d, Donut2DSlice slice) {
    }

}
