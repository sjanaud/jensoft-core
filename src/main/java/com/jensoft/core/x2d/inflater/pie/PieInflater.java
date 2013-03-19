/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.inflater.pie;



import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jensoft.core.drawable.text.TextPath.PathSide;
import com.jensoft.core.drawable.text.TextPath.TextPosition;
import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.Pie.PieNature;
import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.PieToolkit;
import com.jensoft.core.plugin.pie.painter.effect.AbstractPieEffect;
import com.jensoft.core.plugin.pie.painter.effect.PieCompoundEffect;
import com.jensoft.core.plugin.pie.painter.effect.PieLinearEffect;
import com.jensoft.core.plugin.pie.painter.effect.PieRadialEffect;
import com.jensoft.core.plugin.pie.painter.effect.PieReflectionEffect;
import com.jensoft.core.plugin.pie.painter.label.AbstractPieSliceLabel;
import com.jensoft.core.plugin.pie.painter.label.AbstractPieSliceLabel.Style;
import com.jensoft.core.plugin.pie.painter.label.PieBorderLabel;
import com.jensoft.core.plugin.pie.painter.label.PieBorderLabel.LinkStyle;
import com.jensoft.core.plugin.pie.painter.label.PieBoundLabel;
import com.jensoft.core.plugin.pie.painter.label.PiePathLabel;
import com.jensoft.core.plugin.pie.painter.label.PiePathLabel.PathName;
import com.jensoft.core.plugin.pie.painter.label.PieRadialLabel;
import com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.inflater.X2DInflater;
import com.jensoft.core.x2d.lang.X2DSchema;

/**
 * <code>PieInflater</code>
 * <p>
 * Pie inflater takes the responsibility to parse XML X2D Pie plugin element, see {@link X2DPieElement} into pie related
 * objects.
 * <p>
 * 
 * @see Pie
 * @see PiePlugin
 * @see PieToolkit
 * @see PieNature
 * @see AbstractPieSliceLabel
 * @see AbstractPieEffect
 * @see X2DPieElement
 * @see X2DSchema
 * @author Sebastien Janaud
 */
@X2DInflater(xsi="PiePlugin")
public class PieInflater extends AbstractX2DPluginInflater<PiePlugin> implements X2DPieElement{

    /**
     * create pie inflater with a default pie plugin instance
     */
    public PieInflater() {
        setPlugin(new PiePlugin());
        setXSIType("PiePlugin");
    }

   
    /**
     * inflate the {@link PieLinearEffect}
     * 
     * @param effectElement
     *            the effect element to inflate
     * @return pie linear effect
     */
    private PieLinearEffect inflatePieLinearEffect(Element effectElement) {
        PieLinearEffect linearEffect = new PieLinearEffect();
        Integer incidence = elementInteger(effectElement, ELEMENT_PIE_EFFECT_LINEAR_INCIDENCE);
        Integer offsetRadius = elementInteger(effectElement, ELEMENT_PIE_EFFECT_LINEAR_OFFSET_RADIUS);
        if (incidence != null) {
            linearEffect.setIncidenceAngleDegree(incidence);
        }
        if (offsetRadius != null) {
            linearEffect.setOffsetRadius(offsetRadius);
        }
        return linearEffect;
    }

    /**
     * inflate the {@link PieRadialEffect}
     * 
     * @param effectElement
     *            the effect element to inflate
     * @return pie radial effect
     */
    private PieRadialEffect inflatePieRadialEffect(Element effectElement) {
        PieRadialEffect radialEffect = new PieRadialEffect();
        Integer focusAngle = elementInteger(effectElement, ELEMENT_PIE_EFFECT_RADIAL_FOCUS_ANGLE);
        Integer focusRadius = elementInteger(effectElement, ELEMENT_PIE_EFFECT_RADIAL_FOCUS_RADIUS);
        Integer offsetRadius = elementInteger(effectElement, ELEMENT_PIE_EFFECT_RADIAL_OFFSET_RADIUS);
        if (focusAngle != null) {
            radialEffect.setFocusAngle(focusAngle);
        }
        if (focusRadius != null) {
            radialEffect.setFocusRadius(focusRadius);
        }
        if (offsetRadius != null) {
            radialEffect.setOffsetRadius(offsetRadius);
        }
        return radialEffect;
    }

