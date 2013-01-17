/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.analysis;

/**
 * <p>
 * Interface representing a univariate real interpolator
 * </p>
 * <p>
 * an interpolator defines an univariate function for a given set of points
 * </p>
 */
public interface UnivariateRealInterpolator {

    /**
     * Computes an interpolating function for the data set.
     * 
     * @param xval
     *            the arguments for the interpolation points
     * @param yval
     *            the values for the interpolation points
     * @return a function which interpolates the data set
     * @throws MathException
     *             if arguments violate assumptions made by the interpolation
     *             algorithm
     */
    UnivariateRealFunction interpolate(double xval[], double yval[])
            throws MathException;
}
