/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.effect;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PieSlice;

/**
 * <code>PieCompatibleEffect</code>
 * <p>
 * a compatible pie effect use slice effect definition to draw effect on each slice effect
 * </p>
 * 
 * @author Sebastien Janaud
 */
public class PieCompatibleEffect extends AbstractPieEffect {

    /** the slice effect */
    private AbstractPieSliceEffect sliceEffect;

    /** reload flag */
    private boolean reload = false;

    /**
     * @return the reload
     */
    public boolean isReload() {
        return reload;
    }

    /**
     * @param reload
     *            the reload to set
     */
    public void setReload(boolean reload) {
        this.reload = reload;
    }

    /**
     * Create a new compatible effect based on slice effect
     * 
     * @param sliceEffect
     *            the compatible slice effect to set
     */
    public PieCompatibleEffect(AbstractPieSliceEffect sliceEffect) {
        this.sliceEffect = sliceEffect;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.painter.effect.AbstractPieEffect#paintPieEffect(java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie)
     */
    @Override
    protected final void paintPieEffect(Graphics2D g2d, Pie pie) {
        for (PieSlice slice : pie.getSlices()) {
            sliceEffect.paintPieSlice(g2d, pie, slice);
        }
    }

}
