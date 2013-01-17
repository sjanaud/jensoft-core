/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.painter.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;

/**
 * <code>AbstractDonut2DDefaultDraw</code>
 * <p>
 * Abstract definition for donut2D draw operation
 * </p>
 */
public class AbstractDonut2DDefaultDraw extends Donut2DDefaultDraw {

    /** outline color */
    private Color outlineColor;

    /**
     * create empty draw
     */
    public AbstractDonut2DDefaultDraw() {
    }

    /**
     * create donut draw with specified color
     * 
     * @param outlineColor
     */
    public AbstractDonut2DDefaultDraw(Color outlineColor) {
        super();
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
     * com.jensoft.sw2d.core.plugin.donut2d.painter.Donut2DDraw#paintDonut2DDraw
     * (java.awt.Graphics2D, com.jensoft.sw2d.core.plugin.donut2d.Donut2D)
     */
    @Override
    public void paintDonut2DDraw(Graphics2D g2d, Donut2D donut2D) {
        g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                             java.awt.AlphaComposite.SRC_OVER, 1f));

        List<Donut2DSlice> sections = donut2D.getSlices();
        g2d.setStroke(new BasicStroke(2f));
        for (int j = 0; j < sections.size(); j++) {

            Donut2DSlice s = sections.get(j);

            g2d.setColor(outlineColor);
            g2d.draw(s.getSlicePath());
        }
    }

}
