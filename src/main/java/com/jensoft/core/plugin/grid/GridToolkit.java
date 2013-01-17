/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.grid;

import java.awt.Color;
import java.awt.Stroke;

import com.jensoft.core.plugin.Toolkit;
import com.jensoft.core.plugin.grid.Grid.GridOrientation;
import com.jensoft.core.plugin.grid.manager.FlowGridManager;
import com.jensoft.core.plugin.grid.manager.MultiplierGridManager;

/**
 * <code>GridToolkit</code> support various creation manager instance
 */
public class GridToolkit extends Toolkit {

    /**
     * create DynamicGridManager with specified parameters
     * 
     * @param gridOrientation
     *            the grid orientation
     * @param gridRef
     *            the grid reference use to solve grid
     * @param gridInterval
     *            the grid interval
     * @return dynamic grid manager
     */
    public static MultiplierGridManager createDynamicGridManager(
            GridOrientation gridOrientation, double gridRef, double gridInterval) {
        MultiplierGridManager dynamicGridManager = new MultiplierGridManager(
                                                                       gridOrientation, gridRef, gridInterval);
        return dynamicGridManager;
    }

    /**
     * create DynamicGridManager with specified parameters
     * 
     * @param gridOrientation
     *            the grid orientation
     * @param gridRef
     *            the grid reference use to solve grid
     * @param gridInterval
     *            the grid interval
     * @param gridColor
     *            the grid color to set
     * @return dynamic grid manager
     */
    public static MultiplierGridManager createDynamicGridManager(
            GridOrientation gridOrientation, double gridRef,
            double gridInterval, Color gridColor) {
        MultiplierGridManager dynamicGridManager = new MultiplierGridManager(
                                                                       gridOrientation, gridRef, gridInterval);
        dynamicGridManager.setGridColor(gridColor);
        return dynamicGridManager;
    }

    /**
     * create DynamicGridManager with specified parameters
     * 
     * @param gridOrientation
     *            the grid orientation
     * @param gridRef
     *            the grid reference use to solve grid
     * @param gridInterval
     *            the grid interval
     * @param gridColor
     *            the grid color to set
     * @param gridStroke
     *            the grid stroke to set
     * @return dynamic grid manager
     */
    public static MultiplierGridManager createDynamicGridManager(
            GridOrientation gridOrientation, double gridRef,
            double gridInterval, Color gridColor, Stroke gridStroke) {
        MultiplierGridManager dynamicGridManager = new MultiplierGridManager(
                                                                       gridOrientation, gridRef, gridInterval);
        dynamicGridManager.setGridColor(gridColor);
        dynamicGridManager.setGridStroke(gridStroke);
        return dynamicGridManager;
    }

    /**
     * create FlowGridManager with specified parameters
     * 
     * @param gridOrientation
     *            the grid orientation
     * @param startFlow
     *            the flow start grid value
     * @param endFlow
     *            the flow end grid value
     * @param gridInterval
     *            the grid interval
     * @return flow grid manager
     */
    public static FlowGridManager createFlowGridManager(
            GridOrientation gridOrientation, double startFlow, double endFlow,
            double gridInterval) {
        FlowGridManager flowGridManager = new FlowGridManager(gridOrientation,
                                                              startFlow, endFlow, gridInterval);
        return flowGridManager;
    }

    /**
     * create FlowGridManager with specified parameters
     * 
     * @param gridOrientation
     *            the grid orientation
     * @param startFlow
     *            the flow start grid value
     * @param endFlow
     *            the flow end grid value
     * @param gridInterval
     *            the grid interval
     * @param gridColor
     *            the grid color to set
     * @return flow grid manager
     */
    public static FlowGridManager createFlowGridManager(
            GridOrientation gridOrientation, double startFlow, double endFlow,
            double gridInterval, Color gridColor) {
        FlowGridManager flowGridManager = new FlowGridManager(gridOrientation,
                                                              startFlow, endFlow, gridInterval);
        flowGridManager.setGridColor(gridColor);
        return flowGridManager;
    }

    /**
     * create FlowGridManager with specified parameters
     * 
     * @param gridOrientation
     *            the grid orientation
     * @param startFlow
     *            the flow start grid value
     * @param endFlow
     *            the flow end grid value
     * @param gridInterval
     *            the grid interval
     * @param gridColor
     *            the grid color to set
     * @param gridStroke
     *            the grid stroke to set
     * @return flow grid manager
     */
    public static FlowGridManager createFlowGridManager(
            GridOrientation gridOrientation, double startFlow, double endFlow,
            double gridInterval, Color gridColor, Stroke gridStroke) {
        FlowGridManager flowGridManager = new FlowGridManager(gridOrientation,
                                                              startFlow, endFlow, gridInterval);
        flowGridManager.setGridColor(gridColor);
        flowGridManager.setGridStroke(gridStroke);
        return flowGridManager;
    }

}
