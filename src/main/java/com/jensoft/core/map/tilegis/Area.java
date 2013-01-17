/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.tilegis;

import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.map.layer.highway.Highway;

public class Area {

    int startX;
    int endX;
    int startY;
    int endY;

    double latNorth;
    double latSouth;
    double longWest;
    double longEast;

    String name;

    private List<Highway> highways;

    public Area(int startX, int endX, int startY, int endY) {
        super();
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;

        highways = new ArrayList<Highway>();

    }

    public void registerHighway(Highway h) {
        highways.add(h);
    }

    public void registerHighway(List<Highway> lh) {
        highways.addAll(lh);
    }

    public List<Highway> getHighways() {
        return highways;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public double getLatNorth() {
        return latNorth;
    }

    public void setLatNorth(double latNorth) {
        this.latNorth = latNorth;
    }

    public double getLatSouth() {
        return latSouth;
    }

    public void setLatSouth(double latSouth) {
        this.latSouth = latSouth;
    }

    public double getLongWest() {
        return longWest;
    }

    public void setLongWest(double longWest) {
        this.longWest = longWest;
    }

    public double getLongEast() {
        return longEast;
    }

    public void setLongEast(double longEast) {
        this.longEast = longEast;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
