/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.manmade;

import org.jensoft.core.map.primitive.Primitive;

public class ManMade {

    // private List<GeoPosition> regionGeoLimits;
    // private List<Point2D> regionPointLimits;

    private int id;
    private String nature;
    private String name = "";

    private Primitive primitive;

    public ManMade(int id, String nature) {
        this.id = id;
        this.nature = nature;
        // this.regionGeoLimits = new ArrayList<GeoPosition>();
        // this.regionPointLimits = new ArrayList<Point2D>();
    }

    public Primitive getPrimitive() {
        return primitive;
    }

    public void setPrimitive(Primitive primitive) {
        this.primitive = primitive;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ManMade) {
            ManMade l = (ManMade) obj;
            return l.getId() == id;
        }
        return false;

    }

    //
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

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
