/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.railway;

import org.jensoft.core.map.primitive.Primitive;

public class Railway {

    private Primitive primitive;
    // private List<GeoPosition> regionGeoLimits;
    // private List<Point2D> regionPointLimits;

    private int id;
    private String nature;
    private String name = "";

    public Railway(int id, String nature) {
        this.id = id;
        this.nature = nature;
        // regionGeoLimits = new ArrayList<GeoPosition>();
        // regionPointLimits = new ArrayList<Point2D>();
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

    public String getNature() {
        return nature;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Railway) {
            Railway railway = (Railway) obj;
            return railway.getId() == id;
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
    //
    //

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
