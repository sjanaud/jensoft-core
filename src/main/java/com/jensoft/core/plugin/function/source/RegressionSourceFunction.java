/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.function.analysis.SimpleRegression;

/**
 * <code>LinearRegressionSourceFunction</code> defines the user data input with
 * linear regression segment.
 * 
 * @author sebastien janaud
 */
public class RegressionSourceFunction extends LineSourceFunction {

	/** simple regression */
	private SimpleRegression regression = null;	

	/** the interpolate increment */
	private double delta;

	/**
	 * create the regression with given based source and delta
	 * 
	 * @param userSource
	 * @param delta
	 */
	public RegressionSourceFunction(List<Point2D> userSource, double delta) {
		super(userSource);
		this.delta = delta;
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#setSource(java.util.List)
	 */
	@Override
	public void setSource(List<Point2D> source) {
		super.setSource(source);
		regression = null;
		clearCurrentFunction();
	}
	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.LineSourceFunction#solveFunction(double, double)
	 */
	@Override
	public List<Point2D> solveFunction(double start, double end) {
		if(regression == null){
			createInterpolateFunction();
		}
		if(regression == null){
			return getSource();
		}		
		List<Point2D> newFunction = new ArrayList<Point2D>();
		Point2D pd2Min = getSource().get(0);
		Point2D pd2Max = getSource().get(getSource().size()-1);
		if (getNature() == FunctionNature.XFunction) {
			for (double x = pd2Min.getX(); x <= pd2Max.getX(); x = x + delta) {
				newFunction.add(new Point2D.Double(x, regression.predict(x)));
			}
		} else {
			for (double y = pd2Min.getY(); y <= pd2Max.getY(); y = y + delta) {
				newFunction.add(new Point2D.Double(regression.predict(y), y));
			}
		}
		
		return newFunction;
	}
	

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#evaluate(double)
	 */
	@Override
	public Point2D evaluate(double value) {
		if(regression == null){
			createInterpolateFunction();
		}
		Point2D evaluatePoint = null;
		try {
			if (getNature() == FunctionNature.XFunction) {
				evaluatePoint = new Point2D.Double(value, regression.predict(value));
			} else {
				evaluatePoint = new Point2D.Double(regression.predict(value),value);
			}
		} catch (Exception e) {
		}
		return evaluatePoint;
	}

	/**
	 * create linear regression for the source
	 */
	public void createInterpolateFunction() {
		regression = new SimpleRegression();
		List<Point2D> source = getSource();
		for (int i = 0; i < source.size(); i++) {
			Point2D p2d = source.get(i);
			regression.addData(p2d.getX(), p2d.getY());
		}
	}

}
