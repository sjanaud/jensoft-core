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
 * <code>MultiplierGridManager</code>
 * 
 * @author sebastien janaud
 */
public class MultiplierGridManager extends AbstractGridManager {

    /** grid interval */
    private double multiplier = 0;

    /** solve algorithm reference during grid generation */
    private double ref = 0;

    /** generated grids */
    private List<Grid> grids = new ArrayList<Grid>();

    /**
     * create DynamicGridManager with specified parameter
     * 
     * @param gridOrientation
     *            the grid orientation
     * @param ref
     *            the grid reference
     * @param multiplier
     *            the grid interval
     */
    public MultiplierGridManager(GridOrientation gridOrientation, double ref, double multiplier) {
        super(gridOrientation);
        this.ref = ref;
        this.multiplier = multiplier;
    }

    /**
     * @return the multiplier
     */
    public double getMultiplier() {
        return multiplier;
    }

    /**
     * @param multiplier
     *            the multiplier to set
     */
    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * @return the gridRef
     */
    public double getRef() {
        return ref;
    }

    /**
     * @param ref
     *            the ref to set
     */
    public void setRef(double ref) {
        this.ref = ref;
    }

    /**
     * add a grid into {@link #grids} if the grid has not been registered yet
     * 
     * @param grid
     *            the grid to add
     */
    private void addGrid(Grid grid) {
        boolean flag = false;
        for (Grid g : grids) {
            if (grid.equals(g)) {
                flag = true;
            }
        }
        if (!flag) {
            grids.add(grid);
        }
    }

    /**
     * get grids
     */
    @Override
    public List<Grid> getGrids() {

        grids.clear();

        if (getGridOrientation() == GridOrientation.Vertical) {

            int gridNumber = (int) (getWindow2D().getUserWidth() / multiplier);
            if (gridNumber < 0 || gridNumber > getGridMaxNumber() + 4) {
                grids.clear();
                return grids;
            }

            int count1 = 0;
            boolean flag1 = true;
            while (flag1) {
                // visible grids to max x
                if (ref + count1 * multiplier > getWindow2D().getMinX()) {
                    Point2D p2dUser = new Point2D.Double(ref + count1
                            * multiplier, 0);
                    Point2D p2dDevice = getWindow2D().userToPixel(p2dUser);

                    Grid grid = new Grid(GridOrientation.Vertical);
                    grid.setGridDeviceValue(p2dDevice.getX());

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

                    addGrid(grid);

                }

                if (ref + count1 * multiplier > getWindow2D().getMaxX()) {
                    flag1 = false;
                }

                count1++;
            }

            int count2 = 0;
            boolean flag2 = true;
            while (flag2) {
                // visible grids to min x
                if (ref - count2 * multiplier < getWindow2D().getMaxX()) {

                    Point2D p2dUser = new Point2D.Double(ref - count2
                            * multiplier, 0);
                    Point2D p2dDevice = getWindow2D().userToPixel(p2dUser);

                    Grid grid = new Grid(GridOrientation.Vertical);
                    grid.setGridDeviceValue(p2dDevice.getX());

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

                    addGrid(grid);

                }

                if (ref - count2 * multiplier < getWindow2D().getMinX()) {
                    flag2 = false;
                }

                count2++;

            }
        }
        if (getGridOrientation() == GridOrientation.Horizontal) {

            int gridNumber = (int) (getWindow2D().getUserHeight() / multiplier);
            if (gridNumber < 0 || gridNumber > getGridMaxNumber() + 4) {
                grids.clear();
                return grids;
            }

            int count1 = 0;
            boolean flag1 = true;
            while (flag1) {
                // visible grids to max y
                if (ref + count1 * multiplier > getWindow2D().getMinY()) {

                    Point2D p2dUser = new Point2D.Double(0, ref + count1
                            * multiplier);
                    Point2D p2dDevice = getWindow2D().userToPixel(p2dUser);

                    Grid grid = new Grid(GridOrientation.Horizontal);
                    grid.setGridDeviceValue(p2dDevice.getY());

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

                    addGrid(grid);

                }

                if (ref + count1 * multiplier > getWindow2D().getMaxY()) {
                    flag1 = false;
                }
                count1++;
            }

            int count2 = 0;
            boolean flag2 = true;
            while (flag2) {
                // visible grids to min y
                if (ref - count2 * multiplier < getWindow2D().getMaxY()) {
                    Point2D p2dUser = new Point2D.Double(0, ref - count2
                            * multiplier);
                    Point2D p2dDevice = getWindow2D().userToPixel(p2dUser);

                    Grid grid = new Grid(GridOrientation.Horizontal);
                    grid.setGridDeviceValue(p2dDevice.getY());

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

                    addGrid(grid);

                }

                if (ref - count2 * multiplier < getWindow2D().getMinY()) {
                    flag2 = false;
                }

                count2++;

            }
        }

        if (grids.size() > getGridMaxNumber()) {
            grids.clear();
        }
        return grids;
    }

}
