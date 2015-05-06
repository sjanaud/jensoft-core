/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.pie;

import org.jensoft.core.plugin.pie.Pie;

/**
 * <code>X2DPieElement</code> defines {@link Pie} related elements names that
 * should be used in any XML x2d view document and pie particular purpose.
 * 
 * 
 * @author Sebastien Janaud
 */
public interface X2DPieElement {

	/** pie element root */
	public final static String ELEMENT_PIE = "pie";

	/** pie name element */
	public final static String ELEMENT_PIE_NAME = "name";

	/** pie center x coordinate element */
	public final static String ELEMENT_PIE_X = "x";

	/** pie center y coordinate element */
	public final static String ELEMENT_PIE_Y = "y";

	/** pie radius element */
	public final static String ELEMENT_PIE_RADIUS = "radius";

	/** pie start angle element */
	public final static String ELEMENT_PIE_START_ANGLE_DEGREE = "start-angle";

	/** projection nature element */
	public final static String ELEMENT_PIE_NATURE = "nature";

	/** pie slice root element */
	public final static String ELEMENT_PIE_SLICE = "slice";

	/** pie slice name element */
	public final static String ELEMENT_PIE_SLICE_NAME = "name";

	/** pie slice value element */
	public final static String ELEMENT_PIE_SLICE_VALUE = "value";

	/** pie slice divergence element */
	public final static String ELEMENT_PIE_SLICE_DIVERGENCE = "divergence";

	/** pie slice color element */
	public final static String ELEMENT_PIE_SLICE_COLOR = "slice-color";

	/** pie slice root label element */
	public final static String ELEMENT_PIE_SLICE_LABEL = "label";

	/** pie slice radial label element */
	public final static String ELEMENT_PIE_SLICE_LABEL_TYPE_RADIAL = "PieRadialLabel";

	/** pie slice border label element */
	public final static String ELEMENT_PIE_SLICE_LABEL_TYPE_BORDER = "PieBorderLabel";

	/** pie slice bound label element */
	public final static String ELEMENT_PIE_SLICE_LABEL_TYPE_BOUND = "PieBoundLabel";

	/** pie slice path label element */
	public final static String ELEMENT_PIE_SLICE_LABEL_TYPE_PATH = "PiePathLabel";

	/** pie slice abstract label text */
	public final static String ELEMENT_PIE_LABEL_TEXT = "text";

	/** pie slice abstract label text color element */
	public final static String ELEMENT_PIE_LABEL_TEXT_COLOR = "text-color";

	/** pie slice abstract label text font element */
	public final static String ELEMENT_PIE_LABEL_TEXT_FONT = "font";

	/** pie slice abstract label text padding x element */
	public final static String ELEMENT_PIE_LABEL_TEXT_PADDING_X = "text-padding-x";

	/** pie slice abstract label text padding y element */
	public final static String ELEMENT_PIE_LABEL_TEXT_PADDING_Y = "text-padding-y";

	/** pie slice abstract label outline color element */
	public final static String ELEMENT_PIE_LABEL_OUTLINE_ROUND = "outline-round";

	/** pie slice abstract outline color element */
	public final static String ELEMENT_PIE_LABEL_OUTLINE_COLOR = "outline-color";

	/** pie slice abstract outline stroke element */
	public final static String ELEMENT_PIE_LABEL_OUTLINE_STROKE = "outline-stroke";

	/** pie slice abstract fill color element */
	public final static String ELEMENT_PIE_LABEL_FILL_COLOR = "fill-color";

	/** pie slice abstract label shader element */
	public final static String ELEMENT_PIE_LABEL_SHADER = "shader";

	/** pie slice abstract label style element */
	public final static String ELEMENT_PIE_LABEL_STYLE = "style";

	/** pie 'slice path label' text divergence element */
	public final static String ELEMENT_PIE_LABEL_PATH_TEXT_DIVERGENCE = "text-divergence";

	/** pie 'slice path label' text position element */
	public final static String ELEMENT_PIE_LABEL_PATH_TEXT_POSITION = "text-position";

	/** pie 'slice path label' text side element */
	public final static String ELEMENT_PIE_LABEL_PATH_TEXT_SIDE = "text-side";

	/** pie 'slice path label' segment name element */
	public final static String ELEMENT_PIE_LABEL_PATH_SEGMENT_PATH = "segment-path";

	/** pie 'slice path label' text shader element */
	public final static String ELEMENT_PIE_LABEL_PATH_TEXT_SHADER = "text-shader";

	/** pie 'slice radial label' text divergence element */
	public final static String ELEMENT_PIE_LABEL_RADIAL_OFFSET_RADIUS = "offset-radius";

	/** pie 'slice border label' link extends element */
	public final static String ELEMENT_PIE_LABEL_BORDER_LINK_EXTENDS = "link-extends";

	/** pie 'slice border label' link color element */
	public final static String ELEMENT_PIE_LABEL_BORDER_LINK_COLOR = "link-color";

	/** pie 'slice border label' margin element */
	public final static String ELEMENT_PIE_LABEL_BORDER_MARGIN = "label-margin";

