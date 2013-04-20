/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function;

import java.awt.Color;
import java.awt.Stroke;

import com.jensoft.core.plugin.Toolkit;
import com.jensoft.core.plugin.function.area.Area;
import com.jensoft.core.plugin.function.area.painter.draw.AbstractAreaDraw;
import com.jensoft.core.plugin.function.curve.Curve;
import com.jensoft.core.plugin.function.curve.painter.draw.CurveDefaultDraw;
import com.jensoft.core.plugin.function.scatter.Scatter;
import com.jensoft.core.plugin.function.scatter.morphe.RectangleMorphe;
import com.jensoft.core.plugin.function.scatter.morphe.ScatterMorphe;
import com.jensoft.core.plugin.function.scatter.painter.ScatterDraw;
import com.jensoft.core.plugin.function.scatter.painter.ScatterFill;
import com.jensoft.core.plugin.function.scatter.painter.fill.ScatterDefaultFill;
import com.jensoft.core.plugin.function.source.SourceFunction;

public class FunctionToolkit extends Toolkit {

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @return curve
     */
    public static Curve createCurveFunction(SourceFunction source) {
        Curve curve = new Curve(source);
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
    public static Curve createCurveFunction(SourceFunction source, Color color) {
        Curve curve = new Curve(source);
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
    public static Curve createCurveFunction(SourceFunction source, Color color,
            Stroke stroke) {
        Curve curve = new Curve(source);
        curve.setThemeColor(color);
        curve.setCurveDraw(new CurveDefaultDraw(stroke));
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
    public static Curve createCurveFunction(SourceFunction source, Color color,CurveDefaultDraw curveDraw) {
        Curve curve = new Curve(source);
        curve.setThemeColor(color);
        curve.setCurveDraw(curveDraw);
        return curve;
    }
    
    /**
     * create default area curve with specified parameters
     * 
     * @param source
     *            the area curve source
     * @return area
     */
    public static Area createAreaFunction(SourceFunction source) {
        Area curve = new Area(source);
        curve.setThemeColor(Color.BLACK);
        return curve;
    }

    /**
     * create default area with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @return area
     */
    public static Area createAreaFunction(SourceFunction source, Color color) {
        Area curve = new Area(source);
        curve.setThemeColor(color);
        return curve;
    }

    /**
     * create default area with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @return area
     */
    public static Area createAreaFunction(SourceFunction source, Color color,
            AbstractAreaDraw curveAreaDraw) {
        Area curve = new Area(source);
        curve.setAreaDraw(curveAreaDraw);
        curve.setThemeColor(color);
        return curve;
    }
    
    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @return curve
     */
    public static Scatter createScatterFunction(SourceFunction source) {
        Scatter curve = new Scatter(source);
        curve.setScatterMorphe(new RectangleMorphe(3, 3));
        curve.setScatterFill(new ScatterDefaultFill());
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
    public static Scatter createScatterFunction(SourceFunction source, Color color) {
        Scatter curve = new Scatter(source);
        curve.setScatterMorphe(new RectangleMorphe(3, 3));
        curve.setScatterFill(new ScatterDefaultFill());
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
     * @param morphe
     *            the scatter morphe
     * @return curve
     */
    public static Scatter createScatterFunction(SourceFunction source, Color color,
            ScatterMorphe morphe) {
        Scatter curve = new Scatter(source);
        curve.setThemeColor(color);
        curve.setScatterMorphe(morphe);
        curve.setScatterFill(new ScatterDefaultFill());
        return curve;
    }

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @param morphe
     *            the scatter morphe
     * @param scatterFill
     *            the scatter fill
     * @return curve
     */
    public static Scatter createScatterFunction(SourceFunction source, Color color,
            ScatterMorphe morphe, ScatterFill scatterFill) {
        Scatter curve = new Scatter(source);
        curve.setThemeColor(color);
        curve.setScatterMorphe(morphe);
        curve.setScatterFill(scatterFill);
        return curve;
    }

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @param morphe
     *            the scatter morphe
     * @param scatterFill
     *            the scatter fill
     * @param scatterDraw
     * @return curve
     */
    public static Scatter createScatterFunction(SourceFunction source, Color color,
            ScatterMorphe morphe, ScatterFill scatterFill,
            ScatterDraw scatterDraw) {
        Scatter curve = new Scatter(source);
        curve.setThemeColor(color);
        curve.setScatterMorphe(morphe);
        curve.setScatterFill(scatterFill);
        curve.setScatterDraw(scatterDraw);
        return curve;
    }

}
