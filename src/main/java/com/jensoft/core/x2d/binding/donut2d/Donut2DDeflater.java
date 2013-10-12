/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.donut2d;

import java.util.List;

import org.w3c.dom.Element;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DPlugin;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;
import com.jensoft.core.plugin.donut2d.painter.effect.AbstractDonut2DEffect;
import com.jensoft.core.plugin.donut2d.painter.effect.Donut2DCompoundEffect;
import com.jensoft.core.plugin.donut2d.painter.effect.Donut2DLinearEffect;
import com.jensoft.core.plugin.donut2d.painter.effect.Donut2DReflectionEffect;
import com.jensoft.core.plugin.donut2d.painter.fill.AbstractDonut2DFill;
import com.jensoft.core.plugin.donut2d.painter.fill.Donut2DDefaultFill;
import com.jensoft.core.plugin.donut2d.painter.fill.Donut2DRadialFill;
import com.jensoft.core.plugin.donut2d.painter.label.AbstractDonut2DSliceLabel;
import com.jensoft.core.plugin.donut2d.painter.label.Donut2DBorderLabel;
import com.jensoft.core.plugin.donut2d.painter.label.Donut2DPathLabel;
import com.jensoft.core.plugin.donut2d.painter.label.Donut2DRadialLabel;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import com.jensoft.core.x2d.binding.DeflaterUtil;
import com.jensoft.core.x2d.binding.X2DBinding;

/**
 * <code>Donut2DDeflater</code>
 * 
 * @author sebastien janaud
 * 
 */
@X2DBinding(xsi = "Donut2DPlugin", plugin = Donut2DPlugin.class)
public class Donut2DDeflater extends AbstractX2DPluginDeflater<Donut2DPlugin> implements X2DDonut2DElement {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate(Donut2DPlugin plugin) {
		Element pluginElement = createPluginRootElement();
		List<Donut2D> donuts = plugin.getDonuts();
		for (Donut2D donut : donuts) {
			pluginElement.appendChild(deflateDonut2D(donut));
		}
		return pluginElement;
	}

