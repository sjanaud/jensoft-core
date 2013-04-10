/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.inflater.function;

import java.awt.Color;
import java.awt.Stroke;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.function.FunctionPlugin;
import com.jensoft.core.plugin.function.FunctionPlugin.AreaFunctionPlugin;
import com.jensoft.core.plugin.function.area.AreaFunction;
import com.jensoft.core.plugin.function.area.painter.draw.AbstractAreaDraw;
import com.jensoft.core.plugin.function.area.painter.draw.AreaDefaultDraw;
import com.jensoft.core.plugin.function.area.painter.fill.AbstractAreaFill;
import com.jensoft.core.plugin.function.area.painter.fill.AreaDefaultFill;
import com.jensoft.core.plugin.function.area.painter.fill.AreaGradientFill;
import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.inflater.X2DInflater;

/**
 * <code>AreaFunctionInflater</code>
 * 
 * @author Sebastien Janaud
 * @since 1.0
 */
@X2DInflater(xsi="AreaPlugin")
public class AreaFunctionInflater extends AbstractX2DPluginInflater<AreaFunctionPlugin> implements X2DAreaElement {

    /**
     * create area inflater
     */
    public AreaFunctionInflater() {
        setPlugin(new FunctionPlugin.AreaFunctionPlugin());
        setXSIType("AreaPlugin");
    }

   

    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public void inflate(Element pluginElement) {
        NodeList curveElementList = pluginElement.getElementsByTagName(ELEMENT_AREA_FUNCTION);
        for (int i = 0; i < curveElementList.getLength(); i++) {
            Element curveElement = (Element) curveElementList.item(i);
            AreaFunction function = inflateFunction(curveElement);
            if (function != null) {
                getPlugin().addFunction(function);
            }
        }
    }

    /**
     * inflate area draw
     * 
     * @param drawElement
     * @return area draw
     */
    private AbstractAreaDraw inflateFunctionDraw(Element drawElement) {
        if (drawElement == null) {
            return new AreaDefaultDraw();
        }

        String type = getType(drawElement);

        if (type.equals(ELEMENT_AREA_DRAW_XSI_TYPE_DEFAULT)) {

            Color curveColor = elementColor(drawElement, ELEMENT_AREA_DRAW_AREA_COLOR);
            Stroke curveStroke = elementStroke(drawElement, ELEMENT_AREA_DRAW_AREA_STROKE);
            Color baseColor = elementColor(drawElement, ELEMENT_AREA_DRAW_BASE_COLOR);
            Stroke baseStroke = elementStroke(drawElement, ELEMENT_AREA_DRAW_BASE_STROKE);

            AreaDefaultDraw draw = new AreaDefaultDraw(curveColor, curveStroke, baseColor, baseStroke);
            return draw;

        }

        return new AreaDefaultDraw();
    }

    /**
     * inflate area fill
     * 
     * @param fillElement
     * @return area fill
     */
    private AbstractAreaFill inflateFunctionFill(Element fillElement) {
        if (fillElement == null) {
            return new AreaDefaultFill();
        }

        String type = getType(fillElement);

        if (type.equals(ELEMENT_AREA_FILL_XSI_TYPE_DEFAULT)) {
            Color fillColor = elementColor(fillElement, ELEMENT_AREA_FILL_DEFAULT_FILL_COLOR);
            return new AreaDefaultFill(fillColor);
        }
        else if (type.equals(ELEMENT_AREA_FILL_XSI_TYPE_GRADIENT)) {
            Color fillColor1 = elementColor(fillElement, ELEMENT_AREA_GRADIENT_FILL_COLOR_1);
            Color fillColor2 = elementColor(fillElement, ELEMENT_AREA_GRADIENT_FILL_COLOR_2);
            Shader shader = elementShader(fillElement, ELEMENT_AREA_GRADIENT_FILL_SHADER);

            AreaGradientFill gradient = new AreaGradientFill();
            //if (fillColor1 != null && fillColor2 == null) {
                gradient.setColor1(fillColor1);
                gradient.setColor2(fillColor2);
                if (shader != null) {
                    gradient.setShader(shader);
                }
           // }
            return gradient;

        }
        return new AreaDefaultFill();
    }

    /**
     * inflate {@link AreaFunction}
     * 
     * @param areaFunctionElement
     *            the area function element to inflate
     * @return area function
     */
    private AreaFunction inflateFunction(Element areaFunctionElement) {

        Element sourceFunctionElement = (Element) areaFunctionElement.getElementsByTagName(ELEMENT_SOURCE_FUNCTION).item(0);
        SourceFunction sourceFunction = FunctionUtil.inflateSourceFunction(sourceFunctionElement);
        if (sourceFunction == null) {
            return null;
        }
        AreaFunction areaFunction = new AreaFunction(sourceFunction);

        String name = elementText(areaFunctionElement, ELEMENT_AREA_NAME);
        areaFunction.setName(name);

        Double areaBase = elementDouble(areaFunctionElement, ELEMENT_AREA_BASE);
        areaFunction.setAreaBase(areaBase);

        Color c = elementColor(areaFunctionElement, ELEMENT_AREA_THEME_COLOR);
        areaFunction.setThemeColor(c);

        // draw
        Element drawElement = (Element) areaFunctionElement.getElementsByTagName(ELEMENT_AREA_DRAW).item(0);
        AbstractAreaDraw areaDraw = inflateFunctionDraw(drawElement);
        areaFunction.setAreaDraw(areaDraw);

        // fill
        Element fillElement = (Element) areaFunctionElement.getElementsByTagName(ELEMENT_AREA_FILL).item(0);
        AbstractAreaFill areaFill = inflateFunctionFill(fillElement);
        areaFunction.setAreaFill(areaFill);

        // glyph
        NodeList glyphElements = areaFunctionElement.getElementsByTagName(ELEMENT_GLYPH);
        for (int i = 0; i < glyphElements.getLength(); i++) {
            Element element = (Element) glyphElements.item(i);
            GlyphMetric glyphMetrics = FunctionUtil.parseGlyphMetrics(element);
            if (glyphMetrics != null) {
                areaFunction.addMetricsLabel(glyphMetrics);
            }
        }
        return areaFunction;
    }

}
