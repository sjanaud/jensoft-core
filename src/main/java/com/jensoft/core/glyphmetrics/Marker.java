/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics;

import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.painter.GlyphMetricFill;
import com.jensoft.core.glyphmetrics.painter.MetricsPathPainter;

/**
 * Marker defines a point
 * 
 * @see GlyphMetric
 * @see AbstractMetricsPath
 * @see GeneralMetricsPath
 * @see GlyphMetricsNature
 * @see GlyphMetricFill
 * @see MetricsPathPainter
 * @author Sebastien Janaud
 */
public class Marker {

    /** marker point location */
    private Point2D markerLocation;

    /**
     * create new marker
     * 
     * @param markerLocation
     *            the marker location to set
     */
    public Marker(Point2D markerLocation) {
        super();
        this.markerLocation = markerLocation;
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
     *            the marker location to set
     */
    public void setMarkerLocation(Point2D markerLocation) {
        this.markerLocation = markerLocation;
    }

}