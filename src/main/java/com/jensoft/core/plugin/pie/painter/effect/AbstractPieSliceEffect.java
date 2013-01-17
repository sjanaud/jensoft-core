/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.effect;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.painter.AbstractPieSlicePainter;

/**
 * <code>AbstractPieSliceEffect</code> Abstract definition for effect pie slice painting operation
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractPieSliceEffect extends AbstractPieSlicePainter {

    /**
     * override this method to make an effect on the specified slice in the
     * given graphics context
     * 
     * @param g2d
     *            the graphics context
     * @param pie
     *            the host pie for the given slice
     * @param slice
     *            the slice to on which make an effect
     */
    protected abstract void paintPieSliceEffect(Graphics2D g2d, Pie pie,
            PieSlice slice);

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.pie.painter.AbstractPieSlicePainter#paintPieSlice(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.pie.Pie, com.jensoft.sw2d.core.plugin.pie.PieSlice)
     */
    @Override
    public final void paintPieSlice(Graphics2D g2d, Pie pie, PieSlice slice) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, slice.getAlpha()));
        paintPieSliceEffect(g2d, pie, slice);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
