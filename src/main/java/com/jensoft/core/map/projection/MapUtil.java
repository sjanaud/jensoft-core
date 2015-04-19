/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.projection;

/**
 * <code>MapUtil</code> transforms index/geo-position
 * 
 * @author Sebastien Janaud
 */
public class MapUtil {

    /**
     * get the y tile index for specified latitude and zoom level
     * 
     * @param latitude
     *            latitude
     * @param zoom
     *            zoom level
     * @return y tile index
     */
    public static int latToY(final double latitude, final int zoom) {
        int ytile = (int) Math.floor((1 - Math.log(Math.tan(latitude * Math.PI
                / 180)
                + 1 / Math.cos(latitude * Math.PI / 180))
                / Math.PI)
                / 2 * (1 << zoom));
        return ytile;
    }

    /**
     * get the x tile index for specified longitude and zoom level
     * 
     * @param longitude
     *            longitude
     * @param zoom
     *            zoom level
     * @return x tile index
     */
    public static int longToX(final double longitude, final int zoom) {
        int xtile = (int) Math.floor((longitude + 180) / 360 * (1 << zoom));
        return xtile;
    }

    /**
     * get the longitude (west of tile) for specified x tile index and zoom level
     * 
     * @param x
     *            x tile index
     * @param zoom
     *            zoom level
     * @return longitude
     */
    public static double tile2long(int x, int zoom) {
        return x / Math.pow(2, zoom) * 360 - 180;
    }

    /**
     * get the latitude (north of tile) for specified y tile index and zoom level
     * 
     * @param y
     *            y tile index
     * @param zoom
     *            zoom level
     * @return latitude
     */
    public static double tile2lat(int y, int zoom) {
        double n = Math.PI - 2 * Math.PI * y / Math.pow(2, zoom);
        return 180 / Math.PI * Math.atan(0.5 * (Math.exp(n) - Math.exp(-n)));
    }

    /**
     * main test
     * 
     * @param args
     */
    public static void main(String[] args) {
        GeoPosition centerWindow = new GeoPosition(44.8380405,-0.5938274);

        System.out.println(latToY(centerWindow.getLatitude(), 15));
        System.out.println(longToX(centerWindow.getLongitude(), 15));

        System.out.println(tile2long(65319, 17));
        System.out.println(tile2lat(47233, 17));

        System.out.println(tile2lat(0, 17));
        System.out.println(tile2lat(0, 14));

    }

}
