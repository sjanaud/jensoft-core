/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pie.painter.draw;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.pie.Pie;
import org.jensoft.core.plugin.pie.PieSlice;
import org.jensoft.core.plugin.pie.painter.AbstractPieSlicePainter;

/**
 * <code>AbstractPieSliceDraw</code>
 * <p>
 * Abstract definition for slice draw painting operation
 * <p>
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractPieSliceDraw extends AbstractPieSlicePainter {

    /**
     * override this method to draw specified slice in the given graphics
     * context
     * 
     * @param g2d
     *            the graphics context
     * @param pie
     *            the host pie for the given slice
     * @param slice
     *            the slice to be drawn
     */
    protected abstract void paintPieSliceDraw(Graphics2D g2d, Pie pie,
            PieSlice slice);

    
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.pie.painter.AbstractPieSlicePainter#paintPieSlice(java.awt.Graphics2D, org.jensoft.core.plugin.pie.Pie, org.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    public final void paintPieSlice(Graphics2D g2d, Pie pie, PieSlice slice) {
        paintPieSliceDraw(g2d, pie, slice);
    }
}
