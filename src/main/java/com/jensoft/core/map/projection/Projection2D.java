/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.projection;

import java.awt.geom.Point2D;

/**
 * <code>Projection2D</code>transforms pixel/geoposition
 * 
 * @author sebastien Janaud
 */
public interface Projection2D {

    /**
     * transform the given geo position in the pixel coordinate system
     * 
     * @param position
     *            the geo position to transform
     * @return the pixel transform geo position
     */
    public Point2D geoToPixel(GeoPosition position);

    /**
     * transform the specified latitude to pixel y coordinate
     * 
     * @param latitude
     *            the latitude degree
     * @return pixel y latitude
     */
    public double latitudeToPixel(double latitude);

    /**
     * transform the specified latitude to pixel x coordinate
     * 
     * @param longitude
     *            the longitude degree
     * @return pixel x longitude
     */
    public double longitudeToPixel(double longitude);

    /**
     * transform coordinate from specified pixel to geo position
     * 
     * @param pixel
     *            the pixel to transform
     * @return the geo position of the given pixel
     */
    public GeoPosition pixelToGeo(Point2D pixel);

    /**
     * transform y pixel coordinate to latitude degree
     * 
     * @param pixelY
     *            the pixel y to transform
     * @return the latitude degree
     */
    public double pixelToLatitude(double pixelY);

    /**
     * transform x pixel coordinate to longitude degree
     * 
     * @param pixelX
     *            the pixel x to transform
     * @return the longitude degree
     */
    public double pixelToLongitude(double pixelX);

}
