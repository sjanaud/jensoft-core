/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.curve;

import com.jensoft.core.plugin.function.core.Function;
import com.jensoft.core.plugin.function.curve.painter.draw.AbstractCurveDraw;
import com.jensoft.core.plugin.function.curve.painter.draw.CurveDefaultDraw;
import com.jensoft.core.plugin.function.source.SourceFunction;

/**
 * <code>Curve</code> defines curve line function.
 * 
 * @author sebastien janaud
 */
public class CurveFunction extends Function {

    /** painter to draw curve */
    private AbstractCurveDraw curveDraw;

    /**
     * create new curve from specified source
     * 
     * @param source
     *            the source of this curve
     */
    public CurveFunction(SourceFunction source) {
        super("curve", source);
        curveDraw = new CurveDefaultDraw();
    }

    /**
     * get the curve draw
     * 
     * @return the curveDraw
     */
    public AbstractCurveDraw getCurveDraw() {
        return curveDraw;
    }

    /**
     * set the curve draw
     * 
     * @param curveDraw
     *            the curveDraw to set
     */
    public void setCurveDraw(AbstractCurveDraw curveDraw) {
        this.curveDraw = curveDraw;
    }

}
