/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.fill;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.plugin.pie.painter.AbstractPiePainter;
import com.jensoft.core.plugin.pie.painter.PiePainter;

/**
 * <code>AbstractPieFill</code>
 * <p>
 * Abstract definition to pie fill painting operation
 * </p>
 * 
 * @see AbstractPiePainter
 * @see Pie
 * @see PiePainter
 * @see PiePlugin
 * @author Sebastien Janaud
 */
public abstract class AbstractPieFill extends AbstractPiePainter {

    /**
     * pie fill painting operation
     * 
     * @param g2d
     *            the graphics context
     * @param pie
     *            the pie to fill
     */
    protected abstract void paintPieFill(Graphics2D g2d, Pie pie);

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.pie.painter.AbstractPiePainter#paintPie(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.pie.Pie)
     */
    @Override
    public final void paintPie(Graphics2D g2d, Pie pie) {
        paintPieFill(g2d, pie);
    }

}
