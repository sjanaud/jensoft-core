/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.area.painter.fill;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.function.area.Area;
import org.jensoft.core.plugin.function.area.painter.AbstractAreaPainter;

/**
 * defines abstract area curve fill
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractAreaFill extends AbstractAreaPainter {

    /**
     * fill the specified area
     * 
     * @param g2d
     *            the graphics context
     * @param areaCurve
     *            the area curve to fill
     */
    protected abstract void paintAreaFill(Graphics2D g2d, Area areaCurve);

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.function.area.painter.AbstractAreaPainter#paintArea(java.awt.Graphics2D, com.jensoft.core.plugin.function.area.AreaFunction)
     */
    @Override
    public final void paintArea(Graphics2D g2d, Area areaCurve) {
        paintAreaFill(g2d, areaCurve);
    }

}
