/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.plot.spline;

/**
 * Cubic polynomial of the following form
 * 
 * a + b*u + c*u^2 +d*u^3
 * 
 * @author sebastien janaud
 * 
 */
public class Cubic {

	private double a, b, c, d;

	/**
	 * create cubic polynomial
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 */
	public Cubic(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	/**
	 * evaluate cubic
	 * 
	 * @param u
	 * @return eval
	 */
	public double eval(double u) {
		return ((d * u + c) * u + b) * u + a;
	}
}
