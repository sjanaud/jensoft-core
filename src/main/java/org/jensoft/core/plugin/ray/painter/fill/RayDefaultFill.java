/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.ray.painter.fill;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jensoft.core.plugin.ray.Ray;

/**
 * <code>RayDefaultFill</code> fills the ray with the specified color or ray theme color
 * 
 * @author sebastien janaud
 */
public class RayDefaultFill extends AbstractRayFill {

    /** ray fill color */
    private Color rayColor;

    /**
     * create default fill color, the ray will be fill with ray theme color
     */
    public RayDefaultFill() {
    }

    /**
     * create ray default fill color with specified color
     * 
     * @param rayColor
     *            the fill color
     */
    public RayDefaultFill(Color rayColor) {
        this.rayColor = rayColor;
    }

    /**
     * @return the rayColor
     */
    public Color getRayColor() {
        return rayColor;
    }

    /**
     * @param rayColor
     *            the rayColor to set
     */
    public void setRayColor(Color rayColor) {
        this.rayColor = rayColor;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.ray.painter.fill.AbstractRayFill#paintRayFill(java.awt.Graphics2D, com.jensoft.core.plugin.ray.Ray)
     */
    @Override
    public void paintRayFill(Graphics2D g2d, Ray ray) {
        if (rayColor != null) {
            g2d.setColor(rayColor);
            g2d.fill(ray.getRayShape());
        }
        else {
            Color cBase = ray.getThemeColor();
            g2d.setColor(cBase);
            g2d.fill(ray.getRayShape());
        }
    }

}
