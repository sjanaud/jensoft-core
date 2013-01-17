/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * define the dithering option for {@link RenderingHints}
 * 
 * @author Sebastien Janaud
 */
public enum Dithering {
    On(RenderingHints.VALUE_DITHER_ENABLE), Off(
            RenderingHints.VALUE_DITHER_DISABLE);

    private Object value;

    Dithering(Object value) {
        this.value = value;
    }

    public void configureGraphics(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, value);
    }
}
