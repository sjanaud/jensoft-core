/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.manager;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.metrics.Metrics;
import com.jensoft.core.plugin.metrics.Metrics.MetricsType;
import com.jensoft.core.projection.Projection;

/**
 * <code>FlowMetricsManager</code> takes the responsibility to generate a flow of metrics.
 * 
 * @since 1.0
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
     * format the metrics value
     * @param value
     * @return string
     */
    protected String format(BigDecimal value){
    	
    	//if format if provided, just return format value by formatter
    	if(getMetricsPlugin().getFormat() != null){
    		return getMetricsPlugin().getFormat().format(value.doubleValue());
    	}
    	
    	//else provide internal formating process
    	DecimalFormat formater = null;
    	if(getMetricsPlugin().getLocale() != null)
    		formater = (DecimalFormat)NumberFormat.getInstance(getMetricsPlugin().getLocale());
    	else
    		formater = new DecimalFormat();
    	
    	if(getMetricsPlugin().getSuffix() != null)
    		return formater.format(value.doubleValue())+getMetricsPlugin().getSuffix();
    	else
    		return formater.format(value.doubleValue());
    		
    }
    
      /**
     * get flow metrics
     * 
     * @return metrics flow
     */
    @Override
    public List<Metrics> getDeviceMetrics() {
        deviceMetrics.clear();
        Projection projection = getMetricsPlugin().getProjection();
        
        if(flowEnd <= flowStart)
        	throw new IllegalArgumentException("metrics flow end should be greater than metrics flow start");
        
        
        BigDecimal start = new BigDecimal(flowStart+"");
        BigDecimal end = new BigDecimal(flowEnd+"");
        BigDecimal interval = new BigDecimal(flowInterval+"");
        boolean flag = true;
        int count = 0;
        while(flag){
        	BigDecimal increment = new BigDecimal(count);
        	BigDecimal m = start.add(increment.multiply(interval));
        	Metrics metrics = null;
        	if (getType() == MetricsType.XMetrics) {
        		 Point2D p2dUser = new Point2D.Double(m.doubleValue(), 0);
                 Point2D p2dDevice = projection.userToPixel(p2dUser);
                 metrics = new Metrics(MetricsType.XMetrics);

                 metrics.setDeviceValue(p2dDevice.getX());
                 metrics.setUserValue(m.doubleValue());
                 metrics.setUserValueAsBigDecimal(m);
                 metrics.setMetricsLabel(format(m));
                 
                 if (m.doubleValue() >= projection.getMinX()  && m.doubleValue() <= projection.getMaxX()) {
                	 deviceMetrics.add(metrics);
                 }
        	}
        	else if (getType() == MetricsType.YMetrics) {
        		 Point2D p2dUser = new Point2D.Double(0,m.doubleValue());
                 Point2D p2dDevice = projection.userToPixel(p2dUser);

                 metrics = new Metrics(MetricsType.YMetrics);
                 metrics.setDeviceValue(p2dDevice.getY());
                 metrics.setUserValue(m.doubleValue());
                 metrics.setUserValueAsBigDecimal(m);
                 metrics.setMetricsLabel(format(m));
                 
                 if (m.doubleValue() >= projection.getMinY()  && m.doubleValue() <= projection.getMaxY()) {
                	 deviceMetrics.add(metrics);
                 }
        	}
        	
        	if(m.doubleValue() > end.doubleValue())
        		flag = false;
        	
        	count++;
        }
        

        return deviceMetrics;
    }

}
