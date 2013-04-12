/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.function.analysis.FunctionEvaluationException;
import com.jensoft.core.plugin.function.analysis.MathException;
import com.jensoft.core.plugin.function.analysis.SplineInterpolator;
import com.jensoft.core.plugin.function.analysis.UnivariateRealFunction;
import com.jensoft.core.plugin.function.analysis.UnivariateRealInterpolator;

/**
 * <code>SplineSourceFunction</code> defines a spline source function which is
 * interpolate from source
 * 
 * @author sebastien janaud
 */
public class SplineSourceFunction extends LineSourceFunction {

	/** the univariate real function */
	private UnivariateRealFunction function = null;

	/** the interpolate delta x segment */
	private double delta;

	/**
	 * create an interpolate source for specified super source and delta
	 * 
	 * @param source
	 *            the source to interpolate
	 * @param delta
	 *            the delta to make interpolation
	 */
	public SplineSourceFunction(List<Point2D> source, double delta) {
		super(source);
		this.delta = delta;
	}

	/**
	 * create an interpolate source for specified super source and delta
	 * 
	 * @param xValues
	 *            the given x array function values
	 * @param yValues
	 *            the given y array function values
	 * @param delta
	 *            the delta to make interpolation
	 */
	public SplineSourceFunction(double[] xValues, double[] yValues, double delta) {
		super(xValues, yValues);
		this.delta = delta;
	}

	/**
	 * create an interpolate source for specified super source and delta
	 * 
	 * @param source
	 *            the source to interpolate
	 * @param delta
	 *            the delta to make interpolation
	 */
	public SplineSourceFunction(List<Point2D> source, double delta, FunctionNature nature) {
		super(source, nature);
		this.delta = delta;
	}

	/**
	 * create an interpolate source for specified super source and delta
	 * 
	 * @param xValues
	 *            the given x array function values
	 * @param yValues
	 *            the given y array function values
	 * @param delta
	 *            the delta to make interpolation
	 */
	public SplineSourceFunction(double[] xValues, double[] yValues, double delta, FunctionNature nature) {
		super(xValues, yValues, nature);
		this.delta = delta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.function.source.AffineSourceFunction#setSource
	 * (java.util.List)
	 */
	@Override
	public void setSource(List<Point2D> source) {
		super.setSource(source);
		function = null;
		clearCurrentFunction();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.function.source.AffineSourceFunction#evaluate
	 * (double)
	 */
	@Override
	public Point2D evaluate(double value) {
		if (function == null) {
			createInterpolateFunction();
		}
		Point2D evaluatePoint = null;
		try {
			if (getNature() == FunctionNature.XFunction) {
				evaluatePoint = new Point2D.Double(value, function.value(value));
			} else {
				evaluatePoint = new Point2D.Double(function.value(value), value);
			}
		} catch (FunctionEvaluationException e) {
		}
		return evaluatePoint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.function.source.LineSourceFunction#solveFunction
	 * (double, double)
	 */
	@Override
	public List<Point2D> solveFunction(double start, double end) {
		sortFunction();
		List<Point2D> interpolateSource = new ArrayList<Point2D>();
		List<Point2D> superSource = getSource();

		if (function == null) {
			createInterpolateFunction();
		}
		if (function == null) {
			return getSource();
		}

		Point2D pd2Min = superSource.get(0);
		Point2D pd2Max = superSource.get(superSource.size() - 1);
		if (getNature() == FunctionNature.XFunction) {
			for (double x = pd2Min.getX(); x <= pd2Max.getX(); x = x + delta) {
				try {
					if (x > pd2Min.getX() && x < pd2Max.getX()) {
						interpolateSource.add(new Point2D.Double(x, function.value(x)));
					}

				} catch (FunctionEvaluationException e) {
					return getSource();
				}
			}
		} else {
			for (double y = pd2Min.getY(); y <= pd2Max.getY(); y = y + delta) {
				try {
					if (y > pd2Min.getY() && y < pd2Max.getY()) {
						interpolateSource.add(new Point2D.Double(function.value(y), y));
					}

				} catch (FunctionEvaluationException e) {
					return getSource();
				}
			}
		}

		return interpolateSource;
	}

	
	/**
	 * create interpolate function for given source.
	 */
	private void createInterpolateFunction() {
		try {
			List<Point2D> suserSource = getSource();
			int len = suserSource.size();
			double[] xValues = new double[len];
			double[] yValues = new double[len];
			for (int i = 0; i < suserSource.size(); i++) {
				Point2D p2dUser = suserSource.get(i);
				xValues[i] = p2dUser.getX();
				yValues[i] = p2dUser.getY();
			}
			UnivariateRealInterpolator interpolator = new SplineInterpolator();
			if (getNature() == FunctionNature.XFunction) {
				function = interpolator.interpolate(xValues, yValues);
			} else {
				function = interpolator.interpolate(yValues, xValues);
			}
		} catch (MathException e) {
		}
	}

}
