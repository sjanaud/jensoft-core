/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.donut2d;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jensoft.core.drawable.text.TextPath.PathSide;
import com.jensoft.core.drawable.text.TextPath.TextPosition;
import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2D.Donut2DNature;
import com.jensoft.core.plugin.donut2d.Donut2DPlugin;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;
import com.jensoft.core.plugin.donut2d.Donut2DToolkit;
import com.jensoft.core.plugin.donut2d.painter.effect.AbstractDonut2DEffect;
import com.jensoft.core.plugin.donut2d.painter.effect.Donut2DCompoundEffect;
import com.jensoft.core.plugin.donut2d.painter.effect.Donut2DLinearEffect;
import com.jensoft.core.plugin.donut2d.painter.effect.Donut2DReflectionEffect;
import com.jensoft.core.plugin.donut2d.painter.fill.AbstractDonut2DFill;
import com.jensoft.core.plugin.donut2d.painter.fill.Donut2DDefaultFill;
import com.jensoft.core.plugin.donut2d.painter.fill.Donut2DRadialFill;
import com.jensoft.core.plugin.donut2d.painter.fill.Donut2DSliceRadialFill.GradientFillType;
import com.jensoft.core.plugin.donut2d.painter.label.AbstractDonut2DSliceLabel;
import com.jensoft.core.plugin.donut2d.painter.label.AbstractDonut2DSliceLabel.Style;
import com.jensoft.core.plugin.donut2d.painter.label.Donut2DBorderLabel;
import com.jensoft.core.plugin.donut2d.painter.label.Donut2DBorderLabel.LinkStyle;
import com.jensoft.core.plugin.donut2d.painter.label.Donut2DPathLabel;
import com.jensoft.core.plugin.donut2d.painter.label.Donut2DPathLabel.Donut2DFacetPathName;
import com.jensoft.core.plugin.donut2d.painter.label.Donut2DRadialLabel;
import com.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.binding.InflaterUtil;
import com.jensoft.core.x2d.binding.X2DBinding;
/**
 * <code>Donut2DInflater</code>
 * <p>
 * Donut2D inflater takes the responsibility to parse XML element into {@link Donut2D} related objects.
 * <p>
 * 
 * @author Sebastien Janaud
 */
@X2DBinding(xsi="Donut2DPlugin", plugin=Donut2DPlugin.class)
public class Donut2DInflater extends AbstractX2DPluginInflater<Donut2DPlugin> implements X2DDonut2DElement{

    /**
     * defines the various type for donut2D effect
     * 
     * @author Sebastien Janaud
     */
    public enum Donut2DEffectType {
        Donut2DLinearEffect("Donut2DLinearEffect"),
        Donut2DReflectionEffect("Donut2DReflectionEffect"),
        Donut2DCompoundEffect("Donut2DCompoundEffect");

        /** the type name */
        private String donut2DEffectType;

        /**
         * create donut2D effect available type
         * 
         * @param donut2DEffectType
         */
        private Donut2DEffectType(String donut2DEffectType) {
            this.donut2DEffectType = donut2DEffectType;
        }

        /**
         * @return the pieEffectType
         */
        public String getDonut2DEffectType() {
            return donut2DEffectType;
        }
    }

    /**
     * inflate the {@link Donut2DLinearEffect}
     * 
     * @param effectElement
     *            the effect element to inflate
     * @return donut2D linear effect
     */
    private Donut2DLinearEffect inflateDonut2DLinearEffect(Element effectElement) {
        Element incidenceElement = (Element) effectElement
                .getElementsByTagName(ELEMENT_DONUT2D_EFFECT_LINEAR_INCIDENCE).item(0);
        Element offsetElement = (Element) effectElement
                .getElementsByTagName(ELEMENT_DONUT2D_EFFECT_LINEAR_OFFSET_RADIUS).item(0);

        Donut2DLinearEffect linear = new Donut2DLinearEffect();

        if (incidenceElement != null) {
            linear.setIncidenceAngleDegree(Integer.parseInt(incidenceElement.getTextContent().trim()));
        }
        if (offsetElement != null) {
            linear.setOffsetRadius(Integer.parseInt(offsetElement.getTextContent().trim()));
        }

        return linear;
    }

