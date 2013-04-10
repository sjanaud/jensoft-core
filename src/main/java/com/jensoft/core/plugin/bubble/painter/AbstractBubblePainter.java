/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.bubble.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.bubble.Bubble;

/**
 * AbstractBubblePainter
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractBubblePainter implements BubblePainter {

 
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.bubble.painter.BubblePainter#paintBubble(java.awt.Graphics2D, com.jensoft.core.plugin.bubble.Bubble)
     */
    @Override
    public void paintBubble(Graphics2D g2d, Bubble bubble) {
    }

}
