/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.inflater.function;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.plugin.function.source.SourceFunction;

/**
 * <code>X2DFunctionElement</code> defines {@link GlyphMetric} and {@link SourceFunction} related elements names that
 * should
 * be used in any XML x2d view document and curve particular purpose.
 * 
 * @author Sebastien Janaud
 */
public interface X2DFunctionElement {

    /** serie element */
    public static String ELEMENT_SOURCE_FUNCTION = "source-function";

    /** serie id element */
    public static String ELEMENT_SOURCEFUNCTION_ID = "id";

    /** serie name element */
    public static String ELEMENT_SOURCEFUNCTION_NAME = "name";

    /** serie source x element */
    public static String ELEMENT_SOURCEFUNCTION_SOURCE_X = "source-x";

    /** serie source y element */
    public static String ELEMENT_SOURCEFUNCTION_SOURCE_Y = "source-y";

    /** interpolate serie delta element */
    public static String ELEMENT_SOURCEFUNCTION_INTERPOLATE_DELTA = "delta";

    /** regression serie delta element */
    public static String ELEMENT_SOURCEFUNCTION_REGRESSION_DELTA = "delta";

    /** affine source function element type */
    public static String ELEMENT_SOURCEFUNCTION_XSI_TYPE_AFFINE_SOURCEFUNCTION = "AffineSourceFunction";

    /** interpolate source function element type */
    public static String ELEMENT_SOURCEFUNCTION_XSI_TYPE_INTERPOLATE_SOURCEFUNCTION = "InterpolateSourceFunction";

    /** regression source function element type */
    public static String ELEMENT_SOURCEFUNCTION_XSI_TYPE_REGRESSION_SOURCEFUNCTION = "RegressionSourceFunction";

    /** glyph root element */
    public static String ELEMENT_GLYPH = "glyph";

    /** glyph x value element */
    public static String ELEMENT_GLYPH_X_VALUE = "x-value";

    /** glyph text element */
    public static String ELEMENT_GLYPH_TEXT = "text";

    /** glyph divergence element */
    public static String ELEMENT_GLYPH_DIVERGENCE = "divergence";

    /** glyph style element */
    public static String ELEMENT_GLYPH_STYLE = "style";

    /** glyph color element */
    public static String ELEMENT_GLYPH_COLOR_1 = "color-1";

    /** glyph shader element */
    public static String ELEMENT_GLYPH_COLOR_2 = "color-2";

    /** glyph marker element */
    public static String ELEMENT_GLYPH_MARKER = "marker";

    /** round glyph element type */
    public static String ELEMENT_MARKER_XSI_TYPE_MARKER_ROUND = "RoundGlyphMarker";

    /** round glyph radius element type */
    public static String ELEMENT_MARKER_ROUND_RADIUS = "radius";

    /** round glyph draw color element type */
    public static String ELEMENT_MARKER_ROUND_DRAW_COLOR = "draw-color";

    /** round glyph fill color element type */
    public static String ELEMENT_MARKER_ROUND_FILL_COLOR = "fill-color";

    /** tic tac glyph element type */
    public static String ELEMENT_MARKER_XSI_TYPE_MARKER_TICTAC = "TicTacGlyphMarker";

    /** tic tac glyph theme color element type */
    public static String ELEMENT_MARKER_TICTAC_THEME_COLOR = "theme-color";

    /** tic tac glyph thickness element type */
    public static String ELEMENT_MARKER_TICTAC_THICKNESS = "thickness";

    /** tic tac glyph divergence left and right element type */
    public static String ELEMENT_MARKER_TICTAC_DIVERGENCE = "divergence";

    /** tic tac glyph divergence right element type */
    public static String ELEMENT_MARKER_TICTAC_DIVERGENCE_LEFT = "divergence-left";

    /** tic tac glyph divergence left element type */
    public static String ELEMENT_MARKER_TICTAC_DIVERGENCE_RIGHT = "divergence-right";

}
