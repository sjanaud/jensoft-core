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
public class LinearRegressionSourceFunction extends AffineSourceFunction {

	/** simple regression */
	private SimpleRegression regression = null;

	/** the interpolate x segment */
	private double delta;

	/**
	 * create the regression with given based source and delta
	 * 
	 * @param userSource
	 * @param delta
	 */
	public LinearRegressionSourceFunction(List<Point2D> userSource, double delta) {
		super(userSource);
		this.delta = delta;
		createInterpolateFunction();
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#setSource(java.util.List)
	 */
	@Override
	public void setSource(List<Point2D> source) {
		super.setSource(source);
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

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#getSource()
	 */
	@Override
	public List<Point2D> getSource() {
		List<Point2D> regressionSegment = new ArrayList<Point2D>();
		Point2D pd2Min = first();
		Point2D pd2Max = last();
		if (getNature() == FunctionNature.XFunction) {
			for (double x = pd2Min.getX(); x <= pd2Max.getX(); x = x + delta) {
				regressionSegment.add(new Point2D.Double(x, regression.predict(x)));
			}
		} else {
			for (double y = pd2Min.getY(); y <= pd2Max.getY(); y = y + delta) {
				regressionSegment.add(new Point2D.Double(regression.predict(y), y));
			}
		}

		return regressionSegment;
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#evaluate(double)
	 */
	@Override
	public Point2D evaluate(double value) {
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
		List<Point2D> userSource = super.getSource();
		for (int i = 0; i < userSource.size(); i++) {
			Point2D p2d = userSource.get(i);
			regression.addData(p2d.getX(), p2d.getY());
		}
	}

}