	/**
	 * deflate the given donut 2D into XML element
	 * 
	 * @param donut
	 * @return donut element
	 */
	private Element deflateDonut2D(Donut2D donut) {
		Element donutElement = getX2dDocument().createElement(ELEMENT_DONUT2D);
		if (donut.getName() == null) {
			donut.setName("donut2d");
		}
		donutElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_NAME, donut.getName()));
		donutElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_X, donut.getCenterX()));
		donutElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_Y, donut.getCenterY()));
		donutElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_INNER_RADIUS, donut.getInnerRadius()));
		donutElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_OUTER_RADIUS, donut.getOuterRadius()));
		donutElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_START_ANGLE, donut.getStartAngleDegree()));
		donutElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_NATURE, donut.getNature().name()));

		if (donut.getDonut2DFill() != null) {
			donutElement.appendChild(deflateDonut2DFill(donut.getDonut2DFill()));
		}

		if (donut.getDonut2DEffect() != null) {
			donutElement.appendChild(deflateDonut2DEffect(donut.getDonut2DEffect()));
		}
		for (Donut2DSlice slice : donut.getSlices()) {
			donutElement.appendChild(deflateDonut2DSlice(slice));
		}
		return donutElement;
	}

	/**
	 * deflate donut slice label into XML element
	 * 
	 * @param label
	 * @return label element
	 */
	private Element deflateDonut2DSliceLabel(AbstractDonut2DSliceLabel label) {
		if (label.getClass().getName().equals(Donut2DRadialLabel.class.getName())) {
			return deflateDonut2DSliceRadialLabel((Donut2DRadialLabel) label);
		} else if (label.getClass().getName().equals(Donut2DBorderLabel.class.getName())) {
			return deflateDonut2DSliceBorderLabel((Donut2DBorderLabel) label);
		} else if (label.getClass().getName().equals(Donut2DPathLabel.class.getName())) {
			return deflateDonut2DSlicePathLabel((Donut2DPathLabel) label);
		}
		return null;
	}

	/**
	 * deflate path label
	 * 
	 * @param label
	 * @return path label element
	 */
	private Element deflateDonut2DSlicePathLabel(Donut2DPathLabel label) {
		Element labelElement = getX2dDocument().createElement(ELEMENT_DONUT2D_SLICE_LABEL);
		labelElement.setAttribute("xsi:type", ELEMENT_DONUT2D_LABEL_TYPE_PATH);
		deflateDonut2DSliceLabel(label, labelElement);

		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_PATH_TEXT_DIVERGENCE, label.getDivergence()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_PATH_TEXT_POSITION, label.getTextPosition().name()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_PATH_TEXT_SIDE, label.getPathSide().name()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_PATH_SEGMENT_PATH, label.getFacetPathName().name()));
		if (label.getTextShader() != null) {
			labelElement.appendChild(createShaderElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_PATH_TEXT_SHADER, label.getTextShader()));
		}

		return labelElement;
	}

	
	/**
	 * deflate border label
	 * 
	 * @param label
	 * @return border label element
	 */
	private Element deflateDonut2DSliceBorderLabel(Donut2DBorderLabel label) {
		Element labelElement = getX2dDocument().createElement(ELEMENT_DONUT2D_SLICE_LABEL);
		labelElement.setAttribute("xsi:type", ELEMENT_DONUT2D_LABEL_TYPE_BORDER);
		deflateDonut2DSliceLabel(label, labelElement);

		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_BORDER_MARGIN, label.getMargin()));

		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_BORDER_LINK_ENABLE, Boolean.toString(label.isLink())));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_BORDER_LINK_STYLE, label.getLinkStyle().name()));
		labelElement.appendChild(createColorElement(getX2dDocument(),  ELEMENT_DONUT2D_LABEL_BORDER_LINK_COLOR, label.getLinkColor()));
		labelElement.appendChild(createStrokeElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_BORDER_LINK_STROKE, label.getLinkStroke()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_BORDER_LINK_EXTENDS, label.getLinkExtends()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_BORDER_LINK_MARKER_ENABLE, Boolean.toString(label.isLinkMarker())));
		if (label.getLinkMarkerDrawColor() != null) {
			labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_BORDER_LINK_MARKER_DRAW, label.getLinkMarkerDrawColor()));
		}
		if (label.getLinkMarkerFillColor() != null) {
			labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_BORDER_LINK_MARKER_FILL, label.getLinkMarkerFillColor()));
		}

		return labelElement;
	}

	/**
	 * deflate radial label
	 * 
	 * @param label
	 * @return radial label element
	 */
	private Element deflateDonut2DSliceRadialLabel(Donut2DRadialLabel label) {
		Element labelElement = getX2dDocument().createElement(ELEMENT_DONUT2D_SLICE_LABEL);
		labelElement.setAttribute("xsi:type", ELEMENT_DONUT2D_LABEL_TYPE_RADIAL);
		deflateDonut2DSliceLabel(label, labelElement);
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_RADIAL_OFFSET_RADIUS, label.getOffsetRadius()));
		return labelElement;
	}

	/**
	 * deflate abstract label properties into label element
	 * 
	 * @param abstractLabel
	 *            label
	 * @param labelElement
	 *            the root label element
	 */
	private void deflateDonut2DSliceLabel(AbstractDonut2DSliceLabel abstractLabel, Element labelElement) {

		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_TEXT, abstractLabel.getLabel()));
		labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_TEXT_COLOR, abstractLabel.getLabelColor()));

		Element fontElement = createFontElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_TEXT_FONT, abstractLabel.getLabelFont());
		if (fontElement != null) {
			labelElement.appendChild(fontElement);
		}
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_PADDING_X, abstractLabel.getLabelPaddingX()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_PADDING_Y, abstractLabel.getLabelPaddingY()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_OUTLINE_ROUND, abstractLabel.getOutlineRound()));
		if (abstractLabel.getOutlineColor() != null) {
			labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_OUTLINE_COLOR, abstractLabel.getOutlineColor()));
		}

		if (abstractLabel.getOutlineStroke() != null) {
			labelElement.appendChild(createStrokeElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_OUTLINE_STROKE, abstractLabel.getOutlineStroke()));
		}
		if (abstractLabel.getFillColor() != null) {
			labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_FILL_COLOR, abstractLabel.getFillColor()));
		}
		if (abstractLabel.getShadeFractions() != null && abstractLabel.getShadeColors() != null) {
			labelElement.appendChild(createShaderElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_SHADER, new Shader(abstractLabel.getShadeFractions(), abstractLabel.getShadeColors())));
		}

		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_LABEL_STYLE, abstractLabel.getStyle().name()));

	}

	/**
	 * deflate the given slice into element
	 * 
	 * @param slice
	 *            the slice to deflate
	 * @return element
	 */
	private Element deflateDonut2DSlice(Donut2DSlice slice) {
		Element sliceElement = getX2dDocument().createElement(ELEMENT_DONUT2D_SLICE);
		if (slice.getName() != null) {
			sliceElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_SLICE_NAME, slice.getName()));
		}

		sliceElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_SLICE_VALUE, slice.getValue()));
		sliceElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_SLICE_DIVERGENCE, slice.getDivergence()));
		sliceElement.appendChild(DeflaterUtil.createColorElement(getX2dDocument(), ELEMENT_DONUT2D_SLICE_COLOR, slice.getThemeColor()));

		for (AbstractDonut2DSliceLabel label : slice.getSliceLabels()) {
			sliceElement.appendChild(deflateDonut2DSliceLabel(label));
		}

		return sliceElement;
	}

	/**
	 * deflate element
	 * 
	 * @param effect
	 *            the effect to deflate
	 * @return effect element
	 */
	private Element deflateDonut2DLinearEffect(Donut2DLinearEffect effect) {
		Element effectElement = getX2dDocument().createElement(ELEMENT_DONUT2D_EFFECT);
		effectElement.setAttribute("xsi:type", ELEMENT_DONUT2D_EFFECT_TYPE_LINEAR);

		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_EFFECT_LINEAR_INCIDENCE, effect.getIncidenceAngleDegree()));
		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_EFFECT_LINEAR_OFFSET_RADIUS, effect.getOffsetRadius()));

		return effectElement;
	}

	/**
	 * deflate element
	 * 
	 * @param effect
	 *            the effect to deflate
	 * @return effect element
	 */
	private Element deflateDonut2DReflectionEffect(Donut2DReflectionEffect effect) {
		Element effectElement = getX2dDocument().createElement(ELEMENT_DONUT2D_EFFECT);
		effectElement.setAttribute("xsi:type", ELEMENT_DONUT2D_EFFECT_TYPE_REFLECTION);

		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_EFFECT_REFLECTION_BLUR_ENABLE, Boolean.toString(effect.isBlurEnabled())));
		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_EFFECT_REFLECTION_MASK_OPACITY, effect.getOpacity()));
		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_EFFECT_REFLECTION_REFLECT_LENGTH, effect.getLength()));

		return effectElement;
	}

	/**
	 * deflate element
	 * 
	 * @param effect
	 *            the effect to deflate
	 * @return effect element
	 */
	private Element deflateDonut2DCompoundEffect(Donut2DCompoundEffect effect) {
		Element effectElement = getX2dDocument().createElement(ELEMENT_DONUT2D_EFFECT);
		effectElement.setAttribute("xsi:type", ELEMENT_DONUT2D_EFFECT_TYPE_COMPOUND);

		AbstractDonut2DEffect[] effects = effect.getEffects();
		for (int i = 0; i < effects.length; i++) {
			Element e = deflateDonut2DEffect(effects[i]);
			if (e != null) {
				effectElement.appendChild(e);
			}
		}
		return effectElement;
	}

	/**
	 * inflate pie fill {@link AbstractDonut2DFill}
	 * 
	 * @param fill
	 *            the pie fill to deflate
	 * @return effect
	 */
	private Element deflateDonut2DFill(AbstractDonut2DFill fill) {
		if (fill.getClass().getName().equals(Donut2DDefaultFill.class.getName())) {
			Element fillElement = getX2dDocument().createElement(ELEMENT_DONUT2D_FILL);
			fillElement.setAttribute("xsi:type", ELEMENT_DONUT2D_FILL_TYPE_DEFAULT);
			return fillElement;
		}
		if (fill.getClass().getName().equals(Donut2DRadialFill.class.getName())) {
			Element fillElement = getX2dDocument().createElement(ELEMENT_DONUT2D_FILL);
			fillElement.setAttribute("xsi:type", ELEMENT_DONUT2D_FILL_TYPE_RADIAL);
			Donut2DRadialFill radialFill = (Donut2DRadialFill)fill;			
			fillElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_DONUT2D_FILL_RADIAL_GRADIENT_TYPE, radialFill.getGradientBehavior().name()));
			return fillElement;
		}
		return null;
	}

	/**
	 * inflate donut 2D effect {@link AbstractDonut2DEffect}
	 * 
	 * @param effect
	 *            the donut 2D effect to deflate
	 * @return effect
	 */
	private Element deflateDonut2DEffect(AbstractDonut2DEffect effect) {

		if (effect.getClass().getName().equals(Donut2DCompoundEffect.class.getName())) {
			return deflateDonut2DCompoundEffect((Donut2DCompoundEffect) effect);
		}

		if (effect.getClass().getName().equals(Donut2DLinearEffect.class.getName())) {
			return deflateDonut2DLinearEffect((Donut2DLinearEffect) effect);
		}

		if (effect.getClass().getName().equals(Donut2DReflectionEffect.class.getName())) {
			return deflateDonut2DReflectionEffect((Donut2DReflectionEffect) effect);
		}

		return null;
	}

}