    /**
     * inflate the {@link Donut2DReflectionEffect}
     * 
     * @param effectElement
     *            the effect element to inflate
     * @return donut2D reflection effect
     */
    private Donut2DReflectionEffect inflateDonut2DReflectionEffect(Element effectElement) {
        Element blurElement = (Element) effectElement
                .getElementsByTagName(ELEMENT_DONUT2D_EFFECT_REFLECTION_BLUR_ENABLE).item(0);
        Element opacityElement = (Element) effectElement
                .getElementsByTagName(ELEMENT_DONUT2D_EFFECT_REFLECTION_MASK_OPACITY).item(0);
        Element lengthElement = (Element) effectElement
                .getElementsByTagName(ELEMENT_DONUT2D_EFFECT_REFLECTION_REFLECT_LENGTH).item(0);

        Donut2DReflectionEffect reflectionEffect = new Donut2DReflectionEffect();

        if (blurElement != null) {
            reflectionEffect.setBlurEnabled(Boolean.parseBoolean(blurElement.getTextContent().trim()));
        }
        if (opacityElement != null) {
            reflectionEffect.setOpacity(Float.parseFloat(opacityElement.getTextContent().trim()));
        }
        if (lengthElement != null) {
            reflectionEffect.setLength(Float.parseFloat(lengthElement.getTextContent().trim()));
        }

        return reflectionEffect;
    }

    /**
     * inflate donut2D effect {@link AbstractDonut2DEffect}
     * 
     * @param donut2DEffectElement
     *            the effect element to inflate
     * @return effect
     */
    private AbstractDonut2DEffect inflateDonut2DEffect(Element donut2DEffectElement) {
        if (donut2DEffectElement == null) {
            return null;
        }

        String type = donut2DEffectElement.getAttribute("xsi:type");

        if (type == null) {
            return null;
        }

        if (Donut2DEffectType.Donut2DCompoundEffect.getDonut2DEffectType().equals(type)) {
            NodeList effectElementList = donut2DEffectElement.getElementsByTagName(ELEMENT_DONUT2D_EFFECT);
            AbstractDonut2DEffect[] fxs = new AbstractDonut2DEffect[effectElementList.getLength()];
            for (int i = 0; i < effectElementList.getLength(); i++) {
                Element subEffectElement = (Element) effectElementList.item(i);
                fxs[i] = inflateDonut2DEffect(subEffectElement);
            }
            return new Donut2DCompoundEffect(fxs);
        }

        if (Donut2DEffectType.Donut2DLinearEffect.getDonut2DEffectType().equals(type)) {
            return inflateDonut2DLinearEffect(donut2DEffectElement);
        }

        if (Donut2DEffectType.Donut2DReflectionEffect.getDonut2DEffectType().equals(type)) {
            return inflateDonut2DReflectionEffect(donut2DEffectElement);
        }

        return null;
    }

    /**
     * inflate the {@link Donut2DDefaultFill}
     * 
     * @param fillElement
     *            the paint element to inflate
     * @return default donut2D fill
     */
    private Donut2DDefaultFill inflateDonut2DDefaultFill(Element fillElement) {
        Donut2DDefaultFill defaultFill = new Donut2DDefaultFill();
        return defaultFill;
    }

