/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.projection;

/**
 * <code>GeoBound</code>
 * 
 * @author Sebastien Janaud
 */
public class GeoBound {

    /** latitude north */
    private double latitudeNorth;

    /** latitude south */
    private double latitudeSouth;

    /** longitude west */
    private double longitudeWest;

    /** longitude east */
    private double longitudeEast;

    /** the projection */
    private Projection2D projection;

    /**
     * create a geo bound with specified coordinates
     * 
     * @param longitudeWest
     * @param latitudeSouth
     * @param longitudeEast
     * @param latitudeNorth
     */
    public GeoBound(double longitudeWest, double latitudeSouth,
            double longitudeEast, double latitudeNorth) {
        this.longitudeWest = longitudeWest;
        this.latitudeSouth = latitudeSouth;
        this.longitudeEast = longitudeEast;
        this.latitudeNorth = latitudeNorth;
    }

    /**
     * get the latitude north
     * 
     * @return latitude north
     */
    public double getLatitudeNorth() {
        return latitudeNorth;
    }

    /**
     * set the latitude north
     * 
     * @param latitudeNorth
     *            the latitude north to set
     */
    public void setLatitudeNorth(double latitudeNorth) {
        this.latitudeNorth = latitudeNorth;
    }

    /**
     * get the latitude south
     * 
     * @return latitude south
     */
    public double getLatitudeSouth() {
        return latitudeSouth;
    }

    /**
     * set the latitudeSouth south
     * 
     * @param latitudeSouth
     *            the latitude south to set
     */
    public void setLatitudeSouth(double latitudeSouth) {
        this.latitudeSouth = latitudeSouth;
    }

    /**
     * get longitude west
     * 
     * @return longitude west
     */
    public double getLongitudeWest() {
        return longitudeWest;
    }

    /**
     * set longitude west
     * 
     * @param longitudeWest
     *            the longitude west to set
     */
    public void setLongitudeWest(double longitudeWest) {
        this.longitudeWest = longitudeWest;
    }

    /**
     * get longitude east
     * 
     * @return longitude east to set
     */
    public double getLongitudeEast() {
        return longitudeEast;
    }

    /**
     * set longitude east
     * 
     * @param longitudeEast
     *            the longitude east to set
     */
    public void setLongitudeEast(double longitudeEast) {
        this.longitudeEast = longitudeEast;
    }

    /**
     * get the projection for this geo bound
     * 
     * @return geo bound
     */
    public Projection2D getProjection() {
        return projection;
    }

    /**
     * set the projection for this bound
     * 
     * @param projection
     *            the projection to set
     */
    public void setProjection(Projection2D projection) {
        this.projection = projection;
    }

}
