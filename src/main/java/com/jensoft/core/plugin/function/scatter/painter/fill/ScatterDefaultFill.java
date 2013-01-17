/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.scatter.painter.fill;

import java.awt.Color;
import java.awt.Graphics2D;

import com.jensoft.core.plugin.function.scatter.ScatterFunction.ScatterPoint;
import com.jensoft.core.plugin.function.scatter.painter.ScatterFill;

public class ScatterDefaultFill extends ScatterFill {

    /**
     * create default scatter fill
     */
    public ScatterDefaultFill() {
    }

    @Override
    public void paintScatter(Graphics2D g2d, ScatterPoint scatter) {
        Color cBase = scatter.getThemeColor();
        g2d.setColor(cBase);
        g2d.fill(scatter.getPrimitiveShape());
    }

}