    /**
     * inflate the {@link Donut2DRadialFill}
     * 
     * @param fillElement
     *            the paint element to inflate
     * @return radial donut2D fill
     */
    private Donut2DRadialFill inflateDonut2DRadialFill(Element fillElement) {

        Element gradientBehaviorElement = (Element) fillElement
                .getElementsByTagName(ELEMENT_DONUT2D_FILL_RADIAL_GRADIENT_TYPE).item(0);

        Donut2DRadialFill radialFill = new Donut2DRadialFill();

        if (gradientBehaviorElement != null) {
            radialFill.setGradientBehavior(GradientFillType.parse(gradientBehaviorElement.getTextContent().trim()));
        }

        return radialFill;
    }

    /**
     * defines the various type for donut 2d fill
     * 
     * @author Sebastien Janaud
     */
    public enum Donut2DFillType {
        Donut2DDefaultFill("Donut2DDefaultFill"),
        Donut2DRadialFill("Donut2DRadialFill");

        private String donut2DFillType;

        private Donut2DFillType(String donut2DFillType) {
            this.donut2DFillType = donut2DFillType;
        }

        /**
         * @return the donut3D fill Type
         */
        public String getDonut2DPaintType() {
            return donut2DFillType;
        }
    }

    /**
     * inflate donutD fill
     * 
     * @param donut2DFillElement
     *            the donut3D paint element to inflate
     * @return fill
     */
    private AbstractDonut2DFill inflateDonut2DFill(Element donut2DFillElement) {
        if (donut2DFillElement == null) {
            return null;
        }
        String type = donut2DFillElement.getAttribute("xsi:type");

        if (type == null) {
            return null;
        }

        if (Donut2DFillType.Donut2DDefaultFill.getDonut2DPaintType().equals(type)) {
            return inflateDonut2DDefaultFill(donut2DFillElement);
        }
        if (Donut2DFillType.Donut2DRadialFill.getDonut2DPaintType().equals(type)) {
            return inflateDonut2DRadialFill(donut2DFillElement);
        }

        return null;
    }

    /**
     * inflate the specified label element into {@link Donut2DBorderLabel}
     * 
     * @param labelElement
     *            the label element to inflate
     * @return donut2D border slice label
     */
    private Donut2DBorderLabel inflateDonut2DSliceBorderLabel(Element labelElement) {

        Donut2DBorderLabel sliceLabel = new Donut2DBorderLabel();
        inflateDonut2DSliceLabel(sliceLabel, labelElement);

        // border slice label related properties
        String link = elementText(labelElement, ELEMENT_DONUT2D_LABEL_BORDER_LINK_ENABLE);
        String linkStyle = elementText(labelElement, ELEMENT_DONUT2D_LABEL_BORDER_LINK_STYLE);
        Element linkColorElement = (Element) labelElement.getElementsByTagName(ELEMENT_DONUT2D_LABEL_BORDER_LINK_COLOR)
                .item(0);
        Element linkStrokeElement = (Element) labelElement
                .getElementsByTagName(ELEMENT_DONUT2D_LABEL_BORDER_LINK_STROKE).item(0);
        String linkextends = elementText(labelElement, ELEMENT_DONUT2D_LABEL_BORDER_LINK_EXTENDS);
        String linkMarker = elementText(labelElement, ELEMENT_DONUT2D_LABEL_BORDER_LINK_MARKER_ENABLE);
        Element linkMarkerDrawColorElement = (Element) labelElement
                .getElementsByTagName(ELEMENT_DONUT2D_LABEL_BORDER_LINK_MARKER_DRAW).item(0);
        Element linkMarkerFillColorElement = (Element) labelElement
                .getElementsByTagName(ELEMENT_DONUT2D_LABEL_BORDER_LINK_MARKER_FILL).item(0);

        String margin = elementText(labelElement, ELEMENT_DONUT2D_LABEL_BORDER_MARGIN);

        if (link != null && !link.equals("undefined")) {
            sliceLabel.setLink(Boolean.parseBoolean(link));
        }
        if (linkStyle != null && !linkStyle.equals("undefined")) {
            sliceLabel.setLinkStyle(LinkStyle.parseStyle(linkStyle));
        }

        if (linkextends != null && !linkextends.equals("undefined")) {
            sliceLabel.setLinkExtends(Integer.parseInt(linkextends));
        }

        sliceLabel.setLinkColor(elementColor(linkColorElement));
        sliceLabel.setLinkStroke(InflaterUtil.elementStroke(linkStrokeElement));

        if (linkMarker != null && !linkMarker.equals("undefined")) {
            sliceLabel.setLinkMarker(Boolean.parseBoolean(linkMarker));
        }

        sliceLabel.setLinkMarkerDrawColor(elementColor(linkMarkerDrawColorElement));
        sliceLabel.setLinkMarkerFillColor(elementColor(linkMarkerFillColorElement));

        if (margin != null && !margin.equals("undefined")) {
            sliceLabel.setMargin(Integer.parseInt(margin));
        }

        return sliceLabel;
    }