    /**
     * inflate the {@link PieReflectionEffect}
     * 
     * @param effectElement
     *            the effect element to inflate
     * @return pie reflection effect
     */
    private PieReflectionEffect inflatePieReflectionEffect(Element effectElement) {
        PieReflectionEffect reflectionEffect = new PieReflectionEffect();
        Boolean blurEnable = elementBoolean(effectElement, ELEMENT_PIE_EFFECT_REFLECTION_BLUR);
        Float opacity = elementFloat(effectElement, ELEMENT_PIE_EFFECT_REFLECTION_OPACITY);
        Float length = elementFloat(effectElement, ELEMENT_PIE_EFFECT_REFLECTION_LENGTH);
        if (blurEnable != null) {
            reflectionEffect.setBlurEnabled(blurEnable);
        }
        if (opacity != null) {
            reflectionEffect.setOpacity(opacity);
        }
        if (length != null) {
            reflectionEffect.setLength(length);
        }

        return reflectionEffect;

    }

    /**
     * inflate pie effect {@link AbstractPieEffect}
     * 
     * @param pieEffectElement
     *            the pie effect element to inflate
     * @return effect
     */
    private AbstractPieEffect inflatePieEffect(Element pieEffectElement) {
        if (pieEffectElement == null) {
            return null;
        }

        String type = pieEffectElement.getAttribute("xsi:type");

        if (type == null) {
            return null;
        }

        if (getType(pieEffectElement).equals(ELEMENT_PIE_EFFECT_TYPE_COMPOUND)) {
            NodeList effectElementList = pieEffectElement.getElementsByTagName(ELEMENT_PIE_EFFECT);
            AbstractPieEffect[] fxs = new AbstractPieEffect[effectElementList.getLength()];
            for (int i = 0; i < effectElementList.getLength(); i++) {
                Element subEffectElement = (Element) effectElementList.item(i);
                fxs[i] = inflatePieEffect(subEffectElement);
            }
            return new PieCompoundEffect(fxs);
        }

        if (getType(pieEffectElement).equals(ELEMENT_PIE_EFFECT_TYPE_LINEAR)) {
            return inflatePieLinearEffect(pieEffectElement);
        }

        if (getType(pieEffectElement).equals(ELEMENT_PIE_EFFECT_TYPE_RADIAL)) {
            return inflatePieRadialEffect(pieEffectElement);
        }

        if (getType(pieEffectElement).equals(ELEMENT_PIE_EFFECT_TYPE_REFLECTION)) {
            return inflatePieReflectionEffect(pieEffectElement);
        }

        return null;
    }

    /**
     * inflate the specified label element into {@link PieBorderLabel}
     * 
     * @param labelElement
     *            the label element to inflate
     * @return pie slice label
     */
    private PieBorderLabel inflatePieSliceBorderLabel(Element labelElement) {

        PieBorderLabel sliceLabel = new PieBorderLabel();
        inflatePieSliceLabel(sliceLabel, labelElement);

        // border slice label related properties
        Boolean linkEnabled = elementBoolean(labelElement, ELEMENT_PIE_LABEL_BORDER_LINK);
        String linkStyle = elementText(labelElement, ELEMENT_PIE_LABEL_BORDER_LINK_STYLE);
        Color linkColor = elementColor(labelElement, ELEMENT_PIE_LABEL_BORDER_LINK_COLOR);
        Stroke linkStroke = elementStroke(labelElement, ELEMENT_PIE_LABEL_BORDER_LINK_STROKE);
        Integer linkExtends = elementInteger(labelElement, ELEMENT_PIE_LABEL_BORDER_LINK_EXTENDS);
        Boolean markerEnabled = elementBoolean(labelElement, ELEMENT_PIE_LABEL_BORDER_LINK_MARKER);
        Color linkMarkerDrawColor = elementColor(labelElement, ELEMENT_PIE_LABEL_BORDER_LINK_MARKERDRAW);
        Color linkMarkerFillColor = elementColor(labelElement, ELEMENT_PIE_LABEL_BORDER_LINK_MARKERFILL);
        Integer margin = elementInteger(labelElement, ELEMENT_PIE_LABEL_BORDER_MARGIN);

        if (linkEnabled != null && !linkEnabled.equals("undefined")) {
            sliceLabel.setLink(linkEnabled);
        }
        if (linkStyle != null && !linkStyle.equals("undefined")) {
            sliceLabel.setLinkStyle(LinkStyle.parseStyle(linkStyle));
        }
        if (linkExtends != null && !linkExtends.equals("undefined")) {
            sliceLabel.setLinkExtends(linkExtends);
        }
        sliceLabel.setLinkColor(linkColor);
        sliceLabel.setLinkStroke(linkStroke);

        if (markerEnabled != null && !markerEnabled.equals("undefined")) {
            sliceLabel.setLinkMarker(markerEnabled);
        }

        sliceLabel.setLinkMarkerDrawColor(linkMarkerDrawColor);
        sliceLabel.setLinkMarkerFillColor(linkMarkerFillColor);

        if (margin != null && !margin.equals("undefined")) {
            sliceLabel.setMargin(margin);
        }

        return sliceLabel;
    }

