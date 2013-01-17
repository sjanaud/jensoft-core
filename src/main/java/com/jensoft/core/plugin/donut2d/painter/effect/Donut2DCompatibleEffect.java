/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.painter.effect;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;

/**
 * <code>CompatiblePieEffect</code>
 * <p>
 * a compatible donut2D effect use slice effect definition to draw effect each slice effect
 * </p>
 * 
 * @author Sebastien Janaud
 */
public class Donut2DCompatibleEffect extends AbstractDonut2DEffect {

    /** the slice effect */
    private AbstractDonut2DSliceEffect sliceEffect;

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
    public Donut2DCompatibleEffect(AbstractDonut2DSliceEffect sliceEffect) {
        this.sliceEffect = sliceEffect;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.donut2d.painter.effect.AbstractDonut2DEffect#paintDonut2DEffect(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.donut2d.Donut2D)
     */
    @Override
    protected final void paintDonut2DEffect(Graphics2D g2d, Donut2D donut2D) {
        for (Donut2DSlice slice : donut2D.getSlices()) {
            sliceEffect.paintDonut2DSlice(g2d, donut2D, slice);
        }
    }

}