    /**
     * inflate the specified label element into {@link Donut2DRadialLabel}
     * 
     * @param labelElement
     *            the label element to parse
     * @return the slice label
     */
    private Donut2DRadialLabel inflateDonut2DSliceRadialLabel(Element labelElement) {

        Donut2DRadialLabel sliceLabel = new Donut2DRadialLabel();
        inflateDonut2DSliceLabel(sliceLabel, labelElement);

        String offsetRadius = elementText(labelElement, ELEMENT_DONUT2D_LABEL_RADIAL_OFFSET_RADIUS);

        if (offsetRadius != null && !offsetRadius.equals("undefined")) {
            sliceLabel.setOffsetRadius(Integer.parseInt(offsetRadius));
        }

        return sliceLabel;
    }

    /**
     * inflate the specified element into {@link Donut2DPathLabel}
     * 
     * @param labelElement
     *            the label element to parse
     * @return pie slice label
     */
    private Donut2DPathLabel inflateDonut2DSlicePathLabel(Element labelElement) {

        Donut2DPathLabel sliceLabel = new Donut2DPathLabel();
        inflateDonut2DSliceLabel(sliceLabel, labelElement);

        String pathdivergence = elementText(labelElement, ELEMENT_DONUT2D_LABEL_TEXT_DIVERGENCE);
        String textposition = elementText(labelElement, ELEMENT_DONUT2D_LABEL_PATH_TEXT_POSITION);
        String pathside = elementText(labelElement, ELEMENT_DONUT2D_LABEL_PATH_TEXT_SIDE);
        String pathname = elementText(labelElement, ELEMENT_DONUT2D_LABEL_PATH_SEGMENT_PATH);
        Element textShaderElement = (Element) labelElement.getElementsByTagName(ELEMENT_DONUT2D_LABEL_PATH_TEXT_SHADER)
                .item(0);

        sliceLabel.setDivergence(Donut2DPathLabel.parseDivergence(pathdivergence));
        sliceLabel.setFacetPathName(Donut2DFacetPathName.parse(pathname));
        sliceLabel.setTextPosition(TextPosition.parse(textposition));
        sliceLabel.setPathSide(PathSide.parse(pathside));
        sliceLabel.setTextShader(InflaterUtil.elementShader(textShaderElement));

        return sliceLabel;

    }

    /**
     * list of available donut2D slice label
     * 
     * @author Sebastien Janaud
     */
    public enum Donut2DLabelType {
        Donut2DRadialLabel("Donut2DRadialLabel"), Donut2DBorderLabel("Donut2DBorderLabel"), Donut2DPathLabel(
                "Donut2DPathLabel");

        private String donut2DLabelType;

        private Donut2DLabelType(String donut2DLabelType) {
            this.donut2DLabelType = donut2DLabelType;
        }

        /**
         * @return the donut2D Label Type
         */
        public String getDonut2DLabelType() {
            return donut2DLabelType;
        }

    }

