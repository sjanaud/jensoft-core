/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.function;

import static com.jensoft.core.x2d.binding.InflaterUtil.elementColor;
import static com.jensoft.core.x2d.binding.InflaterUtil.elementDouble;
import static com.jensoft.core.x2d.binding.InflaterUtil.elementInteger;
import static com.jensoft.core.x2d.binding.InflaterUtil.elementText;
import static com.jensoft.core.x2d.binding.InflaterUtil.getType;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.w3c.dom.Element;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.GlyphMetricMarkerPainter;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.RoundMarker;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.plugin.function.source.FunctionNature;
import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.plugin.function.source.UserSourceFunction;
import com.jensoft.core.plugin.function.source.UserSourceFunction.LineSource;
import com.jensoft.core.plugin.function.source.UserSourceFunction.RegressionSource;
import com.jensoft.core.plugin.function.source.UserSourceFunction.SplineSource;


/**
 * <code>FunctionUtil</code> is an helper class to parse some common plot object
 * like {@link GlyphMetric} or {@link GlyphMetricMarkerPainter} or {@link SourceFunction}
 * 
 * @author Sebastien Janaud
 */
public class FunctionUtil implements X2DFunctionElement{

    /**
     * parse the element as a {@link GlyphMetric}
     * 
     * @param glyphElement
     *            the glyph element to parse
     * @return glyph metric
     */
    public static GlyphMetric parseGlyphMetrics(Element glyphElement) {

        Double xv = elementDouble(glyphElement, ELEMENT_GLYPH_X_VALUE);
        String text = elementText(glyphElement, ELEMENT_GLYPH_TEXT);
        Color startColor = elementColor(glyphElement, ELEMENT_GLYPH_COLOR_1);
        Color endColor = elementColor(glyphElement, ELEMENT_GLYPH_COLOR_2);
        String stl = elementText(glyphElement, ELEMENT_GLYPH_STYLE);
        Integer divergence = elementInteger(glyphElement, ELEMENT_GLYPH_DIVERGENCE);
        Element markerElement = (Element) glyphElement.getElementsByTagName("marker").item(0);

        GlyphMetric glyph = new GlyphMetric();
        glyph.setValue(xv);
        glyph.setMetricsLabel(text);

        if (startColor != null && endColor == null) {
            glyph.setGlyphMetricFill(new GlyphFill(startColor, startColor));
        }
        else if (startColor != null && endColor != null) {
            glyph.setGlyphMetricFill(new GlyphFill(startColor, endColor));
        }

        if (divergence != null) {
            glyph.setDivergence(divergence);
        }

        if (stl != null) {
            StylePosition sp = StylePosition.parse(stl);
            if (sp != null) {
                glyph.setStylePosition(sp);
            }
        }

        if (markerElement != null) {
            GlyphMetricMarkerPainter marker = inflateMarker(markerElement);
            glyph.setGlyphMetricMarkerPainter(marker);
        }

        return glyph;
    }

