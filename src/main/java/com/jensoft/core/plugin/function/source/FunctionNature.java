/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

/**
 * <code>FunctionNature</code>
 * <p>
 * defines function nature
 * <p>
 * 
 * <h3>X Function<h3>
 * <ul>
 * <li>only and only one Y point P(x,y=f(x)) for given x value</li>
 * <li>sort by x</li>
 * </ul>
 * 
 * <h3>Y Function<h3>
 * <ul>
 * <li>only and only one X point P(x=f(y),y) for given y value</li>
 * <li>sort by y</li>
 * </ul>
 * @author sebastien janaud
 * 
 */
public enum FunctionNature {
	XFunction, YFunction;
}