    /**
     * inflate abstract label properties
     * 
     * @param labelElement
     *            the label element to inflate
     */
    private void inflateDonut2DSliceLabel(AbstractDonut2DSliceLabel abstractLabel,
            Element labelElement) {

        String text = elementText(labelElement, ELEMENT_DONUT2D_LABEL_TEXT);
        Element textColorElement = (Element) labelElement.getElementsByTagName(
                                                                               ELEMENT_DONUT2D_LABEL_TEXT_COLOR)
                .item(0);
        String font = elementText(labelElement, ELEMENT_DONUT2D_LABEL_TEXT_FONT);
        String labelPaddingX = elementText(labelElement, ELEMENT_DONUT2D_LABEL_PADDING_X);
        String labelPaddingY = elementText(labelElement, ELEMENT_DONUT2D_LABEL_PADDING_Y);
        String outlineRound = elementText(labelElement, ELEMENT_DONUT2D_LABEL_OUTLINE_ROUND);
        Element outlineColorElement = (Element) labelElement
                .getElementsByTagName(ELEMENT_DONUT2D_LABEL_OUTLINE_COLOR).item(0);
        Element outlineStrokeElement = (Element) labelElement
                .getElementsByTagName(ELEMENT_DONUT2D_LABEL_OUTLINE_STROKE).item(0);
        Element fillColorElement = (Element) labelElement.getElementsByTagName(
                                                                               ELEMENT_DONUT2D_LABEL_FILL_COLOR)
                .item(0);
        Element shaderElement = (Element) labelElement.getElementsByTagName(
                                                                            ELEMENT_DONUT2D_LABEL_SHADER).item(0);
        String style = elementText(labelElement, ELEMENT_DONUT2D_LABEL_STYLE);

        abstractLabel.setLabel(text);
        abstractLabel.setLabelColor(elementColor(textColorElement));
        abstractLabel.setLabelFont(InflaterUtil.parseFont(font));
        abstractLabel.setLabelPaddingX(Integer.parseInt(labelPaddingX));
        abstractLabel.setLabelPaddingY(Integer.parseInt(labelPaddingY));
        abstractLabel.setOutlineRound(Integer.parseInt(outlineRound));
        abstractLabel.setOutlineColor(elementColor(outlineColorElement));
        abstractLabel.setOutlineStroke(InflaterUtil.elementStroke(outlineStrokeElement));
        abstractLabel.setFillColor(elementColor(fillColorElement));
        abstractLabel.setShader(InflaterUtil.elementShader(shaderElement));
        abstractLabel.setStyle(Style.parseStyle(style));

    }

    /**
     * inflate specified element into {@link AbstractDonut2DSliceLabel}
     * 
     * @param labelElement
     *            the label element to inflate
     * @return donut3D slice label
     */
    private AbstractDonut2DSliceLabel inflateDonut2DSliceLabel(Element labelElement) {

        String type = labelElement.getAttribute("xsi:type");

        if (type == null) {
            return null;
        }

        if (Donut2DLabelType.Donut2DRadialLabel.getDonut2DLabelType().equals(type)) {
            return inflateDonut2DSliceRadialLabel(labelElement);
        }
        else if (Donut2DLabelType.Donut2DBorderLabel.getDonut2DLabelType().equals(type)) {
            return inflateDonut2DSliceBorderLabel(labelElement);
        }
        else if (Donut2DLabelType.Donut2DPathLabel.getDonut2DLabelType().equals(type)) {
            return inflateDonut2DSlicePathLabel(labelElement);
        }
        return null;
    }

