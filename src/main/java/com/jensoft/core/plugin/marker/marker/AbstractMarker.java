/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.marker.marker;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View;

public abstract class AbstractMarker {

    /** marker name */
    private String name;

    /** marker plugin host */
    private AbstractPlugin host;

    /** marker x coordinate */
    private double markerX;

    /** marker y coordinate */
    private double markerY;

    /** marker lock intercept */
    private boolean lockIntercept;

    /** marker lock move */
    private boolean lockMove;

    /** marker nature */
    private MarkerNature markerNature = MarkerNature.MarkerUser;

    /** marker point */
    private Point2D markerPoint;

    /**
     * Marker Nature
     */
    public enum MarkerNature {
        MarkerUser, MarkerDevice;
    }

    /**
     * create default marker
     */
    public AbstractMarker() {
    }

    public Point2D getMarkerPoint() {
        if (markerNature == MarkerNature.MarkerDevice) {
            markerPoint = new Point2D.Double(getMarkerX(), getMarkerY());
        }
        if (markerNature == MarkerNature.MarkerUser) {
            markerPoint = getHost().getProjection().userToPixel(
                                                              new Point2D.Double(getMarkerX(), getMarkerY()));
        }
        return markerPoint;

    }

    /**
     * @return the markerNature
     */
    public MarkerNature getMarkerNature() {
        return markerNature;
    }

    /**
     * @param markerNature
     *            the markerNature to set
     */
    public void setMarkerNature(MarkerNature markerNature) {
        this.markerNature = markerNature;
    }

    /**
     * get marker name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * set marker name
     * 
     * @param name
     *            name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the host
     */
    public AbstractPlugin getHost() {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(AbstractPlugin host) {
        this.host = host;
    }

    public void lockIntercept() {
        lockIntercept = true;

    }

    public void unlockIntercept() {
        lockIntercept = false;

    }

    public boolean isLockIntercept() {
        return lockIntercept;
    }

    public void lockMove() {
        lockMove = true;

    }

    public void unlockMove() {
        lockMove = false;

    }

    public boolean isLockMove() {
        return lockMove;
    }

    /**
     * @return the markerX
     */
    public double getMarkerX() {
        return markerX;
    }

    /**
     * @param markerX
     *            the markerX to set
     */
    public void setMarkerX(double markerX) {
        this.markerX = markerX;
    }

    /**
     * @return the markerY
     */
    public double getMarkerY() {
        return markerY;
    }

    /**
     * @param markerY
     *            the markerY to set
     */
    public void setMarkerY(double markerY) {
        this.markerY = markerY;
    }

    /**
     * override this method to paint the marker paint the marker
     * 
     * @param view
     * @param g2d
     */
    public abstract void paintMarker(View view, Graphics2D g2d);

}
