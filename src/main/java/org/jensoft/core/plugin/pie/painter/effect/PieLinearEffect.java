/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pie.painter.effect;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import org.jensoft.core.graphics.Shader;
import org.jensoft.core.plugin.pie.Pie;
import org.jensoft.core.plugin.pie.PieSlice;

/**
 * <code>PieLinearEffect</code>
 * 
 * @author Sebastien Janaud
 */
public class PieLinearEffect extends AbstractPieEffect {

	/** offset radius */
	private int offsetRadius = 10;

	/** incidence angle degree */
	private int incidenceAngleDegree = 90;

	/** section effect */
	private PieSliceLinearEffect pieSliceLinearEffect;

	/** shade fractions */
	private float[] shadeFractions;

	/** shade colors */
	private Color[] shadeColors;

	/** reload flag */
	private boolean reload = false;

	/** shifting flag */
	private boolean shifting = false;

	/**
	 * create a default pie linear effect with default angle degree, offset
	 * radius and shader parameters create default effect
	 */
	public PieLinearEffect() {
	}

	/**
	 * @param incidenceAngleDegree
	 */
	public PieLinearEffect(int incidenceAngleDegree) {
		this();
		this.incidenceAngleDegree = incidenceAngleDegree;
	}

	/**
	 * create a pie linear effect with given parameters
	 * 
	 * @param incidenceAngleDegree
	 * @param offsetRadius
	 *            the offset radius, must be greater than 0
	 */
	public PieLinearEffect(int incidenceAngleDegree, int offsetRadius) {
		this(incidenceAngleDegree);
		if (offsetRadius < 0) {
			throw new IllegalArgumentException("offset radius should be greater than 0");
		}
		this.offsetRadius = offsetRadius;
	}

	/**
	 * create a linear effect with given angle degree, offset radius and shader
	 * parameters
	 * 
	 * @param incidenceAngleDegree
	 * @param offsetRadius
	 * @param shadeFractions
	 * @param shadeColors
	 * 
	 */
	public PieLinearEffect(int incidenceAngleDegree, int offsetRadius, float[] shadeFractions, Color[] shadeColors) {
		this(incidenceAngleDegree, offsetRadius);
		this.shadeFractions = shadeFractions;
		this.shadeColors = shadeColors;
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
		if (offsetRadius < 0) {
			throw new IllegalArgumentException("offset radius should be greater than 0");
		}
		this.offsetRadius = offsetRadius;
		setReload(true);
	}

	/**
	 * set the shadow parameters
	 * 
	 * @param fractions
	 * @param colors
	 */
	public void setShader(float[] fractions, Color[] colors) {
		if (fractions.length != colors.length) {
			throw new IllegalArgumentException("length array does not match");
		}
		shadeFractions = fractions;
		shadeColors = colors;
	}

	/**
	 * set the shadow parameters
	 * 
	 * @param shader
	 */
	public void setShader(Shader shader) {
		shadeFractions = shader.getFractions();
		shadeColors = shader.getColors();
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
	 * shift incidence angle degree for the embedded effect in specified pie
	 * 
	 * @param pie
	 */
	public static Effect1ShiftIncidence shiftIncidence(Pie pie) {
		AbstractPieEffect effect = pie.getPieEffect();
		if (effect != null && (effect instanceof PieLinearEffect || effect instanceof PieCompoundEffect)) {
			Effect1ShiftIncidence shift = new Effect1ShiftIncidence(pie);
			shift.start();
			return shift;
		}
		return null;
	}

	/**
	 * @return the shifting
	 */
	public boolean isShifting() {
		return shifting;
	}

	/**
	 * @param shifting
	 *            the shifting to set
	 */
	public void setShifting(boolean shifting) {
		this.shifting = shifting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jensoft.core.plugin.pie.painter.effect.AbstractPieEffect#paintPieEffect
	 * (java.awt.Graphics2D, org.jensoft.core.plugin.pie.Pie)
	 */
	@Override
	public final void paintPieEffect(Graphics2D g2d, Pie pie) {
		if (pieSliceLinearEffect == null || reload) {
			pieSliceLinearEffect = new PieSliceLinearEffect(incidenceAngleDegree,offsetRadius);

			if (shadeFractions != null && shadeColors != null) {
				pieSliceLinearEffect.setShader(shadeFractions, shadeColors);
			}
		}

		for (PieSlice slice : pie.getSlices()) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, slice.getAlpha()));
			pieSliceLinearEffect.paintPieSlice(g2d, pie, slice);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}

	}

	/**
	 * shift effect incidence
	 */
	public static class Effect1ShiftIncidence extends Thread {

		private Pie pie;
		private PieLinearEffect effect1;

		public Effect1ShiftIncidence(Pie pie) {
			this.pie = pie;
		}

		@Override
		public void run() {

			try {

				if (pie.getPieEffect() instanceof PieLinearEffect) {
					effect1 = (PieLinearEffect) pie.getPieEffect();
					if (effect1.isShifting()) {
						throw new InterruptedException("effect is already shifting.");
					}
					pie.getHostPlugin().getProjection().getView().repaintDevice();
					effect1.setShifting(true);
					while (true) {
						for (int i = 0; i < 90; i++) {
							effect1.setIncidenceAngleDegree(i * 4);
							Thread.sleep(20);
							pie.getHostPlugin().getProjection().getView().repaintDevice();
						}
					}
				} else if (pie.getPieEffect() instanceof PieCompoundEffect) {
					PieCompoundEffect pc = (PieCompoundEffect) pie.getPieEffect();
					AbstractPieEffect[] effects = pc.getEffects();
					for (int i = 0; i < effects.length; i++) {
						if (effects[i] instanceof PieLinearEffect) {
							effect1 = (PieLinearEffect) effects[i];
							if (effect1.isShifting()) {
								throw new InterruptedException("effect is already shifting.");
							}
							pie.getHostPlugin().getProjection().getView().repaintDevice();
							while (true) {
								for (int j = 0; j < 90; j++) {
									effect1.setIncidenceAngleDegree(j * 4);
									Thread.sleep(20);
									pie.getHostPlugin().getProjection().getView().repaintDevice();
								}
							}
						}
					}
				}

			} catch (InterruptedException e) {
				effect1.setShifting(false);
				Thread.currentThread().interrupt();
			} finally {
				effect1.setShifting(false);
			}

		}
	}
}
