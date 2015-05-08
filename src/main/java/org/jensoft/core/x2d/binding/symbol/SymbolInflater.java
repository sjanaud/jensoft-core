/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.symbol;
import java.awt.Color;
import java.awt.Stroke;

import org.jensoft.core.graphics.Shader;
import org.jensoft.core.plugin.symbol.BarSymbol;
import org.jensoft.core.plugin.symbol.BarSymbolGroup;
import org.jensoft.core.plugin.symbol.BarSymbolLayer;
import org.jensoft.core.plugin.symbol.Stack;
import org.jensoft.core.plugin.symbol.StackedBarSymbol;
import org.jensoft.core.plugin.symbol.SymbolComponent;
import org.jensoft.core.plugin.symbol.SymbolPlugin;
import org.jensoft.core.plugin.symbol.SymbolToolkit;
import org.jensoft.core.plugin.symbol.BarSymbol.MorpheStyle;
import org.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;
import org.jensoft.core.plugin.symbol.painter.axis.AbstractBarAxisLabel;
import org.jensoft.core.plugin.symbol.painter.axis.BarDefaultAxisLabel;
import org.jensoft.core.plugin.symbol.painter.draw.BarDefaultDraw;
import org.jensoft.core.plugin.symbol.painter.effect.BarEffect1;
import org.jensoft.core.plugin.symbol.painter.effect.BarEffect2;
import org.jensoft.core.plugin.symbol.painter.effect.BarEffect3;
import org.jensoft.core.plugin.symbol.painter.effect.BarEffect4;
import org.jensoft.core.plugin.symbol.painter.fill.BarDefaultFill;
import org.jensoft.core.plugin.symbol.painter.fill.BarFill1;
import org.jensoft.core.plugin.symbol.painter.fill.BarFill2;
import org.jensoft.core.plugin.symbol.painter.label.BarSymbolRelativeLabel;
import org.jensoft.core.plugin.symbol.painter.label.BarSymbolRelativeLabel.HorizontalAlignment;
import org.jensoft.core.plugin.symbol.painter.label.BarSymbolRelativeLabel.VerticalAlignment;
import org.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import org.jensoft.core.x2d.binding.InflaterUtil;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>SymbolInflater<code>
 * 
 * @author Sebastien Janaud
 */
@X2DBinding(xsi="SymbolPlugin",plugin=SymbolPlugin.class)
public class SymbolInflater extends AbstractX2DPluginInflater<SymbolPlugin> {


    /* (non-Javadoc)
     * @see org.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public SymbolPlugin inflate(Element paramsElement) {
    	SymbolPlugin symbolPlugin = new SymbolPlugin();
        String nature = elementText(paramsElement, "nature");
        SymbolNature barnature = SymbolNature.parse(nature);
        if (barnature == null) {
            return null;
        }

        symbolPlugin.setNature(barnature);
        Element layersElement = (Element) paramsElement.getElementsByTagName("layers");

        NodeList layerElements = layersElement.getElementsByTagName("layer");
        for (int i = 0; i < layerElements.getLength(); i++) {
            Element layerElement = (Element) layerElements.item(i);
            String type = elementText(layerElement, "type");
            if (type.equals("BarSymbolLayer")) {
            	symbolPlugin.addLayer(inflateBarSymbolLayer(layerElement));
            }
            else if (type.equals("PointSymbolLayer")) {
                // getPlugin().addLayer(inflateBarSymbolLayer(layerRootElement));
            }
        }
        return symbolPlugin;
    }

    /**
     * inflate bar symbol layer {@link BarSymbolLayer}
     * 
     * @param layerElement
     *            the bar symbol layer elemnt to inflate
     * @return bar symbol layer
     */
    public BarSymbolLayer inflateBarSymbolLayer(Element layerElement) {
        BarSymbolLayer layer = new BarSymbolLayer();
        Element symbolsElement = (Element) layerElement.getElementsByTagName("symbols").item(0);
        NodeList symbolElements = symbolsElement.getElementsByTagName("symbol");
        for (int i = 0; i < symbolElements.getLength(); i++) {
            Element symbolRootElement = (Element) symbolElements.item(i);
            String type = elementText(symbolRootElement, "type");
            if (type.equals("bar")) {
                layer.addSymbol(inflateBar(symbolRootElement));
            }
            else if (type.equals("stackedbar")) {
                layer.addSymbol(inflateStackedBar(symbolRootElement));
            }
            else if (type.equals("glue")) {
                layer.addSymbol(SymbolComponent.createGlue(BarSymbol.class));
            }
            else if (type.equals("strut")) {
                String strutValue = elementText(symbolRootElement, "value");
                BarSymbol strut = SymbolComponent.createStrut(BarSymbol.class,
                                                              Double.parseDouble(strutValue));
                layer.addSymbol(strut);
            }
            else if (type.equals("group")) {
                layer.addSymbol(inflateBarGroup(symbolRootElement));
            }
        }

        return layer;
    }

