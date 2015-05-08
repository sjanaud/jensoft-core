/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pie.painter.fill;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import org.jensoft.core.plugin.pie.Pie;
import org.jensoft.core.plugin.pie.PiePlugin;
import org.jensoft.core.plugin.pie.PieSlice;
import org.jensoft.core.plugin.pie.painter.AbstractPieSlicePainter;
import org.jensoft.core.plugin.pie.painter.PiePainter;

/**
 * <code>AbstractPieSliceFill</code>
 * <p>
 * Abstract definition for slice fill rendering
 * </p>
 * 
 * @see AbstractPieSlicePainter
 * @see Pie
 * @see PiePainter
 * @see PiePlugin
 * @author Sebastien Janaud
 */
public abstract class AbstractPieSliceFill extends AbstractPieSlicePainter {

    /**
     * override this method to fill specified slice in the given graphics
     * context
     * 
     * @param g2d
     *            the graphics context
     * @param pie
     *            the host pie for the given slice
     * @param slice
     *            the slice to fill
     */
    protected abstract void paintPieSliceFill(Graphics2D g2d, Pie pie,
            PieSlice slice);

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.pie.painter.AbstractPieSlicePainter#paintPieSlice(java.awt.Graphics2D, org.jensoft.core.plugin.pie.Pie, org.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    public final void paintPieSlice(Graphics2D g2d, Pie pie, PieSlice slice) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, slice.getAlpha()));
        paintPieSliceFill(g2d, pie, slice);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
