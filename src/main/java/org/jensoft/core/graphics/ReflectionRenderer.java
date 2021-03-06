/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public class ReflectionRenderer {

	private float opacity;

	private float length;

	private boolean blurEnabled;

	private StackBlurFilter stackBlurFilter;

	/**
	 * <p>
	 * Creates a default good looking reflections generator. The default
	 * reflection renderer provides the following default values:
	 * <ul>
	 * <li><i>opacity</i>: 35%</li>
	 * <li><i>length</i>: 40%</li>
	 * <li><i>blurring</i>: disabled with a radius of 1 pixel</li>
	 * </ul>
	 * </p>
	 * <p>
	 * These properties provide a regular, good looking reflection.
	 * </p>
	 * 
	 * @see #getOpacity()
	 * @see #setOpacity(float)
	 * @see #getLength()
	 * @see #setLength(float)
	 * @see #isBlurEnabled()
	 * @see #setBlurEnabled(boolean)
	 * @see #getBlurRadius()
	 * @see #setBlurRadius(int)
	 */
	public ReflectionRenderer() {
		this(0.35f, 0.4f, false);
	}

	/**
	 * <p>
	 * Creates a default good looking reflections generator with the specified
	 * opacity. The default reflection renderer provides the following default
	 * values:
	 * <ul>
	 * <li><i>length</i>: 40%</li>
	 * <li><i>blurring</i>: disabled with a radius of 1 pixel</li>
	 * </ul>
	 * </p>
	 * 
	 * @param opacity
	 *            the opacity of the reflection, between 0.0 and 1.0
	 * @see #getOpacity()
	 * @see #setOpacity(float)
	 * @see #getLength()
	 * @see #setLength(float)
	 * @see #isBlurEnabled()
	 * @see #setBlurEnabled(boolean)
	 * @see #getBlurRadius()
	 * @see #setBlurRadius(int)
	 */
	public ReflectionRenderer(float opacity) {
		this(opacity, 0.4f, false);
	}

	/**
	 * <p>
	 * Creates a reflections generator with the specified properties. Both
	 * opacity and length are numbers between 0.0 (0%) and 1.0 (100%). If the
	 * provided numbers are outside this range, they are clamped.
	 * </p>
	 * <p>
	 * Enabling the blur generates a different kind of reflections that might
	 * look more natural. The default blur radius is 1 pixel
	 * </p>
	 * 
	 * @param opacity
	 *            the opacity of the reflection
	 * @param length
	 *            the length of the reflection
	 * @param blurEnabled
	 *            if true, the reflection is blurred
	 */
	public ReflectionRenderer(float opacity, float length, boolean blurEnabled) {

		stackBlurFilter = new StackBlurFilter(1);

		setOpacity(opacity);
		setLength(length);
		setBlurEnabled(blurEnabled);
	}

	/**
	 * <p>
	 * Gets the opacity used by the factory to generate reflections.
	 * </p>
	 * <p>
	 * The opacity is comprised between 0.0f and 1.0f; 0.0f being fully
	 * transparent and 1.0f fully opaque.
	 * </p>
	 * 
	 * @return this factory's shadow opacity
	 * @see #getOpacity()
	 * @see #createReflection(java.awt.image.BufferedImage)
	 * @see #appendReflection(java.awt.image.BufferedImage)
	 */
	public float getOpacity() {
		return opacity;
	}

	/**
	 * <p>
	 * Sets the opacity used by the factory to generate reflections.
	 * </p>
	 * <p>
	 * Consecutive calls to {@link #createReflection} will all use this opacity
	 * until it is set again.
	 * </p>
	 * <p>
	 * The opacity is comprised between 0.0f and 1.0f; 0.0f being fully
	 * transparent and 1.0f fully opaque. If you provide a value out of these
	 * boundaries, it will be restrained to the closest boundary.
	 * </p>
	 * 
	 * @param opacity
	 *            the generated reflection opacity
	 * @see #setOpacity(float)
	 * @see #createReflection(java.awt.image.BufferedImage)
	 * @see #appendReflection(java.awt.image.BufferedImage)
	 */
	public void setOpacity(float opacity) {
		if (opacity < 0.0f) {
			opacity = 0.0f;
		} else if (opacity > 1.0f) {
			opacity = 1.0f;
		}
		this.opacity = opacity;
	}

	/**
	 * <p>
	 * Returns the length of the reflection. The result is a number between 0.0
	 * and 1.0. This number is the fraction of the height of the source image
	 * that is used to compute the size of the reflection.
	 * </p>
	 * 
	 * @return the length of the reflection, as a fraction of the source image
	 *         height
	 * @see #setLength(float)
	 * @see #createReflection(java.awt.image.BufferedImage)
	 * @see #appendReflection(java.awt.image.BufferedImage)
	 */
	public float getLength() {
		return length;
	}

	/**
	 * <p>
	 * Sets the length of the reflection, as a fraction of the height of the
	 * source image.
	 * </p>
	 * <p>
	 * Consecutive calls to {@link #createReflection} will all use this opacity
	 * until it is set again.
	 * </p>
	 * <p>
	 * The opacity is comprised between 0.0f and 1.0f; 0.0f being fully
	 * transparent and 1.0f fully opaque. If you provide a value out of these
	 * boundaries, it will be restrained to the closest boundary.
	 * </p>
	 * 
	 * @param length
	 *            the length of the reflection, as a fraction of the source
	 *            image height
	 * @see #getLength()
	 * @see #createReflection(java.awt.image.BufferedImage)
	 * @see #appendReflection(java.awt.image.BufferedImage)
	 */
	public void setLength(float length) {
		if (length < 0.0f) {
			length = 0.0f;
		} else if (length > 1.0f) {
			length = 1.0f;
		}
		this.length = length;

	}

	/**
	 * <p>
	 * Returns true if the blurring of the reflection is enabled, false
	 * otherwise. When blurring is enabled, the reflection is blurred to look
	 * more natural.
	 * </p>
	 * 
	 * @return true if blur is enabled, false otherwise
	 * @see #setBlurEnabled(boolean)
	 * @see #createReflection(java.awt.image.BufferedImage)
	 * @see #appendReflection(java.awt.image.BufferedImage)
	 */
	public boolean isBlurEnabled() {
		return blurEnabled;
	}

	/**
	 * <p>
	 * Setting the blur to true will enable the blurring of the reflection when
	 * {@link #createReflection} is invoked.
	 * </p>
	 * <p>
	 * Enabling the blurring of the reflection can yield to more natural results
	 * which may or may not be better looking, depending on the source picture.
	 * </p>
	 * <p>
	 * Consecutive calls to {@link #createReflection} will all use this opacity
	 * until it is set again.
	 * </p>
	 * 
	 * @param blurEnabled
	 *            true to enable the blur, false otherwise
	 * @see #isBlurEnabled()
	 * @see #createReflection(java.awt.image.BufferedImage)
	 * @see #appendReflection(java.awt.image.BufferedImage)
	 */
	public void setBlurEnabled(boolean blurEnabled) {
		this.blurEnabled = blurEnabled;
	}

	/**
	 * <p>
	 * Returns the effective radius, in pixels, of the blur used by this
	 * renderer when {@link #isBlurEnabled()} is true.
	 * </p>
	 * 
	 * @return the effective radius of the blur used when
	 *         <code>isBlurEnabled</code> is true
	 * @see #isBlurEnabled()
	 * @see #setBlurEnabled(boolean)
	 * @see #setBlurRadius(int)
	 * @see #getBlurRadius()
	 */
	public int getEffectiveBlurRadius() {
		return stackBlurFilter.getEffectiveRadius();
	}

	/**
	 * <p>
	 * Returns the radius, in pixels, of the blur used by this renderer when
	 * {@link #isBlurEnabled()} is true.
	 * </p>
	 * 
	 * @return the radius of the blur used when <code>isBlurEnabled</code> is
	 *         true
	 * @see #isBlurEnabled()
	 * @see #setBlurEnabled(boolean)
	 * @see #setBlurRadius(int)
	 * @see #getEffectiveBlurRadius()
	 */
	public int getBlurRadius() {
		return stackBlurFilter.getRadius();
	}

	/**
	 * <p>
	 * Sets the radius, in pixels, of the blur used by this renderer when
	 * {@link #isBlurEnabled()} is true. This radius changes the size of the
	 * generated image when blurring is applied.
	 * </p>
	 * 
	 * @param radius
	 *            the radius, in pixels, of the blur
	 * @see #isBlurEnabled()
	 * @see #setBlurEnabled(boolean)
	 * @see #getBlurRadius()
	 */
	public void setBlurRadius(int radius) {
		stackBlurFilter = new StackBlurFilter(radius);
	}

	/**
	 * <p>
	 * Returns the source image and its reflection. The appearance of the
	 * reflection is defined by the opacity, the length and the blur properties.
	 * </p>
	 * *
	 * <p>
	 * The width of the generated image will be augmented when
	 * {@link #isBlurEnabled()} is true. The generated image will have the width
	 * of the source image plus twice the effective blur radius (see
	 * {@link #getEffectiveBlurRadius()}). The default blur radius is 1 so the
	 * width will be augmented by 6. You might need to take this into account at
	 * drawing time.
	 * </p>
	 * <p>
	 * The returned image height depends on the value returned by
	 * {@link #getLength()} and {@link #getEffectiveBlurRadius()}. For instance,
	 * if the length is 0.5 (or 50%) and the source image is 480 pixels high,
	 * then the reflection will be 246 (480 * 0.5 + 3 * 2) pixels high.
	 * </p>
	 * <p>
	 * You can create only the reflection by calling
	 * {@link #createReflection(java.awt.image.BufferedImage)}.
	 * </p>
	 * 
	 * @param image
	 *            the source image
	 * @return the source image with its reflection below
	 * @see #createReflection(java.awt.image.BufferedImage)
	 */
	public BufferedImage appendReflection(BufferedImage image) {
		BufferedImage reflection = createReflection(image);
		BufferedImage buffer = GraphicsUtilities.createCompatibleTranslucentImage(reflection.getWidth(), image.getHeight() + reflection.getHeight());
		Graphics2D g2 = buffer.createGraphics();

		int effectiveRadius = isBlurEnabled() ? stackBlurFilter.getEffectiveRadius() : 0;
		g2.drawImage(image, effectiveRadius, 0, null);
		g2.drawImage(reflection, 0, image.getHeight() - effectiveRadius, null);

		g2.dispose();
		reflection.flush();

		return buffer;
	}

	/**
	 * <p>
	 * Returns the reflection of the source image. The appearance of the
	 * reflection is defined by the opacity, the length and the blur properties.
	 * </p>
	 * *
	 * <p>
	 * The width of the generated image will be augmented when
	 * {@link #isBlurEnabled()} is true. The generated image will have the width
	 * of the source image plus twice the effective blur radius (see
	 * {@link #getEffectiveBlurRadius()}). The default blur radius is 1 so the
	 * width will be augmented by 6. You might need to take this into account at
	 * drawing time.
	 * </p>
	 * <p>
	 * The returned image height depends on the value returned by
	 * {@link #getLength()} and {@link #getEffectiveBlurRadius()}. For instance,
	 * if the length is 0.5 (or 50%) and the source image is 480 pixels high,
	 * then the reflection will be 246 (480 * 0.5 + 3 * 2) pixels high.
	 * </p>
	 * <p>
	 * The returned image contains <strong>only</strong> the reflection. You
	 * will have to append it to the source image to produce the illusion of a
	 * reflective environement. The method
	 * {@link #appendReflection(java.awt.image.BufferedImage)} provides an easy
	 * way to create an image containing both the source and the reflection.
	 * </p>
	 * 
	 * @param image
	 *            the source image
	 * @return the reflection of the source image
	 * @see #appendReflection(java.awt.image.BufferedImage)
	 */
	public BufferedImage createReflection(BufferedImage image) {
		// if (length == 0.0f) {
		// return GraphicsUtilities.createCompatibleTranslucentImage(1, 1);
		// }
		// length = 1.0f;
		int blurOffset = isBlurEnabled() ? stackBlurFilter.getEffectiveRadius() : 0;
		int height = (int) (image.getHeight() * length);

		BufferedImage buffer = new BufferedImage(image.getWidth() + blurOffset * 2, height + blurOffset * 2, BufferedImage.TYPE_INT_ARGB);// GraphicsUtilities.createCompatibleTranslucentImage(image.getWidth()
																																			// +
																																			// blurOffset
																																			// *
																																			// 2,
																																			// height
																																			// +
																																			// blurOffset
																																			// *
																																			// 2);
		Graphics2D g2 = buffer.createGraphics();

		g2.translate(0, image.getHeight());
		g2.scale(1.0, -1.0);

		g2.drawImage(image, blurOffset, -blurOffset, null);

		g2.scale(1.0, -1.0);
		g2.translate(0, -image.getHeight());

		g2.setComposite(AlphaComposite.DstIn);
		g2.setPaint(new GradientPaint(0.0f, 0.0f, new Color(0.0f, 0.0f, 0.0f, getOpacity()), 0.0f, buffer.getHeight(), new Color(0.0f, 0.0f, 0.0f, 0.0f), true));
		g2.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());

		g2.dispose();
		return isBlurEnabled() ? stackBlurFilter.filter(buffer, null) : buffer;
	}
}
