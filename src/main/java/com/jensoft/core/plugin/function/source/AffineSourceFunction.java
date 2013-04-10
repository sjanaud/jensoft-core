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
 * <code>AffineSourceFunction</code> defines a basic source with collection of
 * (x,y) sort by X
 * 
 * @author sebastien janaud
 */
public class AffineSourceFunction implements SourceFunction {

	/** function x or function y nature */
	private FunctionNature nature;

	/** source */
	private List<Point2D> source;

	/** serie id */
	private String id;

	/** serie name */
	private String name;

	/** comparator by x or y coordinate */
	private ValueComparator valueComparator = new ValueComparator();

	/**
	 * create default x source function
	 */
	public AffineSourceFunction() {
		this.nature = FunctionNature.XFunction;
	}

	/**
	 * create source function for given nature
	 * @param source
	 * 				the function source
	 * @param nature
	 *            the x or y function nature
	 */
	public AffineSourceFunction(List<Point2D> source,FunctionNature nature) {
		this.nature = nature;
		this.source = source;
		sortFunction();
	}

	/**
	 * create serie for source point
	 * 
	 * @param source
	 */
	public AffineSourceFunction(List<Point2D> source) {
		this();
		this.source = source;
		sortFunction();
	}

	/**
	 * @return the nature
	 */
	public FunctionNature getNature() {
		return nature;
	}

	/**
	 * @param nature
	 *            the nature to set
	 */
	public void setNature(FunctionNature nature) {
		this.nature = nature;
		sortFunction();
	}

	/**
	 * set source of this serie
	 * 
	 * @param source
	 *            the source to set
	 */
	public void setSource(List<Point2D> source) {
		this.source = source;
		sortFunction();
	}

	/**
	 * get the serie name
	 * 
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * set the serie name
	 * 
	 * @param name
	 *            the name to set
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#select(double, double)
	 */
	@Override
	public List<Point2D> select(double start, double end) {
		List<Point2D> select = new ArrayList<Point2D>();
		List<Point2D> source = getSource();
		if (FunctionNature.XFunction == nature) {
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
		
		return select;
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#next(double)
	 */
	@Override
	public Point2D next(double value) {
		if (FunctionNature.XFunction == nature) {
			for (Point2D p : getSource()) {
				if (p.getX() >= value) {
					return p;
				}
			}
		}
		else{
			for (Point2D p : getSource()) {
				if (p.getY() >= value) {
					return p;
				}
			}
		}		
		return null;
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#previous(double)
	 */
	@Override
	public Point2D previous(double value) {
		if (FunctionNature.XFunction == nature) {
			for (Point2D p : getSource()) {
				if (p.getX() <= value) {
					return p;
				}
			}
		}
		else{
			for (Point2D p : getSource()) {
				if (p.getY() <= value) {
					return p;
				}
			}
		}	
		return null;
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#evaluate(double)
	 */
	@Override
	public Point2D evaluate(double value) {
		throw new IllegalAccessError("Affine source function does not provide interpolation.");
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#first()
	 */
	@Override
	public Point2D first() {
		return source.get(0);
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#last()
	 */
	@Override
	public Point2D last() {
		return source.get(source.size() - 1);
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#minFunction()
	 */
	@Override
	public Point2D minFunction() {
		Point2D minFunction = getSource().get(0);
		if(FunctionNature.XFunction == nature){
			for (Point2D p : getSource()) {
				if (p.getY() < minFunction.getY()) {
					minFunction = p;
				}
			}
			
		}
		if(FunctionNature.YFunction == nature){			
			for (Point2D p : getSource()) {
				if (p.getX() < minFunction.getX()) {
					minFunction = p;
				}
			}
		}
		return minFunction;
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#maxFunction()
	 */
	@Override
	public Point2D maxFunction() {
		
		Point2D maxFunction = getSource().get(0);
		if(FunctionNature.XFunction == nature){
			for (Point2D p : getSource()) {
				if (p.getY() > maxFunction.getY()) {
					maxFunction = p;
				}
			}
			
		}
		if(FunctionNature.YFunction == nature){			
			for (Point2D p : getSource()) {
				if (p.getX() > maxFunction.getX()) {
					maxFunction = p;
				}
			}
		}
		return maxFunction;
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#getSource()
	 */
	@Override
	public List<Point2D> getSource() {
		return source;
	}

	/**
	 * sort function
	 */
	public void sortFunction() {
		Collections.sort(source, valueComparator);
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
			if (FunctionNature.XFunction == nature) {
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
