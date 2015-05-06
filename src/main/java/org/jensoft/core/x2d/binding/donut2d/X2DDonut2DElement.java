/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.donut2d;

import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.painter.effect.Donut2DCompoundEffect;
import com.jensoft.core.plugin.donut2d.painter.effect.Donut2DLinearEffect;
import com.jensoft.core.plugin.donut2d.painter.effect.Donut2DReflectionEffect;
import com.jensoft.core.plugin.donut2d.painter.fill.Donut2DDefaultFill;
import com.jensoft.core.plugin.donut2d.painter.fill.Donut2DRadialFill;
import com.jensoft.core.plugin.donut2d.painter.label.Donut2DBorderLabel;
import com.jensoft.core.plugin.donut2d.painter.label.Donut2DPathLabel;
import com.jensoft.core.plugin.donut2d.painter.label.Donut2DRadialLabel;

/**
 * <code>X2DDonut2DElement</code> defines {@link Donut2D} related elements names that should be used in any XML x2d view
 * document
 * and donut2D particular purpose.
 * 
 * @author Sebastien Janaud
 */
public interface X2DDonut2DElement {

    /** donut2D root element */
    public static String ELEMENT_DONUT2D = "donut2d";
    
    /** donut2D center x element */
    public static String ELEMENT_DONUT2D_NAME = "name";

    /** donut2D center x element */
    public static String ELEMENT_DONUT2D_X = "x";

    /** donut2D center y element */
    public static String ELEMENT_DONUT2D_Y = "y";

    /** donut2D inner radius element */
    public static String ELEMENT_DONUT2D_INNER_RADIUS = "inner-radius";

    /** donut2D outer radius element */
    public static String ELEMENT_DONUT2D_OUTER_RADIUS = "outer-radius";

    /** donut2D start angle element */
    public static String ELEMENT_DONUT2D_START_ANGLE = "start-angle";

    /** donut2D projection nature element */
    public static String ELEMENT_DONUT2D_NATURE = "nature";

    /** donut2D slice root element */
    public static String ELEMENT_DONUT2D_SLICE = "slice";

    /** donut2D slice name element */
    public static String ELEMENT_DONUT2D_SLICE_NAME = "name";

    /** donut2D slice value element */
    public static String ELEMENT_DONUT2D_SLICE_VALUE = "value";

    /** donut2D slice divergence element */
    public static String ELEMENT_DONUT2D_SLICE_DIVERGENCE = "divergence";

    /** donut2D slice color element */
    public static String ELEMENT_DONUT2D_SLICE_COLOR = "slice-color";

    /** donut2D slice label root element */
    public static String ELEMENT_DONUT2D_SLICE_LABEL = "label";

    /** donut2D slice label text element */
    public static String ELEMENT_DONUT2D_LABEL_TEXT = "text";

    /** donut2D slice label text color element */
    public static String ELEMENT_DONUT2D_LABEL_TEXT_COLOR = "text-color";

    /** donut2D slice label text font element */
    public static String ELEMENT_DONUT2D_LABEL_TEXT_FONT = "font";

    /** donut2D slice label text padding x element */
    public static String ELEMENT_DONUT2D_LABEL_PADDING_X = "text-padding-x";

    /** donut2D slice label text pading y element */
    public static String ELEMENT_DONUT2D_LABEL_PADDING_Y = "text-padding-y";

    /** donut2D slice label outline round element */
    public static String ELEMENT_DONUT2D_LABEL_OUTLINE_ROUND = "outline-round";

    /** donut2D slice label outline color element */
    public static String ELEMENT_DONUT2D_LABEL_OUTLINE_COLOR = "outline-color";

    /** donut2D slice label outline stroke element */
    public static String ELEMENT_DONUT2D_LABEL_OUTLINE_STROKE = "outline-stroke";

    /** donut2D slice label fill color element */
    public static String ELEMENT_DONUT2D_LABEL_FILL_COLOR = "fill-color";
    
    
    /** donut2D path label type */
    public static String ELEMENT_DONUT2D_LABEL_TYPE_PATH = Donut2DPathLabel.class.getSimpleName();
    
    /** donut2D path label type */
    public static String ELEMENT_DONUT2D_LABEL_TYPE_BORDER = Donut2DBorderLabel.class.getSimpleName();
    
    /** donut2D path label type */
    public static String ELEMENT_DONUT2D_LABEL_TYPE_RADIAL = Donut2DRadialLabel.class.getSimpleName();
    
    

    /** donut2D slice label shader element */
    public static String ELEMENT_DONUT2D_LABEL_SHADER = "shader";

    /** donut2D slice label style element */
    public static String ELEMENT_DONUT2D_LABEL_STYLE = "style";

    /** donut2D slice 'label path' divergence element */
    public static String ELEMENT_DONUT2D_LABEL_PATH_TEXT_DIVERGENCE = "text-divergence";

    /** donut2D slice 'label path' text position element */
    public static String ELEMENT_DONUT2D_LABEL_PATH_TEXT_POSITION = "text-position";

