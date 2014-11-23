/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.gauge.RadialGaugePlugin;
import com.jensoft.core.projection.Projection;

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

	/** gauge projection */
	private Projection projection;
	
	/**host plugin*/
	private RadialGaugePlugin host;

	/** gauge envelop */
	private GaugeEnvelope envelop;

	/** gauge glass effects */
	private List<GaugeGlass> glasses;

	/** gauge backgrounds */
	private List<GaugeBackground> backgrounds;

	/** gauge bodies */
	private List<GaugeBody> bodies;

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
		backgrounds = new ArrayList<GaugeBackground>();
		glasses = new ArrayList<GaugeGlass>();
		bodies = new ArrayList<GaugeBody>();
	}

	/**
	 * get the gauge center in the device system coordinate according to the
	 * given x and y coordinate define in user coordinate
	 * 
	 * @return gauge center device
	 */
	public Point2D getCenterDevice() {
		double centerX = getProjection().userToPixel(new Point2D.Double(getX(), 0)).getX();
		double centerY = getProjection().userToPixel(new Point2D.Double(0, getY())).getY();
		return new Point2D.Double(centerX, centerY);
	}

	public Point2D getRadialPointAt(double radius, float angleDegree) {
		double centerX = getCenterDevice().getX();
		double centerY = getCenterDevice().getY();
		double shiftCenterX = centerX + radius * Math.cos(Math.toRadians(angleDegree));
		double shiftCenterY = centerY - radius * Math.sin(Math.toRadians(angleDegree));
		return new Point2D.Double(shiftCenterX, shiftCenterY);
	}
	
	
	/**
	 * get host plugin
	 * @return host
	 */
	public RadialGaugePlugin getHost() {
		return host;
	}

	/**
	 * set host plugin
	 * @param host
	 */
	public void setHost(RadialGaugePlugin host) {
		this.host = host;
	}

	/**
	 * get gauge backgrounds
	 * 
	 * @return gauge backgrounds
	 */
	public List<GaugeBackground> getBackgrounds() {
		return backgrounds;
	}

	/**
	 * set gauge backgrounds
	 * 
	 * @param backgrounds
	 */
	public void setBackgrounds(List<GaugeBackground> backgrounds) {
		for (GaugeBackground gaugeBackground : backgrounds) {
			addBackground(gaugeBackground);
		}
	}

	/**
	 * add gauge background
	 * 
	 * @param background
	 */
	public void addBackground(GaugeBackground background) {
		background.setGauge(this);
		this.backgrounds.add(background);
	}

	/**
	 * get projection
	 * 
	 * @return projection
	 */
	public Projection getProjection() {
		return projection;
	}

	/**
	 * set  projection
	 * 
	 * @param projection
	 *            projection
	 */
	public void setProjection(Projection projection) {
		this.projection = projection;
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
	public GaugeEnvelope getEnvelop() {
		return envelop;
	}

	/**
	 * set gauge envelop
	 * 
	 * @param envelop
	 */
	public void setEnvelop(GaugeEnvelope envelop) {
		envelop.setGauge(this);
		this.envelop = envelop;
	}

	/**
	 * get all glasses
	 * 
	 * @return glasses
	 */
	public List<GaugeGlass> getGlasses() {
		return glasses;
	}

	/**
	 * set glasses list
	 * 
	 * @param glasses
	 */
	public void setGlasses(List<GaugeGlass> glasses) {
		for (GaugeGlass gaugeGlass : glasses) {
			addGlass(gaugeGlass);
		}
	}

	/**
	 * add given glass
	 * 
	 * @param effect
	 */
	public void addGlass(GaugeGlass glass) {
		glass.setGauge(this);
		glasses.add(glass);
	}

	/**
	 * add given effects array
	 * 
	 * @param effects
	 */
	public void addGlass(GaugeGlass... glasses) {
		for (int i = 0; i < glasses.length; i++) {
			addGlass(glasses[i]);
		}
	}

	/**
	 * @return the bodies
	 */
	public List<GaugeBody> getBodies() {
		return bodies;
	}

	/**
	 * @param bodies
	 *            the bodies to set
	 */
	public void setBodies(List<GaugeBody> bodies) {
		for (GaugeBody body : bodies) {
			addBody(body);
		}
	}

	/**
	 * add given body
	 * 
	 * @param body
	 */
	public void addBody(GaugeBody body) {
		body.setGauge(this);
		bodies.add(body);
	}

	/**
	 * add given body array
	 * 
	 * @param bodies
	 */
	public void addBody(GaugeBody... bodies) {
		for (int i = 0; i < bodies.length; i++) {
			addBody(bodies[i]);
		}
	}

}
