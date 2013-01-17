/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie;

import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import com.jensoft.core.plugin.pie.animator.AbstractPieAnimator;
import com.jensoft.core.plugin.pie.painter.draw.AbstractPieDraw;
import com.jensoft.core.plugin.pie.painter.effect.AbstractPieEffect;
import com.jensoft.core.plugin.pie.painter.fill.AbstractPieFill;

/**
 * <code>Pie</code> <br>
 * <img src="doc-files/pie-border-label.png"> <br>
 * 
 * @author sebastien janaud
 */
public class Pie {

    /** pie name */
    private String name;

    /** center x */
    private double centerX = 0;

    /** center y */
    private double centerY = 0;

    /** radius */
    private double radius = 0;

    /** start angle degree */
    private double startAngleDegree = 0;

    /** pie draw */
    private AbstractPieDraw pieDraw;

    /** pie fill */
    private AbstractPieFill pieFill;

    /** pie effect */
    private AbstractPieEffect pieEffect;

    /** host plugin */
    private PiePlugin hostPlugin;

    /** pie slices */
    private List<PieSlice> slices;

    /** default pie nature */
    private PieNature pieNature = PieNature.PieUser;

    /** minimum from which the labels will be disabled */
    private double passiveLabelAtMinPercent = 0;

    /** listeners */
    private EventListenerList pieListenerList;

    /**
     * Pie nature defines pie projection.
     * <p>
     * nature is the pie projection, should be user projection or device
     * </p>
     * 
     * @author Sebastien Janaud
     */
    public enum PieNature {
        PieDevice("device"), PieUser("user");

        /** nature is the projection system */
        private String nature;

        /**
         * pie projection nature
         * 
         * @param nature
         *            the nature
         */
        private PieNature(String nature) {
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
         * parse pie nature from given string
         * 
         * @param nature
         *            the nature to parse
         * @return pie nature
         */
        public static PieNature parseNature(String nature) {
            if (PieDevice.getNature().equals(nature)) {
                return PieDevice;
            }
            if (PieUser.getNature().equals(nature)) {
                return PieUser;
            }
            return PieDevice;
        }

    }

    /**
     * create default pie
     */
    public Pie() {
        slices = new ArrayList<PieSlice>();
        pieListenerList = new EventListenerList();
    }

    /**
     * create pie with the specified name
     * 
     * @param name
     *            the pie name to set
     */
    public Pie(String name) {
        this();
        this.name = name;
    }

    /**
     * create pie with specified given parameters, default nature is in device
     * coordinate
     * 
     * @param name
     *            the pie name to set
     * @param radius
     *            the radius of the pie
     */
    public Pie(String name, double radius) {
        this(name);
        this.radius = radius;
    }