    /**
     * inflate the specified element as {@link GlyphMetricMarkerPainter}
     * 
     * @param markerElement
     *            the marker element to parse
     * @return the glyph marker painter
     */
    public static GlyphMetricMarkerPainter inflateMarker(Element markerElement) {
        String type = getType(markerElement);

        if (type.equals(ELEMENT_MARKER_XSI_TYPE_MARKER_ROUND)) {

            Color fillColor = elementColor(markerElement, ELEMENT_MARKER_ROUND_FILL_COLOR);
            if (fillColor != null) {
                RoundMarker roundMarker = new RoundMarker(fillColor);
                String radius = elementText(markerElement, ELEMENT_MARKER_ROUND_RADIUS);

                Color drawColor = elementColor(markerElement, ELEMENT_MARKER_ROUND_DRAW_COLOR);
                try {
                    if (radius != null && !radius.equals("undefined")) {
                        roundMarker.setRadius(Integer.parseInt(radius));
                    }
                    if (drawColor != null) {
                        roundMarker.setMarkerDrawColor(drawColor);
                    }
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                return roundMarker;
            }
        }
        else if (type.equals(ELEMENT_MARKER_XSI_TYPE_MARKER_TICTAC)) {

            
            Color themeColor = elementColor(markerElement,ELEMENT_MARKER_TICTAC_THEME_COLOR);
            if (themeColor != null) {
                TicTacMarker tictacMarker = new TicTacMarker(themeColor);
                try {
                    String s = elementText(markerElement, ELEMENT_MARKER_TICTAC_THICKNESS);
                    String d = elementText(markerElement, ELEMENT_MARKER_TICTAC_DIVERGENCE);
                    String dl = elementText(markerElement, ELEMENT_MARKER_TICTAC_DIVERGENCE_LEFT);
                    String dr = elementText(markerElement, ELEMENT_MARKER_TICTAC_DIVERGENCE_RIGHT);

                    if (s != null && !s.equals("undefined")) {
                        tictacMarker.setSize(Integer.parseInt(s));
                    }

                    if (d != null && !d.equals("undefined")) {
                        tictacMarker.setDivergence(Integer.parseInt(d));
                    }

                    if (dl != null && !dl.equals("undefined")) {
                        tictacMarker.setDivergenceLeft(Integer.parseInt(dl));
                    }

                    if (dr != null && !dr.equals("undefined")) {
                        tictacMarker.setDivergenceRight(Integer.parseInt(dr));
                    }

                }
                catch (NumberFormatException e) {

                    e.printStackTrace();
                }
                return tictacMarker;
            }
        }

        return null;
    }

    /**
     * inflate {@link LineSource}
     * 
     * @param sourceElement
     * @return source function
     */
    public static SourceFunction inflateSourceFunction(Element sourceElement) {
        if (sourceElement == null) {
            return null;
        }

        String type = getType(sourceElement);

        if (type == null) {
            return null;
        }

        SourceFunction serie = null;
        if (type.equalsIgnoreCase(ELEMENT_SOURCEFUNCTION_XSI_TYPE_AFFINE_SOURCEFUNCTION)) {
            serie = FunctionUtil.inflateLineSource(sourceElement);
        }
        if (type.equalsIgnoreCase(ELEMENT_SOURCEFUNCTION_XSI_TYPE_INTERPOLATE_SOURCEFUNCTION)) {
            serie = FunctionUtil.inflateSplineSource(sourceElement);
        }
        if (type.equalsIgnoreCase(ELEMENT_SOURCEFUNCTION_XSI_TYPE_REGRESSION_SOURCEFUNCTION)) {
            serie = FunctionUtil.inflateRegressionSource(sourceElement);
        }
        if (serie != null) {
            String id = elementText(sourceElement, ELEMENT_SOURCEFUNCTION_ID);
            String name = elementText(sourceElement, ELEMENT_SOURCEFUNCTION_NAME);
            if (id != null) {
                serie.setId(id);
            }
            if (name != null) {
                serie.setName(name);
            }
        }
        return serie;
    }

    /**
     * parse the specified element as {@link SourceFunction}
     * 
     * @param sourceElement
     *            the element to parse
     * @return the source
     */
    private static LineSource inflateLineSource(Element sourceElement) {

        String sourcex = elementText(sourceElement, ELEMENT_SOURCEFUNCTION_SOURCE_X);
        String sourcey = elementText(sourceElement, ELEMENT_SOURCEFUNCTION_SOURCE_Y);

        StringTokenizer tokenizerX = new StringTokenizer(sourcex, " ");
        List<Double> valuesAsDoubleX = new ArrayList<Double>();
        while (tokenizerX.hasMoreElements()) {
            String x = (String) tokenizerX.nextElement();
            valuesAsDoubleX.add(Double.parseDouble(x));
        }
        StringTokenizer tokenizerY = new StringTokenizer(sourcey, " ");
        List<Double> valuesAsDoubleY = new ArrayList<Double>();
        while (tokenizerY.hasMoreElements()) {
            String y = (String) tokenizerY.nextElement();
            valuesAsDoubleY.add(Double.parseDouble(y));
        }

        return new UserSourceFunction.LineSource(valuesAsDoubleX.toArray((new Double[valuesAsDoubleX.size()])),
                                                 valuesAsDoubleY.toArray(new Double[valuesAsDoubleY.size()]));


    }

    /**
     * parse the specified element as {@link SourceFunction}
     * 
     * @param sourceElement
     *            the element to parse
     * @return the source
     */
    private static SplineSource inflateSplineSource(Element sourceElement) {
        String sourcex = elementText(sourceElement, ELEMENT_SOURCEFUNCTION_SOURCE_X);
        String sourcey = elementText(sourceElement, ELEMENT_SOURCEFUNCTION_SOURCE_Y);
        Double delta = elementDouble(sourceElement, ELEMENT_SOURCEFUNCTION_INTERPOLATE_DELTA);

        StringTokenizer tokenizerX = new StringTokenizer(sourcex, " ");
        List<Double> valuesAsDoubleX = new ArrayList<Double>();
        while (tokenizerX.hasMoreElements()) {
            String x = (String) tokenizerX.nextElement();
            valuesAsDoubleX.add(Double.parseDouble(x));
        }
        StringTokenizer tokenizerY = new StringTokenizer(sourcey, " ");
        List<Double> valuesAsDoubleY = new ArrayList<Double>();
        while (tokenizerY.hasMoreElements()) {
            String y = (String) tokenizerY.nextElement();
            valuesAsDoubleY.add(Double.parseDouble(y));
        }

       
        
        return new UserSourceFunction.SplineSource(valuesAsDoubleX.toArray((new Double[valuesAsDoubleX.size()])),
                valuesAsDoubleY.toArray(new Double[valuesAsDoubleY.size()]),FunctionNature.XFunction, delta.doubleValue());
		
       
    }

    /**
     * parse the specified element as {@link SourceFunction}
     * 
     * @param sourceElement
     *            the element to parse
     * @return the source
     */
    private static RegressionSource inflateRegressionSource(Element sourceElement) {
        String sourcex = elementText(sourceElement, ELEMENT_SOURCEFUNCTION_SOURCE_X);
        String sourcey = elementText(sourceElement, ELEMENT_SOURCEFUNCTION_SOURCE_Y);
        Double delta = elementDouble(sourceElement, ELEMENT_SOURCEFUNCTION_REGRESSION_DELTA);

        StringTokenizer tokenizerX = new StringTokenizer(sourcex, " ");
        List<Double> valuesAsDoubleX = new ArrayList<Double>();
        while (tokenizerX.hasMoreElements()) {
            String x = (String) tokenizerX.nextElement();
            valuesAsDoubleX.add(Double.parseDouble(x));
        }
        StringTokenizer tokenizerY = new StringTokenizer(sourcey, " ");
        List<Double> valuesAsDoubleY = new ArrayList<Double>();
        while (tokenizerY.hasMoreElements()) {
            String y = (String) tokenizerY.nextElement();
            valuesAsDoubleY.add(Double.parseDouble(y));
        }

        return new UserSourceFunction.RegressionSource(valuesAsDoubleX.toArray((new Double[valuesAsDoubleX
                .size()])), valuesAsDoubleY.toArray(new Double[valuesAsDoubleY.size()]), delta.doubleValue());
    }

}
