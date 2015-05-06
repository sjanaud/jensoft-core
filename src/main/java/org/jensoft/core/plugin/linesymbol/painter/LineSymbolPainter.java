/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.linesymbol.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.linesymbol.core.LineSymbolComponent;

public interface LineSymbolPainter {

    public void paintLineSymbol(Graphics2D g2d,
            LineSymbolComponent lineSymbolComponent);
}
