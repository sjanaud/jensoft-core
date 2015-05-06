/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.function;
import java.awt.Color;

import org.jensoft.core.plugin.function.FunctionPlugin;
import org.jensoft.core.plugin.function.FunctionPlugin.ScatterFunction;
import org.jensoft.core.plugin.function.scatter.Scatter;
import org.jensoft.core.plugin.function.scatter.morphe.EllipseMorphe;
import org.jensoft.core.plugin.function.scatter.morphe.PolygonMorphe;
import org.jensoft.core.plugin.function.scatter.morphe.QInverseMorphe;
import org.jensoft.core.plugin.function.scatter.morphe.RectangleMorphe;
import org.jensoft.core.plugin.function.scatter.morphe.StarMorphe;
import org.jensoft.core.plugin.function.scatter.painter.fill.ScatterDefaultFill;
import org.jensoft.core.plugin.function.source.SourceFunction;
import org.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>ScatterCurveInflater</code>
 * 
 * @author Sebastien Janaud
 */
@X2DBinding(xsi="ScatterPlugin",plugin=ScatterFunction.class)
public class ScatterFunctionInflater extends AbstractX2DPluginInflater<ScatterFunction> {


    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public FunctionPlugin.ScatterFunction inflate(Element paramsElement) {
    	FunctionPlugin.ScatterFunction scatter = new ScatterFunction();
        Element curvesElement = (Element) paramsElement.getElementsByTagName("curves").item(0);
        NodeList curveElements = curvesElement.getElementsByTagName("curve");
        for (int i = 0; i < curveElements.getLength(); i++) {
            Element labelElement = (Element) curveElements.item(i);
            Scatter curve = inflateCurve(labelElement);
            scatter.addFunction(curve);
        }
        return scatter;
    }

    /**
     * inflate {@link Scatter}
     * 
     * @param curveElement
     *            the curve element to inflate
     * @return scatter curve
     */
    private Scatter inflateCurve(Element curveElement) {

        Element serieElement = (Element) curveElement.getElementsByTagName("serie2d").item(0);
        SourceFunction serie2D = FunctionUtil.inflateSourceFunction(serieElement);

        Scatter curve = new Scatter(serie2D);

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
