/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.point.manager;

import java.awt.Color;
import java.util.List;

import com.jensoft.core.plugin.point.Point;
import com.jensoft.core.projection.Projection;

/**
 * PointLayoutManager defines the interface for classes that know how to lay out
 * points window.
 */
public interface PointLayoutManager {

    public List<Point> getPoints();

    public void setWindow2D(Projection w2d);

    public Projection getWindow2D();

    public static int POINT_X = 0;
    public static int POINT_Y = 1;
    public static int AREA = 2;

    public void setType(int type);

    public int getType();

    public void setPointColor(Color p);

    public Color getPointColor();

}
