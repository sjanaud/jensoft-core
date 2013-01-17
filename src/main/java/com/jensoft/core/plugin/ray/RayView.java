/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.Axis;
import com.jensoft.core.plugin.outline.OutlinePlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;

/**
 * Compatible ray view
 */
public class RayView extends View2D {

    /** uid */
    private static final long serialVersionUID = 4610374377279625205L;

    /** window2D associate to this view and ray plugin */
    private Window2D.Linear window2DRay;

    /** ray plug in */
    private RayPlugin rayPlugin;

    /** default axis metrics */
    private AxisMetricsPlugin.MultiMultiplierMetrics axisMiliMetricsX;
    private AxisMetricsPlugin.MultiMultiplierMetrics axisMiliMetricsY;

    /**
     * create compatible view with bar plugin
     * 
     * @param minx
     *            the minimum x coordinate value
     * @param maxx
     *            the maximum y coordinate value
     * @param miny
     *            the minimum y coordinate value
     * @param maxy
     *            the maximum y coordinate value
     */
    public RayView(double minx, double maxx, double miny, double maxy) {

        setPlaceHolderAxisNorth(60);
        setPlaceHolderAxisSouth(60);
        setPlaceHolderAxisWest(60);
        setPlaceHolderAxisEast(60);

        // setBackground(Color.WHITE);

        createWindow(minx, maxx, miny, maxy);
        registerPlugin();

    }

    /**
     * register the specified plugin into this view window2D
     * 
     * @param plugin
     *            the plugin to register
     */
    public void registerPlugin(AbstractPlugin plugin) {
        window2DRay.registerPlugin(plugin);
    }

    /**
     * add the specified symbol into this compatible view
     * 
     * @param ray
     *            the ray to add
     */
    public void addRay(Ray ray) {
        rayPlugin.addRay(ray);
    }

    /**
     * create compatible window according to specified constructor parameters
     * 
     * @param minx
     *            the minimum x coordinate value
     * @param maxx
     *            the maximum y coordinate value
     * @param miny
     *            the minimum y coordinate value
     * @param maxy
     *            the maximum y coordinate value
     */
    private void createWindow(double minx, double maxx, double miny, double maxy) {
        window2DRay = new Window2D.Linear(minx, maxx, miny, maxy);
        registerWindow2D(window2DRay);
    }

    /**
     * register bar plug in in internal window of this view
     */
    private void registerPlugin() {
        rayPlugin = new RayPlugin();

        rayPlugin.setPriority(10);
        window2DRay.registerPlugin(rayPlugin);

        axisMiliMetricsY = new AxisMetricsPlugin.MultiMultiplierMetrics(window2DRay.getMinY(),
                                                Axis.AxisWest);
        double height = window2DRay.getUserHeight();
        axisMiliMetricsY.setMajor(height / 10);
        axisMiliMetricsY.setMedian(height / 20);
        axisMiliMetricsY.setMinor(height / 100);

        axisMiliMetricsX = new AxisMetricsPlugin.MultiMultiplierMetrics(window2DRay.getMinX(),
                                                Axis.AxisSouth);
        double width = window2DRay.getUserWidth();
        axisMiliMetricsX.setMajor(width / 10);
        axisMiliMetricsX.setMedian(width / 20);
        axisMiliMetricsX.setMinor(width / 100);

        window2DRay.registerPlugin(axisMiliMetricsX);
        window2DRay.registerPlugin(axisMiliMetricsY);

        window2DRay.registerPlugin(new OutlinePlugin());

    }

    /**
     * @return the ray window
     */
    public Window2D getRayWindow() {
        return window2DRay;
    }

    /**
     * @return the ray Plugin
     */
    public RayPlugin getRayPlugin() {
        return rayPlugin;
    }

}
