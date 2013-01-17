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
 * <code>FlowGridManager</code>
 * 
 * @author sebastien janaud
 */
public class FlowGridManager extends AbstractGridManager {

    /** grid interval */
    private double flowInterval = 0;

    /** flow start grid */
    private double flowStart = 0;

    /** flow end grid */
    private double flowEnd = 0;

    /** generated grids */
    private List<Grid> deviceGrids = new ArrayList<Grid>();

    /**
     * create flow grid manager with specified parameters
     * 
     * @param gridOrientation
     *            the grid orientation
     * @param startGrid
     *            the flow start
     * @param endGrid
     *            the flow end
     * @param gridInterval
     *            the grid interval
     */
    public FlowGridManager(GridOrientation gridOrientation, double startGrid,
            double endGrid, double gridInterval) {
        super(gridOrientation);
        flowStart = startGrid;
        flowEnd = endGrid;
        flowInterval = gridInterval;
    }

    /**
     * @return the flowInterval
     */
    public double getFlowInterval() {
        return flowInterval;
    }

    /**
     * @param flowInterval
     *            the flowInterval to set
     */
    public void setFlowInterval(double flowInterval) {
        this.flowInterval = flowInterval;
    }

    /**
     * @return the flowStart
     */
    public double getFlowStart() {
        return flowStart;
    }

    /**
     * @param flowStart
     *            the flowStart to set
     */
    public void setFlowStart(double flowStart) {
        this.flowStart = flowStart;
    }

    /**
     * @return the flowEnd
     */
    public double getFlowEnd() {
        return flowEnd;
    }

    /**
     * @param flowEnd
     *            the flowEnd to set
     */
    public void setFlowEnd(double flowEnd) {
        this.flowEnd = flowEnd;
    }

    /**
     * get flow grids
     */
    @Override
    public List<Grid> getGrids() {
        deviceGrids.clear();
        double interval = flowEnd - flowStart;
        int gridNumber = (int) (interval / flowInterval);

        if (gridNumber > 0) {
            for (int i = 0; i <= gridNumber; i++) {

                double g = flowStart + i * flowInterval;
                if (getGridOrientation() == GridOrientation.Vertical) {
                    Point2D p2dUser = new Point2D.Double(g, 0);
                    Point2D p2ddevice = getWindow2D().userToPixel(p2dUser);

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
                else if (getGridOrientation() == GridOrientation.Horizontal) {
                    Point2D p2dUser = new Point2D.Double(0, g);
                    Point2D p2ddevice = getWindow2D().userToPixel(p2dUser);

                    Grid grid = new Grid(GridOrientation.Horizontal);
                    grid.setGridDeviceValue(p2ddevice.getY());

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
        }
        return deviceGrids;
    }

}
