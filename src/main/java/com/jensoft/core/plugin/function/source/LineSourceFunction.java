/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jensoft.core.window.Window2D;

/**
 * <code>LineSourceFunction</code> defines a source which is a sorted points set.
 * 
 * @author sebastien janaud
 */
public class LineSourceFunction extends AbstractSourceFunction {


	/** source */
	private List<Point2D> source;

	/** comparator by x or y coordinate */
	private ValueComparator valueComparator = new ValueComparator();


	/**
	 * create source function with {@link FunctionNature#XFunction} nature.
	 * 
	 * @param source
	 *            the source function data points
	 */
	public LineSourceFunction(List<Point2D> source) {
		super();
		this.source = source;
		sortFunction();
	}

	
	/**
	 *  create source function with {@link FunctionNature#XFunction} nature and specified points array
	 * @param xValues
	 * 			the given x array function values
	 * @param yValues
	 * 			the given y array function values
	 */
	public LineSourceFunction(double[] xValues, double[] yValues) {
		super();
		this.source = createPointsFromArray(xValues, yValues);
		sortFunction();
	}

	/**
	 * create source function for given nature
	 * 
	 * @param source
	 *            the function source
	 * @param nature
	 *            the x or y function nature
	 */
	public LineSourceFunction(List<Point2D> source, FunctionNature nature) {
		super(nature);
		this.source = source;
		sortFunction();
	}
	
	/**
	 * create source function for given nature
	 * 
	 * @param xValues
	 * 				the given x array function values
	 * @param yValues
	 * 				the given y array function values	 * 
	 * @param nature
	 *            the x or y function nature
	 */
	public LineSourceFunction(double[] xValues, double[] yValues, FunctionNature nature) {
		super(nature);
		this.source = createPointsFromArray(xValues, yValues);
		sortFunction();
	}
	

	/**
	 * set source
	 * 
	 * @param source
	 *            the source to set
	 */
	public void setSource(List<Point2D> source) {
		this.source = source;
		sortFunction();
	}

	/**
	 * @return the source
	 */
	public List<Point2D> getSource() {
		return source;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.function.source.SourceFunction#select(double,
	 * double)
	 */
	@Override
	public List<Point2D> solveFunction(double start, double end) {
		List<Point2D> newFunction = new ArrayList<Point2D>();
		List<Point2D> source = getSource();
		if (FunctionNature.XFunction == getNature()) {
			for (Point2D p : source) {
				if (p.getX() >= start && p.getX() <= end) {
					newFunction.add(p);
				}
			}
		} else {
			for (Point2D p : source) {
				if (p.getY() >= start && p.getY() <= end) {
					newFunction.add(p);
				}
			}
		}
		
		if(newFunction.size() >= 1){
			Point2D previous;
			Point2D next;
			if (FunctionNature.XFunction == getNature()) {
				previous = previous(newFunction.get(0).getX());
				next = next(newFunction.get(newFunction.size()-1).getX());
				
			}else{
				previous = previous(newFunction.get(0).getY());
				next = next(newFunction.get(newFunction.size()-1).getY());
			}
			if(previous != null && !previous.equals(newFunction.get(0))){
				newFunction.add(0, previous);
			}
			if(next != null && !next.equals(newFunction.get(newFunction.size()-1))){
				newFunction.add(next);
			}
		}
		else{
			Point2D previous = previous(start);
			Point2D next = next(end);
			
			if(previous != null ){
				newFunction.add(0, previous);
			}
			if(next != null ){
				newFunction.add(next);
			}
		}
		
		return newFunction;
	}

	
	private Point2D next(double value) {
		List<Point2D> functionPoints = getSource();
		
		for (int i = 0; i < functionPoints.size(); i++) {
			Point2D p = functionPoints.get(i);
			if (FunctionNature.XFunction == getNature()) {
				if (p.getX() > value) {
					return p;
				}
			}else{
				if (p.getY() > value) {
					return p;
				}
			}
		}
		return null;
	}


	private Point2D previous(double value) {
		List<Point2D> functionPoints = getSource();
		for (int i = functionPoints.size()-1; i >= 0; i--) {
			Point2D p = functionPoints.get(i);
			if (FunctionNature.XFunction == getNature()) {
				if (p.getX() < value) {
					return p;
				}
			}
			else{
				if (p.getY() < value) {
					return p;
				}
			}
		}
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.function.source.SourceFunction#evaluate(double)
	 */
	@Override
	public Point2D evaluate(double value) {		
		Window2D w2d = getHost().getHost().getWindow2D();		
		
		Point2D previous = previous(value);
		Point2D next = next(value);
		if(previous != null && next != null){
			double coefficient=0;
			double constant = 0;
			if(getNature() == FunctionNature.XFunction){
				coefficient = (next.getY() - previous.getY()) / (next.getX() - previous.getX());
				constant 	= previous.getY() - coefficient * previous.getX();	
				return new Point2D.Double(value, coefficient*value+constant);
			}else{
				coefficient = (next.getX() - previous.getX()) / (next.getY() - previous.getY());
				constant	= previous.getX() - coefficient * previous.getY();
				return new Point2D.Double(coefficient*value+constant,value);
			}
		}
		else{
			return null;
		}
	}
	
	/**
	 * create list of points from x and y arrays.
	 * 
	 * @param xValues
	 * @param yValues
	 * @return list of points
	 */
	public static List<Point2D> createPointsFromArray(double[] xValues, double[] yValues) {
		if (xValues.length != yValues.length) {
			throw new IllegalArgumentException(" x and y  array values length does not match");
		}
		List<Point2D> source = new ArrayList<Point2D>();
		for (int i = 0; i < xValues.length; i++) {
			source.add(new Point2D.Double(xValues[i], yValues[i]));
		}
		return source;
	}


//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.jensoft.core.plugin.function.source.SourceFunction#getFunction()
//	 */
//	@Override
//	public List<Point2D> getFunction() {
//		return source;
//	}
	
	/**
	 * sort function
	 */
	public void sortFunction() {
		if(source != null){
			Collections.sort(source, valueComparator);
		}
	}
	

	/**
	 * value comparator
	 * 
	 * @author Sebastien Janaud
	 */
	class ValueComparator implements Comparator<Point2D> {

		public ValueComparator() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Point2D p2d1, Point2D p2d2) {
			if (FunctionNature.XFunction == getNature()) {
				if (p2d1.getX() > p2d2.getX()) {
					return 1;
				} else if (p2d1.getX() < p2d2.getX()) {
					return -1;
				}
				return 0;
			} else {
				if (p2d1.getY() > p2d2.getY()) {
					return 1;
				} else if (p2d1.getY() < p2d2.getY()) {
					return -1;
				}
				return 0;
			}

		}

	}



}
