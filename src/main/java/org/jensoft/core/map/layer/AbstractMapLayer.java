/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.map.primitive.Node;
import org.jensoft.core.map.primitive.Primitive;
import org.jensoft.core.map.projection.Clearable;
import org.jensoft.core.map.projection.GeoBound;
import org.jensoft.core.map.projection.GeoPosition;
import org.jensoft.core.map.projection.Map2D;
import org.jensoft.core.map.projection.Projectable2D;
import org.jensoft.core.map.projection.Projection2D;

/**
 * <code>AbstractMapLayer<code>
 * 
 * @author sebastien janaud
 */
public abstract class AbstractMapLayer implements Projectable2D, Clearable {

    private boolean lock = false;
    private Projection2D projection2D;
    private GeoBound geoBound;
    private List<Primitive> primitives;

    public AbstractMapLayer() {
        primitives = new ArrayList<Primitive>();
    }

    public void registerPrimitive(Primitive primitive) {
        primitives.add(primitive);
    }

    public Projection2D getProjection2D() {
        return projection2D;
    }

    public void setProjection2D(Projection2D projection) {
        projection2D = projection;
    }

    /** interpolation */
    private Interpolation interpolation = Interpolation.NearestNeighbor;

    /** aliasing */
    private Antialiasing antialiasing = Antialiasing.Off;

    /** fractional */
    private Fractional fractional = Fractional.Off;

    public enum Interpolation {

        Bicubic(RenderingHints.VALUE_INTERPOLATION_BICUBIC), Bilinear(
                RenderingHints.VALUE_INTERPOLATION_BILINEAR), NearestNeighbor(
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        private Object value;

        Interpolation(Object value) {
            this.value = value;
        }

        private void configureGraphics(Graphics2D g2d) {
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, value);
        }
    }

    public enum Fractional {
        On(RenderingHints.VALUE_FRACTIONALMETRICS_ON), Off(
                RenderingHints.VALUE_FRACTIONALMETRICS_OFF);

        private Object value;

        Fractional(Object value) {
            this.value = value;
        }

        private void configureGraphics(Graphics2D g2d) {
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, value);
        }
    }

    public enum Antialiasing {
        On(RenderingHints.VALUE_ANTIALIAS_ON), Off(
                RenderingHints.VALUE_ANTIALIAS_OFF);

        private Object value;

        Antialiasing(Object value) {
            this.value = value;
        }

        private void configureGraphics(Graphics2D g) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, value);
        }
    }

    @Override
    public void project() {
        for (Primitive p : primitives) {
            List<Node> nodes = p.getNodes();
            for (Node n : nodes) {
                GeoPosition gp = n.getGeoPosition();
                Point2D p2d = projection2D.geoToPixel(gp);
                n.setProjection(p2d);
            }
        }
    }

    @Override
    public void clearLayer() {
        clearPrimitives();
    }

    public void clearPrimitives() {
        primitives.clear();
    }

    public void lock() {
        lock = true;
    }

    public void unlock() {
        lock = false;
    }

    public boolean isLock() {
        return lock;
    }

    public GeoBound getGeoBound() {
        return geoBound;
    }

    /**
     * set the GeoBound
     * 
     * @param geoBound
     */
    public void setGeoBound(GeoBound geoBound) {
        this.geoBound = geoBound;

    }

    /**
     * get the interpolation
     * 
     * @return interpolation
     */
    public Interpolation getInterpolation() {
        return interpolation;
    }

    /**
     * set the interpolation
     * 
     * @param interpolation
     */
    public void setInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation == null ? Interpolation.NearestNeighbor
                : interpolation;
    }

    /**
     * return true if anti alias is required, false otherwise
     * 
     * @return antialiasing
     */
    public Antialiasing getAntialiasing() {
        return antialiasing;
    }

    /**
     * set the antialiasing
     * 
     * @param antialiasing
     */
    public void setAntialiasing(Antialiasing antialiasing) {
        this.antialiasing = antialiasing;
    }

    /**
     * set the fractional
     * 
     * @param fractional
     */
    public void setFractionalMetrics(Fractional fractional) {
        this.fractional = fractional;
    }

    /**
     * return Fractional
     * 
     * @return fractional
     */
    public Fractional getFractional() {
        return fractional;
    }

    /**
     * set the rendering quality for painting this layout
     * 
     * @param g2d
     */
    private void setQuality(Graphics2D g2d) {
        getAntialiasing().configureGraphics(g2d);
        getInterpolation().configureGraphics(g2d);
        getFractional().configureGraphics(g2d);
    }

    /**
     * paint the layer for the specified map
     * 
     * @param map2D
     *            the map2D to paint with this layer
     */
    public abstract void doPaintMap(Map2D map2D);

    /**
     * paint the layer in graphics context
     * 
     * @param g2d
     *            the graphics to paint
     */
    public abstract void doPaint(Graphics2D g2d);

}
