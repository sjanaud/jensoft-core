/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.painter.draw;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;
import com.jensoft.core.plugin.donut2d.painter.AbstractDonut2DSlicePainter;

/**
 * <code>AbstractDonut2DSliceDraw</code>
 * <p>
 * Abstract definition for slice draw painting operation
 * <p>
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractDonut2DSliceDraw extends AbstractDonut2DSlicePainter {

    /**
     * override this method to draw specified slice in the given graphics
     * context
     * 
     * @param g2d
     *            the graphics context
     * @param donut2D
     *            the host donut2D for the given slice
     * @param slice
     *            the slice to be drawn
     */
    protected abstract void paintDonut2DSliceDraw(Graphics2D g2d, Donut2D donut2D,
            Donut2DSlice slice);

    @Override
    public final void paintDonut2DSlice(Graphics2D g2d, Donut2D donut2D, Donut2DSlice slice) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, slice.getAlpha()));
        paintDonut2DSliceDraw(g2d, donut2D, slice);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
