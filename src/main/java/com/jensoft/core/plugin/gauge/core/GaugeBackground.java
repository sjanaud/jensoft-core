/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * <code>GaugeBackgroundPainter</code> takes the responsibility to paint gauge
 * background.
 * 
 * <p>
 * background painting process is after the envelop painting and just before
 * gauge metrics path
 * </p>
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public abstract class GaugeBackground {

	/**
	 * Abstract definition of circular background (whole gauge or area circular
	 * portion according to radius and polar coordinate of the center) that are
	 * filled in different style, texture, gradient.
	 */
	public abstract static class Circular extends GaugeBackground {

		/**
		 * create circular background with gradient paint
		 * 
		 */
		public static class Gradient extends Circular {
			
			/**
			 * create gauge texture paint with the given texture
			 * 
			 * @param texturePaint
			 */
			public Gradient() {
			}

			/**
			 * create texture background with texture and given circle
			 * definition parameters
			 * 
			 * @param texturePaint
			 * @param radius
			 * @param polarRadius
			 * @param polarAngle
			 */
			public Gradient(int radius, double polarRadius, float polarAngle) {
				super(radius, polarRadius, polarAngle);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.jensoft.core.plugin.gauge.core.bg.GaugeBackgroundPainter.
			 * Circular#fill(java.awt.Graphics2D, java.awt.Shape)
			 */
			@Override
			public void fill(Graphics2D g2d, RadialGauge radialGauge, Shape shape) {
				Point2D centerDef = radialGauge.getRadialPointAt(getPolarRadius(), getPolarAngle());

				// if radius is not set, take gauge radius as the default value
				if (getRadius() == 0) {
					setRadius(radialGauge.getRadius());
				}

				RadialGradientPaint rgp = new RadialGradientPaint(centerDef, 3 * getRadius() / 4, new float[] { 0f, 1f }, new Color[] { Color.BLACK, new Color(50, 50, 50) });
				g2d.setPaint(rgp);
				g2d.fill(shape);

			}

		}

		/**
		 * create circular background with texture paint
		 * 
		 */
		public static class Texture extends Circular {

			/** texture paint */
			private TexturePaint texturePaint;

			/**
			 * create gauge texture paint with the given texture
			 * 
			 * @param texturePaint
			 */
			public Texture(TexturePaint texturePaint) {
				super();
				this.texturePaint = texturePaint;
			}

			/**
			 * create texture background with texture and given circle
			 * definition parameters
			 * 
			 * @param texturePaint
			 * @param radius
			 * @param polarRadius
			 * @param polarAngle
			 */
			public Texture(TexturePaint texturePaint, int radius, double polarRadius, float polarAngle) {
				super(radius, polarRadius, polarAngle);
				this.texturePaint = texturePaint;
			}

			/**
			 * @return the texturePaint
			 */
			public TexturePaint getTexturePaint() {
				return texturePaint;
			}

			/**
			 * @param texturePaint
			 *            the texturePaint to set
			 */
			public void setTexturePaint(TexturePaint texturePaint) {
				this.texturePaint = texturePaint;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.jensoft.core.plugin.gauge.core.bg.GaugeBackgroundPainter.
			 * Circular#fill(java.awt.Graphics2D, java.awt.Shape)
			 */
			@Override
			public void fill(Graphics2D g2d, RadialGauge radialGauge, Shape shape) {
				g2d.setPaint(texturePaint);
				g2d.fill(shape);
			}
		}

		/** radius */
		private int radius = 0;

		/** center point radius in polar coordinate from gauge center */
		private double polarRadius = 0;

		/**
		 * center point angle degree in polar coordinate from zero on right by
		 * convention
		 */
		private float polarAngle = 0;

		/**
		 * create abstract default circular background
		 */
		public Circular() {
		}

		/**
		 * create abstract circular background
		 * 
		 * @param radius
		 * @param polarRadius
		 * @param polarAngle
		 */
		public Circular(int radius, double polarRadius, float polarAngle) {
			super();
			this.radius = radius;
			this.polarRadius = polarRadius;
			this.polarAngle = polarAngle;
		}

		/**
		 * @return the radius
		 */
		public int getRadius() {
			return radius;
		}

		/**
		 * @param radius
		 *            the radius to set
		 */
		public void setRadius(int radius) {
			this.radius = radius;
		}

		/**
		 * @return the polarRadius
		 */
		public double getPolarRadius() {
			return polarRadius;
		}

		/**
		 * @param polarRadius
		 *            the polarRadius to set
		 */
		public void setPolarRadius(double polarRadius) {
			this.polarRadius = polarRadius;
		}

		/**
		 * @return the polarAngle
		 */
		public float getPolarAngle() {
			return polarAngle;
		}

		/**
		 * @param polarAngle
		 *            the polarAngle to set
		 */
		public void setPolarAngle(float polarAngle) {
			this.polarAngle = polarAngle;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.plugin.gauge.core.bg.GaugeBackgroundPainter#
		 * paintBackground(java.awt.Graphics2D,
		 * com.jensoft.core.plugin.gauge.core.RadialGauge)
		 */
		@Override
		public final void paintBackground(Graphics2D g2d, RadialGauge radialGauge) {

			Point2D centerDef = radialGauge.getRadialPointAt(getPolarRadius(), getPolarAngle());

			// if radius is not set, take gauge radius as the default value
			if (getRadius() == 0) {
				setRadius(radialGauge.getRadius());
			}

			Ellipse2D baseShape = new Ellipse2D.Double(centerDef.getX() - radius, centerDef.getY() - radius, 2 * radius, 2 * radius);
			fill(g2d, radialGauge, baseShape);
		}

		/**
		 * fill the circular shape
		 * 
		 * @param g2d
		 * @param shape
		 */
		public abstract void fill(Graphics2D g2d, RadialGauge radialGauge, Shape shape);

	}

	/**
	 * implements this method to create background painter
	 * 
	 * @param g2d
	 * @param radialGauge
	 */
	public abstract void paintBackground(Graphics2D g2d, RadialGauge radialGauge);

}
