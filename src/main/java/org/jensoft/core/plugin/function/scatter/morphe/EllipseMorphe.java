/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.scatter.morphe;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * <code>EllipseMorphe</code>
 * 
 * @author sebastien janaud
 * @since 1.0
 */
public class EllipseMorphe extends ScatterMorphe {

    private double width;
    private double height;

    public EllipseMorphe(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Shape getMorphe() {
        Ellipse2D morphe = new Ellipse2D.Double(0, 0, width, height);
        return morphe;
    }

}
