/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core;

import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.gauge.core.bg.GaugeBackground;
import com.jensoft.core.plugin.gauge.core.env.EnvelopGaugePainter;
import com.jensoft.core.plugin.gauge.core.glass.GaugeGlassPainter;
import com.jensoft.core.window.Window2D;

/**
 * <code>RadialGauge</code> is defined by a center in user coordinate projection
 * by its center and radius, in pixel from this center.
 * 
 * <p>
 * gauge has an envelop, set of background, set of gauge metrics path, set of
 * gauge text path, set of glasses
 * </p>
 * 
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class RadialGauge {

	/** gauge center x */
	private double x;

	/** gauge center y */
	private double y;

	/** gauge radius */
	private int radius;

	/** gauge window */
	private Window2D window2D;

	/** gauge envelop */
	private EnvelopGaugePainter envelop;

	/** gauge glass effects */
	private List<GaugeGlassPainter> glasses;

	/** gauge backgrounds */
	private List<GaugeBackground> gaugeBackgrounds;

	/** gauges metrics paths */
	private List<GaugeMetricsPath> gaugeMetricsPaths;

	/** gauges texts paths */
	private List<GaugeTextPath> gaugeTextPaths;

	/**
	 * create radial gauge
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 */
	public RadialGauge(double x, double y, int radius) {
		this.radius = radius;
		this.x = x;
		this.y = y;
		gaugeBackgrounds = new ArrayList<GaugeBackground>();
		glasses = new ArrayList<GaugeGlassPainter>();
		gaugeMetricsPaths = new ArrayList<GaugeMetricsPath>();
		gaugeTextPaths = new ArrayList<GaugeTextPath>();
	}

	/**
	 * get the gauge center in the device system coordinate according to the
	 * given x and y coordinate define in user coordinate
	 * 
	 * @return gauge center device
	 */
	public Point2D getCenterDevice() {
		double centerX = getWindow2D().userToPixel(new Point2D.Double(getX(), 0)).getX();
		double centerY = getWindow2D().userToPixel(new Point2D.Double(0, getY())).getY();
		return new Point2D.Double(centerX, centerY);
	}
	
	public Point2D getRadialPointAt(double radius,float angleDegree){
		double centerX = getCenterDevice().getX();
		double centerY = getCenterDevice().getY();
		double shiftCenterX = centerX + radius*Math.cos(Math.toRadians(angleDegree));
		double shiftCenterY = centerY - radius*Math.sin(Math.toRadians(angleDegree));
		return new Point2D.Double(shiftCenterX, shiftCenterY);
	}

	/**
	 * register a gauge metrics path in this gauge
	 * 
	 * @param pathMetrics
	 *            path metrics to register
	 */
	public void registerGaugeMetricsPath(GaugeMetricsPath pathMetrics) {
		pathMetrics.setGauge(this);
		this.gaugeMetricsPaths.add(pathMetrics);
	}

	/**
	 * get gauge registered path metrics list
	 * 
	 * @return path metrics list
	 */
	public List<GaugeMetricsPath> getMetricsPaths() {
		return gaugeMetricsPaths;
	}

	/**
	 * register gauge metrics path list to this gauge
	 * 
	 * @param gaugePathMetrics
	 *            the gauge path list to register
	 */
	public void setGaugeMetricsPath(List<GaugeMetricsPath> gaugePathMetrics) {
		for (GaugeMetricsPath path : gaugePathMetrics) {
			registerGaugeMetricsPath(path);
		}
	}

	/**
	 * register a gauge text path in this gauge
	 * 
	 * @param textPath
	 *            text path to register
	 */
	public void registerGaugeTextPath(GaugeTextPath textPath) {
		textPath.setGauge(this);
		this.gaugeTextPaths.add(textPath);
	}

	/**
	 * get gauge text paths
	 * 
	 * @return gauge text paths
	 */
	public List<GaugeTextPath> getTextPaths() {
		return gaugeTextPaths;
	}

	/**
	 * register gauge text path list
	 * 
	 * @param gaugeTextPaths
	 *            text path list to register
	 */
	public void setGaugeTextPaths(List<GaugeTextPath> gaugeTextPaths) {
		for (GaugeTextPath path : gaugeTextPaths) {
			registerGaugeTextPath(path);
		}
	}

	/**
	 * get gauge backgrounds
	 * 
	 * @return gauge backgrounds
	 */
	public List<GaugeBackground> getGaugeBackgrounds() {
		return gaugeBackgrounds;
	}

	/**
	 * set gauge backgrounds
	 * 
	 * @param gaugeBackgrounds
	 */
	public void setGaugeBackgrounds(List<GaugeBackground> gaugeBackgrounds) {
		this.gaugeBackgrounds = gaugeBackgrounds;
	}

	/**
	 * add gauge background
	 * 
	 * @param gaugeBackground
	 */
	public void addGaugeBackground(GaugeBackground gaugeBackground) {
		this.gaugeBackgrounds.add(gaugeBackground);
	}

	/**
	 * window projection
	 * 
	 * @return projection
	 */
	public Window2D getWindow2D() {
		return window2D;
	}

	/**
	 * set window projection
	 * 
	 * @param window2d
	 *            projection
	 */
	public void setWindow2D(Window2D window2d) {
		window2D = window2d;
	}

	/**
	 * get gauge radius
	 * 
	 * @return gauge radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * set gauge radius
	 * 
	 * @param radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * get gauge center x
	 * 
	 * @return center x
	 */
	public double getX() {
		return x;
	}

	/**
	 * set gauge center x
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * get gauge center y
	 * 
	 * @return center y
	 */
	public double getY() {
		return y;
	}

	/**
	 * set gauge center y
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * get gauge envelop
	 * 
	 * @return gauge envelop
	 */
	public EnvelopGaugePainter getEnvelop() {
		return envelop;
	}

	/**
	 * set gauge envelop
	 * 
	 * @param envelop
	 */
	public void setEnvelop(EnvelopGaugePainter envelop) {
		this.envelop = envelop;
	}

	/**
	 * get all glasses
	 * 
	 * @return glasses
	 */
	public List<GaugeGlassPainter> getGlasses() {
		return glasses;
	}

	/**
	 * set glasses list
	 * 
	 * @param glasses
	 */
	public void setGlasses(List<GaugeGlassPainter> glasses) {
		this.glasses = glasses;
	}

	/**
	 * add given glass
	 * 
	 * @param effect
	 */
	public void addGlass(GaugeGlassPainter glass) {
		glasses.add(glass);
	}

	/**
	 * add given effects array
	 * 
	 * @param effects
	 */
	public void addGlass(GaugeGlassPainter... glasses) {
		for (int i = 0; i < glasses.length; i++) {
			this.glasses.add(glasses[i]);
		}
	}

}
