/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.inflater.function;

import com.jensoft.core.plugin.function.curve.Curve;


/**
 * <code>X2DCurveElement</code> defines {@link Curve} related elements names that should be used in any XML x2d
 * view
 * document and curve particular purpose.
 * 
 * @author Sebastien Janaud
 */
public interface X2DCurveElement extends X2DFunctionElement {

    /** curve root element */
    public static String ELEMENT_CURVE_FUNCTION = "curve-function";

    /** curve name element */
    public static String ELEMENT_CURVE_NAME = "name";

    /** curve color element */
    public static String ELEMENT_CURVE_THEME_COLOR = "theme-color";

    /** curve draw element */
    public static String ELEMENT_CURVE_DRAW = "curve-draw";

    /** curve draw XSI default type */
    public static String ELEMENT_CURVE_DRAW_XSI_TYPE_DEFAULT = "CurveDefaultDraw";

    /** default draw color element */
    public static String ELEMENT_CURVE_DRAW_DEFAULT_AREA_COLOR = "color";

    /** curve stroke element */
    public static String ELEMENT_CURVE_DRAW_DEFAULT_AREA_STROKE = "stroke";

}