    /**
     * inflate bar symbol group {@link BarSymbolGroup}
     * 
     * @param symbolElement
     *            the symbol element to inflate
     * @return bar group
     */
    public BarSymbolGroup inflateBarGroup(Element symbolElement) {

        String name = elementText(symbolElement, "name");
        Element themecolor = (Element) symbolElement.getElementsByTagName("themecolor").item(0);

        String thickness = elementText(symbolElement, "thickness");
        String base = elementText(symbolElement, "base");
        String morphestyle = elementText(symbolElement, "morphestyle");
        String morpheround = elementText(symbolElement, "morpheround");
        Element draw = (Element) symbolElement.getElementsByTagName("draw").item(0);
        Element fill = (Element) symbolElement.getElementsByTagName("fill").item(0);
        Element effect = (Element) symbolElement.getElementsByTagName("effect").item(0);
        NodeList labelElements = symbolElement.getElementsByTagName("label");

        BarSymbolGroup barGroup = new BarSymbolGroup(name);
        if (themecolor != null && !themecolor.equals("undefined")) {
            barGroup.setThemeColor(elementColor(themecolor));
        }
        if (thickness != null && !thickness.equals("undefined")) {
            barGroup.setThickness(Double.parseDouble(thickness));
        }
        if (base != null && !base.equals("undefined")) {
            barGroup.setBase(Double.parseDouble(base));
        }

        if (morphestyle != null && !morphestyle.equals("undefined")) {
            if (morphestyle.equals("round")) {
                barGroup.setMorpheStyle(MorpheStyle.Round);
                barGroup.setRound(Integer.parseInt(morpheround));
            }
        }
        if (draw != null && !draw.equals("undefined")) {
            barGroup.setBarDraw(new BarDefaultDraw(elementColor(draw)));
        }
        else {
            barGroup.setBarDraw(new BarDefaultDraw());
        }

        if (fill != null && !fill.equals("undefined")) {
            if (fill.equals("fill1")) {
                barGroup.setBarFill(new BarFill1());
            }
            if (fill.equals("fill2")) {
                barGroup.setBarFill(new BarFill2());
            }
        }
        else {
            barGroup.setBarFill(new BarDefaultFill());
        }

        if (effect != null && !effect.equals("undefined")) {
            if (effect.equals("fx1")) {
                barGroup.setBarEffect(new BarEffect1());
            }
            if (effect.equals("fx2")) {
                barGroup.setBarEffect(new BarEffect2());
            }
            if (effect.equals("fx3")) {
                barGroup.setBarEffect(new BarEffect3());
            }
            if (effect.equals("fx4")) {
                barGroup.setBarEffect(new BarEffect4());
            }
        }

        for (int i = 0; i < labelElements.getLength(); i++) {
            Element element = (Element) labelElements.item(i);
            String type = elementText(element, "type");
            if (type.equals("relative")) {
                BarSymbolRelativeLabel label = inflateRelativeLabel(element);
                barGroup.setBarLabel(label);
            }
            if (type.equals("axis")) {
                AbstractBarAxisLabel label = inflateAxisLabel(element);
                barGroup.setAxisLabel(label);
            }
        }

        Element childrenElement = (Element) symbolElement.getElementsByTagName("children").item(0);

        NodeList symbolElements = childrenElement.getElementsByTagName("symbol");
        for (int i = 0; i < symbolElements.getLength(); i++) {
            Element element = (Element) symbolElements.item(i);
            String type = elementText(element, "type");
            if (type.equals("bar")) {
                barGroup.addSymbol(inflateBar(element));
            }
            else if (type.equals("stackedbar")) {
                barGroup.addSymbol(inflateStackedBar(element));
            }
            else if (type.equals("strut")) {
                String strutValue = elementText(element, "value");
                BarSymbol strut = SymbolComponent.createStrut(BarSymbol.class,
                                                              Double.parseDouble(strutValue));
                barGroup.addSymbol(strut);
            }
            else if (type.equals("group")) {
                barGroup.addSymbol(inflateBarGroup(element));
            }
        }

        return barGroup;
    }

