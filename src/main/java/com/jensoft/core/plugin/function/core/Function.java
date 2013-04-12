/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.core;

import java.awt.Color;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.plugin.function.FunctionPlugin;
import com.jensoft.core.plugin.function.source.FunctionNature;
import com.jensoft.core.plugin.function.source.SourceFunction;

/**
 * <code>Function</code>
 * 
 * @author sebastien janaud
 */
public abstract class Function {

    /** function name */
    private String name;

    /** source function */
    private SourceFunction sourceFunction;

    /** curve metrics path */
    private MetricsPathFunction pathFunction;

    /** function plug-in */
    private FunctionPlugin<?> host;

    /** function theme color */
    private Color themeColor;

    /**
     * create function
     * 
     * @param name
     */
    public Function() {
        pathFunction = new MetricsPathFunction();
    }

    /**
     * create function
     * 
     * @param name
     */
    public Function(String name) {
        this();
        this.name = name;
    }

    /**
     * create function with given parameters
     * 
     * @param name
     * @param sourceFunction
     */
    public Function(String name, SourceFunction sourceFunction) {
        this(name);
        setSourceFunction(sourceFunction);
    }

    /**
     * @return the sourceFunction
     */
    public SourceFunction getSourceFunction() {
        return sourceFunction;
    }

    /**
     * @param sourceFunction
     *            the sourceFunction to set
     */
    public void setSourceFunction(SourceFunction sourceFunction) {
        this.sourceFunction = sourceFunction;
        this.sourceFunction.setHost(this);
        pathFunction.setSource(sourceFunction);
    }

    /**
     * @return the themeColor
     */
    public Color getThemeColor() {
        return themeColor;
    }

    /**
     * @param themeColor
     *            the themeColor to set
     */
    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
    }

    /**
     * get function name
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * set function name
     * 
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the pathFunction
     */
    public MetricsPathFunction getPathFunction() {
        return pathFunction;
    }

    /**
     * @return the host
     */
    public FunctionPlugin<?> getHost() {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(FunctionPlugin<?> host) {
        this.host = host;
    }

    /**
     * add metric {@link GlyphMetric} in path function
     * 
     * @param metric
     */
    public void addMetricsLabel(GlyphMetric metric) {
    	if(sourceFunction.getNature() == FunctionNature.XFunction){
    		//if (metric.getValue() >= sourceFunction.first().getX()
              //      && metric.getValue() <= sourceFunction.last().getX()) {
                pathFunction.addMetrics(metric);
           // }
           // else {
            //    throw new IllegalArgumentException("x metric value out of path function");
           // }
    	}else{
    	//	if (metric.getValue() >= sourceFunction.first().getY()
          //          && metric.getValue() <= sourceFunction.last().getY()) {
                pathFunction.addMetrics(metric);
           // }
           // else {
            //    throw new IllegalArgumentException("y metric value out of path function");
            //}
    	}
        
    }

}
