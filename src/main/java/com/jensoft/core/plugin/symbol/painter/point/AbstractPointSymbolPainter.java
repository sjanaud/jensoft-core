/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol.painter.point;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.symbol.PointSymbol;
import com.jensoft.core.plugin.symbol.SymbolComponent;
import com.jensoft.core.plugin.symbol.painter.AbstractSymbolPainter;
import com.jensoft.core.view.ViewPart;

public abstract class AbstractPointSymbolPainter extends AbstractSymbolPainter {

    protected abstract void paintPointSymbol(Graphics2D g2d, PointSymbol point);

    @Override
    public final void paintSymbol(Graphics2D g2d, SymbolComponent symbol,
            ViewPart viewPart) {
        if (symbol.isVisible()) {
            paintPointSymbol(g2d, (PointSymbol) symbol);
        }
    }

}
