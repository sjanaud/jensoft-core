/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.bubble;

import java.awt.Color;
import java.awt.Shape;

import org.jensoft.core.plugin.bubble.painter.BubbleDraw;
import org.jensoft.core.plugin.bubble.painter.BubbleEffect;
import org.jensoft.core.plugin.bubble.painter.BubbleFill;

/**
 * Bubble <br>
 * <center><img src="doc-files/bubble1.png"></center> <br>
 * 
 * @see BubblePlugin
 * @see BubbleDraw
 * @see BubbleFill
 * @see BubbleEffect
 * @author Sebastien Janaud
 */
public class Bubble {

    /** center x coordinate */
    private double x;

    /** center y coordinate */
    private double y;

    /** radius */
    private double radius;

    /** theme color */
    private Color themeColor;

    /** bubble draw */
    private BubbleDraw bubbleDraw;

    /** bubble fill */
    private BubbleFill bubbleFill;

    /** bubble effect */
    private BubbleEffect bubbleEffect;

    /** bubble shape */
    private Shape bubbleShape;

    /** bubble host plugin */
    private BubblePlugin host;

    /**
     * create bubble with specified parameters
     * 
     * @param x
     *            the center x coordinate to set
     * @param y
     *            the center y coordinate to set
     * @param radius
     *            the bubble radius to set
     * @param themeColor
     *            the bubble theme color to set
     */
    public Bubble(double x, double y, double radius, Color themeColor) {
        super();
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.themeColor = themeColor;
    }

    /**
     * get bubble shape
     * 
     * @return the bubble shape
     */
    public Shape getBubbleShape() {
        return bubbleShape;
    }

    /**
     * set bubble shape
     * 
     * @param bubbleShape
     *            the shape to set
     */
    public void setBubbleShape(Shape bubbleShape) {
        this.bubbleShape = bubbleShape;
    }

    /**
     * get host plugin
     * 
     * @return host plugin
     */
    public BubblePlugin getHost() {
        return host;
    }

    /**
     * set host plugin
     * 
     * @param host
     *            the host plugin to set
     */
    public void setHost(BubblePlugin host) {
        this.host = host;
    }

    /**
     * get center x coordinate
     * 
     * @return center x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * set center x coordinate
     * 
     * @param x
     *            the center x coordinate to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * get center y coordinate
     * 
     * @return center y
     */
    public double getY() {
        return y;
    }

    /**
     * set center y coordinate
     * 
     * @param y
     *            the y coordinate to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * get bubble radius
     * 
     * @return the bubble radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * set radius
     * 
     * @param radius
     *            radius to set
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * get theme color
     * 
     * @return theme color
     */
    public Color getThemeColor() {
        return themeColor;
    }

    /**
     * set theme color
     * 
     * @param themeColor
     *            theme color to set
     */
    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
    }

    /**
     * get bubble draw
     * 
     * @return draw
     */
    public BubbleDraw getBubbleDraw() {
        return bubbleDraw;
    }

    /**
     * set bubble draw
     * 
     * @param bubbleDraw
     *            the draw to set
     */
    public void setBubbleDraw(BubbleDraw bubbleDraw) {
        this.bubbleDraw = bubbleDraw;
    }

    /**
     * get bubble fill
     * 
     * @return fill
     */
    public BubbleFill getBubbleFill() {
        return bubbleFill;
    }

    /**
     * set bubble fill
     * 
     * @param bubbleFill
     *            the fill to set
     */
    public void setBubbleFill(BubbleFill bubbleFill) {
        this.bubbleFill = bubbleFill;
    }

    /**
     * get bubble effect
     * 
     * @return effect
     */
    public BubbleEffect getBubbleEffect() {
        return bubbleEffect;
    }

    /**
     * set bubble effect
     * 
     * @param bubbleEffect
     *            the bubble effect to set
     */
    public void setBubbleEffect(BubbleEffect bubbleEffect) {
        this.bubbleEffect = bubbleEffect;
    }

}
