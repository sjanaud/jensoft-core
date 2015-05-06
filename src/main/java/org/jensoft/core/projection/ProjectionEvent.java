/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.projection;

import java.util.EventObject;

/**
 * @version 1.0
 * @author Sebastien Janaud
 */
public class ProjectionEvent extends EventObject {

    /** uid */
    private static final long serialVersionUID = -2649243100213574998L;

    public ProjectionEvent(Projection w2d) {
        super(w2d);
    }

}
