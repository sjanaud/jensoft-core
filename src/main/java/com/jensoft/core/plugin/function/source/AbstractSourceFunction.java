/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.plugin.function.Function;
import com.jensoft.core.window.Window2D;

/**
 * <code>AbstractSourceFunction</code> defines a source function. The current build of function is get 
 * by a call to {@link #getCurrentFunction()} which is the internal result of {@link #solveFunction(double, double)}
 * on window domain [min,max], depends function nature.
 * 
 * <p> this source is hosted by function {@link #host} 
 * </p>
 * 
 * <p> the method {@link AbstractSourceFunction#evaluate(double)} can be used to solved the function result for a particular value.
 * </p>
 * @author sebastien janaud
 * 
 */
public abstract class AbstractSourceFunction implements SourceFunction {

	/** the function that hosts this source function */
	private Function host;

	/** source id */
	private String id;

	/** source name */
	private String name;

	/** function x or function y nature */
	private FunctionNature nature;

	/** current solved source for the current window 2D */
	private List<Point2D> currentFunction;
	
	/**
	 * create abstract x function
	 */
	public AbstractSourceFunction(){
		nature = FunctionNature.XFunction;
	}
	
	/**
	 * create abstract function with specified nature
	 * @param nature
	 */
	public AbstractSourceFunction(FunctionNature nature){
		this.nature = nature;
	}
	
	/**
	 * clear current function
	 */
	public void clearCurrentFunction(){
		if(currentFunction != null)
			currentFunction.clear();
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#getCurrentFunction()
	 */
	@Override
	public List<Point2D> getCurrentFunction() {
		if(currentFunction == null || currentFunction.size()==0){
			Window2D window = getHost().getHost().getWindow2D();
			if(getNature() == FunctionNature.XFunction){
				currentFunction = solveFunction(window.getMinX(), window.getMaxX());
			}
			else{
				currentFunction = solveFunction(window.getMinX(), window.getMaxX());
			}			
		}
		return currentFunction;
	}

	/**
	 * @param currentFunction
	 *            the currentFunction to set
	 */
	public void setCurrentFunction(List<Point2D> currentFunction) {
		this.currentFunction = currentFunction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#getFunction()
	 */
	//@Override
	//public abstract List<Point2D> getFunction();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.function.source.SourceFunction#solveFunction(
	 * double, double)
	 */
	@Override
	public abstract List<Point2D> solveFunction(double start, double end);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.function.source.SourceFunction#evaluate(double)
	 */
	@Override
	public abstract Point2D evaluate(double value);

	/**
	 * @return the host function
	 */
	public Function getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host function to set
	 */
	public void setHost(Function host) {
		this.host = host;
	}

	/**
	 * @return the source id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the source id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the source name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the source name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the source nature
	 */
	public FunctionNature getNature() {
		return nature;
	}

	/**
	 * @param nature
	 *            the source nature to set
	 */
	public void setNature(FunctionNature nature) {
		this.nature = nature;
	}

}
