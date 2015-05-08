/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.legend.title.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.legend.title.TitleLegend;

/**
 * <code>AbstractTitleLegendFill</code> defines title legend fill operation
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractTitleLegendFill extends AbstractTitleLegendPainter {

    /**
     * fill title legend, implements this method to fill title legend
     * 
     * @param g2d
     * @param title legend
     */
    protected abstract void paintLegendFill(Graphics2D g2d, TitleLegend legend);

  
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.legend.title.painter.AbstractTitleLegendPainter#paintLegend(java.awt.Graphics2D, org.jensoft.core.plugin.legend.title.TitleLegend)
     */
    @Override
    public final void paintLegend(Graphics2D g2d, TitleLegend legend) {
        paintLegendFill(g2d, legend);
    }

}
