/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.widget.pad;

import com.jensoft.core.plugin.AbstractPlugin;

/**
 * abstract backward forward square pad widget
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractBackwardForwardPadWidget<P extends AbstractPlugin> extends
        AbstractPadWidget<P> {

    /**
     * create abstract backward forward square pad widget
     * 
     * @param id
     *            pad id
     * @param padSquare
     *            pas square size
     * @param xIndex
     *            x folder index
     * @param yIndex
     *            y folder index
     */
    public AbstractBackwardForwardPadWidget(String id, double padSquare,
            int xIndex, int yIndex) {
        super(id, padSquare, xIndex, yIndex, new BackwardForwardPadGeometry());
    }

}
