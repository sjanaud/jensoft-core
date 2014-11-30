/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.manager;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.metrics.geom.Metrics;
import com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType;
import com.jensoft.core.projection.Projection;

/**
 * <code>FreeMetricsManager</code>
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public class FreeMetricsManager extends AbstractMetricsManager {

    /** free metrics */
    private List<Metrics> metricsDevice = new ArrayList<Metrics>();

    /** volatile free metrics */
    private List<metricsTemp> volatileMetrics = new ArrayList<metricsTemp>();

    /**
     * create free metrics manager
     */
    public FreeMetricsManager() {
    }

    class metricsTemp {

        double value;
        String label;

        public metricsTemp(double v, String label) {
            super();
            this.value = v;
            this.label = label;
        }
    }

    /**
     * add metrics with specified parameter
     * 
     * @param metricsValue
     *            the metric value
     */
    public void addMetrics(double metricsValue) {
        volatileMetrics.add(new metricsTemp(metricsValue, null));
    }

    public void addMetrics(double m, String label) {
        volatileMetrics.add(new metricsTemp(m, label));
    }

    /**
     * get free metrics
     * 
     * @return free metrics
     */
    @Override
    public List<Metrics> getDeviceMetrics() {

        metricsDevice.clear();
        Projection w2d = getRenderContext().getProjection();
        if (getType() == MetricsType.XMetrics) {

            for (int i = 0; i < volatileMetrics.size(); i++) {

                metricsTemp tTemp = volatileMetrics.get(i);
                double userMetricsX = tTemp.value;

                if (userMetricsX > w2d.getMinX()
                        && userMetricsX < w2d.getMaxX()) {
                    Point2D p2dUserMetrics = new Point2D.Double(userMetricsX, 0);
                    Point2D p2dDeviceMetrics = w2d.userToPixel(p2dUserMetrics);

                    Metrics metrics = new Metrics(MetricsType.XMetrics);

                    metrics.setDeviceValue(p2dDeviceMetrics.getX());
                    metrics.setUserValue(userMetricsX);
                    metrics.setMetricsMarkerColor(getMetricsMarkerColor());
                    metrics.setMetricsLabelColor(getMetricsLabelColor());
                    metrics.setLockLabel(isLockLabel());
                    metrics.setLockMarker(isLockMarker());

                    if (tTemp.label != null) {
                        metrics.setMetricsLabel(tTemp.label);
                    }
                    else {
                        if (getMetricsFormat() == null) {
                            metrics.setMetricsLabel(getDefaultFormat().format(
                                                                              userMetricsX));
                        }
                        else {
                            metrics.setMetricsLabel(getMetricsFormat().format(
                                                                              userMetricsX));
                        }
                    }

                    metricsDevice.add(metrics);
                }

            }
        }
        else if (getType() == MetricsType.YMetrics) {

            for (int i = 0; i < volatileMetrics.size(); i++) {

                metricsTemp tTemp = volatileMetrics.get(i);
                double userMetricsY = tTemp.value;

                if (userMetricsY > w2d.getMinY()
                        && userMetricsY < w2d.getMaxY()) {
                    Point2D p2dUserMetrics = new Point2D.Double(0, userMetricsY);
                    Point2D p2dDeviceMetrics = w2d.userToPixel(p2dUserMetrics);

                    Metrics metrics = new Metrics(MetricsType.YMetrics);

                    metrics.setDeviceValue(p2dDeviceMetrics.getY());
                    metrics.setUserValue(userMetricsY);
                    metrics.setMetricsMarkerColor(getMetricsMarkerColor());
                    metrics.setMetricsLabelColor(getMetricsLabelColor());
                    metrics.setLockLabel(isLockLabel());
                    metrics.setLockMarker(isLockMarker());

                    if (tTemp.label != null) {
                        metrics.setMetricsLabel(tTemp.label);
                    }
                    else {
                        if (getMetricsFormat() == null) {
                            metrics.setMetricsLabel(getDefaultFormat().format(
                                                                              userMetricsY));
                        }
                        else {
                            metrics.setMetricsLabel(getMetricsFormat().format(
                                                                              userMetricsY));
                        }
                    }

                    metricsDevice.add(metrics);
                }

            }

        }
        return metricsDevice;

    }

}
