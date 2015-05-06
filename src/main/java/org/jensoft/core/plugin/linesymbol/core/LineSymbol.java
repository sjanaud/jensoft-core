/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.linesymbol.core;

import org.jensoft.core.plugin.linesymbol.painter.LineSymbolPainter;

/**
 * Line symbol define symbols along a line.
 */
public abstract class LineSymbol extends LineSymbolComponent {

    /** a string value for specified symbol */
    private String symbol;
    /** symbols painter */
    private LineSymbolPainter symbolPainter;
    /** line painter */
    private LineSymbolPainter linePainter;

    /**
     * constructor
     */
    public LineSymbol() {

    }

    /**
     * get the symbol painter
     * 
     * @return the symbol painter
     */
    public LineSymbolPainter getSymbolPainter() {
        return symbolPainter;
    }

    /**
     * set the symbol painter
     * 
     * @param painter
     */
    public void setSymbolPainter(LineSymbolPainter painter) {
        symbolPainter = painter;
    }

    /**
     * get the line painter
     * 
     * @return the line painter
     */
    public LineSymbolPainter getLinePainter() {
        return linePainter;
    }

    /**
     * set the line painter
     * 
     * @param linePainter
     */
    public void setLinePainter(LineSymbolPainter linePainter) {
        this.linePainter = linePainter;
    }

    /**
     * get the symbol
     * 
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * set the symbol
     * 
     * @param symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
