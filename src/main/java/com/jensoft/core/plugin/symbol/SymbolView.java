/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.Axis;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.MultiMultiplierMetrics;
import com.jensoft.core.plugin.outline.OutlinePlugin;
import com.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;

/**
 * Compatible view with preset:
 * <ul>
 * <li>Window2D</li>
 * <li>Symbol Plugin</li>
 * <li>Metrics Axis Plugin</li>
 * <li>Outline Plugin</li>
 * <li>Delegates methods</li>
 * <ul>
 * 
 * @author Sebastien Janaud
 */
public class SymbolView extends View2D {

    /** uid */
    private static final long serialVersionUID = 5196980120162363267L;

    /** window2D associate to this view and bar plugin */
    private Window2D.Linear window2DSymbol;

    /** bar plug in */
    private SymbolPlugin symbolPlugin;

    /** bar nature */
    private SymbolNature symbolNature;

    /** default axis metrics */
    private MultiMultiplierMetrics axisMiliMetrics;

    /** private outline */
    private OutlinePlugin outlinePlugin;

    /**
     * create compatible view with bar plugin
     * 
     * @param barNature
     *            the bar nature to set
     * @param min
     *            the minimum coordinate value of the metrics dimension
     * @param max
     *            the maximum coordinate value of the metrics dimension
     */
    public SymbolView(SymbolNature barNature, double min, double max) {
        super(40);
        symbolNature = barNature;

        createWindow(min, max);
        registerPlugin();

    }

    /**
     * register the specified plugin into this view window2D
     * 
     * @param plugin
     *            the plugin to register
     */
    public void registerPlugin(AbstractPlugin plugin) {
        window2DSymbol.registerPlugin(plugin);
    }

    /**
     * add the specified layer into this compatible view
     * 
     * @param layer
     *            the layer to add
     */
    public void addLayer(SymbolLayer<? extends SymbolComponent> layer) {
        symbolPlugin.addLayer(layer);
    }

    /**
     * create compatible window according to specified constructor parameters
     * 
     * @param min
     *            the minimum coordinate value of the metrics dimension
     * @param max
     *            the maximum coordinate value of the metrics dimension
     */
    private void createWindow(double min, double max) {
        if (symbolNature == SymbolNature.Vertical) {
            window2DSymbol = new Window2D.Linear(0, 0, min, max);
            window2DSymbol.setName("compatible vertical bar window");
        }
        else if (symbolNature == SymbolNature.Horizontal) {
            window2DSymbol = new Window2D.Linear(min, max, 0, 0);
            window2DSymbol.setName("compatible horizontal bar window");
        }
        registerWindow2D(window2DSymbol);
    }

    /**
     * register bar plug in in internal window of this view
     */
    private void registerPlugin() {
        symbolPlugin = new SymbolPlugin();
        symbolPlugin.setNature(symbolNature);

        window2DSymbol.registerPlugin(symbolPlugin);

        if (symbolNature == SymbolNature.Vertical) {
            axisMiliMetrics = new MultiMultiplierMetrics(window2DSymbol.getMinY(),
                                                   Axis.AxisWest);
            double height = window2DSymbol.getUserHeight();
            axisMiliMetrics.setMajor(height / 10);
            axisMiliMetrics.setMedian(height / 20);
            axisMiliMetrics.setMinor(height / 100);
        }
        else if (symbolNature == SymbolNature.Horizontal) {
            axisMiliMetrics = new MultiMultiplierMetrics(window2DSymbol.getMinX(),
                                                   Axis.AxisSouth);
            double width = window2DSymbol.getUserWidth();
            axisMiliMetrics.setMajor(width / 10);
            axisMiliMetrics.setMedian(width / 20);
            axisMiliMetrics.setMinor(width / 100);
        }

        window2DSymbol.registerPlugin(axisMiliMetrics);

        outlinePlugin = new OutlinePlugin();
        window2DSymbol.registerPlugin(outlinePlugin);

    }

    /**
     * @return the outlinePlugin
     */
    public OutlinePlugin getOutlinePlugin() {
        return outlinePlugin;
    }

    /**
     * @param outlinePlugin
     *            the outlinePlugin to set
     */
    public void setOutlinePlugin(OutlinePlugin outlinePlugin) {
        this.outlinePlugin = outlinePlugin;
    }

    /**
     * @return the window2DBar
     */
    public Window2D getWindow2D() {
        return window2DSymbol;
    }

    /**
     * @return the barPlugin
     */
    public SymbolPlugin getBarPlugin() {
        return symbolPlugin;
    }

    /**
     * @return the barNature
     */
    public SymbolNature getBarNature() {
        return symbolNature;
    }

    /**
     * @return the axisMiliMetrics
     */
    public MultiMultiplierMetrics getAxisMiliMetrics() {
        return axisMiliMetrics;
    }

    /**
     * @param axisMiliMetrics
     *            the axisMiliMetrics to set
     */
    public void setAxisMiliMetrics(MultiMultiplierMetrics axisMiliMetrics) {
        this.axisMiliMetrics = axisMiliMetrics;
    }

}
