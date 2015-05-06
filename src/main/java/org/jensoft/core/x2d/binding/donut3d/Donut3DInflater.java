/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.donut3d;

import org.jensoft.core.drawable.text.TextPath.PathSide;
import org.jensoft.core.drawable.text.TextPath.TextPosition;
import org.jensoft.core.plugin.donut3d.Donut3D;
import org.jensoft.core.plugin.donut3d.Donut3DPlugin;
import org.jensoft.core.plugin.donut3d.Donut3DSlice;
import org.jensoft.core.plugin.donut3d.Donut3DToolkit;
import org.jensoft.core.plugin.donut3d.Donut3D.Donut3DNature;
import org.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel;
import org.jensoft.core.plugin.donut3d.painter.label.Donut3DBorderLabel;
import org.jensoft.core.plugin.donut3d.painter.label.Donut3DPathLabel;
import org.jensoft.core.plugin.donut3d.painter.label.Donut3DRadialLabel;
import org.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel.Style;
import org.jensoft.core.plugin.donut3d.painter.label.Donut3DBorderLabel.LinkAlignment;
import org.jensoft.core.plugin.donut3d.painter.label.Donut3DBorderLabel.LinkStyle;
import org.jensoft.core.plugin.donut3d.painter.label.Donut3DPathLabel.Donut3DFacetPathName;
import org.jensoft.core.plugin.donut3d.painter.paint.AbstractDonut3DPaint;
import org.jensoft.core.plugin.donut3d.painter.paint.Donut3DDefaultPaint;
import org.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import org.jensoft.core.x2d.binding.InflaterUtil;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
/**
 * <code>Donut3DInflater</code>
 * <p>
 * Donut3D inflater takes the responsibility to create {@link Donut3DPlugin} from donut3D x2d element.
 * <p>
 * 
 * @author Sebastien Janaud
 */
@X2DBinding(xsi="Donut3DPlugin",plugin=Donut3DPlugin.class)
public class Donut3DInflater extends AbstractX2DPluginInflater<Donut3DPlugin> implements X2DDonut3DElement{


    /**
     * inflate the {@link Donut3DDefaultPaint}
     * 
     * @param paintElement
     *            the paint element to inflate
     * @return default paint
     */
    private Donut3DDefaultPaint inflateDonut3DDefaultPaint(Element paintElement) {
        Element incidenceElement = (Element) paintElement
                .getElementsByTagName(ELEMENT_DONUT3D_DEFAULTPAINT_INCIDENCE_EFFECT_ANGLE).item(0);

        Element paintTopEffect = (Element) paintElement
                .getElementsByTagName(ELEMENT_DONUT3D_DEFAULTPAINT_TOP_EFFECT_ENABLE).item(0);
        Element paintInnerEffect = (Element) paintElement
                .getElementsByTagName(ELEMENT_DONUT3D_DEFAULTPAINT_INNER_EFFECT_ENABLE).item(0);
        Element paintOuterEffect = (Element) paintElement
                .getElementsByTagName(ELEMENT_DONUT3D_DEFAULTPAINT_OUTER_EFFECT_ENABLE).item(0);

        Element alphaFillElement = (Element) paintElement
                .getElementsByTagName(ELEMENT_DONUT3D_DEFAULTPAINT_SLICE_FILL_ALPHA).item(0);
        Element alphaTopEffectElement = (Element) paintElement
                .getElementsByTagName(ELEMENT_DONUT3D_DEFAULTPAINT_TOP_EFFECT_ALPHA).item(0);
        Element alphaInnerEffectElement = (Element) paintElement
                .getElementsByTagName(ELEMENT_DONUT3D_DEFAULTPAINT_INNER_EFFECT_ALPHA).item(0);
        Element alphaOuterEffectElement = (Element) paintElement
                .getElementsByTagName(ELEMENT_DONUT3D_DEFAULTPAINT_OUTER_EFFECT_ALPHA).item(0);

        Donut3DDefaultPaint defaultPaint = new Donut3DDefaultPaint();

        if (incidenceElement != null) {
            defaultPaint.setIncidenceAngleDegree(Integer.parseInt(incidenceElement.getTextContent().trim()));
        }

        if (paintTopEffect != null) {
            defaultPaint.setPaintTopEffect(Boolean.parseBoolean(paintTopEffect.getTextContent()));
        }

        if (paintInnerEffect != null) {
            defaultPaint.setPaintInnerEffect(Boolean.parseBoolean(paintInnerEffect.getTextContent()));
        }

        if (paintOuterEffect != null) {
            defaultPaint.setPaintOuterEffect(Boolean.parseBoolean(paintOuterEffect.getTextContent()));
        }

        if (alphaFillElement != null) {
            defaultPaint.setAlphaFill(Float.parseFloat(alphaFillElement.getTextContent()));
        }

        if (alphaTopEffectElement != null) {
            defaultPaint.setAlphaTop(Float.parseFloat(alphaTopEffectElement.getTextContent()));
        }

        if (alphaInnerEffectElement != null) {
            defaultPaint.setAlphaInner(Float.parseFloat(alphaInnerEffectElement.getTextContent()));
        }

        if (alphaOuterEffectElement != null) {
            defaultPaint.setAlphaOuter(Float.parseFloat(alphaOuterEffectElement.getTextContent()));
        }

        return defaultPaint;

    }

