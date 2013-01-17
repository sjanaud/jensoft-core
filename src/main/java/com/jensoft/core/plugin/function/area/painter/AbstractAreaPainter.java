/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.area.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.function.area.AreaFunction;

/**
 * abstract Area Painter
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractAreaPainter implements AreaPainter {

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.area.painter.AreaPainter#paintArea(java.
     * awt.Graphics2D, com.jensoft.sw2d.core.plugin.area.AreaCurve)
     */
    @Override
    public void paintArea(Graphics2D g2d, AreaFunction areaCurve) {
    }

}
