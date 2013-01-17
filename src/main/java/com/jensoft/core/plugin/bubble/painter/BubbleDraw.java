/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.bubble.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.bubble.Bubble;

/**
 * BubbleDraw
 * 
 * @author Sebastien Janaud
 */
public abstract class BubbleDraw extends AbstractBubblePainter {

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.bubble.painter.AbstractBubblePainter#paintBubble
     * (java.awt.Graphics2D, com.jensoft.sw2d.core.plugin.bubble.Bubble)
     */
    @Override
    public void paintBubble(Graphics2D g2d, Bubble bubble) {
    }

}
