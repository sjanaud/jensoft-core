/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.bubble.painter.fill;

import java.awt.Color;
import java.awt.Graphics2D;

import com.jensoft.core.plugin.bubble.Bubble;
import com.jensoft.core.plugin.bubble.painter.BubbleFill;

/**
 * BubbleFill1
 * 
 * @author Sebastien Janaud
 */
public class BubbleDefaultFill extends BubbleFill {

    /** color */
    private Color fillColor;

    /**
     * create bubble default fill
     */
    public BubbleDefaultFill() {
    }

    /**
     * create bubble default with specified color
     * 
     * @param fillColor
     *            the fill color to set
     */
    public BubbleDefaultFill(Color fillColor) {
        this.fillColor = fillColor;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.bubble.painter.BubbleFill#paintBubble(java.awt.Graphics2D, com.jensoft.core.plugin.bubble.Bubble)
     */
    @Override
    public void paintBubble(Graphics2D g2d, Bubble bubble) {

        if (fillColor != null) {
            g2d.setColor(fillColor);
        }
        else if (bubble.getThemeColor() != null) {
            g2d.setColor(bubble.getThemeColor());
        }
        else {
            g2d.setColor(bubble.getHost().getProjection().getThemeColor());
        }
        g2d.fill(bubble.getBubbleShape());

    }

}
