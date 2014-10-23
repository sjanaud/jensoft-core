/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.title.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.legend.title.TitleLegend;

/**
 * <code>AbstractTitleLegendDraw</code> defines title legend draw operation
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractTitleLegendDraw extends AbstractTitleLegendPainter {

    /**
     * draw title legend, implements this method to draw title legend
     * 
     * @param g2d
     * @param legend
     */
    protected abstract void paintLegendDraw(Graphics2D g2d, TitleLegend legend);
   
   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.legend.title.painter.AbstractTitleLegendPainter#paintLegend(java.awt.Graphics2D, com.jensoft.core.plugin.legend.title.TitleLegend)
     */
    @Override
    public final void paintLegend(Graphics2D g2d, TitleLegend legend) {
        paintLegendDraw(g2d, legend);
    }

}
