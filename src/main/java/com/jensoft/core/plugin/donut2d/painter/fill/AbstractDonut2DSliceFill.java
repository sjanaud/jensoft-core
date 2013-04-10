/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.painter.fill;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;
import com.jensoft.core.plugin.donut2d.painter.AbstractDonut2DSlicePainter;

/**
 * <code>AbstractDonut2DSliceFill</code>
 * <p>
 * Abstract definition for slice fill rendering
 * </p>
 * 
 * @see AbstractDonut2DSlicePainter
 * @see Donut2D
 * @author Sebastien Janaud
 */
/**
 * @author sebastien
 *
 */
public abstract class AbstractDonut2DSliceFill extends AbstractDonut2DSlicePainter {

    /**
     * override this method to fill specified slice in the given graphics
     * context
     * 
     * @param g2d
     *            the graphics context
     * @param donut2D
     *            the host donut2D for the given slice
     * @param slice
     *            the slice to fill
     */
    protected abstract void paintDonut2DSliceFill(Graphics2D g2d, Donut2D donut2D,
            Donut2DSlice slice);

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.painter.AbstractDonut2DSlicePainter#paintDonut2DSlice(java.awt.Graphics2D, com.jensoft.core.plugin.donut2d.Donut2D, com.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    public final void paintDonut2DSlice(Graphics2D g2d, Donut2D donut2D, Donut2DSlice slice) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, slice.getAlpha()));
        paintDonut2DSliceFill(g2d, donut2D, slice);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
