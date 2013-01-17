/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.outline;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;

import com.jensoft.core.window.WindowPart;

public abstract class AbstractOutlinePainter {

    private Color outlineColor;

    /**
     * @return the outlineColor
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    /**
     * @param outlineColor
     *            the outlineColor to set
     */
    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    public abstract void doPaintOutline(Component c, Graphics2D g2d, int x,
            int y, int weigh, WindowPart windowPart);

}
