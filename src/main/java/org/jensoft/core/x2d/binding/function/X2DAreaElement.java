/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.function;

import com.jensoft.core.plugin.function.area.Area;

/**
 * <code>X2DAreaElement</code> defines {@link Area} related elements names that should be used in any XML x2d
 * view
 * document and area particular purpose.
 * 
 * @author Sebastien Janaud
 */
public interface X2DAreaElement extends X2DFunctionElement {

    /** area function root element */
    public static String ELEMENT_AREA_FUNCTION = "area-function";

    /** area function name element */
    public static String ELEMENT_AREA_NAME = "name";

    /** area function color element */
    public static String ELEMENT_AREA_THEME_COLOR = "theme-color";

    /** area function base element */
    public static String ELEMENT_AREA_BASE = "base";

    public static String ELEMENT_AREA_DRAW = "area-draw";

    public static String ELEMENT_AREA_FILL = "area-fill";

    /** area function draw area color element */
    public static String ELEMENT_AREA_DRAW_XSI_TYPE_DEFAULT = "AreaDefaultDraw";

    /** area function draw area color element */
    public static String ELEMENT_AREA_DRAW_AREA_COLOR = "area-color";

    /** area function draw area stroke element */
    public static String ELEMENT_AREA_DRAW_AREA_STROKE = "area-stroke";

    /** area function draw base color element */
    public static String ELEMENT_AREA_DRAW_BASE_COLOR = "base-color";

    /** area function draw base stroke element */
    public static String ELEMENT_AREA_DRAW_BASE_STROKE = "base-stroke";

    /** area function draw area color element */
    public static String ELEMENT_AREA_FILL_XSI_TYPE_DEFAULT = "AreaDefaultFill";

    /** area function fill default color element */
    public static String ELEMENT_AREA_FILL_DEFAULT_FILL_COLOR = "color";

    /** area function draw area color element */
    public static String ELEMENT_AREA_FILL_XSI_TYPE_GRADIENT = "AreaGradientFill";

    /** area function fill gradient color 1 element */
    public static String ELEMENT_AREA_GRADIENT_FILL_COLOR_1 = "color-1";

    /** area function fill gradient color 1 element */
    public static String ELEMENT_AREA_GRADIENT_FILL_COLOR_2 = "color-2";

    /** area function fill gradient shader element */
    public static String ELEMENT_AREA_GRADIENT_FILL_SHADER = "shader";
}
