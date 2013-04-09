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
	private List<Point2D> interpolateSource = new ArrayList<Point2D>();

	/** the interpolate delta x segment */
	private double delta;

	/**
	 * create an interpolate serie for specified source and delta
	 * 
	 * @param userSource
	 *            the serie to interpolate
	 * @param delta
	 *            the delta to make interpolation
	 */
	public SplineSourceFunction(List<Point2D> userSource, double delta) {
		super(userSource);
		interpolator = new SplineInterpolator();
		this.delta = delta;
		interpolateSource = new ArrayList<Point2D>();
		createInterpolateFunction();
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#setSource(java.util.List)
	 */
	@Override
	public void setSource(List<Point2D> source) {
		super.setSource(source);
		interpolateSource.clear();
		createInterpolateFunction();
	}
	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#setNature(com.jensoft.core.plugin.function.source.FunctionNature)
	 */
	@Override
	public void setNature(FunctionNature nature) {
		super.setNature(nature);
		createInterpolateFunction();
	}

	/**
	 * evaluate point for the specified value
	 * 
	 * @return the evaluate point at the given value
	 */
	@Override
	public Point2D evaluate(double value) {
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

	/**
	 * override method to get interpolate source
	 */
	@Override
	public List<Point2D> getSource() {
		sortFunction();
		List<Point2D> superSource = super.getSource();
		if (function == null) {
			return super.getSource();
		}

		if (interpolateSource.size() > 0) {
			return interpolateSource;
		}
		//interpolateSource.clear();
		
		Point2D pd2Min = superSource.get(0);
		Point2D pd2Max = superSource.get(superSource.size() - 1);
		if(getNature() == FunctionNature.XFunction){
			for (double x = pd2Min.getX(); x <= pd2Max.getX(); x = x + delta) {
				try {
					if(x > pd2Min.getX() && x < pd2Max.getX()){
						interpolateSource.add(new Point2D.Double(x, function.value(x)));
					}
	
				} catch (FunctionEvaluationException e) {
					return super.getSource();
				}
			}
		}
		else{
			for (double y = pd2Min.getY(); y <= pd2Max.getY(); y = y + delta) {
				try {
				
					if(y > pd2Min.getY() && y < pd2Max.getY()){
						System.out.println("value to interpolate : "+y);
						interpolateSource.add(new Point2D.Double(function.value(y),y));
					}
					
	
				} catch (FunctionEvaluationException e) {
					return super.getSource();
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
			List<Point2D> suserSource = super.getSource();
			int len = suserSource.size();
			double[] xValues = new double[len];
			double[] yValues = new double[len];
			for (int i = 0; i < suserSource.size(); i++) {
				Point2D p2dUser = suserSource.get(i);
				xValues[i] = p2dUser.getX();
				yValues[i] = p2dUser.getY();
			}
			
			if(getNature() == FunctionNature.XFunction){
				function = interpolator.interpolate(xValues, yValues);
			}
			else{
				function = interpolator.interpolate(yValues,xValues);
			}
		} catch (MathException e) {
		}
	}

}
