/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.bubble.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.bubble.Bubble;

/**
 * bubble paint definition
 * 
 * @author Sebastien Janaud
 */
public interface BubblePainter {

    /**
     * paint bubble
     * 
     * @param g2d
     *            graphics context
     * @param bubble
     *            the bubble to paint
     */
    public void paintBubble(Graphics2D g2d, Bubble bubble);

}
