/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.leisure;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.map.primitive.Primitive;
import com.jensoft.core.map.projection.GeoPosition;

public class Leisure {

    private int id;
    private LeisureRenderer leisureRenderer;
    private List<GeoPosition> leisureGeoLimits;
    private List<Point2D> leisurePointLimits;
    private String nature;
    private String name = "";

    private Primitive primitive;

    public Leisure(int id, String nature) {
        this.id = id;
        leisureGeoLimits = new ArrayList<GeoPosition>();
        leisurePointLimits = new ArrayList<Point2D>();
        this.nature = nature;
    }

    public Primitive getPrimitive() {
        return primitive;
    }

    public void setPrimitive(Primitive primitive) {
        this.primitive = primitive;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Leisure) {
            Leisure l = (Leisure) obj;
            return l.getId() == id;
        }
        return false;

    }

    public void addLimitPosition(GeoPosition limitPosition) {
        leisureGeoLimits.add(limitPosition);
    }

    public void addLimitPosition(Point2D limitPosition) {
        leisurePointLimits.add(limitPosition);
    }

    public List<GeoPosition> getLimitGeoPositions() {
        return leisureGeoLimits;
    }

    public List<Point2D> getLimitProjectionPositions() {
        return leisurePointLimits;
    }

    public LeisureRenderer getLeisureRenderer() {
        return leisureRenderer;
    }

    public void setLeisureRenderer(LeisureRenderer regionRenderer) {
        leisureRenderer = regionRenderer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

}
