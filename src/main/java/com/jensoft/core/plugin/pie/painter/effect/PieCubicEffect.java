/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.effect;

import java.awt.Color;
import java.awt.Graphics2D;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PieSlice;

/**
 * <code>PieCubicEffect</code> defines a rounded-cubic effect
 * 
 * @author Sebastien Janaud
 */
public class PieCubicEffect extends AbstractPieEffect {

	/** start color */
	private Color startColor;

	/** end color */
	private Color endColor;

	/** start angle delta to add on the incidence */
	private int startAngleDelta = 45;

	/** start angle delta to subtract on the incidence */
	private int endAngleDelta = 90;

	/**
	 * applied fraction on base radius to obtain the virtual radius for start
	 * control
	 */
	private float startControlFractionRadius = 0.5f;

	/**
	 * applied fraction on base radius to obtain the virtual radius for end
	 * control
	 */
	private float endControlFractionRadius = 0.5f;

	/** incidence angle degree */
	private int incidenceAngleDegree = 90;

	/** offset radius */
	private int offsetRadius = 4;

	/** section effect */
	private PieSliceCubicEffect sectionEffect;

	private CubicEffectKey frame;

	/** reload flag */
	private boolean reload = false;

	/**
	 * create default Pie Effect
	 */
	public PieCubicEffect() {
	}

	/**
	 * create Pie Effect
	 * 
	 * @param startColor
	 *            the start color
	 * @param endColor
	 *            the end color
	 * @param incidenceAngleDegree
	 *            the incidence to set
	 */
	public PieCubicEffect(Color startColor, Color endColor, int incidenceAngleDegree) {
		super();
		this.startColor = startColor;
		this.endColor = endColor;
		this.incidenceAngleDegree = incidenceAngleDegree;
	}

	/**
	 * create Pie Effect
	 * 
	 * @param startColor
	 *            the start color
	 * @param endColor
	 *            the end color
	 */
	public PieCubicEffect(Color startColor, Color endColor) {
		super();
		this.startColor = startColor;
		this.endColor = endColor;
	}

	/**
	 * create Pie Effect
	 * 
	 * @param incidenceAngleDegree
	 *            the incidence to set
	 */
	public PieCubicEffect(int incidenceAngleDegree) {
		this.incidenceAngleDegree = incidenceAngleDegree;
	}

	/**
	 * @return the reload
	 */
	public boolean isReload() {
		return reload;
	}

	/**
	 * @param reload
	 *            the reload to set
	 */
	public void setReload(boolean reload) {
		this.reload = reload;
	}

	/**
	 * @return the offsetRadius
	 */
	public int getOffsetRadius() {
		return offsetRadius;
	}

	/**
	 * @param offsetRadius
	 *            the offsetRadius to set
	 */
	public void setOffsetRadius(int offsetRadius) {
		this.offsetRadius = offsetRadius;
		setReload(true);
	}

	/**
	 * @return the startAngleDelta
	 */
	public int getStartAngleDelta() {
		return startAngleDelta;
	}

	/**
	 * @param startAngleDelta
	 *            the startAngleDelta to set
	 */
	public void setStartAngleDelta(int startAngleDelta) {
		if (startAngleDelta < -60 || startAngleDelta > 150) {
			throw new IllegalArgumentException("startAngleDelta out of range [-100,100]");
		}
		this.startAngleDelta = startAngleDelta;
		setReload(true);
	}

	/**
	 * @return the endAngleDelta
	 */
	public int getEndAngleDelta() {
		return endAngleDelta;
	}

	/**
	 * @param endAngleDelta
	 *            the endAngleDelta to set
	 */
	public void setEndAngleDelta(int endAngleDelta) {
		if (endAngleDelta < -100 || endAngleDelta > 150) {
			throw new IllegalArgumentException("endAngleDelta out of range [-100,100]");
		}
		this.endAngleDelta = endAngleDelta;
		setReload(true);
	}

	/**
	 * @return the startControlFractionRadius
	 */
	public float getStartControlFractionRadius() {
		return startControlFractionRadius;
	}

	/**
	 * @param startControlFractionRadius
	 *            the startControlFractionRadius to set
	 */
	public void setStartControlFractionRadius(float startControlFractionRadius) {
		if (startControlFractionRadius < 0 || startControlFractionRadius > 1) {
			throw new IllegalArgumentException("startControlFractionRadius out of range [0,1]");
		}

		this.startControlFractionRadius = startControlFractionRadius;
		setReload(true);
	}

	/**
	 * @return the endControlFractionRadius
	 */
	public float getEndControlFractionRadius() {
		return endControlFractionRadius;
	}

	/**
	 * @param endControlFractionRadius
	 *            the endControlFractionRadius to set
	 */
	public void setEndControlFractionRadius(float endControlFractionRadius) {
		if (endControlFractionRadius < 0 || endControlFractionRadius > 1) {
			throw new IllegalArgumentException("endControlFractionRadius out of range [0,1]");
		}
		this.endControlFractionRadius = endControlFractionRadius;
		setReload(true);
	}

	/**
	 * @return the startColor
	 */
	public Color getStartColor() {
		return startColor;
	}

	/**
	 * @param startColor
	 *            the startColor to set
	 */
	public void setStartColor(Color startColor) {
		this.startColor = startColor;
		setReload(true);
	}

	/**
	 * @return the endColor
	 */
	public Color getEndColor() {
		return endColor;
	}

	/**
	 * @param endColor
	 *            the endColor to set
	 */
	public void setEndColor(Color endColor) {
		this.endColor = endColor;
		setReload(true);
	}

	/**
	 * @return the incidenceAngleDegree
	 */
	public int getIncidenceAngleDegree() {
		return incidenceAngleDegree;
	}

	/**
	 * @param incidenceAngleDegree
	 *            the incidenceAngleDegree to set
	 */
	public void setIncidenceAngleDegree(int incidenceAngleDegree) {
		this.incidenceAngleDegree = incidenceAngleDegree;
		setReload(true);
	}

	/**
	 * @return the frame
	 */
	public CubicEffectKey getFrame() {
		return frame;
	}

	/**
	 * @param frame
	 *            the frame to set
	 */
	public void setFrame(CubicEffectKey frame) {
		this.frame = frame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.pie.painter.effect.AbstractPieEffect#paintPieEffect
	 * (java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie)
	 */
	@Override
	public final void paintPieEffect(Graphics2D g2d, Pie pie) {

		if (sectionEffect == null || reload) {
			sectionEffect = new PieSliceCubicEffect(startColor, endColor, incidenceAngleDegree);
			sectionEffect.setOffsetRadius(offsetRadius);

			sectionEffect.setEndAngleDelta(endAngleDelta);
			sectionEffect.setStartAngleDelta(startAngleDelta);

			sectionEffect.setStartControlFractionRadius(startControlFractionRadius);
			sectionEffect.setEndControlFractionRadius(endControlFractionRadius);

			if (frame != null) {
				sectionEffect.setFrame(frame);
			}
		}

		for (PieSlice pieSection : pie.getSlices()) {
			sectionEffect.paintPieSlice(g2d, pie, pieSection);
		}

	}

}
