/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.radar;

import java.awt.Color;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.plugin.radar.painter.AbstractRadarSurfacePainter;

public class RadarSurface {

    /** name */
    private String name = "surface";

    /** radar that host this surface */
    private Radar host;

    /** theme color */
    private Color themeColor;

    /** surface entry */
    private List<RadarSurfaceAnchor> anchors;

    /** surfacePath */
    private GeneralPath surfacePath;

    /** surface painter */
    private AbstractRadarSurfacePainter surfacePainter;

    /**
     * create radar surface
     */
    public RadarSurface() {
        anchors = new ArrayList<RadarSurfaceAnchor>();
    }

    /**
     * create radar surface with specified given nbame
     */
    public RadarSurface(String name) {
        anchors = new ArrayList<RadarSurfaceAnchor>();
        this.name = name;
    }

    /**
     * @return the host
     */
    public Radar getHost() {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(Radar host) {
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

    /**
     * @return the anchors
     */
    public List<RadarSurfaceAnchor> getAnchors() {
        return anchors;
    }

    /**
     * add surface anchor for specified parameters
     * 
     * @param surfaceAnchor
     *            the radar surfaceAnchor
     */
    public void addSurfaceAnchor(RadarSurfaceAnchor surfaceAnchor) {
        anchors.add(surfaceAnchor);
    }

    /**
     * @return the surfacePainter
     */
    public AbstractRadarSurfacePainter getSurfacePainter() {
        return surfacePainter;
    }

    /**
     * @param surfacePainter
     *            the surfacePainter to set
     */
    public void setSurfacePainter(AbstractRadarSurfacePainter surfacePainter) {
        this.surfacePainter = surfacePainter;
    }

    /**
     * solve surface geometry
     */
    public void solveSurfaceGeometry() {

        if(getAnchors().size() == 0){
            return;
        }
        System.out.println("radar:"+getHost());
       int x = getHost().getBuildCenterX();
       int y = getHost().getBuildCenterY();
        List<RadarSurfaceAnchor> anchors = getAnchors();
        surfacePath = new GeneralPath();
        surfacePath.moveTo(x, y);
        for (int i = 0; i < anchors.size(); i++) {
            RadarSurfaceAnchor anchor = anchors.get(i);
            Point2D point = anchor.getDimension().getMetricsPoint(
                                                                  anchor.getRadarMetrics().getValue());

            if (point != null) {
//                if (i == 0) {
//                    surfacePath.moveTo(point.getX(), point.getY());
//                }
 //               else {
                    surfacePath.lineTo(point.getX(), point.getY());
 //               }
            }
        }

        surfacePath.closePath();

    }

    /**
     * @return the surfacePath
     */
    public GeneralPath getSurfacePath() {
        return surfacePath;
    }

}
