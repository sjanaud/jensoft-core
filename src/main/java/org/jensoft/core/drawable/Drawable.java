/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.drawable;

import java.awt.Graphics2D;

/**
 * defines tagging interface to draw object with the simple draw method
 * 
 * @author Sebastien Janaud
 */
public interface Drawable {

    /**
     * draw this drawable on the specified graphics context
     * 
     * @param g2d
     *            the graphics context
     */
    public void draw(Graphics2D g2d);
}
