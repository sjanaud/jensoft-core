/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.bubble.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.bubble.Bubble;

/**
 * AbstractBubblePainter
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractBubblePainter implements BubblePainter {

 
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.bubble.painter.BubblePainter#paintBubble(java.awt.Graphics2D, org.jensoft.core.plugin.bubble.Bubble)
     */
    @Override
    public void paintBubble(Graphics2D g2d, Bubble bubble) {
    }

}