    /**
     * inflate bar symbol {@link BarSymbol}
     * 
     * @param symbolElement
     *            the symbol element to inflate
     * @return bar symbol
     */
    public BarSymbol inflateBar(Element symbolElement) {
        String name = elementText(symbolElement, "name");
        Element themecolor = (Element) symbolElement.getElementsByTagName("themecolor").item(0);
        String thickness = elementText(symbolElement, "thickness");
        String base = elementText(symbolElement, "base");
        String value = elementText(symbolElement, "value");
        String inflate = elementText(symbolElement, "inflate");
        String morphestyle = elementText(symbolElement, "morphestyle");
        String morpheround = elementText(symbolElement, "morpheround");
        Element draw = (Element) symbolElement.getElementsByTagName("draw").item(0);
        Element fill = (Element) symbolElement.getElementsByTagName("fill").item(0);
        Element effect = (Element) symbolElement.getElementsByTagName("effect").item(0);
        NodeList labelElements = symbolElement.getElementsByTagName("label");

        BarSymbol bar = new BarSymbol(name);
        // getPlugin().addBarComponent(bar);
        if (themecolor != null && !themecolor.equals("undefined")) {
            bar.setThemeColor(elementColor(themecolor));
        }
        if (thickness != null && !thickness.equals("undefined")) {
            bar.setThickness(Double.parseDouble(thickness));
        }
        if (base != null && !base.equals("undefined")) {
            bar.setBase(Double.parseDouble(base));
        }
        if (inflate != null && !inflate.equals("undefined")) {

            if (inflate.equals("ascent")) {
                bar.setAscentValue(Double.parseDouble(value));
            }
            else if (inflate.equals("descent")) {
                bar.setDescentValue(Double.parseDouble(value));
            }
        }
        if (morphestyle != null && !morphestyle.equals("undefined")) {
            if (morphestyle.equals("round")) {
                bar.setMorpheStyle(MorpheStyle.Round);
                bar.setRound(Integer.parseInt(morpheround));
            }
        }
        if (draw != null && !draw.equals("undefined")) {
            bar.setBarDraw(new BarDefaultDraw(elementColor(draw)));
        }
        else {
            bar.setBarDraw(new BarDefaultDraw());
        }

        if (fill != null && !fill.equals("undefined")) {
            if (fill.equals("fill1")) {
                bar.setBarFill(new BarFill1());
            }
            if (fill.equals("fill2")) {
                bar.setBarFill(new BarFill2());
            }
        }
        else {
            bar.setBarFill(new BarDefaultFill());
        }

        if (effect != null && !effect.equals("undefined")) {
            if (effect.equals("fx1")) {
                bar.setBarEffect(new BarEffect1());
            }
            if (effect.equals("fx2")) {
                bar.setBarEffect(new BarEffect2());
            }
            if (effect.equals("fx3")) {
                bar.setBarEffect(new BarEffect3());
            }
            if (effect.equals("fx4")) {
                bar.setBarEffect(new BarEffect4());
            }
        }

        for (int i = 0; i < labelElements.getLength(); i++) {
            Element element = (Element) labelElements.item(i);
            String type = elementText(element, "type");
            if (type.equals("relative")) {
                BarSymbolRelativeLabel label = inflateRelativeLabel(element);
                bar.setBarLabel(label);
            }
            if (type.equals("axis")) {
                AbstractBarAxisLabel label = inflateAxisLabel(element);
                bar.setAxisLabel(label);
            }
        }

        return bar;
    }

