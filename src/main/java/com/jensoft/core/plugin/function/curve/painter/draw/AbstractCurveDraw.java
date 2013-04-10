/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.curve.painter.draw;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.function.curve.CurveFunction;
import com.jensoft.core.plugin.function.curve.painter.CurvePainter;

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
    protected abstract void drawCurve(Graphics2D g2d, CurveFunction curve);

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.function.curve.painter.CurvePainter#paintCurve(java.awt.Graphics2D, com.jensoft.core.plugin.function.curve.CurveFunction)
     */
    @Override
    public final void paintCurve(Graphics2D g2d, CurveFunction curve) {
        drawCurve(g2d, curve);
    }

}
