/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.effect;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PieSlice;

/**
 * <code>PieRadialEffect</code> create radial shader on pie.
 * 
 * @author Sebastien Janaud
 */
public class PieRadialEffect extends AbstractPieEffect {

	/** shade colors */
	private Color[] shadeColors;

	/** shade fractions */
	private float[] shadeFractions;

	/** effect offset radius */
	private int offsetRadius = 0;

	/** focus radius */
	private int focusRadius = 0;

	/** focus angle */
	private int focusAngle = 270;

	/** slice effect */
	private PieSliceRadialEffect pieSliceRadialEffect;

	/** reload flag */
	private boolean reload = false;

	/** shifting incience flag */
	private boolean shiftingFocusIncidence = false;

	/** shifting radius flag */
	private boolean shiftingFocusRadius = false;

	/**
	 * create Pie Effect
	 */
	public PieRadialEffect() {
	}

	/**
	 * create radial effect with given shader
	 * 
	 * @param shadeColors
	 * @param shadeFractions
	 */
	public PieRadialEffect(Color[] shadeColors, float[] shadeFractions) {
		super();
		this.shadeColors = shadeColors;
		this.shadeFractions = shadeFractions;
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
	 * @return the focusRadius
	 */
	public int getFocusRadius() {
		return focusRadius;
	}

	/**
	 * @param focusRadius
	 *            the focusRadius to set
	 */
	public void setFocusRadius(int focusRadius) {
		this.focusRadius = focusRadius;
		setReload(true);
	}

	/**
	 * @return the focusAngle
	 */
	public int getFocusAngle() {
		return focusAngle;
	}

	/**
	 * @param focusAngle
	 *            the focusAngle to set
	 */
	public void setFocusAngle(int focusAngle) {
		this.focusAngle = focusAngle;
		setReload(true);
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
	 * set the shader parameters
	 * 
	 * @param fractions
	 * @param colors
	 */
	public void setShader(float[] fractions, Color[] colors) {
		if (fractions == null || colors == null) {
			return;
		}

		if (fractions.length != colors.length) {
			throw new IllegalArgumentException("length array does not match");
		}

		shadeFractions = fractions;
		shadeColors = colors;
		setReload(true);
	}

	/**
	 * @return the shiftingFocusIncidence
	 */
	public boolean isShiftingFocusIncidence() {
		return shiftingFocusIncidence;
	}

	/**
	 * @param shiftingFocusIncidence
	 *            the shiftingFocusIncidence to set
	 */
	public void setShiftingFocusIncidence(boolean shiftingFocusIncidence) {
		this.shiftingFocusIncidence = shiftingFocusIncidence;
	}

	/**
	 * @return the shiftingFocusRadius
	 */
	public boolean isShiftingFocusRadius() {
		return shiftingFocusRadius;
	}

	/**
	 * @param shiftingFocusRadius
	 *            the shiftingFocusRadius to set
	 */
	public void setShiftingFocusRadius(boolean shiftingFocusRadius) {
		this.shiftingFocusRadius = shiftingFocusRadius;
	}

	/**
	 * shift focus angle degree for the embedded effect in specified pie
	 * 
	 * @param pie
	 */
	public static void shiftIncidence(Pie pie) {
		AbstractPieEffect effect = pie.getPieEffect();
		if (effect != null && effect instanceof PieRadialEffect) {
			Effect2ShiftIncidence shift = new Effect2ShiftIncidence(pie);
			shift.start();
		}
	}

	/**
	 * shift focus radius degree for the embedded effect in specified pie
	 * 
	 * @param pie
	 */
	public static void shiftRadius(Pie pie) {
		AbstractPieEffect effect = pie.getPieEffect();
		if (effect != null && effect instanceof PieRadialEffect) {
			Effect2ShiftRadius shift = new Effect2ShiftRadius(pie);
			shift.start();
		}
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

		if (pieSliceRadialEffect == null || reload) {
			pieSliceRadialEffect = new PieSliceRadialEffect(offsetRadius);
			pieSliceRadialEffect.setFocusAngle(focusAngle);
			pieSliceRadialEffect.setFocusRadius(focusRadius);
			if (shadeFractions != null && shadeColors != null && shadeFractions.length == shadeColors.length) {
				pieSliceRadialEffect.setShader(shadeFractions, shadeColors);
			}
		}

		for (PieSlice slice : pie.getSlices()) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, slice.getAlpha()));
			pieSliceRadialEffect.paintPieSlice(g2d, pie, slice);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}

	}
}

/**
 * shift effect incidence
 */
class Effect2ShiftIncidence extends Thread {

	private Pie pie;
	private PieRadialEffect effect2;

	public Effect2ShiftIncidence(Pie pie) {
		this.pie = pie;
	}

	@Override
	public void run() {

		try {
			effect2 = (PieRadialEffect) pie.getPieEffect();
			if (effect2.isShiftingFocusIncidence()) {
				throw new InterruptedException("effect is already shifting.");
			}
			pie.getHostPlugin().getWindow2D().getView2D().repaintDevice();
			effect2.setShiftingFocusIncidence(true);
			while (true) {
				for (int i = 0; i < 90; i++) {
					effect2.setFocusAngle(i * 4);
					Thread.sleep(20);
					pie.getHostPlugin().getWindow2D().getView2D().repaintDevice();
				}
			}

		} catch (InterruptedException e) {
			effect2.setShiftingFocusIncidence(false);
			Thread.currentThread().interrupt();
		} finally {
			effect2.setShiftingFocusIncidence(false);
		}

	}
}

/**
 * shift effect focus radius
 */
class Effect2ShiftRadius extends Thread {

	private Pie pie;
	private PieRadialEffect effect2;

	public Effect2ShiftRadius(Pie pie) {
		this.pie = pie;
	}

	@Override
	public void run() {

		try {
			effect2 = (PieRadialEffect) pie.getPieEffect();
			if (effect2.isShiftingFocusRadius()) {
				throw new InterruptedException("effect is already shifting.");
			}
			pie.getHostPlugin().getWindow2D().getView2D().repaintDevice();
			effect2.setShiftingFocusRadius(true);
			double pieRadius = pie.getRadius();
			while (true) {
				for (double i = 0; i < pieRadius; i++) {
					effect2.setFocusRadius((int) i);
					Thread.sleep(50);
					pie.getHostPlugin().getWindow2D().getView2D().repaintDevice();
				}
				for (double i = pieRadius; i > 0; i--) {
					effect2.setFocusRadius((int) i);
					Thread.sleep(50);
					pie.getHostPlugin().getWindow2D().getView2D().repaintDevice();
				}

			}

		} catch (InterruptedException e) {
			effect2.setShiftingFocusRadius(false);
			Thread.currentThread().interrupt();
		} finally {
			effect2.setShiftingFocusRadius(false);
		}

	}
}
