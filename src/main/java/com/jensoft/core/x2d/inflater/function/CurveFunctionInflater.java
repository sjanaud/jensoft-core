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
import com.jensoft.core.plugin.function.FunctionPlugin;
import com.jensoft.core.plugin.function.FunctionPlugin.CurveFunctionPlugin;
import com.jensoft.core.plugin.function.curve.CurveFunction;
import com.jensoft.core.plugin.function.curve.painter.draw.AbstractCurveDraw;
import com.jensoft.core.plugin.function.curve.painter.draw.CurveDefaultDraw;
import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.inflater.X2DInflater;

/**
 * <code>CurveInflater</code> takes the responsibility to inflates X2D curve
 * 
 * @author Sebastien Janaud
 */
@X2DInflater(xsi="CurvePlugin")
public class CurveFunctionInflater extends AbstractX2DPluginInflater<CurveFunctionPlugin> implements X2DCurveElement {

    /**
     * create curve inflater
     */
    public CurveFunctionInflater() {
        setPlugin(new FunctionPlugin.CurveFunctionPlugin());
        setXSIType("CurvePlugin");
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public void inflate(Element pluginElement) {
        NodeList curveElementList = pluginElement.getElementsByTagName(ELEMENT_CURVE_FUNCTION);
        for (int i = 0; i < curveElementList.getLength(); i++) {
            Element curveElement = (Element) curveElementList.item(i);
            CurveFunction function = inflateFunction(curveElement);
            if (function != null) {
                getPlugin().addFunction(function);
            }
        }
    }

    /**
     * inflate curve function draw
     * 
     * @param drawElement
     * @return area draw
     */
    private AbstractCurveDraw inflateFunctionDraw(Element drawElement) {
        if (drawElement == null) {
            return new CurveDefaultDraw();
        }

        String type = getType(drawElement);

        if (type.equals(ELEMENT_CURVE_DRAW_XSI_TYPE_DEFAULT)) {

            Color curveColor = elementColor(drawElement, ELEMENT_CURVE_DRAW_DEFAULT_AREA_COLOR);
            Stroke curveStroke = elementStroke(drawElement, ELEMENT_CURVE_DRAW_DEFAULT_AREA_STROKE);

            CurveDefaultDraw draw = new CurveDefaultDraw(curveColor, curveStroke);
            return draw;

        }

        return new CurveDefaultDraw();
    }

    /**
     * inflate {@link CurveFunction}
     * 
     * @param curveFunctionElement
     *            the curve function element to inflate
     * @return curve function
     */
    private CurveFunction inflateFunction(Element curveFunctionElement) {

        Element sourceFunctionElement = (Element) curveFunctionElement.getElementsByTagName(ELEMENT_SOURCE_FUNCTION)
                .item(0);
        SourceFunction sourceFunction = FunctionUtil.inflateSourceFunction(sourceFunctionElement);
        if (sourceFunction == null) {
            return null;
        }
        CurveFunction areaFunction = new CurveFunction(sourceFunction);

        String name = elementText(curveFunctionElement, ELEMENT_CURVE_NAME);
        areaFunction.setName(name);

        Color c = elementColor(curveFunctionElement, ELEMENT_CURVE_THEME_COLOR);
        areaFunction.setThemeColor(c);

        // draw
        Element drawElement = (Element) curveFunctionElement.getElementsByTagName(ELEMENT_CURVE_DRAW).item(0);
        AbstractCurveDraw curveDraw = inflateFunctionDraw(drawElement);
        areaFunction.setCurveDraw(curveDraw);

        // glyph
        NodeList glyphElements = curveFunctionElement.getElementsByTagName(ELEMENT_GLYPH);
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
