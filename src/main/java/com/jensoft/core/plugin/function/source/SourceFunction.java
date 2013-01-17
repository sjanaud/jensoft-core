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

    /** return the source points for this source*/
    public List<Point2D> getSource();

    /** select by x the points of the given x range */
    public List<Point2D> select(double startX, double endX);

    /** evaluate the function point (x,y) for the specified x */
    public Point2D evaluate(double x);

    /** get next point in the source after the specified x */
    public Point2D next(double x);

    /** get previous point in the source before specified x */
    public Point2D previous(double x);

    /** return point corresponding to the min x, assumes that source is sorted by x */
    public Point2D min();

    /** return point corresponding to the max x, assumes that source is sorted by x */
    public Point2D max();

    /** return point corresponding to the min y */
    public Point2D minY();

    /** return the source name */
    public String getName();

    /** set the source name */
    public void setName(String name);
    
    /** return the source id */
    public String getId();

    /** set the source id */
    public void setId(String id);
}
