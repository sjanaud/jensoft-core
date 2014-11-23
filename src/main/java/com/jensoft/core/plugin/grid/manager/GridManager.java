/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.grid.manager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.List;

import com.jensoft.core.plugin.grid.Grid;
import com.jensoft.core.plugin.grid.Grid.GridOrientation;
import com.jensoft.core.projection.Projection;

/**
 * <code>GridLayoutManager</code>
 * 
 * @author sebastien janaud
 */
public interface GridManager {

    public static Color DEFAULT_BLACKCOLOR = Color.BLACK;
    public static Stroke DEFAULT_BASICSTROKE = new BasicStroke();

    /**
     * get grids
     * 
     * @return grids
     */
    public List<Grid> getGrids();

    /**
     * set projection
     * 
     * @param projection
     *            projection to set
     */
    public void setProjection(Projection projection);

    /**
     * get projection
     * 
     * @return projection
     */
    public Projection getProjection();

    /**
     * set band orientation
     * 
     * @param gridOrientation
     *            the band orientation to set
     */
    public void setGridOrientation(GridOrientation gridOrientation);

    /**
     * get grid orientation
     * 
     * @return grid orientation
     */
    public GridOrientation getGridOrientation();

    /**
     * set grid color
     * 
     * @param color
     *            the grid color to set
     */
    public void setGridColor(Color color);

    /**
     * get grid color
     * 
     * @return grid color
     */
    public Color getGridColor();

    /**
     * set grid stroke
     * 
     * @param stroke
     *            the stroke to set
     */
    public void setGridStroke(Stroke stroke);

    /**
     * get grid stroke
     * 
     * @return grid stroke
     */
    public Stroke getGridStroke();

}
