/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.effect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.Pie.PieNature;
import com.jensoft.core.plugin.pie.PieSlice;

/**
 * <code>PieSliceLinearEffect</code> defines linear shadow effect on pie slice.
 * 
 * @author Sebastien Janaud
 */
public class PieSliceLinearEffect extends AbstractPieSliceEffect {

	/** offset radius */
	private int offsetRadius = 5;

	/** gradient incidence angle degree */
	private int incidenceAngleDegree = 90;

	/** shade fractions */
	private float[] shadeFractions;

	/** shade colors */
	private Color[] shadeColors;

	/** color base start */
	private Color cStart = new Color(60, 60, 60, 180);

	/** color base end */
	private Color cEnd = new Color(255, 255, 255, 180);

	/** default shader fractions */
	private float[] fractions = { 0.0f, 0.49f, 0.51f, 1.0f };

	/** default shader colors */
	private Color[] colors = { cStart, new Color(255, 255, 255, 0), new Color(255, 255, 255, 0), cEnd };

	// Color[] colors = {new Color(60, 60, 60,180) ,new Color(255, 255, 255,
	// 0),new Color(255, 255, 255, 0),new Color(255, 255, 255, 180)};

	/**
	 * create default pie linear effect
	 */
	public PieSliceLinearEffect() {
	}

	/**
	 * create pie slice linear effect with given start angle degree
	 * 
	 * @param incidenceAngleDegree
	 */
	public PieSliceLinearEffect(int incidenceAngleDegree) {
		this();
		this.incidenceAngleDegree = incidenceAngleDegree;
	}

	/**
	 * create pie slice linear effect with given angle degree, offset radius
	 * 
	 * @param incidenceAngleDegree
	 * @param offsetRadius
	 */
	public PieSliceLinearEffect(int incidenceAngleDegree,int offsetRadius) {
		this(incidenceAngleDegree);
		if (offsetRadius < 0) {
			throw new IllegalArgumentException("offset radius should be greater than 0");
		}
		this.offsetRadius = offsetRadius;
	}

	/**
	 * create pie slice linear effect with given angle degree, offset radius and
	 * specified shader
	 * 
	 * @param incidenceAngleDegree
	 * @param offsetRadius
	 * @param shadeFractions
	 * @param shadeColors
	 */
	public PieSliceLinearEffect(int incidenceAngleDegree, int offsetRadius, float[] shadeFractions, Color[] shadeColors) {
		this(incidenceAngleDegree,offsetRadius);
		this.shadeFractions = shadeFractions;
		this.shadeColors = shadeColors;
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
	}

	/**
	 * set the shader parameters
	 * 
	 * @param fractions
	 *            the shader fractions to set
	 * @param colors
	 *            the colors fractions to set
	 */
	public void setShader(float[] fractions, Color[] colors) {
		if (fractions.length != colors.length) {
			throw new IllegalArgumentException("length array does not match");
		}
		shadeFractions = fractions;
		shadeColors = colors;
	}

	/**
	 * set the shader
	 * 
	 * @param shader
	 *            the shader to set
	 */
	public void setShader(Shader shader) {
		if (shader != null) {
			shadeFractions = shader.getFractions();
			shadeColors = shader.getColors();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.pie.painter.effect.AbstractPieSliceEffect#
	 * paintPieSliceEffect(java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie,
	 * com.jensoft.core.plugin.pie.PieSlice)
	 */
	@Override
	protected void paintPieSliceEffect(Graphics2D g2d, Pie pie, PieSlice pieSection) {

		double deltaDegree = pieSection.getPercent() * 360;

		double centerX = pie.getCenterX();
		double centerY = pie.getCenterY();

		double medianDegree = pieSection.getStartAngleDegree() + deltaDegree / 2;
		if (medianDegree > 360) {
			medianDegree = medianDegree - 360;
		}

		Point2D c = null;
		if (pie.getPieNature() == PieNature.User) {
			c = pie.getHostPlugin().getProjection().userToPixel(new Point2D.Double(centerX, centerY));
		}
		if (pie.getPieNature() == PieNature.Device) {
			c = new Point2D.Double(centerX, centerY);
		}

		centerX = c.getX() + pieSection.getDivergence() * Math.cos(Math.toRadians(medianDegree));
		centerY = c.getY() - pieSection.getDivergence() * Math.sin(Math.toRadians(medianDegree));

		double radius = pie.getRadius();

		double innerRadius = radius - offsetRadius;
		Ellipse2D e2 = new Ellipse2D.Double(centerX - innerRadius, centerY - innerRadius, 2 * innerRadius, 2 * innerRadius);

		double startX = centerX + radius * Math.cos(Math.toRadians(incidenceAngleDegree));
		double startY = centerY - radius * Math.sin(Math.toRadians(incidenceAngleDegree));

		double endX = centerX + radius * Math.cos(Math.toRadians(incidenceAngleDegree + 180));
		double endY = centerY - radius * Math.sin(Math.toRadians(incidenceAngleDegree + 180));

		Point2D start = new Point2D.Double(startX, startY);
		Point2D end = new Point2D.Double(endX, endY);

		if (start.equals(end)) {
			return;
		}

		if (shadeFractions != null && shadeColors != null) {
			colors = shadeColors;
			fractions = shadeFractions;
		}

		LinearGradientPaint shader = new LinearGradientPaint(start, end, fractions, colors);
		g2d.setPaint(shader);
		Area a1 = new Area(e2);
		Area a2 = new Area(pieSection.getSlicePath());
		a2.intersect(a1);
		g2d.fill(a2);

	}

}
