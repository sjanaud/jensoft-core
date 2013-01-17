/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.effect;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.painter.AbstractPiePainter;

/**
 * <code>AbstractPieEffect</code> Abstract definition for pie effect painting operation
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractPieEffect extends AbstractPiePainter {

    /**
     * pie effect painting operation
     * 
     * @param g2d
     *            the graphics context
     * @param pie
     *            the pie to effect
     */
    protected abstract void paintPieEffect(Graphics2D g2d, Pie pie);

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.pie.painter.AbstractPiePainter#paintPie(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.pie.Pie)
     */
    @Override
    public final void paintPie(Graphics2D g2d, Pie pie) {
        paintPieEffect(g2d, pie);
    }

}
