/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.legend;

import java.awt.Color;
import java.awt.Font;

import org.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jensoft.core.plugin.legend.title.TitleLegend;
import com.jensoft.core.plugin.legend.title.TitleLegendConstraints;
import com.jensoft.core.plugin.legend.title.TitleLegendConstraints.LegendAlignment;
import com.jensoft.core.plugin.legend.title.TitleLegendConstraints.LegendPosition;
import com.jensoft.core.plugin.legend.title.TitleLegendPlugin;
import com.jensoft.core.plugin.legend.title.painter.fil.TitleLegendDefaultFill;
import com.jensoft.core.plugin.legend.title.painter.fil.TitleLegendGradientFill;

/**
 * <code>LegendInflater</code>
 * 
 * @author sebastien janaud
 */
@X2DBinding(xsi="LegendPlugin",plugin=TitleLegendPlugin.class)
public class LegendInflater extends AbstractX2DPluginInflater<TitleLegendPlugin> implements X2DLegendElement {


    /**
     * inflate into {@link TitleLegend} the given x2d legend element
     * @param element
     * @return legend
     */
    private TitleLegend inflateLegend(Element element) {

        String text = elementText(element, ELEMENT_LEGEND_TEXT);
        Font font = elementFont(element, ELEMENT_LEGEND_TEXT_FONT);
        Color themeColor = elementColor(element, ELEMENT_LEGEND_THEME_COLOR);
        Color color1 = elementColor(element, ELEMENT_LEGEND_COLOR_1);
        Color color2 = elementColor(element, ELEMENT_LEGEND_COLOR_2);
        LegendPosition position = LegendPosition.parse(elementText(element, ELEMENT_LEGEND_POSITION));
        LegendAlignment alignment = LegendAlignment.parse(elementText(element, ELEMENT_LEGEND_ALIGNMENT));
        Float depth = elementFloat(element, ELEMENT_LEGEND_DEPTH);

        TitleLegend legend = new TitleLegend(text);
      

        if (font != null) {
            legend.setFont(font);
        }
        if (themeColor != null) {
            legend.setThemeColor(themeColor);
        }

        if (position != null && depth != null && alignment != null) {
            TitleLegendConstraints constraints = new TitleLegendConstraints(position, depth, alignment);
            legend.setConstraints(constraints);
        }

        if (color1 != null && color2 != null) {
            TitleLegendGradientFill fill = new TitleLegendGradientFill(color1, color2);
            legend.setLegendFill(fill);
        }
        else if (color1 != null) {
            TitleLegendDefaultFill fill = new TitleLegendDefaultFill(color1);
            legend.setLegendFill(fill);
        }
        else {
            TitleLegendDefaultFill fill = new TitleLegendDefaultFill();
            legend.setLegendFill(fill);
        }

        return legend;

    }

    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public TitleLegendPlugin inflate(Element pluginElement) {
        TitleLegendPlugin plugin = new TitleLegendPlugin();
        NodeList legendElements = pluginElement.getElementsByTagName(ELEMENT_LEGEND);
        for (int i = 0; i < legendElements.getLength(); i++) {
            Element element = (Element) legendElements.item(i);
            TitleLegend legend = inflateLegend(element);
            if(legend != null){
                plugin.addLegend(legend);
            }
        }
       return plugin;
    }

}
