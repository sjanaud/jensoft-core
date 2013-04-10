/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.inflater.legend;

import java.awt.Color;
import java.awt.Font;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jensoft.core.plugin.legend.Legend;
import com.jensoft.core.plugin.legend.LegendConstraints;
import com.jensoft.core.plugin.legend.LegendConstraints.LegendAlignment;
import com.jensoft.core.plugin.legend.LegendConstraints.LegendPosition;
import com.jensoft.core.plugin.legend.LegendPlugin;
import com.jensoft.core.plugin.legend.painter.fill.LegendDefaultFill;
import com.jensoft.core.plugin.legend.painter.fill.LegendGradientFill;
import com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.inflater.X2DInflater;

/**
 * <code>LegendInflater</code>
 * 
 * @author sebastien janaud
 */
@X2DInflater(xsi="LegendPlugin")
public class LegendInflater extends AbstractX2DPluginInflater<LegendPlugin> implements X2DLegendElement {

    /**
     * create new legend inflater
     */
    public LegendInflater() {
        setPlugin(new LegendPlugin());
        setXSIType("LegendPlugin");
    }

    /**
     * inflate into {@link Legend} the given x2d legend element
     * @param element
     * @return legend
     */
    private Legend inflateLegend(Element element) {

        String text = elementText(element, ELEMENT_LEGEND_TEXT);
        Font font = elementFont(element, ELEMENT_LEGEND_TEXT_FONT);
        Color themeColor = elementColor(element, ELEMENT_LEGEND_THEME_COLOR);
        Color color1 = elementColor(element, ELEMENT_LEGEND_COLOR_1);
        Color color2 = elementColor(element, ELEMENT_LEGEND_COLOR_2);
        LegendPosition position = LegendPosition.parse(elementText(element, ELEMENT_LEGEND_POSITION));
        LegendAlignment alignment = LegendAlignment.parse(elementText(element, ELEMENT_LEGEND_ALIGNMENT));
        Float depth = elementFloat(element, ELEMENT_LEGEND_DEPTH);

        Legend legend = new Legend(text);
        getPlugin().addLegend(legend);

        if (font != null) {
            legend.setFont(font);
        }
        if (themeColor != null) {
            legend.setThemeColor(themeColor);
        }

        if (position != null && depth != null && alignment != null) {
            LegendConstraints constraints = new LegendConstraints(position, depth, alignment);
            legend.setConstraints(constraints);
        }

        if (color1 != null && color2 != null) {
            LegendGradientFill fill = new LegendGradientFill(color1, color2);
            legend.setLegendFill(fill);
        }
        else if (color1 != null) {
            LegendDefaultFill fill = new LegendDefaultFill(color1);
            legend.setLegendFill(fill);
        }
        else {
            LegendDefaultFill fill = new LegendDefaultFill();
            legend.setLegendFill(fill);
        }

        return legend;

    }

    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public void inflate(Element pluginElement) {
        LegendPlugin plugin = new LegendPlugin();
        NodeList legendElements = pluginElement.getElementsByTagName(ELEMENT_LEGEND);
        for (int i = 0; i < legendElements.getLength(); i++) {
            Element element = (Element) legendElements.item(i);
            Legend legend = inflateLegend(element);
            if(legend != null){
                System.out.println("add legend!");
                plugin.addLegend(legend);
            }
        }
        setPlugin(plugin);
    }

}
