/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.point.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.point.manager.PointLayoutManager;

/**
 * <code>AbstractPointPainter</code>
 * 
 * @since 1.0
 * @author sebastien janaud
 *
 */
public abstract class AbstractPointPainter {

	/**point layout manager*/
    private PointLayoutManager manager;

    
    /**
     * set layout manager
     * @param layout
     */
    public void setLayout(PointLayoutManager layout) {
        this.manager = layout;
    }

    /**
     * get layout manager
     * @return
     */
    public PointLayoutManager getLayout() {
        return manager;
    }

    /**
     * paint point layout
     * @param g2d
     */
    public abstract void doPaintPoint(Graphics2D g2d);
}