    /** donut2D slice 'label path' text side element */
    public static String ELEMENT_DONUT2D_LABEL_PATH_TEXT_SIDE = "text-side";

    /** donut2D slice 'label path' segment path element */
    public static String ELEMENT_DONUT2D_LABEL_PATH_SEGMENT_PATH = "segment-path";

    /** donut2D slice 'label path' text shader element */
    public static String ELEMENT_DONUT2D_LABEL_PATH_TEXT_SHADER = "text-shader";

    /** donut2D slice 'label radial' offset radius element */
    public static String ELEMENT_DONUT2D_LABEL_RADIAL_OFFSET_RADIUS = "offsetRadius";

    /** donut2D slice 'label border' margin element */
    public static String ELEMENT_DONUT2D_LABEL_BORDER_MARGIN = "label-margin";

    /** donut2D slice 'label border' link enable element */
    public static String ELEMENT_DONUT2D_LABEL_BORDER_LINK_ENABLE = "link-enable";

    /** donut2D slice 'label border' link extends element */
    public static String ELEMENT_DONUT2D_LABEL_BORDER_LINK_EXTENDS = "link-extends";

    /** donut2D slice 'label border' link color element */
    public static String ELEMENT_DONUT2D_LABEL_BORDER_LINK_COLOR = "link-color";

    /** donut2D slice 'label border' link style element */
    public static String ELEMENT_DONUT2D_LABEL_BORDER_LINK_STYLE = "link-style";

    /** donut2D slice 'label border' link stroke element */
    public static String ELEMENT_DONUT2D_LABEL_BORDER_LINK_STROKE = "link-stroke";

    /** donut2D slice 'label border' marker enable element */
    public static String ELEMENT_DONUT2D_LABEL_BORDER_LINK_MARKER_ENABLE = "marker-enable";

    /** donut2D slice 'label border' marker draw element */
    public static String ELEMENT_DONUT2D_LABEL_BORDER_LINK_MARKER_DRAW = "marker-draw";

    /** donut2D slice 'label border' marker fill element */
    public static String ELEMENT_DONUT2D_LABEL_BORDER_LINK_MARKER_FILL = "marker-fill";

    /** donut2D effect root element */
    public static String ELEMENT_DONUT2D_EFFECT = "effect";
    
    
    /** donut2D effect linear */
    public static String ELEMENT_DONUT2D_EFFECT_TYPE_LINEAR = Donut2DLinearEffect.class.getSimpleName();
    
    /** donut2D effect compound */
    public static String ELEMENT_DONUT2D_EFFECT_TYPE_COMPOUND = Donut2DCompoundEffect.class.getSimpleName();
    
    /** donut2D effect reflection */
    public static String ELEMENT_DONUT2D_EFFECT_TYPE_REFLECTION = Donut2DReflectionEffect.class.getSimpleName();
    

    /** donut2D 'effect linear' incidence element */
    public static String ELEMENT_DONUT2D_EFFECT_LINEAR_INCIDENCE = "incidence-angle";

    /** donut2D 'effect linear' offset radius element */
    public static String ELEMENT_DONUT2D_EFFECT_LINEAR_OFFSET_RADIUS = "offset-radius";

    /** donut2D 'effect radial' focus angle element */
    public static String ELEMENT_DONUT2D_EFFECT_RADIAL_FOCUS_ANGLE = "focus-angle";

    /** donut2D 'effect radial' focus radius element */
    public static String ELEMENT_DONUT2D_EFFECT_RADIAL_FOCUS_RADIUS = "focus-radius";

    /** donut2D 'effect radial' offset radius element */
    public static String ELEMENT_DONUT2D_EFFECT_RADIAL_OFFSET_RADIUS = "offset-radius";

    /** donut2D 'effect reflection' blur enable element */
    public static String ELEMENT_DONUT2D_EFFECT_REFLECTION_BLUR_ENABLE = "blur-enable";

    /** donut2D 'effect reflection' mask opacity element */
    public static String ELEMENT_DONUT2D_EFFECT_REFLECTION_MASK_OPACITY = "mask-opacity";

    /** donut2D 'effect reflection' reflection length element */
    public static String ELEMENT_DONUT2D_EFFECT_REFLECTION_REFLECT_LENGTH = "reflect-length";

    /** donut2D fill root element */
    public static String ELEMENT_DONUT2D_FILL = "fill";
    
    /** donut2D fill default */
    public static String ELEMENT_DONUT2D_FILL_TYPE_DEFAULT = Donut2DDefaultFill.class.getSimpleName();
    
    /** donut2D fill radial */
    public static String ELEMENT_DONUT2D_FILL_TYPE_RADIAL = Donut2DRadialFill.class.getSimpleName();

    /** donut2D fill radial gradient type element */
    public static String ELEMENT_DONUT2D_FILL_RADIAL_GRADIENT_TYPE = "gradient-type";

}
