/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d;

import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.donut2d.animator.AbstractDonut2DAnimator;
import com.jensoft.core.plugin.donut2d.painter.draw.AbstractDonut2DDraw;
import com.jensoft.core.plugin.donut2d.painter.effect.AbstractDonut2DEffect;
import com.jensoft.core.plugin.donut2d.painter.fill.AbstractDonut2DFill;
import com.jensoft.core.plugin.donut2d.painter.fill.Donut2DDefaultFill;

/**
 * <code>Donut2D</code>
 * <p>
 * <center><img src="doc-files/donut2d.png"></center> <br>
 * <br>
 * </p>
 * 
 * @author Sebastien Janaud
 */
public class Donut2D {

    /** donut2D name */
    private String name;

    /** donut2D nature */
    private Donut2DNature nature = Donut2DNature.Device;

    /** donut2D center x */
    private double centerX;

    /** donut2D center y */
    private double centerY;

    /** donut2D center x */
    private double buildCenterX;

    /** donut2D center y */
    private double buildCenterY;

    /** donut2D external radius */
    private double outerRadius;

    /** donut2D internal radius */
    private double innerRadius;

    /** donut2D start angle degree */
    private double startAngleDegree = 0;

    /** donut2D explose */
    private double explose = 0;

    /** donut2D draw */
    private AbstractDonut2DDraw donut2DDraw;

    /** donut2D fill */
    private AbstractDonut2DFill donut2DFill;

    /** donut2D effect */
    private AbstractDonut2DEffect donut2DEffect;

    /** donut2D slices */
    private List<Donut2DSlice> slices;

    /** private host plugin */
    private AbstractPlugin hostPlugin;

    /** listeners */
    private EventListenerList donut2DListenerList;

    /**
     * define coordinate system, User or Device
     */
    public enum Donut2DNature {

        /**
         * defines coordinate system in user space, base on window2D projection
         */
        User("user"),

        /**
         * defines coordinate system in device space, base on pixel component
         */
        Device("device");

        /** nature is the projection system */
        private String nature;

        /**
         * donut2d projection nature
         * 
         * @param nature
         *            the nature
         */
        private Donut2DNature(String nature) {
            this.nature = nature;
        }

        /**
         * get projection nature
         * 
         * @return the nature
         */
        public String getNature() {
            return nature;
        }

        /**
         * parse donut3D nature from given string
         * 
         * @param nature
         *            the nature to parse
         * @return donut3D nature
         */
        public static Donut2DNature parseNature(String nature) {
            if (Device.getNature().equals(nature)) {
                return Device;
            }
            if (User.getNature().equals(nature)) {
                return User;
            }
            return Device;
        }
    }

    /**
     * create empty donut
     */
    public Donut2D() {
        slices = new ArrayList<Donut2DSlice>();
        donut2DFill = new Donut2DDefaultFill();
        donut2DListenerList = new EventListenerList();
    }

    /**
     * add donut2D listener
     * 
     * @param listener
     *            the donut2D listener to add
     */
    public void addDonut2DListener(Donut2DListener listener) {
        donut2DListenerList.add(Donut2DListener.class, listener);
    }

    /**
     * remove donut2D listener
     * 
     * @param listener
     *            the donut2D listener to remove
     */
    public void removeDonut2DListener(Donut2DListener listener) {
        donut2DListenerList.remove(Donut2DListener.class, listener);
    }

    /**
     * @return the donut2DListenerList
     */
    public EventListenerList getDonut2DListenerList() {
        return donut2DListenerList;
    }

