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
 * <code>FlowMetricsManager</code> take the responsibility to generate a flow of metrics.
 * 
 * @author sebastien janaud
 */
public class FlowMetricsManager extends AbstractMetricsManager {

    /** flow interval */
    private double flowInterval = 0;

    /** flow start */
    private double flowStart = 0;

    /** flow end */
    private double flowEnd = 0;

    /** metrics flow */
    private List<Metrics> deviceMetrics = new ArrayList<Metrics>();

    /**
     * create flow metrics manager
     * 
     * @param flowStart
     *            the start of this flow
     * @param flowEnd
     *            the end this flow
     * @param flowInterval
     *            the flow interval
     */
    public FlowMetricsManager(double flowStart, double flowEnd,
            double flowInterval) {
        this.flowStart = flowStart;
        this.flowEnd = flowEnd;
        this.flowInterval = flowInterval;
    }

    /**
     * get flow interval
     * 
     * @return flow interval
     */
    public double getFlowInterval() {
        return flowInterval;
    }

    /**
     * set flow interval
     * 
     * @param flowInterval
     *            the flow interval to set
     */
    public void setFlowInterval(double flowInterval) {
        this.flowInterval = flowInterval;
    }

    /**
     * get flow start
     * 
     * @return flow start
     */
    public double getFlowStart() {
        return flowStart;
    }

    /**
     * set flow start
     * 
     * @param flowStart
     *            the flow start to set
     */
    public void setFlowStart(double flowStart) {
        this.flowStart = flowStart;
    }

    /**
     * get flow end
     * 
     * @return flow end
     */
    public double getFlowEnd() {
        return flowEnd;
    }

    /**
     * set flow end
     * 
     * @param flowEnd
     *            the flow end to set
     */
    public void setFlowEnd(double flowEnd) {
        this.flowEnd = flowEnd;
    }

    /**
     * get flow metrics
     * 
     * @return metrics flow
     */
    @Override
    public List<Metrics> getDeviceMetrics() {
        deviceMetrics.clear();
        Projection w2d = getRenderContext().getWindow2D();
        double interval = flowEnd - flowStart;

        int metricsCount = (int) (interval / flowInterval);

        if (metricsCount > 0) {
            for (int i = 0; i <= metricsCount; i++) {
                double userMetricsValue = flowStart + i * flowInterval;

                if (getType() == MetricsType.XMetrics) {
                    if (userMetricsValue > w2d.getMinX()
                            && userMetricsValue < w2d.getMaxX()) {
                        Point2D p2dUser = new Point2D.Double(userMetricsValue,
                                                             0);
                        Point2D p2dDevice = w2d.userToPixel(p2dUser);

                        Metrics metrics = new Metrics(MetricsType.XMetrics);

                        metrics.setDeviceValue(p2dDevice.getX());
                        metrics.setUserValue(userMetricsValue);
                        metrics.setMetricsMarkerColor(getMetricsMarkerColor());
                        metrics.setMetricsLabelColor(getMetricsLabelColor());
                        metrics.setLockLabel(isLockLabel());
                        metrics.setLockMarker(isLockMarker());

                        if (getMetricsFormat() == null) {
                            metrics.setMetricsLabel(getDefaultFormat().format(
                                                                              userMetricsValue));
                        }
                        else {
                            metrics.setMetricsLabel(getMetricsFormat().format(
                                                                              userMetricsValue));
                        }

                        deviceMetrics.add(metrics);
                    }
                }
                if (getType() == MetricsType.YMetrics) {
                    if (userMetricsValue > w2d.getMinY()
                            && userMetricsValue < w2d.getMaxY()) {
                        Point2D p2dUser = new Point2D.Double(0,
                                                             userMetricsValue);
                        Point2D p2dDevice = w2d.userToPixel(p2dUser);

                        Metrics metrics = new Metrics(MetricsType.YMetrics);
                        metrics.setDeviceValue(p2dDevice.getY());
                        metrics.setUserValue(userMetricsValue);
                        metrics.setMetricsMarkerColor(getMetricsMarkerColor());
                        metrics.setMetricsLabelColor(getMetricsLabelColor());
                        metrics.setLockLabel(isLockLabel());
                        metrics.setLockMarker(isLockMarker());

                        if (getMetricsFormat() == null) {
                            metrics.setMetricsLabel(getDefaultFormat().format(
                                                                              userMetricsValue));
                        }
                        else {
                            metrics.setMetricsLabel(getMetricsFormat().format(
                                                                              userMetricsValue));
                        }

                        deviceMetrics.add(metrics);
                    }
                }

            }
        }

        return deviceMetrics;
    }

}
