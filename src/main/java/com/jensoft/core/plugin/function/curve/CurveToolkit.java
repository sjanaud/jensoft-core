/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.curve;

import java.awt.Color;
import java.awt.Stroke;

import com.jensoft.core.plugin.Toolkit;
import com.jensoft.core.plugin.function.curve.painter.draw.CurveDefaultDraw;
import com.jensoft.core.plugin.function.source.SourceFunction;

public class CurveToolkit extends Toolkit {

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @return curve
     */
    public static CurveFunction createCurveFunction(SourceFunction source) {
        CurveFunction curve = new CurveFunction(source);
        curve.setThemeColor(Color.BLACK);
        return curve;
    }

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @return curve
     */
    public static CurveFunction createCurveFunction(SourceFunction source, Color color) {
        CurveFunction curve = new CurveFunction(source);
        curve.setThemeColor(color);
        return curve;
    }

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @param stroke
     *            the curve stroke
     * @return curve
     */
    public static CurveFunction createCurveFunction(SourceFunction source, Color color,
            Stroke curveStroke) {
        CurveFunction curve = new CurveFunction(source);
        curve.setThemeColor(color);
        curve.setCurveDraw(new CurveDefaultDraw(curveStroke));
        return curve;
    }

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @param curveDraw
     *            the curve draw
     * @return curve
     */
    public static CurveFunction createCurveFunction(SourceFunction source, Color color,CurveDefaultDraw curveDraw) {
        CurveFunction curve = new CurveFunction(source);
        curve.setThemeColor(color);
        curve.setCurveDraw(curveDraw);
        return curve;
    }

}
