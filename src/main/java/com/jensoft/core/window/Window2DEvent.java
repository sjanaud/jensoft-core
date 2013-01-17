/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.window;

import java.util.EventObject;

/**
 * @version 1.0
 * @author Sebastien Janaud
 */
public class Window2DEvent extends EventObject {

    /** uid */
    private static final long serialVersionUID = -2649243100213574998L;

    public Window2DEvent(Window2D w2d) {
        super(w2d);
    }

}
