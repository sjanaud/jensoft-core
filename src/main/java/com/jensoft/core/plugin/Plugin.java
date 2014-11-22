/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin;

import java.awt.Graphics2D;

import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>Plugin</code> defines the painting plug in operation
 * 
 * @since 1.O
 * @author Sebastien Janaud
 */
public interface Plugin {

	/**
	 * paint the plugin
	 * @param v2d
	 * @param g2d
	 * @param viewPart
	 */
    public void paint(View v2d, Graphics2D g2d, ViewPart viewPart);
}
