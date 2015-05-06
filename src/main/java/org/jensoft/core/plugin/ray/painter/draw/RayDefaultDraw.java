/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.ray.painter.draw;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jensoft.core.plugin.ray.Ray;

/**
 * classic ray draw
 */
public class RayDefaultDraw extends AbstractRayDraw {

    /** the ray outline color */
    private Color outlineColor;

    /**
     * Empty ray draw, default theme color will be use to draw ray
     */
    public RayDefaultDraw() {
    }

    /**
     * create new classic ray draw with the specified ouline color
     * 
     * @param outlineColor
     */
    public RayDefaultDraw(Color outlineColor) {
        super();
        this.outlineColor = outlineColor;
    }

    /**
     * get the outline to set
     * 
     * @return the outline color
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    /**
     * set the outline ray color
     * 
     * @param outlineColor
     *            the outline color to set
     */
    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.ray.painter.draw.AbstractRayDraw#paintRayDraw(java.awt.Graphics2D, com.jensoft.core.plugin.ray.Ray)
     */
    @Override
    public void paintRayDraw(Graphics2D g2d, Ray ray) {

        Color cBase = ray.getThemeColor();
        if (outlineColor != null) {
            cBase = outlineColor;
        }

        g2d.setColor(cBase);

        g2d.draw(ray.getRayShape());

    }

}