    /**
     * defines the various type for donut3D paint
     * 
     * @author Sebastien Janaud
     */
    public enum Donut3DPaintType {
        Donut3DDefaultPaint("Donut3DDefaultPaint");

        private String donut3DPaintType;

        private Donut3DPaintType(String donut3DPaintType) {
            this.donut3DPaintType = donut3DPaintType;
        }

        /**
         * @return the donut3D Paint Type
         */
        public String getDonut3DPaintType() {
            return donut3DPaintType;
        }
    }

    /**
     * inflate donut3D paint {@link AbstractDonut3DPaint}
     * 
     * @param donut3DPaintElement
     *            the donut3D paint element to inflate
     * @return paint
     */
    private AbstractDonut3DPaint inflateDonut3DPaint(Element donut3DPaintElement) {
        String type = donut3DPaintElement.getAttribute("xsi:type");

        if (type == null) {
            return null;
        }

        if (Donut3DPaintType.Donut3DDefaultPaint.getDonut3DPaintType().equals(type)) {
            return inflateDonut3DDefaultPaint(donut3DPaintElement);
        }

        return null;
    }

    /**
     * inflate the specified label element into {@link Donut3DBorderLabel}
     * 
     * @param labelElement
     *            the label element to inflate
     * @return donut3D border slice label
     */
    private Donut3DBorderLabel inflateDonut3DSliceBorderLabel(Element labelElement) {

        Donut3DBorderLabel sliceLabel = new Donut3DBorderLabel();
        inflateDonut3DSliceLabel(sliceLabel, labelElement);

        // border slice label related properties
        String link = elementText(labelElement, ELEMENT_DONUT3D_LABEL_BORDER_LINK_ENABLE);
        String linkStyle = elementText(labelElement, ELEMENT_DONUT3D_LABEL_BORDER_LINK_STYLE);
        Element linkColorElement = (Element) labelElement.getElementsByTagName(ELEMENT_DONUT3D_LABEL_BORDER_LINK_COLOR)
                .item(0);
        Element linkStrokeElement = (Element) labelElement
                .getElementsByTagName(ELEMENT_DONUT3D_LABEL_BORDER_LINK_STROKE).item(0);
        String linkextends = elementText(labelElement, ELEMENT_DONUT3D_LABEL_BORDER_LINK_EXTENDS);
        String linkAlignment = elementText(labelElement, ELEMENT_DONUT3D_LABEL_BORDER_LINK_ALIGNMENT);
        String linkMarker = elementText(labelElement, ELEMENT_DONUT3D_LABEL_BORDER_LINK_MARKER_ENABLE);
        Element linkMarkerDrawColorElement = (Element) labelElement
                .getElementsByTagName(ELEMENT_DONUT3D_LABEL_BORDER_LINK_MARKER_DRAW).item(0);
        Element linkMarkerFillColorElement = (Element) labelElement
                .getElementsByTagName(ELEMENT_DONUT3D_LABEL_BORDER_LINK_MARKER_FILL).item(0);

        String margin = elementText(labelElement, ELEMENT_DONUT3D_LABEL_BORDER_LABEL_MARGIN);

        if (link != null && !link.equals("undefined")) {
            sliceLabel.setLink(Boolean.parseBoolean(link));
        }
        if (linkStyle != null && !linkStyle.equals("undefined")) {
            sliceLabel.setLinkStyle(LinkStyle.parseStyle(linkStyle));
        }

        if (linkAlignment != null && !linkAlignment.equals("undefined")) {
            sliceLabel.setLinkAlignment(LinkAlignment.parseLinkAlignment(linkAlignment));
        }

        if (linkextends != null && !linkextends.equals("undefined")) {
            sliceLabel.setLinkExtends(Integer.parseInt(linkextends));
        }

        sliceLabel.setLinkColor(elementColor(linkColorElement));
        sliceLabel.setLinkStroke(elementStroke(linkStrokeElement));

       
       sliceLabel.setLinkMarker(elementBoolean(labelElement, ELEMENT_DONUT3D_LABEL_BORDER_LINK_MARKER_ENABLE));
       

        sliceLabel.setLinkMarkerDrawColor(elementColor(linkMarkerDrawColorElement));
        sliceLabel.setLinkMarkerFillColor(elementColor(linkMarkerFillColorElement));

        if (margin != null && !margin.equals("undefined")) {
            sliceLabel.setMargin(Integer.parseInt(margin));
        }

        return sliceLabel;
    }

