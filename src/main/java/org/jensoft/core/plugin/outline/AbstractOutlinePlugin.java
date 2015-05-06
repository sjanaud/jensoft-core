/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.outline;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

/**
 * <code>AbstractOutlinePlugin</code> abstract definition of a delegate that takes the responsibility of
 * painting a projection Outline Layout.<BR>
 * 
 * @since 1.0
 * 
 * @author sebastien janaud
 */
public abstract class AbstractOutlinePlugin extends AbstractPlugin {

	
	/**
	 * paint outline
	 * @param view
	 * @param g2d
	 * @param viewPart
	 */
    public abstract void doPaintOutline(View view, Graphics2D g2d,ViewPart viewPart);
            

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    public final void paintPlugin(View view, Graphics2D g2d,  ViewPart viewPart) {
        doPaintOutline(view, g2d, viewPart);
    }

}
