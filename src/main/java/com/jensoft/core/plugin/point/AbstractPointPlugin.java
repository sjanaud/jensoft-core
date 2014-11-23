/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.point;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>AbstractPointPlugin</code> defines plugin to paint point within projection
 * 
 * @since 1.0
 * 
 * @author sebastien janaud
 */
public abstract class AbstractPointPlugin extends AbstractPlugin {

	
	/**
	 * override to paint points 
	 * @param v2d
	 * @param g2d
	 */
    public abstract void doPaintPoints(View v2d, Graphics2D g2d);

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {
        if (viewPart != ViewPart.Device) {
            return;
        }
        doPaintPoints(view, g2d);
    }

}
