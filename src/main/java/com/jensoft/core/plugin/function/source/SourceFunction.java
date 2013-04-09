/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * <code>SourceFunction</code> defines a collection of points (x,y) for source function y=f(x)
 * 
 * <ul>
 * <li>only one point P(x,y=f(x)) for given x value</li>
 * <li>sort by x</li>
 * </ul>
 * 
 * <p>
 *  the serie define {@link #getSource()} to provides the point collection (x,y) that define the curve function
 * </p>
 * 
 * @author Sebastien Janaud
 */
public interface SourceFunction {
	
	public FunctionNature getNature();

    /** return the source points for this source*/
    public List<Point2D> getSource();

    /** select by x or y (depends of nature) the points of the given range */
    public List<Point2D> select(double start, double end);

    /** evaluate the function point (x,y) for the specified x or y (depends on function nature x or y) */
    public Point2D evaluate(double x);

    /** get next point in the source after the specified x or y (depends on function nature x or y)*/
    public Point2D next(double value);

    /** get previous point in the source before specified x or y (depends on function nature x or y)*/
    public Point2D previous(double value);

    /** return point corresponding to the min x or y (depends on function nature x or y), assumes that source is sorted*/
    public Point2D first();

    /** return point corresponding to the max x or y (depends on function nature x or y), assumes that source is sorted */
    public Point2D last();

    /** return point corresponding to the min peak of function (depends on function nature x or y)*/
    public Point2D minFunction();
    
    /** return point corresponding to the max peak of function (depends on function nature x or y)*/
    public Point2D maxFunction();

    /** return the source name */
    public String getName();

    /** set the source name */
    public void setName(String name);
    
    /** return the source id */
    public String getId();

    /** set the source id */
    public void setId(String id);
}
