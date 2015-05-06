/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.landuse;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.map.primitive.Primitive;
import org.jensoft.core.map.projection.GeoPosition;

public class Landuse {

    private List<GeoPosition> regionGeoLimits;
    private List<Point2D> regionProjectionLimits;
    private Color regionOutlineColor = Color.RED;
    private Color regionColor = Color.RED;
    private float alphaTransparence = 0.5f;
    private String nature;
    private String name = "";
    private int id;

    private Primitive primitive;

    public Landuse(int id, String nature) {
        this.id = id;
        this.nature = nature;
        regionGeoLimits = new ArrayList<GeoPosition>();
        regionProjectionLimits = new ArrayList<Point2D>();
    }

    public Primitive getPrimitive() {
        return primitive;
    }

    public void setPrimitive(Primitive primitive) {
        this.primitive = primitive;
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

    public void addLimitPosition(GeoPosition limitPosition) {
        regionGeoLimits.add(limitPosition);
    }

    public void addLimitPosition(Point2D limitPosition) {
        regionProjectionLimits.add(limitPosition);
    }

    public List<GeoPosition> getLimitGeoPositions() {
        return regionGeoLimits;
    }

    public List<Point2D> getProjectionPositions() {
        return regionProjectionLimits;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Landuse) {
            Landuse l = (Landuse) obj;
            return l.getId() == id;
        }
        return false;

    }

    public Color getRegionOutlineColor() {
        return regionOutlineColor;
    }

    public void setRegionOutlineColor(Color regionOutlineColor) {
        this.regionOutlineColor = regionOutlineColor;
    }

    public Color getRegionColor() {
        return regionColor;
    }

    public void setRegionColor(Color regionColor) {
        this.regionColor = regionColor;
    }

    public float getAlphaTransparence() {
        return alphaTransparence;
    }

    public void setAlphaTransparence(float alphaTransparence) {
        this.alphaTransparence = alphaTransparence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
