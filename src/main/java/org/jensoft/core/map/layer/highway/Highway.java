/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.highway;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import org.jensoft.core.map.primitive.Node;
import org.jensoft.core.map.primitive.Primitive;
import org.jensoft.core.map.projection.GeoPosition;

public class Highway {

    private Primitive primitive;
    private int id;
    private GeneralPath highwayPath;
    private String nature;
    private String name = "";

    public Highway(int id, String nature) {
        this.id = id;
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
        if (obj instanceof Highway) {
            Highway h = (Highway) obj;
            return h.getId() == id;
        }
        return false;

    }

    public boolean needToReverse() {

        if (primitive.getNodes() != null && primitive.getNodes().size() >= 2) {
            GeoPosition positionStart = primitive.getNodes().get(0)
                    .getGeoPosition();
            GeoPosition positionEnd = primitive.getNodes()
                    .get(primitive.getNodes().size() - 1).getGeoPosition();
            if (positionStart.getLongitude() > positionEnd.getLongitude()) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void calculatePath() {
        GeneralPath path = new GeneralPath();

        Point2D ptInitial = primitive.getNodes().get(0).getProjection();
        path.moveTo(primitive.getNodes().get(0).getProjection().getX(),
                    ptInitial.getY());

        for (Node n : primitive.getNodes()) {
            path.lineTo(n.getProjection().getX(), n.getProjection().getY());
        }

        setHighwayPath(path);

    }

    public GeneralPath getReversePath() {

        GeneralPath path = new GeneralPath();
        Point2D ptInitial = primitive.getNodes()
                .get(primitive.getNodes().size() - 1).getProjection();
        path.moveTo(ptInitial.getX(), ptInitial.getY());

        for (int i = primitive.getNodes().size() - 1; i >= 0; i--) {
            Point2D pp = primitive.getNodes().get(i).getProjection();
            path.lineTo(pp.getX(), pp.getY());
        }

        return path;

    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeneralPath getHighwayPath() {
        return highwayPath;
    }

    public void setHighwayPath(GeneralPath highwayPath) {
        this.highwayPath = highwayPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
