/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.painter.effect;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import org.jensoft.core.plugin.donut2d.Donut2D;
import org.jensoft.core.plugin.donut2d.Donut2DSlice;
import org.jensoft.core.plugin.donut2d.painter.AbstractDonut2DSlicePainter;

/**
 * <code>AbstractDonut2DSliceEffect</code> Abstract definition for effect donut2D slice painting operation
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractDonut2DSliceEffect extends AbstractDonut2DSlicePainter {

    /**
     * override this method to make an effect on the specified slice in the
     * given graphics context
     * 
     * @param g2d
     *            the graphics context
     * @param donut2D
     *            the host donut2D for the given slice
     * @param slice
     *            the slice to on which make an effect
     */
    protected abstract void paintDonut2DSliceEffect(Graphics2D g2d, Donut2D donut2D,
            Donut2DSlice slice);

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.painter.AbstractDonut2DSlicePainter#paintDonut2DSlice(java.awt.Graphics2D, com.jensoft.core.plugin.donut2d.Donut2D, com.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    public final void paintDonut2DSlice(Graphics2D g2d, Donut2D donut2D, Donut2DSlice slice) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, slice.getAlpha()));
        paintDonut2DSliceEffect(g2d, donut2D, slice);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
