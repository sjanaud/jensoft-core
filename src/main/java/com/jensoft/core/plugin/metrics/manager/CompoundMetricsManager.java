/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.manager;

import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.metrics.format.IMetricsFormat;
import com.jensoft.core.plugin.metrics.geom.Metrics;
import com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType;
import com.jensoft.core.plugin.metrics.geom.MetricsRenderContext;

/**
 * CompoundMetricsManager
 * TODO check this compound
 */
public class CompoundMetricsManager extends AbstractMetricsManager {

    /** metrics format */
    private IMetricsFormat metricsFormat;

    /** metrics type */
    private MetricsType metricsType;

    /** render context */
    private MetricsRenderContext metricsRenderContext;

    /** metrics managers */
    private MetricsManager[] managers;

    /** compound metrics */
    private List<Metrics> deviceMetrics = new ArrayList<Metrics>();

    /***
     * create compound metrics manager with the specified managers array
     * 
     * @param managers
     *            the managers to set
     */
    public CompoundMetricsManager(MetricsManager... managers) {
        this.managers = managers;
    }

    /**
     * @return the managers
     */
    public MetricsManager[] getManagers() {
        return managers;
    }

    /**
     * @param managers
     *            the managers to set
     */
    public void setManagers(MetricsManager[] managers) {
        this.managers = managers;
    }

    /**
     * @return the metricsType
     */
    public MetricsType getMetricsType() {
        return metricsType;
    }

    /**
     * @return the metricsRenderContext
     */
    public MetricsRenderContext getMetricsRenderContext() {
        return metricsRenderContext;
    }

    /**
     * set metrics type
     * 
     * @param type
     *            the metrics type to set
     */
    @Override
    public void setMetricsType(MetricsType type) {
        metricsType = type;
    }

    /**
     * set metrics render context
     * 
     * @param renderContext
     *            the render context to set
     */
    public void setMetricsRenderContext(MetricsRenderContext renderContext) {
        metricsRenderContext = renderContext;
    }

    @Override
    public List<Metrics> getDeviceMetrics() {
        copyFormat2Manager();
        copyType2Manager();
        deviceMetrics.clear();
        for (int i = 0; i < managers.length; i++) {
            deviceMetrics.addAll(managers[i].getDeviceMetrics());
        }
        return deviceMetrics;
    }

    /**
     * get metrics format
     */
    @Override
    public IMetricsFormat getMetricsFormat() {
        return metricsFormat;
    }

    /**
     * set formater for all register manager
     */
    @Override
    public void setMetricsFormat(IMetricsFormat formater) {
        metricsFormat = formater;

    }

    /**
     * copy metrics type property to each registered manager
     */
    private void copyType2Manager() {
        for (int i = 0; i < managers.length; i++) {
            managers[i].setMetricsType(metricsType);
        }
    }

    /**
     * copy metrics format property to each registered manager
     */
    private void copyFormat2Manager() {
        for (int i = 0; i < managers.length; i++) {
            managers[i].setMetricsFormat(metricsFormat);
        }
    }

}
