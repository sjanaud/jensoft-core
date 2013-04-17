/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.painter.draw;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.painter.AbstractDonut2DPainter;

/**
 * <code>AbstractDonut2DDraw</code>
 * 
 * @author sebastien janaud
 */
/**
 * @author sebastien
 *
 */
public abstract class AbstractDonut2DDraw extends AbstractDonut2DPainter {

    /**
     * Draw donut 2D
     * 
     * @param g2d
     *            the graphics context
     * @param donut2D
     *            the donut 2D to paint
     */
    protected abstract void paintDonut2DDraw(Graphics2D g2d, Donut2D donut2D);

   
    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.painter.AbstractDonut2DPainter#paintDonut2D(java.awt.Graphics2D, com.jensoft.core.plugin.donut2d.Donut2D)
     */
    @Override
    public final void paintDonut2D(Graphics2D g2d, Donut2D donut2D) {
        paintDonut2DDraw(g2d, donut2D);
    }

}
