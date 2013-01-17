/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.pie.Pie;

/**
 * Abstract definition for pie operation painting
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractPiePainter implements PiePainter {

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.pie.painter.PiePainter#paintPie(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.pie.Pie)
     */
    @Override
    public void paintPie(Graphics2D g2d, Pie pie) {
    }

}
