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

/**
 * <code>MetricsManager</code> defines a metrics manager
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public interface MetricsManager {


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
     * @return metrics collection
     */
    public List<Metrics> getDeviceMetrics();

}
