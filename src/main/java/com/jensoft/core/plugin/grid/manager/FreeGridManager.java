/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.grid.manager;

import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jensoft.core.plugin.grid.Grid;
import com.jensoft.core.plugin.grid.Grid.GridOrientation;

/**
 * <code>FreeGridManager</code>
 * 
 * @author sebastien janaud
 */
public class FreeGridManager extends AbstractGridManager {

    /** user grids */
    private List<Grid> grids = new ArrayList<Grid>();

    /** generated grids */
    List<Grid> deviceGrids = new Vector<Grid>();

    /**
     * create free grid manager
     * @param gridOrientation
     *          the grid orientation
     */
    public FreeGridManager(GridOrientation gridOrientation) {
        super(gridOrientation);
    }

    /**
     * add grid
     * 
     * @param grid
     *            the grid
     */
    public void addGrid(Grid grid) {
        grids.add(grid);
    }

    /**
     * add grid with specified parameter
     * 
     * @param grid
     *            the grid value in user coordinate system
     */
    public void addGrid(double grid) {
        Grid g = new Grid();
        g.setGridUserValue(grid);
        g.setAnnotationFraction(0.1f);
        grids.add(g);
    }

    /**
     * add grid with specified parameter
     * 
     * @param grid
     * @param gridColor
     */
    public void addGrid(double grid, Color gridColor) {
        Grid g = new Grid();
        g.setGridUserValue(grid);
        g.setAnnotationFraction(0.1f);
        g.setGridColor(gridColor);
        grids.add(g);
    }

    public void addGrid(double grid, String annotation) {
        Grid g = new Grid();
        g.setGridUserValue(grid);
        g.setAnnotationFraction(0.1f);
        g.setAnnotation(annotation);
        grids.add(g);
    }

    public void addGrid(double grid, String annotation, Color gridColor) {
        Grid g = new Grid();
        g.setGridUserValue(grid);
        g.setAnnotationFraction(0.1f);
        g.setAnnotation(annotation);
        g.setGridColor(gridColor);
        grids.add(g);
    }

    public void addGrid(double grid, String annotation, Color gridColor,
            Stroke gridStroke) {
        Grid g = new Grid();
        g.setGridUserValue(grid);
        g.setAnnotationFraction(0.1f);
        g.setAnnotation(annotation);
        g.setGridColor(gridColor);
        g.setGridStroke(gridStroke);
        grids.add(g);
    }

    public void addGrid(double grid, String annotation, Color gridColor,
            Stroke gridStroke, float fractionAnnotation) {
        Grid g = new Grid();
        g.setGridUserValue(grid);
        g.setAnnotationFraction(fractionAnnotation);
        g.setAnnotation(annotation);
        g.setGridColor(gridColor);
        g.setGridStroke(gridStroke);
        grids.add(g);
    }

    public void addGrid(double grid, String annotation, float fractionAnnotation) {
        Grid g = new Grid();
        g.setGridUserValue(grid);
        g.setAnnotationFraction(fractionAnnotation);
        g.setAnnotation(annotation);
        grids.add(g);
    }

    public void addGrid(double grid, String annotation, Color gridColor,
            float fractionAnnotation) {
        Grid g = new Grid();
        g.setGridUserValue(grid);
        g.setGridColor(gridColor);
        g.setAnnotationFraction(fractionAnnotation);
        g.setAnnotation(annotation);
        grids.add(g);
    }

    /**
     * get free grids
     */
    @Override
    public List<Grid> getGrids() {
        deviceGrids.clear();

        if (getGridOrientation() == GridOrientation.Vertical) {

            for (int i = 0; i < grids.size(); i++) {

                Grid gt = grids.get(i);
                double dx = gt.getGridUserValue();

                Point2D p2dUser = new Point2D.Double(dx, 0);
                Point2D p2ddevice = getWindow2D().userToPixel(p2dUser);

                Grid grid = new Grid(GridOrientation.Vertical);
                grid.setGridDeviceValue(p2ddevice.getX());

                if (gt.getGridColor() != null) {
                    grid.setGridColor(gt.getGridColor());
                }
                else if (getGridColor() != null) {
                    grid.setGridColor(getGridColor());
                }
                else {
                    grid.setGridColor(GridManager.DEFAULT_BLACKCOLOR);
                }

                if (gt.getGridStroke() != null) {
                    grid.setGridStroke(gt.getGridStroke());
                }
                else if (getGridStroke() != null) {
                    grid.setGridStroke(getGridStroke());
                }
                else {
                    grid.setGridStroke(GridManager.DEFAULT_BASICSTROKE);
                }

                grid.setAnnotationFraction(gt.getAnnotationFraction());

                grid.setAnnotation(gt.getAnnotation());
                deviceGrids.add(grid);

            }
        }
        else if (getGridOrientation() == GridOrientation.Horizontal) {
            for (int i = 0; i < grids.size(); i++) {

                Grid gt = grids.get(i);
                double dy = gt.getGridUserValue();
                Point2D p2dUser = new Point2D.Double(0, dy);
                Point2D p2ddevice = getWindow2D().userToPixel(p2dUser);

                Grid grid = new Grid(GridOrientation.Horizontal);
                grid.setGridDeviceValue(p2ddevice.getY());

                if (gt.getGridColor() != null) {
                    grid.setGridColor(gt.getGridColor());
                }
                else if (getGridColor() != null) {
                    grid.setGridColor(getGridColor());
                }
                else {
                    grid.setGridColor(GridManager.DEFAULT_BLACKCOLOR);
                }

                if (gt.getGridStroke() != null) {
                    grid.setGridStroke(gt.getGridStroke());
                }
                else if (getGridStroke() != null) {
                    grid.setGridStroke(getGridStroke());
                }
                else {
                    grid.setGridStroke(GridManager.DEFAULT_BASICSTROKE);
                }

                grid.setAnnotationFraction(gt.getAnnotationFraction());
                grid.setAnnotation(gt.getAnnotation());
                deviceGrids.add(grid);
            }

        }
        return deviceGrids;

    }
}