	/** pie 'slice border label' link enable element */
	public final static String ELEMENT_PIE_LABEL_BORDER_LINK = "link-enable";

	/** pie 'slice border label' link style element */
	public final static String ELEMENT_PIE_LABEL_BORDER_LINK_STYLE = "link-style";

	/** pie 'slice border label' link stroke element */
	public final static String ELEMENT_PIE_LABEL_BORDER_LINK_STROKE = "link-stroke";

	/** pie 'slice border label' link marker enable element */
	public final static String ELEMENT_PIE_LABEL_BORDER_LINK_MARKER = "marker-enable";

	/** pie 'slice border label' link marker draw element */
	public final static String ELEMENT_PIE_LABEL_BORDER_LINK_MARKERDRAW = "marker-draw";

	/** pie 'slice border label' link marker fill element */
	public final static String ELEMENT_PIE_LABEL_BORDER_LINK_MARKERFILL = "marker-fill";
	
	
	/** pie fill root element */
	public final static String ELEMENT_PIE_FILL = "fill";
	
	/** pie linear fill type */
	public final static String ELEMENT_PIE_FILL_TYPE_LINEAR = "PieLinearFill";
	
	/** pie radial fill element */
	public final static String ELEMENT_PIE_FILL_TYPE_RADIAL = "PieRadialFill";

	/** pie effect root element */
	public final static String ELEMENT_PIE_EFFECT = "effect";

	/** pie effect linear element */
	public final static String ELEMENT_PIE_EFFECT_TYPE_LINEAR = "PieLinearEffect";
	
	/** pie effect cubic element */
	public final static String ELEMENT_PIE_EFFECT_TYPE_CUBIC = "PieCubicEffect";

	/** pie effect linear element */
	public final static String ELEMENT_PIE_EFFECT_TYPE_RADIAL = "PieRadialEffect";

	/** pie effect linear element */
	public final static String ELEMENT_PIE_EFFECT_TYPE_REFLECTION = "PieReflectionEffect";

	/** pie effect linear element */
	public final static String ELEMENT_PIE_EFFECT_TYPE_COMPOUND = "PieCompoundEffect";

	/** pie 'linear effect' incidence angle element */
	public final static String ELEMENT_PIE_EFFECT_LINEAR_INCIDENCE = "incidence-angle";

	/** pie 'linear effect' offset radius element */
	public final static String ELEMENT_PIE_EFFECT_LINEAR_OFFSET_RADIUS = "offset-radius";

	/** pie 'radial effect' focus angle element */
	public final static String ELEMENT_PIE_EFFECT_RADIAL_FOCUS_ANGLE = "focus-angle";

	/** pie 'radial effect' focus radius element */
	public final static String ELEMENT_PIE_EFFECT_RADIAL_FOCUS_RADIUS = "focus-radius";

	/** pie 'radial effect' offset radius element */
	public final static String ELEMENT_PIE_EFFECT_RADIAL_OFFSET_RADIUS = "offset-radius";

	/** pie 'reflection effect' blur enable element */
	public final static String ELEMENT_PIE_EFFECT_REFLECTION_BLUR = "blur-enable";

	/** pie 'reflection effect' mask opacity element */
	public final static String ELEMENT_PIE_EFFECT_REFLECTION_OPACITY = "mask-opacity";

	/** pie 'reflection effect' reflection length element */
	public final static String ELEMENT_PIE_EFFECT_REFLECTION_LENGTH = "reflection-length";
	
	/** pie 'cubic effect' incidence angle element */
	public final static String ELEMENT_PIE_EFFECT_CUBIC_INCIDENCE = "incidence-angle";

	/** pie 'cubic effect' offset radius element */
	public final static String ELEMENT_PIE_EFFECT_CUBIC_OFFSET_RADIUS = "offset-radius";
	
	/** pie 'cubic effect' cubic key element */
	public final static String ELEMENT_PIE_EFFECT_CUBIC_KEY = "cubic-key";
	
	/** pie 'cubic effect' cubic key delta angle start element */
	public final static String ELEMENT_PIE_EFFECT_CUBIC_KEY_ANGLE_DELTA_START = "start-angle-delta";
	
	/** pie 'cubic effect' cubic key delta angle end element */
	public final static String ELEMENT_PIE_EFFECT_CUBIC_KEY_ANGLE_DELTA_END = "end-angle-delta";
	
	/** pie 'cubic effect' cubic key delta angle start element */
	public final static String ELEMENT_PIE_EFFECT_CUBIC_KEY_RADIUS_FRACTION_START = "start-radius-fraction";
	
	/** pie 'cubic effect' cubic key delta angle end element */
	public final static String ELEMENT_PIE_EFFECT_CUBIC_KEY_RADIUS_FRACTION_END = "end-radius-fraction";
	
	
	/** pie 'cubic effect' cubic frame element */
	public final static String ELEMENT_PIE_EFFECT_CUBIC_FRAME = "cubic-frame";
	
	

}
