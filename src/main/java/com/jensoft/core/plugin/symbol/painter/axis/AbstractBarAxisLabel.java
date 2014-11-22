/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol.painter.axis;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.symbol.BarSymbol;
import com.jensoft.core.plugin.symbol.SymbolComponent;
import com.jensoft.core.plugin.symbol.painter.AbstractSymbolPainter;
import com.jensoft.core.view.ViewPart;

public abstract class AbstractBarAxisLabel extends AbstractSymbolPainter {

    protected abstract void paintAxisLabel(Graphics2D g2d, BarSymbol bar,
            ViewPart viewPart);

    @Override
    public void paintSymbol(Graphics2D g2d, SymbolComponent symbol,
            ViewPart viewPart) {
        if (symbol.isVisible()) {
            paintAxisLabel(g2d, (BarSymbol) symbol, viewPart);
        }
    }

}
