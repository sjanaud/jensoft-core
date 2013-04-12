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

/**
 * <code>AffineSourceFunction</code> defines a sorted points set depends of
 * function nature {@link FunctionNature}
 * 
 * @author sebastien janaud
 */
public class AffineSourceFunction extends AbstractSourceFunction {


	/** source */
	private List<Point2D> source;

	/** comparator by x or y coordinate */
	private ValueComparator valueComparator = new ValueComparator();
	
	/**
	 * create default x source function
	 */
	public AffineSourceFunction() {
		setNature(FunctionNature.XFunction);
	}

	/**
	 * create source function with {@link FunctionNature#XFunction} nature.
	 * 
	 * @param source
	 *            the source function data points
	 */
	public AffineSourceFunction(List<Point2D> source) {
		this.source = source;
		setNature(FunctionNature.XFunction);
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
	public AffineSourceFunction(List<Point2D> source, FunctionNature nature) {
		setNature(nature);
		this.source = source;
		sortFunction();
	}

	/**
	 * create source function with {@link FunctionNature#XFunction} nature.
	 * 
	 * @param xValues
	 *            the x values array
	 * @param yValues
	 *            the y values array
	 */
	public AffineSourceFunction(double[] xValues, double[] yValues) {
		if (xValues.length != yValues.length) {
			throw new IllegalArgumentException(" x and y  array values length does not match");
		}
		setNature(FunctionNature.XFunction);
		this.source = new ArrayList<Point2D>();
		for (int i = 0; i < xValues.length; i++) {
			source.add(new Point2D.Double(xValues[i], yValues[i]));
		}
		sortFunction();
	}

	/**
	 * create source function with specified parameters
	 * 
	 * @param xValues
	 *            the x values array
	 * @param yValues
	 *            the y values array
	 * @param nature
	 *            the function nature
	 */
	public AffineSourceFunction(double[] xValues, double[] yValues, FunctionNature nature) {
		if (xValues.length != yValues.length) {
			throw new IllegalArgumentException(" x and y  array values length does not match");
		}
		setNature(nature);
		this.source = new ArrayList<Point2D>();
		for (int i = 0; i < xValues.length; i++) {
			source.add(new Point2D.Double(xValues[i], yValues[i]));
		}
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
	public List<Point2D> select(double start, double end) {
		List<Point2D> select = new ArrayList<Point2D>();
		List<Point2D> source = getFunction();
		if (FunctionNature.XFunction == getNature()) {
			for (Point2D p : source) {
				if (p.getX() >= start && p.getX() <= end) {
					select.add(p);
				}
			}
		} else {
			for (Point2D p : source) {
				if (p.getY() >= start && p.getY() <= end) {
					select.add(p);
				}
			}
		}
		
		if(select.size() >= 1){
			Point2D previous;
			Point2D next;
			if (FunctionNature.XFunction == getNature()) {
				previous = previous(select.get(0).getX());
				next = next(select.get(select.size()-1).getX());
				
			}else{
				previous = previous(select.get(0).getY());
				next = next(select.get(select.size()-1).getY());
			}
			if(previous != null && !previous.equals(select.get(0))){
				select.add(0, previous);
			}
			if(next != null && !next.equals(select.get(select.size()-1))){
				select.add(next);
			}
		}
		else{
			Point2D previous = previous(start);
			Point2D next = next(end);
			
			if(previous != null ){
				select.add(0, previous);
			}
			if(next != null ){
				select.add(next);
			}
		}
		System.out.println("select after :"+select);
		return select;
	}

	
	private Point2D next(double value) {
		List<Point2D> functionPoints = getFunction();
		
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
		List<Point2D> functionPoints = getFunction();
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

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.jensoft.core.plugin.function.source.SourceFunction#first()
//	 */
//	@Override
//	public Point2D first() {
//		return source.get(0);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.jensoft.core.plugin.function.source.SourceFunction#last()
//	 */
//	@Override
//	public Point2D last() {
//		return source.get(source.size() - 1);
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#minFunction()
	 */
//	@Override
//	public Point2D minFunction() {
//		Point2D minFunction = getFunction().get(0);
//		if (FunctionNature.XFunction == nature) {
//			for (Point2D p : getFunction()) {
//				if (p.getY() < minFunction.getY()) {
//					minFunction = p;
//				}
//			}
//
//		}
//		if (FunctionNature.YFunction == nature) {
//			for (Point2D p : getFunction()) {
//				if (p.getX() < minFunction.getX()) {
//					minFunction = p;
//				}
//			}
//		}
//		return minFunction;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#maxFunction()
	 */
//	@Override
//	public Point2D maxFunction() {
//
//		Point2D maxFunction = getFunction().get(0);
//		if (FunctionNature.XFunction == nature) {
//			for (Point2D p : getFunction()) {
//				if (p.getY() > maxFunction.getY()) {
//					maxFunction = p;
//				}
//			}
//
//		}
//		if (FunctionNature.YFunction == nature) {
//			for (Point2D p : getFunction()) {
//				if (p.getX() > maxFunction.getX()) {
//					maxFunction = p;
//				}
//			}
//		}
//		return maxFunction;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#getFunction()
	 */
	@Override
	public List<Point2D> getFunction() {
		return source;
	}
	
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
