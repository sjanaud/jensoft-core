/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * <code>AbstractSourceFunction</code>
 * @author sebastien janaud
 *
 */
public abstract class AbstractSourceFunction implements SourceFunction {
	
	/** source id */
	private String id;

	/** source name */
	private String name;

	/** function x or function y nature */
	private FunctionNature nature;


	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#getFunction()
	 */
	@Override
	public abstract List<Point2D> getFunction();

	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#select(double, double)
	 */
	@Override
	public abstract List<Point2D> select(double start, double end);

	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.function.source.SourceFunction#evaluate(double)
	 */
	@Override
	public abstract Point2D evaluate(double value);

		
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the nature
	 */
	public FunctionNature getNature() {
		return nature;
	}

	/**
	 * @param nature the nature to set
	 */
	public void setNature(FunctionNature nature) {
		this.nature = nature;
	}
	
	

}
