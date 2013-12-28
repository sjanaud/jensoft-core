/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.jensoft.core.palette.NanoChromatique;

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
	 * Abstract definition of circular area background to paint (whole gauge or
	 * area circular portion according to radius and polar coordinate of the
	 * center) that are filled in different style, texture, gradient.
	 */
	public abstract static class Circular extends GaugeBackground {

		/**
		 * Circular background represent a circle area to be paint
		 * 
		 * @since 1.0
		 * @author sebastien janaud
		 * 
		 */
		public abstract static class Gradient extends Circular {

			/** gradient fractions */
			private float[] fractions = { 0, 1 };

			/** gradient colors */
			private Color[] colors = { NanoChromatique.WHITE, NanoChromatique.BLACK };

			/**
			 * create default gradient paint
			 */
			public Gradient() {
				super();
			}

			/**
			 * create gradient paint for the given area
			 * 
			 * @param radius
			 * @param polarRadius
			 * @param polarAngle
			 */
			public Gradient(int radius, double polarRadius, float polarAngle) {
				super(radius, polarRadius, polarAngle);
			}

			/**
			 * create gradient with default area parameter and given colors
			 * 
			 * @param fractions
			 * @param colors
			 */
			public Gradient(float[] fractions, Color[] colors) {
				super();
				this.fractions = fractions;
				this.colors = colors;
			}

			/**
			 * create gradient paint with the given area and gradient parameter
			 * 
			 * @param radius
			 * @param polarRadius
			 * @param polarAngle
			 * @param fractions
			 * @param colors
			 */
			public Gradient(int radius, double polarRadius, float polarAngle, float[] fractions, Color[] colors) {
				super(radius, polarRadius, polarAngle);
				this.fractions = fractions;
				this.colors = colors;
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
				this.fractions = fractions;
				this.colors = colors;
			}

			/**
			 * @return the fractions
			 */
			public float[] getFractions() {
				return fractions;
			}

			/**
			 * @return the colors
			 */
			public Color[] getColors() {
				return colors;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.jensoft.core.plugin.gauge.core.GaugeBackground.Circular#fill
			 * (java.awt.Graphics2D,
			 * com.jensoft.core.plugin.gauge.core.RadialGauge, java.awt.Shape)
			 */
			@Override
			public final void fill(Graphics2D g2d, RadialGauge radialGauge, Shape shape) {
				g2d.setPaint(getGradient(radialGauge));
				g2d.fill(shape);
			}

			/**
			 * get the gradient paint to use
			 * 
			 * @return gradient paint
			 */
			public abstract Paint getGradient(RadialGauge radialGauge);

		}

		/**
		 * <code>LinearGradient</code> takes the responsibility to paint a
		 * circular area with a linear gradient.
		 * 
		 * @since 1.0
		 * @author sebastien janaud
		 * 
		 */
		public static class LinearGradient extends Gradient {

			/** gradient angle */
			private float gradientAngle = 90;

			/**
			 * create gauge texture paint with the given texture
			 * 
			 * @param texturePaint
			 */
			public LinearGradient() {
				super(new float[] { 0f, 1f }, new Color[] { Color.BLACK, new Color(50, 50, 50) });
			}

			/**
			 * create linear gradient according to given parameters
			 * 
			 * @param fractions
			 * @param colors
			 */
			public LinearGradient(float[] fractions, Color[] colors) {
				super(fractions, colors);
			}

			/**
			 * create linear gradient background with given parameters
			 * 
			 * @param radius
			 * @param polarRadius
			 * @param polarAngle
			 */
			public LinearGradient(int radius, double polarRadius, float polarAngle) {
				super(radius, polarRadius, polarAngle);
			}

			/**
			 * create linear gradient according to given parameters
			 * 
			 * @param radius
			 * @param polarRadius
			 * @param polarAngle
			 * @param fractions
			 * @param colors
			 */
			public LinearGradient(int radius, double polarRadius, float polarAngle, float[] fractions, Color[] colors) {
				super(radius, polarRadius, polarAngle, fractions, colors);
			}

			/**
			 * @return the gradientAngle
			 */
			public float getGradientAngle() {
				return gradientAngle;
			}

			/**
			 * @param gradientAngle
			 *            the gradientAngle to set
			 */
			public void setGradientAngle(float gradientAngle) {
				this.gradientAngle = gradientAngle;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.jensoft.core.plugin.gauge.core.GaugeBackground.Circular.
			 * CircularGradient#getGradient()
			 */
			@Override
			public Paint getGradient(RadialGauge radialGauge) {
				Point2D centerDef = radialGauge.getRadialPointAt(getPolarRadius(), getPolarAngle());

				double startX = centerDef.getX() + getRadius() * Math.cos(Math.toRadians(gradientAngle));
				double startY = centerDef.getY() - getRadius() * Math.sin(Math.toRadians(gradientAngle));

				double endX = centerDef.getX() + getRadius() * Math.cos(Math.toRadians(gradientAngle) + Math.PI);
				double endY = centerDef.getY() - getRadius() * Math.sin(Math.toRadians(gradientAngle) + Math.PI);

				Point2D start = new Point2D.Double(startX, startY);
				Point2D end = new Point2D.Double(endX, endY);
				LinearGradientPaint paint = new LinearGradientPaint(start, end, getFractions(), getColors());
				return paint;
			}
		}

		/**
		 * <code>RadialGradient</code> takes the responsibility to paint a
		 * circular area with a radial gradient.
		 * 
		 * @since 1.0
		 * @author sebastien janaud
		 * 
		 */
		public static class RadialGradient extends Gradient {

			/**
			 * create gauge radial gradient paint
			 * 
			 * @param texturePaint
			 */
			public RadialGradient() {
				super(new float[] { 0f, 1f }, new Color[] { Color.BLACK, new Color(50, 50, 50) });
			}

			/**
			 * create gauge radial gradient paint with given shader parameters
			 * 
			 * @param fractions
			 * @param colors
			 */
			public RadialGradient(float[] fractions, Color[] colors) {
				super(fractions, colors);
			}

			/**
			 * create radial background paint with given parameters
			 * 
			 * @param radius
			 * @param polarRadius
			 * @param polarAngle
			 * @param fractions
			 * @param colors
			 */
			public RadialGradient(int radius, double polarRadius, float polarAngle, float[] fractions, Color[] colors) {
				super(radius, polarRadius, polarAngle, fractions, colors);
			}

			/**
			 * create radial background paint with given parameters
			 * 
			 * @param radius
			 * @param polarRadius
			 * @param polarAngle
			 */
			public RadialGradient(int radius, double polarRadius, float polarAngle) {
				super(radius, polarRadius, polarAngle);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.jensoft.core.plugin.gauge.core.GaugeBackground.Circular.
			 * CircularGradient
			 * #getGradient(com.jensoft.core.plugin.gauge.core.RadialGauge)
			 */
			@Override
			public Paint getGradient(RadialGauge radialGauge) {
				Point2D centerDef = radialGauge.getRadialPointAt(getPolarRadius(), getPolarAngle());

				RadialGradientPaint rgp = new RadialGradientPaint(centerDef, 3 * getRadius() / 4, getFractions(), getColors());
				return rgp;
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
