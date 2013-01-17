/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie;

import java.util.EventObject;

/**
 * An event which indicates that a mouse action occurred in a pie slice.
 * 
 * @author Sebastien Janaud
 */
public class PieEvent extends EventObject {

    /** uid */
    private static final long serialVersionUID = 7637532019271993369L;

    /**
     * create new pie event
     * 
     * @param source
     */
    public PieEvent(Object source) {
        super(source);
    }

    /**
     * get the pie slice of the event
     * 
     * @return the pie slice
     */
    public PieSlice getSlice() {
        return (PieSlice) getSource();
    }

}
