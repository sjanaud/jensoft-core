/**
 * 
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>CosSource</code>
 * 
 * @author Sebastien Janaud
 * 
 */
public class CosSource implements SourceFunction {
	
	/** source */
	private List<Point2D> source;

	private FunctionNature nature;
	private double min;
	private double max;
	private double delta;

	/**
	 * Create Cosinus source function
	 */
	public CosSource(double min, double max, double delta) {
		this.min = min;
		this.max = max;
		this.delta = delta;
		nature = FunctionNature.XFunction;
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
	}

	/**
	 * Create Cosinus source function
	 */
	public CosSource(double min, double max, double delta, FunctionNature nature) {
		this(min, max, delta);
		this.nature = nature;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.sw2d.core.plugin.function.source.SourceFunction#getSource()
	 */
	@Override
	public List<Point2D> getSource() {
		List<Point2D> source = new ArrayList<Point2D>();
		for(double d = min; d < max;d = d+delta){
			source.add(evaluate(d));
		}
		this.source = source;
		return source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.sw2d.core.plugin.function.source.SourceFunction#select(double
	 * , double)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.sw2d.core.plugin.function.source.SourceFunction#evaluate(
	 * double)
	 */
	@Override
	public Point2D evaluate(double value) {
		if (FunctionNature.XFunction == nature) {
			return new Point2D.Double(value, Math.cos(value));
		} else {
			return new Point2D.Double(Math.cos(value),value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.sw2d.core.plugin.function.source.SourceFunction#next(double)
	 */
	@Override
	public Point2D next(double x) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.sw2d.core.plugin.function.source.SourceFunction#previous(
	 * double)
	 */
	@Override
	public Point2D previous(double x) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#first()
	 */
	@Override
	public Point2D first() {
		if(source == null)
			getSource();
		return source.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#last()
	 */
	@Override
	public Point2D last() {
		if(source == null)
			getSource();
		return source.get(source.size() - 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.sw2d.core.plugin.function.source.SourceFunction#minFunction()
	 */
	@Override
	public Point2D minFunction() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.sw2d.core.plugin.function.source.SourceFunction#maxFunction()
	 */
	@Override
	public Point2D maxFunction() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.sw2d.core.plugin.function.source.SourceFunction#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.sw2d.core.plugin.function.source.SourceFunction#setName(java
	 * .lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#getId()
	 */
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.sw2d.core.plugin.function.source.SourceFunction#setId(java
	 * .lang.String)
	 */
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub

	}

}
