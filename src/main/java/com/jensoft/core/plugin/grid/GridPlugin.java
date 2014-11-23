/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.grid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;

import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.grid.Grid.GridOrientation;
import com.jensoft.core.plugin.grid.manager.AbstractGridManager;
import com.jensoft.core.plugin.grid.manager.CompoundGridManager;
import com.jensoft.core.plugin.grid.manager.FlowGridManager;
import com.jensoft.core.plugin.grid.manager.FreeGridManager;
import com.jensoft.core.plugin.grid.manager.GridManager;
import com.jensoft.core.plugin.grid.manager.ModeledGridManager;
import com.jensoft.core.plugin.grid.manager.ModeledGridManager.GridModel;
import com.jensoft.core.plugin.grid.manager.ModeledGridManager.GridModelCollections;
import com.jensoft.core.plugin.grid.manager.MultiplierGridManager;
import com.jensoft.core.plugin.grid.manager.StaticGridManager;
import com.jensoft.core.plugin.grid.painter.AbstractGridPainter;
import com.jensoft.core.plugin.grid.painter.GridDefaultPainter;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>GridPlugin</code> takes the responsibility to paint grids.
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public abstract class GridPlugin<M extends AbstractGridManager> extends AbstractPlugin {

    /** grid manager */
    private M gridManager;

    /** grid painter */
    private AbstractGridPainter gridPainter = new GridDefaultPainter();

    /**
     * create grid plugin
     */
    public GridPlugin() {
        setName(GridPlugin.class.getCanonicalName());
        setTextAntialising(TextAntialiasing.On);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    public final void paintPlugin(View view, Graphics2D g2d,
            ViewPart viewPart) {
        if (viewPart != ViewPart.Device) {
            return;
        }
        paintGrids(view, g2d);
    }

    /**
     * create grid plug-in with specified grid manager
     * 
     * @param manager
     *            the manager for solving grid
     */
    public GridPlugin(M manager) {
        setName(GridPlugin.class.getCanonicalName());
        gridManager = manager;
        gridPainter = new GridDefaultPainter();
        setTextAntialising(TextAntialiasing.On);
    }

    /**
     * @return the gridPainter
     */
    public AbstractGridPainter getGridPainter() {
        return gridPainter;
    }

    /**
     * paint grids
     * 
     * @param v2d
     * @param g2d
     */
    protected void paintGrids(View v2d, Graphics2D g2d) {
        if (gridManager == null) {
            return;
        }
        gridManager.setProjection(getProjection());
        gridPainter.setLayoutManager(gridManager);
        gridPainter.paintGrids(g2d, gridManager.getGrids());
    }

    /**
     * @return the gridLayoutManager
     */
    public M getGridManager() {
        return gridManager;
    }

    /**
     * @param gridManager
     *            the gridManager to set
     */
    public void setGridManager(M gridManager) {
        this.gridManager = gridManager;
    }

    /**
     * set band orientation
     * 
     * @param gridOrientation
     *            the band orientation to set
     */
    public void setGridOrientation(GridOrientation gridOrientation) {
        getGridManager().setGridOrientation(gridOrientation);
    }

    /**
     * get grid orientation
     * 
     * @return grid orientation
     */
    public GridOrientation getGridOrientation() {
        return getGridManager().getGridOrientation();
    }

    /**
     * set grid color
     * 
     * @param color
     *            the grid color to set
     */
    public void setGridColor(Color color) {
        getGridManager().setGridColor(color);
    }

    /**
     * get grid color
     * 
     * @return grid color
     */
    public Color getGridColor() {
        return getGridManager().getGridColor();
    }

    /**
     * set grid stroke
     * 
     * @param stroke
     *            the stroke to set
     */
    public void setGridStroke(Stroke stroke) {
        getGridManager().setGridStroke(stroke);
    }

    /**
     * get grid stroke
     * 
     * @return grid stroke
     */
    public Stroke getGridStroke() {
        return getGridManager().getGridStroke();
    }
    
    /**
     * <code>CompoundGrid</code> takes the responsibility to manage compound device grids
     * 
     * @author Sebastien Janaud
     */
    public static class CompoundGrid extends GridPlugin<CompoundGridManager> {
    	
    	
        /**
         * <code>H</code> creates {@link GridOrientation#Horizontal} {@link Grid} based on {@link CompoundGridManager}
         */
        public static class H extends CompoundGrid {

            /**
             * <code>H</code> creates {@link GridOrientation#Horizontal} {@link Grid} based on {@link CompoundGridManager}
             * 
             * @param gridCount
             */
            public H(int gridCount) {
                super(GridOrientation.Horizontal);
            }
        }
        
    	
        /**
         * <code>V</code> creates {@link GridOrientation#Vertical} {@link Grid} based on {@link CompoundGridManager}
         */
        public static class V extends CompoundGrid {

            /**
             * <code>V</code> creates {@link GridOrientation#Vertical} {@link Grid} based on {@link CompoundGridManager}
             * 
             * @param gridCount
             */
            public V(int gridCount) {
                super(GridOrientation.Vertical);
            }
        }
        
        
    	/**
         * create compound grid plug in
         * 
         * @param gridOrientation
         */
        public CompoundGrid(GridOrientation gridOrientation) {
            super(new CompoundGridManager(gridOrientation));
        }
        
        /**
         * add manager in this compound
         * @param gmanagers
         */
        public void addManager(GridManager...gmanagers){
        	getGridManager().addManagers(gmanagers);
        }
    }

    /**
     * <code>StaticGrid</code> takes the responsibility to manage static device grids
     * 
     * @author Sebastien Janaud
     */
    public static class StaticGrid extends GridPlugin<StaticGridManager> {

        /**
         * <code>H</code> creates {@link GridOrientation#Horizontal} {@link Grid} based on {@link StaticGridManager}
         */
        public static class H extends StaticGrid {

            /**
             * <code>H</code> creates {@link GridOrientation#Horizontal} {@link Grid} based on {@link StaticGridManager}
             * 
             * @param gridCount
             */
            public H(int gridCount) {
                super(GridOrientation.Horizontal, gridCount);
            }
        }

        /**
         * <code>H</code> creates {@link GridOrientation#Vertical} {@link Grid} based on {@link StaticGridManager}
         */
        public static class V extends StaticGrid {

            /**
             * <code>H</code> creates {@link GridOrientation#Vertical} {@link Grid} based on {@link StaticGridManager}
             * 
             * @param gridCount
             */
            public V(int gridCount) {
                super(GridOrientation.Vertical, gridCount);
            }
        }

        /**
         * create static grid plug in
         * 
         * @param gridOrientation
         * @param gridCount
         */
        public StaticGrid(GridOrientation gridOrientation, int gridCount) {
            super(new StaticGridManager(gridOrientation, gridCount));
        }

        /**
         * set the grid grid count
         * 
         * @param gridCount
         *            the grid count to set
         */
        public void setGridCount(int gridCount) {
            getGridManager().setGridCount(gridCount);
        }

        /**
         * get the grid count in the static manager
         * 
         */
        public void getGridCount() {
            getGridManager().getGridCount();
        }

    }

    /**
     * <code>ModeledGrid</code> takes the responsibility to manage device modeled grids
     * 
     * @author Sebastien Janaud
     */
    public static class ModeledGrid extends GridPlugin<ModeledGridManager> {

        /**
         * <code>H</code> creates {@link GridOrientation#Horizontal} {@link Grid} based on {@link ModeledGridManager}
         */
        public static class H extends ModeledGrid {

            /**
             * creates {@link GridOrientation#Horizontal} {@link Grid} based on {@link ModeledGridManager}
             */
            public H() {
                super(GridOrientation.Horizontal);
            }
        }

        /**
         * <code>V</code> creates {@link GridOrientation#Vertical} {@link Grid} based on {@link ModeledGridManager}
         */
        public static class V extends ModeledGrid {

            /**
             * creates {@link GridOrientation#Vertical} {@link Grid} based on {@link ModeledGridManager}
             */
            public V() {
                super(GridOrientation.Vertical);
            }
        }

        /**
         * create modeled grid plug in
         * 
         * @param gridOrientation
         */
        public ModeledGrid(GridOrientation gridOrientation) {
            super(new ModeledGridManager(gridOrientation));
        }

        /**
         * register grid models
         * 
         * @param model
         */
        public void registerGridModel(GridModel model) {
            getGridManager().registerGridModel(model);
        }

        /**
         * register grid models
         * 
         * @param models
         */
        public void registerGridModels(List<GridModel> models) {
            getGridManager().registerGridModels(models);
        }

        /**
         * register grid models
         * 
         * @param models
         */
        public void registerGridModels(GridModel... models) {
            getGridManager().registerGridModels(models);
        }

        /**
         * register grid models
         * 
         * @param gridModelCollections
         */
        public void registerGridModels(GridModelCollections gridModelCollections) {
            getGridManager().registerGridModels(gridModelCollections);
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.grid.GridPlugin#paintGrids(com.jensoft.core.view.View, java.awt.Graphics2D)
         */
        @Override
        protected void paintGrids(View v2d, Graphics2D g2d) {

            if (getGridManager() == null) {
                return;
            }
            getGridManager().setProjection(getProjection());

            List<GridModel> models = getGridManager().getSequenceGrid();
            for (GridModel gridModel : models) {
                List<Grid> grids = gridModel.generateGrids();
                getGridPainter().setLayoutManager(getGridManager());
                getGridPainter().paintGrids(g2d, grids);
            }
        }

    }

    /**
     * <code>FreeGrid</code> takes the responsibility to manage device free grids
     * 
     * @author Sebastien Janaud
     */
    public static class FreeGrid extends GridPlugin<FreeGridManager> {

        public static class H extends FreeGrid {

            public H() {
                super(GridOrientation.Horizontal);
            }
        }

        public static class V extends FreeGrid {

            public V() {
                super(GridOrientation.Vertical);
            }
        }

        /**
         * create free grid plugin
         */
        public FreeGrid(GridOrientation gridOrientation) {
            super(new FreeGridManager(gridOrientation));
        }

        /**
         * add the specified grid
         * 
         * @param grid
         *            the grid to add
         */
        public void addGrid(Grid grid) {
            FreeGridManager manager = (FreeGridManager) getGridManager();
            manager.addGrid(grid);
        }

        /**
         * add the specified grid
         * 
         * @param grid
         *            the grid to add
         */
        public void addGrid(double grid) {
            getGridManager().addGrid(grid);
        }

        /**
         * add grid
         * 
         * @param grid
         * @param gridColor
         */
        public void addGrid(double grid, Color gridColor) {
            getGridManager().addGrid(grid, gridColor);
        }

        /**
         * add grid
         * 
         * @param grid
         * @param annotation
         * @param gridColor
         * @param fractionAnnotation
         */
        public void addGrid(double grid, String annotation, Color gridColor,
                float fractionAnnotation) {
            getGridManager().addGrid(grid, annotation, gridColor, fractionAnnotation);
        }

    }

    /**
     * <code>FreeGrid</code> takes the responsibility to manage device flow grids
     * 
     * @author Sebastien Janaud
     */
    public static class FlowGrid extends GridPlugin<FlowGridManager> {

        public static class H extends FlowGrid {

            /**
             * @param startGrid
             * @param endGrid
             * @param interval
             */
            public H(double startGrid, double endGrid, double interval) {
                super(startGrid, endGrid, interval, GridOrientation.Horizontal);
            }
        }

        public static class V extends FlowGrid {

            /**
             * @param startGrid
             * @param endGrid
             * @param interval
             */
            public V(double startGrid, double endGrid, double interval) {
                super(startGrid, endGrid, interval, GridOrientation.Vertical);
            }
        }

        /**
         * create flow grid plugin with specified given parameters
         * 
         * @param startGrid
         * @param endGrid
         * @param interval
         * @param gridOrientation
         */
        public FlowGrid(double startGrid, double endGrid, double interval, GridOrientation gridOrientation) {
            super(new FlowGridManager(gridOrientation, startGrid, endGrid, interval));
        }

        /**
         * get the flow grid layout manager for this layout
         */
        @Override
        public FlowGridManager getGridManager() {
            return (FlowGridManager) super.getGridManager();
        }

        /**
         * set the flow start value
         * 
         * @param flowStart
         */
        public void setFlowStart(double flowStart) {
            getGridManager().setFlowStart(flowStart);
        }

        /**
         * set the flow end value
         * 
         * @param flowEnd
         */
        public void setFlowEnd(double flowEnd) {
            getGridManager().setFlowStart(flowEnd);
        }

        /**
         * set the flow interval value
         * 
         * @param flowInterval
         */
        public void setFlowInterval(double flowInterval) {
            getGridManager().setFlowInterval(flowInterval);
        }

        /**
         * get the flow start value
         * 
         * @return the flow start
         */
        public double getFlowStart() {
            return getGridManager().getFlowStart();
        }

        /**
         * get the flow end value
         * 
         * @return the flow end
         */
        public double getFlowEnd() {
            return getGridManager().getFlowEnd();
        }

        /**
         * get the flow interval value
         * 
         * @return the flow interval
         */
        public double getFlowInterval() {
            return getGridManager().getFlowInterval();
        }

    }

    /**
     * <code>MultiplierGrid</code> takes the responsibility to manage device grids by multiplier
     * 
     * @author Sebastien Janaud
     */
    public static class MultiplierGrid extends GridPlugin<MultiplierGridManager> {

        /**
         * <code>H</code> creates {@link GridOrientation#Horizontal} {@link MultiplierGrid} based on
         * {@link MultiplierGridManager}
         */
        public static class H extends MultiplierGrid {

            /**
             * <code>H</code> creates {@link GridOrientation#Horizontal} {@link MultiplierGrid} based on
             * {@link MultiplierGridManager}
             * 
             * @param ref
             * @param multiplier
             */
            public H(double ref, double multiplier) {
                super(ref, multiplier, GridOrientation.Horizontal);
            }
        }

        /**
         * <code>V</code> creates {@link MultiplierGrid} for {@link GridOrientation#Vertical} based on
         * {@link MultiplierGridManager}
         */
        public static class V extends MultiplierGrid {

            /**
             * <code>V</code> creates {@link MultiplierGrid} for {@link GridOrientation#Vertical} based on
             * {@link MultiplierGridManager}
             * 
             * @param ref
             * @param multiplier
             */
            public V(double ref, double multiplier) {
                super(ref, multiplier, GridOrientation.Vertical);
            }
        }

        /**
         * create {@link MultiplierGrid} for specified parameters
         * 
         * @param ref
         *            the grid reference
         * @param multiplier
         *            the grid multiplier
         * @param gridOrientation
         *            the grid orientation
         */
        public MultiplierGrid(double ref, double multiplier, GridOrientation gridOrientation) {
            super(new MultiplierGridManager(gridOrientation, ref, multiplier));
        }

        /**
         * set the grid reference value
         * 
         * @param ref
         *            the grid reference to set
         */
        public void setRef(double ref) {
            getGridManager().setRef(ref);
        }

        /**
         * set the grid multiplier
         * 
         * @param interval
         */
        public void setMultiplier(double interval) {
            getGridManager().setMultiplier(interval);
        }

        /**
         * get the grid reference value
         * 
         * @return the grid reference
         */
        public double getRef() {
            return getGridManager().getRef();
        }

        /**
         * get the grid multiplier
         * 
         * @return the grid multiplier
         */
        public double getMultiplier() {
            return getGridManager().getMultiplier();
        }

    }

}
