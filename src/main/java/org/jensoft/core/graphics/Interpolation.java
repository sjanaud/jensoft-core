/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;


/**
 * 
 * @since 1.0
 * @author sebastien janaud
 *
 */
public enum Interpolation {

    Bicubic(RenderingHints.VALUE_INTERPOLATION_BICUBIC), Bilinear(
            RenderingHints.VALUE_INTERPOLATION_BILINEAR), NearestNeighbor(
            RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

    private Object value;

    Interpolation(Object value) {
        this.value = value;
    }

    public void configureGraphics(Graphics2D g2d) {
        // g2d.getRenderingHints().put(RenderingHints.KEY_INTERPOLATION, value);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, value);
    }
}
