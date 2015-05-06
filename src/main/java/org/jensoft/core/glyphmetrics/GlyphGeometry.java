/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.glyphmetrics;

import java.awt.Shape;
import java.awt.geom.Point2D;

/**
 * <code>package-info.java</code> defines the glyph metrics geometry
 * 
 * @see GlyphMetric
 * @see AbstractMetricsPath
 * @see GeneralMetricsPath
 * @author Sebastien Janaud
 */
public class GlyphGeometry {

    /** glyph shape */
    private Shape glyphShape;

    /** north point bound after transform */
    private Point2D northTransform;

    /** south point bound after transform */
    private Point2D southTransform;

    /** west point bound after transform */
    private Point2D westTransform;

    /** east point bound after transform */
    private Point2D eastTransform;

    /**
     * create empty legend glyph
     */
    public GlyphGeometry() {
    }

    /**
     * create legend glyph with specified shape
     * 
     * @param glyphShape
     */
    public GlyphGeometry(Shape glyphShape) {
        super();
        this.glyphShape = glyphShape;
    }

    /**
     * create glyph legend with specified parameters
     * 
     * @param glyphShape
     * @param northTransform
     * @param southTransform
     * @param westTransform
     * @param eastTransform
     */
    public GlyphGeometry(Shape glyphShape, Point2D northTransform,
            Point2D southTransform, Point2D westTransform, Point2D eastTransform) {
        super();
        this.glyphShape = glyphShape;
        this.northTransform = northTransform;
        this.southTransform = southTransform;
        this.westTransform = westTransform;
        this.eastTransform = eastTransform;
    }

    /**
     * @return the glyphShape
     */
    public Shape getGlyphShape() {
        return glyphShape;
    }

    /**
     * @param glyphShape
     *            the glyphShape to set
     */
    public void setGlyphShape(Shape glyphShape) {
        this.glyphShape = glyphShape;
    }

    /**
     * @return the northTransform
     */
    public Point2D getNorthTransform() {
        return northTransform;
    }

    /**
     * @param northTransform
     *            the northTransform to set
     */
    public void setNorthTransform(Point2D northTransform) {
        this.northTransform = northTransform;
    }

    /**
     * @return the southTransform
     */
    public Point2D getSouthTransform() {
        return southTransform;
    }

    /**
     * @param southTransform
     *            the southTransform to set
     */
    public void setSouthTransform(Point2D southTransform) {
        this.southTransform = southTransform;
    }

    /**
     * @return the westTransform
     */
    public Point2D getWestTransform() {
        return westTransform;
    }

    /**
     * @param westTransform
     *            the westTransform to set
     */
    public void setWestTransform(Point2D westTransform) {
        this.westTransform = westTransform;
    }

    /**
     * @return the eastTransform
     */
    public Point2D getEastTransform() {
        return eastTransform;
    }

    /**
     * @param eastTransform
     *            the eastTransform to set
     */
    public void setEastTransform(Point2D eastTransform) {
        this.eastTransform = eastTransform;
    }

}
