/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>CosinusSourceFunction</code>
 * 
 * @author Sebastien Janaud
 * 
 */
public class CosinusSourceFunction extends  AbstractSourceFunction {
	
	/***/
	private double min;
	private double max;
	private double delta;

	/**
	 * Create Cosinus source function with the default interval and delta interpolation increment
	 */
	public CosinusSourceFunction(double min, double max, double delta) {
		this.min = min;
		this.max = max;
		this.delta = delta;
		setNature(FunctionNature.XFunction);
	}

	/**
	 * Create Cosinus source function
	 */
	public CosinusSourceFunction(double min, double max, double delta, FunctionNature nature) {
		this(min, max, delta);
		setNature(nature);
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#getFunction()
	 */
	@Override
	public List<Point2D> getFunction() {
		List<Point2D> source = new ArrayList<Point2D>();
		for(double d = min; d < max;d = d+delta){
			source.add(evaluate(d));
		}
		return source;
	}

	
	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#select(double, double)
	 */
	@Override
	public List<Point2D> select(double start, double end) {
		this.min = start;
		this.max = end;
		return getFunction();
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#evaluate(double)
	 */
	@Override
	public Point2D evaluate(double value) {
		if (FunctionNature.XFunction == getNature()) {
			return new Point2D.Double(value, Math.cos(value));
		} else {
			return new Point2D.Double(Math.cos(value),value);
		}
	}

	
//	/* (non-Javadoc)
//	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#first()
//	 */
//	@Override
//	public Point2D first() {
//		if(getSource() == null)
//			getFunction();
//		return getSource().get(0);
//	}
//
//	
//	/* (non-Javadoc)
//	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#last()
//	 */
//	@Override
//	public Point2D last() {
//		if(getSource() == null)
//			getFunction();
//		return getSource().get(getSource().size() - 1);
//	}

	
//	/* (non-Javadoc)
//	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#minFunction()
//	 */
//	@Override
//	public Point2D minFunction() {
//		return null;
//	}
//
//	
//	/* (non-Javadoc)
//	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#maxFunction()
//	 */
//	@Override
//	public Point2D maxFunction() {
//		return null;
//	}

}
