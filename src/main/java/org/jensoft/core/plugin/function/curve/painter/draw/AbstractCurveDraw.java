/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.curve.painter.draw;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.function.curve.Curve;
import org.jensoft.core.plugin.function.curve.painter.CurvePainter;

/**
 * <code>AbstractCurveDraw</code> defines abstract curve painting operation.
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractCurveDraw implements CurvePainter {

    /**
     * draw specified curve.
     * 
     * @param g2d
     *            the graphics context
     * @param curve
     *            the curve to draw
     */
    protected abstract void drawCurve(Graphics2D g2d, Curve curve);

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.function.curve.painter.CurvePainter#paintCurve(java.awt.Graphics2D, org.jensoft.core.plugin.function.curve.CurveFunction)
     */
    @Override
    public final void paintCurve(Graphics2D g2d, Curve curve) {
        drawCurve(g2d, curve);
    }

}