    /**
     * inflate the specified element into {@link Donut2DSlice}
     * 
     * @param sliceElement
     *            the element to inflate
     * @return donut3D slice
     */
    private Donut2DSlice inflateDonut2DSlice(Element sliceElement) {

        String sliceName = elementText(sliceElement, ELEMENT_DONUT2D_SLICE_NAME);
        String value = elementText(sliceElement, ELEMENT_DONUT2D_SLICE_VALUE);
        String divergence = elementText(sliceElement, ELEMENT_DONUT2D_SLICE_DIVERGENCE);
        Element colorElement = (Element) sliceElement.getElementsByTagName(ELEMENT_DONUT2D_SLICE_COLOR).item(0);

        Donut2DSlice s = Donut2DToolkit.createDonut2DSlice(sliceName,
                                                           elementColor(colorElement),
                                                           Integer.parseInt(value),
                                                           Integer.parseInt(divergence));

        NodeList labelListElement = sliceElement.getElementsByTagName(ELEMENT_DONUT2D_SLICE_LABEL);

        for (int i = 0; i < labelListElement.getLength(); i++) {
            Element labelElement = (Element) labelListElement.item(i);
            AbstractDonut2DSliceLabel sliceLabel = inflateDonut2DSliceLabel(labelElement);
            if (sliceLabel != null) {
                // s.addSliceLabel(sliceLabel);
                s.addSliceLabel(sliceLabel);
            }
        }

        return s;
    }

    /**
     * inflate specified element into {@link Donut2D}
     * 
     * @param donut2DElement
     *            the element to inflate
     * @return donut3D
     */
    private Donut2D inflateDonut2D(Element donut2DElement) {

        String x = elementText(donut2DElement, ELEMENT_DONUT2D_X);
        String y = elementText(donut2DElement, ELEMENT_DONUT2D_Y);
        String innerRadius = elementText(donut2DElement, ELEMENT_DONUT2D_INNER_RADIUS);
        String outerRadius = elementText(donut2DElement, ELEMENT_DONUT2D_OUTER_RADIUS);
        String startAngleDegree = elementText(donut2DElement, ELEMENT_DONUT2D_START_ANGLE);
        String nature = elementText(donut2DElement, ELEMENT_DONUT2D_NATURE);

        Donut2D donut2D = Donut2DToolkit.createDonut2D("donut2d", Integer.parseInt(innerRadius),
                                                       Integer.parseInt(outerRadius));

        donut2D.setCenterX(Double.parseDouble(x));
        donut2D.setCenterY(Double.parseDouble(y));
        donut2D.setStartAngleDegree(Integer.parseInt(startAngleDegree));
        donut2D.setNature(Donut2DNature.parseNature(nature));

        NodeList slices = donut2DElement.getElementsByTagName(ELEMENT_DONUT2D_SLICE);
        for (int i = 0; i < slices.getLength(); i++) {
            Element donut3DSliceElement = (Element) slices.item(i);
            Donut2DSlice donut3DSlice = inflateDonut2DSlice(donut3DSliceElement);
            donut2D.addSlice(donut3DSlice);
        }

        Element fillElement = (Element) donut2DElement.getElementsByTagName(ELEMENT_DONUT2D_FILL).item(0);
        AbstractDonut2DFill donut2DFill = inflateDonut2DFill(fillElement);
        if (donut2DFill != null) {
            donut2D.setDonut2DFill(donut2DFill);
        }
        Element effectElement = (Element) donut2DElement.getElementsByTagName(ELEMENT_DONUT2D_EFFECT).item(0);
        AbstractDonut2DEffect donut2DEffect = inflateDonut2DEffect(effectElement);
        if (donut2DEffect != null) {
            donut2D.setDonut2DEffect(donut2DEffect);
        }

        return donut2D;

    }

    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public Donut2DPlugin inflate(Element pluginElement) {
    	Donut2DPlugin d2d = new Donut2DPlugin();
        NodeList donut2DElementList = pluginElement.getElementsByTagName(ELEMENT_DONUT2D);
        for (int i = 0; i < donut2DElementList.getLength(); i++) {
            Element donut2DElement = (Element) donut2DElementList.item(i);
            Donut2D donut2d = inflateDonut2D(donut2DElement);
            d2d.addDonut(donut2d);
        }
        
        return d2d;
    }

}
