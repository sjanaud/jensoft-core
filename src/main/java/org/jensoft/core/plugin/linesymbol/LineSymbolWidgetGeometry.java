/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.linesymbol;

import java.awt.Polygon;
import java.awt.geom.GeneralPath;

/**
 * define the geometry for a <code>LineSymBolWidget<code>
 * 
 * @see LineSymbolPlugin
 * @see LineSymbolWidget
 */
public class LineSymbolWidgetGeometry {

    /** the center x of this geometry */
    private int centerX;

    /** the center y of this geometry */
    private int centerY;

    /** radius of this geometry */
    private int radius;

    /** fragment radius */
    private double fragmentRadius;

    /**
     * create a this geometry
     * 
     * @param centerX
     *            the geometry center X
     * @param centerY
     *            the geometry center Y
     * @param radius
     *            the radius geometry
     */
    public LineSymbolWidgetGeometry(int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        fragmentRadius = new Double(radius) / 2.8;
    }

    /** control shape is the base shape */
    private GeneralPath controlShape;

    /** north sensible shape */
    private Polygon northSensibleShape;

    /** east sensible shape */
    private Polygon eastSensibleShape;

    /** south sensible shape */
    private Polygon southSensibleShape;

    /** west control shape */
    private Polygon westSensibleShape;

    /** lock North */
    private boolean lockNorth = false;

    /** lock East */
    private boolean lockEast = false;

    /** lock South */
    private boolean lockSouth = false;

    /** lock West */
    private boolean lockWest = false;

    /**
     * unlock sensible shape
     */
    public void unlockAllSensibleShape() {
        lockNorth = false;
        lockEast = false;
        lockSouth = false;
        lockWest = false;
    }

    /**
     * build NorthSouth geometry is typically use to scroll LineSymbol X
     */
    public void buildNorthSouth() {

        controlShape = new GeneralPath();

        // north control
        controlShape.moveTo(centerX - fragmentRadius, centerY - fragmentRadius);
        controlShape.lineTo(centerX - fragmentRadius, centerY - 2
                * fragmentRadius);
        controlShape.curveTo(centerX - fragmentRadius, centerY - radius,
                             centerX + fragmentRadius, centerY - radius, centerX
                                     + fragmentRadius, centerY - 2 * fragmentRadius);
        controlShape.lineTo(centerX + fragmentRadius, centerY - fragmentRadius);

        // south control
        controlShape.lineTo(centerX + fragmentRadius, centerY + 2
                * fragmentRadius);
        controlShape.curveTo(centerX + fragmentRadius, centerY + radius,
                             centerX - fragmentRadius, centerY + radius, centerX
                                     - fragmentRadius, centerY + 2 * fragmentRadius);
        controlShape.lineTo(centerX - fragmentRadius, centerY + fragmentRadius);

        controlShape.closePath();

        // sensible shape
        int deltaSensible = (int) (fragmentRadius / 1.8);

        northSensibleShape = new Polygon();
        northSensibleShape.addPoint(centerX - deltaSensible, centerY - radius
                / 2);
        northSensibleShape.addPoint(centerX + deltaSensible, centerY - radius
                / 2);
        northSensibleShape.addPoint(centerX, centerY - 5 * radius / 6);

        southSensibleShape = new Polygon();
        southSensibleShape.addPoint(centerX - deltaSensible, centerY + radius
                / 2);
        southSensibleShape.addPoint(centerX + deltaSensible, centerY + radius
                / 2);
        southSensibleShape.addPoint(centerX, centerY + 5 * radius / 6);

    }

    /**
     * build EastWest geometry is typically use to scroll LineSymbol X
     */
    public void buildEastWest() {

        controlShape = new GeneralPath();

        // east control
        controlShape.lineTo(centerX + 2 * fragmentRadius, centerY
                - fragmentRadius);
        controlShape.curveTo(centerX + radius, centerY - fragmentRadius,
                             centerX + radius, centerY + fragmentRadius, centerX + 2
                                     * fragmentRadius, centerY + fragmentRadius);
        controlShape.lineTo(centerX + fragmentRadius, centerY + fragmentRadius);
        // west control
        controlShape.lineTo(centerX - 2 * fragmentRadius, centerY
                + fragmentRadius);
        controlShape.curveTo(centerX - radius, centerY + fragmentRadius,
                             centerX - radius, centerY - fragmentRadius, centerX - 2
                                     * fragmentRadius, centerY - fragmentRadius);

        controlShape.closePath();

        // sensible shape
        int deltaSensible = (int) (fragmentRadius / 1.8);

        eastSensibleShape = new Polygon();
        eastSensibleShape.addPoint(centerX + radius / 2, centerY
                - deltaSensible);
        eastSensibleShape.addPoint(centerX + radius / 2, centerY
                + deltaSensible);
        eastSensibleShape.addPoint(centerX + 5 * radius / 6, centerY);

        westSensibleShape = new Polygon();
        westSensibleShape.addPoint(centerX - radius / 2, centerY
                - deltaSensible);
        westSensibleShape.addPoint(centerX - radius / 2, centerY
                + deltaSensible);
        westSensibleShape.addPoint(centerX - 5 * radius / 6, centerY);

    }

