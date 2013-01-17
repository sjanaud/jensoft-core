/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.core2;

import java.util.List;

import com.jensoft.core.gauge.core.painter.EnvelopGaugePainter;
import com.jensoft.core.window.Window2D;

/**
 * AbstractRadialGauge
 */
public abstract class AbstractRadialGauge {

    private double x;
    private double y;
    private double outerRadius;
    private double innerRadius;
    private EnvelopGaugePainter envelop;
    private List<Body> bodies;

    private Window2D window;

    /**
     * create abstract radial gauge
     */
    public AbstractRadialGauge() {
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x
     *            the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y
     *            the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the outerRadius
     */
    public double getOuterRadius() {
        return outerRadius;
    }

    /**
     * @param outerRadius
     *            the outerRadius to set
     */
    public void setOuterRadius(double outerRadius) {
        this.outerRadius = outerRadius;
    }

    /**
     * @return the innerRadius
     */
    public double getInnerRadius() {
        return innerRadius;
    }

    /**
     * @param innerRadius
     *            the innerRadius to set
     */
    public void setInnerRadius(double innerRadius) {
        this.innerRadius = innerRadius;
    }

    /**
     * @return the envelop
     */
    public EnvelopGaugePainter getEnvelop() {
        return envelop;
    }

    /**
     * @param envelop
     *            the envelop to set
     */
    public void setEnvelop(EnvelopGaugePainter envelop) {
        this.envelop = envelop;
    }

    /**
     * @return the bodies
     */
    public List<Body> getBodies() {
        return bodies;
    }

    /**
     * @param bodies
     *            the bodies to set
     */
    public void setBodies(List<Body> bodies) {
        this.bodies = bodies;
    }

    /**
     * add specified body to gauge
     * 
     * @param body
     */
    public void addBody(Body body) {
        bodies.add(body);
    }

    /**
     * @return the window
     */
    public Window2D getWindow() {
        return window;
    }

    /**
     * @param window
     *            the window to set
     */
    public void setWindow(Window2D window) {
        this.window = window;
    }

}
