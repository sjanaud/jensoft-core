/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.inflater.function;

import com.jensoft.core.plugin.function.curve.Curve;

/**
 * <code>X2DCurveElement</code> defines {@link Curve} related elements names that should be used in any XML x2d view
 * document and curve particular purpose.
 * 
 * @author Sebastien Janaud
 */
public interface X2DScatterElement extends X2DFunctionElement {

    /** curve root element */
    public static String ELEMENT_CURVE = "curve";

    /** curve name element */
    public static String ELEMENT_CURVE_NAME = "name";

    /** curve color element */
    public static String ELEMENT_CURVE_COLOR = "color";

    /** curve stroke element */
    public static String ELEMENT_CURVE_STROKE = "stroke";

}
