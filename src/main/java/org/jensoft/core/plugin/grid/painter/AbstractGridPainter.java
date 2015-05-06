/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.grid.painter;

import java.awt.Graphics2D;
import java.util.List;

import org.jensoft.core.plugin.grid.Grid;
import org.jensoft.core.plugin.grid.manager.GridManager;

/**
 * AbstractGridPainter defines the abstract base method to paint the Grids.
 */
public abstract class AbstractGridPainter {

    /** the grid layout */
    private GridManager layoutManager;

    /**
     * set the grid layout for sub class painter
     * 
     * @param layout
     */
    public void setLayoutManager(GridManager layout) {
        layoutManager = layout;
    }

    /**
     * get the grid layout for sub class painter
     * 
     * @return GridLayoutManager
     */
    public GridManager getLayoutManager() {
        return layoutManager;
    }

    /**
     * paint grid layout operation
     * 
     * @param g2d
     * @param grids
     *            grids to paint
     */
    public abstract void paintGrids(Graphics2D g2d, List<Grid> grids);
}
