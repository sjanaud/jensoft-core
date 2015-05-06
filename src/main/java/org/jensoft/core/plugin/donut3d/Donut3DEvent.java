/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut3d;

import java.util.EventObject;

/**
 * <code>Donut3DEvent</code> An event which indicates that a mouse action occurred in a donut3D Slice.
 * 
 * @author Sebastien Janaud
 */
public class Donut3DEvent extends EventObject {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4613935844829571364L;

    /**
     * create new donut3D event
     * 
     * @param source
     *            the donut3D Slice source
     */
    public Donut3DEvent(Object source) {
        super(source);
    }

    /**
     * get the donut3D slice of the event
     * 
     * @return the donut3D slice
     */
    public Donut3DSlice getDonut3DSlice() {
        return (Donut3DSlice) getSource();
    }

}
