/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>MathSourceFunction</code> defines many kinds of commons math function.
 * 
 * @author Sebastien Janaud
 * 
 */
public class MathSourceFunction extends AbstractSourceFunction {

	/** delta increment for solving */
	private double delta;

	/** common math function */
	private MathFunction mathFunction;

	/**
	 * common math function
	 * @author sebastien
	 *
	 */
	public enum MathFunction {
		cos, cosh, acos, sin, sinh, asin, tan, tanh, atan, abs, log, log10, sqrt, cbrt, exp;
	}

	/**
	 * create specified math function
	 * 
	 * @param mathFunction
	 * @param delta
	 */
	public MathSourceFunction(MathFunction mathFunction, double delta) {
		super();
		this.mathFunction = mathFunction;
		this.delta = delta;
	}

	/**
	 * create specified math function
	 * 
	 * @param mathFunction
	 * @param delta
	 * @param nature
	 */
	public MathSourceFunction(MathFunction mathFunction, double delta, FunctionNature nature) {
		super(nature);
		this.mathFunction = mathFunction;
		this.delta = delta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jensoft.core.plugin.function.source.AffineSourceFunction#solveFunction
	 * (double, double)
	 */
	@Override
	public List<Point2D> solveFunction(double start, double end) {
		List<Point2D> newFunction = new ArrayList<Point2D>();
		for (double d = start; d <= end; d = d + delta) {
			newFunction.add(evaluate(d));
		}
		return newFunction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jensoft.core.plugin.function.source.AffineSourceFunction#evaluate
	 * (double)
	 */
	@Override
	public Point2D evaluate(double value) {
		if (FunctionNature.XFunction == getNature()) {
			if (MathFunction.cos == mathFunction) {
				return new Point2D.Double(value, Math.cos(value));
			} else {
				return new Point2D.Double(value, Math.cos(value));
			}
		} else {
			if (MathFunction.cos == mathFunction) {
				return new Point2D.Double(Math.cos(value), value);
			} else {
				return new Point2D.Double(Math.cos(value), value);
			}
		}
	}

}
