/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.fill;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PieSlice;

/**
 * <code>PieCompatibleFill</code> a compatible pie fill is a pie fill based on slice pie fill
 * 
 * @author Sebastien Janaud
 */
public class PieCompatibleFill extends AbstractPieFill {

    /** the section fill */
    private AbstractPieSliceFill sliceFill;

    /**
     * create a new compatible pie fill with the specified section fill
     * 
     * @param compatibleSliceFill
     *            the section fill to set
     */
    public PieCompatibleFill(AbstractPieSliceFill compatibleSliceFill) {
        sliceFill = compatibleSliceFill;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.pie.painter.fill.AbstractPieFill#paintPieFill(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.pie.Pie)
     */
    @Override
    protected final void paintPieFill(Graphics2D g2d, Pie pie) {
        for (PieSlice pieSection : pie.getSlices()) {
            sliceFill.paintPieSlice(g2d, pie, pieSection);
        }
    }

    /**
     * @return the sliceFill
     */
    public AbstractPieSliceFill getSliceFill() {
        return sliceFill;
    }

    /**
     * @param sliceFill
     *            the sliceFill to set
     */
    public void setSliceFill(AbstractPieSliceFill sliceFill) {
        this.sliceFill = sliceFill;
    }

}