    /**
     * get the center X of this geometry
     * 
     * @return the center X of this geometry
     */
    public int getCenterX() {
        return centerX;
    }

    /**
     * set the center X of this geometry
     * 
     * @param centerX
     *            the center X to set
     */
    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    /**
     * get the center Y of this geometry
     * 
     * @return the center Y of this geometry
     */
    public int getCenterY() {
        return centerY;
    }

    /**
     * set the center Y of this geometry
     * 
     * @param centerY
     *            the center Y to set
     */
    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    /**
     * get the control base shape
     * 
     * @return the control shape
     */
    public GeneralPath getControlShape() {
        return controlShape;
    }

    /**
     * set the control base shape
     * 
     * @param controlShape
     *            the control base shape to set
     */
    public void setControlShape(GeneralPath controlShape) {
        this.controlShape = controlShape;
    }

    /**
     * get the north sensible shape geometry
     * 
     * @return the north sensible shape geometry
     */
    public Polygon getNorthSensibleShape() {
        return northSensibleShape;
    }

    /**
     * set the north sensible shape
     * 
     * @param northSensibleShape
     *            the north sensible shape geometry to set
     */
    public void setNorthSensibleShape(Polygon northSensibleShape) {
        this.northSensibleShape = northSensibleShape;
    }

    /**
     * get the east sensible shape geometry
     * 
     * @return the east sensible shape geometry
     */
    public Polygon getEastSensibleShape() {
        return eastSensibleShape;
    }

    /**
     * set the east sensible shape
     * 
     * @param eastSensibleShape
     *            the east sensible shape geometry to set
     */
    public void setEastSensibleShape(Polygon eastSensibleShape) {
        this.eastSensibleShape = eastSensibleShape;
    }

    /**
     * get the south sensible shape geometry
     * 
     * @return the south sensible shape geometry
     */
    public Polygon getSouthSensibleShape() {
        return southSensibleShape;
    }

    /**
     * set the south sensible shape
     * 
     * @param southSensibleShape
     *            the south sensible shape geometry to set
     */
    public void setSouthSensibleShape(Polygon southSensibleShape) {
        this.southSensibleShape = southSensibleShape;
    }

    /**
     * get the west sensible shape geometry
     * 
     * @return the west sensible shape geometry
     */
    public Polygon getWestSensibleShape() {
        return westSensibleShape;
    }

    /**
     * set the west sensible shape
     * 
     * @param westSensibleShape
     *            the west sensible shape geometry to set
     */
    public void setWestSensibleShape(Polygon westSensibleShape) {
        this.westSensibleShape = westSensibleShape;
    }

    /**
     * return true if north sensible is lock, false otherwise
     * 
     * @return true if north sensible is lock, false otherwise
     */
    public boolean isLockNorthSensible() {
        return lockNorth;
    }

    /**
     * lock/unlock north sensible
     * 
     * @param lockNorth
     *            lock north to set
     */
    public void setLockNorthSensible(boolean lockNorth) {
        this.lockNorth = lockNorth;
    }

    /**
     * return true if east sensible is lock, false otherwise
     * 
     * @return true if east sensible is lock, false otherwise
     */
    public boolean isLockEastSensible() {
        return lockEast;
    }

    /**
     * lock/unlock east sensible
     * 
     * @param lockEast
     *            lock east to set
     */
    public void setLockEastSensible(boolean lockEast) {
        this.lockEast = lockEast;
    }

    /**
     * return true if south sensible is lock, false otherwise
     * 
     * @return true if south sensible is lock, false otherwise
     */
    public boolean isLockSouthSensible() {
        return lockSouth;
    }

    /**
     * lock/unlock south sensible
     * 
     * @param lockSouth
     *            lock south to set
     */
    public void setLockSouthSensible(boolean lockSouth) {
        this.lockSouth = lockSouth;
    }

    /**
     * return true if west sensible is lock, false otherwise
     * 
     * @return true if west sensible is lock, false otherwise
     */
    public boolean isLockWestSensible() {
        return lockWest;
    }

    /**
     * lock/unlock west sensible
     * 
     * @param lockWest
     *            lock west to set
     */
    public void setLockWestSensible(boolean lockWest) {
        this.lockWest = lockWest;
    }

}
