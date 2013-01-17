/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.waterway;

import com.jensoft.core.map.layer.manmade.ManMade;
import com.jensoft.core.map.primitive.Primitive;

public class Waterway {

    // private List<GeoPosition> regionGeoLimits;
    // private List<Point2D> regionPointLimits;
    private String nature;
    private String name = "";
    private int id;

    private Primitive primitive;

    public Waterway(int id, String nature) {
        this.id = id;
        // this.regionGeoLimits = new ArrayList<GeoPosition>();
        // this.regionPointLimits = new ArrayList<Point2D>();
        this.nature = nature;
    }

    public Primitive getPrimitive() {
        return primitive;
    }

    public void setPrimitive(Primitive primitive) {
        this.primitive = primitive;
    }

    public String getNature() {
        return nature;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ManMade) {
            Waterway mm = (Waterway) obj;
            return mm.getId() == id;

        }
        return false;

    }

    // public void addLimitPosition(GeoPosition limitPosition){
    // regionGeoLimits.add(limitPosition);
    // }
    // public void addLimitPosition(Point2D limitPosition){
    // regionPointLimits.add(limitPosition);
    // }
    //
    // public List<GeoPosition> getRegionLimits() {
    // return regionGeoLimits;
    // }
    // public List<Point2D> getProjectionLimits() {
    // return regionPointLimits;
    // }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
