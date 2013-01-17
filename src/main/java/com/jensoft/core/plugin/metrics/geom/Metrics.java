/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.geom;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Comparator;

import com.jensoft.core.plugin.metrics.format.IMetricsFormat;

/**
 * <code>Metrics</code> defines a metrics with marker and label
 * 
 * @author sebastien janaud
 */
public class Metrics {

    /** metrics type */
    private MetricsType metricsType;

    /** device value */
    private double deviceValue;

    /** user value */
    private double userValue;

    /** metrics marker color */
    private Color metricsMarkerColor;

    /** metrics label color */
    private Color metricsLabelColor;

    /** metrics format */
    private IMetricsFormat format;

    /** metrics label */
    private String metricsLabel;

    public static int MAJOR = 1;
    public static int MEDIAN = 0;
    public static int MINOR = -1;
    private int nature = MAJOR;

    /** metrics position */
    private Point2D markerLocation;

    /** marker position */
    private MarkerPosition markerPosition;

    /** gravity */
    private Gravity gravity = Gravity.Neutral;

    /** lock marker flag */
    private boolean lockMarker;

    /** lock label */
    private boolean lockLabel;

    /** visible flag */
    private boolean visible = true;

    /** Gravity defines the position in device flow, first, last or neutral */
    public enum Gravity {
        Neutral, First, Last;
    }

    /**
     * Metrics X or Y type
     */
    public enum MetricsType {
        XMetrics, YMetrics;
    }

    /**
     * Marker position
     */
    public enum MarkerPosition {
        N, S, W, E;

        public static boolean isXCompatible(MarkerPosition marker) {
            if (marker != null && (marker == N || marker == S )) {
                return true;
            }
            return false;
        }

        public static boolean isYCompatible(MarkerPosition marker) {
            if (marker != null && (marker == W || marker == E )) {
                return true;
            }
            return false;
        }
    }

    /**
     * create empty metrics
     */
    public Metrics() {
    }

    /**
     * create metrics with specified type
     * 
     * @param type
     *            the type to set
     */
    public Metrics(MetricsType type) {
        metricsType = type;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Metrics [metricsType=" + metricsType + ", deviceValue=" + deviceValue + ", userValue=" + userValue
                + ", format=" + format + ", metricsLabel=" + metricsLabel + ", nature=" + nature + ", gravity="
                + gravity + ", visible=" + visible + "]";
    }

    /**
     * true if marker is lock, false otherwise
     * 
     * @return true if marker is lock, false otherwise
     */
    public boolean isLockMarker() {
        return lockMarker;
    }

    /**
     * set lock marker with specified lock
     * 
     * @param lockMarker
     *            lock marker
     */
    public void setLockMarker(boolean lockMarker) {
        this.lockMarker = lockMarker;
    }

    /**
     * true if label is lock, false otherwise
     * 
     * @return true if label is lock, false otherwise
     */
    public boolean isLockLabel() {
        return lockLabel;
    }

    /**
     * set lock label with specified lock
     * 
     * @param lockLabel
     *            lock label
     */
    public void setLockLabel(boolean lockLabel) {
        this.lockLabel = lockLabel;
    }

    /**
     * get marker position
     * 
     * @return marker position
     */
    public MarkerPosition getMarkerPosition() {
        return markerPosition;
    }

    /**
     * set marker position
     * 
     * @param markerPosition
     *            the marker position to set
     */
    public void setMarkerPosition(MarkerPosition markerPosition) {
        this.markerPosition = markerPosition;
    }

    /**
     * @return the gravity
     */
    public Gravity getGravity() {
        return gravity;
    }

    /**
     * @param gravity
     *            the gravity to set
     */
    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

    /**
     * get marker location
     * 
     * @return marker location
     */
    public Point2D getMarkerLocation() {
        return markerLocation;
    }

    /**
     * set marker location
     * 
     * @param markerLocation
     *            the markerLocation to set
     */
    public void setMarkerLocation(Point2D markerLocation) {
        this.markerLocation = markerLocation;
    }

    /**
     * get the minor, minor or median nature of this metrics
     * 
     * @return nature
     */
    public int getNature() {
        return nature;
    }

    /***
     * set major, minor or median nature for this metrics
     * 
     * @param nature
     *            the nature to set
     */
    public void setNature(int nature) {
        this.nature = nature;
    }

    /**
     * get the x or y type of this metrics
     * 
     * @return type
     */
    public MetricsType getMetricsType() {
        return metricsType;
    }

    /**
     * set the x or y type for this metrics
     * 
     * @param metricsType
     */
    public void setMetricsType(MetricsType metricsType) {
        this.metricsType = metricsType;
    }

    /**
     * get the theme color for marker
     * 
     * @return marker color
     */
    public Color getMetricsMarkerColor() {
        return metricsMarkerColor;
    }

    /**
     * set the theme marker color
     * 
     * @param metricsMarkerColor
     */
    public void setMetricsMarkerColor(Color metricsMarkerColor) {
        this.metricsMarkerColor = metricsMarkerColor;
    }

    /**
     * get the device/pixel value for this metrics
     * 
     * @return device value
     */
    public double getDeviceValue() {
        return deviceValue;
    }

    /**
     * set the device value for this metrics
     * 
     * @param metricsValue
     *            the metrics value
     */
    public void setDeviceValue(double metricsValue) {
        deviceValue = metricsValue;
    }

    /**
     * get the user value of this metrics
     * 
     * @return user value
     */
    public double getUserValue() {
        return userValue;
    }

    /**
     * set the user value for this metrics
     * 
     * @param userValue
     */
    public void setUserValue(double userValue) {
        this.userValue = userValue;
    }

    /**
     * get the label theme color
     * 
     * @return label color
     */
    public Color getMetricsLabelColor() {
        return metricsLabelColor;
    }

    /**
     * set the label theme color
     * 
     * @param metricsLabelColor
     */
    public void setMetricsLabelColor(Color metricsLabelColor) {
        this.metricsLabelColor = metricsLabelColor;
    }

    /**
     * get the metrics format of
     * 
     * @return format
     */
    public IMetricsFormat getFormat() {
        return format;
    }

    /**
     * set the metrics format
     * 
     * @param format
     */
    public void setFormat(IMetricsFormat format) {
        this.format = format;
    }

    /**
     * get the metrics label
     * 
     * @return metrics label
     */
    public String getMetricsLabel() {
        return metricsLabel;
    }

    /**
     * set the metrics label
     * 
     * @param metricsLabel
     */
    public void setMetricsLabel(String metricsLabel) {
        this.metricsLabel = metricsLabel;
    }

    public static MetricsComparator metricsComparator;

    public static MetricsComparator getComparator() {
        if (metricsComparator == null) {
            metricsComparator = new MetricsComparator();
        }
        return metricsComparator;
    }

    public static class MetricsComparator implements Comparator<Metrics> {

        public MetricsComparator() {
        }

        @Override
        public int compare(Metrics d1, Metrics d2) {
            if (d1.getUserValue() > d2.getUserValue()) {
                return 1;
            }
            else if (d1.getUserValue() < d2.getUserValue()) {
                return -1;
            }
            return 0;
        }

    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible
     *            the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
