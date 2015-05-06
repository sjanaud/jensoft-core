/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.primitive;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Primitive {

    private Way way;
    private List<Node> nodes;

    public Primitive(Way way) {
        this.way = way;
        nodes = way.getNodes();
    }

    public Way getWay() {
        return way;
    }

    public void setWay(Way way) {
        this.way = way;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    private List<Point2D> projection;

    public List<Point2D> getProjection() {
        if (projection == null) {
            projection = new ArrayList<Point2D>();
            for (Node n : getNodes()) {
                projection.add(n.getProjection());
            }
        }
        return projection;

    }

}
