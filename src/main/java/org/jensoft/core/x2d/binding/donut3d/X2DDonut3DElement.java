/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.donut3d;

import org.jensoft.core.plugin.donut3d.Donut3D;
import org.jensoft.core.plugin.donut3d.painter.paint.Donut3DDefaultPaint;

/**
 * <code>X2DDonut3DElement</code> defines {@link Donut3D} related elements names
 * that should be used in any XML x2d view document and donut3D particular
 * purpose.
 * 
 * @author Sebastien Janaud
 */
public interface X2DDonut3DElement {

	/** donut3D root element */
	public static String ELEMENT_DONUT3D = "donut3d";

	/** donut3D name element */
	public static String ELEMENT_DONUT3D_NAME = "name";

	/** donut3D center x coordinate element */
	public static String ELEMENT_DONUT3D_X = "x";

	/** donut3D center y coordinate element */
	public static String ELEMENT_DONUT3D_Y = "y";

	/** donut3D inner radius element */
	public static String ELEMENT_DONUT3D_INNER_RADIUS = "inner-radius";

	/** donut3D outer radius element */
	public static String ELEMENT_DONUT3D_OUTER_RADIUS = "outer-radius";

	/** donut3D thickness element */
	public static String ELEMENT_DONUT3D_THICKNESS = "thickness";

	/** donut3D tilt element */
	public static String ELEMENT_DONUT3D_TILT = "tilt";

	/** donut3D start element */
	public static String ELEMENT_DONUT3D_START_ANGLE = "start-angle";

	/** donut3D projection nature element */
	public static String ELEMENT_DONUT3D_NATURE = "nature";

	/** donut3D slice root element */
	public static String ELEMENT_DONUT3D_SLICE = "slice";

	/** donut3D slice name element */
	public static String ELEMENT_DONUT3D_SLICE_NAME = "name";

	/** donut3D slice value element */
	public static String ELEMENT_DONUT3D_SLICE_VALUE = "value";

	/** donut3D slice divergence element */
	public static String ELEMENT_DONUT3D_SLICE_DIVERGENCE = "divergence";

	/** donut3D slice color element */
	public static String ELEMENT_DONUT3D_SLICE_COLOR = "slice-color";

	/** donut3D slice label root element */
	public static String ELEMENT_DONUT3D_SLICE_LABEL = "label";

	/** donut3D slice radial label*/
	public static String ELEMENT_DONUT3D_SLICE_LABEL_TYPE_RADIAL = "Donut3DRadialLabel";
	
	/** donut3D slice border label*/
	public static String ELEMENT_DONUT3D_SLICE_LABEL_TYPE_BORDER = "Donut3DBorderLabel";
	
	/** donut3D slice path label*/
	public static String ELEMENT_DONUT3D_SLICE_LABEL_TYPE_PATH = "Donut3DPathLabel";

	/** donut3D slice label element */
	public static String ELEMENT_DONUT3D_LABEL_TEXT = "text";

	/** donut3D slice label text color element */
	public static String ELEMENT_DONUT3D_LABEL_TEXT_COLOR = "textColor";

	/** donut3D slice label text font element */
	public static String ELEMENT_DONUT3D_LABEL_TEXT_FONT = "font";

	/** donut3D slice label text padding x element */
	public static String ELEMENT_DONUT3D_LABEL_TEXT_PADDING_X = "text-padding-x";

	/** donut3D slice label text padding y element */
	public static String ELEMENT_DONUT3D_LABEL_TEXT_PADDING_Y = "text-padding-y";

	/** donut3D slice label outline round element */
	public static String ELEMENT_DONUT3D_LABEL_OUTLINE_ROUND = "outline-round";

	/** donut3D slice label outline color element */
	public static String ELEMENT_DONUT3D_LABEL_OUTLINE_COLOR = "outline-color";

	/** donut3D slice label outline stroke element */
	public static String ELEMENT_DONUT3D_LABEL_OUTLINE_STROKE = "outline-stroke";

	/** donut3D slice label fill color element */
	public static String ELEMENT_DONUT3D_LABEL_FILL_COLOR = "fill-color";

	/** donut3D slice label shader element */
	public static String ELEMENT_DONUT3D_LABEL_SHADER = "shader";

	/** donut3D slice label style element */
	public static String ELEMENT_DONUT3D_LABEL_STYLE = "style";

