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
 * <code>PieCubicEffect</code> defines a pie cubic effect
 * 
 * @author Sebastien Janaud
 */
public class PieCubicEffect extends AbstractPieEffect {

	/** start color */
	private Color startColor;

	/** end color */
	private Color endColor;

	

	/** incidence angle degree */
	private int incidenceAngleDegree = 300;

	/** offset radius */
	private int offsetRadius = 4;

	/** section effect */
	private PieSliceCubicEffect sectionEffect;

	private CubicEffectKey key;
	
	private CubicEffectFrame frame;

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
	 * @return the key
	 */
	public CubicEffectKey getCubicKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setCubicKey(CubicEffectKey key) {
		this.key = key;
	}
	
	

	/**
	 * @return the frame
	 */
	public CubicEffectFrame getFrame() {
		return frame;
	}

	/**
	 * @param frame the frame to set
	 */
	public void setFrame(CubicEffectFrame frame) {
		this.frame = frame;
		setCubicKey(frame.getKeyFrame());
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


			if (key != null) {
				sectionEffect.setCubicKey(key);
			}
		}

		for (PieSlice pieSection : pie.getSlices()) {
			sectionEffect.paintPieSlice(g2d, pie, pieSection);
		}

	}

}
