/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.primitive;

import java.awt.geom.Point2D;
import java.util.Vector;

import com.jensoft.core.map.projection.GeoPosition;

public class Node {

    private int id;
    private String timestamp;
    private String user;
    private String lat;
    private String lon;

    private GeoPosition geoPosition;
    private Point2D projection;

    private Vector<Tag> tags = new Vector<Tag>();

    
    public Node() {
        
    }
    public Node(int id) {
        this.id = id;
    }

    public boolean equals(Node n) {
        if (n == null) {
            return false;
        }
        if (n.getId() == id) {
            return true;
        }
        if (n.getGeoPosition().equals(getGeoPosition())) {
            return true;
        }
        return false;
    }

    public GeoPosition getGeoPosition() {
        if (geoPosition == null) {
            geoPosition = new GeoPosition(getLatitudeAsDouble(),
                                          getLongitudeAsDouble());
        }
        return geoPosition;
    }

    public void setGeoPosition(GeoPosition geoPosition) {
        this.geoPosition = geoPosition;
    }

    public Point2D getProjection() {
        return projection;
    }

    public void setProjection(Point2D projection) {
        this.projection = projection;
    }

    public void addTag(String key, String value) {
        tags.add(new Tag(key, value));
    }

    public void addTag(Tag t) {
        tags.add(t);
    }

    public Tag getTag(int index) {
        return tags.get(index);
    }

    public Tag getTag(String key) {
        for (Tag t : tags) {
            if (t.getKey().equals(key)) {
                return t;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLat() {
        return lat;
    }

    public double getLatitudeAsDouble() {
        return Double.parseDouble(lat);
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public double getLongitudeAsDouble() {
        return Double.parseDouble(lon);
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public Vector<Tag> getTags() {
        return tags;
    }

    public void setTags(Vector<Tag> tags) {
        this.tags = tags;
    }

}
