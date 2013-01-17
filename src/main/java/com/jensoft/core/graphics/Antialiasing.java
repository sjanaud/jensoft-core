/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * define the antialiasing option for {@link RenderingHints}
 * 
 * @author Sebastien Janaud
 */
public enum Antialiasing {
    On(RenderingHints.VALUE_ANTIALIAS_ON), Off(
            RenderingHints.VALUE_ANTIALIAS_OFF);

    private Object value;

    Antialiasing(Object value) {
        this.value = value;
    }

    public void configureGraphics(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, value);
    }
}
