/**
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.curve.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.function.curve.Curve;

/**
 * this interface defines a curve painting operation
 * 
 * @author Sebastien Janaud
 */
public interface CurvePainter {

    /**
     * paint specified curve
     * 
     * @param g2d
     *            the graphics context
     * @param curve
     *            the curve to paint
     */
    public void paintCurve(Graphics2D g2d, Curve curve);
}
