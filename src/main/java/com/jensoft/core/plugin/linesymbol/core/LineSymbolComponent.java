/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.linesymbol.core;

import com.jensoft.core.plugin.linesymbol.LineSymbolPlugin;
import com.jensoft.core.plugin.linesymbol.LineSymbolPlugin.LineNature;

/**
 * <code>LineSymbolComponent</code>
 */
public abstract class LineSymbolComponent {

	/** the line nature of this line component, can be XLine or YLine */
	private LineNature lineNature;

	/**
	 * the line location is along x axis or y axis, location is the location of
	 * the base line
	 */
	private double location;

	/** component thickness */
	private double thickness;

	/** component name */
	private String name;

	/** host plugin for this component */
	private LineSymbolPlugin host;

	/**
	 * constructor
	 */
	public LineSymbolComponent() {

	}

	/**
	 * get the geometry for this line symbol
	 * 
	 * @return the line symbol component geometry
	 */
	public LineSymbolGeometry getGeometry() {
		return new LineSymbolGeometry(this);
	}

	/**
	 * get the host plugin
	 * 
	 * @return the host plugin
	 */
	public LineSymbolPlugin getHost() {
		return host;
	}

	/**
	 * set the host for this component
	 * 
	 * @param host
	 *            the host
	 */
	public void setHost(LineSymbolPlugin host) {
		this.host = host;
	}

	/**
	 * get the component name
	 * 
	 * @return the component name
	 */
	public String getName() {
		return name;
	}

	/**
	 * set the component name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get the line thickness
	 * 
	 * @return the line thickness
	 */
	public double getThickness() {
		return thickness;
	}

	/**
	 * set the thickness for this line symbol
	 * 
	 * @param thickness
	 */
	public void setThickness(double thickness) {
		this.thickness = thickness;
	}

	/**
	 * get the line symbol nature
	 * 
	 * @return the line nature
	 */
	public LineNature getLineNature() {
		return lineNature;
	}

	/**
	 * set the line symbol nature
	 * 
	 * @param lineNature
	 */
	public void setLineNature(LineNature lineNature) {
		this.lineNature = lineNature;
	}

	/**
	 * get the location for the line symbol component
	 * 
	 * @return the location
	 */
	public double getLocation() {
		return location;
	}

	/**
	 * set the location for the line symbol component
	 * 
	 * @param location
	 */
	public void setLocation(double location) {
		this.location = location;
	}

}
