/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view;

import java.util.EventObject;

/**
 * View2DEvent
 * 
 * @author Sebastien Janaud
 */
public class View2DEvent extends EventObject {

    /** serial version UID */
    private final static long serialVersionUID = 828462626882282L;

    /**
     * create view event for the specified view
     * 
     * @param v2d
     *            the view
     */
    public View2DEvent(View2D v2d) {
        super(v2d);
    }

}
