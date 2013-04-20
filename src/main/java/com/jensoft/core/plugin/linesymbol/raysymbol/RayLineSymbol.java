/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.linesymbol.raysymbol;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.linesymbol.core.LineSymbol;
import com.jensoft.core.plugin.linesymbol.painter.LinePainter;

/**
 * <code>RayLineSymbol</code> defines a line symbol with rays
 */
public class RayLineSymbol extends LineSymbol {

    /** the rays registered on line symbol */
    private List<RayEntry> entries;

    /**
     * create new Ray line symbol
     */
    public RayLineSymbol() {
        super();
        entries = new ArrayList<RayEntry>();
        setSymbolPainter(new RayLinePainter(Color.WHITE, Color.RED));
        setLinePainter(new LinePainter(TangoPalette.BUTTER1));
    }

    /**
     * add ray entry for this line ray symbol
     * 
     * @param entry
     */
    public void addEntry(RayEntry entry) {
        entries.add(entry);
    }

    /**
     * remove ray entry for this line ray symbol
     * 
     * @param entry
     */
    public void removeEntry(RayEntry entry) {
        entries.remove(entry);
    }

    /**
     * get all entries registered in this line ray symbol
     * 
     * @return ray entries
     */
    public List<RayEntry> getEntries() {
        return entries;
    }

}