    /**
     * inflate the specified label element into {@link Donut3DRadialLabel}
     * 
     * @param labelElement
     *            the label element to parse
     * @return the slice label
     */
	private Donut3DRadialLabel inflateDonut3DSliceRadialLabel(Element labelElement) {

        Donut3DRadialLabel sliceLabel = new Donut3DRadialLabel();
        inflateDonut3DSliceLabel(sliceLabel, labelElement);

        String offsetRadius = elementText(labelElement, ELEMENT_DONUT3D_LABEL_RADIAL_OFFSET_RADIUS);

        if (offsetRadius != null && !offsetRadius.equals("undefined")) {
            sliceLabel.setOffsetRadius(Integer.parseInt(offsetRadius));
        }

        return sliceLabel;
    }

    /**
     * inflate the specified element into {@link Donut3DPathLabel}
     * 
     * @param labelElement
     *            the label element to parse
     * @return pie slice label
     */
    private Donut3DPathLabel inflateDonut3DSlicePathLabel(Element labelElement) {

        Donut3DPathLabel sliceLabel = new Donut3DPathLabel();
        inflateDonut3DSliceLabel(sliceLabel, labelElement);

        String pathdivergence = elementText(labelElement, ELEMENT_DONUT3D_LABEL_PATH_TEXT_DIVERGENCE);
        String textposition = elementText(labelElement, ELEMENT_DONUT3D_LABEL_PATH_TEXT_POSITION);
        String pathside = elementText(labelElement, ELEMENT_DONUT3D_LABEL_PATH_TEXT_SIDE);
        String pathname = elementText(labelElement, ELEMENT_DONUT3D_LABEL_PATH_SEGMENT_PATH);
        Element textShaderElement = (Element) labelElement.getElementsByTagName(ELEMENT_DONUT3D_LABEL_PATH_TEXT_SHADER)
                .item(0);

        sliceLabel.setDivergence(Donut3DPathLabel.parseDivergence(pathdivergence));
        sliceLabel.setFacetPathName(Donut3DFacetPathName.parse(pathname));
        sliceLabel.setTextPosition(TextPosition.parse(textposition));
        sliceLabel.setPathSide(PathSide.parse(pathside));
        sliceLabel.setTextShader(InflaterUtil.elementShader(textShaderElement));

        return sliceLabel;

    }

    /**
     * list of available donut3D slice label
     * 
     * @author Sebastien Janaud
     */
    public enum Donut3DLabelType {
        Donut3DRadialLabel("Donut3DRadialLabel"), Donut3DBorderLabel("Donut3DBorderLabel"), Donut3DPathLabel(
                "Donut3DPathLabel");

