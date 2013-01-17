/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.area.painter.draw;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.function.area.AreaFunction;
import com.jensoft.core.plugin.function.area.painter.AbstractAreaPainter;

/**
 * defines abstract area curve draw
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractAreaDraw extends AbstractAreaPainter {

    /**
     * draw the specified area
     * 
     * @param g2d
     *            the graphics context
     * @param areaCurve
     *            the area curve to draw
     */
    protected abstract void paintAreaDraw(Graphics2D g2d, AreaFunction areaCurve);

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.area.painter.AbstractAreaPainter#paintArea
     * (java.awt.Graphics2D, com.jensoft.sw2d.core.plugin.area.AreaCurve)
     */
    @Override
    public final void paintArea(Graphics2D g2d, AreaFunction areaCurve) {
        paintAreaDraw(g2d, areaCurve);
    }

}
