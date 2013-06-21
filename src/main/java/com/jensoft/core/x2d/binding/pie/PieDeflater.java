/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.pie;

import java.util.List;

import org.w3c.dom.Element;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.painter.effect.AbstractPieEffect;
import com.jensoft.core.plugin.pie.painter.effect.PieCompoundEffect;
import com.jensoft.core.plugin.pie.painter.effect.PieCubicEffect;
import com.jensoft.core.plugin.pie.painter.effect.PieLinearEffect;
import com.jensoft.core.plugin.pie.painter.effect.PieRadialEffect;
import com.jensoft.core.plugin.pie.painter.effect.PieReflectionEffect;
import com.jensoft.core.plugin.pie.painter.label.AbstractPieSliceLabel;
import com.jensoft.core.plugin.pie.painter.label.PieBorderLabel;
import com.jensoft.core.plugin.pie.painter.label.PieBoundLabel;
import com.jensoft.core.plugin.pie.painter.label.PiePathLabel;
import com.jensoft.core.plugin.pie.painter.label.PieRadialLabel;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import com.jensoft.core.x2d.binding.DeflaterUtil;
import com.jensoft.core.x2d.binding.X2DBinding;

/**
 * <code>PieDeflater</code>
 * 
 * @author sebastien janaud
 * 
 */
@X2DBinding(xsi="PiePlugin",plugin=PiePlugin.class)
public class PieDeflater extends AbstractX2DPluginDeflater<PiePlugin> implements X2DPieElement {

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate(com.jensoft.core.plugin.AbstractPlugin)
	 */
	@Override
	public Element deflate(PiePlugin plugin) {	
		Element pluginElement = createPluginRootElement();
		List<Pie> pies = plugin.getPies();
		for (Pie pie : pies) {
			pluginElement.appendChild(deflatePie(pie));
		}
		return pluginElement;
	}

