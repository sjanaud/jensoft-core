/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.painter.fill;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.painter.AbstractDonut2DPainter;

/**
 * <code>AbstractDonut2DFill</code>
 * <p>
 * Abstract definition of donut 2D fill operation
 * </p>
 */
public abstract class AbstractDonut2DFill extends AbstractDonut2DPainter {

    /**
     * fill donut 2D
     * 
     * @param g2d
     *            the graphics context
     * @param donut2D
     *            the donut 2D to paint
     */
    protected abstract void paintDonut2DFill(Graphics2D g2d, Donut2D donut2D);

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.donut2d.painter.AbstractDonut2DPainter#
     * paintDonut2D(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.donut2d.Donut2D)
     */
    @Override
    public final void paintDonut2D(Graphics2D g2d, Donut2D donut2D) {
        paintDonut2DFill(g2d, donut2D);
    }

}
