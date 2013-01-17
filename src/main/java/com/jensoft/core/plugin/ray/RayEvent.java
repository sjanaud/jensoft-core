/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray;

import java.util.EventObject;

/**
 * An event which indicates that a mouse action occurred in a ray.
 */
public class RayEvent extends EventObject {

    /*
     * JDK 1.1 serialVersionUID
     */
    private static final long serialVersionUID = 3565497308945273909L;

    public RayEvent(Object source) {
        super(source);
    }

    /**
     * get the ray of the event
     * 
     * @return the ray
     */
    public Ray getRay() {
        return (Ray) getSource();
    }

}
