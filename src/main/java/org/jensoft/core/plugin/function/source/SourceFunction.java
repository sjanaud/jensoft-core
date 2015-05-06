/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.List;

import org.jensoft.core.plugin.function.Function;

/**
 * <code>SourceFunction</code> defines a collection of points (x,y) for source function y=f(x) or x=f(y)
 * 
 * <h3>X Function<h3>
 * <ul>
 * <li>only and only one Y point P(x,y=f(x)) for given x value</li>
 * <li>sort by x</li>
 * </ul>
 * 
 * <h3>Y Function<h3>
 * <ul>
 * <li>only and only one X point P(x=f(y),y) for given y value</li>
 * <li>sort by y</li>
 * </ul>
 * 
 * <p>
 *  {@link #getCurrentFunction()} method provides the source function points collection (x,y) that define this source function which is the solved for current projection
 * </p>
 * 
 * <p>
 * {@link #solveFunction(double, double)} solved the source function on the given interval
 * </p>
 * 
 * @author Sebastien Janaud
 */
public interface SourceFunction {
	
	/**get nature of this source function, for nature convention  x [y=f(x)] or y [x=f(y)] */
	public FunctionNature getNature();
    
    /** return the current solved function for projection*/
    public List<Point2D> getCurrentFunction();
    
    /**return the host function for this source*/
    public Function getHost();
    
    /**set the host function function for this source*/
    public void setHost(Function function);

    /** solve and select by x or y (depends of nature) the points of the given range */
    public List<Point2D> solveFunction(double start, double end);

    /** evaluate the function point (x,y) for the specified x or y (depends on function nature x or y) */
    public Point2D evaluate(double value);

    /** return the source name */
    public String getName();

    /** set the source name */
    public void setName(String name);
    
    /** return the source id */
    public String getId();

    /** set the source id */
    public void setId(String id);
}
