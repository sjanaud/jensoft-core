/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.ray;

import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.plugin.metrics.AxisMetricsPlugin;
import org.jensoft.core.plugin.metrics.AxisMetricsPlugin.Axis;
import org.jensoft.core.plugin.outline.OutlinePlugin;
import org.jensoft.core.projection.Projection;
import org.jensoft.core.view.View;

/**
 * Compatible ray view
 */
public class RayView extends View {

    /** uid */
    private static final long serialVersionUID = 4610374377279625205L;

    /** projection associate to this view and ray plugin */
    private Projection.Linear projection;

    /** ray plug in */
    private RayPlugin rayPlugin;

    /** default axis metrics */
    private AxisMetricsPlugin.ModeledMetrics axisMiliMetricsX;
    private AxisMetricsPlugin.ModeledMetrics axisMiliMetricsY;

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

        createProjection(minx, maxx, miny, maxy);
        registerPlugin();

    }

    /**
     * register the specified plugin into this view projection
     * 
     * @param plugin
     *            the plugin to register
     */
    public void registerPlugin(AbstractPlugin plugin) {
        projection.registerPlugin(plugin);
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
     * create compatible projection according to specified constructor parameters
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
    private void createProjection(double minx, double maxx, double miny, double maxy) {
        projection = new Projection.Linear(minx, maxx, miny, maxy);
        registerProjection(projection);
    }

    /**
     * register bar plug in in internal projection of this view
     */
    private void registerPlugin() {
        rayPlugin = new RayPlugin();

        rayPlugin.setPriority(10);
        projection.registerPlugin(rayPlugin);

        axisMiliMetricsY = new AxisMetricsPlugin.ModeledMetrics(
                                                Axis.AxisWest);

        axisMiliMetricsX = new AxisMetricsPlugin.ModeledMetrics(Axis.AxisSouth);

        projection.registerPlugin(axisMiliMetricsX);
        projection.registerPlugin(axisMiliMetricsY);

        projection.registerPlugin(new OutlinePlugin());

    }

    /**
     * @return the ray projection
     */
    public Projection getProjection() {
        return projection;
    }

    /**
     * @return the ray Plugin
     */
    public RayPlugin getRayPlugin() {
        return rayPlugin;
    }

}
