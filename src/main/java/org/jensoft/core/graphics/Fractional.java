/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * define the fractional metrics option for {@link RenderingHints}
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public enum Fractional {
    On(RenderingHints.VALUE_FRACTIONALMETRICS_ON), Off(
            RenderingHints.VALUE_FRACTIONALMETRICS_OFF);

    private Object value;

    Fractional(Object value) {
        this.value = value;
    }

    public void configureGraphics(Graphics2D g2d) {
        // g.getRenderingHints().put(RenderingHints.KEY_FRACTIONALMETRICS,
        // value);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, value);
    }
}
