/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d;

import java.util.EventObject;

/**
 * <code>Donut2DEvent</code>
 * <p>
 * An event which indicates that a mouse action occurred in a donut2D Slice.
 * </p>
 * 
 * @author Sebastien Janaud
 */
public class Donut2DEvent extends EventObject {

    /** uid */
    private static final long serialVersionUID = 9098820963760861313L;

    /**
     * create new donu2D event
     * 
     * @param source
     *            the donu2D Slice source
     */
    public Donut2DEvent(Object source) {
        super(source);
    }

    /**
     * get the donu2D slice of the event
     * 
     * @return the donu2D slice
     */
    public Donut2DSlice getDonut2DSlice() {
        return (Donut2DSlice) getSource();
    }

}
