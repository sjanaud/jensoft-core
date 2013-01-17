/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.grid.manager;

import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.grid.Grid;
import com.jensoft.core.plugin.grid.Grid.GridOrientation;
import com.jensoft.core.window.Window2D;

/**
 * <code>CompoundGridManager</code>
 * 
 * @author sebastien janaud
 */
public class CompoundGridManager extends AbstractGridManager {

    /** managers */
    private GridManager[] managers;

    /** generated grids from all manager */
    private List<Grid> compoundgrids;

    /**
     * create compound manager with the specified manager array
     * 
     * @param gridOrientation
     *            the grids orientation
     * @param managers
     *            the managers
     */
    public CompoundGridManager(GridOrientation gridOrientation, GridManager... managers) {
        super(gridOrientation);
        this.managers = managers;
        compoundgrids = new ArrayList<Grid>();
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.grid.manager.GridManager#getGrids()
     */
    @Override
    public List<Grid> getGrids() {
        compoundgrids.clear();
        for (int i = 0; i < managers.length; i++) {
            managers[i].setGridOrientation(super.getGridOrientation());
        }
        for (int i = 0; i < managers.length; i++) {
            compoundgrids.addAll(managers[i].getGrids());
        }
        return compoundgrids;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.grid.manager.AbstractGridManager#setWindow2D(com.jensoft.sw2d.core.window.Window2D)
     */
    @Override
    public void setWindow2D(Window2D w2d) {
        super.setWindow2D(w2d);
        for (int i = 0; i < managers.length; i++) {
            managers[i].setWindow2D(w2d);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.grid.manager.AbstractGridManager#setGridOrientation(com.jensoft.sw2d.core.plugin
     * .grid.Grid.GridOrientation)
     */
    @Override
    public void setGridOrientation(GridOrientation gridOrientation) {
        super.setGridOrientation(gridOrientation);
        for (int i = 0; i < managers.length; i++) {
            managers[i].setGridOrientation(gridOrientation);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.grid.manager.AbstractGridManager#setGridColor(java.awt.Color)
     */
    @Override
    public void setGridColor(Color c) {
        super.setGridColor(c);
        for (int i = 0; i < managers.length; i++) {
            managers[i].setGridColor(c);
        }
    }

    @Override
    public void setGridStroke(Stroke s) {
        super.setGridStroke(s);
        for (int i = 0; i < managers.length; i++) {
            managers[i].setGridStroke(s);
        }
    }

}
