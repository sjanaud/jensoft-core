/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.manager;

import java.awt.Color;
import java.awt.Font;
import java.util.Locale;

import com.jensoft.core.plugin.metrics.format.IMetricsFormat;
import com.jensoft.core.plugin.metrics.format.MetricsDecimalFormat;
import com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType;
import com.jensoft.core.plugin.metrics.geom.MetricsRenderContext;

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
    private Font metricsMedianFont = new Font("Verdana", Font.PLAIN, 10);

    /** metrics major font */
    private Font metricsMajorFont = new Font("Verdana", Font.PLAIN, 12);

    /** metrics render context */
    private MetricsRenderContext renderContext;

    /** lock marker */
    private boolean lockMarker = true;

    /** lock label */
    private boolean lockLabel = true;
    
    /** locale*/
    private Locale locale;

    /**
     * create abstract metrics manager
     */
    public AbstractMetricsManager() {
    }
    
    /**
     * get the locale for this manager
     * @return locale
     */
    public Locale getLocale() {
    	if(locale == null)
    		return Locale.getDefault();
		return locale;
	}

    /**
     * set locale for this manager.
     * @param locale
     */
	public void applyLocalizedMetrics(Locale locale) {
		this.locale = locale;
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

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#getRenderContext()
     */
    @Override
    public MetricsRenderContext getRenderContext() {
        return renderContext;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#setRenderContext(com.jensoft.core.plugin.metrics.geom.MetricsRenderContext)
     */
    @Override
    public void setRenderContext(MetricsRenderContext renderContext) {
        this.renderContext = renderContext;
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

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#getMetricsMarkerColor()
     */
    @Override
    public Color getMetricsMarkerColor() {
        return metricsMarkerColor;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#setMetricsMarkerColor(java.awt.Color)
     */
    @Override
    public void setMetricsMarkerColor(Color metricsColor) {
        metricsMarkerColor = metricsColor;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#getMetricsLabelColor()
     */
    @Override
    public Color getMetricsLabelColor() {
        return metricsLabelColor;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#setMetricsLabelColor(java.awt.Color)
     */
    @Override
    public void setMetricsLabelColor(Color metricsLabelColor) {
        this.metricsLabelColor = metricsLabelColor;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#getMetricsBaseLineColor()
     */
    @Override
    public Color getMetricsBaseLineColor() {
        return metricsBaseLineColor;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#setMetricsBaseLineColor(java.awt.Color)
     */
    @Override
    public void setMetricsBaseLineColor(Color metricsBaseLineColor) {
        this.metricsBaseLineColor = metricsBaseLineColor;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#getMetricsMedianFont()
     */
    @Override
    public Font getMetricsMedianFont() {
        return metricsMedianFont;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#setMetricsMedianFont(java.awt.Font)
     */
    @Override
    public void setMetricsMedianFont(Font metricsFont) {
        metricsMedianFont = metricsFont;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#getMetricsMajorFont()
     */
    @Override
    public Font getMetricsMajorFont() {
        return metricsMajorFont;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#setMetricsMajorFont(java.awt.Font)
     */
    @Override
    public void setMetricsMajorFont(Font metricsMajorFont) {
        this.metricsMajorFont = metricsMajorFont;
    }

}
