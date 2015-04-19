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
 * <code>StaticMetricsManager</code> takes the responsibility to manage static metrics
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public class StaticMetricsManager extends AbstractMetricsManager {

    /** static metrics */
    private List<Metrics> metricsDevice = new ArrayList<Metrics>();

    /** metrcis count */
    private int metricsCount;

    /**
     * create static metrics with specified parameter
     * 
     * @param metricsCount
     *            the metrics count
     */
    public StaticMetricsManager(int metricsCount) {
        this.metricsCount = metricsCount;
    }

    /**
     * get static metrics
     * 
     * @return static metrics
     */
    @Override
    public List<Metrics> getDeviceMetrics() {
        metricsDevice.clear();
        Projection w2d = getMetricsPlugin().getProjection();

        if (getType() == MetricsType.XMetrics) {

            double userWidth = w2d.getUserWidth();
            double userMetricsX;

            for (int i = 0; i < metricsCount; i++) {

                userMetricsX = w2d.getMinX() + i * userWidth / new Double(metricsCount - 1);

                Point2D p2dUserMetrics = new Point2D.Double(userMetricsX, 0);
                Point2D p2dDeviceMetrics = w2d.userToPixel(p2dUserMetrics);

                Metrics metrics = new Metrics(MetricsType.XMetrics);

                metrics.setDeviceValue(p2dDeviceMetrics.getX());
                metrics.setUserValue(userMetricsX);
                //metrics.setMetricsMarkerColor(getMetricsMarkerColor());
                //metrics.setMetricsLabelColor(getMetricsLabelColor());
                //metrics.setLockLabel(isLockLabel());
                //metrics.setLockMarker(isLockMarker());

                if (getMetricsFormat() == null) {
                    metrics.setMetricsLabel(getDefaultFormat().format(
                                                                      userMetricsX));
                }
                else {
                    metrics.setMetricsLabel(getMetricsFormat().format(
                                                                      userMetricsX));
                }

                metricsDevice.add(metrics);
            }

        }
        else if (getType() == MetricsType.YMetrics) {

            double userHeight = w2d.getUserHeight();
            double userMetricsY;

            for (int i = 0; i < metricsCount; i++) {

                userMetricsY = w2d.getMinY() + i * userHeight / new Double(metricsCount - 1);

                Point2D p2dUserMetrics = new Point2D.Double(0, userMetricsY);
                Point2D p2dDeviceMetrics = w2d.userToPixel(p2dUserMetrics);

                Metrics metrics = new Metrics(MetricsType.YMetrics);

                metrics.setDeviceValue(p2dDeviceMetrics.getY());
                metrics.setUserValue(userMetricsY);
               // metrics.setMetricsMarkerColor(getMetricsMarkerColor());
               // metrics.setMetricsLabelColor(getMetricsLabelColor());
               // metrics.setLockLabel(isLockLabel());
               // metrics.setLockMarker(isLockMarker());

                if (getMetricsFormat() == null) {
                    metrics.setMetricsLabel(getDefaultFormat().format(
                                                                      userMetricsY));
                }
                else {
                    metrics.setMetricsLabel(getMetricsFormat().format(
                                                                      userMetricsY));
                }

                metricsDevice.add(metrics);
            }

        }

        return metricsDevice;

    }

}
