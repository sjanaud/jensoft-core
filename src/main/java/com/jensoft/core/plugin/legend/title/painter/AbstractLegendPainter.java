/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.title.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.legend.title.TitleLegend;

/**
 * AbstractLegendPainter
 */
public abstract class AbstractLegendPainter implements LegendPainter {

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.legend.painter.LegendPainter#paintLegend(java.awt.Graphics2D, com.jensoft.core.plugin.legend.Legend)
     */
    @Override
    public void paintLegend(Graphics2D g2d, TitleLegend legend) {
    }

}
