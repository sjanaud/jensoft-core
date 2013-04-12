/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.area;

import java.awt.Color;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.plugin.Toolkit;
import com.jensoft.core.plugin.function.FunctionPlugin.AreaFunctionPlugin;
import com.jensoft.core.plugin.function.area.painter.draw.AbstractAreaDraw;
import com.jensoft.core.plugin.function.source.LineSourceFunction;
import com.jensoft.core.plugin.function.source.RegressionSourceFunction;
import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.plugin.function.source.SplineSourceFunction;

/**
 * <code>AreaFunctionToolkit</code>
 * 
 * @see AreaFunction
 * @see AreaFunctionPlugin
 * @see AbstractAreaDraw
 * @see LineSourceFunction
 * @see SplineSourceFunction
 * @see RegressionSourceFunction
 * @see GlyphMetric
 * @author Sebastien Janaud
 */
public class AreaFunctionToolkit extends Toolkit {

    /**
     * create default area curve with specified parameters
     * 
     * @param source
     *            the area curve source
     * @return area curve
     */
    public static AreaFunction createArea(SourceFunction source) {
        AreaFunction curve = new AreaFunction(source);
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
    public static AreaFunction createArea(SourceFunction source, Color color) {
        AreaFunction curve = new AreaFunction(source);
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
     * @return curve
     */
    public static AreaFunction createArea(SourceFunction source, Color color,
            AbstractAreaDraw curveAreaDraw) {
        AreaFunction curve = new AreaFunction(source);
        curve.setAreaDraw(curveAreaDraw);
        curve.setThemeColor(color);
        return curve;
    }

}
