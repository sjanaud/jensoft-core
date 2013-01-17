/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.scatter.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.function.scatter.ScatterFunction.ScatterPoint;

/**
 * <code>AbstractScatterPainter</code> knows how to paint scatter
 */
public abstract class AbstractScatterPainter implements ScatterPainter {

    @Override
    public void paintScatter(Graphics2D g2d, ScatterPoint scatter) {
    }

}
