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
 * <code>SplineSourceFunction</code> defines the user data input with
 * interpolation segment.
 * @author sebastien janaud
 */
public class SplineSourceFunction extends AffineSourceFunction {

	/** the univariate real function */
	private UnivariateRealFunction function = null;

	/** the univariate real interpolator */
	private UnivariateRealInterpolator interpolator;

	/** interpolate segment */
	private List<Point2D> interpolateFunction = new ArrayList<Point2D>();

	/** the interpolate delta x segment */
	private double delta;

	/**
	 * create an interpolate source for specified super source and delta
	 * 
	 * @param userSource
	 *            the source to interpolate
	 * @param delta
	 *            the delta to make interpolation
	 */
	public SplineSourceFunction(List<Point2D> userSource, double delta) {
		super(userSource);
		this.interpolator = new SplineInterpolator();
		this.delta = delta;
		this.interpolateFunction = new ArrayList<Point2D>();
		setNature(FunctionNature.XFunction);
	}
	
	/**
	 * create an interpolate source for specified super source and delta
	 * 
	 * @param userSource
	 *            the serie to interpolate
	 * @param delta
	 *            the delta to make interpolation
	 */
	public SplineSourceFunction(List<Point2D> userSource, double delta,FunctionNature nature) {
		super(userSource);
		this.interpolator = new SplineInterpolator();
		this.delta = delta;
		this.interpolateFunction = new ArrayList<Point2D>();
		setNature(nature);		
	}
	
	/**
	 * create spline source function with {@link FunctionNature#XFunction} nature.
	 * @param xValues
	 * 			the x values array
	 * @param yValues
	 * 			the y values array
	 * @param delta
	 * 			the epsilon for interpolation 
	 */
	public SplineSourceFunction(double[] xValues, double[] yValues, double delta) {
		if (xValues.length != yValues.length) {
			throw new IllegalArgumentException(" x and y  array values length does not match");
		}
		this.interpolator = new SplineInterpolator();
		this.delta = delta;
		this.interpolateFunction = new ArrayList<Point2D>();
		setNature(FunctionNature.XFunction);
		List<Point2D> src =  new ArrayList<Point2D>();
		for (int i = 0; i < xValues.length; i++) {
			src.add(new Point2D.Double(xValues[i], yValues[i]));
		}
		setSource(src);
		sortFunction();
	}

	/**
	 * create spline source function with specified parameters
	 * @param xValues
	 * 			the x values array
	 * @param yValues
	 * 			the y values array
	 * @param delta
	 * 			the epsilon for interpolation 
	 * @param nature
	 * 			the function nature
	 */
	public SplineSourceFunction(double[] xValues, double[] yValues, double delta,FunctionNature nature) {
		if (xValues.length != yValues.length) {
			throw new IllegalArgumentException(" x and y  array values length does not match");
		}
		this.interpolator = new SplineInterpolator();
		this.delta = delta;
		this.interpolateFunction = new ArrayList<Point2D>();
		setNature(nature);
		List<Point2D> src =  new ArrayList<Point2D>();
		for (int i = 0; i < xValues.length; i++) {
			src.add(new Point2D.Double(xValues[i], yValues[i]));
		}
		setSource(src);
		sortFunction();
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#setSource(java.util.List)
	 */
	@Override
	public void setSource(List<Point2D> source) {
		super.setSource(source);
		interpolateFunction.clear();
		function = null;
	}
	
//	@Override
//	public void setNature(FunctionNature nature) {
//		super.setNature(nature);
//		//interpolateFunction.clear();
//		function = null;
//	}
	
	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#evaluate(double)
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

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#getFunction()
	 */
	@Override
	public List<Point2D> getFunction() {
		sortFunction();
		List<Point2D> superSource = getSource();
		if (function == null) {
			createInterpolateFunction();
		}
		if (function == null) {
			return getSource();
		}

		if (interpolateFunction.size() > 0) {
			return interpolateFunction;
		}
		
		Point2D pd2Min = superSource.get(0);
		Point2D pd2Max = superSource.get(superSource.size() - 1);
		if(getNature() == FunctionNature.XFunction){
			for (double x = pd2Min.getX(); x <= pd2Max.getX(); x = x + delta) {
				try {
					if(x > pd2Min.getX() && x < pd2Max.getX()){
						interpolateFunction.add(new Point2D.Double(x, function.value(x)));
					}
	
				} catch (FunctionEvaluationException e) {
					return super.getFunction();
				}
			}
		}
		else{
			for (double y = pd2Min.getY(); y <= pd2Max.getY(); y = y + delta) {
				try {
				
					if(y > pd2Min.getY() && y < pd2Max.getY()){
						interpolateFunction.add(new Point2D.Double(function.value(y),y));
					}
					
	
				} catch (FunctionEvaluationException e) {
					return super.getFunction();
				}
			}
		}

		return interpolateFunction;
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
			
			if(getNature() == FunctionNature.XFunction){
				function = interpolator.interpolate(xValues,yValues);
			}
			else{
				function = interpolator.interpolate(yValues,xValues);
			}
		} catch (MathException e) {
		}
	}

}
