/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.grid.manager;

import java.awt.Color;
import java.awt.Stroke;

import com.jensoft.core.plugin.grid.Grid.GridOrientation;
import com.jensoft.core.projection.Projection;

/**
 * <code>AbstractGridLayoutManager</code> defines the abstract grid manager.
 * 
 * @author sebastien janaud
 */
public abstract class AbstractGridManager implements GridManager {

    /** grid orientation */
    private GridOrientation gridOrientation;

    /** window */
    private Projection window2D;

    /** grid color */
    private Color color;

    /** grid stroke */
    private Stroke stroke;

    /** max iteration for solve grids */
    private int iterationMax = 10000;

    /** max grids number */
    private int gridMaxNumber = 60;

    /**
     * create empty AbstractGridLayoutManager
     */
    public AbstractGridManager(GridOrientation gridOrientation) {
        this.gridOrientation = gridOrientation;
        stroke = GridManager.DEFAULT_BASICSTROKE;
        color = GridManager.DEFAULT_BLACKCOLOR;
    }

    @Override
    public void setWindow2D(Projection w2d) {
        window2D = w2d;
    }

    @Override
    public Projection getWindow2D() {
        return window2D;
    }

    /**
     * @return the iterationMax
     */
    public int getIterationMax() {
        return iterationMax;
    }

    /**
     * @param iterationMax
     *            the iterationMax to set
     */
    public void setIterationMax(int iterationMax) {
        this.iterationMax = iterationMax;
    }

    /**
     * @return the gridMaxNumber
     */
    public int getGridMaxNumber() {
        return gridMaxNumber;
    }

    /**
     * @param gridMaxNumber
     *            the gridMaxNumber to set
     */
    public void setGridMaxNumber(int gridMaxNumber) {
        this.gridMaxNumber = gridMaxNumber;
    }

    @Override
    public void setGridOrientation(GridOrientation gridOrientation) {
        this.gridOrientation = gridOrientation;
    }

    @Override
    public GridOrientation getGridOrientation() {
        return gridOrientation;
    }

    @Override
    public void setGridColor(Color c) {
        color = c;
    }

    @Override
    public Color getGridColor() {
        return color;
    }

    @Override
    public void setGridStroke(Stroke s) {
        stroke = s;
    }

    @Override
    public Stroke getGridStroke() {
        return stroke;
    }

}
