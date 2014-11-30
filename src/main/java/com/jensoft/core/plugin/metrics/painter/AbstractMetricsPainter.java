/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.painter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.plugin.metrics.geom.Metrics;
import com.jensoft.core.plugin.metrics.geom.MetricsRenderContext;

/**
 * <code>AbstractMetricsPainter</code> takes the responsibility to paint metrics on projection part
 * 
 * @since 1.0
 * 
 * @author sebastien janaud
 * 
 */
public abstract class AbstractMetricsPainter {

    /**
     * create metrics painter
     */
    public AbstractMetricsPainter() {
    }

    /** metrics render context */
    private MetricsRenderContext metricsRenderContext;
    
    /**
     * get metrics render context
     * 
     * @return metrics render context
     */
    public MetricsRenderContext getMetricsRenderContext() {
        return metricsRenderContext;
    }

    /**
     * set metrics render context
     * 
     * @param metricsRenderContext
     *            the render context to set
     */
    public void setMetricsRenderContext(
            MetricsRenderContext metricsRenderContext) {
        this.metricsRenderContext = metricsRenderContext;
    }

    /**
     * paint metrics base line
     * 
     * @param g2d
     *            the graphics context
     * @param start
     *            the start point of the axis base line
     * @param end
     *            the end point of the axis base line
     * @param axisBaseColor
     *            axis base color
     */
    public abstract void doPaintLineMetrics(Graphics2D g2d, Point2D start,
            Point2D end, Color axisBaseColor);

    /**
     * paint metrics
     * 
     * @param g2d
     *            the graphics context
     * @param metrics
     *            the metrics to paint
     */
    public abstract void doPaintMetrics(Graphics2D g2d, List<Metrics> metrics);

}