    /**
     * add donut2D animator
     * 
     * @param animator
     *            the donut2D animator to add
     */
    public void addDonutAnimator(AbstractDonut2DAnimator animator) {
        animator.setDonut2D(this);
        addDonut2DListener(animator);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the nature
     */
    public Donut2DNature getNature() {
        return nature;
    }

    /**
     * @param nature
     *            the nature to set
     */
    public void setNature(Donut2DNature nature) {
        this.nature = nature;
    }

    /**
     * @return the host plugin of this donut2D
     */
    public AbstractPlugin getHostPlugin() {
        return hostPlugin;
    }

    /**
     * @param host
     *            plugin
     *            the host plugin to set
     */
    public void setHostPlugin(AbstractPlugin host) {
        hostPlugin = host;
    }

    /**
     * get donut draw
     * 
     * @return donut draw
     */
    public AbstractDonut2DDraw getDonut2DDraw() {
        return donut2DDraw;
    }

    /**
     * set donut draw
     * 
     * @param donut2dDraw
     *            the draw to set
     */
    public void setDonut2DDraw(AbstractDonut2DDraw donut2dDraw) {
        donut2DDraw = donut2dDraw;
    }

    /**
     * get donut fill
     * 
     * @return donut fill
     */
    public AbstractDonut2DFill getDonut2DFill() {
        return donut2DFill;
    }

    /**
     * set donut fill
     * 
     * @param donut2dFill
     *            the fill to set
     */
    public void setDonut2DFill(AbstractDonut2DFill donut2dFill) {
        donut2DFill = donut2dFill;
    }

    /**
     * get donut effect
     * 
     * @return donut effect
     */
    public AbstractDonut2DEffect getDonut2DEffect() {
        return donut2DEffect;
    }

    /**
     * set donut effect
     * 
     * @param donut2dEffect
     *            the donut effect to set
     */
    public void setDonut2DEffect(AbstractDonut2DEffect donut2dEffect) {
        donut2DEffect = donut2dEffect;
    }

    /**
     * set alpha transparency and copy to slices if needed
     * 
     * @param alpha
     *            donut alpha to set
     */
    public void setAlpha(float alpha) {
        for (int i = 0; i < slices.size(); i++) {
            Donut2DSlice s = slices.get(i);
            s.setAlpha(alpha);
        }
    }

    /**
     * clear slices
     */
    public void clearSlices() {
        slices.clear();
    }

    /**
     * count slice of this donut
     * 
     * @return slice count
     */
    public int countSlice() {
        return slices.size();
    }

    /**
     * set donut slices
     * 
     * @param slices
     */
    public void setSlices(Vector<Donut2DSlice> slices) {
        this.slices = slices;
    }

    /**
     * get slice at the specified index
     * 
     * @param index
     *            the slice index
     * @return slice at the specified index
     */
    public Donut2DSlice getSlice(int index) {
        return slices.get(index);
    }

    /**
     * get donut center as non transform nature coordinate
     * 
     * @return donut center
     */
    public Point2D getCenter() {
        return new Point2D.Double(centerX, centerY);
    }

    /**
     * get center X coordinate
     * 
     * @return center X coordinate
     */
    public double getCenterX() {
        return centerX;
    }

    /**
     * set center x
     * 
     * @param centerX
     *            the center x coordinate to set
     */
    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    /**
     * get center Y coordinate
     * 
     * @return center Y coordinate
     */
    public double getCenterY() {
        return centerY;
    }

    /**
     * set center y
     * 
     * @param centerY
     *            the center y coordinate to set
     */
    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    /**
     * get the donut outer radius
     * 
     * @return donut outer radius
     */
    public double getOuterRadius() {
        return outerRadius;
    }

    /**
     * set the outer radius
     * 
     * @param outerRadius
     *            the outer radius to set
     */
    public void setOuterRadius(double outerRadius) {
        this.outerRadius = outerRadius;
    }

    /**
     * get inner radius
     * 
     * @return inner radius
     */
    public double getInnerRadius() {
        return innerRadius;
    }

    /**
     * set donut inner radius
     * 
     * @param innerRadius
     *            the inner radius
     */
    public void setInnerRadius(double innerRadius) {
        this.innerRadius = innerRadius;
    }

    /**
     * get start angle degree to set
     * 
     * @return start angle degree to set
     */
    public double getStartAngleDegree() {
        return startAngleDegree;
    }

    /**
     * set start angle degree
     * 
     * @param startAngleDegree
     *            the start angle degree to set
     */
    public void setStartAngleDegree(double startAngleDegree) {
        this.startAngleDegree = startAngleDegree;
    }

    /**
     * get donut explose
     * 
     * @return donut explose
     */
    public double getExplose() {
        return explose;
    }

    /**
     * set donut explose
     * 
     * @param explose
     *            the explose to set
     */
    public void setExplose(double explose) {
        this.explose = explose;
    }

    /**
     * add donut slice
     * 
     * @param slice
     *            the slice to add
     */
    public void addSlice(Donut2DSlice slice) {
        slice.setHost(this);
        slices.add(slice);
    }

    /**
     * solve donut value and normalize slice value as percent
     */
    private void sum() {
        double sum = 0;
        for (int i = 0; i < slices.size(); i++) {
            Donut2DSlice s = slices.get(i);
            sum = sum + s.getValue();
        }

        for (int i = 0; i < slices.size(); i++) {
            Donut2DSlice s = slices.get(i);
            double percent = s.getValue() / sum;
            s.setPercent(percent);
        }

    }

    /**
     * get donut2D Area
     * 
     * @return donut2D area
     */
    public Area getDonut2DArea() {
        Area donut2DArea = new Area();
        for (Donut2DSlice slice : getSlices()) {
            donut2DArea.add(new Area(slice.getSlicePath()));
        }
        return donut2DArea;
    }

    /**
     * compute buildCenterX and buildCenterY with given projection nature
     */
    private void projection() {
        if (getNature() == Donut2DNature.User) {
            Point2D center = getCenter();
            Point2D projectedCenter = getHostPlugin().getProjection().userToPixel(
                                                                                center);
            buildCenterX = (int) projectedCenter.getX();
            buildCenterY = (int) projectedCenter.getY();
        }
        else {
            buildCenterX = centerX;
            buildCenterY = centerY;
        }
    }

    /**
     * build donut slices
     */
    public void solveGeometry() {
        projection();
        sum();
        for (int j = 0; j < slices.size(); j++) {
            Donut2DSlice s = slices.get(j);
            buildSlice(s);
        }
    }

    /**
     * build slices
     * 
     * @param slice
     */
    private void buildSlice(Donut2DSlice slice) {

        double deltaDegree = slice.getPercent() * 360 - explose;

        if (startAngleDegree > 360) {
            startAngleDegree = startAngleDegree - 360;
        }

        double medianDegree = startAngleDegree + deltaDegree / 2;
        if (medianDegree > 360) {
            medianDegree = medianDegree - 360;
        }

        double sliceCenterX = buildCenterX + slice.getDivergence()
                * Math.cos(Math.toRadians(medianDegree));
        double sliceCenterY = buildCenterY - slice.getDivergence()
                * Math.sin(Math.toRadians(medianDegree));

        double cornerExternalX = sliceCenterX - getOuterRadius();
        double cornerExternalY = sliceCenterY - getOuterRadius();

        Arc2D externalArc2D = new Arc2D.Double(cornerExternalX,
                                               cornerExternalY, 2 * getOuterRadius(),
                                               2 * getOuterRadius(), startAngleDegree, deltaDegree,
                                               Arc2D.OPEN);

        slice.setOuterArc(externalArc2D);

        double cornerInternalX = sliceCenterX - getInnerRadius();
        double cornerInternalY = sliceCenterY - getInnerRadius();
        Arc2D internalArc2D = new Arc2D.Double(cornerInternalX,
                                               cornerInternalY, 2 * getInnerRadius(),
                                               2 * getInnerRadius(), startAngleDegree + deltaDegree,
                                               -deltaDegree, Arc2D.OPEN);

        slice.setInnerArc(internalArc2D);

        GeneralPath gp = new GeneralPath();
        gp.append(externalArc2D, false);
        gp.append(internalArc2D, true);
        gp.closePath();

        slice.setSlicePath(gp);

        Line2D lineStart = new Line2D.Double(internalArc2D.getEndPoint(), externalArc2D.getStartPoint());
        Line2D lineEnd = new Line2D.Double(externalArc2D.getEndPoint(), internalArc2D.getStartPoint());
        slice.setLineStart(lineStart);
        slice.setLineEnd(lineEnd);

        slice.setStartAngleDegree(startAngleDegree);
        slice.setEndAngleDegree(startAngleDegree + deltaDegree);
        startAngleDegree = startAngleDegree + deltaDegree + explose;

    }

    /**
     * get donut slices
     * 
     * @return donut slices
     */
    public List<Donut2DSlice> getSlices() {
        return slices;
    }

    /**
     * return true if donut is roll over, false otherwise
     * 
     * @return true if donut is roll over, false otherwise
     */
    public boolean isLockRollover() {
        for (int i = 0; i < slices.size(); i++) {
            Donut2DSlice s = slices.get(i);
            if (s.isLockRollover()) {
                return true;
            }

        }
        return false;
    }

    /**
     * return true if donut (any slice is lock) is lock false otherwise
     * 
     * @return true if donut is lock false otherwise
     */
    public boolean isLockEnter() {
        for (int i = 0; i < slices.size(); i++) {
            Donut2DSlice s = slices.get(i);
            if (s.isLockEnter()) {
                return true;
            }
        }
        return false;
    }

}
