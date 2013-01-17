/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.linesymbol.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class LineSymbolGroup extends LineSymbol {

    // only group
    public String symbol;

    // can be use for children
    private Color themeColor;

    public LineSymbolGroup() {
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Color getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
    }

    private List<LineSymbolComponent> lineComponents = new ArrayList<LineSymbolComponent>();

    public void addLineComponent(LineSymbolComponent bar) {
        lineComponents.add(bar);
    }

    public void removeLineComponent(LineSymbolComponent bar) {
        lineComponents.remove(bar);
    }

    public List<LineSymbolComponent> getLineComponents() {
        return lineComponents;
    }

    public void setLineComponents(List<LineSymbolComponent> bars) {
        lineComponents = bars;
    }

}
