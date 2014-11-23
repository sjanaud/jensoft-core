/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.point;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.point.manager.PointLayoutManager;
import com.jensoft.core.plugin.point.painter.AbstractPointPainter;
import com.jensoft.core.plugin.point.painter.PointPainter;
import com.jensoft.core.view.View;

/**
 * <code>PointPlugin</code> defines how to lay out the projection point
 */
public class PointPlugin extends AbstractPointPlugin {

    /** points layout manager */
    private PointLayoutManager manager;

    /** point painter */
    private AbstractPointPainter painter;

    public PointPlugin(PointLayoutManager manager) {
        this.manager = manager;
        painter = new PointPainter();
    }

    /**
     * get the point painter
     * 
     * @return the painter
     */
    public AbstractPointPainter getPainter() {
        return painter;
    }

    /**
     * set the specified point painter to this layout
     * 
     * @param painter
     */
    public void setPainter(AbstractPointPainter painter) {
        this.painter = painter;
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.point.AbstractPointPlugin#doPaintPoints(com.jensoft.core.view.View, java.awt.Graphics2D)
     */
    @Override
    public void doPaintPoints(View v2d, Graphics2D g2d) {
        manager.setProjection(getProjection());
        painter.setLayout(manager);
        painter.doPaintPoint(g2d);
    }

}
