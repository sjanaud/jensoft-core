/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.metrics.manager;

import org.jensoft.core.plugin.metrics.AbstractMetricsPlugin;
import org.jensoft.core.plugin.metrics.Metrics.MetricsType;
import org.jensoft.core.plugin.metrics.format.IMetricsFormat;
import org.jensoft.core.plugin.metrics.format.MetricsDecimalFormat;

/**
 * <code>AbstractMetricsManager</code> takes the responsibility to solve and create metrics
 * <p>
 * subclass this abstract definition to solve metrics and override {@link #getDeviceMetrics()}
 * <p>
 * 
 * @author sebastien janaud
 */
public abstract class AbstractMetricsManager implements
        MetricsManager {
	
	private AbstractMetricsPlugin<?> metricsPlugin;

    /** default decimal format */
    private IMetricsFormat defaultFormat = new MetricsDecimalFormat();

    /** metrics type */
    private MetricsType type;

    /** metrics format */
    private IMetricsFormat metricsFormat;

   

    /** lock marker */
    private boolean lockMarker = true;

    /** lock label */
    private boolean lockLabel = true;
    

    /**
     * create abstract metrics manager
     */
    public AbstractMetricsManager() {
    }
    
    
    
    public AbstractMetricsPlugin<?> getMetricsPlugin() {
		return metricsPlugin;
	}



	public void setMetricsPlugin(AbstractMetricsPlugin<?> metricsPlugin) {
		this.metricsPlugin = metricsPlugin;
	}



	/* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#lockMarker()
     */
    @Override
    public void lockMarker() {
        lockMarker = true;
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#unlockMarker()
     */
    @Override
    public void unlockMarker() {
        lockMarker = false;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#isLockMarker()
     */
    @Override
    public boolean isLockMarker() {
        return lockMarker;
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#lockLabel()
     */
    @Override
    public void lockLabel() {
        lockLabel = true;
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#unlockLabel()
     */
    @Override
    public void unlockLabel() {
        lockLabel = false;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#isLockLabel()
     */
    @Override
    public boolean isLockLabel() {
        return lockLabel;
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
        this.defaultFormat = defaultFormat;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#getType()
     */
    @Override
    public MetricsType getType() {
        return type;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#setMetricsType(com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType)
     */
    @Override
    public void setMetricsType(MetricsType type) {
        this.type = type;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#getMetricsFormat()
     */
    @Override
    public IMetricsFormat getMetricsFormat() {
        return metricsFormat;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#setMetricsFormat(com.jensoft.core.plugin.metrics.format.IMetricsFormat)
     */
    @Override
    public void setMetricsFormat(IMetricsFormat metricsFormat) {
        this.metricsFormat = metricsFormat;
    }

   
   

}
