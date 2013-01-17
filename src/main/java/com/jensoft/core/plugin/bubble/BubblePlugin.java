/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.bubble;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.bubble.painter.BubbleDraw;
import com.jensoft.core.plugin.bubble.painter.BubbleEffect;
import com.jensoft.core.plugin.bubble.painter.BubbleFill;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * BubblePlugin
 * 
 * @see BubblePlugin
 * @see BubbleDraw
 * @see BubbleFill
 * @see BubbleEffect
 * @author sebastien janaud
 */
public class BubblePlugin extends AbstractPlugin {

    /** bubbles registry */
    private List<Bubble> bubbles;

    /**
     * create bubble plugin
     */
    public BubblePlugin() {
        setName("BubblePlugin");
        setPriority(100);
        bubbles = new ArrayList<Bubble>();
        setAntialiasing(Antialiasing.On);
    }

    /**
     * add bubble
     * 
     * @param bubble
     *            the bubble to add
     */
    public void addBubble(Bubble bubble) {
        bubble.setHost(this);
        bubbles.add(bubble);
    }

    /**
     * remove the bubble
     * 
     * @param bubble
     *            the bubble to remove
     */
    public void removeBubble(Bubble bubble) {
        bubbles.remove(bubble);
    }

    /**
     * paint bubble
     * 
     * @param v2d
     * @param g2d
     * @param bubble
     */
    private void paintBubble(View2D v2d, Graphics2D g2d, Bubble bubble) {

        if (bubble.getBubbleFill() != null) {
            bubble.getBubbleFill().paintBubble(g2d, bubble);
        }

        if (bubble.getBubbleDraw() != null) {
            bubble.getBubbleDraw().paintBubble(g2d, bubble);
        }

        if (bubble.getBubbleEffect() != null) {
            bubble.getBubbleEffect().paintBubble(g2d, bubble);
        }

    }

    /**
     * solve bubble geometry
     */
    private void solveGeometry() {
        for (Bubble bubble : bubbles) {
            Point2D p2dUserCenter = new Point2D.Double(bubble.getX(),
                                                       bubble.getY());
            Point2D p2dDeviceCenter = bubble.getHost().getWindow2D()
                    .userToPixel(p2dUserCenter);

            Ellipse2D bubbleShape = new Ellipse2D.Double(p2dDeviceCenter.getX()
                    - bubble.getRadius(), p2dDeviceCenter.getY()
                    - bubble.getRadius(), 2 * bubble.getRadius(),
                                                         2 * bubble.getRadius());

            bubble.setBubbleShape(bubbleShape);
        }

    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.sw2d
     * .core.view.View2D, java.awt.Graphics2D,
     * com.jensoft.sw2d.core.window.WindowPart)
     */
    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {

        if (windowPart != WindowPart.Device) {
            return;
        }

        solveGeometry();

        for (Bubble bubble : bubbles) {
            paintBubble(v2d, g2d, bubble);
        }

    }

}
