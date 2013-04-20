/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.projection;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.map.layer.AbstractMapLayer;

/**
 * <code>DalleProjection</code>
 * 
 * @author Sebastien Janaud
 */
public class DalleProjection implements Projection2D {

    /** square tile size */
    private int squareTileSize = 256;

    /** max tile index */
    private int maxTileIndex = 0;

    /** zoom level */
    private int zoom;

    /** map layers */
    private List<AbstractMapLayer> mapLayers = new ArrayList<AbstractMapLayer>();

    /**
     * create a default paving projection with specified zoom and 256 square tile pixel.
     * 
     * @param zoom
     *            the specified zoom
     */
    public DalleProjection(int zoom) {
        this.zoom = zoom;
        maxTileIndex = (int) Math.pow(2, zoom) - 1;
    }

    /**
     * create a paving projection for specified zoom level and square tile size
     * 
     * @param zoom
     *            the zoom level
     * @param squareTileSize
     *            the square tile size
     */
    public DalleProjection(int zoom, int squareTileSize) {
        this.zoom = zoom;
        this.squareTileSize = squareTileSize;
        maxTileIndex = (int) Math.pow(2, zoom) - 1;
    }

    /**
     * get zoom level for this paving projection
     * 
     * @return zomm level
     */
    public int getZoom() {
        return zoom;
    }

    /**
     * get the square size tile for this paving projection
     * 
     * @return the squareTileSize
     */
    public int getSquareTileSize() {
        return squareTileSize;
    }

    /**
     * get the max tile index for this paving projection
     * 
     * @return the maxTileIndex
     */
    public int getMaxTileIndex() {
        return maxTileIndex;
    }

    @Override
    public String toString() {
        return "projection dalle[Level:"+zoom+";square:"+squareTileSize+"]";
    }

    /**
     * get paving pixel dimension
     * 
     * @return paving pixel dimension
     */
    public Dimension getDalleDimension() {
        int max = (int) Math.pow(2, zoom);
        return new Dimension(max * squareTileSize, max * squareTileSize);
    }

    /**
     * get the map2D for this paving projection
     * 
     * @param startX
     *            the start x tile index
     * @param endX
     *            the end x tile index
     * @param startY
     *            the start y tile index
     * @param endY
     *            the end y tile index
     * @return the map 2D
     */
    public Map2D createMap2D(int startX, int endX, int startY, int endY) {
        Map2D map2D = new Map2D(startX, endX, startY, endY, squareTileSize,
                                zoom, this);
        return map2D;
    }

    /**
     * register layer in this paving projection
     * 
     * @param mapLayer
     *            the map layer to register
     */
    public void registerLayer(AbstractMapLayer mapLayer) {
        mapLayer.setProjection2D(this);
        mapLayers.add(mapLayer);
    }

    /**
     * get the paving center in pixel coordinate
     * 
     * @return the center pixel
     */
    public Point2D getDalleCenter() {
        Dimension dim = getDalleDimension();
        return new Point2D.Double(dim.getWidth() / 2, dim.getHeight() / 2);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.map.projection.Projection2D#geoToPixel(com.jensoft.core.map.projection.GeoPosition)
     */
    @Override
    public Point2D geoToPixel(GeoPosition geoPosition) {
        Dimension dalleDimension = getDalleDimension();
        double pixelX = dalleDimension.getWidth()
                * ((geoPosition.getLongitude() + 180) / 360);
        double pixelY = dalleDimension.getHeight()
                / 2d
                - Math.log(Math.tan(Math.PI / 4
                        + Math.toRadians(geoPosition.getLatitude()) / 2)) / (2 * Math.PI)
                * dalleDimension.getWidth();
        return new Point2D.Double(pixelX, pixelY);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.map.projection.Projection2D#latitudeToPixel(double)
     */
    @Override
    public double latitudeToPixel(double latitude) {
        Dimension dalleDimension = getDalleDimension();
        double pixelY = dalleDimension.getHeight()
                / 2d
                - Math.log(Math.tan(Math.PI / 4 + Math.toRadians(latitude)
                        / 2)) / (2 * Math.PI) * dalleDimension.getWidth();
        return pixelY;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.map.projection.Projection2D#longitudeToPixel(double)
     */
    @Override
    public double longitudeToPixel(double longitude) {
        Dimension dalleDimension = getDalleDimension();
        double pixelX = dalleDimension.getWidth() * ((longitude + 180) / 360);
        return pixelX;
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.map.projection.Projection2D#pixelToGeo(java.awt.geom.Point2D)
     */
    @Override
    public GeoPosition pixelToGeo(Point2D point) {
        Dimension dalleDimension = getDalleDimension();
        double longitude = point.getX() / dalleDimension.getWidth() * 360 - 180;
        double A = 2 * Math.PI / dalleDimension.getWidth();
        double B = dalleDimension.getHeight() / 2d - point.getY();
        double C = Math.exp(A * B);
        double D = Math.atan(C);
        double latitudeRadian = 2 * (D - Math.PI / 4);
        double latitude = Math.toDegrees(latitudeRadian);
        return new GeoPosition(latitude, longitude);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.map.projection.Projection2D#pixelToLatitude(double)
     */
    @Override
    public double pixelToLatitude(double pixelY) {
        Dimension dalleDimension = getDalleDimension();
        double A = 2 * Math.PI / dalleDimension.getWidth();
        double B = dalleDimension.getHeight() / 2d - pixelY;
        double C = Math.exp(A * B);
        double D = Math.atan(C);
        double latitudeRadian = 2 * (D - Math.PI / 4);
        double latitude = Math.toDegrees(latitudeRadian);
        return latitude;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.map.projection.Projection2D#pixelToLongitude(double)
     */
    @Override
    public double pixelToLongitude(double pixelX) {
        Dimension dalleDimension = getDalleDimension();
        double longitude = pixelX / dalleDimension.getWidth() * 360 - 180;
        return longitude;
    }

    // public static int latToY(final double latitude,int zoom) {
    // int ytile = (int)Math.floor( (1 - Math.log(Math.tan(latitude * Math.PI /
    // 180) + 1 / Math.cos(latitude * Math.PI / 180)) / Math.PI) / 2 * (1<<zoom)
    // ) ;
    // return ytile;
    // }
    //
    // public static int longToX(double longitude,int zoom) {
    // int xtile = (int)Math.floor( (longitude + 180) / 360 * (1<<zoom) ) ;
    // return xtile ;
    // }
    //
    // public static double tile2long(int x,int zoom) {
    // return (x/Math.pow(2,zoom)*360-180);
    // }
    //
    // public static double tile2lat(int y,int zoom) {
    // double n=Math.PI-2*Math.PI*y/Math.pow(2,zoom);
    // return (180/Math.PI*Math.atan(0.5*(Math.exp(n)-Math.exp(-n))));
    // }

}
