/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.scatter.morphe;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * <code>ScatterMorphe</code>
 * 
 * @author sebastien janaud
 * @since 1.0
 */
public class RectangleMorphe extends ScatterMorphe {

    private double width;
    private double height;

    public RectangleMorphe(double width, double height) {

        this.width = width;
        this.height = height;
    }

    @Override
    public Shape getMorphe() {
        Rectangle2D morphe = new Rectangle2D.Double(0, 0, width, height);
        return morphe;
    }

}
