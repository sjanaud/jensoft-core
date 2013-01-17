/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.radar;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.plugin.radar.painter.RadarPainter;

/**
 * Radar
 */
public class Radar {

    private int centerX;
    private int centerY;
    private int buildCenterX;
    private int buildCenterY;
    private double radius;
    private RadarPainter painter;
    private RadarNature nature = RadarNature.User;
    private List<RadarDimension> dimensions;
    private List<RadarSurface> surfaces;
    private RadarPlugin host;
    private Color themeColor;

    public enum RadarNature {
        Device, User;
    }

    /**
     * create radar with specified parameters in user system coordinate
     * 
     * @param centerX
     *            radar center in user coordinate
     * @param centerY
     *            radar center in user coordinate
     * @param radius
     *            radar radius in pixel
     */
    public Radar(int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        dimensions = new ArrayList<RadarDimension>();
        surfaces = new ArrayList<RadarSurface>();
    }

    /**
     * create radar with specified parameters and specified nature, user or
     * device
     * 
     * @param centerX
     *            radar center in specified nature coordinate
     * @param centerY
     *            radar center in specified nature coordinate
     * @param radius
     *            radar radius in pixel
     * @param nature
     */
    public Radar(int centerX, int centerY, int radius, RadarNature nature) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.nature = nature;
        dimensions = new ArrayList<RadarDimension>();
        surfaces = new ArrayList<RadarSurface>();
    }

    /**
     * add radar dimension to this radar
     * 
     * @param radarDimension
     *            the dimension to add
     */
    public void addDimension(RadarDimension radarDimension) {
        dimensions.add(radarDimension);
    }

    /**
     * add the specified surface to this radar
     * 
     * @param radarSurface
     *            the surface to add
     */
    public void addSurface(RadarSurface radarSurface) {
        surfaces.add(radarSurface);
    }

    /**
     * @return the centerX
     */
    public int getCenterX() {
        return centerX;
    }

    /**
     * @param centerX
     *            the centerX to set
     */
    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    /**
     * @return the centerY
     */
    public int getCenterY() {
        return centerY;
    }

    /**
     * @param centerY
     *            the centerY to set
     */
    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    /**
     * @return the dimensions
     */
    public List<RadarDimension> getDimensions() {
        return dimensions;
    }

    /**
     * @param dimensions
     *            the dimensions to set
     */
    public void setDimensions(List<RadarDimension> dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * @return the surfaces
     */
    public List<RadarSurface> getSurfaces() {
        return surfaces;
    }

    /**
     * @param surfaces
     *            the surfaces to set
     */
    public void setSurfaces(List<RadarSurface> surfaces) {
        this.surfaces = surfaces;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @param radius
     *            the radius to set
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * @return the painter
     */
    public RadarPainter getPainter() {
        return painter;
    }

    /**
     * @param painter
     *            the painter to set
     */
    public void setRadarPainter(RadarPainter painter) {
        this.painter = painter;
    }

    /**
     * @return the nature
     */
    public RadarNature getNature() {
        return nature;
    }

    /**
     * @param nature
     *            the nature to set
     */
    public void setNature(RadarNature nature) {
        this.nature = nature;
    }

    /**
     * @return the buildCenterX
     */
    public int getBuildCenterX() {
        return buildCenterX;
    }

    /**
     * @param buildCenterX
     *            the buildCenterX to set
     */
    public void setBuildCenterX(int buildCenterX) {
        this.buildCenterX = buildCenterX;
    }

    /**
     * @return the buildCenterY
     */
    public int getBuildCenterY() {
        return buildCenterY;
    }

    /**
     * @param buildCenterY
     *            the buildCenterY to set
     */
    public void setBuildCenterY(int buildCenterY) {
        this.buildCenterY = buildCenterY;
    }

    /**
     * @return the host
     */
    public RadarPlugin getHost() {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(RadarPlugin host) {
        this.host = host;
    }

    /**
     * @return the themeColor
     */
    public Color getThemeColor() {
        if (themeColor == null) {
            themeColor = ColorPalette.getRandomColor();
        }
        return themeColor;
    }

    /**
     * @param themeColor
     *            the themeColor to set
     */
    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
    }

    public Point2D getDimensionPointAtRadius(RadarDimension dimension,
            double radius) {
        if (nature == RadarNature.User) {
            Point2D projectedCenter = getHost().getWindow2D().userToPixel(
                                                                          new Point2D.Double(centerX, centerY));
            buildCenterX = (int) projectedCenter.getX();
            buildCenterY = (int) projectedCenter.getY();
        }
        else {
            buildCenterX = centerX;
            buildCenterY = centerY;
        }

        double angleDegree = dimension.getAngleDegree();
        double pointX = buildCenterX + radius
                * Math.cos(Math.toRadians(angleDegree));
        double pointY = buildCenterY - radius
                * Math.sin(Math.toRadians(angleDegree));

        Point2D p = new Point2D.Double(pointX, pointY);

        return p;
    }

    public void solveGeometry() {
        for (RadarDimension dimension : dimensions) {
            if (nature == RadarNature.User) {
                Point2D projectedCenter = getHost().getWindow2D().userToPixel(
                                                                              new Point2D.Double(centerX, centerY));
                buildCenterX = (int) projectedCenter.getX();
                buildCenterY = (int) projectedCenter.getY();
            }
            else {
                buildCenterX = centerX;
                buildCenterY = centerY;
            }

            double angleDegree = dimension.getAngleDegree();
            double dimensionEndX = buildCenterX + radius
                    * Math.cos(Math.toRadians(angleDegree));
            double dimensionEndY = buildCenterY - radius
                    * Math.sin(Math.toRadians(angleDegree));

            Line2D dimensionLine = new Line2D.Double(buildCenterX,
                                                     buildCenterY, dimensionEndX, dimensionEndY);
            dimension.setDimensionLine(dimensionLine);
        }
    }

}
