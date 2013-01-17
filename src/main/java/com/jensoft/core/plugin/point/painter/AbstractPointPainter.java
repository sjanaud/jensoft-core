/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.point.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.point.manager.PointLayoutManager;

public abstract class AbstractPointPainter {

    private PointLayoutManager layout;

    public void setLayout(PointLayoutManager layout) {
        this.layout = layout;
    }

    public PointLayoutManager getLayout() {
        return layout;
    }

    public abstract void doPaintPoint(Graphics2D g2d);
}
