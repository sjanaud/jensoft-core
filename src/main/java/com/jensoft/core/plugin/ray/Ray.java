/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.plugin.ray.painter.axis.AbstractRayAxisLabel;
import com.jensoft.core.plugin.ray.painter.draw.AbstractRayDraw;
import com.jensoft.core.plugin.ray.painter.effect.AbstractRayEffect;
import com.jensoft.core.plugin.ray.painter.fill.AbstractRayFill;
import com.jensoft.core.plugin.ray.painter.label.AbstractRayLabel;
import com.jensoft.core.view.View2D.DeviceBand;

/**
 * Ray define a bar with geometry in twice dimension x and y <br>
 * <center><img src="doc-files/ray1.png"></center> <br>
 * <br>
 * <ul>
 * <li>a X Ray is defined by it center as ray value on X and define a minimum maximum value along Y</li>
 * <li>a Y Ray is defined by it center as ray value on Y and define a minimum maximum value along X</li>
 * <p>
 * ray thickness can be expressed in pixel or user coordinate.
 * </p>
 */
public class Ray {

    /** the ray name */
    private String name;

    /** the ray theme color */
    private Color themeColor;

    /** the ray thickness */
    private double thickness;

    /** the ray thickness type, pixel or user coordinate */
    private ThicknessType thicknessType;

    /** the center of the ray in user coordinate */
    private double ray;

    /** the ray base */
    private double rayBase;

    /** the ray value in device coordinate */
    private double rayValue;

    /** ray ascent */
    private boolean ascent = false;

    /** ray descent */
    private boolean descent = false;

    /** ray nature, XRay or YRay */
    private RayNature rayNature;

    /** the ray basic shape */
    private Rectangle2D rayShape;

    /** the ray draw painter */
    private AbstractRayDraw rayDraw;

    /** the ray fill painter */
    private AbstractRayFill rayFill;

    /** the ray effect painter */
    private AbstractRayEffect rayEffect;

    /** the ray label painter */
    private AbstractRayLabel rayLabel;

    /** the ray axis label painter */
    private AbstractRayAxisLabel rayAxisLabel;

    /** the ray host plugin */
    private RayPlugin host;

    /** enter flag */
    private boolean lockEnter = false;

    /** boolean inflating operation flag */
    private boolean inflating = false;

    /** deflating operation flag */
    private boolean deflating = false;

    /**
     * define Ray Thickness type, Device if the ray thickness is fixed in pixel,
     * or User if the ray thickness is define in user coordinate
     */
    public enum ThicknessType {
        Device, User;
    }

    /**
     * define the ray nature, XRay or YRay
     */
    public enum RayNature {
        XRay, YRay;
    };

    /**
     * define ray inflate, ascent or descent
     */
    public enum RayInflate {
        Ascent, Descent;
    }

    /**
     * create empty ray
     */
    public Ray() {
    }

    /**
     * create ray with specified name
     */
    public Ray(String name) {
        this.name = name;
    }

    /**
     * get the ray draw painter
     * 
     * @return ray draw painter
     */
    public AbstractRayDraw getRayDraw() {
        return rayDraw;
    }

    /**
     * set ray draw painter
     * 
     * @param rayDraw
     *            the ray draw painter to set
     */
    public void setRayDraw(AbstractRayDraw rayDraw) {
        this.rayDraw = rayDraw;
    }

    /**
     * get the ray fill painter
     * 
     * @return ray fill painter
     */
    public AbstractRayFill getRayFill() {
        return rayFill;
    }

    /**
     * set the ray fill painter
     * 
     * @param rayFill
     *            the ray fill painter to set
     */
    public void setRayFill(AbstractRayFill rayFill) {
        this.rayFill = rayFill;
    }

    /**
     * get the ray effect painter
     * 
     * @return the ray effect painter
     */
    public AbstractRayEffect getRayEffect() {
        return rayEffect;
    }

    /**
     * set the ray effect
     * 
     * @param rayEffect
     *            the ray effect painter to set
     */
    public void setRayEffect(AbstractRayEffect rayEffect) {
        this.rayEffect = rayEffect;
    }

    /**
     * get the ray label
     * 
     * @return the rayLabel
     */
    public AbstractRayLabel getRayLabel() {
        return rayLabel;
    }

    /**
     * set the ray label
     * 
     * @param rayLabel
     *            the rayLabel to set
     */
    public void setRayLabel(AbstractRayLabel rayLabel) {
        this.rayLabel = rayLabel;
    }

    /**
     * get ray axis label
     * 
     * @return the rayAxisLabel
     */
    public AbstractRayAxisLabel getRayAxisLabel() {
        return rayAxisLabel;
    }

    /**
     * set the ray axis label
     * 
     * @param rayAxisLabel
     *            the rayAxisLabel to set
     */
    public void setRayAxisLabel(AbstractRayAxisLabel rayAxisLabel) {
        this.rayAxisLabel = rayAxisLabel;
    }

