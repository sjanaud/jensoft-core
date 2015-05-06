/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.widget.pad;

import org.jensoft.core.plugin.AbstractPlugin;

/**
 * plus minus square pad widget
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractPlusMinusPadWidget<P extends AbstractPlugin> extends AbstractPadWidget<P> {

    /**
     * create abstract plus minus square pad widget
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
    public AbstractPlusMinusPadWidget(String id, double padSquare, int xIndex,
            int yIndex) {
        super(id, padSquare, xIndex, yIndex, new PlusMinusPadGeometry());
    }

}
