/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol;

import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;
import com.jensoft.core.plugin.symbol.painter.polyline.AbstractPolylinePointSymbolPainter;

/**
 * <code>PointSymbolGroup</code> is a symbol that host other symbol, it has two
 * main responsibility:
 * <ul>
 * <li>diffuse properties to hosted symbol</li>
 * <li>lay out hosted symbol</li>
 * </ul>
 * 
 * @see PointSymbol
 * @author Sebastien Janaud
 */
public class PolylinePointSymbol extends PointSymbol {

    /** bar members */
    private List<PointSymbol> symbolComponents = new ArrayList<PointSymbol>();

    /** polyline painter */
    private AbstractPolylinePointSymbolPainter polylinePainter;

    /**
     * Bar Symbol Group
     */
    public PolylinePointSymbol() {
    }

    /**
     * Bar Symbol Group
     * 
     * @param name
     *            name to set
     */
    public PolylinePointSymbol(String name) {
        setName(name);
    }

    /**
     * copy all set property to point members.
     */
    public void copyToBar() {

    }

    @Override
    public SymbolNature getNature() {
        return getHost().getNature();
    }

    @Override
    public double getThickness() {
        // double groupThickness = 0;
        // for (SymbolComponent b : symbolComponents) {
        // groupThickness = groupThickness + b.getThickness();
        // }
        // return groupThickness;
        return 50;
    }

    /***
     * get the polyline painter
     * 
     * @return polyline painter
     */
    public AbstractPolylinePointSymbolPainter getPolylinePainter() {
        return polylinePainter;
    }

    /**
     * set the polyline painter
     * 
     * @param polylinePainter
     *            the polyline painter to set
     */
    public void setPolylinePainter(AbstractPolylinePointSymbolPainter polylinePainter) {
        this.polylinePainter = polylinePainter;
    }

    /**
     * add symbol component
     * 
     * @param symbol
     *            the symbol to add
     */
    public void addSymbol(PointSymbol symbol) {
        symbolComponents.add(symbol);
    }

    /**
     * remove symbol component
     * 
     * @param symbol
     *            the bar to remove
     */
    public void removeSymbolComponent(PointSymbol symbol) {
        symbolComponents.remove(symbol);
    }

    /**
     * get symbol components
     * 
     * @return symbol components
     */
    public List<PointSymbol> getSymbolComponents() {
        return symbolComponents;
    }

    /**
     * set symbol components
     * 
     * @param symbolComponents
     */
    public void setSymbolComponents(List<PointSymbol> symbolComponents) {
        this.symbolComponents = symbolComponents;
    }

}
