/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.capacity;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Fractional;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

public class CapacityPlugin extends AbstractPlugin {

    private int cellWidthNumber;
    private int cellHeightNumber;
    private double capacityWidth;
    private double capacityHeight;;

    public CapacityPlugin(int cellWidthNumber, int cellHeightNumber) {
        this.cellWidthNumber = cellWidthNumber;
        this.cellHeightNumber = cellHeightNumber;
        setFractionalMetrics(Fractional.On);
        setAntialiasing(Antialiasing.On);

    }

    private List<CapacityUnit> capacities = new ArrayList<CapacityUnit>();

    public void registerCapacity(CapacityUnit capacity) {
        capacities.add(capacity);
    }

    private void paintCapacity(Graphics2D g2d, CapacityUnit capacity) {

        g2d.setColor(capacity.getColor());

        for (CapacityCell cell : capacity.getCells()) {
            Point2D p2d = capacityToPixel(cell);
            Rectangle2D cellUnit = new Rectangle2D.Double(p2d.getX(),
                                                          p2d.getY(), capacityWidth, capacityHeight);
            g2d.fill(cellUnit);
        }

    }

    private Point2D capacityToPixel(CapacityCell cell) {

        Point2D p2d = new Point2D.Double(
                                         new Double(cell.getColumn()) * capacityWidth,
                                         new Double(cell.getRow()) * capacityHeight);
        return p2d;
    }

    public int cellToVIndex(CapacityCell cell) {
        return (cell.getColumn() + 1) * cellHeightNumber - 1;
    }

    public CapacityCell vIndexToCell(int vIndex) {
        int[] rc = new int[2];
        rc[0] = vIndex / cellHeightNumber;
        rc[1] = vIndex % cellHeightNumber;
        return new CapacityCell(rc[1], rc[0]);
    }

    public int cellToHIndex(CapacityCell cell) {
        return (cell.getRow() + 1) * cellWidthNumber - 1;
    }

    public CapacityCell hIndexToCell(int hIndex) {
        int[] rc = new int[2];
        rc[0] = hIndex / cellWidthNumber;
        rc[1] = hIndex % cellWidthNumber;
        return new CapacityCell(rc[0], rc[1]);
    }

    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {

        if (windowPart != WindowPart.Device) {
            return;
        }

        int width = getWindow2D().getDevice2D().getDeviceWidth();
        int height = getWindow2D().getDevice2D().getDeviceHeight();

        capacityWidth = new Double(width) / new Double(cellWidthNumber);
        capacityHeight = new Double(height) / new Double(cellHeightNumber);

        for (CapacityUnit c : capacities) {
            paintCapacity(g2d, c);
        }

    }

}