    /**
     * get the ray name
     * 
     * @return the ray name
     */
    public String getName() {
        return name;
    }

    /**
     * set the ray name
     * 
     * @param name
     *            the ray name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the ray theme color
     * 
     * @return the ray theme color
     */
    public Color getThemeColor() {
        return themeColor;
    }

    /**
     * set the ray theme color
     * 
     * @param themeColor
     *            the theme color to set
     */
    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
    }

    /**
     * get the ray thickness
     * 
     * @return the ray thickness
     */
    public double getThickness() {
        return thickness;
    }

    /**
     * set the ray thickness
     * 
     * @param thickness
     *            the ray thickness to set
     */
    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    /**
     * get the thickness type
     * 
     * @return the thickness type
     */
    public ThicknessType getThicknessType() {
        return thicknessType;
    }

    /**
     * set the thickness type
     * 
     * @param thicknessType
     *            the ray thickness to set
     */
    public void setThicknessType(ThicknessType thicknessType) {
        this.thicknessType = thicknessType;
    }

    /**
     * get the ray nature
     * 
     * @return the ray nature
     */
    public RayNature getRayNature() {
        return rayNature;
    }

    /**
     * set the ray nature
     * 
     * @param rayNature
     *            the ray nature to set
     */
    public void setRayNature(RayNature rayNature) {
        this.rayNature = rayNature;
    }

    /**
     * get the ray define by the ray center in user coordinate
     * 
     * @return the ray center in user coordinate
     */
    public double getRay() {
        return ray;
    }

    /**
     * set the ray center in user coordinate
     * 
     * @param ray
     *            the ray center in user coordinate to set
     */
    public void setRay(double ray) {
        this.ray = ray;
    }

    /**
     * get the ray value in device coordinate
     * 
     * @return the ray value in device coordinate
     */
    public double getRayValue() {
        return rayValue;
    }

    /**
     * set ray ascent value, value should be greater than 0
     * 
     * @param value
     *            the ray ascent to set
     */
    public void setAscentValue(double value) {
        ascent = true;
        descent = false;
        if (value < 0) {
            throw new IllegalArgumentException(
                                               "ray value should be greater than 0");
        }
        rayValue = value;
    }

    /**
     * set ray descent value, value should be greater than 0
     * 
     * @param value
     *            the ray descent to set
     */
    public void setDescentValue(double value) {
        ascent = false;
        descent = true;
        if (value < 0) {
            throw new IllegalArgumentException(
                                               "ray value should be greater than 0");
        }
        rayValue = value;
    }

    /**
     * get this ray base
     * 
     * @return ray base
     */
    public double getRayBase() {
        return rayBase;
    }

    /**
     * set this ray base
     * 
     * @param rayBase
     *            the ray base to set
     */
    public void setRayBase(double rayBase) {
        this.rayBase = rayBase;
    }

    /**
     * return true if this ray is ascent, false otherwise
     * 
     * @return true if this ray is ascent, false otherwise
     */
    public boolean isAscent() {
        return ascent;
    }

    /**
     * return true if this ray is descent, false otherwise
     * 
     * @return true if this ray is descent, false otherwise
     */
    public boolean isDescent() {
        return descent;
    }

    /**
     * get this ray shape
     * 
     * @return the shape of this ray
     */
    public Rectangle2D getRayShape() {
        return rayShape;
    }

    /**
     * set this ray shape
     * 
     * @param rayShape
     */
    public void setRayShape(Rectangle2D rayShape) {
        this.rayShape = rayShape;
    }

    /**
     * get this ray host
     * 
     * @return the host of this ray
     */
    public RayPlugin getHost() {
        return host;
    }

    /**
     * set host of this ray
     * 
     * @param host
     *            this ray host to set
     */
    public void setHost(RayPlugin host) {
        this.host = host;
    }

    /**
     * return true if mouse has just enter in this ray, false otherwise
     * 
     * @return enter flag
     */
    public boolean isLockEnter() {
        return lockEnter;
    }

    /**
     * lock ray enter flag
     */
    public void lockEnter() {
        if (!isLockEnter()) {
            lockEnter = true;
        }
    }

    /**
     * unlock ray enter
     */
    public void unlockEnter() {
        if (isLockEnter()) {
            lockEnter = false;
        }
    }

    /**
     * @return the inflating
     */
    public boolean isInflating() {
        return inflating;
    }

    /**
     * @param inflating
     *            the inflating to set
     */
    public void setInflating(boolean inflating) {
        this.inflating = inflating;
    }

    /**
     * @return the deflating
     */
    public boolean isDeflating() {
        return deflating;
    }

    /**
     * @param deflating
     *            the deflating to set
     */
    public void setDeflating(boolean deflating) {
        this.deflating = deflating;
    }

    /**
     * @param lockEnter
     *            the lockEnter to set
     */
    public void setLockEnter(boolean lockEnter) {
        this.lockEnter = lockEnter;
    }

    /**
     * inflate bar
     * 
     * @param deltaValue
     *            the delta value to inflate
     * @param waitBeforeStarting
     *            the wait delay before starting
     * @param delay
     *            the step delay
     * @param step
     *            the inflate step
     */
    public void inflate(double deltaValue, int waitBeforeStarting, int delay,
            int step) {
        if (isInflating() || isDeflating()) {
            return;
        }
        Inflate inflate = new Inflate(deltaValue, waitBeforeStarting, delay,
                                      step);
        inflate.start();
    }

    /**
     * inflate thread animator
     */
    class Inflate extends Thread {

        /** wait before starting inflate */
        private int waitBeforeStarting;

        /** inflate step number */
        private int step;

        /** inflate transition drlay */
        private int delay;

        /** inflate vale */
        private double deltaValue;

        /**
         * create inflate
         * 
         * @param deltaValue
         * @param waitBeforeStarting
         * @param delay
         * @param step
         */
        public Inflate(double deltaValue, int waitBeforeStarting, int delay,
                int step) {
            this.waitBeforeStarting = waitBeforeStarting;
            this.deltaValue = deltaValue;
            this.delay = delay;
            this.step = step;
        }

        @Override
        public void run() {
            setInflating(true);
            try {
                Thread.sleep(waitBeforeStarting);
                double val = getRayValue();
                double valueByStep = deltaValue / step;
                int delayByStep = delay / step;
                for (int i = 0; i < step; i++) {
                    val = val + valueByStep;

                    if (isAscent()) {
                        setAscentValue(val);
                    }
                    else if (isDescent()) {

                        setDescentValue(val);
                    }

                    if (getHost() != null) {
                        getHost().resolveRayComponent(Ray.this);
                    }

                    if (getHost() != null) {
                        if (getRayNature() == RayNature.XRay) {
                            getHost()
                                    .getWindow2D()
                                    .getView2D()
                                    .repaintDeviceBand(
                                                       DeviceBand.XBand,
                                                       (int) getRayShape().getBounds()
                                                               .getX(),
                                                       (int) getRayShape().getBounds()
                                                               .getWidth() + 1);
                        }
                        else if (getRayNature() == RayNature.YRay) {
                            getHost()
                                    .getWindow2D()
                                    .getView2D()
                                    .repaintDeviceBand(
                                                       DeviceBand.YBand,
                                                       (int) getRayShape().getBounds()
                                                               .getY(),
                                                       (int) getRayShape().getBounds()
                                                               .getHeight() + 1);
                        }
                    }

                    Thread.sleep(delayByStep);

                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            finally {
                setInflating(false);
            }
        }
    }

    /**
     * deflate bar
     * 
     * @param deltaValue
     * @param waitBeforeStarting
     * @param delay
     * @param step
     */
    public void deflate(double deltaValue, int waitBeforeStarting, int delay,
            int step) {
        if (isInflating() || isDeflating()) {
            return;
        }
        Deflate deflate = new Deflate(deltaValue, waitBeforeStarting, delay,
                                      step);
        deflate.start();
    }

    /**
     * Deflate Thread Animator
     */
    class Deflate extends Thread {

        /** wait before starting deflate */
        private int waitBeforeStarting;

        /** deflate step number */
        private int step;

        /** delay for deflate */
        private int delay;

        /** deflate value */
        private double deltaValue;

        /**
         * create Deflate
         * 
         * @param deltaValue
         * @param waitBeforeStarting
         * @param delay
         * @param step
         */
        public Deflate(double deltaValue, int waitBeforeStarting, int delay,
                int step) {
            this.waitBeforeStarting = waitBeforeStarting;
            this.deltaValue = deltaValue;
            this.delay = delay;
            this.step = step;

        }

        @Override
        public void run() {
            setDeflating(true);
            try {
                Thread.sleep(waitBeforeStarting);

                double val = getRayValue();
                double valueByStep = deltaValue / step;
                int delayByStep = delay / step;
                for (int i = 0; i < step; i++) {

                    val = val - valueByStep;
                    if (isAscent()) {
                        setAscentValue(val);
                    }
                    else if (isDescent()) {
                        setDescentValue(val);
                    }
                    if (getHost() != null) {
                        getHost().resolveRayComponent(Ray.this);
                    }

                    if (getRayNature() == RayNature.XRay) {
                        getHost()
                                .getWindow2D()
                                .getView2D()
                                .repaintDeviceBand(
                                                   DeviceBand.XBand,
                                                   (int) getRayShape().getBounds().getX(),
                                                   (int) getRayShape().getBounds()
                                                           .getWidth() + 1);
                    }
                    else if (getRayNature() == RayNature.YRay) {
                        getHost()
                                .getWindow2D()
                                .getView2D()
                                .repaintDeviceBand(
                                                   DeviceBand.YBand,
                                                   (int) getRayShape().getBounds().getY(),
                                                   (int) getRayShape().getBounds()
                                                           .getHeight() + 1);
                    }

                    // oldBound = newBound;
                    Thread.sleep(delayByStep);

                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            finally {
                setDeflating(false);
            }
        }
    }

}
