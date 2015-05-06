/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.scatter.morphe;

import java.awt.Shape;

/**
 * <code>ScatterMorphe</code>
 * 
 * @author sebastien janaud
 * @since 1.0
 */
public abstract class ScatterMorphe {

    public double centerX;
    public double centerY;

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public abstract Shape getMorphe();

}
