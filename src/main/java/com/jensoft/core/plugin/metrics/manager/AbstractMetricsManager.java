/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.manager;

import java.awt.Color;
import java.awt.Font;

import com.jensoft.core.bean.AbstractBean;
import com.jensoft.core.plugin.metrics.format.IMetricsFormat;
import com.jensoft.core.plugin.metrics.format.MetricsDecimalFormat;
import com.jensoft.core.plugin.metrics.geom.MetricsRenderContext;
import com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType;

/**
 * AbstractMetricsManager takes the responsibility to solve and create metrics
 * <p>
 * subclass this abstract definition to solve metrics and override {@link #getDeviceMetrics()}
 * <p>
 * 
 * @author sebastien janaud
 */
public abstract class AbstractMetricsManager extends AbstractBean implements
        MetricsManager {

    /** default decimal format */
    private IMetricsFormat defaultFormat = new MetricsDecimalFormat();

    /** metrics type */
    private MetricsType type;

    /** metrics format */
    private IMetricsFormat metricsFormat;

    /** metrics MarkerColor */
    private Color metricsMarkerColor;

    /** metrics label color */
    private Color metricsLabelColor;

    /** metrics base line color */
    private Color metricsBaseLineColor;

    /** metrics median font */
    private Font metricsMedianFont = new Font("Dialog", Font.PLAIN, 10);

    /** metrics major font */
    private Font metricsMajorFont = new Font("Dialog", Font.PLAIN, 12);

    /** metrics render context */
    private MetricsRenderContext renderContext;

    /** lock marker */
    private boolean lockMarker = true;

    /** lock label */
    private boolean lockLabel = true;

    /**
     * create abstract metrics manager
     */
    public AbstractMetricsManager() {
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#lockMarker()
     */
    @Override
    public void lockMarker() {
        lockMarker = true;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#unlockMarker()
     */
    @Override
    public void unlockMarker() {
        lockMarker = false;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#isLockMarker()
     */
    @Override
    public boolean isLockMarker() {
        return lockMarker;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#lockLabel()
     */
    @Override
    public void lockLabel() {
        lockLabel = true;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#unlockLabel()
     */
    @Override
    public void unlockLabel() {
        lockLabel = false;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#isLockLabel()
     */
    @Override
    public boolean isLockLabel() {
        return lockLabel;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#getRenderContext()
     */
    @Override
    public MetricsRenderContext getRenderContext() {
        return renderContext;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#setRenderContext(com.jensoft.sw2d.core.plugin
     * .metrics.geom.MetricsRenderContext)
     */
    @Override
    public void setRenderContext(MetricsRenderContext renderContext) {
        MetricsRenderContext old = getRenderContext();
        this.renderContext = renderContext;
        firePropertyChange("renderContext", old, getRenderContext());
    }

    /**
     * get basic format
     * 
     * @return default format
     */
    public IMetricsFormat getDefaultFormat() {
        return defaultFormat;
    }

    /**
     * set the default format, use if format is not set
     * 
     * @param defaultFormat
     *            the default format
     */
    public void setDefaultFormat(IMetricsFormat defaultFormat) {
        IMetricsFormat old = getDefaultFormat();
        this.defaultFormat = defaultFormat;
        firePropertyChange("basicFormater", old, getDefaultFormat());
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#getType()
     */
    @Override
    public MetricsType getType() {
        return type;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#setMetricsType(com.jensoft.sw2d.core.plugin
     * .metrics.axis.AxisMetricsPlugin.MetricsType)
     */
    @Override
    public void setMetricsType(MetricsType type) {
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#getMetricsFormat()
     */
    @Override
    public IMetricsFormat getMetricsFormat() {
        return metricsFormat;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#setMetricsFormat(com.jensoft.sw2d.core.plugin
     * .metrics.format.IMetricsFormat)
     */
    @Override
    public void setMetricsFormat(IMetricsFormat metricsFormat) {
        this.metricsFormat = metricsFormat;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#getMetricsMarkerColor()
     */
    @Override
    public Color getMetricsMarkerColor() {
        return metricsMarkerColor;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#setMetricsMarkerColor(java.awt.Color)
     */
    @Override
    public void setMetricsMarkerColor(Color metricsColor) {
        Color old = getMetricsMarkerColor();
        metricsMarkerColor = metricsColor;
        firePropertyChange("metricsMarkerColor", old, getMetricsMarkerColor());
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#getMetricsLabelColor()
     */
    @Override
    public Color getMetricsLabelColor() {
        return metricsLabelColor;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#setMetricsLabelColor(java.awt.Color)
     */
    @Override
    public void setMetricsLabelColor(Color metricsLabelColor) {
        Color old = getMetricsLabelColor();
        this.metricsLabelColor = metricsLabelColor;
        firePropertyChange("metricsLabelColor", old, getMetricsLabelColor());
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#getMetricsBaseLineColor()
     */
    @Override
    public Color getMetricsBaseLineColor() {
        return metricsBaseLineColor;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#setMetricsBaseLineColor(java.awt.Color)
     */
    @Override
    public void setMetricsBaseLineColor(Color metricsBaseLineColor) {
        this.metricsBaseLineColor = metricsBaseLineColor;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#getMetricsMedianFont()
     */
    @Override
    public Font getMetricsMedianFont() {
        return metricsMedianFont;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#setMetricsMedianFont(java.awt.Font)
     */
    @Override
    public void setMetricsMedianFont(Font metricsFont) {
        metricsMedianFont = metricsFont;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#getMetricsMajorFont()
     */
    @Override
    public Font getMetricsMajorFont() {
        return metricsMajorFont;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsLayoutManager#setMetricsMajorFont(java.awt.Font)
     */
    @Override
    public void setMetricsMajorFont(Font metricsMajorFont) {
        this.metricsMajorFont = metricsMajorFont;
    }

}
