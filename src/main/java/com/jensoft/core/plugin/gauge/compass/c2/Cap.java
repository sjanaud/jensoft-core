/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.compass.c2;

import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Cap {

    private String name;
    private double theta = 0;
    private Paint paint = new Color(0, 0, 0, 150);
    private Color colorTheme = Color.WHITE;
    private int alphaProjection = 20;

    private boolean lockNeedle = false;
    private boolean lockRollover = false;
    private boolean lockPressed = false;

    private Arc2D needleArc;
    private Line2D needlePath;
    private Line2D baseLine;;
    private Point2D refPoint;

    public static int MAJOR = 0;
    public static int MEDIAN = 1;
    public static int MINOR = 2;
    public static int MILI = 3;
    private int nature = MAJOR;

    public Cap() {

    }

    public Point2D getRefPoint() {
        return refPoint;
    }

    public void setRefPoint(Point2D refPoint) {
        this.refPoint = refPoint;
    }

    public int getAlphaProjection() {
        return alphaProjection;
    }

    public void setAlphaProjection(int alphaProjection) {
        this.alphaProjection = alphaProjection;
    }

    public Arc2D getNeedleArc() {
        return needleArc;
    }

    public void setNeedleArc(Arc2D needleArc) {
        this.needleArc = needleArc;
    }

    public int getNature() {
        return nature;
    }

    public void setNature(int nature) {
        this.nature = nature;
    }

    public Line2D getBaseLine() {
        return baseLine;
    }

    public void setBaseLine(Line2D baseLine) {
        this.baseLine = baseLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean contains(Point2D p2d) {
        return needlePath.contains(p2d);
    }

    public Cap(double theta) {
        super();
        this.theta = theta;
    }

    public Cap(int alphaProjection, double theta) {
        super();
        this.alphaProjection = alphaProjection;
        this.theta = theta;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Color getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(Color colorTheme) {
        this.colorTheme = colorTheme;
    }

    public Line2D getNeedlePath() {
        return needlePath;
    }

    public void setNeedlePath(Line2D needlePath) {
        this.needlePath = needlePath;
    }

    public void lockNeedle() {
        lockNeedle = true;
    }

    public void unlockNeedle() {
        lockNeedle = false;
    }

    public boolean isLockNeedle() {
        return lockNeedle;
    }

    public void lockRollover() {
        lockRollover = true;
    }

    public void unlockRollover() {
        lockRollover = false;
    }

    public boolean isLockRollover() {
        return lockRollover;
    }

    public void lockPressed() {
        lockPressed = true;
    }

    public void unlockPressed() {
        lockPressed = false;
    }

    public boolean isLockPressed() {
        return lockPressed;
    }

}
