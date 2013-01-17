/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.point.manager;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import com.jensoft.core.plugin.point.Point;
import com.jensoft.core.window.Window2D;

public abstract class AbstractPointManager implements PointLayoutManager {

    private Window2D window2D;
    private int type;
    private Color color;

    @Override
    public List<Point> getPoints() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public void setWindow2D(Window2D w2d) {
        window2D = w2d;
    }

    @Override
    public Window2D getWindow2D() {
        return window2D;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setPointColor(Color p) {
        color = color;
    }

    @Override
    public Color getPointColor() {
        return color;
    }

}
