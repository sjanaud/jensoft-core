/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.util.Locale;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.metrics.Metrics.Gravity;
import com.jensoft.core.plugin.metrics.Metrics.MetricsNature;
import com.jensoft.core.plugin.metrics.manager.AbstractMetricsManager;
import com.jensoft.core.plugin.metrics.painter.AbstractMetricsPainter;
import com.jensoft.core.plugin.metrics.painter.MetricsGlyphPainter;

/**
 * <code>MetricsPlugin</code> takes the responsibility to draw metrics
 * 
 * @since 2.0.2
 * @author sebastien janaud
 */
public abstract class AbstractMetricsPlugin<M extends AbstractMetricsManager> extends AbstractPlugin {
	/** the metrics manager */
	private M metricsManager;

	/** the metrics painter */
	private AbstractMetricsPainter metricsPainter;
	
	/**Natural or rotate gravity*/
	private Gravity gravity = Gravity.Natural;
	
	/**paint line flag*/
	private boolean baseLinePaint = false;
	
	/**base line color*/
	private Color  baseLineColor;
	
	/**base line stroke*/
	private Stroke baseLineStroke = new BasicStroke();
	
	
	//Metrics marker properties
	private int    minorMarkerSize = 2;
	private Color  minorMarkerColor;
	private Stroke minorMarkerStroke = new BasicStroke();
	
	private int    medianMarkerSize = 4;
	private Color  medianMarkerColor;
	private Stroke medianMarkerStroke = new BasicStroke();
	private int    medianTextOffset = 5;
	private Color  medianTextColor;
	private Font   medianTextFont = new Font("Dialog", Font.PLAIN, 10);
	
	private int    majorMarkerSize = 6;
	private Color  majorMarkerColor;
	private Stroke majorMarkerStroke = new BasicStroke();
	private int    majorTextOffset = 10;
	private Color  majorTextColor;
	private Font   majorTextFont = new Font("Dialog", Font.PLAIN, 12);
	
	/** locale*/
    private Locale locale;
    
    /** metric suffix*/
    private String suffix;
	
	
	/**
	 * Create a new
	 * <code>MetricsPlugin<code> with the specified manager
	 * 
	 * @param manager
	 *            the manager
	 */
	public AbstractMetricsPlugin(M manager) {
		metricsPainter = new MetricsGlyphPainter();
		setPriority(1000);
		setTextAntialising(TextAntialiasing.On);
		setAntialiasing(Antialiasing.On);
		metricsManager = manager;
	}

	/**
	 * get the metrics manager
	 * 
	 * @return manager
	 */
	public M getMetricsManager() {
		return metricsManager;
	}

	/**
	 * set the metrics manager
	 * 
	 * @param metricsManager
	 */
	public void setMetricsManager(M metricsManager) {
		this.metricsManager = metricsManager;
	}
	
	/**
	 * get the metrics painter
	 * 
	 * @return the painter
	 */
	public AbstractMetricsPainter getMetricsPainter() {
		return metricsPainter;
	}

	/**
	 * set the metrics painter
	 * 
	 * @param metricsPainter
	 */
	public void setMetricsPainter(AbstractMetricsPainter metricsPainter) {
		this.metricsPainter = metricsPainter;
	}
	
	/**
     * get the locale
     * @return locale
     */
    public Locale getLocale() {
    	if(locale == null)
    		return Locale.getDefault();
		return locale;
	}

