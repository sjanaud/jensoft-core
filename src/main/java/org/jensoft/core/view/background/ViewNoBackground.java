/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.view.background;

import java.awt.Graphics2D;

import org.jensoft.core.view.View;

/**
 * <code>ViewNoBackground</code> defines a no background view
 * 
 * @since 1.0
 * 
 * @author sebastien janaud
 *
 */
public class ViewNoBackground extends ViewBackgroundPainter {

	/**
	 * create a no background
	 */
	public ViewNoBackground() {
	}

	/* (non-Javadoc)
	 * @see org.jensoft.core.view.background.BackgroundPainter#paintViewBackground(org.jensoft.core.view.View, int, int, java.awt.Graphics2D)
	 */
	@Override
	public void paintViewBackground(View view,int viewWidth,int viewHeight, Graphics2D g2d) {
	}

}
