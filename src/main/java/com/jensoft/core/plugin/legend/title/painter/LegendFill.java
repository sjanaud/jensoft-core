/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.title.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.legend.title.Legend;

/**
 * LegendDraw defines legend filling operation
 * 
 * @author Sebastien Janaud
 */
public abstract class LegendFill extends AbstractLegendPainter {

    /**
     * paint legend fill
     * 
     * @param g2d
     * @param legend
     */
    protected abstract void paintLegendFill(Graphics2D g2d, Legend legend);

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.legend.painter.AbstractLegendPainter#paintLegend(java.awt.Graphics2D, com.jensoft.core.plugin.legend.Legend)
     */
    @Override
    public final void paintLegend(Graphics2D g2d, Legend legend) {
        paintLegendFill(g2d, legend);
    }

}
