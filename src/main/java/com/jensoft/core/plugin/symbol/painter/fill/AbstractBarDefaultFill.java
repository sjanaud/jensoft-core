/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol.painter.fill;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import com.jensoft.core.plugin.symbol.BarSymbol;

public class AbstractBarDefaultFill extends BarFill {

    public AbstractBarDefaultFill() {

    }

    @Override
    public void paintBarFill(Graphics2D g2d, BarSymbol bar) {
        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));
        Color cBase = bar.getThemeColor();
        g2d.setColor(cBase);
        if (bar.getBarShape() != null) {
            g2d.fill(bar.getBarShape());
        }
    }

}
