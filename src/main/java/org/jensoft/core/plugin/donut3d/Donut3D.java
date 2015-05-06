/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut3d;

import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jensoft.core.plugin.donut3d.Donut3DSlice.Type;
import org.jensoft.core.plugin.donut3d.painter.label.Donut3DBorderLabel;
import org.jensoft.core.plugin.donut3d.painter.label.Donut3DRadialLabel;
import org.jensoft.core.plugin.donut3d.painter.paint.AbstractDonut3DPaint;
import org.jensoft.core.plugin.donut3d.painter.paint.Donut3DDefaultPaint;

/**
 * <code>Donut3D</code> <br>
 * <img src="doc-files/Donut3D_1.png">
 * <img src="doc-files/Donut3D_2.png"> <br>

 * @see Donut3DPlugin
 * @see Donut3DBorderLabel
 * @see Donut3DRadialLabel
 * @see Donut3DToolkit
 * @see Donut3DDefaultPaint
 * @author Sebastien Janaud
 */
public class Donut3D {

    /** inner radius in pixel */
    private double innerRadius;

    /** outer radius in pixel */
    private double outerRadius;

    /** donut3D thickness */
    private double thickness;

    /** donut3D projection thickness */
    private double projectionThickness;

    /** tilt angle in degree 0 to 90 */
    private double tilt;

    /** outer A radius */
    private double outerA;

    /** outer B radius */
    private double outerB;

    /** inner A radius */
    private double innerA;

    /** inner B radius */
    private double innerB;

    /** center x coordinate in the specified coordinate system nature */
    private double centerX = 0;

    /** center y coordinate in the specified coordinate system nature */
    private double centerY = 0;

    /** start angle degree */
    private double startAngleDegree = 35;

    /** donut 3D paint */
    private AbstractDonut3DPaint donut3DPaint;

    /** donut 3D nature */
    private Donut3DNature donut3DNature = Donut3DNature.Donut3DUser;

    /** host plugin */
    private Donut3DPlugin hostPlugin;

    /** slices of this donut 3D */
    private List<Donut3DSlice> slices;

    /** donut 3d name */
    private String name;

    /**
     * define coordinate system, User or Device
     */
    public enum Donut3DNature {

        /**
         * defines coordinate system in user space, base on projection
         */
        Donut3DUser("user"),

        /**
         * defines coordinate system in device space, base on pixel component
         */
        Donut3DDevice("device");

        /** nature is the projection system */
        private String nature;

        /**
         * donut3d projection nature
         * 
         * @param nature
         *            the nature
         */
        private Donut3DNature(String nature) {
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
        public static Donut3DNature parseNature(String nature) {
            if (Donut3DDevice.getNature().equals(nature)) {
                return Donut3DDevice;
            }
            if (Donut3DUser.getNature().equals(nature)) {
                return Donut3DUser;
            }
            return Donut3DDevice;
        }
    }