    /**
     * create pie with specified given parameters, default nature is in device
     * coordinate
     * 
     * @param name
     *            the pie name to set
     * @param centerX
     *            the centers x coordinate of the pie
     * @param centerY
     *            the center y coordinate of the pie
     * @param radius
     *            the radius of the pie
     */
    public Pie(String name, double centerX, double centerY, double radius) {
        this(name);
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    /**
     * create pie with specified given parameters
     * 
     * @param name
     *            the pie name to set
     * @param pieNature
     *            the pie nature to set
     * @param centerX
     *            the centers x coordinate of the pie
     * @param centerY
     *            the center y coordinate of the pie
     * @param radius
     *            the radius of the pie
     */
    public Pie(String name, PieNature pieNature, int centerX, int centerY,
            double radius) {
        this(name, centerX, centerY, radius);
        this.pieNature = pieNature;
    }

    /**
     * add pie animator
     * 
     * @param pie
     *            the pie animator to add
     */
    public void addPieAnimator(AbstractPieAnimator animator) {
        animator.setPie(this);
        addPieListener(animator);
    }

    /**
     * add pie listener
     * 
     * @param listener
     *            the pie listener to add
     */
    public void addPieListener(PieListener listener) {
        pieListenerList.add(PieListener.class, listener);
    }

    /**
     * @return the pieListenerList
     */
    public EventListenerList getPieListenerList() {
        return pieListenerList;
    }

    /**
     * remove pie listener
     * 
     * @param listener
     *            the pie listener to remove
     */
    public void removePieListener(PieListener listener) {
        pieListenerList.remove(PieListener.class, listener);
    }

    /**
     * get the host plugin of this pie
     * 
     * @return the host plugin of this pie
     */
    public PiePlugin getHostPlugin() {
        return hostPlugin;
    }

    /**
     * set the host plugin
     * 
     * @param host
     *            the host plugin of this pie
     */
    public void setHostPlugin(PiePlugin host) {
        hostPlugin = host;
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
     * get the center on x coordinate
     * 
     * @return the center x
     */
    public double getCenterX() {
        return centerX;
    }

    /**
     * set the center on x coordinate
     * 
     * @param centerX
     *            the center x to set
     */
    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    /**
     * get the center on y coordinate
     * 
     * @return the center y
     */
    public double getCenterY() {
        return centerY;
    }

    /**
     * set center on y coordinate
     * 
     * @param centerY
     *            center y to set
     */
    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    /**
     * get the start angle degree
     * 
     * @return the start angle degree
     */
    public double getStartAngleDegree() {
        return startAngleDegree;
    }

    /**
     * set start angle degree
     * 
     * @param startAngleDegree
     *            start angle degree to set
     */
    public void setStartAngleDegree(double startAngleDegree) {
        this.startAngleDegree = startAngleDegree;
    }

    /**
     * get teh radius
     * 
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * set the radius
     * 
     * @param radius
     *            the radius to set
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * get pied draw of this piue
     * 
     * @return the pie draw
     */
    public AbstractPieDraw getPieDraw() {
        return pieDraw;
    }

    /**
     * set the draw to this pie
     * 
     * @param pieDraw
     *            the pie draw to set
     */
    public void setPieDraw(AbstractPieDraw pieDraw) {
        this.pieDraw = pieDraw;
    }

    /**
     * get pie fill
     * 
     * @return the pie fill
     */
    public AbstractPieFill getPieFill() {
        return pieFill;
    }

    /**
     * set pie fill
     * 
     * @param pieFill
     *            the pie fill to set
     */
    public void setPieFill(AbstractPieFill pieFill) {
        this.pieFill = pieFill;
    }

    /**
     * get pie effect
     * 
     * @return pie effect
     */
    public AbstractPieEffect getPieEffect() {
        return pieEffect;
    }

    /**
     * set pie effect
     * 
     * @param pieEffect
     */
    public void setPieEffect(AbstractPieEffect pieEffect) {
        this.pieEffect = pieEffect;
    }

    /**
     * get slices
     * 
     * @return the slices of this pie
     */
    public List<PieSlice> getSlices() {
        return slices;
    }

    /**
     * set slices to this pie
     * 
     * @param slices
     *            the slices to set
     */
    public void setSlices(List<PieSlice> slices) {
        this.slices = slices;
    }

    /**
     * add slice to this pie
     * 
     * @param slice
     *            the slice to add
     */
    public void addSlice(PieSlice slice) {
        slice.setHost(this);
        slices.add(slice);
    }

    /**
     * get the pie nature
     * 
     * @return the pieNature
     */
    public PieNature getPieNature() {
        return pieNature;
    }

    /**
     * set the pie nature
     * 
     * @param pieNature
     *            the pieNature to set
     */
    public void setPieNature(PieNature pieNature) {
        this.pieNature = pieNature;
    }

    /**
     * make pie slice's sum
     */
    private void makeSum() {
        double sum = 0;
        for (int i = 0; i < slices.size(); i++) {
            PieSlice s = slices.get(i);
            sum = sum + s.getValue();
        }

        for (int i = 0; i < slices.size(); i++) {
            PieSlice s = slices.get(i);
            double percent = s.getValue() / sum;
            s.setPercent(percent);
        }

    }

    /**
     * get pie Area
     * 
     * @return pie area
     */
    public Area getPieArea() {
        Area pieArea = new Area();
        for (PieSlice slice : getSlices()) {
            pieArea.add(new Area(slice.getSlicePath()));
        }
        return pieArea;
    }

    /**
     * @return the passiveLabelAtMinPercent
     */
    public double getPassiveLabelAtMinPercent() {
        return passiveLabelAtMinPercent;
    }

    /**
     * @param passiveLabelAtMinPercent
     *            the passiveLabelAtMinPercent to set
     */
    public void setPassiveLabelAtMinPercent(double passiveLabelAtMinPercent) {
        this.passiveLabelAtMinPercent = passiveLabelAtMinPercent;
    }

    /**
     * remove all slice labels
     */
    public void removeAllSliceLabels() {
        for (PieSlice slice : getSlices()) {
            slice.removeAllSliceLabels();
        }
    }

    /**
     * return true if a pie slice has a divergence greater than zero, false
     * otherwise
     * 
     * @return true if a pie slice has a divergence greater than zero, false
     *         otherwise
     */
    public boolean isDivergent() {

        for (int i = 0; i < slices.size(); i++) {
            PieSlice s = slices.get(i);
            if (s.getDivergence() != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * build the pie
     */
    public void build() {
        makeSum();
        for (PieSlice slice : slices) {
            buildSlice(slice);
        }
    }

    /**
     * build slice
     * 
     * @param pieslice
     *            the pie slice to build
     */
    private void buildSlice(PieSlice slice) {

        double deltaDegree = slice.getPercent() * 360;

        if (startAngleDegree > 360) {
            startAngleDegree = startAngleDegree - 360;
        }

        double medianDegree = startAngleDegree + deltaDegree / 2;
        if (medianDegree > 360) {
            medianDegree = medianDegree - 360;
        }

        Point2D c = null;
        if (pieNature == PieNature.PieUser) {
            c = getHostPlugin().getWindow2D().userToPixel(
                                                          new Point2D.Double(centerX, centerY));
        }
        if (pieNature == PieNature.PieDevice) {
            c = new Point2D.Double(centerX, centerY);
        }

        double sliceCenterX = c.getX() + slice.getDivergence()
                * Math.cos(Math.toRadians(medianDegree));
        double sliceCenterY = c.getY() - slice.getDivergence()
                * Math.sin(Math.toRadians(medianDegree));

        double cornerExternalX = sliceCenterX - radius;
        double cornerExternalY = sliceCenterY - radius;
        Arc2D piePath = new Arc2D.Double(cornerExternalX, cornerExternalY,
                                         2 * radius, 2 * radius, startAngleDegree, deltaDegree,
                                         Arc2D.PIE);

        slice.setSlicePath(new GeneralPath(piePath));

        Arc2D externalPath = new Arc2D.Double(cornerExternalX, cornerExternalY,
                                              2 * radius, 2 * radius, startAngleDegree, deltaDegree,
                                              Arc2D.OPEN);

        slice.setExternalArc(externalPath);

        Line2D lineStart = new Line2D.Double(new Point2D.Double(sliceCenterX,
                                                                sliceCenterY), externalPath.getStartPoint());
        Line2D lineEnd = new Line2D.Double(externalPath.getEndPoint(),
                                           new Point2D.Double(sliceCenterX, sliceCenterY));
        slice.setLineStart(lineStart);
        slice.setLineEnd(lineEnd);

        slice.setStartAngleDegree(startAngleDegree);
        slice.setEndAngleDegree(startAngleDegree + deltaDegree);
        startAngleDegree = startAngleDegree + deltaDegree;

    }

}
