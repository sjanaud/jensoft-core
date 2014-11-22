/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol.painter.effect;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.symbol.BarSymbol;
import com.jensoft.core.plugin.symbol.SymbolComponent;
import com.jensoft.core.plugin.symbol.painter.AbstractSymbolPainter;
import com.jensoft.core.view.ViewPart;

public abstract class AbstractBarEffect extends AbstractSymbolPainter {

    protected abstract void paintBarEffect(Graphics2D g2d, BarSymbol bar);

    @Override
    public final void paintSymbol(Graphics2D g2d, SymbolComponent symbol,
            ViewPart viewPart) {
        if (symbol.isVisible()) {
            paintBarEffect(g2d, (BarSymbol) symbol);
        }
    }

}
