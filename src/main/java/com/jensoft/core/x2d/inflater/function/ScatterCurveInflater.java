/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.inflater.function;
import java.awt.Color;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jensoft.core.plugin.function.FunctionPlugin.ScatterFunctionPlugin;
import com.jensoft.core.plugin.function.scatter.ScatterFunction;
import com.jensoft.core.plugin.function.scatter.morphe.EllipseMorphe;
import com.jensoft.core.plugin.function.scatter.morphe.PolygonMorphe;
import com.jensoft.core.plugin.function.scatter.morphe.QInverseMorphe;
import com.jensoft.core.plugin.function.scatter.morphe.RectangleMorphe;
import com.jensoft.core.plugin.function.scatter.morphe.StarMorphe;
import com.jensoft.core.plugin.function.scatter.painter.fill.ScatterDefaultFill;
import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.inflater.X2DInflater;

/**
 * <code>ScatterCurveInflater</code>
 * 
 * @author Sebastien Janaud
 */
@X2DInflater(xsi="ScatterPlugin")
public class ScatterCurveInflater extends AbstractX2DPluginInflater<ScatterFunctionPlugin> {

    /**
     * create scatter curve inflater
     */
    public ScatterCurveInflater() {
        setPlugin(new ScatterFunctionPlugin());
        setXSIType("ScatterPlugin");
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.jet.inflater.AbstractPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public void inflate(Element paramsElement) {
        Element curvesElement = (Element) paramsElement.getElementsByTagName("curves").item(0);
        NodeList curveElements = curvesElement.getElementsByTagName("curve");
        for (int i = 0; i < curveElements.getLength(); i++) {
            Element labelElement = (Element) curveElements.item(i);
            ScatterFunction curve = inflateCurve(labelElement);
            getPlugin().addFunction(curve);
        }
    }

    /**
     * inflate {@link ScatterFunction}
     * 
     * @param curveElement
     *            the curve element to inflate
     * @return scatter curve
     */
    private ScatterFunction inflateCurve(Element curveElement) {

        Element serieElement = (Element) curveElement.getElementsByTagName("serie2d").item(0);
        SourceFunction serie2D = FunctionUtil.inflateSourceFunction(serieElement);

        ScatterFunction curve = new ScatterFunction(serie2D);

        curve.setScatterFill(new ScatterDefaultFill());

        Element tc = (Element) curveElement.getElementsByTagName("themecolor").item(0);
        Color c = elementColor(tc);
        if (c != null) {
            curve.setThemeColor(c);
        }

        String morphe = elementText(curveElement, "morphe");
        if (morphe != null && !morphe.equals("undefined")) {

            // ellipse,polygon,qinverse,qstar,rectangle,star
            if (morphe.equals("ellipse")) {
                curve.setScatterMorphe(new EllipseMorphe(8, 8));
            }
            else if (morphe.equals("polygon")) {
                curve.setScatterMorphe(new PolygonMorphe(5, 6));
            }
            else if (morphe.equals("qinverse")) {
                curve.setScatterMorphe(new QInverseMorphe(5, 3));
            }
            else if (morphe.equals("rectangle")) {
                curve.setScatterMorphe(new RectangleMorphe(4, 4));
            }
            else if (morphe.equals("star")) {
                curve.setScatterMorphe(new StarMorphe(3, 6, 5));
            }

        }
        else {
            curve.setScatterMorphe(new QInverseMorphe(5, 3));
        }

        return curve;
    }

   

}
