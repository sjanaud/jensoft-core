/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol.painter.draw;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.symbol.BarSymbol;
import com.jensoft.core.plugin.symbol.SymbolComponent;
import com.jensoft.core.plugin.symbol.painter.AbstractSymbolPainter;
import com.jensoft.core.window.WindowPart;

public abstract class AbstractBarDraw extends AbstractSymbolPainter {

    protected abstract void paintBarDraw(Graphics2D g2d, BarSymbol bar);

    @Override
    public final void paintSymbol(Graphics2D g2d, SymbolComponent symbol,
            WindowPart windowPart) {
        if (symbol.isVisible()) {
            paintBarDraw(g2d, (BarSymbol) symbol);
        }
    }

}
