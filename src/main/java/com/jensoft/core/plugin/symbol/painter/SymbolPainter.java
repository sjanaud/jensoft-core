/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */package com.jensoft.core.plugin.symbol.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.symbol.SymbolComponent;
import com.jensoft.core.view.ViewPart;

/**
 * <code>SymbolPainter</code>
 * <p>
 * tagging interface for symbol painting operation
 * </p>
 * 
 * @author Sebastien Janaud
 */
public interface SymbolPainter {

    /**
     * paint symbol
     * 
     * @param g2d
     *            the graphics context
     * @param symbol
     *            the symbol to paint
     * @param viewPart
     *            the window zone
     */
    void paintSymbol(Graphics2D g2d, SymbolComponent symbol,
            ViewPart viewPart);
}