	/** donut3D slice 'label path' text divergence element */
	public static String ELEMENT_DONUT3D_LABEL_PATH_TEXT_DIVERGENCE = "text-divergence";

	/** donut3D slice 'label path' text position element */
	public static String ELEMENT_DONUT3D_LABEL_PATH_TEXT_POSITION = "text-position";

	/** donut3D slice 'label path' text side element */
	public static String ELEMENT_DONUT3D_LABEL_PATH_TEXT_SIDE = "text-side";

	/** donut3D slice 'label path' segment path element */
	public static String ELEMENT_DONUT3D_LABEL_PATH_SEGMENT_PATH = "segment-path";

	/** donut3D slice 'label path' text shader element */
	public static String ELEMENT_DONUT3D_LABEL_PATH_TEXT_SHADER = "text-shader";

	/** donut3D slice 'label radial' offset radius element */
	public static String ELEMENT_DONUT3D_LABEL_RADIAL_OFFSET_RADIUS = "offsetRadius";

	/** donut3D slice 'label border' link extends element */
	public static String ELEMENT_DONUT3D_LABEL_BORDER_LINK_EXTENDS = "link-extends";

	/** donut3D slice 'label border' link color element */
	public static String ELEMENT_DONUT3D_LABEL_BORDER_LINK_COLOR = "link-color";

	/** donut3D slice 'label border' label margin element */
	public static String ELEMENT_DONUT3D_LABEL_BORDER_LABEL_MARGIN = "label-margin";
	

	/** donut3D slice 'label border' link enable element */
	public static String ELEMENT_DONUT3D_LABEL_BORDER_LINK_ENABLE = "link-enable";

	/** donut3D slice 'label border' link style element */
	public static String ELEMENT_DONUT3D_LABEL_BORDER_LINK_STYLE = "link-style";

	/** donut3D slice 'label border' link alignment element */
	public static String ELEMENT_DONUT3D_LABEL_BORDER_LINK_ALIGNMENT = "link-alignment";

	/** donut3D slice 'label border' link stroke element */
	public static String ELEMENT_DONUT3D_LABEL_BORDER_LINK_STROKE = "link-stroke";

	/** donut3D slice 'label border' link marker enable element */
	public static String ELEMENT_DONUT3D_LABEL_BORDER_LINK_MARKER_ENABLE = "marker-enable";

	/** donut3D slice 'label border' link marker draw element */
	public static String ELEMENT_DONUT3D_LABEL_BORDER_LINK_MARKER_DRAW = "marker-draw";

	/** donut3D slice 'label border' link marker fill element */
	public static String ELEMENT_DONUT3D_LABEL_BORDER_LINK_MARKER_FILL = "marker-fill";

	/** donut3D paint root element */
	public static String ELEMENT_DONUT3D_PAINT = "paint";
	
	/** donut3D default paint type */
	public static String ELEMENT_DONUT3D_PAINT_TYPE_DEFAULT = Donut3DDefaultPaint.class.getSimpleName();

	/** donut3D paint incidence effect angle element */
	public static String ELEMENT_DONUT3D_DEFAULTPAINT_INCIDENCE_EFFECT_ANGLE = "incidence-effect-angle";

	/** donut3D paint top effect enable element */
	public static String ELEMENT_DONUT3D_DEFAULTPAINT_TOP_EFFECT_ENABLE = "top-effect-enable";

	/** donut3D paint inner effect enable element */
	public static String ELEMENT_DONUT3D_DEFAULTPAINT_INNER_EFFECT_ENABLE = "inner-effect-enable";

	/** donut3D paint outer effect enable element */
	public static String ELEMENT_DONUT3D_DEFAULTPAINT_OUTER_EFFECT_ENABLE = "outer-effect-enable";

	/** donut3D paint slice fill alpha element */
	public static String ELEMENT_DONUT3D_DEFAULTPAINT_SLICE_FILL_ALPHA = "slice-fill-alpha";

	/** donut3D paint top effect alpha element */
	public static String ELEMENT_DONUT3D_DEFAULTPAINT_TOP_EFFECT_ALPHA = "top-effect-alpha";

	/** donut3D paint inner effect alpha element */
	public static String ELEMENT_DONUT3D_DEFAULTPAINT_INNER_EFFECT_ALPHA = "inner-effect-alpha";

	/** donut3D paint outer effect alpha element */
	public static String ELEMENT_DONUT3D_DEFAULTPAINT_OUTER_EFFECT_ALPHA = "outer-effect-alpha";

}
