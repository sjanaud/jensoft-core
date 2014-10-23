/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.painter.fill;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.util.List;

import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;

/**
 * <code>Donut2DDefaultFill</code>
 */
public class Donut2DDefaultFill extends AbstractDonut2DFill {

    /**
     * create default fill
     */
    public Donut2DDefaultFill() {
    }

 
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.painter.fill.AbstractDonut2DFill#paintDonut2DFill(java.awt.Graphics2D, com.jensoft.core.plugin.donut2d.Donut2D)
     */
    @Override
    public void paintDonut2DFill(Graphics2D g2d, Donut2D donut2D) {
        List<Donut2DSlice> sections = donut2D.getSlices();

        for (int j = 0; j < sections.size(); j++) {
        	Donut2DSlice s = sections.get(j);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, s.getAlpha()));
          
            g2d.setColor(s.getThemeColor());
            g2d.fill(s.getSlicePath());
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

}