        private String donut3DLabelType;

        private Donut3DLabelType(String donut3DLabelType) {
            this.donut3DLabelType = donut3DLabelType;
        }

        /**
         * @return the donut3D Label Type
         */
        public String getDonut3DLabelType() {
            return donut3DLabelType;
        }

    }

    /**
     * inflate abstract label properties
     * 
     * @param labelElement
     *            the label element to inflate
     */
    private void inflateDonut3DSliceLabel(AbstractDonut3DSliceLabel abstractLabel,
            Element labelElement) {

        String text = elementText(labelElement, ELEMENT_DONUT3D_LABEL_TEXT);
        Element textColorElement = (Element) labelElement.getElementsByTagName(
                                                                               ELEMENT_DONUT3D_LABEL_TEXT_COLOR)
                .item(0);
        String font = elementText(labelElement, ELEMENT_DONUT3D_LABEL_TEXT_FONT);
        String labelPaddingX = elementText(labelElement, ELEMENT_DONUT3D_LABEL_TEXT_PADDING_X);
        String labelPaddingY = elementText(labelElement, ELEMENT_DONUT3D_LABEL_TEXT_PADDING_Y);
        String outlineRound = elementText(labelElement, ELEMENT_DONUT3D_LABEL_OUTLINE_ROUND);
        Element outlineColorElement = (Element) labelElement
                .getElementsByTagName(ELEMENT_DONUT3D_LABEL_OUTLINE_COLOR).item(0);
        Element outlineStrokeElement = (Element) labelElement
                .getElementsByTagName(ELEMENT_DONUT3D_LABEL_OUTLINE_STROKE).item(0);
        Element fillColorElement = (Element) labelElement.getElementsByTagName(
                                                                               ELEMENT_DONUT3D_LABEL_FILL_COLOR)
                .item(0);
        Element shaderElement = (Element) labelElement.getElementsByTagName(
                                                                            ELEMENT_DONUT3D_LABEL_SHADER).item(0);
        String style = elementText(labelElement, ELEMENT_DONUT3D_LABEL_STYLE);

        abstractLabel.setLabel(text);
        abstractLabel.setLabelColor(elementColor(textColorElement));
        abstractLabel.setLabelFont(InflaterUtil.parseFont(font));
        abstractLabel.setLabelPaddingX(Integer.parseInt(labelPaddingX));
        abstractLabel.setLabelPaddingY(Integer.parseInt(labelPaddingY));
        abstractLabel.setOutlineRound(Integer.parseInt(outlineRound));
        abstractLabel.setOutlineColor(elementColor(outlineColorElement));
        abstractLabel.setOutlineStroke(elementStroke(outlineStrokeElement));
        abstractLabel.setFillColor(elementColor(fillColorElement));
        abstractLabel.setShader(InflaterUtil.elementShader(shaderElement));
        abstractLabel.setStyle(Style.parseStyle(style));

    }

    /**
     * inflate specified element into {@link AbstractDonut3DSliceLabel}
     * 
     * @param labelElement
     *            the label element to inflate
     * @return donut3D slice label
     */
    private AbstractDonut3DSliceLabel inflateDonut3DSliceLabel(Element labelElement) {

        String type = labelElement.getAttribute("xsi:type");

        if (type == null) {
            return null;
        }

        if (Donut3DLabelType.Donut3DRadialLabel.getDonut3DLabelType().equals(type)) {
            return inflateDonut3DSliceRadialLabel(labelElement);
        }
        else if (Donut3DLabelType.Donut3DBorderLabel.getDonut3DLabelType().equals(type)) {
            return inflateDonut3DSliceBorderLabel(labelElement);
        }
        else if (Donut3DLabelType.Donut3DPathLabel.getDonut3DLabelType().equals(type)) {
            return inflateDonut3DSlicePathLabel(labelElement);
        }
        return null;
    }