	private Element deflatePie(Pie pie) {
		Element pieElement = getX2dDocument().createElement(ELEMENT_PIE);
		if (pie.getName() != null) {
			pieElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_NAME, pie.getName()));
		}
		pieElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_X, pie.getCenterX()));
		pieElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_Y, pie.getCenterY()));
		pieElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_RADIUS, pie.getRadius()));
		pieElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_START_ANGLE_DEGREE, pie.getStartAngleDegree()));
		pieElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_NATURE, pie.getPieNature().name()));
		if (pie.getPieEffect() != null) {
			pieElement.appendChild(deflatePieEffect(pie.getPieEffect()));
		}
		for (PieSlice slice : pie.getSlices()) {
			pieElement.appendChild(deflatePieSlice(slice));
		}
		return pieElement;
	}

	private Element deflatePieSliceLabel(AbstractPieSliceLabel label) {
		if (label.getClass().getName().equals(PieRadialLabel.class.getName())) {
			return deflatePieSliceRadialLabel((PieRadialLabel) label);
		} else if (label.getClass().getName().equals(PieBorderLabel.class.getName())) {
			return deflatePieSliceBorderLabel((PieBorderLabel) label);
		} else if (label.getClass().getName().equals(PieBoundLabel.class.getName())) {
			return deflatePieSliceBoundLabel((PieBoundLabel) label);
		} else if (label.getClass().getName().equals(PiePathLabel.class.getName())) {
			return deflatePieSlicePathLabel((PiePathLabel) label);
		}
		return null;
	}

	/**
	 * deflate path label
	 * 
	 * @param label
	 * @return path label element
	 */
	private Element deflatePieSlicePathLabel(PiePathLabel label) {
		Element labelElement = getX2dDocument().createElement(ELEMENT_PIE_SLICE_LABEL);
		labelElement.setAttribute("xsi:type", ELEMENT_PIE_SLICE_LABEL_TYPE_PATH);
		deflatePieSliceLabel(label, labelElement);

		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_PATH_TEXT_DIVERGENCE, label.getDivergence()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_PATH_TEXT_POSITION, label.getTextPosition().name()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_PATH_TEXT_SIDE, label.getPathSide().name()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_PATH_SEGMENT_PATH, label.getPathName().name()));
		if(label.getShader() != null){
			labelElement.appendChild(createShaderElement(getX2dDocument(), ELEMENT_PIE_LABEL_PATH_TEXT_SHADER, label.getShader()));
		}

		return labelElement;
	}

	/**
	 * deflate bound label
	 * 
	 * @param label
	 * @return bound label element
	 */
	private Element deflatePieSliceBoundLabel(AbstractPieSliceLabel label) {
		Element labelElement = getX2dDocument().createElement(ELEMENT_PIE_SLICE_LABEL);
		labelElement.setAttribute("xsi:type", ELEMENT_PIE_SLICE_LABEL_TYPE_BOUND);
		deflatePieSliceLabel(label, labelElement);
		return labelElement;
	}

	/**
	 * deflate border label
	 * 
	 * @param label
	 * @return border label element
	 */
	private Element deflatePieSliceBorderLabel(PieBorderLabel label) {
		Element labelElement = getX2dDocument().createElement(ELEMENT_PIE_SLICE_LABEL);
		labelElement.setAttribute("xsi:type", ELEMENT_PIE_SLICE_LABEL_TYPE_BORDER);
		deflatePieSliceLabel(label, labelElement);

		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_BORDER_MARGIN, label.getMargin()));
		
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_BORDER_LINK, Boolean.toString(label.isLink())));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_BORDER_LINK_STYLE, label.getLinkStyle().name()));
		labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_PIE_LABEL_BORDER_LINK_COLOR, label.getLinkColor()));
		labelElement.appendChild(createStrokeElement(getX2dDocument(), ELEMENT_PIE_LABEL_BORDER_LINK_STROKE, label.getLinkStroke()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_BORDER_LINK_EXTENDS, label.getLinkExtends()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_BORDER_LINK_MARKER, Boolean.toString(label.isLinkMarker())));
		if (label.getLinkMarkerDrawColor() != null) {
			labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_PIE_LABEL_BORDER_LINK_MARKERDRAW, label.getLinkMarkerDrawColor()));
		}
		if (label.getLinkMarkerFillColor() != null) {
			labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_PIE_LABEL_BORDER_LINK_MARKERFILL, label.getLinkMarkerFillColor()));
		}
		

		return labelElement;
	}

	/**
	 * deflate radial label
	 * 
	 * @param label
	 * @return radial label element
	 */
	private Element deflatePieSliceRadialLabel(PieRadialLabel label) {
		Element labelElement = getX2dDocument().createElement(ELEMENT_PIE_SLICE_LABEL);
		labelElement.setAttribute("xsi:type", ELEMENT_PIE_SLICE_LABEL_TYPE_RADIAL);
		deflatePieSliceLabel(label, labelElement);
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_RADIAL_OFFSET_RADIUS, label.getOffsetRadius()));
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
	private void deflatePieSliceLabel(AbstractPieSliceLabel abstractLabel, Element labelElement) {

		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_TEXT, abstractLabel.getLabel()));
		labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_PIE_LABEL_TEXT_COLOR, abstractLabel.getLabelColor()));
		
		Element fontElement = createFontElement(getX2dDocument(), ELEMENT_PIE_LABEL_TEXT_FONT, abstractLabel.getLabelFont());
		if(fontElement != null){
			labelElement.appendChild(fontElement);
		}
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_TEXT_PADDING_X, abstractLabel.getLabelPaddingX()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_TEXT_PADDING_Y, abstractLabel.getLabelPaddingY()));
		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_OUTLINE_ROUND, abstractLabel.getOutlineRound()));
		if(abstractLabel.getOutlineColor() != null){
			labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_PIE_LABEL_OUTLINE_COLOR, abstractLabel.getOutlineColor()));
		}
		
		if(abstractLabel.getOutlineStroke() != null){
			labelElement.appendChild(createStrokeElement(getX2dDocument(), ELEMENT_PIE_LABEL_OUTLINE_STROKE, abstractLabel.getOutlineStroke()));
		}
		if (abstractLabel.getFillColor() != null) {
			labelElement.appendChild(createColorElement(getX2dDocument(), ELEMENT_PIE_LABEL_FILL_COLOR, abstractLabel.getFillColor()));
		}
		if (abstractLabel.getShadeFractions() != null && abstractLabel.getShadeColors() != null) {
			labelElement.appendChild(createShaderElement(getX2dDocument(), ELEMENT_PIE_LABEL_SHADER, new Shader(abstractLabel.getShadeFractions(), abstractLabel.getShadeColors())));
		}

		labelElement.appendChild(createSingleElement(getX2dDocument(), ELEMENT_PIE_LABEL_STYLE, abstractLabel.getStyle().name()));

	}

	/**
	 * deflate the given slice into element
	 * 
	 * @param slice
	 *            the slice to deflate
	 * @return element
	 */
	private Element deflatePieSlice(PieSlice slice) {
		Element sliceElement = getX2dDocument().createElement(ELEMENT_PIE_SLICE);
		if (slice.getName() != null) {
			sliceElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_SLICE_NAME, slice.getName()));
		}
		sliceElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_SLICE_VALUE, slice.getValue()));
		sliceElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_SLICE_DIVERGENCE, slice.getDivergence()));
		sliceElement.appendChild(DeflaterUtil.createColorElement(getX2dDocument(), ELEMENT_PIE_SLICE_COLOR, slice.getThemeColor()));

		for (AbstractPieSliceLabel label : slice.getSliceLabels()) {
			sliceElement.appendChild(deflatePieSliceLabel(label));
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
	private Element deflatePieLinearEffect(PieLinearEffect effect) {
		Element effectElement = getX2dDocument().createElement(ELEMENT_PIE_EFFECT);
		effectElement.setAttribute("xsi:type", ELEMENT_PIE_EFFECT_TYPE_LINEAR);

		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_LINEAR_INCIDENCE,effect.getIncidenceAngleDegree()));
		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_LINEAR_OFFSET_RADIUS, effect.getOffsetRadius()));

		return effectElement;
	}
	
	/**
	 * deflate element
	 * 
	 * @param effect
	 *            the effect to deflate
	 * @return effect element
	 */
	private Element deflatePieCubicEffect(PieCubicEffect effect) {
		Element effectElement = getX2dDocument().createElement(ELEMENT_PIE_EFFECT);
		effectElement.setAttribute("xsi:type", ELEMENT_PIE_EFFECT_TYPE_CUBIC);

		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_CUBIC_INCIDENCE,effect.getIncidenceAngleDegree()));
		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_CUBIC_OFFSET_RADIUS, effect.getOffsetRadius()));
		
		
		Element cubicKey = getX2dDocument().createElement(ELEMENT_PIE_EFFECT_CUBIC_KEY);
		effectElement.appendChild(cubicKey);
		
		cubicKey.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_CUBIC_KEY_ANGLE_DELTA_START, effect.getCubicKey().getStartAngleDelta()));
		cubicKey.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_CUBIC_KEY_ANGLE_DELTA_END, effect.getCubicKey().getEndAngleDelta()));
		cubicKey.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_CUBIC_KEY_RADIUS_FRACTION_START, effect.getCubicKey().getStartControlFractionRadius()));
		cubicKey.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_CUBIC_KEY_RADIUS_FRACTION_END, effect.getCubicKey().getEndControlFractionRadius()));

		if(effect.getFrame() != null)
		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_CUBIC_FRAME, effect.getFrame().name()));
		
		
		return effectElement;
	}

	/**
	 * deflate element
	 * 
	 * @param effect
	 *            the effect to deflate
	 * @return effect element
	 */
	private Element deflatePieRadialEffect(PieRadialEffect effect) {
		Element effectElement = getX2dDocument().createElement(ELEMENT_PIE_EFFECT);
		effectElement.setAttribute("xsi:type", ELEMENT_PIE_EFFECT_TYPE_RADIAL);

		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_RADIAL_FOCUS_ANGLE,effect.getFocusAngle()));
		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_RADIAL_FOCUS_RADIUS, effect.getFocusRadius()));
		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_RADIAL_OFFSET_RADIUS, effect.getOffsetRadius()));

		return effectElement;
	}

	/**
	 * deflate element
	 * 
	 * @param effect
	 *            the effect to deflate
	 * @return effect element
	 */
	private Element deflatePieReflectionEffect(PieReflectionEffect effect) {
		Element effectElement = getX2dDocument().createElement(ELEMENT_PIE_EFFECT);
		effectElement.setAttribute("xsi:type", ELEMENT_PIE_EFFECT_TYPE_REFLECTION);

		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_REFLECTION_BLUR, Boolean.toString(effect.isBlurEnabled())));
		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_REFLECTION_OPACITY, effect.getOpacity()));
		effectElement.appendChild(DeflaterUtil.createSingleElement(getX2dDocument(), ELEMENT_PIE_EFFECT_REFLECTION_LENGTH, effect.getLength()));

		return effectElement;
	}
	
	/**
	 * deflate element
	 * 
	 * @param effect
	 *            the effect to deflate
	 * @return effect element
	 */
	private Element deflatePieCompoundEffect(PieCompoundEffect effect) {
		Element effectElement = getX2dDocument().createElement(ELEMENT_PIE_EFFECT);
		effectElement.setAttribute("xsi:type", ELEMENT_PIE_EFFECT_TYPE_COMPOUND);
		
		AbstractPieEffect[] effects = effect.getEffects();
		for (int i = 0; i < effects.length; i++) {
			Element e =deflatePieEffect(effects[i]);
			if(e != null){
				effectElement.appendChild(e);
			}
		}
		return effectElement;
	}

	/**
	 * inflate pie effect {@link AbstractPieEffect}
	 * 
	 * @param pieEffect
	 *            the pie effect to deflate
	 * @return effect
	 */
	private Element deflatePieEffect(AbstractPieEffect pieEffect) {

		if (pieEffect.getClass().getName().equals(PieCompoundEffect.class.getName())) {
			return deflatePieCompoundEffect((PieCompoundEffect) pieEffect);
		}

		if (pieEffect.getClass().getName().equals(PieLinearEffect.class.getName())) {
			return deflatePieLinearEffect((PieLinearEffect) pieEffect);
		}

		if (pieEffect.getClass().getName().equals(PieRadialEffect.class.getName())) {
			return deflatePieRadialEffect((PieRadialEffect) pieEffect);
		}

		if (pieEffect.getClass().getName().equals(PieReflectionEffect.class.getName())) {
			return deflatePieReflectionEffect((PieReflectionEffect) pieEffect);
		}
		
		if (pieEffect.getClass().getName().equals(PieCubicEffect.class.getName())) {
			return deflatePieCubicEffect((PieCubicEffect) pieEffect);
		}

		return null;
	}

}
