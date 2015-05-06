/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.donut2d.Donut2D;

/**
 * <code>AbstractDonut2DPainter</code>
 * <p>
 * Abstract definition of donut2D painting operation
 * </p>
 */
public abstract class AbstractDonut2DPainter implements Donut2DPainter {

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.painter.Donut2DPainter#paintDonut2D(java.awt.Graphics2D, com.jensoft.core.plugin.donut2d.Donut2D)
     */
    @Override
    public void paintDonut2D(Graphics2D g2d, Donut2D donut2D) {
    }

}
