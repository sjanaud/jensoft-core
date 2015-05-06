/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.widget.pad;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

import org.jensoft.core.widget.AbstractWidgetGeometry;

/**
 * AbstractPadGeometry
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractPadGeometry extends AbstractWidgetGeometry {

    /** widget bounding frame */
    private Rectangle2D bound2D;

    /** pad center x coordinate */
    private double centerX;

    /** pad center y coordinate */
    private double centerY;

    /** pad radius */
    private double radius;

    /** fragment radius */
    private double fragmentRadius;

    /** base shape */
    private Ellipse2D baseShape;

    /** control shape */
    private GeneralPath controlShape;

    /** north button bounding rectangle */
    private Rectangle2D rectNorth;

    /** south button bounding rectangle */
    private Rectangle2D rectSouth;

    /** west button bounding rectangle */
    private Rectangle2D rectWest;

    /** button east bouding rectangle */
    private Rectangle2D rectEast;

    /** north button shape */
    private Shape northButton;

    /** east button shape */
    private Shape eastButton;

    /** south button shape */
    private Shape southButton;

    /** west button shape */
    private Shape westButton;

    /** north roll over flag */
    private boolean northRollover = false;

    /** east roll over flag */
    private boolean eastRollover = false;

    /** south roll over flag */
    private boolean southRollover = false;

    /** west roll over flag */
    private boolean westRollover = false;

    /** button inset */
    private int inset = 6;

    /** solve geometry request */
    private boolean solveRequest = true;

    /**
     * create abstract pad geometry
     */
    public AbstractPadGeometry() {
    }

    /**
     * @return the solveRequest
     */
    public boolean isSolveRequest() {
        return solveRequest;
    }

    /**
     * @param solveRequest
     *            the solveRequest to set
     */
    public void setSolveRequest(boolean solveRequest) {
        this.solveRequest = solveRequest;
    }

    /**
     * solve pad base geometry
     * solve base shape solve control shape solve each button bounding frame
     * rectangle
     */
    public final void solvePadGeometry() {

        // BASE SHAPE
        baseShape = new Ellipse2D.Double(centerX - radius, centerY - radius,
                                         2 * radius, 2 * radius);

        controlShape = new GeneralPath();

        // CONTROL SHAPE
        // north control
        controlShape.moveTo(centerX - fragmentRadius, centerY - fragmentRadius);
        controlShape.lineTo(centerX - fragmentRadius, centerY - 2
                * fragmentRadius);
        controlShape.curveTo(centerX - fragmentRadius, centerY - radius,
                             centerX + fragmentRadius, centerY - radius, centerX
                                     + fragmentRadius, centerY - 2 * fragmentRadius);
        controlShape.lineTo(centerX + fragmentRadius, centerY - fragmentRadius);

        // east control
        controlShape.lineTo(centerX + 2 * fragmentRadius, centerY
                - fragmentRadius);
        controlShape.curveTo(centerX + radius, centerY - fragmentRadius,
                             centerX + radius, centerY + fragmentRadius, centerX + 2
                                     * fragmentRadius, centerY + fragmentRadius);
        controlShape.lineTo(centerX + fragmentRadius, centerY + fragmentRadius);

        // south control
        controlShape.lineTo(centerX + fragmentRadius, centerY + 2
                * fragmentRadius);
        controlShape.curveTo(centerX + fragmentRadius, centerY + radius,
                             centerX - fragmentRadius, centerY + radius, centerX
                                     - fragmentRadius, centerY + 2 * fragmentRadius);
        controlShape.lineTo(centerX - fragmentRadius, centerY + fragmentRadius);

        // west control
        controlShape.lineTo(centerX - 2 * fragmentRadius, centerY
                + fragmentRadius);
        controlShape.curveTo(centerX - radius, centerY + fragmentRadius,
                             centerX - radius, centerY - fragmentRadius, centerX - 2
                                     * fragmentRadius, centerY - fragmentRadius);

        // controlShape.lineTo(centerX+fragmentRadius, centerY+fragmentRadius);
        controlShape.closePath();

        // BUTTONS FRAME

        // sensible shape
        // int deltaSensible = (int)(fragmentRadius/1.8);

        rectNorth = new Rectangle2D.Double(centerX - fragmentRadius + inset,
                                           centerY - 3 * fragmentRadius + inset, 2 * fragmentRadius - 2
                                                   * inset, 2 * fragmentRadius - 2 * inset);
        rectSouth = new Rectangle2D.Double(centerX - fragmentRadius + inset,
                                           centerY + fragmentRadius + inset, 2 * fragmentRadius - 2
                                                   * inset, 2 * fragmentRadius - 2 * inset);
        rectWest = new Rectangle2D.Double(centerX - 3 * fragmentRadius + inset,
                                          centerY - fragmentRadius + inset, 2 * fragmentRadius - 2
                                                  * inset, 2 * fragmentRadius - 2 * inset);
        rectEast = new Rectangle2D.Double(centerX + fragmentRadius + inset,
                                          centerY - fragmentRadius + inset, 2 * fragmentRadius - 2
                                                  * inset, 2 * fragmentRadius - 2 * inset);

        clearSensibleShape();
        addSensibleShape(rectNorth);
        addSensibleShape(rectSouth);
        addSensibleShape(rectWest);
        addSensibleShape(rectEast);

    }

    /**
     * override this method to create button north shape inside specified
     * bounding rectangle parameter
     * 
     * @param buttonNorthBound
     */
    abstract void solveButtonNorthGeometry(Rectangle2D buttonNorthBound);

    /**
     * override this method to create button south shape inside specified
     * bounding rectangle parameter
     * 
     * @param buttonSouthBound
     */
    abstract void solveButtonSouthGeometry(Rectangle2D buttonSouthBound);

    /**
     * override this method to create button west shape inside specified
     * bounding rectangle parameter
     * 
     * @param buttonWestBound
     */
    abstract void solveButtonWestGeometry(Rectangle2D buttonWestBound);

    /**
     * override this method to create button west shape inside specified
     * bounding rectangle parameter
     * 
     * @param buttonEastBound
     */
    abstract void solveButtonEastGeometry(Rectangle2D buttonEastBound);

    /**
     * solve geometry if {@link #solveRequest} is true, not solve geometry
     * otherwise
     * solve consist of following set operations {@link #solvePadGeometry()}
     * {@link #solveButtonNorthGeometry(Rectangle2D)} that have to be override
     * in subclass of this abstract definition {@link #solveButtonSouthGeometry(Rectangle2D)} that have to be override
     * in subclass of this abstract definition {@link #solveButtonWestGeometry(Rectangle2D)} that have to be override in
     * subclass of this abstract definition {@link #solveButtonEastGeometry(Rectangle2D)} that have to be override in
     * subclass of this abstract definition
     */
    @Override
    public final void solveGeometry(Rectangle2D bound2D) {
        if (solveRequest) {

            this.bound2D = bound2D;

            centerX = bound2D.getCenterX();
            centerY = bound2D.getCenterY();
            radius = bound2D.getWidth() / 2;
            // this.fragmentRadius = new Double(radius)/2.8;
            fragmentRadius = new Double(radius) / 3;

            solvePadGeometry();

            solveButtonNorthGeometry(rectNorth);
            solveButtonSouthGeometry(rectSouth);
            solveButtonWestGeometry(rectWest);
            solveButtonEastGeometry(rectEast);

            solveRequest = false;
        }

    }

    /**
     * @return the bound2D
     */
    public Rectangle2D getBound2D() {
        return bound2D;
    }

    /**
     * @param bound2d
     *            the bound2D to set
     */
    public void setBound2D(Rectangle2D bound2d) {
        Rectangle2D oldBound = bound2D;
        bound2D = bound2d;
        if (!oldBound.equals(bound2d)) {
            solveRequest = true;
        }
    }

    /**
     * @return the baseShape
     */
    public Ellipse2D getBaseShape() {
        return baseShape;
    }

    /**
     * @param baseShape
     *            the baseShape to set
     */
    public void setBaseShape(Ellipse2D baseShape) {
        this.baseShape = baseShape;
    }

    /**
     * @return the controlShape
     */
    public GeneralPath getControlShape() {
        return controlShape;
    }

    /**
     * @param controlShape
     *            the controlShape to set
     */
    public void setControlShape(GeneralPath controlShape) {
        this.controlShape = controlShape;
    }

    /**
     * @return the rectNorth
     */
    public Rectangle2D getRectNorth() {
        return rectNorth;
    }

    /**
     * @param rectNorth
     *            the rectNorth to set
     */
    public void setRectNorth(Rectangle2D rectNorth) {
        this.rectNorth = rectNorth;
    }

    /**
     * @return the rectSouth
     */
    public Rectangle2D getRectSouth() {
        return rectSouth;
    }

    /**
     * @param rectSouth
     *            the rectSouth to set
     */
    public void setRectSouth(Rectangle2D rectSouth) {
        this.rectSouth = rectSouth;
    }

    /**
     * @return the rectWest
     */
    public Rectangle2D getRectWest() {
        return rectWest;
    }

    /**
     * @param rectWest
     *            the rectWest to set
     */
    public void setRectWest(Rectangle2D rectWest) {
        this.rectWest = rectWest;
    }

    /**
     * @return the rectEast
     */
    public Rectangle2D getRectEast() {
        return rectEast;
    }

    /**
     * @param rectEast
     *            the rectEast to set
     */
    public void setRectEast(Rectangle2D rectEast) {
        this.rectEast = rectEast;
    }

    /**
     * @return the northButton
     */
    public Shape getNorthButton() {
        return northButton;
    }

    /**
     * @param northButton
     *            the northButton to set
     */
    public void setNorthButton(Shape northButton) {
        this.northButton = northButton;
    }

    /**
     * @return the eastButton
     */
    public Shape getEastButton() {
        return eastButton;
    }

    /**
     * @param eastButton
     *            the eastButton to set
     */
    public void setEastButton(Shape eastButton) {
        this.eastButton = eastButton;
    }

    /**
     * @return the southButton
     */
    public Shape getSouthButton() {
        return southButton;
    }

    /**
     * @param southButton
     *            the southButton to set
     */
    public void setSouthButton(Shape southButton) {
        this.southButton = southButton;
    }

    /**
     * @return the westButton
     */
    public Shape getWestButton() {
        return westButton;
    }

    /**
     * @param westButton
     *            the westButton to set
     */
    public void setWestButton(Shape westButton) {
        this.westButton = westButton;
    }

    /**
     * @return the northRollover
     */
    public boolean isNorthRollover() {
        return northRollover;
    }

    /**
     * @param northRollover
     *            the northRollover to set
     */
    public void setNorthRollover(boolean northRollover) {
        this.northRollover = northRollover;
    }

    /**
     * @return the eastRollover
     */
    public boolean isEastRollover() {
        return eastRollover;
    }

    /**
     * @param eastRollover
     *            the eastRollover to set
     */
    public void setEastRollover(boolean eastRollover) {
        this.eastRollover = eastRollover;
    }

    /**
     * @return the southRollover
     */
    public boolean isSouthRollover() {
        return southRollover;
    }

    /**
     * @param southRollover
     *            the southRollover to set
     */
    public void setSouthRollover(boolean southRollover) {
        this.southRollover = southRollover;
    }

    /**
     * @return the westRollover
     */
    public boolean isWestRollover() {
        return westRollover;
    }

    /**
     * @param westRollover
     *            the westRollover to set
     */
    public void setWestRollover(boolean westRollover) {
        this.westRollover = westRollover;
    }

    /**
     * @return the inset
     */
    public int getInset() {
        return inset;
    }

    /**
     * @param inset
     *            the inset to set
     */
    public void setInset(int inset) {
        this.inset = inset;
    }

}
