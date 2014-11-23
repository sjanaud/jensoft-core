/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.grid.manager;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.grid.Grid;
import com.jensoft.core.plugin.grid.Grid.GridOrientation;

/**
 * <code>StaticGridManager</code>
 * 
 * @author sebastien janaud
 */
public class StaticGridManager extends AbstractGridManager {

    /** static division */
    private int gridCount;

    public StaticGridManager(GridOrientation gridOrientation, int gridCount) {
        super(gridOrientation);
        this.gridCount = gridCount;
    }

    /**
     * @return the gridCount
     */
    public int getGridCount() {
        return gridCount;
    }

    /**
     * @param gridCount
     *            the gridCount to set
     */
    public void setGridCount(int gridCount) {
        this.gridCount = gridCount;
    }

    /**
     * get static grids
     */
    @Override
    public List<Grid> getGrids() {
        List<Grid> deviceGrids = new ArrayList<Grid>();
        if (getGridOrientation() == GridOrientation.Vertical) {

            double userSpaceWidth = getProjection().getUserWidth();
            double deltaGridUser = userSpaceWidth / (gridCount + 1);
            for (int i = 1; i <= gridCount; i++) {

                double dx = getProjection().getMinX() + i * deltaGridUser;
                Point2D p2dUser = new Point2D.Double(dx, 0);
                Point2D p2ddevice = getProjection().userToPixel(p2dUser);

                Grid grid = new Grid(GridOrientation.Vertical);
                grid.setGridDeviceValue(p2ddevice.getX());

                if (getGridColor() != null) {
                    grid.setGridColor(getGridColor());
                }
                else {
                    grid.setGridColor(GridManager.DEFAULT_BLACKCOLOR);
                }

                if (getGridStroke() != null) {
                    grid.setGridStroke(getGridStroke());
                }
                else {
                    grid.setGridStroke(GridManager.DEFAULT_BASICSTROKE);
                }

                deviceGrids.add(grid);

            }
        }
        else if (getGridOrientation() == GridOrientation.Horizontal) {

            double userSpaceWidth = getProjection().getUserHeight();
            double deltaGridUser = userSpaceWidth / (gridCount + 1);
            for (int i = 1; i <= gridCount; i++) {
                double dy = getProjection().getMinY() + i * deltaGridUser;
                Point2D p2dUser = new Point2D.Double(0, dy);
                Point2D p2ddevice = getProjection().userToPixel(p2dUser);

                Grid grid = new Grid(GridOrientation.Horizontal);
                grid.setGridDeviceValue(p2ddevice.getX());

                if (getGridColor() != null) {
                    grid.setGridColor(getGridColor());
                }
                else {
                    grid.setGridColor(GridManager.DEFAULT_BLACKCOLOR);
                }

                if (getGridStroke() != null) {
                    grid.setGridStroke(getGridStroke());
                }
                else {
                    grid.setGridStroke(GridManager.DEFAULT_BASICSTROKE);
                }
                deviceGrids.add(grid);

            }
        }

        return deviceGrids;
    }

}
