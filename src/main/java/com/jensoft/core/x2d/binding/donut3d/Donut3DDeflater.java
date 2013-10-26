/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.donut3d;

import java.util.List;

import org.w3c.dom.Element;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.donut3d.Donut3D;
import com.jensoft.core.plugin.donut3d.Donut3DPlugin;
import com.jensoft.core.plugin.donut3d.Donut3DSlice;
import com.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel;
import com.jensoft.core.plugin.donut3d.painter.label.Donut3DBorderLabel;
import com.jensoft.core.plugin.donut3d.painter.label.Donut3DPathLabel;
import com.jensoft.core.plugin.donut3d.painter.label.Donut3DRadialLabel;
import com.jensoft.core.plugin.donut3d.painter.paint.Donut3DDefaultPaint;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import com.jensoft.core.x2d.binding.X2DBinding;

/**
 * <code>Donut3DDeflater</code>
 * 
 * @author sebastien janaud
 * 
 */
@X2DBinding(xsi = "Donut3DPlugin", plugin = Donut3DPlugin.class)
public class Donut3DDeflater extends AbstractX2DPluginDeflater<Donut3DPlugin> implements X2DDonut3DElement {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate(Donut3DPlugin plugin) {
		Element pluginElement = createPluginRootElement();
		List<Donut3D> donuts = plugin.getDonuts3D();
		for (Donut3D donut : donuts) {
			pluginElement.appendChild(deflateDonut3D(donut));
		}
		return pluginElement;
	}

