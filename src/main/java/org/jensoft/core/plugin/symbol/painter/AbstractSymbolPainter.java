/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.symbol.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.symbol.SymbolComponent;
import org.jensoft.core.view.ViewPart;

public abstract class AbstractSymbolPainter implements SymbolPainter {

    @Override
    public void paintSymbol(Graphics2D g2d, SymbolComponent symbol,
            ViewPart viewPart) {
    }

}
