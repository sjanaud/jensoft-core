/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.symbol;

import java.util.EventObject;

/**
 * An event which indicates that a mouse action occurred in a point.
 * 
 * @author Sebastien Janaud
 */
public class PointEvent extends EventObject {

    private static final long serialVersionUID = 8303168339926163455L;

    public PointEvent(Object source) {
        super(source);
    }

    /**
     * get the point symbol of the event
     * 
     * @return the point symbol
     */
    public PointSymbol getPointSymbol() {
        return (PointSymbol) getSource();
    }

}
