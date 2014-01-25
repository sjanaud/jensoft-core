/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.graphics;

import java.awt.image.BufferedImage;

/**
 * <p>
 * A stack blur filter can be used to create an approximation of a gaussian blur. The approximation is controlled by the
 * number of times the {@link FastBlurFilter} is applied onto the
 * source picture. The default number of iterations, 3, provides a decent compromise between speed and rendering
 * quality.
 * </p>
 * <p>
 * The force of the blur can be controlled with a radius and the default radius is 3. Since the blur clamps values on
 * the edges of the source picture, you might need to provide a picture with empty borders to avoid artifacts at the
 * edges. The performance of this filter are independant from the radius.
 * </p>
 * 
 * 
 * @since 1.0
 * @author sebastien janaud
 *
 */
public class StackBlurFilter extends AbstractFilter {

    private final int radius;
    private final int iterations;

    /**
     * <p>
     * Creates a new blur filter with a default radius of 3 and 3 iterations.
     * </p>
     */
    public StackBlurFilter() {
        this(3, 3);
    }

    /**
     * <p>
     * Creates a new blur filter with the specified radius and 3 iterations. If the radius is lower than 1, a radius of
     * 1 will be used automatically.
     * </p>
     * 
     * @param radius
     *            the radius, in pixels, of the blur
     */
    public StackBlurFilter(int radius) {
        this(radius, 3);
    }

    /**
     * <p>
     * Creates a new blur filter with the specified radius. If the radius is lower than 1, a radius of 1 will be used
     * automatically. The number of iterations controls the approximation to a gaussian blur. If the number of
     * iterations is lower than 1, one iteration will be used automatically.
     * </p>
     * 
     * @param radius
     *            the radius, in pixels, of the blur
     * @param iterations
     *            the number of iterations to approximate a gaussian blur
     */
    public StackBlurFilter(int radius, int iterations) {
        if (radius < 1) {
            radius = 1;
        }
        if (iterations < 1) {
            iterations = 1;
        }

        this.radius = radius;
        this.iterations = iterations;
    }

    /**
     * <p>
     * Returns the effective radius of the stack blur. If the radius of the blur is 1 and the stack iterations count is
     * 3, then the effective blur radius is 1 * 3 = 3.
     * </p>
     * 
     * @return the number of iterations times the blur radius
     */
    public int getEffectiveRadius() {
        return getIterations() * getRadius();
    }

    /**
     * <p>
     * Returns the radius used by this filter, in pixels.
     * </p>
     * 
     * @return the radius of the blur
     */
    public int getRadius() {
        return radius;
    }

    /**
     * <p>
     * Returns the number of iterations used to approximate a gaussian blur.
     * </p>
     * 
     * @return the number of iterations used by this blur
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dst) {
        int width = src.getWidth();
        int height = src.getHeight();

        if (dst == null) {
            dst = createCompatibleDestImage(src, null);
        }

        int[] srcPixels = new int[width * height];
        int[] dstPixels = new int[width * height];

        GraphicsUtilities.getPixels(src, 0, 0, width, height, srcPixels);
        for (int i = 0; i < iterations; i++) {
            // horizontal pass
            FastBlurFilter.blur(srcPixels, dstPixels, width, height, radius);
            // vertical pass
            FastBlurFilter.blur(dstPixels, srcPixels, height, width, radius);
        }
        // the result is now stored in srcPixels due to the 2nd pass
        GraphicsUtilities.setPixels(dst, 0, 0, width, height, srcPixels);

        return dst;
    }
}
