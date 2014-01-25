/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * define the alpha interpolation option for {@link RenderingHints}
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public enum AlphaInterpolation {
    Quality(RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY), Speed(
            RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED), Default(
            RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

    private Object value;

    AlphaInterpolation(Object value) {
        this.value = value;
    }

    public void configureGraphics(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, value);
    }
}
