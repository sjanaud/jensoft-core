/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge;

import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.gauge.core.AbstractGaugePainter;
import com.jensoft.core.plugin.gauge.core.BackgroundGaugePainter;
import com.jensoft.core.plugin.gauge.core.BodyGaugePainter;
import com.jensoft.core.plugin.gauge.core.ConstructorGaugePainter;
import com.jensoft.core.plugin.gauge.core.EnvelopGaugePainter;
import com.jensoft.core.plugin.gauge.core.GlassGaugePainter;
import com.jensoft.core.window.Window2D;

/**
 * <code>RadialGauge</code>
 * 
 * @author sebastien janaud
 * 
 */
public class RadialGauge {

	/**gauge center x*/
	private double x;
	
	/**gauge center y*/
	private double y;
	
	/**gauge radius*/
	private int radius;
	
	/**gauge window*/
	private Window2D window2D;

	/**gauge envelop*/
	private EnvelopGaugePainter envelop;
	
	/**gauge glass effects*/
	private List<GlassGaugePainter> effects;
	
	//?? really need
	private ConstructorGaugePainter constructor;
	private BackgroundGaugePainter background;
	private BodyGaugePainter body;

	
	/**
	 * create radial gauge
	 * @param x
	 * @param y
	 * @param radius
	 */
	public RadialGauge(double x, double y, int radius) {
		this.radius = radius;
		this.x = x;
		this.y = y;
		effects = new ArrayList<GlassGaugePainter>();

	}

	public Window2D getWindow2D() {
		return window2D;
	}

	public void setWindow2D(Window2D window2d) {
		window2D = window2d;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public EnvelopGaugePainter getEnvelop() {
		return envelop;
	}

	public void setEnvelop(EnvelopGaugePainter envelop) {
		envelop.setGauge(this);
		this.envelop = envelop;
	}

	public AbstractGaugePainter getBackground() {
		return background;
	}

	public void setBackground(BackgroundGaugePainter background) {
		background.setGauge(this);
		this.background = background;
	}

	/**
	 * get gauge body
	 * @return gauge body
	 */
	public BodyGaugePainter getBody() {
		return body;
	}

	/**
	 * set gauge body
	 * @param body
	 */
	public void setBody(BodyGaugePainter body) {
		body.setGauge(this);
		this.body = body;
	}

	/**
	 * get all effects
	 * @return effects
	 */
	public List<GlassGaugePainter> getEffects() {
		return effects;
	}

	/**
	 * set effects list
	 * @param effects
	 */
	public void setEffects(List<GlassGaugePainter> effects) {
		this.effects = effects;
	}

	/**
	 * add given effect
	 * @param effect
	 */
	public void addEffect(GlassGaugePainter effect) {
		effect.setGauge(this);
		effects.add(effect);
	}
	
	/**
	 * add given effects array
	 * @param effects
	 */
	public void addEffect(GlassGaugePainter...effects) {
		for (int i = 0; i < effects.length; i++) {
			effects[i].setGauge(this);
			this.effects.add(effects[i]);
		}
	}

	public ConstructorGaugePainter getConstructor() {
		return constructor;
	}

	public void setConstructor(ConstructorGaugePainter constructor) {
		constructor.setGauge(this);
		this.constructor = constructor;
	}

}
