/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.legend.Legend;

/**
 * LegendDraw defines legend drawing operation
 * 
 * @author Sebastien Janaud
 */
public abstract class LegendDraw extends AbstractLegendPainter {

    /**
     * paint legend draw
     * 
     * @param g2d
     * @param legend
     */
    protected abstract void paintLegendDraw(Graphics2D g2d, Legend legend);

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.legend.painter.AbstractLegendPainter#paintLegend
     * (java.awt.Graphics2D, com.jensoft.sw2d.core.plugin.legend.Legend)
     */
    @Override
    public final void paintLegend(Graphics2D g2d, Legend legend) {
        paintLegendDraw(g2d, legend);
    }

}
