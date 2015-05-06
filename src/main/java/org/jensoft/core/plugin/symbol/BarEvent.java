/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.symbol;

import java.util.EventObject;

/**
 * <code>BarEvent</code> An event which indicates that a mouse action occurred in a bar.
 * 
 * @author Sebastien Janaud
 */
public class BarEvent extends EventObject {

    private static final long serialVersionUID = 8303168339926163455L;

    public BarEvent(Object source) {
        super(source);
    }

    /**
     * get the bar symbol of the event
     * 
     * @return the bar symbol
     */
    public BarSymbol getBarSymbol() {
        return (BarSymbol) getSource();
    }

}
