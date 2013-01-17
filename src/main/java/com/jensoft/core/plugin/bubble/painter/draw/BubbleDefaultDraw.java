/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.bubble.painter.draw;

import java.awt.Color;
import java.awt.Graphics2D;

import com.jensoft.core.plugin.bubble.Bubble;
import com.jensoft.core.plugin.bubble.painter.BubbleDraw;

/**
 * @author Sebastien Janaud
 */
public class BubbleDefaultDraw extends BubbleDraw {

    /** bubble outline color */
    private Color outlineColor;

    /**
     * default draw
     */
    public BubbleDefaultDraw() {
    }

    /**
     * default draw with specified parameters
     * 
     * @param outlineColor
     *            teh bubble outline draw
     */
    public BubbleDefaultDraw(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    /**
     * get outline color
     * 
     * @return outline color
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    /**
     * set outline color
     * 
     * @param outlineColor
     *            the outline color to set
     */
    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.bubble.painter.BubbleDraw#paintBubble(java
     * .awt.Graphics2D, com.jensoft.sw2d.core.plugin.bubble.Bubble)
     */
    @Override
    public void paintBubble(Graphics2D g2d, Bubble bubble) {

        if (outlineColor != null) {
            g2d.setColor(outlineColor);
        }
        else if (bubble.getThemeColor() != null) {
            g2d.setColor(bubble.getThemeColor());
        }
        else {
            g2d.setColor(bubble.getHost().getWindow2D().getThemeColor());
        }

        g2d.draw(bubble.getBubbleShape());

    }

}
