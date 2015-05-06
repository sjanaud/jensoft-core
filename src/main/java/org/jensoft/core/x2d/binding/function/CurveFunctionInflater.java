/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.function;

import java.awt.Color;
import java.awt.Stroke;

import org.jensoft.core.glyphmetrics.GlyphMetric;
import org.jensoft.core.plugin.function.FunctionPlugin;
import org.jensoft.core.plugin.function.FunctionPlugin.AreaFunction;
import org.jensoft.core.plugin.function.FunctionPlugin.CurveFunction;
import org.jensoft.core.plugin.function.curve.Curve;
import org.jensoft.core.plugin.function.curve.painter.draw.AbstractCurveDraw;
import org.jensoft.core.plugin.function.curve.painter.draw.CurveDefaultDraw;
import org.jensoft.core.plugin.function.source.SourceFunction;
import org.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>CurveInflater</code> takes the responsibility to inflates X2D curve
 * 
 * @author Sebastien Janaud
 */
@X2DBinding(xsi="CurvePlugin",plugin=AreaFunction.class)
public class CurveFunctionInflater extends AbstractX2DPluginInflater<CurveFunction> implements X2DCurveElement {

    
    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public FunctionPlugin.CurveFunction inflate(Element pluginElement) {
    	FunctionPlugin.CurveFunction curve = new FunctionPlugin.CurveFunction();
        NodeList curveElementList = pluginElement.getElementsByTagName(ELEMENT_CURVE_FUNCTION);
        for (int i = 0; i < curveElementList.getLength(); i++) {
            Element curveElement = (Element) curveElementList.item(i);
            Curve function = inflateFunction(curveElement);
            if (function != null) {
            	curve.addFunction(function);
            }
        }
        return curve;
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
     * inflate {@link Curve}
     * 
     * @param curveFunctionElement
     *            the curve function element to inflate
     * @return curve function
     */
    private Curve inflateFunction(Element curveFunctionElement) {

        Element sourceFunctionElement = (Element) curveFunctionElement.getElementsByTagName(ELEMENT_SOURCE_FUNCTION)
                .item(0);
        SourceFunction sourceFunction = FunctionUtil.inflateSourceFunction(sourceFunctionElement);
        if (sourceFunction == null) {
            return null;
        }
        Curve areaFunction = new Curve(sourceFunction);

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
