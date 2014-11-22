/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.lang;

import com.jensoft.core.view.View;

/**
 * <code>X2DView2DElement</code> defines {@link View} related elements names that should be used in any XML x2d view
 * document
 * and view particular purpose.
 * 
 * @author Sebastien Janaud
 */
public interface X2DView2DElement {

    public static String ELEMENT_VIEW_ROOT = "view2d";
    public static String ELEMENT_VIEW_KEY = "view-key";
    public static String ELEMENT_API_KEY = "api-key";
    public static String ELEMENT_VIEW_WIDTH = "width";
    public static String ELEMENT_VIEW_HEIGHT = "height";

    public static String ELEMENT_VIEW_BACKGROUND_BACKGROUND = "background";
    public static String ELEMENT_VIEW_BACKGROUND_OUTLINEROUND = "outline-round";
    public static String ELEMENT_VIEW_BACKGROUND_STROKE = "outline-stroke";
    public static String ELEMENT_VIEW_BACKGROUND_COLOR = "outline-color";

    public static String ELEMENT_VIEW_BACKGROUND_SHADER = "background-shader";

    public static String ELEMENT_VIEW_HOLDER_SOUTH = "south";
    public static String ELEMENT_VIEW_HOLDER_NORTH = "north";
    public static String ELEMENT_VIEW_HOLDER_EAST = "east";
    public static String ELEMENT_VIEW_HOLDER_WEST = "west";

    public static String ELEMENT_VIEW_WINDOW2D = "window2d";
    public static String ELEMENT_VIEW_WINDOW2D_TYPE_LINEAR = "Window2DLinear";
    public static String ELEMENT_VIEW_WINDOW2D_TYPE_LOGX = "Window2DLogX";
    public static String ELEMENT_VIEW_WINDOW2D_TYPE_LOGY = "Window2DLogY";
    public static String ELEMENT_VIEW_WINDOW2D_TYPE_LOG = "Window2DLog";
    public static String ELEMENT_VIEW_WINDOW2D_TYPE_TIMEX = "Window2DTimeX";
    public static String ELEMENT_VIEW_WINDOW2D_TYPE_TIMEY = "Window2DTimeY";
    public static String ELEMENT_VIEW_WINDOW2D_ID = "id";
    public static String ELEMENT_VIEW_WINDOW2D_NAME = "name";
    public static String ELEMENT_VIEW_WINDOW2D_MIN_X = "min-x";
    public static String ELEMENT_VIEW_WINDOW2D_MAX_X = "max-x";
    public static String ELEMENT_VIEW_WINDOW2D_MIN_Y = "min-y";
    public static String ELEMENT_VIEW_WINDOW2D_MAX_Y = "max-y";
    public static String ELEMENT_VIEW_WINDOW2D_THEME_COLOR = "theme-color";
    
    public static String ELEMENT_VIEW_WINDOW2D_TIMEX_MIN_X = "min-x-datetime";
    public static String ELEMENT_VIEW_WINDOW2D_TIMEX_MAX_X = "max-x-datetime";
    public static String ELEMENT_VIEW_WINDOW2D_TIMEY_MIN_Y = "min-y-datetime";
    public static String ELEMENT_VIEW_WINDOW2D_TIMEY_MAX_Y = "max-y-datetime";

    public static String ELEMENT_VIEW_PLUGIN = "plugin";
    public static String ELEMENT_VIEW_PLUGIN_ID = "id";
    public static String ELEMENT_VIEW_PLUGIN_NAME = "name";
    public static String ELEMENT_VIEW_PLUGIN_CLASS = "class";

}
