/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.analysis;

/**
 * <code>DifferentiableUnivariateRealFunction</code> representing a
 * differentiable univariate real function.
 * 
 */
public interface DifferentiableUnivariateRealFunction extends UnivariateRealFunction {

	/**
	 * Returns the derivative of the function
	 * 
	 * @return the derivative function
	 */
	UnivariateRealFunction derivative();

}
