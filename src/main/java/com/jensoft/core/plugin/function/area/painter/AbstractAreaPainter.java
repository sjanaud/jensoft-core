/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.area.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.function.area.AreaFunction;

/**
 * <code>AbstractAreaPainter</code>
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractAreaPainter implements AreaPainter {

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.function.area.painter.AreaPainter#paintArea(java.awt.Graphics2D, com.jensoft.core.plugin.function.area.AreaFunction)
     */
    @Override
    public void paintArea(Graphics2D g2d, AreaFunction areaCurve) {
    }

}