    /**
     * inflate the specified label element into {@link PieRadialLabel}
     * 
     * @param labelElement
     *            the label element to parse
     * @return the slice label
     */
    private PieRadialLabel inflatePieSliceRadialLabel(Element labelElement) {

        PieRadialLabel sliceLabel = new PieRadialLabel();
        inflatePieSliceLabel(sliceLabel, labelElement);

        Integer offsetRadius = elementInteger(labelElement, ELEMENT_PIE_LABEL_RADIAL_OFFSET_RADIUS);

        if (offsetRadius != null && !offsetRadius.equals("undefined")) {
            sliceLabel.setOffsetRadius(offsetRadius);
        }

        return sliceLabel;
    }

    /**
     * parse the specified label element into {@link PieBoundLabel}
     * 
     * @param labelElement
     *            the label element to parse
     * @return pie slice label
     */
    private PieBoundLabel inflatePieSliceBoundLabel(Element labelElement) {
        PieBoundLabel sliceLabel = new PieBoundLabel();
        inflatePieSliceLabel(sliceLabel, labelElement);
        return sliceLabel;
    }

    /**
     * inflate the specified element into {@link PiePathLabel}
     * 
     * @param labelElement
     *            the label element to parse
     * @return pie slice label
     */
    private PiePathLabel inflatePieSlicePathLabel(Element labelElement) {

        PiePathLabel sliceLabel = new PiePathLabel();
        inflatePieSliceLabel(sliceLabel, labelElement);

        Integer pathDivergence = elementInteger(labelElement, ELEMENT_PIE_LABEL_PATH_TEXT_DIVERGENCE);
        String textposition = elementText(labelElement, ELEMENT_PIE_LABEL_PATH_TEXT_POSITION);
        String pathside = elementText(labelElement, ELEMENT_PIE_LABEL_PATH_TEXT_SIDE);
        String pathname = elementText(labelElement, ELEMENT_PIE_LABEL_PATH_SEGMENT_PATH);
        Shader shader = elementShader(labelElement, ELEMENT_PIE_LABEL_PATH_TEXT_SHADER);

        sliceLabel.setDivergence(pathDivergence);
        sliceLabel.setPathName(PathName.parse(pathname));
        sliceLabel.setTextPosition(TextPosition.parse(textposition));
        sliceLabel.setPathSide(PathSide.parse(pathside));
        sliceLabel.setTextShader(shader);

        return sliceLabel;

    }

    /**
     * inflate abstract label properties into specified {@link AbstractPieSliceLabel}
     * 
     * @param labelElement
     *            the label element to inflate
     */
    private void inflatePieSliceLabel(AbstractPieSliceLabel abstractLabel,
            Element labelElement) {

        String text = elementText(labelElement, ELEMENT_PIE_LABEL_TEXT);
        Color textColor = elementColor(labelElement, ELEMENT_PIE_LABEL_TEXT_COLOR);
        Font textFont = elementFont(labelElement, ELEMENT_PIE_LABEL_TEXT_FONT);
        Integer labelPaddingX = elementInteger(labelElement, ELEMENT_PIE_LABEL_TEXT_PADDING_X);
        Integer labelPaddingY = elementInteger(labelElement, ELEMENT_PIE_LABEL_TEXT_PADDING_Y);
        Integer outlineRound = elementInteger(labelElement, ELEMENT_PIE_LABEL_OUTLINE_ROUND);
        Color outlineColor = elementColor(labelElement, ELEMENT_PIE_LABEL_OUTLINE_COLOR);
        Stroke outlineStroke = elementStroke(labelElement, ELEMENT_PIE_LABEL_OUTLINE_STROKE);
        Color fillColor = elementColor(labelElement, ELEMENT_PIE_LABEL_FILL_COLOR);
        Shader shader = elementShader(labelElement, ELEMENT_PIE_LABEL_SHADER);
        String style = elementText(labelElement, ELEMENT_PIE_LABEL_STYLE);

        if (text != null) {
            abstractLabel.setLabel(text);
        }
        if (textColor != null) {
            abstractLabel.setLabelColor(textColor);
        }
        if (textFont != null) {
            abstractLabel.setLabelFont(textFont);
        }
        if (labelPaddingX != null) {
            abstractLabel.setLabelPaddingX(labelPaddingX);
        }
        if (labelPaddingY != null) {
            abstractLabel.setLabelPaddingY(labelPaddingY);
        }
        if (outlineRound != null) {
            abstractLabel.setOutlineRound(outlineRound);
        }
        if (outlineColor != null) {
            abstractLabel.setOutlineColor(outlineColor);
        }
        if (outlineStroke != null) {
            abstractLabel.setOutlineStroke(outlineStroke);
        }
        if (fillColor != null) {
            abstractLabel.setFillColor(fillColor);
        }
        if (shader != null) {
            abstractLabel.setShader(shader);
        }
        if (style != null) {
            abstractLabel.setStyle(Style.parseStyle(style));
        }

    }