    /**
     * inflate bar symbol relative label {@link BarSymbolRelativeLabel}
     * 
     * @param relativeLabel
     *            the relative label element to inflate
     * @return bar symbol relative label
     */
    public BarSymbolRelativeLabel inflateRelativeLabel(Element relativeLabel) {
        BarSymbolRelativeLabel label = new BarSymbolRelativeLabel();
        try {
            String text = elementText(relativeLabel, "text");
            String va = elementText(relativeLabel, "valign");
            String ha = elementText(relativeLabel, "halign");
            Element tc = (Element) relativeLabel.getElementsByTagName("textcolor").item(0);
            Element dc = (Element) relativeLabel.getElementsByTagName("drawcolor").item(0);
            Element fc = (Element) relativeLabel.getElementsByTagName("fillcolor").item(0);
            String a = elementText(relativeLabel, "alpha");
            String or = elementText(relativeLabel, "outlineround");

            Color textColor = elementColor(tc);
            Color drawColor = elementColor(dc);
            Color fillColor = elementColor(fc);

            String ox = elementText(relativeLabel, "offsetX");
            String oy = elementText(relativeLabel, "offsetY");
            String tpx = elementText(relativeLabel, "textpaddingX");
            String tpy = elementText(relativeLabel, "textpaddingY");
            label.setText(text);
            label.setOffsetX(Integer.parseInt(ox));
            label.setOffsetY(Integer.parseInt(oy));
            label.setTextPaddingX(Integer.parseInt(tpx));
            label.setTextPaddingY(Integer.parseInt(tpy));

            label.setAlpha(Float.parseFloat(a));
            label.setOutlineRound(Integer.parseInt(or));

            if (textColor != null) {
                label.setTextColor(textColor);
            }
            if (drawColor != null) {
                label.setDrawColor(drawColor);
            }
            if (fillColor != null) {
                label.setFillColor(fillColor);
            }

            Element strokeElement = (Element) relativeLabel.getElementsByTagName("stroke").item(0);
            if (strokeElement != null) {
                Stroke outlineStroke = InflaterUtil.elementStroke(strokeElement);
                label.setOutlineStroke(outlineStroke);
            }

            Element shaderElement = (Element) relativeLabel.getElementsByTagName("shader").item(0);
            if (shaderElement != null) {
                Shader shader = InflaterUtil.elementShader(shaderElement);
                label.setShader(shader);
            }

            if (va != null) {
                VerticalAlignment vertAl = VerticalAlignment.parse(va);
                if (vertAl != null) {
                    label.setVerticalAlignment(vertAl);
                }
            }

            if (ha != null) {
                HorizontalAlignment horiAl = HorizontalAlignment.parse(ha);
                if (horiAl != null) {
                    label.setHorizontalAlignment(horiAl);
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return label;
    }

    /**
     * inflate bar symbol axis label {@link AbstractBarAxisLabel}
     * 
     * @param axisLabel
     *            the bar symbol axis label element to inflate
     * @return bar axis label
     */
    public AbstractBarAxisLabel inflateAxisLabel(Element axisLabel) {
        BarDefaultAxisLabel label = new BarDefaultAxisLabel();
        try {
            String text = elementText(axisLabel, "text");

            Element tc = (Element) axisLabel.getElementsByTagName("textcolor").item(0);
            Element dc = (Element) axisLabel.getElementsByTagName("drawcolor").item(0);
            Element fc = (Element) axisLabel.getElementsByTagName("fillcolor").item(0);

            String a = elementText(axisLabel, "alpha");
            String or = elementText(axisLabel, "outlineround");

            Color textColor = elementColor(tc);
            Color drawColor = elementColor(dc);
            Color fillColor = elementColor(fc);

            label.setText(text);

            label.setAlpha(Float.parseFloat(a));
            label.setOutlineRound(Integer.parseInt(or));
            String ox = elementText(axisLabel, "offsetX");
            String oy = elementText(axisLabel, "offsetY");
            String tpx = elementText(axisLabel, "textpaddingX");
            String tpy = elementText(axisLabel, "textpaddingY");

            label.setOffsetX(Integer.parseInt(ox));
            label.setOffsetY(Integer.parseInt(oy));
            label.setTextPaddingX(Integer.parseInt(tpx));
            label.setTextPaddingY(Integer.parseInt(tpy));
            if (textColor != null) {
                label.setTextColor(textColor);
            }
            if (drawColor != null) {
                label.setDrawColor(drawColor);
            }
            if (fillColor != null) {
                label.setFillColor(fillColor);
            }

            Element strokeElement = (Element) axisLabel.getElementsByTagName("stroke").item(0);
            if (strokeElement != null) {
                Stroke outlineStroke = InflaterUtil.elementStroke(strokeElement);
                label.setOutlineStroke(outlineStroke);
            }

            Element shaderElement = (Element) axisLabel.getElementsByTagName("shader").item(0);
            if (shaderElement != null) {
                Shader shader = InflaterUtil.elementShader(shaderElement);
                label.setShader(shader);
            }

        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return label;
    }

    /**
     * inflate stacked bar symbol {@link StackedBarSymbol}
     * 
     * @param symbolElement
     *            the symbol element to inflate
     * @return stacked bar
     */
    public StackedBarSymbol inflateStackedBar(Element symbolElement) {
        String name = elementText(symbolElement, "name");
        Element themecolor = (Element) symbolElement.getElementsByTagName("themecolor").item(0);
        String thickness = elementText(symbolElement, "thickness");
        String base = elementText(symbolElement, "base");
        String value = elementText(symbolElement, "value");
        String inflate = elementText(symbolElement, "inflate");
        String morphestyle = elementText(symbolElement, "morphestyle");
        String morpheround = elementText(symbolElement, "morpheround");
        Element draw = (Element) symbolElement.getElementsByTagName("draw").item(0);
        Element fill = (Element) symbolElement.getElementsByTagName("fill").item(0);
        Element effect = (Element) symbolElement.getElementsByTagName("effect").item(0);

        NodeList labelElements = symbolElement.getElementsByTagName("label");

        StackedBarSymbol bar = new StackedBarSymbol(name);
        if (themecolor != null && !themecolor.equals("undefined")) {
            bar.setThemeColor(elementColor(themecolor));
        }
        if (thickness != null && !thickness.equals("undefined")) {
            bar.setThickness(Double.parseDouble(thickness));
        }
        if (base != null && !base.equals("undefined")) {
            bar.setBase(Double.parseDouble(base));
        }
        if (inflate != null && !inflate.equals("undefined")) {

            if (inflate.equals("ascent")) {
                bar.setAscentValue(Double.parseDouble(value));
            }
            else if (inflate.equals("descent")) {
                bar.setDescentValue(Double.parseDouble(value));
            }
        }
        if (morphestyle != null && !morphestyle.equals("undefined")) {
            if (morphestyle.equals("round")) {
                bar.setMorpheStyle(MorpheStyle.Round);
                bar.setRound(Integer.parseInt(morpheround));
            }
        }
        if (draw != null && !draw.equals("undefined")) {
            try {
                bar.setBarDraw(new BarDefaultDraw(elementColor(draw)));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            bar.setBarDraw(new BarDefaultDraw());
        }
        if (fill != null && !fill.equals("undefined")) {
            if (fill.equals("fill1")) {
                bar.setBarFill(new BarFill1());
            }
            if (fill.equals("fill2")) {
                bar.setBarFill(new BarFill2());
            }
        }
        else {
            bar.setBarFill(new BarDefaultFill());
        }
        if (effect != null && !effect.equals("undefined")) {
            if (effect.equals("fx1")) {
                bar.setBarEffect(new BarEffect1());
            }
            if (effect.equals("fx2")) {
                bar.setBarEffect(new BarEffect2());
            }
            if (effect.equals("fx3")) {
                bar.setBarEffect(new BarEffect3());
            }
            if (effect.equals("fx4")) {
                bar.setBarEffect(new BarEffect4());
            }

        }

        for (int i = 0; i < labelElements.getLength(); i++) {
            Element element = (Element) labelElements.item(i);
            String type = elementText(element, "type");
            if (type.equals("relative")) {
                BarSymbolRelativeLabel label = inflateRelativeLabel(element);
                bar.setBarLabel(label);
            }
            if (type.equals("axis")) {
                AbstractBarAxisLabel label = inflateAxisLabel(element);
                bar.setAxisLabel(label);
            }
        }

        // create stacks!
        Element stacksElement = (Element) symbolElement.getElementsByTagName("stacks").item(0);
        NodeList stacks = stacksElement.getElementsByTagName("stack");
        for (int i = 0; i < stacks.getLength(); i++) {
            Element element = (Element) stacks.item(i);
            String sname = elementText(element, "name");
            Element sthemecolor = (Element) element.getElementsByTagName("themecolor").item(0);
            String svalue = elementText(element, "value");
            Element stackLabelElement = (Element) element.getElementsByTagName("label").item(0);
            Stack s = SymbolToolkit.createStack(sname,
                                                elementColor(sthemecolor),
                                                Double.parseDouble(svalue));
            if (stackLabelElement != null) {
                String type = elementText(stackLabelElement, "type");
                if (type.equals("relative")) {
                    BarSymbolRelativeLabel label = inflateRelativeLabel(stackLabelElement);
                    s.setBarLabel(label);
                }
            }
            bar.addStack(s);
        }

        return bar;
    }


}
