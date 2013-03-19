/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.manager;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import com.jensoft.core.plugin.metrics.format.IMetricsFormat;
import com.jensoft.core.plugin.metrics.geom.Metrics;
import com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType;
import com.jensoft.core.plugin.metrics.geom.MetricsRenderContext;

/**
 * <code>MetricsManager</code> defines a metrics manager
 * 
 * @author sebastien janaud
 */
public interface MetricsManager {

    /**
     * get render context
     * 
     * @return render context
     */
    public MetricsRenderContext getRenderContext();

    /**
     * set render context
     * 
     * @param renderContext
     *            the renderContext to set
     */
    public void setRenderContext(MetricsRenderContext renderContext);

    /**
     * set metrics type
     * 
     * @param type
     *            the type to set
     */
    public void setMetricsType(MetricsType type);

    /**
     * get metrics type
     * 
     * @return metrics type
     */
    public MetricsType getType();

    /**
     * get metrics format
     * 
     * @return metrics format
     */
    public IMetricsFormat getMetricsFormat();

    /**
     * set metrics format
     * 
     * @param format
     */
    public void setMetricsFormat(IMetricsFormat format);

    /**
     * get metrics marker color
     * 
     * @return marker color
     */
    public Color getMetricsMarkerColor();

    /**
     * set metrics marker color
     * 
     * @param metricsMarkerColor
     *            the metrics marker color to set
     */
    public void setMetricsMarkerColor(Color metricsMarkerColor);

    /**
     * get metrics label Color
     * 
     * @return metrics label color
     */
    public Color getMetricsLabelColor();

    /**
     * set metrics label color
     * 
     * @param metricsLabelColor
     *            the metrics label color to set
     */
    public void setMetricsLabelColor(Color metricsLabelColor);

    /**
     * get metrics base line color
     * 
     * @return metrics base line color
     */
    public Color getMetricsBaseLineColor();

    /**
     * set metrics base line color
     * 
     * @param metricsBaseLineColor
     *            the metrics base line color to set
     */
    public void setMetricsBaseLineColor(Color metricsBaseLineColor);

    /**
     * get metrics median font
     * 
     * @return metrics median font
     */
    public Font getMetricsMedianFont();

    /**
     * set metrics median font
     * 
     * @param metricsMedianFont
     *            the metrics median font to set
     */
    public void setMetricsMedianFont(Font metricsMedianFont);

    /**
     * get metrics major font
     * 
     * @return metrics major font
     */
    public Font getMetricsMajorFont();

    /**
     * set metrics major font
     * 
     * @param metricsMajorFont
     *            the metrics major font to set
     */
    public void setMetricsMajorFont(Font metricsMajorFont);

    /**
     * lock marker
     */
    public void lockMarker();

    /**
     * unlock marker
     */
    public void unlockMarker();

    /**
     * true if marker is lock, false otherwise
     * 
     * @return true if marker is lock, false otherwise
     */
    public boolean isLockMarker();

    /**
     * lock label
     */
    public void lockLabel();

    /**
     * unlock label
     */
    public void unlockLabel();

    /**
     * true if label is lock, false otherwise
     * 
     * @return true if label is lock, false otherwise
     */
    public boolean isLockLabel();

    /**
     * <p>
     * subclass this abstract metrics manager and override this method to create metrics
     * </p>
     * <p>
     * get metrics solved
     * </p>
     * 
     * @return
     */
    public List<Metrics> getDeviceMetrics();

}
