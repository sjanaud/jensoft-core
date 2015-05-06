/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.outline;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;

import org.jensoft.core.view.ViewPart;


/**
 * <code>AbstractOutlinePainter</code>
 * 
 * @since 1.0
 * 
 * @author sebastien janaud
 *
 */
public abstract class AbstractOutlinePainter {

	/**outline line color*/
    private Color outlineColor;

    /**
     * @return the outlineColor
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    /**
     * @param outlineColor
     *            the outlineColor to set
     */
    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    /**
     * paint outline according to given parameters
     * @param c
     * @param g2d
     * @param x
     * @param y
     * @param weigh
     * @param viewPart
     */
    public abstract void doPaintOutline(Component c, Graphics2D g2d, int x,int y, int weigh, ViewPart viewPart);
            

}
