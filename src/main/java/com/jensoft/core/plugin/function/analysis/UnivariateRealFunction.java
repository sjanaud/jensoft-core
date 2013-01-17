/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.analysis;

/**
 * <p>
 * An interface representing a univariate real function.
 * </p>
 * <p>
 * an univariate real function computes the corresponding value for the given x value
 * </p>
 */
public interface UnivariateRealFunction {

    /**
     * Compute the value for the function.
     * 
     * @param x
     *            the point for which the function value should be computed
     * @return the value
     * @throws FunctionEvaluationException
     *             if the function evaluation fails
     */
    double value(double x) throws FunctionEvaluationException;

}
