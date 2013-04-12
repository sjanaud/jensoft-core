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
	
	/**delta increment for solving*/
	private double delta;

	/**
	 * Create Cosinus source function with delta interpolation increment
	 */
	public CosinusSourceFunction(double delta) {
		super();
		this.delta = delta;
	}

	/**
	 * Create Cosinus source function
	 */
	public CosinusSourceFunction(double min, double max, double delta, FunctionNature nature) {
		super(nature);
		this.delta = delta;
	}
	
	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.AffineSourceFunction#solveFunction(double, double)
	 */
	@Override
	public List<Point2D> solveFunction(double start, double end) {		
		List<Point2D> newFunction = new ArrayList<Point2D>();
		for(double d = start; d <= end; d = d+delta){
			newFunction.add(evaluate(d));
		}
		return newFunction;
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


}
