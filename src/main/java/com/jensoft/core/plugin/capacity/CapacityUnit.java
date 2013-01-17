/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.capacity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class CapacityUnit {

    private Color color;

    public CapacityUnit() {

    }

    private List<CapacityCell> cells = new ArrayList<CapacityCell>();

    public void registerCell(CapacityCell cell) {
        cells.add(cell);
    }

    public List<CapacityCell> getCells() {
        return cells;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
