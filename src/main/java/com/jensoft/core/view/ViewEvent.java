/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view;

import java.util.EventObject;

/**
 * <code>ViewEvent</code>
 * 
 * @author Sebastien Janaud
 */
public class ViewEvent extends EventObject {

    /** serial version UID */
    private final static long serialVersionUID = 828462626882282L;

    /**
     * create view event for the specified view
     * 
     * @param view
     *            the view
     */
    public ViewEvent(View view) {
        super(view);
    }

}
