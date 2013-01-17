/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.natural;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.map.primitive.Node;

public class Coastline {

    private List<Natural> segments;

    public Coastline() {
        segments = new ArrayList<Natural>();
    }

    public Natural getFirstSegment() {
        return segments.get(0);
    }

    public Natural getLastSegment() {
        return segments.get(segments.size() - 1);
    }

    public Natural getSegment(int index) {
        return segments.get(index);
    }

    public void addSegment(Natural segment) {
        segments.add(segment);
    }

    public int countSegment() {
        return segments.size();
    }

    public List<Natural> getSegments() {
        return segments;
    }

    public LineString getGeometry() {
        LineString lineString = null;

        List<Point2D> linePoints = new ArrayList<Point2D>();
        for (Natural natural : segments) {
            for (Node n : natural.getPrimitive().getNodes()) {
                linePoints.add(n.getProjection());
            }
        }
        int len = linePoints.size();
        Coordinate[] cs = new Coordinate[len];
        for (int i = 0; i < cs.length; i++) {
            cs[i] = new Coordinate(linePoints.get(i).getX(), linePoints.get(i)
                    .getY());
        }

        // GeometryFactory factory = new GeometryFactory();
        // lineString = factory.createLineString(cs);

        // System.out.println("JTS LINESTRING :"+lineString.toText());

        return lineString;
    }

}
