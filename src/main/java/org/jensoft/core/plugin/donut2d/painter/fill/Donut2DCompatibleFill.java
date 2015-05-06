/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.painter.fill;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.donut2d.Donut2D;
import org.jensoft.core.plugin.donut2d.Donut2DSlice;

/**
 * <code>Donut2DCompatibleFill</code> a compatible donut2D fill is a donut2D fill based on slice donut2D fill
 * 
 * @author Sebastien Janaud
 */
public class Donut2DCompatibleFill extends AbstractDonut2DFill {

    /** the slice fill */
    private AbstractDonut2DSliceFill sliceFill;

    /**
     * create a new compatible donut2D fill with the specified section fill
     * 
     * @param compatibleSliceFill
     *            the section fill to set
     */
    public Donut2DCompatibleFill(AbstractDonut2DSliceFill compatibleSliceFill) {
        sliceFill = compatibleSliceFill;
    }

 
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.painter.fill.AbstractDonut2DFill#paintDonut2DFill(java.awt.Graphics2D, com.jensoft.core.plugin.donut2d.Donut2D)
     */
    @Override
    protected final void paintDonut2DFill(Graphics2D g2d, Donut2D donut2D) {
        for (Donut2DSlice donut2DSlice : donut2D.getSlices()) {
            sliceFill.paintDonut2DSlice(g2d, donut2D, donut2DSlice);
        }
    }

    /**
     * @return the sliceFill
     */
    public AbstractDonut2DSliceFill getSliceFill() {
        return sliceFill;
    }

    /**
     * @param sliceFill
     *            the sliceFill to set
     */
    public void setSliceFill(AbstractDonut2DSliceFill sliceFill) {
        this.sliceFill = sliceFill;
    }

}