    /**
     * set locale
     * @param locale
     */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	/**
	 * get metrics suffix
	 * @return metrics suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * set metrics suffix
	 * @param suffix
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Gravity getGravity() {
		return gravity;
	}

	public void setGravity(Gravity gravity) {
		this.gravity = gravity;
	}

	public boolean isBaseLinePaint() {
		return baseLinePaint;
	}

	public void setBaseLinePaint(boolean baseLinePaint) {
		this.baseLinePaint = baseLinePaint;
	}

	public Color getBaseLineColor() {
		return baseLineColor;
	}

	public void setBaseLineColor(Color baseLineColor) {
		this.baseLineColor = baseLineColor;
	}

	public Stroke getBaseLineStroke() {
		return baseLineStroke;
	}

	public void setBaseLineStroke(Stroke baseLineStroke) {
		this.baseLineStroke = baseLineStroke;
	}
	
	public void setMarkerColor(Color color){
		this.minorMarkerColor = color;
		this.medianMarkerColor = color;
		this.majorMarkerColor = color;
	}
	
	public void setTextColor(Color color){
		this.medianTextColor = color;
		this.majorTextColor = color;
	}
	
	public void setTextFont(Font font){
		this.medianTextFont = font;
		this.majorTextFont = font;
	}
	
	public Font getMetricsTextFont(Metrics metrics){
		if(metrics.getNature() == MetricsNature.Minor)
			return null;
		if(metrics.getNature() == MetricsNature.Major)
			return this.majorTextFont;
		if(metrics.getNature() == MetricsNature.Median)
			return this.medianTextFont;
		return null;
	}
	
	public int getMetricsTextOffset(Metrics metrics){
		if(metrics.getNature() == MetricsNature.Minor)
			return 0;
		if(metrics.getNature() == MetricsNature.Major)
			return this.majorTextOffset;
		if(metrics.getNature() == MetricsNature.Median)
			return this.medianTextOffset;
		return 0;
	}
	
	public int getMetricsMarkerSize(Metrics metrics){
		if(metrics.getNature() == MetricsNature.Minor)
			return this.minorMarkerSize;
		if(metrics.getNature() == MetricsNature.Major)
			return this.majorMarkerSize;
		if(metrics.getNature() == MetricsNature.Median)
			return this.medianMarkerSize;
		return 0;
	}
	
	public Stroke getMetricsMarkerStroke(Metrics metrics){
		if(metrics.getNature() == MetricsNature.Minor)
			return this.minorMarkerStroke;
		if(metrics.getNature() == MetricsNature.Major)
			return this.majorMarkerStroke;
		if(metrics.getNature() == MetricsNature.Median)
			return this.medianMarkerStroke;
		return null;
	}
	
	public Color getMetricsTextColor(Metrics metrics){
		if(metrics.getNature() == MetricsNature.Minor)
			return null;
		if(metrics.getNature() == MetricsNature.Major)
			return this.majorTextColor;
		if(metrics.getNature() == MetricsNature.Median)
			return this.medianTextColor;
		return null;
	}
	
	public Color getMetricsMarkerColor(Metrics metrics){
		if(metrics.getNature() == MetricsNature.Minor)
			return this.minorMarkerColor;
		if(metrics.getNature() == MetricsNature.Major)
			return this.majorMarkerColor;
		if(metrics.getNature() == MetricsNature.Median)
			return this.medianMarkerColor;
		return null;
	}

	public int getMinorMarkerSize() {
		return minorMarkerSize;
	}

	public void setMinorMarkerSize(int minorMarkerSize) {
		this.minorMarkerSize = minorMarkerSize;
	}

	public Color getMinorMarkerColor() {
		return minorMarkerColor;
	}

	public void setMinorMarkerColor(Color minorMarkerColor) {
		this.minorMarkerColor = minorMarkerColor;
	}

	public Stroke getMinorMarkerStroke() {
		return minorMarkerStroke;
	}

	public void setMinorMarkerStroke(Stroke minorMarkerStroke) {
		this.minorMarkerStroke = minorMarkerStroke;
	}

	public int getMedianMarkerSize() {
		return medianMarkerSize;
	}

	public void setMedianMarkerSize(int medianMarkerSize) {
		this.medianMarkerSize = medianMarkerSize;
	}

	public Color getMedianMarkerColor() {
		return medianMarkerColor;
	}

	public void setMedianMarkerColor(Color medianMarkerColor) {
		this.medianMarkerColor = medianMarkerColor;
	}

	public Stroke getMedianMarkerStroke() {
		return medianMarkerStroke;
	}

	public void setMedianMarkerStroke(Stroke medianMarkerStroke) {
		this.medianMarkerStroke = medianMarkerStroke;
	}

	public int getMedianTextOffset() {
		return medianTextOffset;
	}

	public void setMedianTextOffset(int medianTextOffset) {
		this.medianTextOffset = medianTextOffset;
	}

	public Color getMedianTextColor() {
		return medianTextColor;
	}

	public void setMedianTextColor(Color medianTextColor) {
		this.medianTextColor = medianTextColor;
	}

	public Font getMedianTextFont() {
		return medianTextFont;
	}

	public void setMedianTextFont(Font medianTextFont) {
		this.medianTextFont = medianTextFont;
	}

	public int getMajorMarkerSize() {
		return majorMarkerSize;
	}

	public void setMajorMarkerSize(int majorMarkerSize) {
		this.majorMarkerSize = majorMarkerSize;
	}

	public Color getMajorMarkerColor() {
		return majorMarkerColor;
	}

	public void setMajorMarkerColor(Color majorMarkerColor) {
		this.majorMarkerColor = majorMarkerColor;
	}

	public Stroke getMajorMarkerStroke() {
		return majorMarkerStroke;
	}

	public void setMajorMarkerStroke(Stroke majorMarkerStroke) {
		this.majorMarkerStroke = majorMarkerStroke;
	}

	public int getMajorTextOffset() {
		return majorTextOffset;
	}

	public void setMajorTextOffset(int majorTextOffset) {
		this.majorTextOffset = majorTextOffset;
	}

	public Color getMajorTextColor() {
		return majorTextColor;
	}

	public void setMajorTextColor(Color majorTextColor) {
		this.majorTextColor = majorTextColor;
	}

	public Font getMajorTextFont() {
		return majorTextFont;
	}

	public void setMajorTextFont(Font majorTextFont) {
		this.majorTextFont = majorTextFont;
	}
	
	

}
