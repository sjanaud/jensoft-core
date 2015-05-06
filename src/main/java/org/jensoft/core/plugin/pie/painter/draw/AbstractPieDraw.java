/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pie.painter.draw;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.pie.Pie;
import org.jensoft.core.plugin.pie.painter.AbstractPiePainter;

/**
 * <code>AbstractPieDraw</code> Abstract definition for pie draw operation
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractPieDraw extends AbstractPiePainter {

    /**
     * pie draw painting operation
     * 
     * @param g2d
     *            the graphics context
     * @param pie
     *            the pie to draw
     */
    protected abstract void paintPieDraw(Graphics2D g2d, Pie pie);

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.painter.AbstractPiePainter#paintPie(java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie)
     */
    @Override
    public final void paintPie(Graphics2D g2d, Pie pie) {
        paintPieDraw(g2d, pie);
    }

}
