/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.view.background;

import java.awt.Graphics2D;

import org.jensoft.core.view.View;

/**
 * <code>ViewBackgroundPainter</code>
 * 
 * @since 1.0
 * 
 * @author Sebastien Janaud
 */
public abstract class ViewBackgroundPainter {

	/**
	 * paint the view background
	 * @param view
	 * @param viewWidth
	 * @param viewHeight
	 * @param g2d
	 */
    public abstract void paintViewBackground(View view,int viewWidth,int viewHeight, Graphics2D g2d);

}
