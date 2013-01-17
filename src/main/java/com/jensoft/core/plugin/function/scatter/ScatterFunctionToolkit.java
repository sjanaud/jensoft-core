/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.scatter;

import java.awt.Color;

import com.jensoft.core.plugin.Toolkit;
import com.jensoft.core.plugin.function.FunctionPlugin.ScatterFunctionPlugin;
import com.jensoft.core.plugin.function.scatter.ScatterFunction.ScatterPoint;
import com.jensoft.core.plugin.function.scatter.morphe.RectangleMorphe;
import com.jensoft.core.plugin.function.scatter.morphe.ScatterMorphe;
import com.jensoft.core.plugin.function.scatter.painter.ScatterDraw;
import com.jensoft.core.plugin.function.scatter.painter.ScatterFill;
import com.jensoft.core.plugin.function.scatter.painter.fill.ScatterDefaultFill;
import com.jensoft.core.plugin.function.source.SourceFunction;

/**
 * <code>ScatterFunctionToolkit</code>
 * 
 * @see ScatterPoint
 * @see ScatterFunction
 * @see ScatterFunctionPlugin
 * @see ScatterDraw
 * @see ScatterFill
 * @see ScatterMorphe
 * @author Sebastien Janaud
 */
public class ScatterFunctionToolkit extends Toolkit {

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @return curve
     */
    public static ScatterFunction createScatterFunction(SourceFunction source) {
        ScatterFunction curve = new ScatterFunction(source);
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
    public static ScatterFunction createScatterFunction(SourceFunction source, Color color) {
        ScatterFunction curve = new ScatterFunction(source);
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
    public static ScatterFunction createScatterFunction(SourceFunction source, Color color,
            ScatterMorphe morphe) {
        ScatterFunction curve = new ScatterFunction(source);
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
    public static ScatterFunction createScatterFunction(SourceFunction source, Color color,
            ScatterMorphe morphe, ScatterFill scatterFill) {
        ScatterFunction curve = new ScatterFunction(source);
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
    public static ScatterFunction createScatterFunction(SourceFunction source, Color color,
            ScatterMorphe morphe, ScatterFill scatterFill,
            ScatterDraw scatterDraw) {
        ScatterFunction curve = new ScatterFunction(source);
        curve.setThemeColor(color);
        curve.setScatterMorphe(morphe);
        curve.setScatterFill(scatterFill);
        curve.setScatterDraw(scatterDraw);
        return curve;
    }

}
