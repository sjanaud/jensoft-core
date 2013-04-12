/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jensoft.core.plugin.function.analysis.SimpleRegression;

/**
 * <code>LinearRegressionSourceFunction</code> defines the user data input with
 * linear regression segment.
 * 
 * @author sebastien janaud
 */
public class RegressionSourceFunction extends AffineSourceFunction {

	/** simple regression */
	private SimpleRegression regression = null;	

	/** interpolate segment */
	private List<Point2D> interpolateFunction = new ArrayList<Point2D>();

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
		interpolateFunction = new ArrayList<Point2D>();
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#setSource(java.util.List)
	 */
	@Override
	public void setSource(List<Point2D> source) {
		super.setSource(source);
		regression = null;
		interpolateFunction.clear();
	}
	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#setNature(com.jensoft.core.plugin.function.source.FunctionNature)
	 */
	@Override
	public void setNature(FunctionNature nature) {
		super.setNature(nature);
		regression = null;
		interpolateFunction.clear();
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#getSource()
	 */
	@Override
	public List<Point2D> getFunction() {
		if(regression == null){
			createInterpolateFunction();
		}
		if(regression == null){
			return getSource();
		}
		if (interpolateFunction.size() > 0) {
			return interpolateFunction;
		}
		if(getSource() == null){
			return Collections.EMPTY_LIST;
		}
		
		Point2D pd2Min = getSource().get(0);
		Point2D pd2Max = getSource().get(getSource().size()-1);
		if (getNature() == FunctionNature.XFunction) {
			for (double x = pd2Min.getX(); x <= pd2Max.getX(); x = x + delta) {
				interpolateFunction.add(new Point2D.Double(x, regression.predict(x)));
			}
		} else {
			for (double y = pd2Min.getY(); y <= pd2Max.getY(); y = y + delta) {
				interpolateFunction.add(new Point2D.Double(regression.predict(y), y));
			}
		}
		
		return interpolateFunction;
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
		List<Point2D> userSource = super.getFunction();
		for (int i = 0; i < userSource.size(); i++) {
			Point2D p2d = userSource.get(i);
			regression.addData(p2d.getX(), p2d.getY());
		}
	}

}