	/**
	 * deflate the given donut into XML element
	 * 
	 * @param donut
	 * @return donut element
	 */
	private Element deflateDonut3D(Donut3D donut) {
		Element donut3DElement = getX2dDocument().createElement(ELEMENT_DONUT3D);
		if (donut.getName() == null) {
			donut.setName("donut3d");
		}
		donut3DElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_NAME, donut.getName()));
		donut3DElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_X, donut.getCenterX()));
		donut3DElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_Y, donut.getCenterY()));
		donut3DElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_INNER_RADIUS, donut.getInnerRadius()));
		donut3DElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_OUTER_RADIUS, donut.getOuterRadius()));
		donut3DElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_THICKNESS, donut.getThickness()));
		donut3DElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_TILT, donut.getTilt()));
		donut3DElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_START_ANGLE, donut.getStartAngleDegree()));
		donut3DElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_NATURE, donut.getDonut3DNature().name()));

		List<Donut3DSlice> slices = donut.getSlices();
		for (Donut3DSlice donut3dSlice : slices) {
			donut3DElement.appendChild(deflateDonut3DSlice(donut3dSlice));
		}

		donut3DElement.appendChild(deflateDonut3DPaint(donut));

		return donut3DElement;
	}

	/**
	 * deflate donut3D paint into XML element
	 * 
	 * @param donut
	 * @return paint element
	 */
	private Element deflateDonut3DPaint(Donut3D donut) {
		if (donut.getDonut3DPaint().getClass().getName().equals(Donut3DDefaultPaint.class.getName())) {
			Element paintElement = getX2dDocument().createElement(ELEMENT_DONUT3D_PAINT);
			paintElement.setAttribute("xsi:type", ELEMENT_DONUT3D_PAINT_TYPE_DEFAULT);
			
			
			Donut3DDefaultPaint defaultPaint = (Donut3DDefaultPaint)donut.getDonut3DPaint();
			
			paintElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_DEFAULTPAINT_INCIDENCE_EFFECT_ANGLE, defaultPaint.getIncidenceAngleDegree()));
			
			paintElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_DEFAULTPAINT_SLICE_FILL_ALPHA, defaultPaint.getAlphaFill()));
			
			paintElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_DEFAULTPAINT_INNER_EFFECT_ALPHA, defaultPaint.getAlphaInner()));
			paintElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_DEFAULTPAINT_INNER_EFFECT_ENABLE, Boolean.toString(defaultPaint.isPaintInnerEffect())));
			
			paintElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_DEFAULTPAINT_OUTER_EFFECT_ALPHA, defaultPaint.getAlphaOuter()));
			paintElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_DEFAULTPAINT_OUTER_EFFECT_ENABLE, Boolean.toString(defaultPaint.isPaintOuterEffect())));
			
			paintElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_DEFAULTPAINT_TOP_EFFECT_ALPHA, defaultPaint.getAlphaTop()));
			paintElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_DEFAULTPAINT_TOP_EFFECT_ENABLE, Boolean.toString(defaultPaint.isPaintTopEffect())));
			
			return paintElement;
		}

		return null;
	}

	/**
	 * deflate given slice into XML element
	 * 
	 * @param slice
	 *            slice to deflate
	 * @return slice element
	 */
	private Element deflateDonut3DSlice(Donut3DSlice slice) {
		Element sliceElement = getX2dDocument().createElement(ELEMENT_DONUT3D_SLICE);
		if (slice.getName() != null) {
			slice.setName("slice");
		}
		sliceElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_SLICE_NAME, slice.getName()));
		sliceElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_SLICE_VALUE, slice.getValue()));
		sliceElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_SLICE_DIVERGENCE, slice.getDivergence()));
		sliceElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_DONUT3D_SLICE_COLOR, slice.getThemeColor()));

		for (AbstractDonut3DSliceLabel label : slice.getSliceLabels()) {
			sliceElement.appendChild(deflateDonut3DSliceLabel(label));
		}

		return sliceElement;
	}

	/**
	 * deflate the given donut label into XML element
	 * 
	 * @param label
	 *            the label to deflate
	 * @return slice label
	 */
	private Element deflateDonut3DSliceLabel(AbstractDonut3DSliceLabel label) {
		if (label.getClass().getName().equals(Donut3DRadialLabel.class.getName())) {
			return deflateDonut3DSliceRadialLabel((Donut3DRadialLabel) label);
		} else if (label.getClass().getName().equals(Donut3DBorderLabel.class.getName())) {
			return deflateDonut3DSliceBorderLabel((Donut3DBorderLabel) label);
		} else if (label.getClass().getName().equals(Donut3DPathLabel.class.getName())) {
			return deflateDonut3DSlicePathLabel((Donut3DPathLabel) label);
		}
		return null;
	}

	/**
	 * deflate abstract label properties into label element
	 * 
	 * @param abstractLabel
	 *            label
	 * @param labelElement
	 *            the root label element
	 */
	private void deflateDonut3DSliceLabel(AbstractDonut3DSliceLabel abstractLabel, Element labelElement) {

		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_TEXT, abstractLabel.getLabel()));
		labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_TEXT_COLOR, abstractLabel.getLabelColor()));

		Element fontElement = createFontElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_TEXT_FONT, abstractLabel.getLabelFont());
		if (fontElement != null) {
			labelElement.appendChild(fontElement);
		}
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_TEXT_PADDING_X, abstractLabel.getLabelPaddingX()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_TEXT_PADDING_Y, abstractLabel.getLabelPaddingY()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_OUTLINE_ROUND, abstractLabel.getOutlineRound()));
		if (abstractLabel.getOutlineColor() != null) {
			labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_OUTLINE_COLOR, abstractLabel.getOutlineColor()));
		}

		if (abstractLabel.getOutlineStroke() != null) {
			labelElement.appendChild(createStrokeElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_OUTLINE_STROKE, abstractLabel.getOutlineStroke()));
		}
		if (abstractLabel.getFillColor() != null) {
			labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_FILL_COLOR, abstractLabel.getFillColor()));
		}
		if (abstractLabel.getShadeFractions() != null && abstractLabel.getShadeColors() != null) {
			labelElement.appendChild(createShaderElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_SHADER, new Shader(abstractLabel.getShadeFractions(), abstractLabel.getShadeColors())));
		}

		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_STYLE, abstractLabel.getStyle().name()));

	}

	/**
	 * deflate the given path label into XML Element
	 * 
	 * @param label
	 *            label
	 * @return label element
	 */
	private Element deflateDonut3DSlicePathLabel(Donut3DPathLabel label) {
		Element labelElement = getX2dDocument().createElement(ELEMENT_DONUT3D_SLICE_LABEL);
		labelElement.setAttribute("xsi:type", ELEMENT_DONUT3D_SLICE_LABEL_TYPE_PATH);
		deflateDonut3DSliceLabel(label, labelElement);

		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_PATH_TEXT_DIVERGENCE, label.getDivergence()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_PATH_TEXT_POSITION, label.getTextPosition().name()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_PATH_TEXT_SIDE, label.getPathSide().name()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_PATH_SEGMENT_PATH, label.getFacetPathName().name()));
		if (label.getTextShader() != null) {
			labelElement.appendChild(createShaderElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_PATH_TEXT_SHADER, label.getTextShader()));
		}

		return labelElement;
	}

	/**
	 * deflate donut 3D border label
	 * 
	 * @param label
	 * @return border label element
	 */
	private Element deflateDonut3DSliceBorderLabel(Donut3DBorderLabel label) {
		Element labelElement = getX2dDocument().createElement(ELEMENT_DONUT3D_SLICE_LABEL);
		labelElement.setAttribute("xsi:type", ELEMENT_DONUT3D_SLICE_LABEL_TYPE_BORDER);
		deflateDonut3DSliceLabel(label, labelElement);

		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_BORDER_LABEL_MARGIN, label.getMargin()));

		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_BORDER_LINK_ENABLE, Boolean.toString(label.isLink())));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_BORDER_LINK_STYLE, label.getLinkStyle().name()));
		labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_BORDER_LINK_COLOR, label.getLinkColor()));
		labelElement.appendChild(createStrokeElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_BORDER_LINK_STROKE, label.getLinkStroke()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_BORDER_LINK_EXTENDS, label.getLinkExtends()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_BORDER_LINK_MARKER_ENABLE, Boolean.toString(label.isLinkMarker())));
		if (label.getLinkMarkerDrawColor() != null) {
			labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_BORDER_LINK_MARKER_DRAW, label.getLinkMarkerDrawColor()));
		}
		if (label.getLinkMarkerFillColor() != null) {
			labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_BORDER_LINK_MARKER_FILL, label.getLinkMarkerFillColor()));
		}

		return labelElement;
	}

	/**
	 * deflate donut 3D radial label
	 * 
	 * @param label
	 * @return radial label element
	 */
	private Element deflateDonut3DSliceRadialLabel(Donut3DRadialLabel label) {
		Element labelElement = getX2dDocument().createElement(ELEMENT_DONUT3D_SLICE_LABEL);
		labelElement.setAttribute("xsi:type", ELEMENT_DONUT3D_SLICE_LABEL_TYPE_RADIAL);
		deflateDonut3DSliceLabel(label, labelElement);
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT3D_LABEL_RADIAL_OFFSET_RADIUS, label.getOffsetRadius()));
		return labelElement;
	}

}
