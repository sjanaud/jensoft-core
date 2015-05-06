/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.widget.bar;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import org.jensoft.core.widget.AbstractWidgetGeometry;

/**
 * Widget Bar Geometry
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractBarGeometry extends AbstractWidgetGeometry {

    /** widget bounding frame */
    private Rectangle2D bound2D;

    /** bar outline shape */
    private RoundRectangle2D outlineShape;

    /** button 1 bounding rectangle */
    private Rectangle2D rect1;

    /** button 2 bounding rectangle */
    private Rectangle2D rect2;

    /** button 1 path */
    private Shape button1;

    /** button 2 path */
    private Shape button2;

    /** button 1 roll over flag */
    private boolean rollover1 = false;

    /** button 2 roll over flag */
    private boolean rollover2 = false;

    /** true make a solving geometry request */
    private boolean solveRequest = true;

    /** margin */
    private int margin = 4;

    /** round radius */
    private double radius;

    /** inset */
    private int inset = 3;

    /** widget orientation */
    private BarWidgetOrientation barWidgetOrientation;

    /**
     * Bar Orientation
     */
    public enum BarWidgetOrientation {
        Horizontal, Vertical;
    }

    /**
     * create empty geometry
     */
    public AbstractBarGeometry() {
    }

    /**
     * create geometry for the specified orientation
     * 
     * @param barWidgetOrientation
     */
    public AbstractBarGeometry(BarWidgetOrientation barWidgetOrientation) {
        super();
        this.barWidgetOrientation = barWidgetOrientation;
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
     * @return the margin
     */
    public int getMargin() {
        return margin;
    }

    /**
     * @param margin
     *            the margin to set
     */
    public void setMargin(int margin) {
        this.margin = margin;
        solveRequest = true;
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
        solveRequest = true;
    }

    /**
     * @return the barWidgetOrientation
     */
    public BarWidgetOrientation getBarWidgetOrientation() {
        return barWidgetOrientation;
    }

    /**
     * @param barWidgetOrientation
     *            the barWidgetOrientation to set
     */
    public void setBarWidgetOrientation(
            BarWidgetOrientation barWidgetOrientation) {
        this.barWidgetOrientation = barWidgetOrientation;
        solveRequest = true;
    }

    /**
     * solve bar geometry outline {@link #outlineShape} and buttons framing
     * rectangles, {@link #rect1} and {@link #rect2}
     */
    private final void solveBarGeometry() {

        if (barWidgetOrientation == BarWidgetOrientation.Horizontal) {
            outlineShape = new RoundRectangle2D.Double(bound2D.getX(),
                                                       bound2D.getY(), bound2D.getWidth(), bound2D.getHeight(),
                                                       bound2D.getHeight(), bound2D.getHeight());
            rect1 = new Rectangle2D.Double(bound2D.getX() + margin + inset,
                                           bound2D.getY() + inset, radius - 2 * inset, radius - 2
                                                   * inset);
            rect2 = new Rectangle2D.Double(bound2D.getX() + inset
                    + bound2D.getWidth() - margin - radius, bound2D.getY()
                    + inset, radius - 2 * inset, radius - 2 * inset);
        }
        else {
            outlineShape = new RoundRectangle2D.Double(bound2D.getX(),
                                                       bound2D.getY(), bound2D.getWidth(), bound2D.getHeight(),
                                                       bound2D.getWidth(), bound2D.getWidth());
            rect1 = new Rectangle2D.Double(bound2D.getX() + inset,
                                           bound2D.getY() + margin + inset, radius - 2 * inset, radius
                                                   - 2 * inset);
            rect2 = new Rectangle2D.Double(bound2D.getX() + inset,
                                           bound2D.getY() + bound2D.getHeight() - radius - margin
                                                   + inset, radius - 2 * inset, radius - 2 * inset);
        }
        clearSensibleShape();
        addSensibleShape(rect1);
        addSensibleShape(rect2);
    }

    /**
     * override this method to create button 1 shape inside specified bounding
     * rectangle parameter consider two orientation cases, horizontal and
     * vertical
     * 
     * @param button1Bound
     */
    abstract void solveButton1Geometry(Rectangle2D button1Bound);

    /**
     * override this method to create button 2 shape inside specified bounding
     * rectangle parameter
     * 
     * @param button2Bound
     */
    abstract void solveButton2Geometry(Rectangle2D button2Bound);

    /**
     * solve geometry if {@link #solveRequest} is true, not solve geometry
     * otherwise
     * solve consist of following set operations {@link #solveBarGeometry()} {@link #solveButton1Geometry(Rectangle2D)}
     * that have to be override in
     * subclass of this abstract definition {@link #solveButton2Geometry(Rectangle2D)} that have to be override in
     * subclass of this abstract definition
     */
    @Override
    public final void solveGeometry(Rectangle2D bound2D) {

        if (solveRequest) {

            this.bound2D = bound2D;

            if (getBarWidgetOrientation() == BarWidgetOrientation.Horizontal) {
                radius = bound2D.getHeight();
            }
            else if (getBarWidgetOrientation() == BarWidgetOrientation.Vertical) {
                radius = bound2D.getWidth();
            }

            if (barWidgetOrientation == null) {
                return;
            }

            solveBarGeometry();

            solveButton1Geometry(rect1);
            solveButton2Geometry(rect2);

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
     * @return the outlineShape
     */
    public RoundRectangle2D getOutlineShape() {
        return outlineShape;
    }

    /**
     * @param outlineShape
     *            the outlineShape to set
     */
    public void setOutlineShape(RoundRectangle2D outlineShape) {
        this.outlineShape = outlineShape;
    }

    /**
     * @return the rect1
     */
    public Rectangle2D getRect1() {
        return rect1;
    }

    /**
     * @param rect1
     *            the rect1 to set
     */
    public void setRect1(Rectangle2D rect1) {
        this.rect1 = rect1;
    }

    /**
     * @return the rect2
     */
    public Rectangle2D getRect2() {
        return rect2;
    }

    /**
     * @param rect2
     *            the rect2 to set
     */
    public void setRect2(Rectangle2D rect2) {
        this.rect2 = rect2;
    }

    /**
     * @return the button1
     */
    public Shape getButton1() {
        return button1;
    }

    /**
     * @param button1
     *            the button1 to set
     */
    public void setButton1(Shape button1) {
        this.button1 = button1;
    }

    /**
     * @return the button2
     */
    public Shape getButton2() {
        return button2;
    }

    /**
     * @param button2
     *            the button2 to set
     */
    public void setButton2(Shape button2) {
        this.button2 = button2;
    }

    /**
     * @return the rollover1
     */
    public boolean isRollover1() {
        return rollover1;
    }

    /**
     * @param rollover1
     *            the rollover1 to set
     */
    public void setRollover1(boolean rollover1) {
        this.rollover1 = rollover1;
    }

    /**
     * @return the rollover2
     */
    public boolean isRollover2() {
        return rollover2;
    }

    /**
     * @param rollover2
     *            the rollover2 to set
     */
    public void setRollover2(boolean rollover2) {
        this.rollover2 = rollover2;
    }
}