    /**
     * create donut 3D
     */
    public Donut3D() {
        slices = new ArrayList<Donut3DSlice>();
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
     * @return the hostPlugin
     */
    public Donut3DPlugin getHostPlugin() {
        return hostPlugin;
    }

    /**
     * @param hostPlugin
     *            the hostPlugin to set
     */
    public void setHostPlugin(Donut3DPlugin hostPlugin) {
        this.hostPlugin = hostPlugin;
    }

    /**
     * @return the projectionThickness
     */
    public double getProjectionThickness() {
        return projectionThickness;
    }

    /**
     * @return the donut3DPaint
     */
    public AbstractDonut3DPaint getDonut3DPaint() {
        return donut3DPaint;
    }

    /**
     * @param donut3dPaint
     *            the donut3DPaint to set
     */
    public void setDonut3DPaint(AbstractDonut3DPaint donut3dPaint) {
        donut3DPaint = donut3dPaint;
    }

    /**
     * get this donut 3D nature
     * 
     * @return donut nature
     */
    public Donut3DNature getDonut3DNature() {
        return donut3DNature;
    }

    /**
     * set 3D nature
     * 
     * @param nature
     *            the nature to set
     */
    public void setDonut3DNature(Donut3DNature nature) {
        donut3DNature = nature;
    }

    /**
     * add slice in this donut
     * 
     * @param slice
     *            the slice to add
     */
    public void addSlice(Donut3DSlice slice) {
        slice.setHost(this);
        slices.add(slice);
    }

    /**
     * get slices of this donut
     * 
     * @return the slice of this donut
     */
    public List<Donut3DSlice> getSlices() {
        return slices;
    }

    /**
     * remove all slices labels of this donut
     */
    public void removeAllSliceLabels() {
        for (Donut3DSlice slice : getSlices()) {
            slice.removeAllSliceLabels();
        }
    }

    /**
     * normalize slices value
     */
    private void normalizeSlice() {

        double sum = 0;
        for (int i = 0; i < slices.size(); i++) {
            Donut3DSlice s = slices.get(i);
            sum = sum + s.getValue();
        }

        for (int i = 0; i < slices.size(); i++) {
            Donut3DSlice s = slices.get(i);
            double percent = s.getValue() / sum;
            s.setNormalizedValue(percent);
        }

    }

    /**
     * solve donut 3D geometry
     */
    public void solveDonut3D() {

        solveRadius();
        solveThickness();

        normalizeSlice();

        double buildAngleDegree = startAngleDegree;
        for (Donut3DSlice slice : slices) {
            solveSliceGeometry(slice, buildAngleDegree);
            solveSliceFragments(slice, buildAngleDegree);

            buildAngleDegree = slice.getEndAngleDegree();

            if (buildAngleDegree > 360) {
                buildAngleDegree = buildAngleDegree - 360;
            }
        }

    }

    private Donut3DSlice createSliceFragment(Donut3DSlice donutSlice,
            double startAngleDegree, double extendsDegree) {

        double centX;
        double centY;

        Donut3DSlice fragment = new Donut3DSlice(donutSlice.getName()
                + ".part", donutSlice.getThemeColor());
        
        fragment.setFragment(true);
        fragment.setParentSlice(donutSlice);
        
        if (startAngleDegree >= 0 && startAngleDegree < 180) {
            fragment.setType(Type.Back);
            fragment.setName(fragment.getName() + ".back");
        }
        else if (startAngleDegree >= 180 && startAngleDegree < 360) {
            fragment.setType(Type.Front);
            fragment.setName(fragment.getName() + ".front");
        }

        Point2D c = null;
        if (getDonut3DNature() == Donut3DNature.Donut3DUser) {
            c = getHostPlugin().getProjection().userToPixel(
                                                          new Point2D.Double(centerX, centerY));
        }
        if (getDonut3DNature() == Donut3DNature.Donut3DDevice) {
            c = new Point2D.Double(centerX, centerY);
        }

        centX = c.getX();
        centY = c.getY();

        // tilt 0--> 90
        double exploseTiltRadius = new Double(donutSlice.getDivergence()) / 90d;
        double exploseRadius = exploseTiltRadius * tilt;

        double exploseA = donutSlice.getDivergence();
        double exploseB = exploseRadius;

        double cX = centX
                + exploseA
                * Math.cos(Math.toRadians(donutSlice.getStartAngleDegree()
                        + donutSlice.getExtendsDegree() / 2));
        double cY = centY
                - exploseB
                * Math.sin(Math.toRadians(donutSlice.getStartAngleDegree()
                        + donutSlice.getExtendsDegree() / 2));

        double cornerOuterXA = cX - getOuterA();
        double cornerOuterYBTop = cY - getOuterB();
        double cornerOuterYBBottom = cY - getOuterB() + projectionThickness;

        Arc2D outerArcTop = new Arc2D.Double(cornerOuterXA, cornerOuterYBTop,
                                             2 * getOuterA(), 2 * getOuterB(), startAngleDegree,
                                             extendsDegree, Arc2D.OPEN);

        Arc2D outerArcBottom = new Arc2D.Double(cornerOuterXA,
                                                cornerOuterYBBottom, 2 * getOuterA(), 2 * getOuterB(),
                                                startAngleDegree, extendsDegree, Arc2D.OPEN);

        Arc2D outerArcBottomRevert = new Arc2D.Double(cornerOuterXA,
                                                      cornerOuterYBBottom, 2 * getOuterA(), 2 * getOuterB(),
                                                      startAngleDegree + extendsDegree, -extendsDegree, Arc2D.OPEN);

        fragment.setOuterArcTop(outerArcTop);
        fragment.setOuterArcBottom(outerArcBottom);

        double cornerInnerXA = cX - getInnerA();
        double cornerInnerYBTop = cY - getInnerB();
        double cornerInnerYBBottom = cY + projectionThickness - getInnerB();

        Arc2D innerArcTop = new Arc2D.Double(cornerInnerXA, cornerInnerYBTop,
                                             2 * getInnerA(), 2 * getInnerB(), startAngleDegree
                                                     + extendsDegree, -extendsDegree, Arc2D.OPEN);

        Arc2D innerArcBottom = new Arc2D.Double(cornerInnerXA,
                                                cornerInnerYBBottom, 2 * getInnerA(), 2 * getInnerB(),
                                                startAngleDegree + extendsDegree, -extendsDegree, Arc2D.OPEN);

        Arc2D innerArcBottomRevert = new Arc2D.Double(cornerInnerXA,
                                                      cornerInnerYBBottom, 2 * getInnerA(), 2 * getInnerB(),
                                                      startAngleDegree, extendsDegree, Arc2D.OPEN);

        fragment.setInnerArcTop(innerArcTop);
        fragment.setInnerArcBottom(innerArcBottom);

        // face top
        GeneralPath topFace = new GeneralPath();
        topFace.append(outerArcTop, false);
        topFace.append(innerArcTop, true);
        topFace.closePath();
        fragment.setTopFace(topFace);

        // face bottom
        GeneralPath bottomFace = new GeneralPath();
        bottomFace.append(outerArcBottom, false);
        bottomFace.append(innerArcBottom, true);
        bottomFace.closePath();
        fragment.setBottomFace(bottomFace);

        // face start
        GeneralPath pfaceStart = new GeneralPath();
        pfaceStart.moveTo(outerArcTop.getStartPoint().getX(), outerArcTop
                .getStartPoint().getY());
        pfaceStart.lineTo(innerArcTop.getEndPoint().getX(), innerArcTop
                .getEndPoint().getY());
        pfaceStart.lineTo(innerArcBottom.getEndPoint().getX(), innerArcBottom
                .getEndPoint().getY());
        pfaceStart.lineTo(outerArcBottom.getStartPoint().getX(), outerArcBottom
                .getStartPoint().getY());
        pfaceStart.closePath();
        fragment.setStartFace(pfaceStart);

        Line2D startExternalLine = new Line2D.Double(outerArcTop
                .getStartPoint().getX(), outerArcTop.getStartPoint().getY(),
                                                     outerArcBottom.getStartPoint().getX(), outerArcBottom
                                                             .getStartPoint().getY());
        Line2D startInternalLine = new Line2D.Double(innerArcTop.getEndPoint()
                .getX(), innerArcTop.getEndPoint().getY(), innerArcBottom
                .getEndPoint().getX(), innerArcBottom.getEndPoint().getY());
        Line2D startBottomLine = new Line2D.Double(innerArcBottom.getEndPoint()
                .getX(), innerArcBottom.getEndPoint().getY(), outerArcBottom
                .getStartPoint().getX(), outerArcBottom.getStartPoint().getY());
        Line2D startTopLine = new Line2D.Double(innerArcTop.getEndPoint()
                .getX(), innerArcTop.getEndPoint().getY(), outerArcTop
                .getStartPoint().getX(), outerArcTop.getStartPoint().getY());
        fragment.setStartOuterLine(startExternalLine);
        fragment.setStartInnerLine(startInternalLine);
        fragment.setStartTopLine(startTopLine);
        fragment.setStartBottomLine(startBottomLine);

        // face end
        GeneralPath pfaceEnd = new GeneralPath();
        pfaceEnd.moveTo(outerArcTop.getEndPoint().getX(), outerArcTop
                .getEndPoint().getY());
        pfaceEnd.lineTo(innerArcTop.getStartPoint().getX(), innerArcTop
                .getStartPoint().getY());
        pfaceEnd.lineTo(innerArcBottom.getStartPoint().getX(), innerArcBottom
                .getStartPoint().getY());
        pfaceEnd.lineTo(outerArcBottom.getEndPoint().getX(), outerArcBottom
                .getEndPoint().getY());
        pfaceEnd.closePath();
        fragment.setEndFace(pfaceEnd);

        Line2D endExternalLine = new Line2D.Double(outerArcTop.getEndPoint()
                .getX(), outerArcTop.getEndPoint().getY(), outerArcBottom
                .getEndPoint().getX(), outerArcBottom.getEndPoint().getY());
        Line2D endInternalLine = new Line2D.Double(innerArcTop.getStartPoint()
                .getX(), innerArcTop.getStartPoint().getY(), innerArcBottom
                .getStartPoint().getX(), innerArcBottom.getStartPoint().getY());
        Line2D endBottomLine = new Line2D.Double(innerArcBottom.getStartPoint()
                .getX(), innerArcBottom.getStartPoint().getY(), outerArcBottom
                .getEndPoint().getX(), outerArcBottom.getEndPoint().getY());
        Line2D endTopLine = new Line2D.Double(innerArcTop.getStartPoint()
                .getX(), innerArcTop.getStartPoint().getY(), outerArcTop
                .getEndPoint().getX(), outerArcTop.getEndPoint().getY());
        fragment.setEndOuterLine(endExternalLine);
        fragment.setEndInnerLine(endInternalLine);
        fragment.setEndTopLine(endTopLine);
        fragment.setEndBottomLine(endBottomLine);

        // internal face
        GeneralPath internalFace = new GeneralPath();
        internalFace.append(innerArcTop, false);
        internalFace.append(innerArcBottomRevert, true);
        internalFace.closePath();
        fragment.setInnerFace(internalFace);

        // external face
        GeneralPath externalFace = new GeneralPath();
        externalFace.append(outerArcTop, false);
        externalFace.append(outerArcBottomRevert, true);
        externalFace.closePath();
        fragment.setOuterFace(externalFace);

        fragment.setStartAngleDegree(startAngleDegree);
        fragment.setEndAngleDegree(startAngleDegree + extendsDegree);

        Ellipse2D innerEllipse = new Ellipse2D.Double(cX - innerA, cY - innerB,
                                                      2 * innerA, 2 * innerB);
        fragment.setInnerModel(innerEllipse);
        return fragment;
    }

    /**
     * get the top face of this donut 3d
     * 
     * @return the top face
     */
    public Area getTopFace() {
        Area top = new Area();
        for (Donut3DSlice s : slices) {
            List<Donut3DSlice> fragments = s.getFragments();
            for (Donut3DSlice fragment : fragments) {
                top.add(new Area(fragment.getTopFace()));
            }
        }
        return top;
    }

    /**
     * get the inner back face of this donut 3D
     * 
     * @return the inner back face
     */
    public Area getInnerBackFace() {
        Area innerbackface = new Area();
        for (Donut3DSlice s : slices) {
            List<Donut3DSlice> fragments = s.getFragments();
            for (Donut3DSlice fragment : fragments) {
                if (fragment.getType() == Type.Back) {
                    innerbackface.add(new Area(fragment.getInnerFace()));
                }
            }
        }
        return innerbackface;
    }

    /**
     * get end face of this donut 3D
     * 
     * @return the end face
     */
    public Area getEndFace() {
        Area endface = new Area();
        for (Donut3DSlice s : slices) {
            List<Donut3DSlice> fragments = s.getFragments();
            for (Donut3DSlice fragment : fragments) {
                if (s.isLast(fragment)) {
                    endface.add(new Area(fragment.getEndFace()));
                }
            }
        }
        return endface;
    }

    /**
     * get start face of this donut 3D
     * 
     * @return the start face
     */
    public Area getStartFace() {
        Area endface = new Area();
        for (Donut3DSlice s : slices) {
            List<Donut3DSlice> fragments = s.getFragments();
            for (Donut3DSlice fragment : fragments) {
                if (s.isFirst(fragment)) {
                    endface.add(new Area(fragment.getStartFace()));
                }
            }
        }
        return endface;
    }

    /**
     * get the first slice found at the specified angle
     * 
     * @param angleDegree
     *            the angle degree
     * @return the slice across the specified angle degree
     */
    public Donut3DSlice getSliceOnAngle(double angleDegree) {
        if (angleDegree < 0 && angleDegree > 360) {
            throw new IllegalArgumentException(
                                               "angleDegree out of range [0,360]");
        }

        for (Donut3DSlice s : slices) {

            if (s.getEndAngleDegree() <= 360) {
                if (s.getStartAngleDegree() <= angleDegree
                        && s.getEndAngleDegree() >= angleDegree) {
                    return s;
                }
            }
            else if (s.getEndAngleDegree() > 360) {

                double reboundAngleDegree = angleDegree;
                if (angleDegree < s.getStartAngleDegree()) {
                    reboundAngleDegree = angleDegree + 360d;
                }

                boolean f1 = s.getStartAngleDegree() <= reboundAngleDegree;
                boolean f2 = s.getEndAngleDegree() >= reboundAngleDegree;

                if (f1 && f2) {
                    return s;
                }
            }

        }
        return null;
    }

    /**
     * get all slices found with the specified angle degree
     * 
     * @param angleDegree
     *            the angle degree
     * @return the slices across this angle degree
     */
    public List<Donut3DSlice> getSlicesOnAngle(double angleDegree) {
        if (angleDegree < 0 && angleDegree > 360) {
            throw new IllegalArgumentException(
                                               "angleDegree out of range [0,360]");
        }

        List<Donut3DSlice> slicesOnAngle = new ArrayList<Donut3DSlice>();

        for (Donut3DSlice s : getSlices()) {

            if (s.getEndAngleDegree() <= 360) {
                if (s.getStartAngleDegree() <= angleDegree
                        && s.getEndAngleDegree() >= angleDegree) {
                    slicesOnAngle.add(s);
                }
            }
            else if (s.getEndAngleDegree() > 360) {

                double reboundAngleDegree = angleDegree;
                if (angleDegree < s.getStartAngleDegree()) {
                    reboundAngleDegree = angleDegree + 360d;
                }

                boolean f1 = s.getStartAngleDegree() <= reboundAngleDegree;
                boolean f2 = s.getEndAngleDegree() >= reboundAngleDegree;

                if (f1 && f2) {
                    slicesOnAngle.add(s);
                }
            }

        }

        return slicesOnAngle;
    }

    /**
     * get slices found on the specified frame angle
     * 
     * @param startAngleDegree
     *            the start angle degree
     * @param endAngleDegree
     *            the end angle degree
     * @return the slices intercept by the specified frame angle
     */
    public List<Donut3DSlice> getSlicesOnAngle(double startAngleDegree,
            double endAngleDegree) {
        if (startAngleDegree < 0 || startAngleDegree > 360
                || endAngleDegree < 0 || endAngleDegree > 360) {
            throw new IllegalArgumentException(
                                               "StarAngleDegree and EndAngleDegree out of range [0,360]");
        }
        if (endAngleDegree < startAngleDegree) {
            throw new IllegalArgumentException(
                                               "EndAngleDegree should be  greater than StartAngleDegree");
        }

        List<Donut3DSlice> slicesOnAngle = new ArrayList<Donut3DSlice>();
        for (Donut3DSlice s : getSlices()) {

        	//case 1 : s is strictly include in segment
            if (s.getStartAngleDegree() >= startAngleDegree
                    && s.getEndAngleDegree() >= startAngleDegree
                    && s.getStartAngleDegree() <= endAngleDegree
                    && s.getEndAngleDegree() <= endAngleDegree) {
                slicesOnAngle.add(s);
            }
            else if (s.getStartAngleDegree() <= startAngleDegree
                    && s.getEndAngleDegree() >= startAngleDegree
                    && s.getStartAngleDegree() <= endAngleDegree
                    && s.getEndAngleDegree() <= endAngleDegree) {
                slicesOnAngle.add(s);
            }
            else if (s.getStartAngleDegree() >= startAngleDegree
                    && s.getEndAngleDegree() >= startAngleDegree
                    && s.getStartAngleDegree() <= endAngleDegree
                    && s.getEndAngleDegree() >= endAngleDegree) {
                slicesOnAngle.add(s);
            }
            else if (s.getStartAngleDegree() <= startAngleDegree
                    && s.getEndAngleDegree() >= startAngleDegree
                    && s.getStartAngleDegree() <= endAngleDegree
                    && s.getEndAngleDegree() >= endAngleDegree) {
                slicesOnAngle.add(s);
            }

        }

        return slicesOnAngle;
    }
    
    /**
     * get slices found on the specified frame angle
     * 
     * @param startAngleDegree
     *            the start angle degree
     * @param endAngleDegree
     *            the end angle degree
     * @return the slices intercept by the specified frame angle
     */
    public List<Donut3DSlice> getSlicesFragmentOnAngle(Donut3DSlice sliceParent,double startAngleDegree,
            double endAngleDegree) {
        if (startAngleDegree < 0 || startAngleDegree > 360
                || endAngleDegree < 0 || endAngleDegree > 360) {
            throw new IllegalArgumentException(
                                               "StarAngleDegree and EndAngleDegree out of range [0,360]");
        }
        if (endAngleDegree < startAngleDegree) {
            throw new IllegalArgumentException(
                                               "EndAngleDegree should be  greater than StartAngleDegree");
        }

        List<Donut3DSlice> slicesOnAngle = new ArrayList<Donut3DSlice>();
        for (Donut3DSlice s : sliceParent.getFragments()) {

        	//case 1 : s is strictly include in segment
            if (s.getStartAngleDegree() >= startAngleDegree
                    && s.getEndAngleDegree() >= startAngleDegree
                    && s.getStartAngleDegree() <= endAngleDegree
                    && s.getEndAngleDegree() <= endAngleDegree) {
                slicesOnAngle.add(s);
            }
            else if (s.getStartAngleDegree() <= startAngleDegree
                    && s.getEndAngleDegree() >= startAngleDegree
                    && s.getStartAngleDegree() <= endAngleDegree
                    && s.getEndAngleDegree() <= endAngleDegree) {
                slicesOnAngle.add(s);
            }
            else if (s.getStartAngleDegree() >= startAngleDegree
                    && s.getEndAngleDegree() >= startAngleDegree
                    && s.getStartAngleDegree() <= endAngleDegree
                    && s.getEndAngleDegree() >= endAngleDegree) {
                slicesOnAngle.add(s);
            }
            else if (s.getStartAngleDegree() <= startAngleDegree
                    && s.getEndAngleDegree() >= startAngleDegree
                    && s.getStartAngleDegree() <= endAngleDegree
                    && s.getEndAngleDegree() >= endAngleDegree) {
                slicesOnAngle.add(s);
            }

        }

        return slicesOnAngle;
    }

    /**
     * clear slice paint flag
     */
    public void clearPaintFlag() {
        for (Donut3DSlice slices : getSlices()) {
            slices.setPainted(false);
        }
    }

    /**
     * solve slice fragment geometry
     * 
     * @param donutSlice
     *            the slice to solve
     * @param buildAngleDegree
     *            the start build angle degree of the specified slice
     */
    private void solveSliceFragments(Donut3DSlice donutSlice,double buildAngleDegree) {

        donutSlice.clearFragment();
        donutSlice.setPainted(false);
        double sliceStartDegree = buildAngleDegree;
        double sliceExtendsDegree = donutSlice.getNormalizedValue() * 360;

        donutSlice.setStartAngleDegree(sliceStartDegree);
        donutSlice.setEndAngleDegree(sliceStartDegree+ sliceExtendsDegree);
                

        double fragmentStartAngleDegree = sliceStartDegree;
        double fragmentExtends = sliceExtendsDegree;
        double resteExtends = sliceExtendsDegree;

        int count = 0;
        while (resteExtends > 0 && count++ < 4) {
        	
            fragmentExtends = getFragmentExtendsDegree(sliceStartDegree,
                                                       sliceExtendsDegree, fragmentStartAngleDegree,
                                                       resteExtends);

            Donut3DSlice fragment = createSliceFragment(donutSlice,
                                                        fragmentStartAngleDegree, fragmentExtends);
            //System.out.println("fragment : "+fragment.getName());
            donutSlice.addFragment(fragment);
            resteExtends = resteExtends - fragmentExtends;
            //System.out.println("reste extends : "+resteExtends);
            fragmentStartAngleDegree = fragmentStartAngleDegree
                    + fragmentExtends;

            if (fragmentStartAngleDegree >= 360) {
                fragmentStartAngleDegree = fragmentStartAngleDegree - 360;
            }

        }
        
        //System.out.println("count fragment for slice :"+donutSlice.getName()+" fragment count : "+donutSlice.getFragments().size());
    }

    /**
     * get the next fragment angle extends
     * 
     * @param sliceStartDegree
     * @param sliceExtendsDegree
     * @param fragmentStartDegree
     */
    private double getFragmentExtendsDegree(double sliceStartDegree,
            double sliceExtendsDegree, double fragmentStartDegree,
            double resteExtendsDegree) {

        // a=[0,180[
        // b=[180,360[

        if (fragmentStartDegree >= 0 && fragmentStartDegree < 180) {
            double potentialExtends = 180 - fragmentStartDegree;
            if (potentialExtends <= resteExtendsDegree) {
                return potentialExtends;
            }
            else {
                return resteExtendsDegree;
            }
        }
        else if (fragmentStartDegree >= 180 && fragmentStartDegree < 360) {
            double potentialExtends = 360 - fragmentStartDegree;
            if (potentialExtends <= resteExtendsDegree) {
                return potentialExtends;
            }
            else {
                return resteExtendsDegree;
            }
        }
        return 0;

    }

    /**
     * solve slice geometry
     * 
     * @param donutSlice
     *            the slice to solve
     * @param buildAngleDegree
     *            the start build angle degree of the specified slice
     */
    private void solveSliceGeometry(Donut3DSlice donutSlice,
            double buildAngleDegree) {

        double deltaDegree = donutSlice.getNormalizedValue() * 360;

        double centX;
        double centY;

        Point2D c = null;
        if (getDonut3DNature() == Donut3DNature.Donut3DUser) {
            c = getHostPlugin().getProjection().userToPixel(
                                                          new Point2D.Double(centerX, centerY));
        }
        if (getDonut3DNature() == Donut3DNature.Donut3DDevice) {
            c = new Point2D.Double(centerX, centerY);
        }

        centX = c.getX();
        centY = c.getY();

        // tilt 0--> 90
        double exploseTiltRadius = new Double(donutSlice.getDivergence()) / 90d;
        double exploseRadius = exploseTiltRadius * tilt;

        double exploseA = donutSlice.getDivergence();
        double exploseB = exploseRadius;

        double cX = centX + exploseA
                * Math.cos(Math.toRadians(buildAngleDegree + deltaDegree / 2));
        double cY = centY - exploseB
                * Math.sin(Math.toRadians(buildAngleDegree + deltaDegree / 2));

        donutSlice.setCenterX(cX);
        donutSlice.setCenterY(cY);

        double cornerOuterXA = cX - getOuterA();
        double cornerOuterYBTop = cY - getOuterB();
        double cornerOuterYBBottom = cY - getOuterB() + projectionThickness;

        Arc2D outerArcTop = new Arc2D.Double(cornerOuterXA, cornerOuterYBTop,
                                             2 * getOuterA(), 2 * getOuterB(), buildAngleDegree,
                                             deltaDegree, Arc2D.OPEN);

        Arc2D outerArcBottom = new Arc2D.Double(cornerOuterXA,
                                                cornerOuterYBBottom, 2 * getOuterA(), 2 * getOuterB(),
                                                buildAngleDegree, deltaDegree, Arc2D.OPEN);

        Arc2D outerArcBottomRevert = new Arc2D.Double(cornerOuterXA,
                                                      cornerOuterYBBottom, 2 * getOuterA(), 2 * getOuterB(),
                                                      buildAngleDegree + deltaDegree, -deltaDegree, Arc2D.OPEN);

        donutSlice.setOuterArcTop(outerArcTop);
        donutSlice.setOuterArcBottom(outerArcBottom);

        double cornerInnerXA = cX - getInnerA();
        double cornerInnerYBTop = cY - getInnerB();
        double cornerInnerYBBottom = cY + projectionThickness - getInnerB();

        Arc2D innerArcTop = new Arc2D.Double(cornerInnerXA, cornerInnerYBTop,
                                             2 * getInnerA(), 2 * getInnerB(), buildAngleDegree
                                                     + deltaDegree, -deltaDegree, Arc2D.OPEN);

        Arc2D innerArcBottom = new Arc2D.Double(cornerInnerXA,
                                                cornerInnerYBBottom, 2 * getInnerA(), 2 * getInnerB(),
                                                buildAngleDegree + deltaDegree, -deltaDegree, Arc2D.OPEN);

        Arc2D innerArcBottomRevert = new Arc2D.Double(cornerInnerXA,
                                                      cornerInnerYBBottom, 2 * getInnerA(), 2 * getInnerB(),
                                                      buildAngleDegree, deltaDegree, Arc2D.OPEN);

        donutSlice.setInnerArcTop(innerArcTop);
        donutSlice.setInnerArcBottom(innerArcBottom);

        // face top
        GeneralPath topFace = new GeneralPath();
        topFace.append(outerArcTop, false);
        topFace.append(innerArcTop, true);
        topFace.closePath();
        donutSlice.setTopFace(topFace);

        // face bottom
        GeneralPath bottomFace = new GeneralPath();
        bottomFace.append(outerArcBottom, false);
        bottomFace.append(innerArcBottom, true);
        bottomFace.closePath();
        donutSlice.setBottomFace(bottomFace);

        // face start
        GeneralPath pfaceStart = new GeneralPath();
        pfaceStart.moveTo(outerArcTop.getStartPoint().getX(), outerArcTop
                .getStartPoint().getY());
        pfaceStart.lineTo(innerArcTop.getEndPoint().getX(), innerArcTop
                .getEndPoint().getY());
        pfaceStart.lineTo(innerArcBottom.getEndPoint().getX(), innerArcBottom
                .getEndPoint().getY());
        pfaceStart.lineTo(outerArcBottom.getStartPoint().getX(), outerArcBottom
                .getStartPoint().getY());
        pfaceStart.closePath();
        donutSlice.setStartFace(pfaceStart);

        Line2D startExternalLine = new Line2D.Double(outerArcTop
                .getStartPoint().getX(), outerArcTop.getStartPoint().getY(),
                                                     outerArcBottom.getStartPoint().getX(), outerArcBottom
                                                             .getStartPoint().getY());
        Line2D startInternalLine = new Line2D.Double(innerArcTop.getEndPoint()
                .getX(), innerArcTop.getEndPoint().getY(), innerArcBottom
                .getEndPoint().getX(), innerArcBottom.getEndPoint().getY());
        Line2D startBottomLine = new Line2D.Double(innerArcBottom.getEndPoint()
                .getX(), innerArcBottom.getEndPoint().getY(), outerArcBottom
                .getStartPoint().getX(), outerArcBottom.getStartPoint().getY());
        Line2D startTopLine = new Line2D.Double(innerArcTop.getEndPoint()
                .getX(), innerArcTop.getEndPoint().getY(), outerArcTop
                .getStartPoint().getX(), outerArcTop.getStartPoint().getY());
        donutSlice.setStartOuterLine(startExternalLine);
        donutSlice.setStartInnerLine(startInternalLine);
        donutSlice.setStartTopLine(startTopLine);
        donutSlice.setStartBottomLine(startBottomLine);

        // face end
        GeneralPath pfaceEnd = new GeneralPath();
        pfaceEnd.moveTo(outerArcTop.getEndPoint().getX(), outerArcTop
                .getEndPoint().getY());
        pfaceEnd.lineTo(innerArcTop.getStartPoint().getX(), innerArcTop
                .getStartPoint().getY());
        pfaceEnd.lineTo(innerArcBottom.getStartPoint().getX(), innerArcBottom
                .getStartPoint().getY());
        pfaceEnd.lineTo(outerArcBottom.getEndPoint().getX(), outerArcBottom
                .getEndPoint().getY());
        pfaceEnd.closePath();
        donutSlice.setEndFace(pfaceEnd);

        Line2D endExternalLine = new Line2D.Double(outerArcTop.getEndPoint()
                .getX(), outerArcTop.getEndPoint().getY(), outerArcBottom
                .getEndPoint().getX(), outerArcBottom.getEndPoint().getY());
        Line2D endInternalLine = new Line2D.Double(innerArcTop.getStartPoint()
                .getX(), innerArcTop.getStartPoint().getY(), innerArcBottom
                .getStartPoint().getX(), innerArcBottom.getStartPoint().getY());
        Line2D endBottomLine = new Line2D.Double(innerArcBottom.getStartPoint()
                .getX(), innerArcBottom.getStartPoint().getY(), outerArcBottom
                .getEndPoint().getX(), outerArcBottom.getEndPoint().getY());
        Line2D endTopLine = new Line2D.Double(innerArcTop.getStartPoint()
                .getX(), innerArcTop.getStartPoint().getY(), outerArcTop
                .getEndPoint().getX(), outerArcTop.getEndPoint().getY());
        donutSlice.setEndOuterLine(endExternalLine);
        donutSlice.setEndInnerLine(endInternalLine);
        donutSlice.setEndTopLine(endTopLine);
        donutSlice.setEndBottomLine(endBottomLine);

        // internal face
        GeneralPath internalFace = new GeneralPath();
        internalFace.append(innerArcTop, false);
        internalFace.append(innerArcBottomRevert, true);
        internalFace.closePath();
        donutSlice.setInnerFace(internalFace);

        // external face
        GeneralPath externalFace = new GeneralPath();
        externalFace.append(outerArcTop, false);
        externalFace.append(outerArcBottomRevert, true);
        externalFace.closePath();
        donutSlice.setOuterFace(externalFace);

        donutSlice.setStartAngleDegree(buildAngleDegree);
        donutSlice.setEndAngleDegree(buildAngleDegree + deltaDegree);

    }

    /**
     * get outer A
     * 
     * @return the outer A
     */
    public double getOuterA() {
        return outerA;
    }

    /**
     * get outer B
     * 
     * @return the outer B
     */
    public double getOuterB() {
        return outerB;
    }

    /**
     * get the inner A
     * 
     * @return inner A
     */
    public double getInnerA() {
        return innerA;
    }

    /**
     * get the inner B
     * 
     * @return inner B
     */
    public double getInnerB() {
        return innerB;
    }

    /**
     * get the center x coordinate
     * 
     * @return the center x coordinate
     */
    public double getCenterX() {
        return centerX;
    }

    /**
     * set the center x coordinate
     * 
     * @param centerX
     *            the center x coordinate to set
     */
    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    /**
     * get the center y coordinate
     * 
     * @return the center y coordinate
     */
    public double getCenterY() {
        return centerY;
    }

    /**
     * set the center y coordinate
     * 
     * @param centerY
     *            the center y coordinate to set
     */
    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    /**
     * get the thickness
     * 
     * @return the thickness
     */
    public double getThickness() {
        return thickness;
    }

    /**
     * set the thickness
     * 
     * @param thickness
     *            the thickness to set
     */
    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    /**
     * get start angle degree
     * 
     * @return start angle degree
     */
    public double getStartAngleDegree() {
        return startAngleDegree;
    }

    /**
     * set start angle degree, range in [0, 360] degrees
     * 
     * @param startAngleDegree
     *            the start angle degree to set
     */
    public void setStartAngleDegree(double startAngleDegree) {
        if (startAngleDegree >= 360) {
            throw new IllegalArgumentException(
                                               "start angle degree out of range [0,360[");
        }
        this.startAngleDegree = startAngleDegree;
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
     * set the inner radius
     * 
     * @param innerRadius
     *            the inner radius to set
     */
    public void setInnerRadius(double innerRadius) {
        this.innerRadius = innerRadius;
    }

    /**
     * get the outer radius
     * 
     * @return the outer radius
     */
    public double getOuterRadius() {
        return outerRadius;
    }

    /**
     * set the outer radius
     * 
     * @param outerRadius
     */
    public void setOuterRadius(double outerRadius) {
        this.outerRadius = outerRadius;
    }

    /**
     * get tilt
     * 
     * @return tilt
     */
    public double getTilt() {
        return tilt;
    }

    /**
     * set the tilt
     * 
     * @param tilt
     *            the tilt to set
     */
    public void setTilt(double tilt) {
        if (tilt < 0 || tilt > 90) {
            throw new IllegalArgumentException("tilt out of range [0,90]");
        }
        this.tilt = tilt;

    }

    /**
     * solve Thickness
     */
    private void solveThickness() {
        double oneDegreeTiltThickness = thickness / 90d;
        projectionThickness = oneDegreeTiltThickness * (90 - tilt);
    }

    /**
     * solve A and B radius
     */
    private void solveRadius() {

        double oneTiltExternalProfil = new Double(outerRadius) / 90d;
        double oneTiltInternalProfil = new Double(innerRadius) / 90d;

        outerA = outerRadius;
        outerB = oneTiltExternalProfil * tilt;

        innerA = innerRadius;
        innerB = oneTiltInternalProfil * tilt;
    }

    /**
     * return true if a donut3D slice is lock enter, false otherwise
     * 
     * @return true if a donut3D slice is lock enter, false otherwise
     */
    public boolean isSliceLockEnter() {
        for (Donut3DSlice slice : getSlices()) {
            if (slice.isLockEnter()) {
                return true;
            }
        }
        return false;
    }

    /**
     * true if the specified slice is in specified slice collection, false
     * otherwise
     * 
     * @param donutSlice
     * @param slices
     * @return true if the specified slice is in specified slice collection,
     *         false otherwise
     */
    public static boolean in(Donut3DSlice donutSlice,
            List<Donut3DSlice> slices) {
        for (Donut3DSlice donut3dSlice : slices) {
            if (donut3dSlice.equals(donutSlice)) {
                return true;
            }
        }
        return false;
    }
    
 
    /**
     * get ordered slice to paint
     * 
     * @return sorted slices to be paint
     */
    public List<Donut3DSlice> getPaintOrderFragments() {
    	 List<Donut3DSlice> paintOrderFragments = new ArrayList<Donut3DSlice>();
    	 
    	 List<Donut3DSlice> firstSlices = getSlicesOnAngle(90);    	 
    	 List<Donut3DSlice> flattenFirstSlicesFragments = new ArrayList<Donut3DSlice>();
         for (Donut3DSlice firstSlice : firstSlices) {
        	 //System.out.println(" add first slices : "+firstSlice.getName());
        	 flattenFirstSlicesFragments.addAll(firstSlice.getFragments());
         }
    	 
         List<Donut3DSlice> lastSlices = getSlicesOnAngle(270);   
         List<Donut3DSlice> flattenLastSlicesFragments = new ArrayList<Donut3DSlice>();
         for (Donut3DSlice lastSlice : lastSlices) {
        	 flattenLastSlicesFragments.addAll(lastSlice.getFragments());
         }
         
         //process twice for back and front, to avoid issue when multiple slice on 90 extends back and front

         //FIRST BACK
         //first fragment on 90°        
         for (Donut3DSlice firstSliceFragment : flattenFirstSlicesFragments) {
			if(firstSliceFragment.getStartAngleDegree() <= 90 && firstSliceFragment.getEndAngleDegree() >= 90 && firstSliceFragment.getType() == Type.Back){
				//System.out.println("found back 90 deg fragment : "+firstSliceFragment.getName() +" for  ["+firstSliceFragment.getStartAngleDegree()+","+firstSliceFragment.getEndAngleDegree()+"]");
				paintOrderFragments.add(firstSliceFragment);
			}
         }        	
         //other from first
         for (Donut3DSlice firstSliceFragment : flattenFirstSlicesFragments) {
        	  if (!in(firstSliceFragment, paintOrderFragments) && !in(firstSliceFragment, flattenLastSlicesFragments) && firstSliceFragment.getType() == Type.Back) {
        		  //System.out.println("found other back 90 deg fragment : "+firstSliceFragment.getName() +" for  ["+firstSliceFragment.getStartAngleDegree()+","+firstSliceFragment.getEndAngleDegree()+"]");
        		  paintOrderFragments.add(firstSliceFragment);
              }
         }
         
         //FIRST FRONT
         //first fragment on 90°        
         for (Donut3DSlice firstSliceFragment : flattenFirstSlicesFragments) {
			if(firstSliceFragment.getStartAngleDegree() <= 90 && firstSliceFragment.getEndAngleDegree() >= 90 && firstSliceFragment.getType() == Type.Front){
				//System.out.println("found front 90 deg fragment : "+firstSliceFragment.getName() +" for  ["+firstSliceFragment.getStartAngleDegree()+","+firstSliceFragment.getEndAngleDegree()+"]");
				paintOrderFragments.add(firstSliceFragment);
			}
         }        	
         //other from first
         for (Donut3DSlice firstSliceFragment : flattenFirstSlicesFragments) {
        	  if (!in(firstSliceFragment, paintOrderFragments) && !in(firstSliceFragment, flattenLastSlicesFragments) && firstSliceFragment.getType() == Type.Front) {
        		  //System.out.println("found other front 90 deg fragment : "+firstSliceFragment.getName() +" for  ["+firstSliceFragment.getStartAngleDegree()+","+firstSliceFragment.getEndAngleDegree()+"]");
        		  paintOrderFragments.add(firstSliceFragment);
              }
         }
         
         
         
         //left
         List<Donut3DSlice> slicesLeft = getSlicesOnAngle(90, 270);
         for (Donut3DSlice sliceLeft : slicesLeft) {
        	 List<Donut3DSlice> sliceLeftFragments = sliceLeft.getFragments();
        	 for (Donut3DSlice leftFragment : sliceLeftFragments) {
        		 if (!in(leftFragment, paintOrderFragments) && !in(leftFragment, flattenLastSlicesFragments)) {
        			 paintOrderFragments.add(leftFragment);
                 }
			}
             
         }
         
         // right
         Donut3DSlice zeroSlice = getSliceOnAngle(0);
         
         List<Donut3DSlice> slicesRight1 = getSlicesOnAngle(0, 90);
         Collections.reverse(slicesRight1);
         for (Donut3DSlice sliceRight1 : slicesRight1) {
        	 List<Donut3DSlice> right1Fragments = getSlicesFragmentOnAngle(sliceRight1, 0, 90);
        	 for (Donut3DSlice right1Fragment : right1Fragments) { 
        		 
        		 
        		 if (!in(right1Fragment, paintOrderFragments) && !zeroSlice.getFragments().contains(right1Fragment)
                         /*&& !in(right1Fragment, flattenLastSlicesFragments)*/) {
        			 if(in(right1Fragment, flattenLastSlicesFragments)){
	        			 for (Donut3DSlice dLastFragment : flattenLastSlicesFragments) {
							if(dLastFragment.equals(right1Fragment) && right1Fragment.getType() == Type.Back){
								paintOrderFragments.add(right1Fragment);
							}
	        			 }
        			 }else{
        				 paintOrderFragments.add(right1Fragment);
        			 }
        			 
                 }
        	 }
             
         }

        
         List<Donut3DSlice> zeroFragments = zeroSlice.getFragments();
	       for (int i = zeroFragments.size()-1; i >= 0; i--) {
	    	   Donut3DSlice zeroFragment = zeroFragments.get(i);
	         	  if (zeroFragment != null && !in(zeroFragment, paintOrderFragments)
	                       /*&& !in(zeroFragment, flattenLastSlicesFragments)*/) {
	              	 //paintOrderFragments.add(zeroFragment);
	         		 if(in(zeroFragment, flattenLastSlicesFragments)){
	        			 for (Donut3DSlice dLastFragment : flattenLastSlicesFragments) {
							if(dLastFragment.equals(zeroFragment) && zeroFragment.getType() == Type.Back){
								paintOrderFragments.add(zeroFragment);
							}
	        			 }
        			 }else{
        				 paintOrderFragments.add(zeroFragment);
        			 }
	               }
	          
	       }
        // Collections.reverse(zeroFragments);
        
       

         // RIGHT 2
         List<Donut3DSlice> slicesRight2 = getSlicesOnAngle(270, 360);
         Collections.reverse(slicesRight2);
         for (Donut3DSlice sliceRight2 : slicesRight2) {
        	 //List<Donut3DSlice> right2Fragments = sliceRight2.getFragments();
        	 List<Donut3DSlice> right2Fragments = getSlicesFragmentOnAngle(sliceRight2, 270, 360);
        	 for (Donut3DSlice right2Fragment : right2Fragments) {
        		 if (!in(right2Fragment, paintOrderFragments) && !zeroSlice.getFragments().contains(right2Fragment)
                         /*&& !in(right2Fragment, flattenLastSlicesFragments)*/) {
        			 //paintOrderFragments.add(right2Fragment);
        			 if(in(right2Fragment, flattenLastSlicesFragments)){
	        			 for (Donut3DSlice dLastFragment : flattenLastSlicesFragments) {
							if(dLastFragment.equals(right2Fragment) && right2Fragment.getType() == Type.Back){
								paintOrderFragments.add(right2Fragment);
							}
	        			 }
        			 }else{
        				 paintOrderFragments.add(right2Fragment);
        			 }
                 }
        	 }
        	
         }

         for (Donut3DSlice lastFragment : flattenLastSlicesFragments) {
        	 if (!in(lastFragment, paintOrderFragments)){
        		 paintOrderFragments.add(lastFragment);
        	 }else{
        		 //System.out.println("last fragment already use :"+lastFragment.getName()+" "+ lastFragment.getStartAngleDegree()+"-->"+lastFragment.getEndAngleDegree());
        	 }
		}
         //System.out.println("paint order by fragments "+paintOrderFragments);
         return paintOrderFragments;
    }

    /**
     * get ordered slice to paint
     * 
     * @return sorted slices to be paint
     */
    public List<Donut3DSlice> getPaintOrder() {
    	getPaintOrderFragments();
    	
        List<Donut3DSlice> paintOrder = new ArrayList<Donut3DSlice>();

        List<Donut3DSlice> firstSlices = getSlicesOnAngle(90);
        List<Donut3DSlice> lastSlices = getSlicesOnAngle(270);

        for (Donut3DSlice firstSlice : firstSlices) {
            if (firstSlice != null && !in(firstSlice, paintOrder)
                    && !in(firstSlice, lastSlices)) {
                paintOrder.add(firstSlice);
            }
        }        

        // LEFT
        List<Donut3DSlice> slicesLeft = getSlicesOnAngle(90, 270);
        for (Donut3DSlice sliceLeft : slicesLeft) {
            if (!in(sliceLeft, paintOrder) && !in(sliceLeft, lastSlices)) {
                paintOrder.add(sliceLeft);
            }
        }

        // RIGHT
        Donut3DSlice zeroSlice = getSliceOnAngle(0);
        List<Donut3DSlice> slicesRight1 = getSlicesOnAngle(0, 90);

        Collections.reverse(slicesRight1);
        for (Donut3DSlice sliceRight1 : slicesRight1) {
            if (!in(sliceRight1, paintOrder) && !sliceRight1.equals(zeroSlice)
                    && !in(sliceRight1, lastSlices)) {
                paintOrder.add(sliceRight1);
            }
        }

        if (zeroSlice != null && !in(zeroSlice, paintOrder)
                && !in(zeroSlice, lastSlices)) {
            paintOrder.add(zeroSlice);
        }

        // RIGHT 2
        List<Donut3DSlice> slicesRight2 = getSlicesOnAngle(270, 360);
        Collections.reverse(slicesRight2);
        for (Donut3DSlice sliceRight2 : slicesRight2) {
            if (!in(sliceRight2, paintOrder) && !in(sliceRight2, lastSlices)) {
                paintOrder.add(sliceRight2);
            }
        }

        // PAINT LAST
        for (Donut3DSlice lastSlice : lastSlices) {
            if (lastSlice != null) {            	
            	paintOrder.add(lastSlice);          
                
            }
        }
        return paintOrder;
    }

}