    /**
     * inflate the specified element into {@link Donut3DSlice}
     * 
     * @param sliceElement
     *            the element to inflate
     * @return donut3D slice
     */
    private Donut3DSlice inflateDonut3DSlice(Element sliceElement) {

        String sliceName = elementText(sliceElement, ELEMENT_DONUT3D_SLICE_NAME);
        String value = elementText(sliceElement, ELEMENT_DONUT3D_SLICE_VALUE);
        String divergence = elementText(sliceElement, ELEMENT_DONUT3D_SLICE_DIVERGENCE);
        Element colorElement = (Element) sliceElement.getElementsByTagName(ELEMENT_DONUT3D_SLICE_COLOR).item(0);

        Donut3DSlice s = Donut3DToolkit.createDonut3DSlice(sliceName,
                                                           elementColor(colorElement),
                                                           Integer.parseInt(value),
                                                           Integer.parseInt(divergence));

        NodeList labelListElement = sliceElement.getElementsByTagName(ELEMENT_DONUT3D_SLICE_LABEL);

        for (int i = 0; i < labelListElement.getLength(); i++) {
            Element labelElement = (Element) labelListElement.item(i);
            AbstractDonut3DSliceLabel sliceLabel = inflateDonut3DSliceLabel(labelElement);
            if (sliceLabel != null) {
                // s.addSliceLabel(sliceLabel);
                s.addSliceLabel(sliceLabel);
            }
        }

        return s;
    }

    /**
     * inflate specified element into {@link Donut3D}
     * 
     * @param donut3DElement
     *            the element to inflate
     * @return donut3D
     */
    private Donut3D inflateDonut3D(Element donut3DElement) {

        String x = elementText(donut3DElement, ELEMENT_DONUT3D_X);
        String y = elementText(donut3DElement, ELEMENT_DONUT3D_Y);
        String innerRadius = elementText(donut3DElement, ELEMENT_DONUT3D_INNER_RADIUS);
        String outerRadius = elementText(donut3DElement, ELEMENT_DONUT3D_OUTER_RADIUS);
        String thickness = elementText(donut3DElement, ELEMENT_DONUT3D_THICKNESS);
        String tilt = elementText(donut3DElement, ELEMENT_DONUT3D_TILT);
        String startAngleDegree = elementText(donut3DElement, ELEMENT_DONUT3D_START_ANGLE);
        String nature = elementText(donut3DElement, ELEMENT_DONUT3D_NATURE);

        Donut3D donut3D = Donut3DToolkit.createDonut3D("donut3d", Integer.parseInt(innerRadius),
                                                       Integer.parseInt(outerRadius), Integer.parseInt(thickness));
        donut3D.setTilt(Integer.parseInt(tilt));
        donut3D.setCenterX(Double.parseDouble(x));
        donut3D.setCenterY(Double.parseDouble(y));
        donut3D.setStartAngleDegree(Integer.parseInt(startAngleDegree));
        donut3D.setDonut3DNature(Donut3DNature.parseNature(nature));

        NodeList slices = donut3DElement.getElementsByTagName(ELEMENT_DONUT3D_SLICE);
        for (int i = 0; i < slices.getLength(); i++) {
            Element donut3DSliceElement = (Element) slices.item(i);
            Donut3DSlice donut3DSlice = inflateDonut3DSlice(donut3DSliceElement);
            donut3D.addSlice(donut3DSlice);
        }

        Element paintElement = (Element) donut3DElement.getElementsByTagName(ELEMENT_DONUT3D_PAINT).item(0);
        AbstractDonut3DPaint paint = inflateDonut3DPaint(paintElement);
        donut3D.setDonut3DPaint(paint);

        return donut3D;

    }

    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public Donut3DPlugin inflate(Element pluginElement) {
    	Donut3DPlugin donut3dPlugin = new Donut3DPlugin();
        NodeList donut3DElementList = pluginElement.getElementsByTagName(ELEMENT_DONUT3D);
        for (int i = 0; i < donut3DElementList.getLength(); i++) {
            Element donut3DElement = (Element) donut3DElementList.item(i);
            Donut3D donut3d = inflateDonut3D(donut3DElement);
            donut3dPlugin.addDonut(donut3d);
        }
        return donut3dPlugin;
    }

}