    /**
     * inflate specified element into {@link AbstractPieSliceLabel}
     * 
     * @param labelElement
     *            the label element to inflate
     * @return pie slice label
     */
    private AbstractPieSliceLabel inflatePieSliceLabel(Element labelElement) {

        if (getType(labelElement).equals(ELEMENT_PIE_SLICE_LABEL_TYPE_RADIAL)) {
            return inflatePieSliceRadialLabel(labelElement);
        }
        else if (getType(labelElement).equals(ELEMENT_PIE_SLICE_LABEL_TYPE_BORDER)) {
            return inflatePieSliceBorderLabel(labelElement);
        }
        else if (getType(labelElement).equals(ELEMENT_PIE_SLICE_LABEL_TYPE_BOUND)) {
            return inflatePieSliceBoundLabel(labelElement);
        }
        else if (getType(labelElement).equals(ELEMENT_PIE_SLICE_LABEL_TYPE_PATH)) {
            return inflatePieSlicePathLabel(labelElement);
        }
        return null;
    }

    /**
     * inflate the specified element into {@link PieSlice}
     * 
     * @param sliceElement
     *            the element to inflate
     * @return pie slice
     */
    private PieSlice inflatePieSlice(Element sliceElement) {

        String sliceName = elementText(sliceElement, ELEMENT_PIE_SLICE_NAME);
        String value = elementText(sliceElement, ELEMENT_PIE_SLICE_VALUE);
        String divergence = elementText(sliceElement, ELEMENT_PIE_SLICE_DIVERGENCE);
        Color color = elementColor(sliceElement, ELEMENT_PIE_SLICE_COLOR);

        PieSlice s = PieToolkit.createSlice(sliceName,
                                            color, Integer.parseInt(value),
                                            Integer.parseInt(divergence));

        NodeList labelListElement = sliceElement.getElementsByTagName(ELEMENT_PIE_SLICE_LABEL);

        for (int i = 0; i < labelListElement.getLength(); i++) {
            Element labelElement = (Element) labelListElement.item(i);
            AbstractPieSliceLabel sliceLabel = inflatePieSliceLabel(labelElement);
            if (sliceLabel != null) {
                s.addSliceLabel(sliceLabel);
            }
        }

        return s;
    }

    /**
     * inflate specified element into {@link Pie}
     * 
     * @param pieElement
     *            the element to inflate
     * @return pie
     */
    private Pie inflatePie(Element pieElement) {
        String name = elementText(pieElement, ELEMENT_PIE_NAME);
        Double x = elementDouble(pieElement, ELEMENT_PIE_X);
        Double y = elementDouble(pieElement, ELEMENT_PIE_Y);
        Integer radius = elementInteger(pieElement, ELEMENT_PIE_RADIUS);
        Integer startAngleDegree = elementInteger(pieElement, ELEMENT_PIE_START_ANGLE_DEGREE);
        String nature = elementText(pieElement, ELEMENT_PIE_NATURE);

        Pie pie = PieToolkit.createPie(name, radius);
        pie.setCenterX(x);
        pie.setCenterY(y);
        pie.setStartAngleDegree(startAngleDegree);
        pie.setPieNature(PieNature.parseNature(nature));
        pie.setHostPlugin(getPlugin());
        NodeList slices = pieElement.getElementsByTagName(ELEMENT_PIE_SLICE);
        for (int i = 0; i < slices.getLength(); i++) {
            Element pieSliceElement = (Element) slices.item(i);
            PieSlice pieSlice = inflatePieSlice(pieSliceElement);
            pie.addSlice(pieSlice);
        }

        Element effectElement = (Element) pieElement.getElementsByTagName(ELEMENT_PIE_EFFECT).item(0);
        AbstractPieEffect effect = inflatePieEffect(effectElement);
        pie.setPieEffect(effect);

        return pie;

    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.jet.inflater.AbstractPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public void inflate(Element pluginElement) {
        NodeList pieElementList = pluginElement.getElementsByTagName(ELEMENT_PIE);
        for (int i = 0; i < pieElementList.getLength(); i++) {
            Element pieElement = (Element) pieElementList.item(i);
            Pie pie = inflatePie(pieElement);
            getPlugin().addPie(pie);
        }
    }

}