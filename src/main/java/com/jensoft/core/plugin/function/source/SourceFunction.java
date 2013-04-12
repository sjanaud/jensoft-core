/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * <code>SourceFunction</code> defines a collection of points (x,y) for source function y=f(x) or x=f(y)
 * 
 * <h3>X Function<h3>
 * <ul>
 * <li>only and only one point P(x,y=f(x)) for given x value</li>
 * <li>sort by x</li>
 * </ul>
 * 
 * <h3>Y Function<h3>
 * <ul>
 * <li>only and only one point P(x=f(y),y) for given y value</li>
 * <li>sort by y</li>
 * </ul>
 * 
 * <p>
 *  {@link #getFunction()} method provides the point collection (x,y) that define this source function
 * </p>
 * 
 * @author Sebastien Janaud
 */
public interface SourceFunction {
	
	/**get nature of this source function*/
	public FunctionNature getNature();

    /** return all known points for this function*/
    public List<Point2D> getFunction();

    /** select by x or y (depends of nature) the points of the given range */
    public List<Point2D> select(double start, double end);

    /** evaluate the function point (x,y) for the specified x or y (depends on function nature x or y) */
    public Point2D evaluate(double value);

    /** get next point in the source after the specified x or y (depends on function nature x or y)*/
    //public Point2D next(double value);

    /** get previous point in the source before specified x or y (depends on function nature x or y)*/
    //public Point2D previous(double value);

    /** return point corresponding to the min x or y (depends on function nature x or y), assumes that source is sorted*/
    //public Point2D first();

    /** return point corresponding to the max x or y (depends on function nature x or y), assumes that source is sorted */
    //public Point2D last();

    /** return point corresponding to the min peak of function (depends on function nature x or y)*/
   // public Point2D minFunction();
    
    /** return point corresponding to the max peak of function (depends on function nature x or y)*/
    //public Point2D maxFunction();

    /** return the source name */
    public String getName();

    /** set the source name */
    public void setName(String name);
    
    /** return the source id */
    public String getId();

    /** set the source id */
    public void setId(String id);
}
